package pxxy.wzf.server.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pxxy.wzf.server.domain.User;
import pxxy.wzf.server.domain.UserExample;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.dto.UserDto;
import pxxy.wzf.server.exception.UserException;
import pxxy.wzf.server.mapper.UserMapper;
import pxxy.wzf.server.utils.CopierUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @auther: 王智芳
     * @Description 列表查询
     * @date: 2021/4/7 21:31
     */
    public List<UserDto> list(PageParams pageParams){
        PageHelper.startPage(pageParams.getPageNo(),pageParams.getPageSize());
        //查询参数
        UserExample userExample = new UserExample();
        List<User> users = userMapper.selectByExample(userExample);
        if(users==null){
            return Collections.EMPTY_LIST;
        }
        List<UserDto> userDtos = users.stream().map(user ->
                CopierUtil.copyProperties(user,new UserDto())).collect(Collectors.toList());
        return userDtos;
    }

    /**
     * @auther: 王智芳
     * @Description 统计总数
     * @date: 2021/4/7 23:24
     */
    public Long totalRecord(){
        return userMapper.countByExample(null);
    }

    /**
     * @auther: 王智芳
     * @Description 新增演员
     * @date: 2021/4/8 22:32
     */
    public void add(UserDto userDto) {
        User user = new User();
        CopierUtil.copyProperties(userDto,user);
        userMapper.insert(user);
    }

    /**
     * @auther: 王智芳
     * @Description 修改演员信息
     * @date: 2021/4/8 22:34
     */
    public void update(UserDto userDto) {
        User user = new User();
        CopierUtil.copyProperties(userDto,user);
           userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * @auther: 王智芳
     * @Description 删除演员信息
     * @date: 2021/4/8 22:39
     */
    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * @auther: 王智芳
     * @Description 登录逻辑:根据手机号查询密码，对比
     * @date: 2021/4/17 13:23
     */
    public UserDto login(UserDto userDto){
        //查询参数
        UserExample userExample = new UserExample();
        userExample.createCriteria().andPhoneEqualTo(userDto.getPhone());
        List<User> users = userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(users)){
            throw new UserException(10000);
        }
        User user = users.get(0);
        if(user.getPassword().equals(userDto.getPassword())){
            return CopierUtil.copyProperties(user,userDto);
        }
        return null;
    }
}
