package pxxy.wzf.business.rest.controller;

import com.github.yitter.idgen.YitIdHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import pxxy.wzf.business.rest.vo.common.Page;
import pxxy.wzf.business.rest.vo.common.Rest;
import pxxy.wzf.business.rest.vo.param.VideoVO;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.dto.VideoDto;
import pxxy.wzf.server.service.VideoService;
import pxxy.wzf.server.utils.CopierUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/video")
@Api(tags = "视频接口")
public class VideoController {

    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(VideoController.class);

    /**
     * 操作名称
     */
    public static final String BUSINESS_NAME = "视频";

    @Autowired
    private VideoService videoService;

    /**
     * @auther: 王智芳
     * @Description 视频列表
     * @date: 2021/4/5 9:22
     */
    @RequestMapping(value = "/list",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "视频列表",httpMethod = "POST",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest<Page<VideoVO>> list(@RequestBody VideoVO videoVO){
        Rest<Page<VideoVO>> rest = new Rest<>();
        //对分页参数进行判断
        if(videoVO.getPageNo()==null||videoVO.getPageNo()<0){
            return rest.resultFail("分页参数出差");
        }
        if(videoVO.getPageSize()==null||videoVO.getPageSize()<0){
            return rest.resultFail("分页参数出差");
        }
        if(videoVO.getSummaryId()==null||videoVO.getSummaryId()==0){
            return rest.resultFail("视频概述出错");
        }
        PageParams pageParams = new PageParams(videoVO.getPageNo(),videoVO.getPageSize());
        List<VideoDto> list = videoService.list(pageParams,videoVO.getSummaryId());
        if(CollectionUtils.isEmpty(list)){
            return rest.resultSuccess("列表为空");
        }
        List<VideoVO> collect = list.stream().map(videoDto -> CopierUtil.copyProperties(videoDto, new VideoVO())).collect(Collectors.toList());
        Page<VideoVO> voPage = new Page<>(videoVO.getPageNo(),videoVO.getPageSize(),videoService.totalRecord(),collect);
        return rest.resultSuccessInfo(voPage);
    }

    /**
     * @auther: 王智芳
     * @Description 新增视频
     * @date: 2021/4/8 22:47
     */
    @RequestMapping(value = "/add",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "新增视频内容",httpMethod = "POST",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest save(@RequestBody VideoVO videoVO){
        Rest rest = new Rest();
        videoVO.setId(YitIdHelper.nextId());
        videoService.add(CopierUtil.copyProperties(videoVO,new VideoDto()));
        return rest.resultSuccess("新增成功");
    }

    /**
     * @auther: 王智芳
     * @Description 修改视频信息
     * @date: 2021/4/8 22:47
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改视频内容",httpMethod = "POST",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest update(@RequestBody VideoVO videoVO){
        Rest rest = new Rest();
        //入参判断
        if(videoVO.getId()==null){
            return rest.resultFail("id不能为空");
        }
        videoService.update(CopierUtil.copyProperties(videoVO,new VideoDto()));
        return rest.resultSuccess("修改成功");
    }

    /**
     * @auther: 王智芳
     * @Description 删除视频
     * @date: 2021/4/10 9:35
     */
    @RequestMapping(value = "/delete/{id}",method = {RequestMethod.GET},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除视频内容",httpMethod = "GET",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest delete(@PathVariable @ApiParam(value = "视频id") Long id){
        Rest rest = new Rest();
        if(id==null){
            return rest.resultFail("id不能为空");
        }
        videoService.delete(id);
        return rest.resultSuccess("删除成功");
    }
}
