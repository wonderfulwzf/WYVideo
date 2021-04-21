package pxxy.wzf.business.rest.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "分类VO")
public class CategoryVO implements Serializable {

    /**
    * id
    */
    @ApiModelProperty(value = "id")
    private String id;

    /**
    * 父id
    */
    @ApiModelProperty(value = "父id")
    private String parent;

    /**
    * 名称
    */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
    * 顺序
    */
    @ApiModelProperty(value = "排序")
    private Integer sort;

}