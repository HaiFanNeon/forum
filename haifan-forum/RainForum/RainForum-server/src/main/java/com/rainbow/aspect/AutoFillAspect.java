package com.rainbow.aspect;


import com.rainbow.annotation.AutoFill;
import com.rainbow.constant.AutoFillConstant;
import com.rainbow.context.BaseContext;
import com.rainbow.enumeration.OperationType;
import com.rainbow.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class AutoFillAspect {

    @Pointcut("execution(* com.rainbow.service.impl.*.*(..)) && @annotation(com.rainbow.annotation.AutoFill)")
    public void autoFillPointcut() {}

    @Before("autoFillPointcut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段的自动填充");

        // 获取到当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);

        OperationType operationType = autoFill.value();
        Object[] args = joinPoint.getArgs();

        if (args == null || args.length == 0) {
            return ;
        }

        Object arg = args[0];
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        // 根据当前不同的操作类型，为对应的属性通过反射来赋值
        if (operationType == OperationType.INSERT) {
            try {
                Method setCreateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setCreateUser.invoke(arg, currentId);
                setUpdateUser.invoke(arg, currentId);
                setCreateTime.invoke(arg, now);
                setUpdateTime.invoke(arg, now);

            } catch (Exception e) {
                throw new BaseException("自动填充异常");
            }
        } else if (operationType == OperationType.UPDATE) {
            // 为两个字段来赋值
            try {
                Method setUpdateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setUpdateTime.invoke(arg, now);
                setUpdateUser.invoke(arg, currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
