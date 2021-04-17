package pxxy.wzf.system.rest.vo.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {

    private static final long serialVersionUID = -5848553924262374297L;
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