package com.iocoder.yudao.module.framework.config.aspectj;

import com.alibaba.fastjson2.JSON;
import com.iocoder.yudao.module.commons.annotation.Log;
import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.core.domain.LoginUser;
import com.iocoder.yudao.module.commons.enums.HttpMethod;
import com.iocoder.yudao.module.commons.enums.common.ResultsEnum;
import com.iocoder.yudao.module.commons.enums.user.UserTypeEnum;
import com.iocoder.yudao.module.commons.utils.SecurityUtils;
import com.iocoder.yudao.module.commons.utils.ServletUtils;
import com.iocoder.yudao.module.commons.utils.StringUtils;
import com.iocoder.yudao.module.commons.utils.ip.IpUtils;
import com.iocoder.yudao.module.system.domain.OperateLogDO;
import com.iocoder.yudao.module.system.mapper.OperateLogMapper;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

/**
 * 操作日志记录
 *
 * @author wu kai
 * @since 2022/6/29
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    private static final ThreadLocal<Long> startTimeThread = new NamedThreadLocal<>("ThreadLocal StartTime");

    @Resource
    OperateLogMapper operateLogMapper;

    /**
     * 配置切入点
     */
    @Pointcut(value = "@annotation(com.iocoder.yudao.module.commons.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 处理请求之前记录时间戳
     */
    @Before("logPointCut()")
    public void before() {
        long beginTime = System.currentTimeMillis();
        startTimeThread.set(beginTime);
    }

    /**
     * 请求处理完成之后执行
     *
     * @param joinPoint  切点
     * @param jsonResult 返回值
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        try {
            // 获取线程中的开始时间
            Long beginTime = startTimeThread.get();
            // 获取到数据后删除
            startTimeThread.remove();
            // 计算操作时长
            long reqTime = System.currentTimeMillis() - beginTime;
            if (jsonResult instanceof CommonResult) {
                CommonResult commonResult = (CommonResult) jsonResult;
                recordLog(joinPoint, reqTime, null, commonResult.getCode(), commonResult.getMsg(), jsonResult);
            } else {
                recordLog(joinPoint, reqTime, null, ResultsEnum.SUCCEED.getCode(), ResultsEnum.SUCCEED.getName(), jsonResult);
            }
        } catch (Exception e) {
            log.error("日志插入失败，错误信息：{}", e.getMessage());
        }
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        try {
            // 获取线程中的开始时间
            Long beginTime = startTimeThread.get();
            // 获取到数据后删除
            startTimeThread.remove();
            // 计算操作时长
            long reqTime = System.currentTimeMillis() - beginTime;
            // 保存日志
            recordLog(joinPoint, reqTime, e, ResultsEnum.FAILURE.getCode(), ResultsEnum.FAILURE.getName(), null);
        } catch (Exception ex) {
            log.error("日志插入失败，错误信息为：{}", e.getMessage());
        }
    }

    protected void recordLog(final JoinPoint joinPoint, long spendTime, final Throwable e, int code, String msg, Object jsonResult) {
        try {
            // 获取登录用户信息
            LoginUser loginUser = SecurityUtils.getLoginUser();
            OperateLogDO operateLog = new OperateLogDO();
            // 操作人编号
            operateLog.setUserId(loginUser.getUserId());
            // 操作人类型-后台用户
            operateLog.setUserType(UserTypeEnum.MANAGE.getValue());

            //获取切入点方法名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            // 获取自定义注解 @Log 的信息
            Log log = method.getAnnotation(Log.class);
            // 获取 swagger @ApiOperation 注解的值
            String value = method.getAnnotation(ApiOperation.class).value();

            // 获取连接处的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取连接处的方法名
            String methodName = signature.getName();
            if (log != null) {
                // 接口描述
                operateLog.setModule(StringUtils.isNotEmpty(value) ? log.title() + "-" + value : log.title());
                // 操作类型
                operateLog.setType(log.businessType().getCode());
                // 请求方式
                operateLog.setRequestMethod(ServletUtils.getRequest().getMethod());
                // 请求地址
                operateLog.setRequestUrl(ServletUtils.getRequest().getRequestURI());
                // 请求的java方法名
                operateLog.setJavaMethod(className + "." + methodName + "()");
                // 方法参数
                setRequestValue(joinPoint, operateLog);
            }
            if (e != null) {
                // 错误信息
                operateLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 浏览器类型
            operateLog.setUserAgent(ServletUtils.getUserAgent());
            // 操作时长
            operateLog.setDuration((int) spendTime);
            // 操作时间
            operateLog.setStartTime(LocalDateTime.now());
            // 用户IP
            operateLog.setUserIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
            // 请求结果码
            operateLog.setResultCode(code);
            // 结果提示
            operateLog.setResultMsg(msg);
            // 返回结果数据
            operateLog.setResultData(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
            // 执行新增
            operateLogMapper.insert(operateLog);

        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 设置请求参数
     *
     * @param joinPoint  切点
     * @param operateLog 日志对象
     */
    private void setRequestValue(JoinPoint joinPoint, OperateLogDO operateLog){
        String requestMethod = operateLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operateLog.setJavaMethodArgs(StringUtils.substring(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operateLog.setJavaMethodArgs(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (StringUtils.isNotNull(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params.append(jsonObj.toString()).append(" ");
                    } catch (Exception e) {
                        log.error("参数拼接异常，错误信息为：{}",e.getMessage());
                    }
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
