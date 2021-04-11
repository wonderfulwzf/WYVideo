package pxxy.wzf.server.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pxxy.wzf.server.domain.SummaryCategory;
import pxxy.wzf.server.domain.SummaryCategoryExample;

public interface SummaryCategoryMapper {
    long countByExample(SummaryCategoryExample example);

    int deleteByExample(SummaryCategoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(SummaryCategory record);

    int insertSelective(SummaryCategory record);

    List<SummaryCategory> selectByExample(SummaryCategoryExample example);

    SummaryCategory selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SummaryCategory record, @Param("example") SummaryCategoryExample example);

    int updateByExample(@Param("record") SummaryCategory record, @Param("example") SummaryCategoryExample example);

    int updateByPrimaryKeySelective(SummaryCategory record);

    int updateByPrimaryKey(SummaryCategory record);
}