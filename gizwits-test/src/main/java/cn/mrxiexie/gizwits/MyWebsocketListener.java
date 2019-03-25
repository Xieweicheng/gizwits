//package cn.mrxiexie.gizwits;
//
//import cn.mrxiexie.gizwits.annotation.GizwitsWebsocketListener;
//import cn.mrxiexie.gizwits.ws.GizwitsWebsocket;
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.Response;
//import okhttp3.WebSocket;
//import okhttp3.WebSocketListener;
//import okio.ByteString;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * @author mrxiexie
// * @date 2/28/19 11:46 PM
// */
//@Slf4j
//@GizwitsWebsocketListener
//public class MyWebsocketListener extends WebSocketListener {
//
//    @Autowired
//    private GizwitsWebsocket gizwitsWebsocket;
//
//    public MyWebsocketListener() {
//
//        System.out.println("hahahahahhahahahahahah");
////        super();
//    }
//
//    @Override
//    public void onOpen(WebSocket webSocket, Response response) {
//        super.onOpen(webSocket, response);
//
//        gizwitsWebsocket.login();
//    }
//
//    @Override
//    public void onMessage(WebSocket webSocket, String text) {
//        super.onMessage(webSocket, text);
//        log.info("asd");
//    }
//
//    @Override
//    public void onMessage(WebSocket webSocket, ByteString bytes) {
//        super.onMessage(webSocket, bytes);
//    }
//
//    @Override
//    public void onClosing(WebSocket webSocket, int code, String reason) {
//        super.onClosing(webSocket, code, reason);
//    }
//
//    @Override
//    public void onClosed(WebSocket webSocket, int code, String reason) {
//        super.onClosed(webSocket, code, reason);
//    }
//
//    @Override
//    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
//        super.onFailure(webSocket, t, response);
//    }
//}
