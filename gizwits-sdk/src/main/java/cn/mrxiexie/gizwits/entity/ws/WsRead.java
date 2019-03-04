package cn.mrxiexie.gizwits.entity.ws;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mrxiexie
 * @date 3/2/19 2:48 PM
 */
@NoArgsConstructor
@Data
public class WsRead {

    /**
     * 设备did
     */
    private String did;

    /**
     * （变长数据点可选参数：传入需要读取的数据点名称，参数省略表示读取全部数据点；定长数据点读操作忽略该参数）
     */
    private List<String> names;
}
