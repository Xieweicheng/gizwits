# 机智云sdk文档说明
> 注意：该项目现阶段只支持在springboot上使用，后续会把 `OpenApi` 和 `Websocket` 接口部分抽离，使非springboot项目也能使用该sdk

依赖最新版的 `gizwits-spring-boot-starter`

**gradle**
```groovy
repositories {
    maven {
        url "http://192.168.101.100:8081/repository/maven-releases/"
    }
}

dependencies {
    implementation 'cn.mrxiexie:gizwits-spring-boot-starter:1.0.0-RELEASE'
}
```

## 机智云OpenApi

> 机智云OpenApi，开发者无需自己维护token是否过期，无需手动调用登录接口，只需要调用具体的方法即可。

- 在 `application.properties` 中添加以下必须配置

```properties
# 机制云OpeaApi账号
gizwits.open-api.username=string
# 机制云OpeaApi密码
gizwits.open-api.password=string
# 机智云AppId
gizwits.open-api.app-id=8748aa5a909b4ed3ac701ccdf7d4e97e
# 机智云AppSecret
gizwits.open-api.app-secret=638032300b1f4344a6b040057426a436
# 机智云ProductKey
gizwits.open-api.product-key=2c77978d3ae84ca79999b1a58af58a04
# 机智云ProductSecret
gizwits.open-api.product-secret=058eb9735bb84ddc98ba2d803720c2a7
```

- 注入 `GizwitsOpenApi`，即可使用封装好的OpenApi，目前已经封装了如下接口

| 方法名                | 说明             |
| --------------------- | ---------------- |
| createUser            | 创建用户         |
| login                 | 登录             |
| bindDevice            | 绑定设备         |
| getBoundDevices       | 获取绑定设备列表 |
| unbindDevice          | 解绑设备         |
| getDeviceOnlineStatus | 获取设备在线状态 |



## 机智云Websocket

> 启动机智云websocket，自动完成首次登录，断开重连，心跳发送，开发者无需手动登录与发送心跳，发送失败的消息会自动重发。
>
> 注意：现仅支持**标准数据点**方式通讯

- 默认情况下启动Websocket，有如下配置

```properties
# 是否启用websocket
gizwits.websocket.enabled=true
# 是否启用websocket登录后自动订阅
gizwits.websocket.autoSubscribe=false
# 与机智云云端心跳间隔设置（cron），以下配置为每分钟的 0 秒和 30 秒会发送心跳
gizwits.websocket.heartbeatInterval=0/30 * * * * ?
# 与机智云云端心跳断开时间
gizwits.websocket.heartbeatTimeout=120
```

- **注意**：`gizwits.websocket.heartbeatInterval` 该值必须配置
- 继承 `GizwitsWebsocketMessageHandler` 并注入到容器中即可

```java
@Component
public class Handler implements GizwitsWebsocketMessageHandler {
    
    @Autowired
    private GizwitsWebsocket gizwitsWebsocket;

    @Override
    public void onLogin(WsLoginRes wsLoginRes) {
		// 机智云登录后回调
    }

    @Override
    public void onSubscribe(WsSubscribeRes wsSubscribeRes) {
		// 机智云订阅设备后回调
    }

    @Override
    public void onOnlineStatus(WsOnlineStatus wsOnlineStatus) {
		// 机智云设备上下线通知
    }

    @Override
    public void onBindingChanged(WsBindingChanged wsBindingChanged) {
		// 机智云绑定解绑通知
    }

    @Override
    public void onNoti(WsNoti wsNoti) {
		// 主动读取或云端推送数据点通知
    }

    @Override
    public void onPong() {
 		// 心跳回调
    }

    @Override
    public void onInvalidMsg(WsInvalidMsg wsInvalidMsg) {
		// 云端收到非法消息回调
    }
}
```

- 注入 `GizwitsWebsocket` ，该类封装了与机智云通讯常用的方法

| 方法名    | 说明                |
| --------- | ------------------- |
| login     | 机智云websocket登录 |
| subscribe | 订阅设备            |
| read      | 读取数据点          |
| write     | 写入数据点          |
| ping      | 发送心跳            |

- 若开发者需要