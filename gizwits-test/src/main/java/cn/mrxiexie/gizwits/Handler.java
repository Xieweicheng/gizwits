package cn.mrxiexie.gizwits;

import cn.mrxiexie.gizwits.entity.ws.*;
import cn.mrxiexie.gizwits.ws.GizwitsWebsocket;
import cn.mrxiexie.gizwits.ws.GizwitsWebsocketMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mrxiexie
 * @date 3/4/19 12:29 AM
 */
@Slf4j
//@Component
public class Handler implements GizwitsWebsocketMessageHandler {

    @Autowired
    private GizwitsWebsocket gizwitsWebsocket;

    @Override
    public void onLogin(WsLoginRes wsLoginRes) {
        log.info("onLogin");
//        gizwitsWebsocket.read("12");
//        gizwitsWebsocket.subscribe(Collections.singletonList("mill"));
    }

    @Override
    public void onSubscribe(WsSubscribeRes wsSubscribeRes) {
        log.info("onSubscribe");
    }

    @Override
    public void onOnlineStatus(WsOnlineStatus wsOnlineStatus) {
        log.info("onOnlineStatus");
    }

    @Override
    public void onBindingChanged(WsBindingChanged wsBindingChanged) {
        log.info("onBindingChanged");
    }

    @Override
    public void onNoti(WsNoti wsNoti) {
        log.info("onNoti");
    }

    @Override
    public void onPong() {
        log.info("onPong");
    }

    @Override
    public void onInvalidMsg(WsInvalidMsg wsInvalidMsg) {
        log.info("onInvalidMsg");
    }
}
