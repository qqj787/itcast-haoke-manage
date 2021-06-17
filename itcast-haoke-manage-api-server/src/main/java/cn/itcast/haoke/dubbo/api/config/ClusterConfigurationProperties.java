package cn.itcast.haoke.dubbo.api.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
@AllArgsConstructor
@NoArgsConstructor
public class ClusterConfigurationProperties {
    private List<String> nodes;
    private Integer maxRedirects;

}
