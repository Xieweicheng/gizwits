package cn.mrxiexie.gizwits.entity.ws;

import lombok.Data;

/**
 * @author mrxiexie
 * @date 3/1/19 2:41 PM
 */
@Data
public class WsLoginReq {


    /**
     * appid :
     * uid :
     * token :
     * p0_type :
     * heartbeat_interval : 1
     * auto_subscribe : true
     */

    private String appid;
    private String uid;
    private String token;
    private String p0_type;
    private Integer heartbeat_interval;
    private Boolean auto_subscribe;
}
