package cn.itcast.haoke.dubbo.api.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("wx")
@RestController
public class WeiXinController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @PostMapping("login")
    public Map<String,Object> wxLogin(@RequestParam("code") String code){
        Map<String,Object> result = new HashMap<>();
        result.put("status",200);
        String appId = "wxe4c2b3c8c55e8c44";
        String secret = "9083abd3f5b2dc2e6eaa47ccf6cff77d";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId +
                "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        String jsonData = this.restTemplate.getForObject(url, String.class);
        if (StringUtils.contains(jsonData,"errcode")){
            result.put("status",500);
            result.put("msg","登录失败");
        }
        String md5Key = DigestUtils.md5Hex(jsonData + "HAOKE_WX_LOGIN");
        //生成规则，WX_LOGIN_{MD5}
        String redisKey = "WX_LOGIN_" + md5Key;
        redisTemplate.opsForValue().set(redisKey,jsonData, Duration.ofDays(7));
        result.put("ticket","HAOKE_" + md5Key);
        return result;
    };
}
