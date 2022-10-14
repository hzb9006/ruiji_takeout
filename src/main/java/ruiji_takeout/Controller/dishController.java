package ruiji_takeout.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ruiji_takeout.common.BaseContext;
import ruiji_takeout.common.R;
import ruiji_takeout.dto.dishDto;
import ruiji_takeout.pojo.Dish;
import ruiji_takeout.service.DishFlavorService;
import ruiji_takeout.service.DishService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/dish")
@Slf4j
// todo：批量功能后续在做
public class dishController {
    @Autowired
    DishService dishService;

    @Autowired
    dishDto dishDto;


    // 菜品管理的分页查询
    @GetMapping("/page")
    public R<Page> pageDish(HttpServletRequest request,int page , int pageSize, String name){
        Page<dishDto> dishPage = dishService.pageDish(request, page, pageSize, name);
        return R.success(dishPage);

    }

    // 修改菜品信息并且回显
    @GetMapping("/{id}")
    public R<dishDto> selecedish(HttpServletRequest request, @PathVariable Long id){
        dishDto dish = dishService.selectDish(request, id);
        return R.success(dish);
    }

    // 新增菜品-保存
    @PostMapping
    public R<String> saveDish(HttpServletRequest request,@RequestBody dishDto dishDto){
        // 前端返回的是json数据，要把他封装为实体，需要加入注解@RequestBody
        dishService.saveDish(request,dishDto);
        return R.success("保存成功！");

    }

    // 修改菜品
    @PutMapping
    public R<String> updateDish(HttpServletRequest request,@RequestBody dishDto dishDto1){
        dishService.updateDish(request,dishDto1);
        return R.success("修改成功");
    }

    // 删除菜品--同时删除dish和dish_flavor表
    // todo: 优化：如果还有套餐涉及此菜品，则不能删除菜品，应该先删除包含此菜品的套餐
    @DeleteMapping
    public R<String> deleteDish(Long ids){

        dishService.deleteDish(ids);
        return R.success("删除成功");

    }

    // 停售
    @PostMapping("/status/{status}")
    public R<String> stopDish(HttpServletRequest request,@PathVariable int status,Long ids){
        BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));
        // 根据id把对应的dish的status设置为0,由于逻辑较为简单，就在控制层写了
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getId,ids);
        Dish dish = dishService.getOne(queryWrapper);
        dish.setStatus(status);
        dishService.updateById(dish);
        return R.success("停售成功！");

    }




}
