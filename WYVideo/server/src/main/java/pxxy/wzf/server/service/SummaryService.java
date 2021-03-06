package pxxy.wzf.server.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pxxy.wzf.server.domain.Summary;
import pxxy.wzf.server.domain.SummaryExample;
import pxxy.wzf.server.dto.ActorDto;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.dto.SummaryDto;
import pxxy.wzf.server.dto.VideoDto;
import pxxy.wzf.server.mapper.MySummaryMapper;
import pxxy.wzf.server.mapper.SummaryMapper;
import pxxy.wzf.server.utils.CopierUtil;

import java.util.ArrayList;
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

    @Autowired
    private SummaryCategoryService summaryCategoryService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private VideoService videoService;

    /**
     * @auther: 王智芳
     * @Description 列表查询
     * @date: 2021/4/7 21:31
     */
    public List<SummaryDto> list(PageParams pageParams){
        PageHelper.startPage(pageParams.getPageNo(),pageParams.getPageSize());
        //查询参数
        SummaryExample summaryExample = new SummaryExample();
        //热度倒叙
        summaryExample.setOrderByClause("create_time desc");
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
    @Transactional
    public void add(SummaryDto summaryDto) {
        Summary summary = new Summary();
        CopierUtil.copyProperties(summaryDto,summary);
        summary.setCreateTime(new Date());
        summary.setUpdateTime(new Date());
        summaryMapper.insert(summary);
        //批量保存分类
        summaryCategoryService.saveBatch(summaryDto.getId(),summaryDto.getIds());
    }

    /**
     * @auther: 王智芳
     * @Description 修改演员信息
     * @date: 2021/4/8 22:34
     */
    @Transactional
    public void update(SummaryDto summaryDto) {
        Summary summary = new Summary();
        CopierUtil.copyProperties(summaryDto,summary);
        summary.setUpdateTime(new Date());
        summaryMapper.updateByPrimaryKeySelective(summary);

        //批量保存分类
        summaryCategoryService.saveBatch(summaryDto.getId(),summaryDto.getIds());
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



    //提供给前端的接口
    /**
     * @auther: 王智芳
     * @Description 分页查询最热的视频
     * @date: 2021/4/15 20:19
     */
    public List<SummaryDto> heatVideo(PageParams pageParams){
        PageHelper.startPage(pageParams.getPageNo(),pageParams.getPageSize());
        //查询参数
        SummaryExample summaryExample = new SummaryExample();
        //热度倒叙
        summaryExample.setOrderByClause("heat desc");
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
     * @Description 种类查询列表信息
     * @date: 2021/4/15 22:15
     */
    public List<SummaryDto> listByCategory(SummaryDto summaryDto){
        PageHelper.startPage(summaryDto.getPageNo(),summaryDto.getPageSize());
        List<Summary> summarys = new ArrayList<>();
        if(StringUtils.isEmpty(summaryDto.getCategoryId())){
            summarys = summaryMapper.selectByExample(null);
        }
        else {
             summarys = mySummaryMapper.listByCategory(summaryDto);
        }
        if(CollectionUtils.isEmpty(summarys)){
            return Collections.EMPTY_LIST;
        }
        List<SummaryDto> summaryDtos = summarys.stream().map(summary ->
                CopierUtil.copyProperties(summary,new SummaryDto())).collect(Collectors.toList());
        return summaryDtos;
    }

    /**
     * @auther: 王智芳
     * @Description 根据概览id查询所有关联信息
     * @date: 2021/4/15 22:49
     */
    public SummaryDto findAllMessage(Long id) {
        Summary summary = summaryMapper.selectByPrimaryKey(id);
        if(summary == null){
            return null;
        }
        SummaryDto summaryDto = new SummaryDto();
        CopierUtil.copyProperties(summary,summaryDto);
        // 查找主演信息
        ActorDto actorDto = actorService.selectById(summaryDto.getActorId());
        summaryDto.setActorDto(actorDto);

        // 查找视频信息
        List<VideoDto> videoDtos = videoService.selectBySummaryId(id);
        summaryDto.setVideoDtos(videoDtos);

        return summaryDto;
    }
}
