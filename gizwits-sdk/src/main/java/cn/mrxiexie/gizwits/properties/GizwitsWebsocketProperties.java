package cn.mrxiexie.gizwits.properties;

import cn.mrxiexie.gizwits.entity.ws.WsLoginReq;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mrxiexie
 * @date 2/28/19 2:14 PM
 */
@Data
@ConfigurationProperties(prefix = "gizwits.websocket")
public class GizwitsWebsocketProperties {

    /**
     * 是否启用websocket
     */
    private boolean enabled = true;

    /**
     * websocket链接地址
     */
    private String url = "ws://sandbox.gizwits.com:8080/ws/app/v1";

    /**
     * 数据点类型
     */
    private P0_type p0Type = P0_type.ATTRS_V4;

    /**
     * 心跳间隔，cron表达式，必须少于{@link #heartbeatTimeout}
     * <p>
     * 以下为30秒心跳间隔
     * 0/30 * * * * ?
     */
    private String heartbeatInterval;

    /**
     * 心跳断开时间，单位为秒，必须少于180
     * <p>
     * ps：该值相同于websocket连接时传递给机智云云端的{@link WsLoginReq#getHeartbeat_interval()}
     */
    private Integer heartbeatTimeout = 120;

    /**
     * 自动订阅已经绑定的设备
     */
    private Boolean autoSubscribe = true;

    /**
     * 默认情况下开启自动配置，若不启动，则需要开发者自己接管整个websocket生命周期，可注入GizwitsWebsocketListener获取监听
     * <p>
     * 自动完成登录
     * 心跳发送
     * 断开重连
     * 消息发送失败重试
     */
    private Boolean autoConfig = true;

    public enum P0_type {

        /**
         * ("attrs_v4" 指通过标准数据点协议的方式和云端交互，见下文“标准数据点操作”部分。
         * "custom"表示使用自定义业务逻辑协议的方式与云端交互，见下文“浏览器与云端的数据交互”部分。)
         */
        ATTRS_V4("attrs_v4"),
        CUSTOM("custom");

        private String type;

        P0_type(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
