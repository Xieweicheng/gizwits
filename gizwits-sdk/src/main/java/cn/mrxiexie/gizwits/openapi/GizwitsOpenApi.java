package cn.mrxiexie.gizwits.openapi;

import cn.hutool.crypto.SecureUtil;
import cn.mrxiexie.gizwits.entity.openapi.DeviceInfo;
import cn.mrxiexie.gizwits.entity.openapi.GizwitsUser;
import cn.mrxiexie.gizwits.entity.openapi.Unbind;
import cn.mrxiexie.gizwits.properties.GizwitsOpenApiProperties;
import cn.mrxiexie.gizwits.util.RequestUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mrxiexie
 * @date 2/28/19 3:35 PM
 */
@Slf4j
public class GizwitsOpenApi {

    @Autowired
    private GizwitsOpenApiProperties gizwitsOpenApiProperties;

    @Autowired
    private RequestUtil requestUtil;

    @Autowired
    private GizwitsUser gizwitsUser;

    private Gson gson;

    {
        gson = new Gson();
    }

    @Bean(name = "gizwitsUser")
    private GizwitsUser gizwitsUser() {
        return login();
    }

    /**
     * 创建用户
     *
     * @return 用户信息实体，创建失败返回null
     */
    public GizwitsUser createUser() {
        return createUser(gizwitsOpenApiProperties.getUsername(), gizwitsOpenApiProperties.getPassword());
    }

    /**
     * 创建用户
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 用户信息实体，创建失败返回null
     */
    public GizwitsUser createUser(String username, String password) {
        JSONObject obj = new JSONObject();
        obj.put("username", username);
        obj.put("password", password);
        Headers headers = Headers.of("X-Gizwits-Application-Id", gizwitsOpenApiProperties.getAppId());
        String result = requestUtil.postSyn(gizwitsOpenApiProperties.getUrl() + "/users", obj.toString(), headers);
        if (!isSuccess(result)) {
            return null;
        }
        return gson.fromJson(result, GizwitsUser.class);
    }

    /**
     * 登录，先判断是否登录，未登录则登录
     * @return
     */
    public GizwitsUser login() {
        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        if (gizwitsUser == null || second > gizwitsUser.getExpire_at()) {
            log.debug("OpenApi用户请求登录");
            JSONObject obj = new JSONObject();
            obj.put("username", gizwitsOpenApiProperties.getUsername());
            obj.put("password", gizwitsOpenApiProperties.getPassword());
            Headers headers = Headers.of("X-Gizwits-Application-Id", gizwitsOpenApiProperties.getAppId());
            String result = requestUtil.postSyn(gizwitsOpenApiProperties.getUrl() + "/login", obj.toString(), headers);

            if (!isSuccess(result)) {
                log.error("OpenApi登录失败，两秒后重试。。。");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return login();
            }
            log.debug("OpenApi登录成功");
            gizwitsUser = gson.fromJson(result, GizwitsUser.class);
        }
        log.debug("OpenApi用户已登录");
        return gizwitsUser;
    }

    /**
     * 绑定设备
     *
     * @param mac 设备mac码
     * @return 绑定成功返回DeviceInfo对象
     */
    public DeviceInfo bindDevice(String mac) {
        return bindDevice(mac, null, null);
    }

    /**
     * 绑定设备
     *
     * @param mac          设备mac码
     * @param deviceAlias  设备别名
     * @param deviceRemark 设备批注
     * @return 绑定成功返回DeviceInfo对象
     */
    public DeviceInfo bindDevice(String mac, String deviceAlias, String deviceRemark) {

        JSONObject obj = new JSONObject();
        obj.put("product_key", gizwitsOpenApiProperties.getProductKey());
        obj.put("mac", mac);
        obj.put("remark", deviceRemark);
        obj.put("dev_alias", deviceAlias);
        Map<String, String> headersMap = new HashMap<>(8);
        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        headersMap.put("X-Gizwits-Application-Id", gizwitsOpenApiProperties.getAppId());
        headersMap.put("X-Gizwits-User-token", login().getToken());
        headersMap.put("X-Gizwits-Timestamp", String.valueOf(second));
        headersMap.put("X-Gizwits-Signature", SecureUtil.md5(gizwitsOpenApiProperties.getProductSecret() + second).toLowerCase());
        String result = requestUtil.postSyn(gizwitsOpenApiProperties.getUrl() + "/bind_mac", obj.toString(), Headers.of(headersMap));

        if (!isSuccess(result)) {
            return null;
        }

        return (DeviceInfo) JSONObject.stringToValue(result);
    }

