package ruiji_takeout.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import ruiji_takeout.pojo.Dish;
import ruiji_takeout.pojo.DishFlavor;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class dishDto extends Dish {
    // 类类型的属性
    private List<DishFlavor> flavors=new ArrayList<>();
    private String categoryName;
    private Integer copies;

}
