package pxxy.wzf.business.rest.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value = "演员VO")
public class ActorVO implements Serializable {

    private static final long serialVersionUID = -4231450578460723393L;

    @ApiModelProperty(value = "id")
    private Long id;
    @NotNull(message = "用户名不能为空")
    @ApiModelProperty(value = "名称")
    private String name;
    @NotNull(message = "图片地址不能为空")
    @ApiModelProperty(value = "头像地址")
    private String image;
    @NotNull
    @Length(min = 60,max = 200,message = "简介长度要60~200")
    @ApiModelProperty(value = "简介")
    private String intro;
}