    /**
     * 获取绑定列表
     *
     * @return 绑定列表
     */
    public List<DeviceInfo> getBoundDevices() {
        return getBoundDevices(null, null, null, null);
    }

    /**
     * 获取绑定列表
     *
     * @param limit        返回的结果条数
     * @param skip         表示跳过的条数，间接表示页数
     * @param showDisabled 是否显示已注销的设备，1:显示，0:不显示
     * @param showProtoVer 是否显示设备通信协议版本，1:显示，0:不显示
     * @return 绑定列表
     */
    public List<DeviceInfo> getBoundDevices(Integer limit, Integer skip, Boolean showDisabled, Boolean showProtoVer) {

        JSONObject obj = new JSONObject();
        obj.put("limit", limit);
        obj.put("skip", skip);
        obj.put("show_disabled", showDisabled);
        obj.put("show_proto_ver", showProtoVer);
        Map<String, String> headersMap = new HashMap<>(4);
        headersMap.put("X-Gizwits-Application-Id", gizwitsOpenApiProperties.getAppId());
        headersMap.put("X-Gizwits-User-token", login().getToken());

        String result = requestUtil.getSyn(gizwitsOpenApiProperties.getUrl() + "/bindings", obj, Headers.of(headersMap));

        if (!isSuccess(result)) {
            return null;
        }

        String devices = new JSONObject(result).getJSONArray("devices").toString();
        return gson.fromJson(devices, List.class);
    }

    /**
     * 解绑设备
     *
     * @param did 设备 did
     * @return 成功返回 true，失败返回 false
     */
    public boolean unbindDevice(String did) {
        Unbind unbind = unbindDevice(Collections.singletonList(did));
        return unbind.getSuccess().size() > 0;
    }

    /**
     * 解绑设备
     *
     * @param dids 设备 did 列表
     */
    public Unbind unbindDevice(List<String> dids) {
        JSONObject obj = new JSONObject();
        JSONArray didArray = new JSONArray();
        for (String did : dids) {
            JSONObject didObj = new JSONObject();
            didObj.put("did", did);
            didArray.put(didObj);
        }
        Map<String, String> headersMap = new HashMap<>(4);
        headersMap.put("X-Gizwits-Application-Id", gizwitsOpenApiProperties.getAppId());
        headersMap.put("X-Gizwits-User-token", login().getToken());
        obj.put("devices", didArray);

        String result = requestUtil.deleteSyn(gizwitsOpenApiProperties.getUrl() + "/bindings", obj.toString(), Headers.of(headersMap));

        if (!isSuccess(result)) {
            return null;
        }

        return gson.fromJson(result, Unbind.class);
    }


    /**
     * 获取设备的在线状态
     *
     * @param did 设备did
     * @return 上线返回 true，下线返回 false
     */
    public Boolean getDeviceOnlineStatus(String did) {
        String result = requestUtil.getSyn(gizwitsOpenApiProperties.getUrl() + "/devices/" + did);

        if (!isSuccess(result)) {
            return null;
        }

        return new JSONObject(result).getBoolean("is_online");
    }

    /**
     * 判断请求结果是否成功
     *
     * @param result 请求结果
     * @return
     */
    private boolean isSuccess(String result) {
        if (StringUtils.isEmpty(result)) {
            return false;
        }
        try {
            JSONObject jsonObject = new JSONObject(result);
            log.debug("OpenApi请求结果 ： " + jsonObject.toString());
            int error_code = jsonObject.getInt("error_code");
            String error_message = jsonObject.getString("error_message");
            Object detail_message = jsonObject.get("detail_message");
            return false;
        } catch (JSONException ignore) {

        }
        return true;
    }
}
