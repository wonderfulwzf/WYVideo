package pxxy.wzf.server.mapper;

import org.apache.ibatis.annotations.Param;

public interface MySummaryMapper {
    /**
     * @auther: 王智芳
     * @Description 统计总时长
     * @date: 2021/4/10 21:45
     */
    int updateTime(@Param("summaryId") Long summaryId);
}
