package cn.mrxiexie.gizwits.ws;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.mrxiexie.gizwits.annotation.GizwitsWebsocketListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 若WebSocketListener存在并且 `gizwits.websocket.enabled` 为true则注入
 *
 * @author mrxiexie
 * @date 2/28/19 10:08 PM
 */
@Slf4j
@ConditionalOnProperty(value = "gizwits.websocket.enabled", havingValue = "true", matchIfMissing = false)
public class GizwitsWebsocketConfiguration {

    @Bean
    public ThreadPoolExecutor gizwitsExecutor() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNamePrefix("Gizwits-Pool-").build();
        return new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), namedThreadFactory);
    }

    @ConditionalOnMissingBean(annotation = GizwitsWebsocketListener.class)
    @Bean
    public DefaultGizwitsWebsocketListener defaultGizwitsWebsocketListener() {
        log.info("没有注入标注【@GizwitsWebsocketListener】注解的【WebSocketListener】，使用默认的【DefaultGizwitsWebsocketListener】");
        return new DefaultGizwitsWebsocketListener();
    }

    @Bean
    public GizwitsWebsocket gizwitsWebsocket() {
        return new GizwitsWebsocket();
    }

    @Bean
    public GizwitsWebsocketListenerAop gizwitsWebsocketListenerAop() {
        return new GizwitsWebsocketListenerAop();
    }

    @Bean
    public GizwitsWebsocketAop gizwitsWebsocketAop() {
        return new GizwitsWebsocketAop();
    }

}
