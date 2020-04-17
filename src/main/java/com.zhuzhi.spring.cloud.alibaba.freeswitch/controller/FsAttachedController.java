package com.zhuzhi.spring.cloud.alibaba.freeswitch.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.utils.StringUtils;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.common.PageResponse;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.FsAttached;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.ReqPageInfo;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.req.fscluster.FsCluterSearch;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.shared.FsClusterShared;
import com.zhuzhi.spring.cloud.alibaba.freeswitch.utils.GsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/fsattached")
@Slf4j
@Api(tags = "FS集群连接状态管理")
public class FsAttachedController {

    @Value("${server.port}")
    private String port;

    private final RestTemplate restTemplate;

    @Qualifier("redisson")
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    public FsAttachedController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/test")
    public String echo() {
        // 使用服务名请求服务提供者
        JSONObject json = restTemplate.getForObject("http://127.0.0.1:" + port + "/actuator/nacos-discovery", JSONObject.class);
        Map<String, Object> obj = (Map<String, Object>) json.get("NacosDiscoveryProperties");
        String ip = (String) obj.get("ip");
        Integer port = (Integer) obj.get("port");
        return ip + ":" + port;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation("ESL连接状态查询")
    public PageResponse<?> ListAttached(@RequestBody FsCluterSearch reqPageInfo) {

        //redis中查出所有esl在线的服务实例信息
        Iterable<String> istr = redissonClient.getKeys().getKeysByPattern(FsClusterShared.FS_ESL_KEY + "*");
        List<FsAttached> listFsAttached = new ArrayList<>();
        for (String str : istr) {
            RBucket<Object> result = redissonClient.getBucket(str);
            FsAttached fsAttached = GsonUtils.fromJson(result.get().toString(), FsAttached.class);
            if (StringUtils.isBlank(reqPageInfo.getName()) || (StringUtils.isNotBlank(reqPageInfo.getName().trim()) && fsAttached.getName().equals(reqPageInfo.getName().trim()))) {
                listFsAttached.add(fsAttached);
            }
            /*log.info("--------------------" + str + "----" + fsAttached.toString());*/
        }

        //将redis中查出的数据添加到分页查询中
        Integer limit = Integer.parseInt(reqPageInfo.getLimit());
        Integer page = Integer.parseInt(reqPageInfo.getPage());
        Integer size = listFsAttached.size();
        if (size > 0) {
            List<FsAttached> resultList = new ArrayList<>();
            Integer totalPages = 0;
            if (limit > 0 && page > 0) {
                if ((size % limit) == 0) {
                    totalPages = size / limit;
                } else {
                    totalPages = size / limit + 1;
                }

                Integer start = 0;
                Integer stop = 0;
                if (totalPages >= page) {
                    start = limit * (page - 1);
                    stop = totalPages == page ? size : (start + limit);

                } else {
                    page = totalPages;
                    start = limit * (page - 1);
                    stop = totalPages == page ? size : (start + limit);
                }

                for (int i = start; i < stop; i++) {
                    resultList.add(listFsAttached.get(i));
                }
                return PageResponse.successPageData(resultList, size, page);
            } else {
                return PageResponse.successPageData(new ArrayList<>(), 0, 1);
            }
        } else {
            return PageResponse.successPageData(new ArrayList<>(), 0, 1);
        }

    }

}
