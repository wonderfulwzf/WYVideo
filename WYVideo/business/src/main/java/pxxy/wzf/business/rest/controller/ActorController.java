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
import pxxy.wzf.business.rest.vo.param.ActorVO;
import pxxy.wzf.server.dto.ActorDto;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.service.ActorService;
import pxxy.wzf.server.utils.CopierUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/actor")
@Api(tags = "演员接口")
public class ActorController {

    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(ActorController.class);

    /**
     * 操作名称
     */
    public static final String BUSINESS_NAME = "演员";

    @Autowired
    private ActorService actorService;

    /**
     * @auther: 王智芳
     * @Description 演员列表
     * @date: 2021/4/5 9:22
     */
    @RequestMapping(value = "/list",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "演员列表",httpMethod = "POST",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest<Page<ActorVO>> list(@RequestBody @ApiParam(value = "分页参数") PageQuery pageQuery){
        Rest<Page<ActorVO>> rest = new Rest<>();
        //对分页参数进行判断
        if(pageQuery.getPageNo()==null||pageQuery.getPageNo()<0){
            return rest.resultFail("分页参数出差");
        }
        if(pageQuery.getPageSize()==null||pageQuery.getPageSize()<0){
            return rest.resultFail("分页参数出差");
        }
        PageParams pageParams = new PageParams(pageQuery.getPageNo(),pageQuery.getPageSize());
        List<ActorDto> list = actorService.list(pageParams);
        //非空判断
        if(CollectionUtils.isEmpty(list)){
            return rest.resultFail("演员列表为空");
        }
        List<ActorVO> collect = list.stream().map(actorDto -> CopierUtil.copyProperties(actorDto, new ActorVO())).collect(Collectors.toList());
        Page<ActorVO> voPage = new Page<>(pageQuery.getPageNo(),pageQuery.getPageSize(),actorService.totalRecord(),collect);
        return rest.resultSuccessInfo(voPage);
    }
    
    /**
     * @auther: 王智芳
     * @Description 新增演员
     * @date: 2021/4/8 22:47
     */
    @RequestMapping(value = "/add",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "新增演员",httpMethod = "POST",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest save(@RequestBody @Validated @ApiParam(value = "演员VO对象") ActorVO actorVO){
        Rest rest = new Rest();
        //雪花id
        actorVO.setId(YitIdHelper.nextId());
        actorService.add(CopierUtil.copyProperties(actorVO,new ActorDto()));
        return rest.resultSuccess("新增演员成功");
    }

    /**
     * @auther: 王智芳
     * @Description 修改演员信息
     * @date: 2021/4/8 22:47
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改演员信息",httpMethod = "POST",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest update(@RequestBody @ApiParam(value = "演员VO对象") ActorVO actorVO){
        Rest rest = new Rest();
        //入参判断
        if(actorVO.getId()==null){
            return rest.resultFail("演员id不能为空");
        }
        actorService.update(CopierUtil.copyProperties(actorVO,new ActorDto()));
        return rest.resultSuccess("修改演员成功");
    }

    /**
     * @auther: 王智芳
     * @Description 删除演员信息
     * @date: 2021/4/21 15:31
     */
    @RequestMapping(value = "/delete/{id}",method = {RequestMethod.GET},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除演员信息",httpMethod = "GET",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest delete(@PathVariable @ApiParam(value = "演员id") Long id){
        Rest rest = new Rest();
        if(id==null){
            return rest.resultFail("演员id不能为空");
        }
        actorService.delete(id);
        return rest.resultSuccess("删除演员成功");
    }

    /**
     * @auther: 王智芳
     * @Description 演员全部
     * @date: 2021/4/5 9:22
     */
    @RequestMapping(value = "/all",method = {RequestMethod.GET},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查询全部演员信息",httpMethod = "GET",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest<List<ActorVO>> all(){
        Rest<List<ActorVO>> rest = new Rest<>();
        List<ActorDto> list = actorService.all();
        if(CollectionUtils.isEmpty(list)){
            return rest.resultSuccess("演员列表为空");
        }
        List<ActorVO> collect = list.stream().map(actorDto -> CopierUtil.copyProperties(actorDto, new ActorVO())).collect(Collectors.toList());
        return rest.resultSuccessInfo(collect);
    }
}
