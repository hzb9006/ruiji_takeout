package ruiji_takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.mapper.OrderDetailMapper;
import generator.pojo.OrderDetail;
import org.springframework.stereotype.Service;
import ruiji_takeout.service.OrderDetailService;

/**
* @author 大饼干
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-10-13 11:29:15
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService {

}




