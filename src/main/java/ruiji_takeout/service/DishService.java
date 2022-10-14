package ruiji_takeout.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ruiji_takeout.common.R;
import ruiji_takeout.dto.dishDto;
import ruiji_takeout.pojo.Dish;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 大饼干
 * @description 针对表【dish(菜品管理)】的数据库操作Service
 * @createDate 2022-10-13 11:29:15
 */
public interface DishService extends IService<Dish> {

    Page<dishDto> pageDish(HttpServletRequest request, int page, int pageSize, String name);

    dishDto selectDish(HttpServletRequest request, Long id);

    void saveDish(HttpServletRequest request, dishDto dishDto);

    void updateDish(HttpServletRequest request, dishDto dishDto1);

    void deleteDish(Long ids);
}
