package com.zhuzhi.spring.cloud.alibaba.freeswitch.controller;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.Gateway;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.dial.AutoDialMeta;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.dial.ZhuzCallResult;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.GatewayService;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.task.FreeswitchCommandThread;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.utils.I18NUtils;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.utils.MobileLocUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/call/")
@Slf4j
@Api(tags = "FS呼叫控制接口")
public class DialController {

    @Autowired
    private MobileLocUtil mobileLocUtil;

    @Autowired
    private GatewayService gatewayService;

    @RequestMapping(value = "/autodial", method = RequestMethod.POST)
    @ApiOperation("AI呼叫接口")
    public ZhuzCallResult autodial(@RequestBody AutoDialMeta autoDialMeta) {

        ZhuzCallResult zhuzCallResult = new ZhuzCallResult();

        //权限参数认证
        if (StringUtils.isBlank(autoDialMeta.getAuthkey()) || !"hantian2014".equals(autoDialMeta.getAuthkey())) {
            zhuzCallResult.setCode(ZhuzCallResult.PARAM_ERROR);
            zhuzCallResult.setErrmsg("zhuz: PARAM_ERROR Without authentication");
        }
        //业务参数认证
        else if (StringUtils.isEmpty(autoDialMeta.getSid()) || StringUtils.isEmpty(autoDialMeta.getTel()) || StringUtils.isEmpty(autoDialMeta.getGateway()) || StringUtils.isEmpty(autoDialMeta.getExt_field())) {
            zhuzCallResult.setCode(ZhuzCallResult.PARAM_ERROR);
            zhuzCallResult.setErrmsg("zhuz: PARAM_ERROR key sid tel ext_field is Empty");
        } else {

            // 用于呼叫 将呼叫所需的参数存入map
            Map<String, String> callMap = new HashMap<String, String>();
            callMap.put("command", "dialRobot");
            try {
                //查该外线实体
                Gateway gateway = gatewayService.findByUserName(new Gateway(autoDialMeta.getGateway()));

                if (gateway != null) {

                    String tel = autoDialMeta.getTel();
                    if (StringUtils.isNotBlank(gateway.getAreacode())) {
                        //本外地识别
                        tel = mobileLocUtil.process(tel, gateway.getAreacode());
                    }

                    //未接原因检测关键参数  是否忽略早起媒体
                    String ignore_early_media = autoDialMeta.getIgnore_early_media();
                    if (StringUtils.isBlank(ignore_early_media) || !"true".equals(ignore_early_media)) {
                        ignore_early_media = "false";
                    }

                    //呼叫超时 ignore_early_media为true时才生效
                    String timeout = gateway.getTimeout();
                    if (StringUtils.isBlank(timeout)) {
                        timeout = "60";
                    }

                    //外显设置
                    /*String effective_caller_id_name = gateway.getEffective_caller_id_name();
                    if (StringUtils.isBlank(effective_caller_id_name)) {
                        effective_caller_id_name = gateway.getUserName();
                    }*/

                    String toExecuteCommand = "";
                    if (StringUtils.isNotBlank(gateway.getRegister()) && "true".equals(gateway.getRegister())) {
                        // sip认证注册模式外呼
                        toExecuteCommand = "bgapi originate {sip_from_display=" + autoDialMeta.getGateway() + ",sip_from_uri=sip:" + autoDialMeta.getGateway() + "@" + gateway.getRealm() + ":" + gateway.getPort() + ",sip_auth_username=" + gateway.getUserName() + ",sip_auth_password=" + gateway.getPassword() + ",is_originate=true,sid=" + autoDialMeta.getSid() + ",dial_type=5,ext_field=" + autoDialMeta.getExt_field() + ",ht_vas_gateway=" + autoDialMeta.getGateway() + ",ignore_early_media=" + ignore_early_media + "}[leg_timeout=" + timeout + "]sofia/external/sip:" + tel + "@" + gateway.getRealm() + ":" + gateway.getPort() + " 999888 XML default_outgoing " + autoDialMeta.getGateway() + " " + tel;
                    } else {
                        // siptrunk模式外呼
                        toExecuteCommand = "bgapi originate {sip_from_display=" + autoDialMeta.getGateway() + ",sip_from_uri=sip:" + autoDialMeta.getGateway() + "@" + gateway.getRealm() + ":" + gateway.getPort() + ",is_originate=true,sid=" + autoDialMeta.getSid() + ",dial_type=5,ext_field=" + autoDialMeta.getExt_field() + ",ht_vas_gateway=" + autoDialMeta.getGateway() + ",ignore_early_media=" + ignore_early_media + "}[leg_timeout=" + timeout + "]sofia/external/sip:" + tel + "@" + gateway.getRealm() + ":" + gateway.getPort() + " 999888 XML default_outgoing " + autoDialMeta.getGateway() + " " + tel;
                    }

                    EslMessage eslMessage = FreeswitchCommandThread.sendSyncApiCommand(toExecuteCommand, null);
                    // 处理执行结果
                    zhuzCallResult = wrapEslMessage(eslMessage);
                } else {
                    zhuzCallResult.setCode(ZhuzCallResult.PARAM_ERROR);
                    zhuzCallResult.setErrmsg("zhuz: gateway not exist");
                }

            } catch (Exception e) {
                log.error("zhuz: error-", e);
                zhuzCallResult.setCode(ZhuzCallResult.BIZ_ERROR);
                zhuzCallResult.setErrmsg("zhuz: BIZ_ERROR call faild");
            }
        }

        return zhuzCallResult;
    }

    // 根据返回值封装结果
    private ZhuzCallResult wrapEslMessage(EslMessage eslMessage) {
        ZhuzCallResult zhuzCallResult = new ZhuzCallResult();

        if (eslMessage == null) {
            zhuzCallResult.setCode(ZhuzCallResult.BIZ_ERROR);
            zhuzCallResult.setErrmsg(I18NUtils.getMessage("zhuz: execute_command_result_no_reply", "execute_command_result_no_reply"));
            return zhuzCallResult;
        }

        // 解析bodyLines首行信息
        List<String> bodyLines = eslMessage.getBodyLines();
        if (bodyLines == null || bodyLines.size() == 0 || StringUtils.isBlank(bodyLines.get(0))) {
            zhuzCallResult.setCode(ZhuzCallResult.BIZ_ERROR);
            zhuzCallResult.setErrmsg(I18NUtils.getMessage("zhuz: execute_command_result_no_body", "execute_command_result_no_body"));
            return zhuzCallResult;
        }

        // 封装结果
        String result = bodyLines.get(0);
        log.debug("zhuz: Subcommand result " + result);
        if (result.startsWith("-ERR")) {
            String subResult = result.substring(4);
            zhuzCallResult.setCode(ZhuzCallResult.BIZ_ERROR);
            zhuzCallResult.setErrmsg(subResult.trim());
            return zhuzCallResult;
        } else if (result.startsWith("+OK")) {
            String subResult = result.substring(3);
            zhuzCallResult.setCode(ZhuzCallResult.SUCCESS);
            zhuzCallResult.setResult(subResult.trim());
            return zhuzCallResult;
        } else {
            zhuzCallResult.setCode(ZhuzCallResult.SUCCESS);
            zhuzCallResult.setResult(result);
            return zhuzCallResult;
        }
    }
}
