package ruiji_takeout.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.modeler.ParameterInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruiji_takeout.common.BaseContext;
import ruiji_takeout.common.R;
import ruiji_takeout.dto.dishDto;
import ruiji_takeout.mapper.DishMapper;
import ruiji_takeout.pojo.Category;
import ruiji_takeout.pojo.Dish;
import ruiji_takeout.pojo.DishFlavor;
import ruiji_takeout.service.CategoryService;
import ruiji_takeout.service.DishFlavorService;
import ruiji_takeout.service.DishService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 大饼干
 * @description 针对表【dish(菜品管理)】的数据库操作Service实现
 * @createDate 2022-10-13 11:29:15
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
        implements DishService {

    @Autowired
    ruiji_takeout.dto.dishDto dishDto;

    @Autowired
    DishFlavorService dishFlavorService;

    @Autowired
    CategoryService categoryService;




    @Override
    public Page<dishDto> pageDish(HttpServletRequest request, int page, int pageSize, String name) {
        // 获取当前登录用户的id，并且线程共享
        BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));

        // 构造分页器
        Page<Dish> pageinfo=new Page<>(page,pageSize);
        Page<dishDto> page1=new Page<>(page,pageSize);// 由于要返回的是dishDto类的，所以此处分页插件的泛型也是dishDto

        // 条件构造器
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name),Dish::getName,name);
        this.page(pageinfo, queryWrapper);

        // 把pageinfo的属性拷贝到page1中，不拷贝records，因为两者类型不一致，不能直接拷贝
        BeanUtils.copyProperties(pageinfo,page1,"records");
        List<dishDto> dishDtoList =new ArrayList<>();

        // 打开Page的源码，我们不难发现，实体数据应该是以List的形式放在records中-- protected List<T> records;
        for (Dish record : pageinfo.getRecords()) {
            dishDto dishDto1=new dishDto();
            // 由于page1的泛型是dishDto，所以List每条数据都是一个dishDto实体,由于dishDto继承了dish，所以也有对应的属性和方法
            Long categoryId = record.getCategoryId();
            // 根据每一个dishDto的属性categoryId到category中查找对应的name，并且赋值给对应的dishDto中的属性
            String categoryName = categoryService.getById(categoryId).getName();
            BeanUtils.copyProperties(record,dishDto1);
            dishDto1.setCategoryName(categoryName);
            dishDtoList.add(dishDto1);

        }
        page1.setRecords(dishDtoList);

        return page1;

    }

    @Override
    public dishDto selectDish(HttpServletRequest request, Long id) {
        // 获取当前登录用户的id
        BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));

        // 查询要修改的dish信息
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getId,id);
        Dish dish = this.getOne(queryWrapper);

        // 拷贝dish的属性到dishdto,此处还差flavor和categoryName
        BeanUtils.copyProperties(dish,dishDto);


        // 由于要返回的还涉及flavor,多表查询，查询dish对应的味道
        LambdaQueryWrapper<DishFlavor> queryWrapper1=new LambdaQueryWrapper<>();
        queryWrapper1.eq(DishFlavor::getDishId,id);
        List<DishFlavor> dishFlavorList = dishFlavorService.list(queryWrapper1);
        dishDto.setFlavors(dishFlavorList);


        return dishDto;

    }

    @Override
    @Transactional// 涉及多表操作，这里需要加入事务控制注解
    public void saveDish(HttpServletRequest request, ruiji_takeout.dto.dishDto dishDto) {
        BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));
        log.info("获取到的dishDto为{}",dishDto);
        // 1. 先保存dish的内容到dish表
        this.save(dishDto);

        // 2. 保存口味信息到dish_flavor表
        for (DishFlavor dishFlavor : dishDto.getFlavors()) {
            // 由于前端返回的口味信息是没有和菜品id对应起来的，所以此处需要对应一下
            Long dishId = dishDto.getId();
            dishFlavor.setDishId(dishId);
        }

        dishFlavorService.saveBatch(dishDto.getFlavors());

    }

    @Override
    public void updateDish(HttpServletRequest request, ruiji_takeout.dto.dishDto dishDto1) {
        BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));
        // 1. 修改dish表中的数据--id是主键
        this.updateById(dishDto1);

        // 2. 修改dish_flavor表的数据
        // 由于id不是主键，所以我们选择清除再插入
        // 清理当前菜品对应的口味数据--dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId,dishDto1.getId());
        dishFlavorService.remove(queryWrapper);

        // 添加当前提交的口味信息--dish_flavor表的insert操作
        // 获取菜品口味

        for (DishFlavor flavor : dishDto.getFlavors()) {
            flavor.setDishId(dishDto1.getId());
        }
        dishFlavorService.saveBatch(dishDto.getFlavors());


    }

    @Override
    public void deleteDish(Long ids) {
        // 之所以要用ids，仅仅只是因为前端传回的参数名为ids
        // 1.根据id删除dish表的数据
        this.removeById(ids);

        // 2. 删除dish_flavor表的数据
        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,ids);
        List<DishFlavor> dishFlavorList = dishFlavorService.list(queryWrapper);
        dishFlavorService.removeBatchByIds(dishFlavorList);
    }
}




