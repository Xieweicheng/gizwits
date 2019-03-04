package cn.mrxiexie.gizwits.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mrxiexie
 * @date 2/28/19 2:14 PM
 */
@Data
@ConfigurationProperties(prefix = "gizwits.open-api")
public class GizwitsOpenApiProperties {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * openApi请求地址
     */
    private String url = "http://api.gizwits.com/app";

    /**
     * 机智云appId
     */
    private String appId;

    /**
     * 机智云appSecret
     */
    private String appSecret;

    /**
     * 机智云productKey
     */
    private String productKey;

    /**
     * 机智云productSecret
     */
    private String productSecret;
}
