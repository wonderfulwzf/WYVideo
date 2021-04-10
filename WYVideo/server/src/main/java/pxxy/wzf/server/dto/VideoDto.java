package pxxy.wzf.server.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoDto implements Serializable{

    private static final long serialVersionUID = 3212292792054276284L;
    /**
    * id
    */
    private Long id;

    /**
    * 标题
    */
    private String title;

    /**
    * 总结id
    */
    private Long summaryId;

    /**
    * 视频地址
    */
    private String video;

    /**
    * 时长
    */
    private Integer time;

    /**
    * 收费|0免费1收费
    */
    private Integer charge;

    /**
    * 顺序
    */
    private Integer sort;

    /**
    * vod用于播放
    */
    private String vod;

}