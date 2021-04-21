package pxxy.wzf.business.rest.controller;

import com.github.yitter.idgen.YitIdHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/summary")
@Api(tags = "视频总览接口")
public class SummaryController {

    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(SummaryController.class);

    /**
     * 操作名称
     */
    public static final String BUSINESS_NAME = "视频总览";

    @Autowired
    private SummaryService summaryService;

    /**
     * @auther: 王智芳
     * @Description 视频概览列表
     * @date: 2021/4/5 9:22
     */
    @RequestMapping(value = "/list",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "分类列表",httpMethod = "POST",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest<Page<SummaryVO>> list(@RequestBody @ApiParam(value = "分页参数") PageQuery pageQuery){
        Rest<Page<SummaryVO>> rest = new Rest<>();
        //对分页参数进行判断
        if(pageQuery.getPageNo()==null||pageQuery.getPageNo()<0){
            return rest.resultFail("分页参数出差");
        }
        if(pageQuery.getPageSize()==null||pageQuery.getPageSize()<0){
            return rest.resultFail("分页参数出差");
        }
        PageParams pageParams = new PageParams(pageQuery.getPageNo(),pageQuery.getPageSize());
        List<SummaryDto> list = summaryService.list(pageParams);
        if(CollectionUtils.isEmpty(list)){
            return rest.resultSuccess("列表为空");
        }
        List<SummaryVO> collect = list.stream().map(summaryDto -> CopierUtil.copyProperties(summaryDto, new SummaryVO())).collect(Collectors.toList());

        Page<SummaryVO> voPage = new Page<>(pageQuery.getPageNo(),pageQuery.getPageSize(),summaryService.totalRecord(),collect);
        return rest.resultSuccessInfo(voPage);
    }

    /**
     * @auther: 王智芳
     * @Description 新增视频概览
     * @date: 2021/4/8 22:47
     */
    @RequestMapping(value = "/add",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "分类列表",httpMethod = "POST",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest save(@RequestBody @Validated SummaryVO summaryVO){
        Rest rest = new Rest();
        summaryVO.setId(YitIdHelper.nextId());
        summaryService.add(CopierUtil.copyProperties(summaryVO,new SummaryDto()));
        return rest.resultSuccess("新增成功");
    }

    /**
     * @auther: 王智芳
     * @Description 修改信息
     * @date: 2021/4/8 22:47
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改概览信息",httpMethod = "POST",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest update(@RequestBody @ApiParam(value = "概览信息VO") SummaryVO summaryVO){
        Rest rest = new Rest();
        //入参判断
        if(summaryVO.getId()==null){
            return rest.resultFail("id不能为空");
        }
        summaryService.update(CopierUtil.copyProperties(summaryVO,new SummaryDto()));
        return rest.resultSuccess("修改成功");
    }

    /**
     * @auther: 王智芳
     * @Description 删除概览信息
     * @date: 2021/4/21 17:44
     */
    @RequestMapping(value = "/delete/{id}",method = {RequestMethod.GET},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除概览信息",httpMethod = "GET",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest delete(@PathVariable @ApiParam(value = "概览id") Long id){
        Rest rest = new Rest();
        if(id==null){
            return rest.resultFail("id不能为空");
        }
        summaryService.delete(id);
        return rest.resultSuccess("删除成功");
    }
}
