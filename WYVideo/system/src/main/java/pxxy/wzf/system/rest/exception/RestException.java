package pxxy.wzf.system.rest.exception;

/**
 * @auther: 王智芳
 * @Description 异常
 * @date: 2021/4/7 20:21
 */
public class RestException extends RuntimeException {
    private static final long serialVersionUID = 4827131589163111455L;

    public RestException(String message) {
        super(message);
    }
}