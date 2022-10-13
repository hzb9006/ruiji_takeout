package ruiji_takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ruiji_takeout.common.R;
import ruiji_takeout.pojo.Employee;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 大饼干
 * @description 针对表【employee(员工信息)】的数据库操作Service
 * @createDate 2022-10-13 11:29:15
 */
public interface EmployeeService extends IService<Employee> {
    R<Employee> login(HttpServletRequest request,Employee employee);

    R<String> lougout(HttpServletRequest request);
}
