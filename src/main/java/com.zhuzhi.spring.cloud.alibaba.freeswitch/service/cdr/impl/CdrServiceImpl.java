package com.zhuzhi.spring.cloud.alibaba.freeswitch.service.cdr.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.BaseResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.PushManage;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.cdr.ChannelCdr;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.cdr.ZhuzCdr;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.cdr.ZhuzCdrVo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.mq.ChannelHangup;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.asr.AsrMeta;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.cdr.CdrService;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.cdr.ChannelCdrService;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.cdr.ZhuzCdrService;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.feignservice.ProviderService;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.shared.PushManageShared;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.utils.CommonUtils;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.utils.GsonUtils;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CdrServiceImpl implements CdrService {

    @Autowired
    private ChannelCdrService channelCdrService;

    @Autowired
    private ZhuzCdrService zhuzCdrService;

    //feign语音识别
    @Autowired
    private ProviderService providerService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void saveChannelCdr(ChannelHangup channelHangup) {
        ChannelCdr channelCdr = new ChannelCdr();
        channelCdr.setId(UUIDUtils.findUUID());

        String dial_type = channelHangup.getVariable_dial_type();
        String caller_ANI = channelHangup.getCaller_ani();
        String caller_Caller_ID_Number = channelHangup.getCaller_caller_id_number();        // 逻辑主叫
        String caller_Callee_ID_Number = channelHangup.getCaller_callee_id_number();      // 逻辑被叫
        String caller_Caller_ID_Name = channelHangup.getCaller_caller_id_name();
        String caller_Callee_ID_Name = channelHangup.getCaller_callee_id_name();
        String caller_Destination_Number = channelHangup.getCaller_destination_number();   // 拨号方案对应的号码(如 ext801, callcenter, 801等)
        String variable_dialed_extension = channelHangup.getVariable_dialed_extension();   // 呼叫的号码
        String variable_dest = channelHangup.getVariable_dest();                           // 使用接口：呼叫的号码
        String channel_HIT_Dialplan = channelHangup.getChannel_hit_dialplan();
        String variable_aleg = channelHangup.getVariable_aleg();
        String zhuz_vas_gateway = channelHangup.getVariable_zhuz_vas_gateway();
        String sip_from_user = channelHangup.getVariable_sip_from_user();
        String sip_to_user = channelHangup.getVariable_sip_to_user();
        String zhuz_orig_dest = channelHangup.getVariable_zhuz_orig_dest();

        // 呼叫类型{1 : 普通呼叫, 2 : 自动群呼, 3 : 语音群发 , 4 : 群呼}
        if (dial_type != null && !dial_type.isEmpty()) {
            channelCdr.setDialtype(Integer.valueOf(dial_type));
        } else {
            channelCdr.setDialtype(1);
        }

//		TODO caller_Caller_ID_Number 这个 参数 可能 为  null, 此时很多参数都是  null, 该通道的CDR 是否需要存储？！该如何处理？！
        if (caller_Caller_ID_Number == null) {
            log.warn("zhuz: ----caller_Caller_ID_Number 这个 参数 可能 为  null, 此时很多参数都是  null===>channelHangup==>" + channelCdr.toString());
        }

        // 判断是否为原始：主叫通道[TODO 这里不适合协商转]
        if (("true".equals(channel_HIT_Dialplan) && caller_Caller_ID_Number.equals(caller_ANI)) ||
                ("false".equals(channel_HIT_Dialplan) && "true".equals(variable_aleg)) || ("true".equals(channel_HIT_Dialplan) && channelCdr.getDialtype() == 4) || (channelCdr.getDialtype() == 2)) {
            channelCdr.setIsCaller(true);
        } else {
            channelCdr.setIsCaller(false);
        }

        // 判断呼叫方向
        boolean isExten = CommonUtils.isExten(caller_ANI);
        if (isExten) {
            zhuz_vas_gateway = StringUtils.isBlank(zhuz_vas_gateway) ? channelHangup.getVariable_init_exten_gateway() : zhuz_vas_gateway;

            String channel_Presence_ID = channelHangup.getChannel_presence_id();
            String currentExtenName = caller_Caller_ID_Number;
            String tmpExtenName = "";
            if (StringUtils.isNotBlank(channel_Presence_ID) && channel_Presence_ID.contains("@")) {
                currentExtenName = channel_Presence_ID.substring(0, channel_Presence_ID.indexOf("@"));
                tmpExtenName = currentExtenName;
            }

            if (!CommonUtils.isExten(sip_to_user) && !CommonUtils.isExten(tmpExtenName)) {    // 处理复杂情况下，将被叫误记为主叫的问题
                channelCdr.setIsCaller(false);
            }

            String callerNumber = caller_Caller_ID_Number;
            if (StringUtils.isNotBlank(zhuz_orig_dest)) {
                if (StringUtils.isNotBlank(currentExtenName) && !zhuz_orig_dest.equals(currentExtenName)) {
                    callerNumber = currentExtenName;
                } else if (StringUtils.isNotBlank(caller_Caller_ID_Number) && !zhuz_orig_dest.equals(caller_Caller_ID_Number)) {
                    callerNumber = caller_Caller_ID_Number;
                } else if (StringUtils.isNotBlank(caller_Callee_ID_Number) && !zhuz_orig_dest.equals(caller_Callee_ID_Number)) {
                    callerNumber = caller_Callee_ID_Number;
                }

                caller_Caller_ID_Number = callerNumber;
                caller_Callee_ID_Number = zhuz_orig_dest;
            }

            if (StringUtils.isNotBlank(zhuz_orig_dest) && CommonUtils.isExten(zhuz_orig_dest)) {
                channelCdr.setDirectiontype(3);    // 内部呼叫

            } else if (StringUtils.isNotBlank(zhuz_orig_dest) && !CommonUtils.isExten(zhuz_orig_dest)) {
                channelCdr.setDirectiontype(1);    // 呼出
                //---------------- 如果上面的不满足，则按常规路线处理------------------//
            } else if (StringUtils.isBlank(caller_Callee_ID_Number) && CommonUtils.isExten(caller_Caller_ID_Number) && CommonUtils.isExten(caller_Destination_Number)) {
                channelCdr.setDirectiontype(3);    // 内部呼叫
            } else if (CommonUtils.isExten(caller_Caller_ID_Number) && CommonUtils.isExten(caller_Callee_ID_Number) && CommonUtils.isExten(caller_Destination_Number)) {
                channelCdr.setDirectiontype(3);    // 内部呼叫
            } else {
                channelCdr.setDirectiontype(1);    // 呼出
            }
        } else {
            channelCdr.setDirectiontype(2);        // 呼入
            // 修改成实际的主被叫信息
            if (!caller_ANI.equals(caller_Caller_ID_Number)) {
                caller_Caller_ID_Number = caller_ANI;
                caller_Caller_ID_Name = caller_ANI;
            }
            if (StringUtils.isNotBlank(sip_to_user)) {
                caller_Callee_ID_Number = sip_to_user;
                caller_Callee_ID_Name = sip_to_user;
            }
            zhuz_vas_gateway = StringUtils.isBlank(zhuz_vas_gateway) ? channelCdr.getVariable_incoming_gateway_number() : zhuz_vas_gateway;
        }


        if (StringUtils.isBlank(zhuz_vas_gateway)) {    // 到这基本上不会发生
            if (!StringUtils.isBlank(channelHangup.getVariable_init_exten_gateway())) {
                zhuz_vas_gateway = channelHangup.getVariable_init_exten_gateway();
            } else if (!StringUtils.isBlank(channelHangup.getVariable_default_gateway())) {
                zhuz_vas_gateway = channelHangup.getVariable_default_gateway();
            } else if (!StringUtils.isBlank(channelHangup.getVariable_incoming_gateway_number())) {
                zhuz_vas_gateway = channelHangup.getVariable_incoming_gateway_number();
            }
        }

        //--------普通字段赋值
        channelCdr.setVariable_zhuz_same_call_uuid(channelHangup.getVariable_zhuz_same_call_uuid());
        channelCdr.setVariable_zhuz_vas_gateway(zhuz_vas_gateway);
        if ("true".equals(channelHangup.getChannel_hit_dialplan())) {
            channelCdr.setChannel_hit_dialplan(true);
        } else {
            channelCdr.setChannel_hit_dialplan(false);
        }
        channelCdr.setCaller_ani(caller_ANI);
        channelCdr.setVariable_caller_id(channelHangup.getVariable_caller_id());
        channelCdr.setCaller_username(channelHangup.getCaller_username());
        channelCdr.setCaller_caller_id_name(caller_Caller_ID_Name);
        channelCdr.setCaller_caller_id_number(caller_Caller_ID_Number);
        channelCdr.setCaller_callee_id_name(caller_Callee_ID_Name);
        channelCdr.setCaller_callee_id_number(caller_Callee_ID_Number);
        if (channelCdr.getDirectiontype() == 3) {    // 内部呼叫，只支持被叫转接，所以主叫不会变化，被叫即当前通道对应分机
            channelCdr.setCaller_caller_id_name(caller_ANI);
            channelCdr.setCaller_caller_id_number(caller_ANI);
            channelCdr.setCaller_callee_id_name(sip_to_user);
            channelCdr.setCaller_callee_id_number(sip_to_user);
        }
        channelCdr.setVariable_sip_from_user(sip_from_user);
        channelCdr.setVariable_sip_to_user(sip_to_user);
        channelCdr.setVariable_sip_contact_user(channelHangup.getVariable_sip_contact_user());
        channelCdr.setVariable_dialed_user(channelHangup.getVariable_dialed_user());
        channelCdr.setVariable_digits_dialed(channelHangup.getVariable_digits_dialed());
        channelCdr.setVariable_dtmf_type(channelHangup.getVariable_dtmf_type());
        channelCdr.setVariable_dest(channelHangup.getVariable_dest());
        channelCdr.setCaller_rdnis(channelHangup.getCaller_rdnis());
        channelCdr.setCaller_destination_number(channelHangup.getCaller_destination_number());
        channelCdr.setVariable_dialed_extension(channelHangup.getVariable_dialed_extension());
        channelCdr.setCaller_orig_caller_id_name(channelHangup.getCaller_orig_caller_id_name());
        channelCdr.setCaller_orig_caller_id_number(channelHangup.getCaller_orig_caller_id_number());
        if ("true".equals(channelHangup.getVariable_is_exten())) {
            channelCdr.setVariable_is_exten(true);
        } else {
            channelCdr.setVariable_is_exten(false);
        }
        if ("true".equals(channelHangup.getVariable_is_originate())) {
            channelCdr.setVariable_is_originate(true);
        } else {
            channelCdr.setVariable_is_originate(false);
        }
        if ("true".equals(channelHangup.getVariable_aleg())) {
            channelCdr.setVariable_aleg(true);
        } else {
            channelCdr.setVariable_aleg(false);
        }

        channelCdr.setVariable_consumer_id(channelHangup.getVariable_consumer_id());
        channelCdr.setVariable_default_gateway(channelHangup.getVariable_default_gateway());
        channelCdr.setVariable_incoming_gateway_number(channelHangup.getVariable_incoming_gateway_number());
        channelCdr.setVariable_init_exten_gateway(channelHangup.getVariable_init_exten_gateway());
        channelCdr.setVariable_ext_field(channelHangup.getVariable_ext_field());
        channelCdr.setCaller_context(channelHangup.getCaller_context());
        channelCdr.setCaller_direction(channelHangup.getCaller_direction());

        channelCdr.setCaller_channel_created_time(parseStrToDate(channelHangup.getCaller_channel_created_time(), "usec"));
        channelCdr.setCaller_profile_created_time(parseStrToDate(channelHangup.getCaller_profile_created_time(), "usec"));
        channelCdr.setCaller_channel_answered_time(parseStrToDate(channelHangup.getCaller_channel_answered_time(), "usec"));
        channelCdr.setCaller_channel_bridged_time(parseStrToDate(channelHangup.getCaller_channel_bridged_time(), "usec"));
        channelCdr.setCaller_channel_hangup_time(parseStrToDate(channelHangup.getCaller_channel_hangup_time(), "usec"));
        channelCdr.setEvent_date_local(parseStrToDate(channelHangup.getEvent_date_local(), "date_str"));
        channelCdr.setVariable_progress_media_stamp(parseStrToDate(channelHangup.getVariable_progress_media_stamp(), "date_str"));
        channelCdr.setVariable_progress_stamp(parseStrToDate(channelHangup.getVariable_progress_stamp(), "date_str"));

        channelCdr.setVariable_record_filename(channelHangup.getVariable_record_filename());
        channelCdr.setVariable_record_seconds(parseStrToInteger(channelHangup.getVariable_record_seconds()));
        channelCdr.setVariable_waitsec(parseStrToInteger(channelHangup.getVariable_waitsec()));
        channelCdr.setVariable_answersec(parseStrToInteger(channelHangup.getVariable_answersec()));
        channelCdr.setVariable_playback_seconds(parseStrToInteger(channelHangup.getVariable_playback_seconds()));
        channelCdr.setVariable_hold_accum_seconds(parseStrToInteger(channelHangup.getVariable_hold_accum_seconds()));
        channelCdr.setVariable_progress_mediasec(parseStrToInteger(channelHangup.getVariable_progress_mediasec()));
        channelCdr.setVariable_progresssec(parseStrToInteger(channelHangup.getVariable_progresssec()));
        channelCdr.setVariable_billsec(parseStrToInteger(channelHangup.getVariable_billsec()));
        channelCdr.setVariable_flow_billsec(parseStrToInteger(channelHangup.getVariable_flow_billsec()));
        channelCdr.setVariable_duration(parseStrToInteger(channelHangup.getVariable_duration()));

        channelCdr.setChannel_name(channelHangup.getChannel_name());
        channelCdr.setVariable_bridge_channel(channelHangup.getVariable_bridge_channel());
        channelCdr.setOther_leg_channel_name(channelHangup.getOther_leg_channel_name());
        channelCdr.setOther_leg_unique_id(channelHangup.getOther_leg_unique_id());
        channelCdr.setOther_leg_orig_caller_id_name(channelHangup.getOther_leg_orig_caller_id_name());
        channelCdr.setOther_leg_orig_caller_id_number(channelHangup.getOther_leg_orig_caller_id_number());
        channelCdr.setVariable_last_bridge_to(channelHangup.getVariable_last_bridge_to());

        channelCdr.setChannel_call_uuid(channelHangup.getChannel_call_uuid());
        channelCdr.setUnique_id(channelHangup.getUnique_id());
        channelCdr.setChannel_presence_id(channelHangup.getChannel_presence_id());
        channelCdr.setVariable_a_uuid(channelHangup.getVariable_a_uuid());
        channelCdr.setVariable_bridge_uuid(channelHangup.getVariable_bridge_uuid());
        channelCdr.setVariable_sip_hangup_disposition(channelHangup.getVariable_sip_hangup_disposition());
        channelCdr.setHangup_cause(channelHangup.getHangup_cause());
        channelCdr.setVariable_last_bridge_hangup_cause(channelHangup.getVariable_last_bridge_hangup_cause());

        channelCdr.setVariable_cc_agent_uuid(channelHangup.getVariable_cc_agent_uuid());
        channelCdr.setVariable_cc_member_uuid(channelHangup.getVariable_cc_member_uuid());
        channelCdr.setVariable_callcenter_string(channelHangup.getVariable_callcenter_string());
        channelCdr.setVariable_cc_agent(channelHangup.getVariable_cc_agent());
        channelCdr.setVariable_cc_queue(channelHangup.getVariable_cc_queue());
        channelCdr.setVariable_cc_side(channelHangup.getVariable_cc_side());
        channelCdr.setVariable_cc_queue_answered_epoch(parseStrToDate(channelHangup.getVariable_cc_queue_answered_epoch(), "sec"));
        channelCdr.setVariable_cc_queue_joined_epoch(parseStrToDate(channelHangup.getVariable_cc_queue_joined_epoch(), "sec"));

        channelCdr.setVariable_last_app(channelHangup.getVariable_last_app());
        channelCdr.setVariable_last_arg(channelHangup.getVariable_last_arg());
        channelCdr.setChannel_read_codec_name(channelHangup.getChannel_read_codec_name());
        channelCdr.setChannel_write_codec_name(channelHangup.getChannel_write_codec_name());
        channelCdr.setVariable_presence_id(channelHangup.getVariable_presence_id());
        channelCdr.setVariable_sip_user_agent(channelHangup.getVariable_sip_user_agent());
        channelCdr.setVariable_remote_media_ip(channelHangup.getVariable_remote_media_ip());
        channelCdr.setVariable_remote_media_port(channelHangup.getVariable_remote_media_port());
        channelCdr.setVariable_sip_network_ip(channelHangup.getVariable_sip_network_ip());
        channelCdr.setVariable_sip_network_port(channelHangup.getVariable_sip_network_port());

        //log.info("zhuz: channelCdr" + channelCdr);
        channelCdrService.create(channelCdr);
        log.info("zhuz: --insert channelCdr id is:" + channelCdr.getId());
    }

    @Override
    public void saveZhuzCdr(String zhuz_same_call_uuid) {
        List<ChannelCdr> channelCdrLs = channelCdrService.findListByZhuzSameCallUuid(zhuz_same_call_uuid);

        int size = channelCdrLs.size();
        if (size == 2) { // 只有两个通道
            ChannelCdr callerCdr = pickCallerFullCdr(channelCdrLs);
            ChannelCdr calleeCdr = null;
            for (ChannelCdr fc : channelCdrLs) {
                if (!fc.getUnique_id().equals(callerCdr.getUnique_id())) {
                    calleeCdr = fc;
                    break;
                }
            }
            ZhuzCdr zhuzCdr = handleTwoChannelConditionCommon(callerCdr, calleeCdr, true);
            if (callerCdr.getVariable_record_seconds() > 0) {
                zhuzCdr.setRecordsec(callerCdr.getVariable_record_seconds());
            } else {
                zhuzCdr.setRecordsec(calleeCdr.getVariable_record_seconds());
            }
            zhuzCdrService.create(zhuzCdr);
            log.info("zhuz: Two channels Insert zhuzCdr--uuid:" + zhuzCdr.getUniqueidzhuz() + " CallerNumber:" + zhuzCdr.getCallerNumber() + " CalleeNumber:" + zhuzCdr.getCalleeNumber());
            /*if (zhuzCdr.getDialtype() == 5) {*/
            //话单推送
            pushCdr(zhuzCdr);
            /*}*/
        } else if (size == 1) { // 语音群发、接口发起呼叫但坐席未摘机等情况
            ChannelCdr channelCdr = channelCdrLs.get(0);
            handleOneChannelCondition(channelCdr);

        } else if (size > 2) { // 发生了转接【区分呼出转接，还是呼入转接】
            ChannelCdr origCallerCdr = pickCallerFullCdr(channelCdrLs);

            if (origCallerCdr.getDirectiontype() == 2 || origCallerCdr.getDirectiontype() == 3) { // 如果是呼入，或者是内部呼叫，只能被叫转接
                for (int i = (size - 1); i >= 0; i--) {
                    ChannelCdr fc = channelCdrLs.get(i);
                    if (!fc.getUnique_id().equals(origCallerCdr.getUnique_id())) {
                        boolean isfirstCallee = (i == size - 2) ? true : false;
                        ZhuzCdr zhuzCdr = handleTwoChannelConditionCommon(origCallerCdr, fc, isfirstCallee);
                        zhuzCdrService.create(zhuzCdr);
                        log.info("zhuz: greater Two channels Insert zhuzCdr==" + zhuzCdr);
                        //话单推送
                        pushCdr(zhuzCdr);
                    }
                }
            } else if (origCallerCdr.getDirectiontype() == 1) {
                ChannelCdr secondCallerCdr = channelCdrLs.get(size - 2); // 按降序排列的
                // order by
                // caller_channel_created_time
                // desc

                for (int i = (size - 1); i >= 0; i--) {
                    ChannelCdr fcTmp = channelCdrLs.get(i);
                    ChannelCdr calleeCdr = secondCallerCdr;

                    if (fcTmp.getUnique_id().equals(origCallerCdr.getUnique_id())) { // 记录原始主叫发起的呼叫信息
                        ChannelCdr callerCdr = origCallerCdr;

                        ZhuzCdr zhuzCdr = handleTwoChannelConditionOutgoing(callerCdr, calleeCdr);

                        zhuzCdr.setCallerNumber(callerCdr.getCaller_caller_id_number());
                        zhuzCdr.setCalleeNumber(callerCdr.getCaller_callee_id_number());
                        zhuzCdr.setDestinationNumber(callerCdr.getCaller_destination_number());
                        zhuzCdr.setDialedextension(callerCdr.getVariable_dialed_extension());
                        zhuzCdr.setVasgateway(callerCdr.getVariable_zhuz_vas_gateway());
                        zhuzCdr.setQueueansweredtime(callerCdr.getVariable_cc_queue_answered_epoch());
                        zhuzCdr.setQueuejoinedtime(callerCdr.getVariable_cc_queue_joined_epoch());

                        long routeSec = (callerCdr.getCaller_channel_created_time().getTime() - calleeCdr.getCaller_channel_created_time().getTime() + 500) / 1000;
                        if (routeSec < 0) {
                            routeSec = Math.abs(routeSec);
                        }
                        zhuzCdr.setRoutesec(Integer.parseInt(routeSec + ""));

                        zhuzCdr.setRecordsec(calleeCdr.getVariable_record_seconds()); // 去被叫通道的录音时长
                        zhuzCdr.setRecordFilename(callerCdr.getVariable_record_filename()); // 取主叫通道的录音文件名称

                        zhuzCdr.setHangupCause("caller_" + callerCdr.getHangup_cause());
                        zhuzCdr.setHangupdisposition("caller_" + callerCdr.getVariable_sip_hangup_disposition());

                        zhuzCdrService.create(zhuzCdr);
                        log.info("zhuz: greater Two channels Insert ZhuzCdr=uuid:" + zhuzCdr.getUniqueidzhuz() + " CallerNumber:" + zhuzCdr.getCallerNumber() + " CalleeNumber:" + zhuzCdr.getCalleeNumber());
                        //话单推送
                        pushCdr(zhuzCdr);
                        // TODO
                        // 暂时去掉h2ZhuzCdrService.saveFindCdr(zhuzCdr);//保存到内存数据库
                    } else if (!fcTmp.getUnique_id().equals(secondCallerCdr.getUnique_id())) {
                        ChannelCdr callerCdr = fcTmp;

                        ZhuzCdr zhuzCdr = handleTwoChannelConditionOutgoing(callerCdr, calleeCdr);

                        zhuzCdr.setCallerNumber(callerCdr.getCaller_caller_id_number());
                        zhuzCdr.setCalleeNumber(origCallerCdr.getCaller_callee_id_number());
                        zhuzCdr.setDestinationNumber(origCallerCdr.getCaller_destination_number());
                        zhuzCdr.setDialedextension(origCallerCdr.getVariable_dialed_extension());
                        zhuzCdr.setVasgateway(origCallerCdr.getVariable_zhuz_vas_gateway());
                        zhuzCdr.setQueueansweredtime(calleeCdr.getVariable_cc_queue_answered_epoch());
                        zhuzCdr.setQueuejoinedtime(calleeCdr.getVariable_cc_queue_joined_epoch());

                        long routeSec = 0;
                        if (i == (size - 3)) {
                            routeSec = (origCallerCdr.getCaller_channel_hangup_time().getTime() - callerCdr.getCaller_channel_created_time().getTime() + 500) / 1000;
                        } else if (i < (size - 3)) {
                            routeSec = (channelCdrLs.get(i + 1).getCaller_channel_hangup_time().getTime() - callerCdr.getCaller_channel_created_time().getTime() + 500) / 1000;
                        }
                        if (routeSec < 0) {
                            routeSec = Math.abs(routeSec);
                        }
                        zhuzCdr.setRoutesec(Integer.parseInt(routeSec + ""));

                        zhuzCdr.setRecordsec(callerCdr.getVariable_record_seconds());
                        zhuzCdr.setRecordFilename(callerCdr.getVariable_record_filename());

                        if (callerCdr.getVariable_sip_hangup_disposition().startsWith("recv")) { // 测试发现
                            // FreeSWITCH
                            // 通道上的挂断原因是反的
                            zhuzCdr.setHangupCause("caller_" + calleeCdr.getHangup_cause());
                            zhuzCdr.setHangupdisposition("caller_" + calleeCdr.getVariable_sip_hangup_disposition());
                        } else {
                            zhuzCdr.setHangupCause("callee_" + callerCdr.getHangup_cause());
                            zhuzCdr.setHangupdisposition("callee_" + callerCdr.getVariable_sip_hangup_disposition());
                        }

                        zhuzCdrService.create(zhuzCdr);
                        log.info("zhuz: greater Two channels Insert ZhuzCdr======" + zhuzCdr);
                        //话单推送
                        pushCdr(zhuzCdr);
                    }
                }
            }
        }

    }

    /**
     * 根据主被叫通道创建 最终合成的CDR
     *
     * @param callerCdr 主叫ChannelCdr
     * @param calleeCdr 被叫叫ChannelCdr
     */
    private ZhuzCdr handleTwoChannelConditionCommon(ChannelCdr callerCdr, ChannelCdr calleeCdr, boolean isfirstCallee) {
        ZhuzCdr zhuzCdr = new ZhuzCdr();

        zhuzCdr.setId(UUIDUtils.findUUID());
        zhuzCdr.setDirectiontype(callerCdr.getDirectiontype());
        zhuzCdr.setDialtype(callerCdr.getDialtype());
        zhuzCdr.setIsOriginate(callerCdr.getVariable_is_originate());
        zhuzCdr.setIsCaller(callerCdr.getIsCaller());
        zhuzCdr.setZhuzSameCallUuid(callerCdr.getVariable_zhuz_same_call_uuid());
        zhuzCdr.setConsumerId(callerCdr.getVariable_consumer_id());
        zhuzCdr.setExtField(callerCdr.getVariable_ext_field());
        zhuzCdr.setCallerani(callerCdr.getCaller_ani());
        zhuzCdr.setCallerid(calleeCdr.getVariable_caller_id());

        zhuzCdr.setCallerNumber(calleeCdr.getCaller_caller_id_number());
        if (StringUtils.isNotBlank(calleeCdr.getCaller_callee_id_number())) {
            zhuzCdr.setCalleeNumber(calleeCdr.getCaller_callee_id_number());
        } else { // 这中情况，会发生在话机上直接呼叫自己，如 801 呼 801
            zhuzCdr.setCalleeNumber(callerCdr.getCaller_callee_id_number());
        }

        zhuzCdr.setDestinationNumber(callerCdr.getCaller_destination_number());
        zhuzCdr.setDialedextension(callerCdr.getVariable_dialed_extension());
        zhuzCdr.setOrigcallernumer(calleeCdr.getCaller_orig_caller_id_number());

        if (callerCdr.getDirectiontype() == 1) {
            zhuzCdr.setDigitsdialed(calleeCdr.getVariable_digits_dialed());
        } else if (callerCdr.getDirectiontype() == 2) {
            zhuzCdr.setDigitsdialed(calleeCdr.getVariable_digits_dialed());
        } else if (callerCdr.getDirectiontype() == 3) {
            if (!"none".equals(calleeCdr.getVariable_digits_dialed())) {
                zhuzCdr.setDigitsdialed(calleeCdr.getVariable_digits_dialed());
            } else {
                zhuzCdr.setDigitsdialed(callerCdr.getVariable_digits_dialed());
            }
        }
        zhuzCdr.setVasgateway(callerCdr.getVariable_zhuz_vas_gateway());

        zhuzCdr.setCreateTime(calleeCdr.getCaller_channel_created_time());
        zhuzCdr.setAnswerTime(calleeCdr.getCaller_channel_answered_time());
        zhuzCdr.setBridgeTime(calleeCdr.getCaller_channel_bridged_time());
        zhuzCdr.setHangupTime(calleeCdr.getCaller_channel_hangup_time());
        zhuzCdr.setEventtime(calleeCdr.getEvent_date_local());
        zhuzCdr.setQueueansweredtime(callerCdr.getVariable_cc_queue_answered_epoch());
        zhuzCdr.setQueuejoinedtime(callerCdr.getVariable_cc_queue_joined_epoch());
        zhuzCdr.setOtherlegcreatetime(callerCdr.getCaller_channel_created_time());

        // ---------统计时长 Start
        zhuzCdr.setFullDuration(calleeCdr.getVariable_duration());

        if (isfirstCallee) { // 如果是第一个被叫，需要计算拨号方案的路由时长
            long routeSec = (callerCdr.getCaller_channel_created_time().getTime() - calleeCdr.getCaller_channel_created_time().getTime() + 500) / 1000;
            if (routeSec < 0) {
                routeSec = Math.abs(routeSec);
            }
            zhuzCdr.setRoutesec(Integer.parseInt(routeSec + ""));
        } else {
            zhuzCdr.setRoutesec(0);
        }

        long ringingSec = 0;
        if (calleeCdr.getCaller_channel_bridged_time() != null) {
            ringingSec = (calleeCdr.getCaller_channel_bridged_time().getTime() - calleeCdr.getCaller_channel_created_time().getTime() + 500) / 1000;
        } else {
            ringingSec = (calleeCdr.getCaller_channel_hangup_time().getTime() - calleeCdr.getCaller_channel_created_time().getTime() + 500) / 1000;
        }
        if (ringingSec < 0) {
            ringingSec = 0;
        }
        if (ringingSec > callerCdr.getVariable_duration()) {
            ringingSec = callerCdr.getVariable_duration();
        }
        zhuzCdr.setRingingsec(Integer.parseInt(ringingSec + ""));

        /*
         * 注意: 统计计费时长，这里不要使用 calleeCdr.getCaller_channel_bridged_time() 时间，这个
         * bridge 时间是不准确的 originate user/801
         * &bridge(sofia/gateway/88860847042/13816760398) bleg 的 billsec 是正常的 ，
         * 但是 bleg 的 caller_Channel_Bridged_Time 是存在的，而且跟 aleg 相同
         * 加上bridge_early_media=true ，那么 caller_Channel_Bridged_Time就
         * 是真实的双方建立通话的时间了
         *
         * if(calleeCdr.getCaller_channel_bridged_time() != null) { long
         * bridgeSec = (calleeCdr.getCaller_channel_hangup_time().getTime() -
         * calleeCdr.getCaller_channel_answered_time().getTime() + 500) / 1000;
         * if(bridgeSec < 0) { bridgeSec = 0; }
         * zhuzCdr.setBridgeSec(Integer.parseInt(bridgeSec+"")); } else {
         * zhuzCdr.setBridgeSec(0); }
         */
        // 统计计费时长
        zhuzCdr.setBridgeSec(calleeCdr.getVariable_billsec());
        if (calleeCdr.getVariable_billsec() > 0) {
            zhuzCdr.setBridgeStatusType(2);
        }

        if (callerCdr.getDialtype() == 4) {
            zhuzCdr.setCreateTime(callerCdr.getCaller_channel_created_time());
            zhuzCdr.setAnswerTime(callerCdr.getCaller_channel_answered_time());
            if (calleeCdr.getCaller_channel_answered_time() != null) {
                Integer bridgeSec = 0;
                bridgeSec = (int) ((callerCdr.getCaller_channel_hangup_time().getTime() - callerCdr.getCaller_channel_answered_time().getTime() + 500) / 1000);
                zhuzCdr.setBridgeSec(bridgeSec);
            }
        }
        zhuzCdr.setHoldaccumsec(callerCdr.getVariable_hold_accum_seconds());
        if (isfirstCallee) {
            zhuzCdr.setRecordsec(callerCdr.getVariable_record_seconds());
        } else {
            zhuzCdr.setRecordsec(calleeCdr.getVariable_record_seconds());
        }
        zhuzCdr.setRecordFilename(calleeCdr.getVariable_record_filename());
        // ---------统计时长 End

        zhuzCdr.setSamecalluuid(calleeCdr.getVariable_zhuz_same_call_uuid());
        zhuzCdr.setUniqueidzhuz(calleeCdr.getUnique_id());
        zhuzCdr.setCallerUniquId(callerCdr.getUnique_id());
        zhuzCdr.setCalleeUniquId(calleeCdr.getUnique_id());
        zhuzCdr.setCallerchannelname(callerCdr.getChannel_name());
        zhuzCdr.setCalleechannelname(calleeCdr.getChannel_name());
        if (callerCdr.getVariable_sip_hangup_disposition().startsWith("recv")) { // 测试发现
            // FreeSWITCH
            // 通道上的挂断原因是反的
            zhuzCdr.setHangupCause("caller_" + calleeCdr.getHangup_cause());
            zhuzCdr.setHangupdisposition("caller_" + calleeCdr.getVariable_sip_hangup_disposition());
        } else {
            zhuzCdr.setHangupCause("callee_" + callerCdr.getHangup_cause());
            zhuzCdr.setHangupdisposition("callee_" + callerCdr.getVariable_sip_hangup_disposition());
        }

        zhuzCdr.setCcagent(calleeCdr.getVariable_cc_agent());
        zhuzCdr.setCcqueue(calleeCdr.getVariable_cc_queue());
        zhuzCdr.setCcside(calleeCdr.getVariable_cc_side());

        zhuzCdr.setCallercontext(callerCdr.getCaller_context());
        zhuzCdr.setLastapp(callerCdr.getVariable_last_app());
        zhuzCdr.setLastarg(callerCdr.getVariable_last_arg());
        zhuzCdr.setCallerreadcodec(callerCdr.getChannel_read_codec_name());
        zhuzCdr.setCalleereadcodec(calleeCdr.getChannel_read_codec_name());
        zhuzCdr.setCallerwritecodec(callerCdr.getChannel_write_codec_name());
        zhuzCdr.setCalleewritecodec(calleeCdr.getChannel_write_codec_name());
        zhuzCdr.setCallerdtmftype(callerCdr.getVariable_dtmf_type());
        zhuzCdr.setCalleedtmftype(calleeCdr.getVariable_dtmf_type());
        zhuzCdr.setCallersipuseragent(callerCdr.getVariable_sip_user_agent());
        zhuzCdr.setCalleesipuseragent(calleeCdr.getVariable_sip_user_agent());
        zhuzCdr.setCallersipnetworkip(callerCdr.getVariable_sip_network_ip());
        zhuzCdr.setCallersipnetworkport(callerCdr.getVariable_sip_network_port());
        zhuzCdr.setCalleesipnetworkip(calleeCdr.getVariable_sip_network_ip());
        zhuzCdr.setCalleesipnetworkport(calleeCdr.getVariable_sip_network_port());
        zhuzCdr.setCallerremotemediaip(callerCdr.getVariable_remote_media_ip());
        zhuzCdr.setCallerremotemediaport(callerCdr.getVariable_remote_media_port());
        zhuzCdr.setCalleeremotemediaip(calleeCdr.getVariable_remote_media_ip());
        zhuzCdr.setCalleeremotemediaport(calleeCdr.getVariable_remote_media_port());
        // 处理双向回拨话单
        if (callerCdr.getDialtype() == 2) {
            zhuzCdr.setCallerNumber(callerCdr.getCaller_callee_id_number());
            zhuzCdr.setCalleeNumber(calleeCdr.getCaller_callee_id_number());
            zhuzCdr.setDialedextension(calleeCdr.getCaller_callee_id_number());
        }

        // 群呼主被叫号码问题
        if (callerCdr.getDialtype() == 4) {
            zhuzCdr.setCallerNumber(callerCdr.getCaller_callee_id_number());
            zhuzCdr.setCalleeNumber(callerCdr.getVariable_zhuz_vas_gateway());
            if (calleeCdr.getCaller_channel_answered_time() != null && !"".equals(calleeCdr.getCaller_channel_answered_time())) {
                zhuzCdr.setDialedextension(calleeCdr.getCaller_callee_id_number());
            }
        } else if (callerCdr.getDialtype() == 5) {
            zhuzCdr.setCallerNumber(callerCdr.getCaller_caller_id_number());
            zhuzCdr.setBridgeSec(callerCdr.getVariable_billsec());
            zhuzCdr.setBridgeStatusType(2);
        }
        return zhuzCdr;
    }

    /**
     * 处理只有一个通道的情况
     *
     * @param callerCdr 主叫通道的完整信息
     */
    private void handleOneChannelCondition(ChannelCdr callerCdr) {
        ZhuzCdr zhuzCdr = new ZhuzCdr();

        zhuzCdr.setId(UUIDUtils.findUUID());
        zhuzCdr.setDirectiontype(callerCdr.getDirectiontype());
        zhuzCdr.setDialtype(callerCdr.getDialtype());
        zhuzCdr.setIsOriginate(callerCdr.getVariable_is_originate());
        zhuzCdr.setIsCaller(callerCdr.getIsCaller());
        zhuzCdr.setZhuzSameCallUuid(callerCdr.getVariable_zhuz_same_call_uuid());
        zhuzCdr.setConsumerId(callerCdr.getVariable_consumer_id());
        zhuzCdr.setExtField(callerCdr.getVariable_ext_field());

        zhuzCdr.setCallerani(callerCdr.getCaller_ani());
        zhuzCdr.setCallerid(callerCdr.getVariable_caller_id());
        zhuzCdr.setCallerNumber(callerCdr.getCaller_caller_id_number());

        if (StringUtils.isBlank(callerCdr.getCaller_callee_id_number())) {
            if (callerCdr.getDirectiontype() == 2) {
                zhuzCdr.setCalleeNumber(callerCdr.getVariable_zhuz_vas_gateway());
            } else {
                zhuzCdr.setCalleeNumber(callerCdr.getCaller_destination_number());
            }
        } else {
            zhuzCdr.setCalleeNumber(callerCdr.getCaller_callee_id_number());
        }

        zhuzCdr.setDestinationNumber(callerCdr.getCaller_destination_number());
        zhuzCdr.setDialedextension(callerCdr.getVariable_dialed_extension());
        zhuzCdr.setOrigcallernumer(callerCdr.getCaller_orig_caller_id_number());
        zhuzCdr.setDigitsdialed(callerCdr.getVariable_digits_dialed());
        zhuzCdr.setVasgateway(callerCdr.getVariable_zhuz_vas_gateway());

        zhuzCdr.setCreateTime(callerCdr.getCaller_channel_created_time());
        zhuzCdr.setAnswerTime(callerCdr.getCaller_channel_answered_time());
        zhuzCdr.setBridgeTime(callerCdr.getCaller_channel_bridged_time());
        zhuzCdr.setHangupTime(callerCdr.getCaller_channel_hangup_time());
        zhuzCdr.setEventtime(callerCdr.getEvent_date_local());
        zhuzCdr.setQueueansweredtime(callerCdr.getVariable_cc_queue_answered_epoch());
        zhuzCdr.setQueuejoinedtime(callerCdr.getVariable_cc_queue_joined_epoch());

        // ---------统计时长 Start
        zhuzCdr.setFullDuration(callerCdr.getVariable_duration());

        if (callerCdr.getDirectiontype() == 2) {
            zhuzCdr.setRoutesec(callerCdr.getVariable_duration());
            zhuzCdr.setRingingsec(0);
        } else {
            zhuzCdr.setRoutesec(0);
            zhuzCdr.setRingingsec(callerCdr.getVariable_duration());
        }

        zhuzCdr.setBridgeSec(0);
        zhuzCdr.setHoldaccumsec(callerCdr.getVariable_hold_accum_seconds());
        zhuzCdr.setRecordsec(callerCdr.getVariable_record_seconds());
        // ---------统计时长 End

        zhuzCdr.setSamecalluuid(callerCdr.getVariable_zhuz_same_call_uuid());
        zhuzCdr.setUniqueidzhuz(callerCdr.getUnique_id());
        zhuzCdr.setCallerUniquId(callerCdr.getUnique_id());
        zhuzCdr.setCalleeUniquId(callerCdr.getUnique_id());
        zhuzCdr.setCallerchannelname(callerCdr.getChannel_name());
        zhuzCdr.setCalleechannelname(callerCdr.getChannel_name());
        zhuzCdr.setHangupCause("caller_" + callerCdr.getHangup_cause());
        zhuzCdr.setHangupdisposition("caller_" + callerCdr.getVariable_sip_hangup_disposition());

        zhuzCdr.setCcagent(callerCdr.getVariable_cc_agent());
        zhuzCdr.setCcqueue(callerCdr.getVariable_cc_queue());
        zhuzCdr.setCcside(callerCdr.getVariable_cc_side());

        zhuzCdr.setRecordFilename(callerCdr.getVariable_record_filename());

        zhuzCdr.setCallercontext(callerCdr.getCaller_context());
        zhuzCdr.setLastapp(callerCdr.getVariable_last_app());
        zhuzCdr.setLastarg(callerCdr.getVariable_last_arg());
        zhuzCdr.setCallerreadcodec(callerCdr.getChannel_read_codec_name());
        zhuzCdr.setCallerwritecodec(callerCdr.getChannel_write_codec_name());
        zhuzCdr.setCallerdtmftype(callerCdr.getVariable_dtmf_type());
        zhuzCdr.setCallersipuseragent(callerCdr.getVariable_sip_user_agent());
        zhuzCdr.setCallersipnetworkip(callerCdr.getVariable_sip_network_ip());
        zhuzCdr.setCallersipnetworkport(callerCdr.getVariable_sip_network_port());
        zhuzCdr.setCallerremotemediaip(callerCdr.getVariable_remote_media_ip());
        zhuzCdr.setCallerremotemediaport(callerCdr.getVariable_remote_media_port());

        // 群呼主被叫设置
        if (zhuzCdr.getDialtype() == 4) {
            zhuzCdr.setCallerNumber(callerCdr.getCaller_callee_id_number());
            zhuzCdr.setCalleeNumber(callerCdr.getVariable_zhuz_vas_gateway());
        }

        // 呼叫机器人话单处理
        else if (zhuzCdr.getDialtype() == 5) {
            if (callerCdr.getCaller_channel_answered_time() == null) {

                // 获取机器人呼叫未接通时运营商返回的提示音 如暂时无人接听、空号、停机、不在服务区、关机
                if (StringUtils.isNotBlank(callerCdr.getVariable_record_filename())) {
                    // zhuzCdr.setRecordFilename(fileName);
                    /*
                     * if ("send_cancel".equals(callerCdr.
                     * getVariable_sip_hangup_disposition())) {
                     * zhuzCdr.setHangupCause("拒接"); } else {
                     */
                    BaseResponse<String> baseResponse = providerService.aliAsr(new AsrMeta(callerCdr.getVariable_record_filename()));
                    if ("0".equals(baseResponse.getCode())) {
                        if (StringUtils.isNotBlank(baseResponse.getData())) {
                            zhuzCdr.setHangupCause(baseResponse.getData());
                        } else {
                            zhuzCdr.setHangupCause("asr未识别");
                        }
                    } else {
                        zhuzCdr.setHangupCause("未找到原因");
                    }
                    // }
                } else {
                    zhuzCdr.setHangupCause("未接通-没有产生录音文件");
                }

                zhuzCdr.setCalleeNumber(callerCdr.getVariable_zhuz_vas_gateway());
            } else {
                Integer bridgeSec = 0;
                bridgeSec = (int) ((callerCdr.getCaller_channel_hangup_time().getTime() - callerCdr.getCaller_channel_answered_time().getTime() + 500) / 1000);
                zhuzCdr.setBridgeSec(bridgeSec);
                zhuzCdr.setBridgeStatusType(2);
                zhuzCdr.setCalleeNumber(callerCdr.getCaller_destination_number());
            }

            //方便呼入小秘书测试
            /*if ("public".equals(callerCdr.getCaller_context()) && callerCdr.getCaller_callee_id_number().startsWith("3941")) {
                zhuzCdr.setCalleeNumber(callerCdr.getCaller_caller_id_number());
                zhuzCdr.setCallerNumber(callerCdr.getCaller_callee_id_number());
                zhuzCdr.setDestinationNumber(callerCdr.getCaller_caller_id_number());
            }*/
        }

        zhuzCdrService.create(zhuzCdr);
        log.info("zhuz: One channel Insert ZhuzCdr--uuid" + zhuzCdr.getUniqueidzhuz() + "");
        /*if (zhuzCdr.getDialtype() == 5) {*/
        //话单推送
        pushCdr(zhuzCdr);
    }

    /**
     * 针对电话呼出后，主叫转接的情况：特殊处理【何谓逻辑主叫：如果是呼出，如果主叫转接后，那么再次接起的分机也算作主叫对象。呼叫的方向是相对于客户而言的
     * 】
     *
     * @param callerCdr 逻辑主叫通道信息
     * @param calleeCdr 逻辑被叫通道信息
     * @return ZhuzCdr 返回赋值后的最终CDR
     */
    private ZhuzCdr handleTwoChannelConditionOutgoing(ChannelCdr callerCdr, ChannelCdr calleeCdr) {
        ZhuzCdr zhuzCdr = new ZhuzCdr();

        zhuzCdr.setId(UUIDUtils.findUUID());
        zhuzCdr.setDirectiontype(callerCdr.getDirectiontype());
        zhuzCdr.setDialtype(callerCdr.getDialtype());
        zhuzCdr.setIsOriginate(callerCdr.getVariable_is_originate());
        zhuzCdr.setIsCaller(callerCdr.getIsCaller());
        zhuzCdr.setZhuzSameCallUuid(callerCdr.getVariable_zhuz_same_call_uuid());
        zhuzCdr.setConsumerId(callerCdr.getVariable_consumer_id());
        zhuzCdr.setExtField(callerCdr.getVariable_ext_field());

        zhuzCdr.setCallerani(callerCdr.getCaller_ani());
        zhuzCdr.setCallerid(callerCdr.getVariable_caller_id());
        zhuzCdr.setOrigcallernumer(callerCdr.getCaller_orig_caller_id_number());

        zhuzCdr.setDigitsdialed(callerCdr.getVariable_digits_dialed());

        zhuzCdr.setCreateTime(callerCdr.getCaller_channel_created_time());
        zhuzCdr.setAnswerTime(callerCdr.getCaller_channel_answered_time());
        zhuzCdr.setBridgeTime(callerCdr.getCaller_channel_bridged_time());
        zhuzCdr.setHangupTime(callerCdr.getCaller_channel_hangup_time());
        zhuzCdr.setEventtime(callerCdr.getEvent_date_local());
        zhuzCdr.setOtherlegcreatetime(calleeCdr.getCaller_channel_created_time());

        // ---------统计时长 Start
        zhuzCdr.setFullDuration(callerCdr.getVariable_duration());

        long ringingSec = 0;
        if (callerCdr.getCaller_channel_answered_time() != null) {
            ringingSec = (callerCdr.getCaller_channel_answered_time().getTime() - callerCdr.getCaller_channel_created_time().getTime() + 500) / 1000;
        } else {
            ringingSec = (callerCdr.getCaller_channel_hangup_time().getTime() - callerCdr.getCaller_channel_created_time().getTime() + 500) / 1000;
        }
        if (ringingSec < 0) {
            ringingSec = 0;
        }
        if (ringingSec > callerCdr.getVariable_duration()) {
            ringingSec = callerCdr.getVariable_duration();
        }
        zhuzCdr.setRingingsec(Integer.parseInt(ringingSec + ""));

        /*
         * if(callerCdr.getCaller_channel_answered_time() != null) { long
         * bridgeSec = (callerCdr.getCaller_channel_hangup_time().getTime() -
         * callerCdr.getCaller_channel_answered_time().getTime() + 500) / 1000;
         * if(bridgeSec < 0) { bridgeSec = 0; }
         * zhuzCdr.setBridgeSec(Integer.parseInt(bridgeSec+"")); } else {
         * zhuzCdr.setBridgeSec(0); }
         */

        // 统计计费时长
        if (calleeCdr.getVariable_billsec() > callerCdr.getVariable_billsec()) { //
            zhuzCdr.setBridgeSec(callerCdr.getVariable_billsec());
        } else {
            zhuzCdr.setBridgeSec(calleeCdr.getVariable_billsec());
        }
        if (zhuzCdr.getBridgeSec() > 0) {
            zhuzCdr.setBridgeStatusType(2);
        }

        zhuzCdr.setHoldaccumsec(calleeCdr.getVariable_hold_accum_seconds());
        // ---------统计时长 End

        zhuzCdr.setSamecalluuid(callerCdr.getVariable_zhuz_same_call_uuid());
        zhuzCdr.setUniqueidzhuz(callerCdr.getUnique_id());
        zhuzCdr.setCallerUniquId(callerCdr.getUnique_id());
        zhuzCdr.setCalleeUniquId(calleeCdr.getUnique_id());
        zhuzCdr.setCallerchannelname(callerCdr.getChannel_name());
        zhuzCdr.setCalleechannelname(calleeCdr.getChannel_name());

        zhuzCdr.setCcagent(callerCdr.getVariable_cc_agent());
        zhuzCdr.setCcqueue(callerCdr.getVariable_cc_queue());
        zhuzCdr.setCcside(callerCdr.getVariable_cc_side());

        zhuzCdr.setCallercontext(callerCdr.getCaller_context());
        zhuzCdr.setLastapp(callerCdr.getVariable_last_app());
        zhuzCdr.setLastarg(callerCdr.getVariable_last_arg());
        zhuzCdr.setCallerreadcodec(callerCdr.getChannel_read_codec_name());
        zhuzCdr.setCalleereadcodec(calleeCdr.getChannel_read_codec_name());
        zhuzCdr.setCallerwritecodec(callerCdr.getChannel_write_codec_name());
        zhuzCdr.setCalleewritecodec(calleeCdr.getChannel_write_codec_name());
        zhuzCdr.setCallerdtmftype(callerCdr.getVariable_dtmf_type());
        zhuzCdr.setCalleedtmftype(calleeCdr.getVariable_dtmf_type());
        zhuzCdr.setCallersipuseragent(callerCdr.getVariable_sip_user_agent());
        zhuzCdr.setCalleesipuseragent(calleeCdr.getVariable_sip_user_agent());
        zhuzCdr.setCallersipnetworkip(callerCdr.getVariable_sip_network_ip());
        zhuzCdr.setCallersipnetworkport(callerCdr.getVariable_sip_network_port());
        zhuzCdr.setCalleesipnetworkip(calleeCdr.getVariable_sip_network_ip());
        zhuzCdr.setCalleesipnetworkport(calleeCdr.getVariable_sip_network_port());
        zhuzCdr.setCallerremotemediaip(callerCdr.getVariable_remote_media_ip());
        zhuzCdr.setCallerremotemediaport(callerCdr.getVariable_remote_media_port());
        zhuzCdr.setCalleeremotemediaip(calleeCdr.getVariable_remote_media_ip());
        zhuzCdr.setCalleeremotemediaport(calleeCdr.getVariable_remote_media_port());

        return zhuzCdr;
    }

    /**
     * 话单推送
     *
     * @param zhuzCdr
     */
    // 推送CDR
    private void pushCdr(ZhuzCdr zhuzCdr) {

        String pushMethod = null;
        String pushUrl = null;
        for (PushManage pushManage : PushManageShared.pushManageMap.values()) {
            pushUrl = pushManage.getPushUrl();
            pushMethod = pushManage.getPushMethod();

            //匹配推送规则 优先级consumerId(客户ID) > gateway(线路) > exten(分机)
            if ("cdr".equals(pushManage.getPushType()) && zhuzCdr.getConsumerId().equals(pushManage.getConsumerId())) {
                break;
            } else if ("cdr".equals(pushManage.getPushType()) && zhuzCdr.getVasgateway().equals(pushManage.getGateway())) {
                break;
            } else if ("cdr".equals(pushManage.getPushType()) && (zhuzCdr.getCallerNumber().equals(pushManage.getExten()) || zhuzCdr.getCalleeNumber().equals(pushManage.getExten()) || zhuzCdr.getDestinationNumber().equals(pushManage.getExten()))) {
                break;
            } else {
                pushUrl = null;
                pushMethod = null;
            }
        }

        //log.info("zhuz: pushMethod:" + pushMethod + "------pushUrl:" + pushUrl);

        if (StringUtils.isBlank(pushMethod) || StringUtils.isBlank(pushUrl)) {
            return;
        }

        // 按配置的格式推送话单
        ZhuzCdrVo zhuzCdrVo = new ZhuzCdrVo();
        zhuzCdrVo.setExtField(zhuzCdr.getExtField());
        zhuzCdrVo.setCallerNumber(zhuzCdr.getCallerNumber());
        zhuzCdrVo.setDestinationNumber(zhuzCdr.getDestinationNumber());
        zhuzCdrVo.setCalleeNumber(zhuzCdr.getCalleeNumber());
        zhuzCdrVo.setIsOriginate(zhuzCdr.getIsOriginate());
        zhuzCdrVo.setZhuzSameCallUuid(zhuzCdr.getZhuzSameCallUuid());
        zhuzCdrVo.setBridgeSec(zhuzCdr.getBridgeSec());
        zhuzCdrVo.setFullDuration(zhuzCdr.getFullDuration());
        zhuzCdrVo.setRecordFilename(zhuzCdr.getRecordFilename());
        zhuzCdrVo.setCreateTime(zhuzCdr.getCreateTime());
        zhuzCdrVo.setAnswerTime(zhuzCdr.getAnswerTime());
        zhuzCdrVo.setHangupTime(zhuzCdr.getHangupTime());
        zhuzCdrVo.setBridgeTime(zhuzCdr.getBridgeTime());
        zhuzCdrVo.setCallerUniquId(zhuzCdr.getCallerUniquId());
        zhuzCdrVo.setCalleeUniquId(zhuzCdr.getCalleeUniquId());
        zhuzCdrVo.setBridgeStatusType(zhuzCdr.getBridgeStatusType());
        zhuzCdrVo.setConsumerId(zhuzCdr.getConsumerId());
        zhuzCdrVo.setVasgateway(zhuzCdr.getVasgateway());

        if (zhuzCdr.getDialtype() == 5) {
            zhuzCdrVo.setHangupCause(zhuzCdr.getHangupCause());
        }

        try {
            String jsonCdr = GsonUtils.toJson(zhuzCdrVo);
            if ("http".equals(pushMethod)) {
                String tmpParam = "cdr";
                log.info("zhuz: Http push cdr start-----pushUrl:" + pushUrl + "------tmpParam:" + tmpParam + "--------jsonCdr:" + jsonCdr);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
                map.add(tmpParam, jsonCdr);
                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
                String result = restTemplate.postForObject(pushUrl, request, String.class);
                log.info("zhuz: Http push cdr end-----------Result:" + result);
            } else if ("https".equals(pushMethod)) {
            /*try {
                // 推送话单给第三方
                factory.setServiceClass(PushCdr.class);
                factory.setAddress(pushUrl);
                PushCdr pushCdr = (PushCdr) factory.create();
                String result = pushCdr.push(jsonCdr);
                log.info("zhuz: WebService push cdr to url-" + pushUrl + " result-" + result + " json_cdr-" + jsonCdr);
            } catch (Exception e) {
                log.warn("zhuz: WebService push cdr failed, url-" + pushUrl + " json_cdr-" + jsonCdr, e);
            }*/
            } else {
                log.warn("zhuz: Push cdr method " + pushMethod + " not support yet");
            }
        } catch (Exception e) {
            log.error("zhuz:error", e);
        }
    }

    /**
     * 根据字符串获取Date 类型的日期
     *
     * @param param   日期的字符串值
     * @param fmtType 参数格式类型
     * @return Date
     */
    private Date parseStrToDate(String param, String fmtType) {
        try {
            param = StringUtils.trim(param);
            if (StringUtils.isNotBlank(param)) {
                if ("usec".equals(fmtType)) {
                    if ("0".equals(param)) {
                        return null;
                    }
                    return new Date(Long.parseLong(param) / 1000);
                } else if ("sec".equals(fmtType)) {
                    return new Date(Long.parseLong(param) * 1000);
                } else if ("date_str".equals(fmtType)) {
                    SimpleDateFormat sdf_sec = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    // SimpleDateFormat是线程不安全的，切忌切忌！ 不要放到全局变量中
                    return sdf_sec.parse(param);
                }
            }
        } catch (Exception e) {
            log.error("zhuz: --根据字符串获取Date 类型的日期出现异常--" + e.getMessage() + "---param=" + param, e);
        }
        return null;
    }

    /**
     * 根据字符串获取Integer 类型的数值
     *
     * @param param 数值字符串
     * @return Integer
     */
    private Integer parseStrToInteger(String param) {
        try {
            if (!StringUtils.isBlank(param) && param.matches("\\d+")) {
                return Integer.parseInt(param);
            }
        } catch (Exception e) {
            log.error("zhuz: --根据字符串获取Integer 类型的数值出现异常--" + e.getMessage(), e);
        }
        return 0;
    }

    /**
     * 从众通道当中找出主叫通道
     *
     * @param channelCdrLs
     * @return ChannelCdr
     */
    private ChannelCdr pickCallerFullCdr(List<ChannelCdr> channelCdrLs) {
        ChannelCdr tmpFc = channelCdrLs.get(channelCdrLs.size() - 1);
        if (tmpFc.getIsCaller()) {
            return tmpFc;
        } else {
            for (ChannelCdr fc : channelCdrLs) {
                if (fc.getIsCaller()) {
                    return fc;
                }
            }
        }

        return channelCdrLs.get(channelCdrLs.size() - 1);
    }
}
