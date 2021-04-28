package org.acgs.autoconfigure.configuration;

import org.acgs.autoconfigure.bean.Templates;
import org.acgs.autoconfigure.util.TemplateUtil;
import org.acgs.core.token.DoubleJWT;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * acgs cms 配置文件
 *
 * @author John@acgs-org
 * create time 2021/4/26
 */
@Configuration(proxyBeanMethods = false)
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties({AcgsCmsProperties.class, AcgsCmsBuildProperties.class})
public class AcgsCmsConfiguration {

    private final AcgsCmsProperties properties;

    public AcgsCmsConfiguration(AcgsCmsProperties properties) {
        this.properties = properties;
    }

    /**
     * @return jwt bean
     */
    @Bean
    public DoubleJWT jwt() {
        String secret = properties.getTokenSecret();
        Long accessExpire = properties.getTokenAccessExpire();
        Long refreshExpire = properties.getTokenRefreshExpire();
        if (accessExpire == null) {
            // 一个小时
            accessExpire = 60 * 60L;
        }
        if (refreshExpire == null) {
            // 一周
            refreshExpire = 60 * 60 * 24 * 7L;
        }
        return new DoubleJWT(secret, accessExpire, refreshExpire);
    }

    @Bean
    public Templates templates() {
        Map<String, String> template = new HashMap<>();
        template.put("entity", TemplateUtil.getTemplate("entity"));
        return new Templates(template);
    }

}