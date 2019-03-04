package cn.mrxiexie.gizwits.enums;

/**
 * @author mrxiexie
 * @date 3/1/19 2:48 PM
 */
public enum GizwitsWsEnum {

    /**
     * 机智云Websocket cmd指令
     */
    LOGIN_REQ("login_req", "用户登录请求"),
    LOGIN_RES("login_res", "用户登录响应"),
    SUBSCRIBE_REQ("subscribe_req", "用户订阅设备请求"),
    SUBSCRIBE_RES("subscribe_res", "用户订阅设备响应"),
    S2C_ONLINE_STATUS("s2c_online_status", "设备上下线通知"),
    S2C_BINDING_CHANGED("s2c_binding_changed", "设备绑定解绑状态变更通知"),
    C2S_READ("c2s_read", " 标准数据点读操作"),
    C2S_WRITE("c2s_write", "标准数据点写操作"),
    S2C_NOTI("s2c_noti", "收到标准数据点"),
    PING("ping", "心跳请求"),
    PONG("pong", "心跳响应"),
    S2C_INVALID_MSG("s2c_invalid_msg", "非法消息"),
    ;


    String cmd;
    String desc;

    GizwitsWsEnum(String cmd, String desc) {
        this.cmd = cmd;
        this.desc = desc;
    }

    public String getCmd() {
        return cmd;
    }

    public String getDesc() {
        return desc;
    }
}
