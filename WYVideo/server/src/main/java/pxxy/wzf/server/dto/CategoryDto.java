package pxxy.wzf.server.dto;
import lombok.Data;
import java.io.Serializable;
@Data
public class CategoryDto implements Serializable{

    /**
    * id
    */
    private String id;

    /**
    * 父id
    */
    private String parent;

    /**
    * 名称
    */
    private String name;

    /**
    * 
    */
    private Integer sort;

}