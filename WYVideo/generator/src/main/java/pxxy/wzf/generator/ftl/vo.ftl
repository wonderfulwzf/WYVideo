package pxxy.wzf.business.rest.vo.param;

import lombok.Data;


import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ${Domain}VO implements Serializable {

<#list fieldList as field>
    /**
    * ${field.comment}
    */
    private ${field.javaType} ${field.nameHump};

</#list>
}