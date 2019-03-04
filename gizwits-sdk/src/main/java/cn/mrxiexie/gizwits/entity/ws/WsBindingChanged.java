package cn.mrxiexie.gizwits.entity.ws;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mrxiexie
 * @date 3/2/19 2:40 PM
 */
@NoArgsConstructor
@Data
public class WsBindingChanged {


    /**
     * did :
     * bind : true  ￼
     */

    private String did;
    private Boolean bind;
}
