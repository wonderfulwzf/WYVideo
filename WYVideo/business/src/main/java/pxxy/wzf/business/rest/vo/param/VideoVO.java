package pxxy.wzf.business.rest.vo.param;

import lombok.Data;
import pxxy.wzf.business.rest.vo.common.PageQuery;

import java.io.Serializable;

@Data
public class VideoVO extends PageQuery implements Serializable {

    private static final long serialVersionUID = 5066431172867548938L;
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