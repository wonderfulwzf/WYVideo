package pxxy.wzf.business.rest.controller;

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
import pxxy.wzf.business.rest.vo.param.SummaryVO;
import pxxy.wzf.server.dto.SummaryDto;
import pxxy.wzf.server.dto.PageParams;
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

    public static final String BUSINESS_NAME = "视频总览";

    @Autowired
    private SummaryService summaryService;

    /**
     * @auther: 王智芳
     * @Description 列表
     * @date: 2021/4/5 9:22
     */
    @PostMapping("/list")
    public Rest<Page<SummaryVO>> list(@RequestBody PageQuery pageQuery){
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
        if(list==null){
            return rest.resultSuccess("列表为空");
        }
        List<SummaryVO> collect = list.stream().map(summaryDto -> CopierUtil.copyProperties(summaryDto, new SummaryVO())).collect(Collectors.toList());

        Page<SummaryVO> voPage = new Page<>(pageQuery.getPageNo(),pageQuery.getPageSize(),summaryService.totalRecord(),collect);
        return rest.resultSuccessInfo(voPage);
    }

    /**
     * @auther: 王智芳
     * @Description 新增
     * @date: 2021/4/8 22:47
     */
    @PostMapping("/add")
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
    @PostMapping("/update")
    public Rest update(@RequestBody SummaryVO summaryVO){
        Rest rest = new Rest();
        //入参判断
        if(summaryVO.getId()==null){
            return rest.resultFail("id不能为空");
        }
        summaryService.update(CopierUtil.copyProperties(summaryVO,new SummaryDto()));
        return rest.resultSuccess("修改成功");
    }

    @GetMapping("/delete/{id}")
    public Rest delete(@PathVariable Long id){
        Rest rest = new Rest();
        if(id==null){
            return rest.resultFail("id不能为空");
        }
        summaryService.delete(id);
        return rest.resultSuccess("删除成功");
    }
}
