package pxxy.wzf.business.web;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pxxy.wzf.business.rest.controller.ActorController;
import pxxy.wzf.business.rest.vo.common.Page;
import pxxy.wzf.business.rest.vo.common.PageQuery;
import pxxy.wzf.business.rest.vo.common.Rest;
import pxxy.wzf.business.rest.vo.param.SummaryVO;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.dto.SummaryDto;
import pxxy.wzf.server.service.SummaryService;
import pxxy.wzf.server.utils.CopierUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/web/summary")
@Api("前端概览接口")
public class SumaryController {
    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(ActorController.class);

    public static final String BUSINESS_NAME = "前端概览";

    @Autowired
    private SummaryService summaryService;

    @PostMapping("/heat")
    public Rest<Page<SummaryVO>> heatVideo(@RequestBody PageQuery pageQuery){
        Rest<Page<SummaryVO>> rest = new Rest<>();
        //对分页参数进行判断
        if(pageQuery.getPageNo()==null||pageQuery.getPageNo()<0){
            return rest.resultFail("分页参数出差");
        }
        if(pageQuery.getPageSize()==null||pageQuery.getPageSize()<0){
            return rest.resultFail("分页参数出差");
        }
        PageParams pageParams = new PageParams(pageQuery.getPageNo(),pageQuery.getPageSize());
        List<SummaryDto> list = summaryService.heatVideo(pageParams);
        if(list==null){
            return rest.resultSuccess("列表为空");
        }
        List<SummaryVO> collect = list.stream().map(summaryDto -> CopierUtil.copyProperties(summaryDto, new SummaryVO())).collect(Collectors.toList());

        Page<SummaryVO> voPage = new Page<>(pageQuery.getPageNo(),pageQuery.getPageSize(),summaryService.totalRecord(),collect);
        return rest.resultSuccessInfo(voPage);
    }

    /**
     * @auther: 王智芳
     * @Description 根据分类返回列表
     * @date: 2021/4/15 22:32
     */
    @PostMapping("/list_by_category")
    public Rest<Page<SummaryDto>> heatVideo(@RequestBody SummaryDto summaryDto){
        Rest<Page<SummaryDto>> rest = new Rest<>();
        //对分页参数进行判断
        if(summaryDto.getPageNo()==null||summaryDto.getPageNo()<0){
            return rest.resultFail("分页参数出差");
        }
        if(summaryDto.getPageSize()==null||summaryDto.getPageSize()<0){
            return rest.resultFail("分页参数出差");
        }
        List<SummaryDto> list = summaryService.listByCategory(summaryDto);
        if(list==null){
            return rest.resultSuccess("列表为空");
        }
        Page<SummaryDto> voPage = new Page<>(summaryDto.getPageNo(),summaryDto.getPageSize(),summaryService.totalRecord(),list);
        return rest.resultSuccessInfo(voPage);
    }
    
}
