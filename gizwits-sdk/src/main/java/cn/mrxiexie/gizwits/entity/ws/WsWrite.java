package cn.mrxiexie.gizwits.entity.ws;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author mrxiexie
 * @date 3/2/19 2:51 PM
 */
@NoArgsConstructor
@Data
public class WsWrite {

    /**
     * 设备did
     */
    private String did;

    /**
     * 标准数据点键值对
     */
    private Map<String, Object> attrs;
}
