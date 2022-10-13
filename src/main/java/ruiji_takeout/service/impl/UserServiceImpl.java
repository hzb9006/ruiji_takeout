package ruiji_takeout.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ruiji_takeout.mapper.UserMapper;
import ruiji_takeout.pojo.User;
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




