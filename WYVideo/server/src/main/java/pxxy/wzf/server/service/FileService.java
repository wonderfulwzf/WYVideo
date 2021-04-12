package pxxy.wzf.server.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pxxy.wzf.server.domain.File;
import pxxy.wzf.server.domain.FileExample;
import pxxy.wzf.server.dto.FileDto;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.mapper.FileMapper;
import pxxy.wzf.server.utils.CopierUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    /**
     * @auther: 王智芳
     * @Description 列表查询
     * @date: 2021/4/7 21:31
     */
    public List<FileDto> list(PageParams pageParams){
        PageHelper.startPage(pageParams.getPageNo(),pageParams.getPageSize());
        //查询参数
        FileExample fileExample = new FileExample();
        fileExample.setOrderByClause("create_time desc");
        List<File> files = fileMapper.selectByExample(fileExample);
        if(files==null){
            return Collections.EMPTY_LIST;
        }
        List<FileDto> fileDtos = files.stream().map(file ->
                CopierUtil.copyProperties(file,new FileDto())).collect(Collectors.toList());
        return fileDtos;
    }

    /**
     * @auther: 王智芳
     * @Description 统计总数
     * @date: 2021/4/7 23:24
     */
    public Long totalRecord(){
        return fileMapper.countByExample(null);
    }

    /**
     * @auther: 王智芳
     * @Description 新增演员
     * @date: 2021/4/8 22:32
     */
    public void add(FileDto fileDto) {
        File file = new File();
        CopierUtil.copyProperties(fileDto,file);
        file.setCreateTime(new Date());
        file.setUpdateTime(new Date());
        fileMapper.insert(file);
    }

    /**
     * @auther: 王智芳
     * @Description 修改演员信息
     * @date: 2021/4/8 22:34
     */
    public void update(FileDto fileDto) {
        File file = new File();
        CopierUtil.copyProperties(fileDto,file);
        file.setUpdateTime(new Date());
        fileMapper.updateByPrimaryKeySelective(file);
    }

    /**
     * @auther: 王智芳
     * @Description 删除演员信息
     * @date: 2021/4/8 22:39
     */
    public void delete(String id) {
        fileMapper.deleteByPrimaryKey(id);
    }
}
