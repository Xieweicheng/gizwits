package cn.mrxiexie.gizwits.entity.ws;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mrxiexie
 * @date 3/2/19 2:56 PM
 */
@Data
@NoArgsConstructor
public class WsInvalidMsg {

    /**
     * 错误码
     */
    private Integer error_code;

    /**
     * 描述文本
     */
    private String msg;
}
