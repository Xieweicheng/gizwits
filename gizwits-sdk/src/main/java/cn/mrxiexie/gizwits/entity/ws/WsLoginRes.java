package cn.mrxiexie.gizwits.entity.ws;

import lombok.Data;

/**
 * @author mrxiexie
 * @date 3/2/19 1:49 PM
 */
@Data
public class WsLoginRes {

    /**
     * websocket登录结果，true成功，false失败
     */
    Boolean success;
}
