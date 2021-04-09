package pxxy.wzf.business.rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pxxy.wzf.business.rest.common.RestConstant;
import pxxy.wzf.business.rest.vo.common.Rest;
import pxxy.wzf.server.exception.AbstractException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 异常处理
 *
 * @author Gam saan
 * @version 1.0.0
 * @project banksteel-consult-member
 * @class
 * @date 2020/6/15 16:39
 */
@ControllerAdvice(basePackages = {"pxxy.wzf.business.rest"})
@ResponseBody
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger("businessLog");

    private static final String ERROR_LOG_TEMPLATE = "{}业务逻辑错误提示用户，信息为{}";

    /**
     * 200 - Bad Request
     * 处理请求参数输入错误
     * @author 平台研发部-wanghui
     * @Date 2020-07-09 21:18
     * @param e e
     * @return Rest
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Rest<Object> handleHibernateValidationException(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        if (!CollectionUtils.isEmpty(errors)) {
            String errorMessage = errors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(";\n"));
            LOGGER.warn("参数验证失败:{}", errorMessage);
            return Rest.optResultSuccessInfo(errorMessage);
        }
        return Rest.build().optResultFailInfo();
    }

    /**
     * 200 - Bad Request
     * 返回错误提示给到用户
     * @author 平台研发部-wanghui
     * @Date 2020-07-09 21:18
     * @param e e
     * @return Rest
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(RestException.class)
    public Rest<Object> handleRestException(RestException e) {
        LOGGER.warn(ERROR_LOG_TEMPLATE, RestConstant.HTTP_LOG, e.getMessage());
        return Rest.optResultSuccessInfo(e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UserException.class)
    public Rest<Object> handleUserException(UserException e) {
        LOGGER.warn(ERROR_LOG_TEMPLATE, RestConstant.HTTP_LOG, e.getMessage());
        return Rest.optResultSuccessInfo(e.getMessage());
    }

    /**
     * 200 - Bad Request
     * 返回错误提示给到用户
     * @author 平台研发部-wanghui
     * @Date 2020-07-09 21:18
     * @param e e
     * @return Rest
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(AbstractException.class)
    public Rest<Object> handleAbstractException(AbstractException e) {
        LOGGER.warn(ERROR_LOG_TEMPLATE, RestConstant.HTTP_LOG, e.getMessage());
        return Rest.optResultSuccessInfo(e.getMessage());
    }


    /**
     * 处理其他异常
     *
     * @param e e
     * @return result
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public void handleRunTimeException(RuntimeException e) {
        LOGGER.error("{}服务错误，错误信息为", RestConstant.HTTP_LOG, e);
    }

    /**
     * 获取请求信息
     * @param request
     * @author 平台研发部-wanghui
     * @Date 2020-07-09 21:18
     */
    private String getAccessInfo(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        String ipAddress = getIpAddress(request);
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getRequestURI();
        String method = request.getMethod();
        sb.append("请求ip:").append(ipAddress)
                .append("\n访问URI：").append(basePath)
                .append("\n请求方法：").append(method);
        return sb.toString();
    }

    /**
     * 获取请求ip
     * @param request
     * @author 平台研发部-wanghui
     * @Date 2020-07-09 21:18
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String unknown = "unknown";
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}