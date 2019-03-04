package cn.mrxiexie.gizwits.openapi;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

/**
 * @author mrxiexie
 * @date 3/3/19 10:36 PM
 */
@Slf4j
@Aspect
public class GizwitsOpenApiAop {

    @Pointcut("execution(public * cn.mrxiexie.gizwits.openapi.GizwitsOpenApi.*(..))")
    public void pointcut() {

    }

    @Before(value = "pointcut()")
    public void onBefore(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.debug("OpenApi执行了【" + name + "】方法，参数为【" + Arrays.toString(args) + "】");
    }
}
