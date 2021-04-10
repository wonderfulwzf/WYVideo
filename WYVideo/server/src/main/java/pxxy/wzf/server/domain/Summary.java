package pxxy.wzf.server.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Summary {
    private Long id;

    private String name;

    private String describe;

    private Integer time;

    private Integer flag;

    private String image;

    private Integer heat;

    private Long actorId;

    private Date createTime;

    private Date updateTime;

    private Integer version;

}