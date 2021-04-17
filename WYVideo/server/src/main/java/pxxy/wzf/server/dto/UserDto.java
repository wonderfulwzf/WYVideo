package pxxy.wzf.server.dto;
import lombok.Data;
import java.io.Serializable;
@Data
public class UserDto implements Serializable{

    /**
    * id
    */
    private Integer id;

    /**
    * 用户名
    */
    private String name;

    /**
    * 手机号
    */
    private String phone;

    /**
    * 密码
    */
    private String password;


}