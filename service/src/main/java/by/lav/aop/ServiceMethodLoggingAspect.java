package by.lav.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceMethodLoggingAspect {

    @Pointcut("within(by.lav.service.*Service)")
    public void isServiceLayer() {
    }

    @Pointcut("execution(public * findById(*))")
    public void anyFindByIdServiceMethod() {
    }

    @Pointcut("execution(public * findAll())")
    public void anyFindAllServiceMethod() {
    }

    @Pointcut("execution(public * create(*))")
    public void anyCreateServiceMethod() {
    }

    @Pointcut("execution(public * update(*,*))")
    public void anyUpdateServiceMethod() {
    }

    @Pointcut("execution(public * delete(*))")
    public void anyDeleteServiceMethod() {
    }

    @Around("isServiceLayer() && anyFindByIdServiceMethod() && target(service) && args(id)")
    public Object addLoggingAroundFindByIdServiceMethod(ProceedingJoinPoint joinPoint, Object service, Object id)
            throws Throwable {
        String methodName = "findById";
        log.info("AROUND before - invoked method {} in class {}, with id {}", methodName, service, id);
        return addTryCatchBlock(joinPoint, service, methodName);
    }

    @Around("isServiceLayer() && anyFindAllServiceMethod() && target(service)")
    public Object addLoggingAroundFindAllServiceMethod(ProceedingJoinPoint joinPoint, Object service)
            throws Throwable {
        String methodName = "findAll";
        log.info("AROUND before - invoked method {} in class {}", methodName, service);
        return addTryCatchBlock(joinPoint, service, methodName);
    }

    @Around("isServiceLayer() && anyCreateServiceMethod() && target(service) && args(dto)")
    public Object addLoggingAroundCreateServiceMethod(ProceedingJoinPoint joinPoint, Object service, Object dto)
            throws Throwable {
        String methodName = "create";
        log.info("AROUND before - invoked method {} in class {}, with DTO {}", methodName, service, dto);
        return addTryCatchBlock(joinPoint, service, methodName);
    }

    @Around("isServiceLayer() && anyUpdateServiceMethod() && target(service) && args(id,dto)")
    public Object addLoggingAroundUpdateServiceMethod(ProceedingJoinPoint joinPoint, Object service, Object id, Object dto)
            throws Throwable {
        String methodName = "update";
        log.info("AROUND before - invoked method {} in class {}, with id {} and DTO {}", methodName, service, id, dto);
        return addTryCatchBlock(joinPoint, service, methodName);
    }

    @Around("isServiceLayer() && anyDeleteServiceMethod() && target(service) && args(id)")
    public Object addLoggingAroundDeleteServiceMethod(ProceedingJoinPoint joinPoint, Object service, Object id)
            throws Throwable {
        String methodName = "delete";
        log.info("AROUND before - invoked method {} in class {}, with id {}", methodName, service, id);
        return addTryCatchBlock(joinPoint, service, methodName);
    }

    private Object addTryCatchBlock(ProceedingJoinPoint joinPoint, Object service, String methodName)
            throws Throwable {
        try {
            Object result = joinPoint.proceed();
            log.info("AROUND after returning - invoked method {} in class {}, result {}", methodName, service, result);
            return result;
        } catch (Throwable ex) {
            log.info("AROUND after throwing - invoked method {} in class {}, exception {}: {}",
                    methodName, service, ex.getClass(), ex.getMessage());
            throw ex;
        } finally {
            log.info("AROUND after (finally) - invoked method {} in class {}", methodName, service);
        }
    }
}
