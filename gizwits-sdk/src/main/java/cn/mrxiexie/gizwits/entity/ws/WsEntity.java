package cn.mrxiexie.gizwits.entity.ws;

import lombok.Data;

/**
 * @author mrxiexie
 * @date 3/1/19 2:46 PM
 */
@Data
public class WsEntity<T> {

    private String cmd;
    private T data;
}
