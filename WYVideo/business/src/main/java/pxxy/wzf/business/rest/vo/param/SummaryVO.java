package pxxy.wzf.business.rest.vo.param;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

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

    /**
     * @auther: 王智芳
     * @Description 分类ids
     * @date: 2021/4/11 15:47
     */
    private List<String> ids;
}