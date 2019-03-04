package cn.mrxiexie.gizwits.ws;

import cn.mrxiexie.gizwits.entity.ws.*;

import java.util.List;

/**
 * 机智云websocket回调
 *
 * {@link GizwitsWebsocket#login()}对应回调{@link #onLogin(WsLoginRes)}
 * {@link GizwitsWebsocket#subscribe(List)}对应回调{@link #onSubscribe(WsSubscribeRes)}
 * {@link cn.mrxiexie.gizwits.openapi.GizwitsOpenApi#bindDevice(String, String, String)}对应回调{@link #onBindingChanged(WsBindingChanged)}
 * {@link GizwitsWebsocket#read(String, List)}对应回调{@link #onNoti(WsNoti)}
 * {@link GizwitsWebsocket#ping()}对应回调{@link #onPong()}
 *
 * @author mrxiexie
 * @date 3/2/19 2:29 PM
 */
public interface GizwitsWebsocketMessageHandler {

    /**
     * 机智云websocket登录回调
     *
     * @param wsLoginRes
     */
    default void onLogin(WsLoginRes wsLoginRes) {
    }

    /**
     * 机智云websocket订阅回调
     *
     * @param wsSubscribeRes
     */
    default void onSubscribe(WsSubscribeRes wsSubscribeRes) {
    }

    /**
     * 机智云websocket上下线通知
     *
     * @param wsOnlineStatus
     */
    default void onOnlineStatus(WsOnlineStatus wsOnlineStatus) {
    }

    /**
     * 机智云websocket绑定通知
     *
     * @param wsBindingChanged
     */
    default void onBindingChanged(WsBindingChanged wsBindingChanged) {
    }

    /**
     * 机智云websocket收到标准数据点通知
     *
     * @param wsNoti
     */
    default void onNoti(WsNoti wsNoti) {
    }

    /**
     * 机智云websocket收到心跳通知
     */
    default void onPong() {
    }

    /**
     * 机智云websocket收到非法消息通知
     *
     * @param wsInvalidMsg
     */
    default void onInvalidMsg(WsInvalidMsg wsInvalidMsg) {
    }
}
