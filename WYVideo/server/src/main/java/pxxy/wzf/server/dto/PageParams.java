package pxxy.wzf.server.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * @auther: 王智芳
 * @Description 分页参数
 * @date: 2021/4/7 21:31
 */
@Data
public class PageParams implements Serializable {

    private static final long serialVersionUID = -3049914829433533912L;

    /**
     * 分页
     */
    private Integer pageNo;

    /**
     * 每页数量
     */
    private Integer pageSize;

    public PageParams(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}