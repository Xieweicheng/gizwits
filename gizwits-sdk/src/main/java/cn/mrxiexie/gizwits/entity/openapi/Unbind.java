package cn.mrxiexie.gizwits.entity.openapi;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mrxiexie
 * @date 3/2/19 12:20 AM
 */
@NoArgsConstructor
@Data
public class Unbind {

    private List<String> failed;
    private List<String> success;
}
