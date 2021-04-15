package pxxy.wzf.server.mapper;

import org.apache.ibatis.annotations.Param;
import pxxy.wzf.server.domain.Summary;
import pxxy.wzf.server.dto.SummaryDto;

import java.util.List;

public interface MySummaryMapper {
    /**
     * @auther: 王智芳
     * @Description 统计总时长
     * @date: 2021/4/10 21:45
     */
    int updateTime(@Param("summaryId") Long summaryId);

    /**
     * @auther: 王智芳
     * @Description 分类查询列表
     * @date: 2021/4/15 22:18
     */
    List<Summary> listByCategory(@Param("summaryDto") SummaryDto summaryDto);
}
