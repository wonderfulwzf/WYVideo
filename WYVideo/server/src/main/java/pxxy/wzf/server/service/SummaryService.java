package pxxy.wzf.server.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pxxy.wzf.server.domain.Summary;
import pxxy.wzf.server.domain.SummaryExample;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.dto.SummaryDto;
import pxxy.wzf.server.mapper.MySummaryMapper;
import pxxy.wzf.server.mapper.SummaryMapper;
import pxxy.wzf.server.utils.CopierUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SummaryService {

    @Autowired
    private SummaryMapper summaryMapper;

    @Autowired
    private MySummaryMapper mySummaryMapper;

    /**
     * @auther: 王智芳
     * @Description 列表查询
     * @date: 2021/4/7 21:31
     */
    public List<SummaryDto> list(PageParams pageParams){
        PageHelper.startPage(pageParams.getPageNo(),pageParams.getPageSize());
        //查询参数
        SummaryExample summaryExample = new SummaryExample();
        List<Summary> summarys = summaryMapper.selectByExample(summaryExample);
        if(summarys==null){
            return Collections.EMPTY_LIST;
        }
        List<SummaryDto> summaryDtos = summarys.stream().map(summary ->
                CopierUtil.copyProperties(summary,new SummaryDto())).collect(Collectors.toList());
        return summaryDtos;
    }

    /**
     * @auther: 王智芳
     * @Description 统计总数
     * @date: 2021/4/7 23:24
     */
    public Long totalRecord(){
        return summaryMapper.countByExample(null);
    }

    /**
     * @auther: 王智芳
     * @Description 新增演员
     * @date: 2021/4/8 22:32
     */
    public void add(SummaryDto summaryDto) {
        Summary summary = new Summary();
        CopierUtil.copyProperties(summaryDto,summary);
        summary.setCreateTime(new Date());
        summary.setUpdateTime(new Date());
        summaryMapper.insert(summary);
    }

    /**
     * @auther: 王智芳
     * @Description 修改演员信息
     * @date: 2021/4/8 22:34
     */
    public void update(SummaryDto summaryDto) {
        Summary summary = new Summary();
        CopierUtil.copyProperties(summaryDto,summary);
        summary.setUpdateTime(new Date());
        summaryMapper.updateByPrimaryKeySelective(summary);
    }

    /**
     * @auther: 王智芳
     * @Description 删除演员信息
     * @date: 2021/4/8 22:39
     */
    public void delete(Long id) {
        summaryMapper.deleteByPrimaryKey(id);
    }

    /**
     * @auther: 王智芳
     * @Description 更新概览总时长
     * @date: 2021/4/10 21:53
     */
    public void updateSummaryTime(Long summary){
        mySummaryMapper.updateTime(summary);
    }
}
