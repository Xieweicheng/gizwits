package cn.mrxiexie.gizwits.entity.openapi;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mrxiexie
 * @date 3/28/19 2:08 PM
 */
@NoArgsConstructor
@Data
public class DeviceInfos {
    private List<DeviceInfo> devices;
}
