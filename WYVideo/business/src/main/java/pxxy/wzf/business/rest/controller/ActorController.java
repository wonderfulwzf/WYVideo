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
import pxxy.wzf.business.rest.vo.param.ActorVO;
import pxxy.wzf.server.dto.ActorDto;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.service.ActorService;
import pxxy.wzf.server.utils.CopierUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/actor")
@Api("演员接口")
public class ActorController {

    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(ActorController.class);

    public static final String BUSINESS_NAME = "演员";

    @Autowired
    private ActorService actorService;

    /**
     * @auther: 王智芳
     * @Description 演员列表
     * @date: 2021/4/5 9:22
     */
    @PostMapping("/list")
    public Rest<Page<ActorVO>> list(@RequestBody PageQuery pageQuery){
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
        if(list==null){
            return rest.resultSuccess("演员列表为空");
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
    @PostMapping("/add")
    public Rest save(@RequestBody @Validated ActorVO actorVO){
        Rest rest = new Rest();
        actorVO.setId(YitIdHelper.nextId());
        actorService.add(CopierUtil.copyProperties(actorVO,new ActorDto()));
        return rest.resultSuccess("新增演员成功");
    }

    /**
     * @auther: 王智芳
     * @Description 修改演员信息
     * @date: 2021/4/8 22:47
     */
    @PostMapping("/update")
    public Rest update(@RequestBody ActorVO actorVO){
        Rest rest = new Rest();
        //入参判断
        if(actorVO.getId()==null){
            return rest.resultFail("演员id不能为空");
        }
        actorService.update(CopierUtil.copyProperties(actorVO,new ActorDto()));
        return rest.resultSuccess("修改演员成功");
    }

    @GetMapping("/delete/{id}")
    public Rest delete(@PathVariable Long id){
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
    @GetMapping("/all")
    public Rest<List<ActorVO>> all(){
        Rest<List<ActorVO>> rest = new Rest<>();
        List<ActorDto> list = actorService.all();
        if(list==null){
            return rest.resultSuccess("演员列表为空");
        }
        List<ActorVO> collect = list.stream().map(actorDto -> CopierUtil.copyProperties(actorDto, new ActorVO())).collect(Collectors.toList());
        return rest.resultSuccessInfo(collect);
    }
}
