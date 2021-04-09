package pxxy.wzf.server.dto;
import lombok.Data;
<#list typeSet as type>
    <#if type=='BigDecimal'>
        import java.math.BigDecimal;
    </#if>
</#list>
import java.io.Serializable;
@Data
public class ${Domain}Dto implements Serializable{

<#list fieldList as field>
    /**
    * ${field.comment}
    */
    private ${field.javaType} ${field.nameHump};

</#list>

}