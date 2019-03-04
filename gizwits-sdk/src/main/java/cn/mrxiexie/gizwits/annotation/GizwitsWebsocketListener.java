package cn.mrxiexie.gizwits.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author mrxiexie
 * @date 2/28/19 10:33 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Component
public @interface GizwitsWebsocketListener {

}
