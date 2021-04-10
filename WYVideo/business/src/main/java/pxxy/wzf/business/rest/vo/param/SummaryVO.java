package pxxy.wzf.business.rest.vo.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class SummaryVO implements Serializable {

    private static final long serialVersionUID = 4390973294939295872L;
    /**
    * id
    */
    private Long id;

    /**
    * 名称
    */
    private String name;

    /**
    * 描述
    */
    private String describe;

    /**
    * 时长
    */
    private Integer time;

    /**
    * 是否为vip|0普通1vip
    */
    private Integer flag;

    /**
    * 封面
    */
    private String image;

    /**
    * 热度
    */
    private Integer heat;

    /**
    * 主演id
    */
    private Long actorId;

}