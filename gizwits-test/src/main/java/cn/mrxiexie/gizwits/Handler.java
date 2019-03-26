package cn.mrxiexie.gizwits;

import cn.mrxiexie.gizwits.entity.ws.*;
import cn.mrxiexie.gizwits.ws.GizwitsWebsocket;
import cn.mrxiexie.gizwits.ws.GizwitsWebsocketMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * @author mrxiexie
 * @date 3/4/19 12:29 AM
 */
@Slf4j
@Component
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
        try {
            JSONObject jsonObject = new JSONObject(wsNoti.getAttrs());
            System.out.println(jsonObject);
            JSONArray business_dot = jsonObject.getJSONArray("business_dot");
            byte[] signByteArray = new byte[business_dot.length()];
            for (int i = 0; i < business_dot.length(); i++) {
                signByteArray[i] = Byte.parseByte(business_dot.get(i).toString());
            }
            System.out.println(new String(signByteArray));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Map<String, Object> attrs = wsNoti.getAttrs();
//        new ArrayList<>(attrs.get("business_dot"))
//        String business_dot = (String) attrs.get("business_dot");
//        String s = new String(business_dot.toArray());
//        log.info("onNoti : " + business_dot);
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
