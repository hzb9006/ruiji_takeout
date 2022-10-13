package ruiji_takeout.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    // 登录
    R<Employee> login(HttpServletRequest request,Employee employee);

    // 退出账号
    R<String> lougout(HttpServletRequest request);

    //员工信息的分页查询
    R<Page> selectPage(int page, int pageSize, String name);

    R<String> inserEmp(HttpServletRequest request, Employee employee);

    R<Employee> updateEmployee(HttpServletRequest request, Long id);

    void saveUpdateEmp(HttpServletRequest request, Employee employee);
}
