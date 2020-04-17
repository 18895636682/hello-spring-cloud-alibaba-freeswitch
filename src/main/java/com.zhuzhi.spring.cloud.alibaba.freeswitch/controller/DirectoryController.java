package com.zhuzhi.spring.cloud.alibaba.freeswitch.controller;

import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.Exten;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.directory.Directory;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.service.ExtenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/conf/")
@Slf4j
@Api(tags = "FS动态文件生成")
public class DirectoryController {

    @Autowired
    private ExtenService extenService;

    @ResponseBody
    @RequestMapping(value = "/directory", produces = {"application/xml; charset=UTF-8"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("分机认证并生成XML文件")
    public String autodial(Directory directory) {
        log.info("zhuz: " + directory);

        if (directory == null || directory.getUser() == null) {
            return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<document type=\"freeswitch/xml\">\n" +
                    "  <section name=\"result\">\n" +
                    "    <result status=\"param not found\" />\n" +
                    "  </section>\n" +
                    "</document>";
        } else {

            Exten exten = extenService.findByExten(new Exten(directory.getUser()));
            if (exten != null && "true".equals(exten.getEnabled())) {

                return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                        "<document type=\"freeswitch/xml\">\n" +
                        "  <section name=\"directory\">\n" +
                        "  <domain name=\"$${domain}\">\n" +
                        "    <params>\n" +
                        "      <param name=\"dial-string\" value=\"{^^:sip_invite_domain=${dialed_domain}:presence_id=${dialed_user}@${dialed_domain}}${sofia_contact(*/${dialed_user}@${dialed_domain})},${verto_contact(${dialed_user}@${dialed_domain})}\"/>\n" +
                        "      <param name=\"jsonrpc-allowed-methods\" value=\"verto\"/>\n" +
                        "    </params>\n" +
                        "    <variables>\n" +
                        "      <variable name=\"record_stereo\" value=\"true\"/>\n" +
                        "      <variable name=\"default_gateway\" value=\"$${default_provider}\"/>\n" +
                        "      <variable name=\"default_areacode\" value=\"$${default_areacode}\"/>\n" +
                        "      <variable name=\"transfer_fallback_extension\" value=\"operator\"/>\n" +
                        "    </variables>\n" +
                        "    <groups>\n" +
                        "      <group name=\"default\">\n" +
                        "        <users>\n" +
                        "          <user id=\"" + exten.getUserId() + "\" cacheable=\"30000\">\n" +
                        /*"          <user id=\"" + exten.getUserId() + "\">\n" +*/
                        "             <params>\n" +
                        "                <param name=\"password\" value=\"" + exten.getPassword() + "\"/>\n" +
                        "                <param name=\"vm-password\" value=\"" + exten.getPassword() + "\"/>\n" +
                        "             </params>\n" +
                        "             <variables>\n" +
                        "                <variable name=\"effective_caller_id_name\" value=\"" + exten.getUserId() + "\"/>\n" +
                        "                <variable name=\"effective_caller_id_number\" value=\"" + exten.getUserId() + "\"/>\n" +
                        "                <variable name=\"outbound_caller_id_name\" value=\"$${outbound_caller_name}\"/>\n" +
                        "                <variable name=\"outbound_caller_id_number\" value=\"$${outbound_caller_id}\"/>\n" +
                        "                <variable name=\"callgroup\" value=\"default\"/>\n" +
                        "                <variable name=\"sip-force-expires\" value=\"300\"/>\n" +
                        "                <variable name=\"is_exten\" value=\"true\"/>\n" +
                        "             </variables>\n" +
                        "          </user>\n" +
                        "        </users>\n" +
                        "      </group>\n" +
                        "    </groups>\n" +
                        "  </domain>\n" +
                        "  </section>\n" +
                        "</document> ";
            } else {
                return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                        "<document type=\"freeswitch/xml\">\n" +
                        "  <section name=\"result\">\n" +
                        "    <result status=\"exten not found\" />\n" +
                        "  </section>\n" +
                        "</document>";
            }
        }
    }
}
