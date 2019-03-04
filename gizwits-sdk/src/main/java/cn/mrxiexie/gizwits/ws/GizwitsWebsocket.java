package cn.mrxiexie.gizwits.ws;

import cn.mrxiexie.gizwits.annotation.GizwitsWebsocketListener;
import cn.mrxiexie.gizwits.entity.ws.*;
import cn.mrxiexie.gizwits.enums.GizwitsWsEnum;
import cn.mrxiexie.gizwits.openapi.GizwitsOpenApi;
import cn.mrxiexie.gizwits.properties.GizwitsOpenApiProperties;
import cn.mrxiexie.gizwits.properties.GizwitsWebsocketProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mrxiexie
 * @date 2/28/19 10:04 PM
 */
@Slf4j
public class GizwitsWebsocket {

    @Autowired
    private GizwitsWebsocketProperties gizwitsWebsocketProperties;

    @Autowired
    private GizwitsOpenApiProperties gizwitsOpenApiProperties;

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private List<WebSocketListener> webSocketListeners;

    @Getter
    private WebSocket webSocket;

    @Autowired
    private GizwitsOpenApi gizwitsOpenApi;

    @PostConstruct
    public void init() {
        log.info("初始化websocket");
        Request request = new Request.Builder().url(gizwitsWebsocketProperties.getUrl()).build();
        WebSocketListener webSocketListener = null;
        for (WebSocketListener listener : webSocketListeners) {
            Class<? extends WebSocketListener> aClass = listener.getClass();
            String name = aClass.getCanonicalName();
            // 容器存在多个WebsocketListener，若有默认的DefaultGizwitsWebsocketListener，则使用该Listener
            if (name.contains("cn.mrxiexie.gizwits.ws.DefaultGizwitsWebsocketListener")) {
                webSocketListener = listener;
                break;
            }
            GizwitsWebsocketListener annotation = aClass.getAnnotation(GizwitsWebsocketListener.class);
            // 容器存在多个标注 @GizwitsWebsocketListener 注解的 WebsocketListener，默认使用第一个Listener
            if (annotation != null) {
                if (webSocketListeners.size() > 1) {
                    log.warn("存在多个标注【@GizwitsWebsocketListener】注解的【WebsocketListener】，使用了【" + name + "】");
                } else {
                    log.info("存在【@GizwitsWebsocketListener】注解的【WebsocketListener】，使用了【" + name + "】");
                }
                webSocketListener = listener;
                break;
            }
        }
        webSocket = okHttpClient.newWebSocket(request, webSocketListener);
    }

    /**
     * 机智云websocket登录，若登录失败2秒后会重新登录
     *
     * @return 若启动了自动配置则永远返回true，若websocket断开连接，会自动重新发送
     * @see GizwitsWebsocketProperties#getAutoConfig()
     */
    public boolean login() {
        WsEntity<WsLoginReq> loginWsEntity = new WsEntity<>();
        loginWsEntity.setCmd(GizwitsWsEnum.LOGIN_REQ.getCmd());
        WsLoginReq wsLogin = new WsLoginReq();
        wsLogin.setAppid(gizwitsOpenApiProperties.getAppId());
        wsLogin.setUid(gizwitsOpenApi.login().getUid());
        wsLogin.setToken(gizwitsOpenApi.login().getToken());
        wsLogin.setP0_type(gizwitsWebsocketProperties.getP0Type().getType());
        wsLogin.setHeartbeat_interval(gizwitsWebsocketProperties.getHeartbeatTimeout());
        wsLogin.setAuto_subscribe(gizwitsWebsocketProperties.getAutoSubscribe());
        loginWsEntity.setData(wsLogin);
        return webSocket.send(new JSONObject(loginWsEntity).toString());
    }

    /**
     * 机智云websocket订阅已绑定的dids
     *
     * @param dids did 列表
     * @return 若启动了自动配置则永远返回true，若websocket断开连接，会自动重新发送
     * @see GizwitsWebsocketProperties#getAutoConfig()
     */
    public boolean subscribe(List<String> dids) {
        WsEntity<List<WsSubscribeReq>> listWsEntity = new WsEntity<>();
        listWsEntity.setCmd(GizwitsWsEnum.SUBSCRIBE_REQ.getCmd());
        List<WsSubscribeReq> wsSubscribeReqs = new ArrayList<>();
        for (String did : dids) {
            WsSubscribeReq wsSubscribeReq = new WsSubscribeReq();
            wsSubscribeReq.setDid(did);
            wsSubscribeReqs.add(wsSubscribeReq);
        }
        listWsEntity.setData(wsSubscribeReqs);
        return webSocket.send(new JSONObject(listWsEntity).toString());
    }

    /**
     * 读取数据点
     *
     * @param did 设备did
     * @return 若启动了自动配置则永远返回true，若websocket断开连接，会自动重新发送
     * @see GizwitsWebsocketProperties#getAutoConfig()
     */
    public boolean read(String did) {
        return read(did, null);
    }

    /**
     * 读取数据点
     *
     * @param did   设备did
     * @param names （变长数据点可选参数：传入需要读取的数据点名称，参数省略表示读取全部数据点；定长数据点读操作忽略该参数）
     * @return 若启动了自动配置则永远返回true，若websocket断开连接，会自动重新发送
     * @see GizwitsWebsocketProperties#getAutoConfig()
     */
    public boolean read(String did, List<String> names) {
        WsEntity<WsRead> readWsEntity = new WsEntity<>();
        readWsEntity.setCmd(GizwitsWsEnum.C2S_READ.getCmd());
        WsRead wsRead = new WsRead();
        wsRead.setDid(did);
        wsRead.setNames(names);
        readWsEntity.setData(wsRead);
        return webSocket.send(new JSONObject(readWsEntity).toString());
    }

    /**
     * 写入数据点
     *
     * @param did   设备did
     * @param attrs 数据点
     * @return 若启动了自动配置则永远返回true，若websocket断开连接，会自动重新发送
     * @see GizwitsWebsocketProperties#getAutoConfig()
     */
    public boolean write(String did, Map<String, Object> attrs) {
        WsEntity<WsWrite> writeWsEntity = new WsEntity<>();
        writeWsEntity.setCmd(GizwitsWsEnum.C2S_WRITE.getCmd());
        WsWrite wsWrite = new WsWrite();
        wsWrite.setDid(did);
        wsWrite.setAttrs(attrs);
        writeWsEntity.setData(wsWrite);
        return webSocket.send(new JSONObject(writeWsEntity).toString());
    }

    /**
     * 发送心跳
     *
     * @return 若启动了自动配置则永远返回true，若websocket断开连接，会自动重新发送
     * @see GizwitsWebsocketProperties#getAutoConfig()
     */
    public void ping() {
        WsEntity wsEntity = new WsEntity();
        wsEntity.setCmd(GizwitsWsEnum.PING.getCmd());
        webSocket.send(new JSONObject(wsEntity).toString());
    }

    public void cancel() {
        webSocket.cancel();
    }
}
