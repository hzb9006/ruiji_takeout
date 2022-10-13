package ruiji_takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.mapper.OrdersMapper;
import generator.pojo.Orders;
import org.springframework.stereotype.Service;
import ruiji_takeout.service.OrdersService;

/**
* @author 大饼干
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2022-10-13 11:29:15
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService {

}




