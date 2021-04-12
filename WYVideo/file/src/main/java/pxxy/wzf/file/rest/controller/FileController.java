package pxxy.wzf.file.rest.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pxxy.wzf.file.rest.vo.common.Page;
import pxxy.wzf.file.rest.vo.common.PageQuery;
import pxxy.wzf.file.rest.vo.common.Rest;
import pxxy.wzf.file.rest.vo.param.FileVO;
import pxxy.wzf.server.dto.FileDto;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.service.FileService;
import pxxy.wzf.server.utils.CopierUtil;
import pxxy.wzf.server.utils.UuidUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    public static final String BUSINESS_NAME = "文件";

    @Autowired
    private FileService fileService;

    /**
     * @auther: 王智芳
     * @Description 文件列表
     * @date: 2021/4/5 9:22
     */
    @PostMapping("/list")
    public Rest<Page<FileVO>> list(@RequestBody PageQuery pageQuery){
        Rest<Page<FileVO>> rest = new Rest<>();
        //对分页参数进行判断
        if(pageQuery.getPageNo()==null||pageQuery.getPageNo()<0){
            return rest.resultFail("分页参数出差");
        }
        if(pageQuery.getPageSize()==null||pageQuery.getPageSize()<0){
            return rest.resultFail("分页参数出差");
        }
        PageParams pageParams = new PageParams(pageQuery.getPageNo(),pageQuery.getPageSize());
        List<FileDto> list = fileService.list(pageParams);
        if(list==null){
            return rest.resultSuccess("文件列表为空");
        }
        List<FileVO> collect = list.stream().map(fileDto -> CopierUtil.copyProperties(fileDto, new FileVO())).collect(Collectors.toList());

        Page<FileVO> voPage = new Page<>(pageQuery.getPageNo(),pageQuery.getPageSize(),fileService.totalRecord(),collect);
        return rest.resultSuccessInfo(voPage);
    }

    /**
     * @auther: 王智芳
     * @Description 新增文件
     * @date: 2021/4/8 22:47
     */
    @PostMapping("/add")
    public Rest save(@RequestBody @Validated FileVO fileVO){
        Rest rest = new Rest();
        fileVO.setId(UuidUtil.getShortUuid());
        fileService.add(CopierUtil.copyProperties(fileVO,new FileDto()));
        return rest.resultSuccess("新增文件成功");
    }

    /**
     * @auther: 王智芳
     * @Description 修改文件信息
     * @date: 2021/4/8 22:47
     */
    @PostMapping("/update")
    public Rest update(@RequestBody FileVO fileVO){
        Rest rest = new Rest();
        //入参判断
        if(fileVO.getId()==null){
            return rest.resultFail("文件id不能为空");
        }
        fileService.update(CopierUtil.copyProperties(fileVO,new FileDto()));
        return rest.resultSuccess("修改文件成功");
    }

    @GetMapping("/delete/{id}")
    public Rest delete(@PathVariable String id){
        Rest rest = new Rest();
        if(id==null){
            return rest.resultFail("文件id不能为空");
        }
        fileService.delete(id);
        return rest.resultSuccess("删除文件成功");
    }
}
