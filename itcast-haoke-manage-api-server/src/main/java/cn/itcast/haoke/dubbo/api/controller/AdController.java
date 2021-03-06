package cn.itcast.haoke.dubbo.api.controller;

import cn.itcast.haoke.dubbo.api.service.AdService;
import cn.itcast.haoke.dubbo.api.vo.WebResult;
import cn.itcast.haoke.dubbo.server.pojo.Ad;
import cn.itcast.haoke.dubbo.server.vo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("ad")
@RestController
@CrossOrigin
public class AdController {
    @Autowired
    AdService adService;
    @GetMapping
    public WebResult queryIndexAd() {
        PageInfo<Ad> adPageInfo = this.adService.queryAdList(1, 1, 3);
        List<Ad> records = adPageInfo.getRecords();
        List<Object> result = new ArrayList<>();
        for (Ad ad : records) {
            Map<String,Object> map = new HashMap<>();
            map.put("original",ad.getUrl());
            result.add(map);
        }
        return WebResult.ok(result);
    }
}
