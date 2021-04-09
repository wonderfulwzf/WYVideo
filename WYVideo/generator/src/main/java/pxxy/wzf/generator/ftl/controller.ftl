package pxxy.wzf.${module}.rest.controller;

import com.github.yitter.idgen.YitIdHelper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pxxy.wzf.business.rest.vo.common.Page;
import pxxy.wzf.business.rest.vo.common.PageQuery;
import pxxy.wzf.business.rest.vo.common.Rest;
import pxxy.wzf.business.rest.vo.param.${Domain}VO;
import pxxy.wzf.server.dto.${Domain}Dto;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.service.${Domain}Service;
import pxxy.wzf.server.utils.CopierUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api("${tableNameCn}接口")
public class ${Domain}Controller {

    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(${Domain}Controller.class);

    public static final String BUSINESS_NAME = "${tableNameCn}";

    @Autowired
    private ${Domain}Service ${domain}Service;

    /**
     * @auther: 王智芳
     * @Description ${tableNameCn}列表
     * @date: 2021/4/5 9:22
     */
    @PostMapping("/list")
    public Rest<Page<${Domain}VO>> list(@RequestBody PageQuery pageQuery){
        Rest<Page<${Domain}VO>> rest = new Rest<>();
        //对分页参数进行判断
        if(pageQuery.getPageNo()==null||pageQuery.getPageNo()<0){
            return rest.resultFail("分页参数出差");
        }
        if(pageQuery.getPageSize()==null||pageQuery.getPageSize()<0){
            return rest.resultFail("分页参数出差");
        }
        PageParams pageParams = new PageParams(pageQuery.getPageNo(),pageQuery.getPageSize());
        List<${Domain}Dto> list = ${domain}Service.list(pageParams);
        if(list==null){
            return rest.resultSuccess("${tableNameCn}列表为空");
        }
        List<${Domain}VO> collect = list.stream().map(${domain}Dto -> CopierUtil.copyProperties(${domain}Dto, new ${Domain}VO())).collect(Collectors.toList());

        Page<${Domain}VO> voPage = new Page<>(pageQuery.getPageNo(),pageQuery.getPageSize(),${domain}Service.totalRecord(),collect);
        return rest.resultSuccessInfo(voPage);
    }

    /**
     * @auther: 王智芳
     * @Description 新增${tableNameCn}
     * @date: 2021/4/8 22:47
     */
    @PostMapping("/add")
    public Rest save(@RequestBody @Validated ${Domain}VO ${domain}VO){
        Rest rest = new Rest();
        ${domain}VO.setId(YitIdHelper.nextId());
        ${domain}Service.add(CopierUtil.copyProperties(${domain}VO,new ${Domain}Dto()));
        return rest.resultSuccess("新增${tableNameCn}成功");
    }

    /**
     * @auther: 王智芳
     * @Description 修改${tableNameCn}信息
     * @date: 2021/4/8 22:47
     */
    @PostMapping("/update")
    public Rest update(@RequestBody ${Domain}VO ${domain}VO){
        Rest rest = new Rest();
        //入参判断
        if(${domain}VO.getId()==null){
            return rest.resultFail("${tableNameCn}id不能为空");
        }
        ${domain}Service.update(CopierUtil.copyProperties(${domain}VO,new ${Domain}Dto()));
        return rest.resultSuccess("修改${tableNameCn}成功");
    }

    @GetMapping("/delete/{id}")
    public Rest delete(@PathVariable Long id){
        Rest rest = new Rest();
        if(id==null){
            return rest.resultFail("${tableNameCn}id不能为空");
        }
        ${domain}Service.delete(id);
        return rest.resultSuccess("删除${tableNameCn}成功");
    }
}
