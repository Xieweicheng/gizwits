package cn.mrxiexie.gizwits;

import cn.mrxiexie.gizwits.annotation.GizwitsWebsocketListener;
import okhttp3.WebSocketListener;
import org.springframework.stereotype.Component;

/**
 * @author mrxiexie
 * @date 3/4/19 12:13 AM
 */
@GizwitsWebsocketListener
@Component
public class L  extends WebSocketListener {
}
