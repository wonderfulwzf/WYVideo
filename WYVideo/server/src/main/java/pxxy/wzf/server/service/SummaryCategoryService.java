package pxxy.wzf.server.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pxxy.wzf.server.domain.SummaryCategory;
import pxxy.wzf.server.domain.SummaryCategoryExample;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.dto.SummaryCategoryDto;
import pxxy.wzf.server.mapper.SummaryCategoryMapper;
import pxxy.wzf.server.utils.CopierUtil;
import pxxy.wzf.server.utils.UuidUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SummaryCategoryService {

    @Autowired
    private SummaryCategoryMapper summaryCategoryMapper;

    /**
     * @auther: 王智芳
     * @Description 列表查询
     * @date: 2021/4/7 21:31
     */
    public List<SummaryCategoryDto> list(PageParams pageParams){
        PageHelper.startPage(pageParams.getPageNo(),pageParams.getPageSize());
        //查询参数
        SummaryCategoryExample summaryCategoryExample = new SummaryCategoryExample();
        List<SummaryCategory> summaryCategorys = summaryCategoryMapper.selectByExample(summaryCategoryExample);
        if(summaryCategorys==null){
            return Collections.EMPTY_LIST;
        }
        List<SummaryCategoryDto> summaryCategoryDtos = summaryCategorys.stream().map(summaryCategory ->
                CopierUtil.copyProperties(summaryCategory,new SummaryCategoryDto())).collect(Collectors.toList());
        return summaryCategoryDtos;
    }

    /**
     * @auther: 王智芳
     * @Description 统计总数
     * @date: 2021/4/7 23:24
     */
    public Long totalRecord(){
        return summaryCategoryMapper.countByExample(null);
    }

    /**
     * @auther: 王智芳
     * @Description 新增概览分类
     * @date: 2021/4/8 22:32
     */
    public void add(SummaryCategoryDto summaryCategoryDto) {
        SummaryCategory summaryCategory = new SummaryCategory();
        CopierUtil.copyProperties(summaryCategoryDto,summaryCategory);
        summaryCategoryMapper.insert(summaryCategory);
    }

    /**
     * @auther: 王智芳
     * @Description 修改概览分类信息
     * @date: 2021/4/8 22:34
     */
    public void update(SummaryCategoryDto summaryCategoryDto) {
        SummaryCategory summaryCategory = new SummaryCategory();
        CopierUtil.copyProperties(summaryCategoryDto,summaryCategory);
        summaryCategoryMapper.updateByPrimaryKeySelective(summaryCategory);
    }

    /**
     * @auther: 王智芳
     * @Description 删除概览分类信息
     * @date: 2021/4/8 22:39
     */
    public void delete(String id) {
        summaryCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量保存种类
     */
    @Transactional
    public void saveBatch(Long summaryId, List<String> ids){
        //先删除再新增
        SummaryCategoryExample example = new SummaryCategoryExample();
        example.createCriteria().andSummaryIdEqualTo(summaryId);
        summaryCategoryMapper.deleteByExample(example);
        for(int i = 0 ;i < ids.size(); i++){
            SummaryCategory summaryCategory = new SummaryCategory();
            summaryCategory.setId(UuidUtil.getShortUuid());
            summaryCategory.setSummaryId(summaryId);
            summaryCategory.setCategoryId(ids.get(i));
            add(CopierUtil.copyProperties(summaryCategory,new SummaryCategoryDto()));
        }
    }

}
