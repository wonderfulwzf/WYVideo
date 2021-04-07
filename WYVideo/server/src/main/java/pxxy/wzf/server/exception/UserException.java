package pxxy.wzf.server.exception;

/**
 * @auther: 王智芳
 * @Description 用户异常类
 * @date: 2021/4/7 18:51
 */
public class UserException extends AbstractException
{
    private static final long serialVersionUID = 1L;

    public UserException(int code, Object... params)
    {
        super(code, params);
    }

}