package ruiji_takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.mapper.UserMapper;
import generator.pojo.User;
import org.springframework.stereotype.Service;
import ruiji_takeout.service.UserService;

/**
* @author 大饼干
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-10-13 11:29:15
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




