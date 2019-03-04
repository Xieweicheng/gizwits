package cn.mrxiexie.gizwits.entity.ws;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mrxiexie
 * @date 3/2/19 2:16 PM
 */
@NoArgsConstructor
@Data
public class WsSubscribeRes {

    private List<SuccessBean> success;
    private List<FailedBean> failed;

    @NoArgsConstructor
    @Data
    public static class SuccessBean {
        /**
         * did :
         * error_code : 0
         * msg :
         */

        private String did;
        private Integer error_code;
        private String msg;
    }

    @NoArgsConstructor
    @Data
    public static class FailedBean {
        /**
         * did :
         * error_code : 0
         * msg :
         */

        private String did;
        private Integer error_code;
        private String msg;
    }
}
