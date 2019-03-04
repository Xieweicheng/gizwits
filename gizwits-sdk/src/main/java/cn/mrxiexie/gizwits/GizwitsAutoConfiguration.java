package cn.mrxiexie.gizwits;

import cn.mrxiexie.gizwits.openapi.GizwitsOpenApi;
import cn.mrxiexie.gizwits.openapi.GizwitsOpenApiAop;
import cn.mrxiexie.gizwits.properties.GizwitsOpenApiProperties;
import cn.mrxiexie.gizwits.properties.GizwitsWebsocketProperties;
import cn.mrxiexie.gizwits.util.RequestUtil;
import cn.mrxiexie.gizwits.ws.GizwitsWebsocketConfiguration;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 机智云自动配置类
 *
 * @author mrxiexie
 * @date 2/28/19 2:16 PM
 */
@Configuration
@EnableScheduling
@EnableConfigurationProperties({
        GizwitsOpenApiProperties.class,
        GizwitsWebsocketProperties.class
})
@Import({
        GizwitsWebsocketConfiguration.class,
        GizwitsOpenApi.class,
        GizwitsOpenApiAop.class,
        RequestUtil.class
})
@AutoConfigureAfter
public class GizwitsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}
