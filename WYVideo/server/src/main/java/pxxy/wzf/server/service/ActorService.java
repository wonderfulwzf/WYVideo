package pxxy.wzf.server.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pxxy.wzf.server.domain.Actor;
import pxxy.wzf.server.domain.ActorExample;
import pxxy.wzf.server.dto.ActorDto;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.mapper.ActorMapper;
import pxxy.wzf.server.utils.CopierUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorService {

    @Autowired
    private ActorMapper actorMapper;
    
    /**
     * @auther: 王智芳
     * @Description 列表查询
     * @date: 2021/4/7 21:31
     */
    public List<ActorDto> list(PageParams pageParams){
        PageHelper.startPage(pageParams.getPageNo(),pageParams.getPageSize());
        //查询参数
        ActorExample actorExample = new ActorExample();
        actorExample.setOrderByClause("create_time desc");
        List<Actor> actors = actorMapper.selectByExample(actorExample);
        if(actors==null){
            return Collections.EMPTY_LIST;
        }
        List<ActorDto> actorDtos = actors.stream().map(actor ->
                CopierUtil.copyProperties(actor,new ActorDto())).collect(Collectors.toList());
        return actorDtos;
    }
    
    /**
     * @auther: 王智芳
     * @Description 统计总数
     * @date: 2021/4/7 23:24
     */
    public Long totalRecord(){
        return actorMapper.countByExample(null);
    }

    /**
     * @auther: 王智芳
     * @Description 新增演员
     * @date: 2021/4/8 22:32
     */
    public void add(ActorDto actorDto) {
        Actor actor = new Actor();
        CopierUtil.copyProperties(actorDto,actor);
        actor.setCreateTime(new Date());
        actor.setUpdateTime(new Date());
        actorMapper.insert(actor);
    }

    /**
     * @auther: 王智芳
     * @Description 修改演员信息
     * @date: 2021/4/8 22:34
     */
    public void update(ActorDto actorDto) {
        Actor actor = new Actor();
        CopierUtil.copyProperties(actorDto,actor);
        actor.setUpdateTime(new Date());
        actorMapper.updateByPrimaryKeySelective(actor);
    }

    /**
     * @auther: 王智芳
     * @Description 删除演员信息
     * @date: 2021/4/8 22:39
     */
    public void delete(Long id) {
        actorMapper.deleteByPrimaryKey(id);
    }

    /**
     * @auther: 王智芳
     * @Description 查询所有主演
     * @date: 2021/4/11 16:46
     */
    public List<ActorDto> all(){
        //查询参数
        List<Actor> actors = actorMapper.selectByExample(null);
        if(actors==null){
            return Collections.EMPTY_LIST;
        }
        List<ActorDto> actorDtos = actors.stream().map(actor ->
                CopierUtil.copyProperties(actor,new ActorDto())).collect(Collectors.toList());
        return actorDtos;
    }
}
