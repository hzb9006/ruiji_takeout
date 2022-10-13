package ruiji_takeout.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import ruiji_takeout.common.R;
import ruiji_takeout.mapper.EmployeeMapper;
import ruiji_takeout.pojo.Employee;
import ruiji_takeout.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 大饼干
 * @description 针对表【employee(员工信息)】的数据库操作Service实现
 * @createDate 2022-10-13 11:29:15
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
        implements EmployeeService {


    /**
     * 登录功能
     * @param request
     * @param employee
     * @return
     */
    @Override
    public R<Employee> login(HttpServletRequest request, Employee employee) {
        // 1.获取页面提交的用户名和密码
        String employeeName = employee.getUsername();
        String employeePassword = employee.getPassword();

        //2. 对密码进行加密
        String digestPassword = DigestUtils.md5DigestAsHex(employeePassword.getBytes());

        // 3.进行数据库的查询
        // TODO: 2022/10/13 这里数据库把用户名设为唯一，可以优化，不合理
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employeeName).eq(Employee::getPassword,digestPassword);;
        Employee employee1 = this.getOne(queryWrapper);

        // 查看账户是否存在
        if (employee1==null){
            return R.error("请检查账号密码");
        }

        if (employee1.getStatus()==0){
            return R.error("账户已禁用！");
        }

        // 登录成功
        request.getSession().setAttribute("employee",employee1.getId());
        return R.success(employee1);


    }

    /**
     * 退出功能
     * @param request
     */
    @Override
    public R<String> lougout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
}




