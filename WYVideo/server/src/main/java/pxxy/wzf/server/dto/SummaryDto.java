package pxxy.wzf.server.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SummaryDto implements Serializable{

    private static final long serialVersionUID = 1734276607014433197L;
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
     * 对应的种类集合
     */
    private List<String> ids;

    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 根据种类id查找
     */
    private String categoryId;

    /**
     * 视频列表
     */
    private List<VideoDto> videoDtos;

    /**
     * 主演信息
     */
    private ActorDto actorDto;

}