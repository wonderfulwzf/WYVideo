package pxxy.wzf.server.exception;

public class AbstractException extends RuntimeException implements Exception {
    private static final long serialVersionUID = 1L;
    protected Integer code;
    protected Object[] params;

    public AbstractException(Integer code, Object... params) {
        this.code = code;
        this.params = params;
    }

    public Integer getMessageCode() {
        return this.code;
    }

    public Object[] getMessageParams() {
        return this.params;
    }
}