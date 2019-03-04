package cn.mrxiexie.gizwits.entity.openapi;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mrxiexie
 * @date 3/1/19 3:50 PM
 */
@NoArgsConstructor
@Data
public class GizwitsUser {


    /**
     * token : f8324047f20144f6914e7be19304f943
     * uid : f082f4e235974cfeb6a1b40a6024f47e
     * expire_at : 1504772734
     */

    private String token;
    private String uid;
    private Integer expire_at;
}
