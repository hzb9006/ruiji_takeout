package ruiji_takeout.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ruiji_takeout.common.R;
import ruiji_takeout.pojo.Category;
import ruiji_takeout.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 分页管理
 */
@Slf4j
@RequestMapping("/category")
@RestController// 建议使用此注解而不是@Controller，防止后续需要人为添加@ResponBody
public class categoryController {
    @Autowired
    public CategoryService categoryService;

    // 菜品分类分页查询
    @GetMapping("/page")
    public R<Page> categoryPage(int page,int pageSize){
        return categoryService.pageCategory(page, pageSize);
    }

    // 新增菜品分类
    @PostMapping
    public R<String> inserCategory(HttpServletRequest request, @RequestBody Category category){
        return categoryService.inserCategory(request,category);

    }

    // 修改分类
    @PutMapping
    public R<String> updateCategory(HttpServletRequest request,@RequestBody Category category){
        return categoryService.updateCategory(request,category);
    }

    // 删除套餐分类或菜品分类
    // todo: 删除套餐导致后续菜品分页查询的时候，找不到对应的套餐id。会报错，优化一下
    @DeleteMapping
    public R<String> deleteCategory(Long ids){
        categoryService.deleteCategory(ids);
        return R.success("删除成功！");
    }

    // 菜品分类
    @GetMapping("/list")
    public R<List<Category>> ListCategory(int type){
        return R.success( categoryService.ListCategory(type));

    }

}
