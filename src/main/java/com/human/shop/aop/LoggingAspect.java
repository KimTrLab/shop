package com.human.shop.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

        // Around 실행 전과 실행 후를 모두 가로채서 처리
        // execution 어떤 메서드를 AOP 대상으로 할 것인지 지정하는 Pointcut 표현식]
        // * com.human.shop.service.*.*(..)) 처음 *는 리턴타입, 그 다음은 메서드 두번째 * 모든 클래스, 세번째 별은
        // ()가 있으니 메서드, 괄호안에 ..은 매개변수 상관없음
        @Around("execution(* com.human.shop.service.*.*(..))")
        public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
                // ProceedingJoinPoint는 Spring AOP의 @Around 어드바이스에서 대상 메서드 정보를 얻고, 실제 메서드를 실행하기
                // 위해 사용하는 객체

                String className = joinPoint.getTarget().getClass().getSimpleName();

                String methodName = joinPoint.getSignature().getName();

                long startTime = System.currentTimeMillis();

                // 시작로그
                log.info(
                                "[START] {}.{} args={}",
                                className,
                                methodName,
                                Arrays.toString(joinPoint.getArgs()));

                try {
                        //실제 메서드를 실행한다.
                        Object result = joinPoint.proceed();

                        //메서드가 종료되면 남기는 로그
                        long endTime = System.currentTimeMillis();
                        log.info(
                                        "[END] {}.{} ({}ms)",
                                        className,
                                        methodName,
                                        endTime - startTime);

                        return result;

                } catch (Exception e) {

                        log.error(
                                        "[ERROR] {}.{}",
                                        className,
                                        methodName,
                                        e);

                        throw e;
                }
        }
}
