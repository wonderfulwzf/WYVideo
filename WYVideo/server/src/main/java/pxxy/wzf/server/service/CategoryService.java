package pxxy.wzf.server.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import pxxy.wzf.server.domain.Category;
import pxxy.wzf.server.domain.CategoryExample;
import pxxy.wzf.server.dto.CategoryDto;
import pxxy.wzf.server.dto.PageParams;
import pxxy.wzf.server.mapper.CategoryMapper;
import pxxy.wzf.server.utils.CopierUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @auther: 王智芳
     * @Description 列表查询
     * @date: 2021/4/7 21:31
     */
    public List<CategoryDto> list(PageParams pageParams){
        PageHelper.startPage(pageParams.getPageNo(),pageParams.getPageSize());
        //查询参数
        CategoryExample categoryExample = new CategoryExample();
        List<Category> categorys = categoryMapper.selectByExample(categoryExample);
        if(categorys==null){
            return Collections.EMPTY_LIST;
        }
        List<CategoryDto> categoryDtos = categorys.stream().map(category ->
                CopierUtil.copyProperties(category,new CategoryDto())).collect(Collectors.toList());
        return categoryDtos;
    }

    /**
     * @auther: 王智芳
     * @Description 统计总数
     * @date: 2021/4/7 23:24
     */
    public Long totalRecord(){
        return categoryMapper.countByExample(null);
    }

    /**
     * @auther: 王智芳
     * @Description 新增种类
     * @date: 2021/4/8 22:32
     */
    public void add(CategoryDto categoryDto) {
        Category category = new Category();
        CopierUtil.copyProperties(categoryDto,category);
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        categoryMapper.insert(category);
    }

    /**
     * @auther: 王智芳
     * @Description 修改种类信息
     * @date: 2021/4/8 22:34
     */
    public void update(CategoryDto categoryDto) {
        Category category = new Category();
        CopierUtil.copyProperties(categoryDto,category);
        category.setUpdateTime(new Date());
        categoryMapper.updateByPrimaryKeySelective(category);
    }
    
    /**
     * @auther: 王智芳
     * @Description 删除分类
     * @date: 2021/4/11 12:16
     */
    @Transactional
    public void delete(String id) {
        //判断是不是一级分类 是的话删除其子类
        deletechildren(id);
        categoryMapper.deleteByPrimaryKey(id);
    }
    /**
     * @auther: 王智芳
     * @Description 删除分类下的子分类
     * @date: 2021/4/11 12:16
     */
    private void deletechildren(String id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if ("00000000".equals(category.getParent())) {
            CategoryExample categoryExample = new CategoryExample();
            categoryExample.createCriteria().andParentEqualTo(category.getId());
            categoryMapper.deleteByExample(categoryExample);
        }
    }

    /**
     * 查询所有
     */
    public List<CategoryDto> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categorys = categoryMapper.selectByExample(categoryExample);
        if(CollectionUtils.isEmpty(categorys)){
            return Collections.EMPTY_LIST;
        }
        return categorys.stream().map(category -> CopierUtil.copyProperties(category, new CategoryDto())).collect(Collectors.toList());
    }
}
