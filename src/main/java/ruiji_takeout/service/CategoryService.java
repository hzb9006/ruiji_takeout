package ruiji_takeout.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ruiji_takeout.common.R;
import ruiji_takeout.pojo.Category;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 大饼干
 * @description 针对表【category(菜品及套餐分类)】的数据库操作Service
 * @createDate 2022-10-13 11:29:15
 */
public interface CategoryService extends IService<Category> {

    R<Page> pageCategory(int page, int pageSize);

    /**
     * @param request
     * @param category
     */
    R<String> inserCategory(HttpServletRequest request,Category category);
}
