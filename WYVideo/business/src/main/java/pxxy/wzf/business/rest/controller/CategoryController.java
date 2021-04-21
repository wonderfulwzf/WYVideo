package pxxy.wzf.business.rest.controller;

import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("category")
public class CategoryController {

    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    public static final String BUSINESS_NAME = "种类";

    @Autowired
    private CategoryService categoryService;

    /**
     * @auther: 王智芳
     * @Description 列表
     * @date: 2021/4/5 9:22
     */
    @PostMapping("/list")
    public Rest<Page<CategoryVO>> list(@RequestBody PageQuery pageQuery){
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
        if(list==null){
            return rest.resultSuccess("列表为空");
        }
        List<CategoryVO> collect = list.stream().map(categoryDto -> CopierUtil.copyProperties(categoryDto, new CategoryVO())).collect(Collectors.toList());

        Page<CategoryVO> voPage = new Page<>(pageQuery.getPageNo(),pageQuery.getPageSize(),categoryService.totalRecord(),collect);
        return rest.resultSuccessInfo(voPage);
    }

    /**
     * @auther: 王智芳
     * @Description 新增
     * @date: 2021/4/8 22:47
     */
    @PostMapping("/add")
    public Rest add(@RequestBody @Validated CategoryVO categoryVO){
        Rest rest = new Rest();
        categoryVO.setId(UuidUtil.getShortUuid());
        categoryService.add(CopierUtil.copyProperties(categoryVO,new CategoryDto()));
        return rest.resultSuccess("新增成功");
    }

    /**
     * @auther: 王智芳
     * @Description 修改信息
     * @date: 2021/4/8 22:47
     */
    @PostMapping("/update")
    public Rest update(@RequestBody CategoryVO categoryVO){
        Rest rest = new Rest();
        //入参判断
        if(categoryVO.getId()==null){
            return rest.resultFail("id不能为空");
        }
        categoryService.update(CopierUtil.copyProperties(categoryVO,new CategoryDto()));
        return rest.resultSuccess("修改成功");
    }

    @GetMapping("/delete/{id}")
    public Rest delete(@PathVariable String id){
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
    @GetMapping("/all")
    public Rest<List<CategoryDto>> all(){
        Rest<List<CategoryDto>> rest = new Rest<>();
        return rest.resultSuccessInfo(categoryService.all());
    }
}
