package demo.web.config.aop;

import demo.web.controller.RestfulController;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * parent
 * demo.web.config.aop
 * 切面记录日志
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/30 10:17 Saturday
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    ThreadLocal<Long> start = new ThreadLocal<>();

    /**
     * 仅针对{@link RestfulController#hello()} 演示
     */
    //@Pointcut("execution(public * demo.web.controller.RestfulController.*(..))")//类内所有方法
    @Pointcut("execution(public * demo.web.controller.RestfulController.hello())")//仅hello()方法
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        start.set(System.currentTimeMillis());
    }

    @AfterReturning("webLog()")
    public void deAfter(JoinPoint joinPoint) {
        //计算耗时
        long cost = System.currentTimeMillis() - start.get();
        //类名
        String className = joinPoint.getSignature().getDeclaringType().getName();
        //方法名
        String methodName = joinPoint.getSignature().getName();
        //获取入参
        Object[] args = joinPoint.getArgs();
        List<Object> list = new ArrayList<>(args.length);
        for (Object obj : args) {
            list.add(obj);
        }
        //{} 表示占位符
        log.info("method=[{}.{}], args={}, cost=[{}ms]", className, methodName, list, cost);
    }
}
