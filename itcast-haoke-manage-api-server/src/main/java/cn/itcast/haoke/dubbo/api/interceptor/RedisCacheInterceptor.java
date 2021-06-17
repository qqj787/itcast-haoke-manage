package cn.itcast.haoke.dubbo.api.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class RedisCacheInterceptor  implements HandlerInterceptor {
    private static ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (StringUtils.equalsIgnoreCase(request.getMethod(),"options"))
        {
            return true;
        }
        if (!StringUtils.equalsIgnoreCase(request.getMethod(),"get")) {
            if (!StringUtils.equalsIgnoreCase(request.getRequestURI(),"/graphql")) {
                return true;
            }
        }
        //通过缓存做命中 查询redis,redisKey ? 组成：md5(请求的url + 请求的参数)
        String redisKey = createRedisKey(request);
        String data = this.redisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isEmpty(data)) {
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset = utf-8");
        // 支持跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.getWriter().write(data);
        return false;
    }

    public static String createRedisKey(HttpServletRequest request) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String paramStr = request.getRequestURI();
        if (parameterMap.isEmpty()) {
            paramStr += IOUtils.toString(request.getInputStream(),"UTF-8");
        }else {
            paramStr += mapper.writeValueAsString(parameterMap);
        }
        String redisKey = "WEB_DATA_" + DigestUtils.md5Hex(paramStr);
        return redisKey;
    }
}
