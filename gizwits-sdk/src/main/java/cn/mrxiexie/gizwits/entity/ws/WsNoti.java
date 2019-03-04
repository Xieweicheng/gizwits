package cn.mrxiexie.gizwits.entity.ws;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author mrxiexie
 * @date 3/2/19 2:53 PM
 */
@Data
@NoArgsConstructor
public class WsNoti {

    /**
     * 设备did
     */
    private String did;

    /**
     * 标准数据点键值对
     */
    private Map<String, Object> attrs;
}
