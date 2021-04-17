package pxxy.wzf.system.rest.controller;


import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.dto.UserDto;
import pxxy.wzf.server.service.UserService;
import pxxy.wzf.server.utils.CopierUtil;
import pxxy.wzf.system.rest.vo.common.Page;
import pxxy.wzf.system.rest.vo.common.PageQuery;
import pxxy.wzf.system.rest.vo.common.Rest;
import pxxy.wzf.system.rest.vo.param.UserVO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api("用户接口")
@RequestMapping("/user")
public class UserController {

    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    public static final String BUSINESS_NAME = "用户";

    @Autowired
    private UserService userService;

    /**
     * @auther: 王智芳
     * @Description 列表
     * @date: 2021/4/5 9:22
     */
    @PostMapping("/list")
    public Rest<Page<UserVO>> list(@RequestBody PageQuery pageQuery){
        Rest<Page<UserVO>> rest = new Rest<>();
        //对分页参数进行判断
        if(pageQuery.getPageNo()==null||pageQuery.getPageNo()<0){
            return rest.resultFail("分页参数出差");
        }
        if(pageQuery.getPageSize()==null||pageQuery.getPageSize()<0){
            return rest.resultFail("分页参数出差");
        }
        PageParams pageParams = new PageParams(pageQuery.getPageNo(),pageQuery.getPageSize());
        List<UserDto> list = userService.list(pageParams);
        if(list==null){
            return rest.resultSuccess("列表为空");
        }
        List<UserVO> collect = list.stream().map(userDto -> CopierUtil.copyProperties(userDto, new UserVO())).collect(Collectors.toList());

        Page<UserVO> voPage = new Page<>(pageQuery.getPageNo(),pageQuery.getPageSize(),userService.totalRecord(),collect);
        return rest.resultSuccessInfo(voPage);
    }

    /**
     * @auther: 王智芳
     * @Description 新增
     * @date: 2021/4/8 22:47
     */
    @PostMapping("/add")
    public Rest save(@RequestBody @Validated UserVO userVO){
        Rest rest = new Rest();
        userService.add(CopierUtil.copyProperties(userVO,new UserDto()));
        return rest.resultSuccess("新增成功");
    }

    /**
     * @auther: 王智芳
     * @Description 修改信息
     * @date: 2021/4/8 22:47
     */
    @PostMapping("/update")
    public Rest update(@RequestBody UserVO userVO){
        Rest rest = new Rest();
        //入参判断
        if(userVO.getId()==null){
            return rest.resultFail("id不能为空");
        }
        userService.update(CopierUtil.copyProperties(userVO,new UserDto()));
        return rest.resultSuccess("修改成功");
    }

    @GetMapping("/delete/{id}")
    public Rest delete(@PathVariable Integer id){
        Rest rest = new Rest();
        if(id==null){
            return rest.resultFail("id不能为空");
        }
        userService.delete(id);
        return rest.resultSuccess("删除成功");
    }
}
