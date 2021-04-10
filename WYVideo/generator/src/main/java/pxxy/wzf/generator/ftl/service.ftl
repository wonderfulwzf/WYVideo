package pxxy.wzf.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pxxy.wzf.server.domain.${Domain};
import pxxy.wzf.server.domain.${Domain}Example;
import pxxy.wzf.server.dto.${Domain}Dto;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.mapper.${Domain}Mapper;
import pxxy.wzf.server.utils.CopierUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ${Domain}Service {

    @Autowired
    private ${Domain}Mapper ${domain}Mapper;

    /**
     * @auther: 王智芳
     * @Description 列表查询
     * @date: 2021/4/7 21:31
     */
    public List<${Domain}Dto> list(PageParams pageParams){
        PageHelper.startPage(pageParams.getPageNo(),pageParams.getPageSize());
        //查询参数
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        List<${Domain}> ${domain}s = ${domain}Mapper.selectByExample(${domain}Example);
        if(${domain}s==null){
            return Collections.EMPTY_LIST;
        }
        List<${Domain}Dto> ${domain}Dtos = ${domain}s.stream().map(${domain} ->
                CopierUtil.copyProperties(${domain},new ${Domain}Dto())).collect(Collectors.toList());
        return ${domain}Dtos;
    }

    /**
     * @auther: 王智芳
     * @Description 统计总数
     * @date: 2021/4/7 23:24
     */
    public Long totalRecord(){
        return ${domain}Mapper.countByExample(null);
    }

    /**
     * @auther: 王智芳
     * @Description 新增演员
     * @date: 2021/4/8 22:32
     */
    public void add(${Domain}Dto ${domain}Dto) {
        ${Domain} ${domain} = new ${Domain}();
        CopierUtil.copyProperties(${domain}Dto,${domain});
        ${domain}.setCreateTime(new Date());
        ${domain}.setUpdateTime(new Date());
        ${domain}Mapper.insert(${domain});
    }

    /**
     * @auther: 王智芳
     * @Description 修改演员信息
     * @date: 2021/4/8 22:34
     */
    public void update(${Domain}Dto ${domain}Dto) {
        ${Domain} ${domain} = new ${Domain}();
        CopierUtil.copyProperties(${domain}Dto,${domain});
        ${domain}.setUpdateTime(new Date());
        ${domain}Mapper.updateByPrimaryKeySelective(${domain});
    }

    /**
     * @auther: 王智芳
     * @Description 删除演员信息
     * @date: 2021/4/8 22:39
     */
    public void delete(Long id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}
