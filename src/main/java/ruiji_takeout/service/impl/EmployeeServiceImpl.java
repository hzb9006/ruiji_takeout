package ruiji_takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.mapper.EmployeeMapper;
import generator.pojo.Employee;
import org.springframework.stereotype.Service;
import ruiji_takeout.service.EmployeeService;

/**
* @author 大饼干
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2022-10-13 11:29:15
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService {

}




