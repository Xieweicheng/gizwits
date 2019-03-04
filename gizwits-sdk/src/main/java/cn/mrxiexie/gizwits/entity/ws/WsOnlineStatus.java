package cn.mrxiexie.gizwits.entity.ws;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mrxiexie
 * @date 3/2/19 2:35 PM
 */
@NoArgsConstructor
@Data
public class WsOnlineStatus {


    /**
     * did :
     * passcode :
     * mac :
     * online : true
     */

    private String did;
    private String passcode;
    private String mac;
    private Boolean online;
}
