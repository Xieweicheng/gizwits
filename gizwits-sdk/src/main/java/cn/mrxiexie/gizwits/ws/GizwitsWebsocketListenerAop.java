package cn.mrxiexie.gizwits.ws;

import cn.mrxiexie.gizwits.entity.ws.*;
import cn.mrxiexie.gizwits.enums.GizwitsWsEnum;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author mrxiexie
 * @date 3/1/19 1:33 PM
 */
@Slf4j
@Aspect
public class GizwitsWebsocketListenerAop {

    @Autowired
    private GizwitsWebsocket gizwitsWebsocket;

    @Autowired
    private List<GizwitsWebsocketMessageHandler> messageHandlers;

    private Gson gson;

    {
        gson = new Gson();
    }

    @Pointcut(value = "execution(public * *.*WebSocketListener.*(..))")
    public void pointcut() {

    }

    @Before(value = "pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String name = joinPoint.getSignature().getName();

//        Signature signature = joinPoint.getSignature();
//        String kind = joinPoint.getKind();
//        SourceLocation sourceLocation = joinPoint.getSourceLocation();
//        JoinPoint.StaticPart staticPart = joinPoint.getStaticPart();
//        Object target = joinPoint.getTarget();
//        Class declaringType = signature.getDeclaringType();
//        String declaringTypeName = signature.getDeclaringTypeName();
//        int modifiers = signature.getModifiers();
//        System.out.println("name : " + name);
//        System.out.println("declaringType : " + declaringType);
//        System.out.println("declaringTypeName : " + declaringTypeName);
//        System.out.println("modifiers : " + modifiers);
//        System.out.println(gizwitsWebsocket);
//        System.out.println("args : " + Arrays.toString(args));
//        System.out.println("kind : " + kind);
//        System.out.println("sourceLocation : " + sourceLocation);
//        System.out.println("staticPart : " + staticPart);
//        System.out.println("target : " + target);
        Class<? extends GizwitsWebsocketListenerAop> aClass = getClass();
        try {
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(name)) {
                    method.invoke(this, args);
                    break;
                }
            }
        } catch (Exception e) {
            log.error("exception");
            e.printStackTrace();
        }
    }

    public void onOpen(WebSocket webSocket, Response response) {
        log.debug("机智云websocket【onOpen】");
        gizwitsWebsocket.login();
    }

    public void onMessage(WebSocket webSocket, String text) {

        log.debug("机智云websocket【onMessage】，收到消息为【" + text + "】");
        WsEntity wsEntity = gson.fromJson(text, WsEntity.class);
        String cmd = wsEntity.getCmd();

        if (cmd.equals(GizwitsWsEnum.LOGIN_RES.getCmd())) {
            WsLoginRes wsLoginRes = gson.fromJson(JSONObject.valueToString(wsEntity.getData()), WsLoginRes.class);
            handleLoginRes(wsLoginRes);
            for (GizwitsWebsocketMessageHandler messageHandler : messageHandlers) {
                messageHandler.onLogin(wsLoginRes);
            }
        }

        if (cmd.equals(GizwitsWsEnum.SUBSCRIBE_RES.getCmd())) {
            WsSubscribeRes wsSubscribeRes = gson.fromJson(JSONObject.valueToString(wsEntity.getData()), WsSubscribeRes.class);
            for (GizwitsWebsocketMessageHandler messageHandler : messageHandlers) {
                messageHandler.onSubscribe(wsSubscribeRes);
            }
        }

        if (cmd.equals(GizwitsWsEnum.S2C_ONLINE_STATUS.getCmd())) {
            WsOnlineStatus wsOnlineStatus = gson.fromJson(JSONObject.valueToString(wsEntity.getData()), WsOnlineStatus.class);
            for (GizwitsWebsocketMessageHandler messageHandler : messageHandlers) {
                messageHandler.onOnlineStatus(wsOnlineStatus);
            }
        }

        if (cmd.equals(GizwitsWsEnum.S2C_BINDING_CHANGED.getCmd())) {
            WsBindingChanged wsBindingChanged = gson.fromJson(JSONObject.valueToString(wsEntity.getData()), WsBindingChanged.class);
            for (GizwitsWebsocketMessageHandler messageHandler : messageHandlers) {
                messageHandler.onBindingChanged(wsBindingChanged);
            }
        }

        if (cmd.equals(GizwitsWsEnum.S2C_NOTI.getCmd())) {
            WsNoti wsNoti = gson.fromJson(JSONObject.valueToString(wsEntity.getData()), WsNoti.class);
            for (GizwitsWebsocketMessageHandler messageHandler : messageHandlers) {
                messageHandler.onNoti(wsNoti);
            }
        }

        if (cmd.equals(GizwitsWsEnum.PONG.getCmd())) {
            for (GizwitsWebsocketMessageHandler messageHandler : messageHandlers) {
                messageHandler.onPong();
            }
        }

        if (cmd.equals(GizwitsWsEnum.S2C_INVALID_MSG.getCmd())) {
            WsInvalidMsg wsInvalidMsg = gson.fromJson(JSONObject.valueToString(wsEntity.getData()), WsInvalidMsg.class);
            handleInvalidMsg(wsInvalidMsg);
            for (GizwitsWebsocketMessageHandler messageHandler : messageHandlers) {
                messageHandler.onInvalidMsg(wsInvalidMsg);
            }
        }
    }

    public void onClosing(WebSocket webSocket, int code, String reason) {
        log.debug("机智云websocket【onClosing】，code【" + code + "】，reason【" + reason + "】");
        gizwitsWebsocket.init();
    }

    public void onClosed(WebSocket webSocket, int code, String reason) {
        log.debug("机智云websocket【onClosed】，code【" + code + "】，reason【" + reason + "】");
        gizwitsWebsocket.init();
    }

    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        log.debug("机智云websocket【onFailure】，Throwable【" + t + "】");
        gizwitsWebsocket.init();
    }

    /**
     * 处理登录响应
     *
     * @param wsLoginRes
     */
    private void handleLoginRes(WsLoginRes wsLoginRes) {
        if (wsLoginRes.getSuccess()) {
            log.info("机智云websocket登录成功");
        } else {
            log.info("机智云websocket登录失败，2秒后重新登录");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gizwitsWebsocket.login();
        }
    }

    /**
     * 处理非法信息
     *
     * @param wsInvalidMsg
     */
    private void handleInvalidMsg(WsInvalidMsg wsInvalidMsg) {
        if (wsInvalidMsg.getError_code() == 1009) {
            log.error("M2M socket has closed, please login again!");
            log.error("机智云websocket登录超时，重新进行websocket登录");
            gizwitsWebsocket.login();
        }
    }

    /**
     * 心跳
     */
//    @Scheduled(cron = "${gizwits.websocket.heartbeatInterval}")
    private void handleHeartbeat() {
        gizwitsWebsocket.ping();
    }
}
