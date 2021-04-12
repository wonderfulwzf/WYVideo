package pxxy.wzf.server.dto;
import lombok.Data;
import java.io.Serializable;
@Data
public class FileDto implements Serializable{

    /**
    * id
    */
    private String id;

    /**
    * 阿里云相对路径
    */
    private String path;

    /**
    * 文件名称
    */
    private String name;

    /**
    * 文件大小
    */
    private Integer size;

    /**
    * 来源
    */
    private String resource;

    /**
    * vod用于播放视频
    */
    private String vod;

}