package cn.mrxiexie.gizwits.enums;

/**
 * @author mrxiexie
 * @date 3/1/19 2:15 PM
 */
public enum WebsocketListenerEnum {


    /**
     * websocket生命周期所有方法名
     */
    ON_OPEN("onOpen", "onOpen方法"),
    ON_MESSAGE("onMessage", "onMessage方法"),
    ON_CLOSING("onClosing", "onClosing方法"),
    ON_CLOSED("onClosed", "onClosed方法"),
    ON_FAILURE("onFailure", "onFailure方法");

    private String methodName;
    private String desc;

    WebsocketListenerEnum(String methodName, String desc) {
        this.methodName = methodName;
        this.desc = desc;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }}
