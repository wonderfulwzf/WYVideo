package pxxy.wzf.business.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pxxy.wzf.business.rest.vo.common.Page;
import pxxy.wzf.business.rest.vo.common.PageQuery;
import pxxy.wzf.business.rest.vo.common.Rest;
import pxxy.wzf.business.rest.vo.param.CategoryVO;
import pxxy.wzf.server.dto.CategoryDto;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.service.CategoryService;
import pxxy.wzf.server.utils.CopierUtil;
import pxxy.wzf.server.utils.UuidUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "接口")
@RequestMapping("/category")
public class CategoryController {

    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    /**
     * 操作名称
     */
    public static final String BUSINESS_NAME = "种类";

    @Autowired
    private CategoryService categoryService;

    /**
     * @auther: 王智芳
     * @Description 分类列表
     * @date: 2021/4/5 9:22
     */
    @RequestMapping(value = "/list",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "分类列表",httpMethod = "POST",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest<Page<CategoryVO>> list(@RequestBody @ApiParam(value = "分页参数") PageQuery pageQuery){
        Rest<Page<CategoryVO>> rest = new Rest<>();
        //对分页参数进行判断
        if(pageQuery.getPageNo()==null||pageQuery.getPageNo()<0){
            return rest.resultFail("分页参数出差");
        }
        if(pageQuery.getPageSize()==null||pageQuery.getPageSize()<0){
            return rest.resultFail("分页参数出差");
        }
        PageParams pageParams = new PageParams(pageQuery.getPageNo(),pageQuery.getPageSize());
        List<CategoryDto> list = categoryService.list(pageParams);
        if(CollectionUtils.isEmpty(list)){
            return rest.resultSuccess("列表为空");
        }
        //转化
        List<CategoryVO> collect = list.stream().map(categoryDto -> CopierUtil.copyProperties(categoryDto, new CategoryVO())).collect(Collectors.toList());
        Page<CategoryVO> voPage = new Page<>(pageQuery.getPageNo(),pageQuery.getPageSize(),categoryService.totalRecord(),collect);
        return rest.resultSuccessInfo(voPage);
    }

    /**
     * @auther: 王智芳
     * @Description 新增种类
     * @date: 2021/4/8 22:47
     */
    @RequestMapping(value = "/add",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "新增种类",httpMethod = "POST",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest add(@RequestBody @Validated CategoryVO categoryVO){
        Rest rest = new Rest();
        //分配id
        categoryVO.setId(UuidUtil.getShortUuid());
        categoryService.add(CopierUtil.copyProperties(categoryVO,new CategoryDto()));
        return rest.resultSuccess("新增成功");
    }

    /**
     * @auther: 王智芳
     * @Description 修改信息
     * @date: 2021/4/8 22:47
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "新增种类",httpMethod = "POST",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest update(@RequestBody @ApiParam(value = "种类VO") CategoryVO categoryVO){
        Rest rest = new Rest();
        //入参判断
        if(categoryVO.getId()==null){
            return rest.resultFail("id不能为空");
        }
        categoryService.update(CopierUtil.copyProperties(categoryVO,new CategoryDto()));
        return rest.resultSuccess("修改成功");
    }

    /**
     * @auther: 王智芳
     * @Description 删除种类信息
     * @date: 2021/4/21 16:18
     */
    @RequestMapping(value = "/delete/{id}",method = {RequestMethod.GET},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "新增种类",httpMethod = "GET",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest delete(@PathVariable @ApiParam(value = "分类id") String id){
        Rest rest = new Rest();
        if(StringUtils.isEmpty(id)){
            return rest.resultFail("id不能为空");
        }
        categoryService.delete(id);
        return rest.resultSuccess("删除成功");
    }

    /**
     * @description: 查询所有
     * @author wangzhifang
     * @createTime：2021/3/12 20:28
     */
    @RequestMapping(value = "/all",method = {RequestMethod.GET},produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "新增种类",httpMethod = "GET",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Rest<List<CategoryDto>> all(){
        Rest<List<CategoryDto>> rest = new Rest<>();
        return rest.resultSuccessInfo(categoryService.all());
    }
}
