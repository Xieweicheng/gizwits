package cn.mrxiexie.gizwits.entity.openapi;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class DeviceInfo {

    /**
     * product_key : 4214bf2d79694a259232431b6f2ef46b
     * did : gKufzxZwYeyd3Skbsb6mza
     * mac : accf2350d446
     * is_online : false
     * passcode : JHHOOIWJBA
     * host : m2m.gizwits.com
     * port : 1883
     * port_s : 8883
     * ws_port : 8080
     * wss_port : 8880
     * remark : 备注信息
     * is_disabled : false
     * type : normal
     * dev_alias : 设备别名
     * dev_label : []
     * role : special
     */

    /**
     * 产品product_key
     */
    private String product_key;

    /**
     * 设备id
     */
    private String did;

    /**
     * 设备mac地址
     */
    private String mac;

    /**
     * 是否在线
     */
    private Boolean is_online;

    /**
     * 设备 passcode
     */
    private String passcode;

    /**
     * 连接服务器的域名
     */
    private String host;

    /**
     * M2M 的 mqtt 端口号
     */
    private Integer port;

    /**
     * M2M 的 mqtt SSL 端口号
     */
    private Integer port_s;

    /**
     * websocket 端口号
     */
    private Integer ws_port;

    /**
     * websocket SSL 端口号
     */
    private Integer wss_port;

    /**
     * 设备备注
     */
    private String remark;

    /**
     * 是否注销
     */
    private Boolean is_disabled;

    /**
     * 设备类型，单品设备:normal,中控设备:center_control,中控子设备:sub_dev
     */
    private String type;

    /**
     * 设备别名
     */
    private String dev_alias;

    /**
     * 设备标签列表，目前用于语音 API 批量设备控制
     */
    private String role;

    /**
     * 协议版本号，’01’, ‘01_01’, ‘03’, ‘04’
     */
    private String proto_ver;

    /**
     * wifi版本号
     */
    private String wifi_soft_version;

    /**
     * 是否连接sandbox环境
     */
    private Boolean is_sandbox;

    /**
     * 绑定角色， 特殊用户:special,拥有者:owner,访客:guest,普通用户:normal
     */
    private List<?> dev_label;

}
