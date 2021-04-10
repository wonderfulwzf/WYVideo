package pxxy.wzf.server.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Video {
    private Long id;

    private String title;

    private Long summaryId;

    private String video;

    private Integer time;

    private Integer charge;

    private Integer sort;

    private String vod;

    private Date createTime;

    private Date updateTime;

    private Integer version;

}