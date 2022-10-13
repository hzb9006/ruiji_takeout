package ruiji_takeout.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ruiji_takeout.mapper.CategoryMapper;
import ruiji_takeout.pojo.Category;
import ruiji_takeout.service.CategoryService;

/**
 * @author 大饼干
 * @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
 * @createDate 2022-10-13 11:29:15
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

}




