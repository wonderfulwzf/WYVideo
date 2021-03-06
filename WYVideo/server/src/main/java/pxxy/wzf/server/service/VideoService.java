package pxxy.wzf.server.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pxxy.wzf.server.domain.Video;
import pxxy.wzf.server.domain.VideoExample;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.dto.VideoDto;
import pxxy.wzf.server.mapper.VideoMapper;
import pxxy.wzf.server.utils.CopierUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private  SummaryService summaryService;

    /**
     * @auther: 王智芳
     * @Description 列表查询
     * @date: 2021/4/7 21:31
     */
    public List<VideoDto> list(PageParams pageParams,Long summaryId){
        PageHelper.startPage(pageParams.getPageNo(),pageParams.getPageSize());
        //查询参数
        VideoExample videoExample = new VideoExample();
        VideoExample.Criteria criteria = videoExample.createCriteria();
        if(summaryId!=null && summaryId > 0){
            criteria.andSummaryIdEqualTo(summaryId);
        }
        List<Video> videos = videoMapper.selectByExample(videoExample);
        if(videos==null){
            return Collections.EMPTY_LIST;
        }
        List<VideoDto> videoDtos = videos.stream().map(video ->
                CopierUtil.copyProperties(video,new VideoDto())).collect(Collectors.toList());
        return videoDtos;
    }

    /**
     * @auther: 王智芳
     * @Description 统计总数
     * @date: 2021/4/7 23:24
     */
    public Long totalRecord(){
        return videoMapper.countByExample(null);
    }

    /**
     * @auther: 王智芳
     * @Description 新增视频
     * @date: 2021/4/8 22:32
     */
    @Transactional
    public void add(VideoDto videoDto) {
        Video video = new Video();
        CopierUtil.copyProperties(videoDto,video);
        video.setCreateTime(new Date());
        video.setUpdateTime(new Date());
        videoMapper.insert(video);
        //更新概览时长
        summaryService.updateSummaryTime(videoDto.getSummaryId());
    }

    /**
     * @auther: 王智芳
     * @Description 修改视频信息
     * @date: 2021/4/8 22:34
     */
    @Transactional
    public void update(VideoDto videoDto) {
        Video video = new Video();
        CopierUtil.copyProperties(videoDto,video);
        video.setUpdateTime(new Date());
        videoMapper.updateByPrimaryKeySelective(video);
        //更新概览时长
        summaryService.updateSummaryTime(videoDto.getSummaryId());
    }

    /**
     * @auther: 王智芳
     * @Description 删除视频信息
     * @date: 2021/4/8 22:39
     */
    public void delete(Long id) {
        videoMapper.deleteByPrimaryKey(id);
    }

    /**
     * @auther: 王智芳
     * @Description 列表查询
     * @date: 2021/4/7 21:31
     */
    public List<VideoDto> selectBySummaryId(Long summaryId){
        if(summaryId == null){
            return Collections.EMPTY_LIST;
        }
        //查询参数
        VideoExample videoExample = new VideoExample();
        VideoExample.Criteria criteria = videoExample.createCriteria();
        criteria.andSummaryIdEqualTo(summaryId);
        List<Video> videos = videoMapper.selectByExample(videoExample);
        if(videos==null){
            return Collections.EMPTY_LIST;
        }
        List<VideoDto> videoDtos = videos.stream().map(video ->
                CopierUtil.copyProperties(video,new VideoDto())).collect(Collectors.toList());
        return videoDtos;
    }
}
