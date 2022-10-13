package ruiji_takeout.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ruiji_takeout.common.BaseContext;
import ruiji_takeout.common.R;
import ruiji_takeout.mapper.CategoryMapper;
import ruiji_takeout.pojo.Category;
import ruiji_takeout.service.CategoryService;

import javax.naming.Name;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 大饼干
 * @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
 * @createDate 2022-10-13 11:29:15
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    /**
     * 分类管理的分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public R<Page> pageCategory(int page, int pageSize) {
        // 1.构造分页插件
        Page pageCategory =new Page(page,pageSize);
        // 2.查询
        this.page(pageCategory);
        return R.success(pageCategory);
    }

    /**
     * 新增菜品分类
     * @param category
     */
    @Override
    public R<String> inserCategory(HttpServletRequest request,Category category) {
        // 1.获取当前用户的id
        BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));

        // 2.根据前端传回来的菜品名字，查询数据库是否存在
        String categoryName = category.getName();
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getName, categoryName);
        Category category1 = this.getOne(queryWrapper);
        if (category1!=null){
            return R.error("该菜品名已存在");
        }

        // 3. 如果数据库中没有该菜品名，则新增分类
        this.save(category);
        return R.success("新增菜品分类成功！");



    }
}




