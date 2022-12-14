package ruiji_takeout.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import ruiji_takeout.common.BaseContext;
import ruiji_takeout.common.R;
import ruiji_takeout.mapper.EmployeeMapper;
import ruiji_takeout.pojo.Employee;
import ruiji_takeout.service.EmployeeService;

import javax.jws.WebMethod;
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

    @Override
    public R<Page> selectPage(int page, int pageSize, String name) {
        // 新增分页插件
        Page pageinfo =new Page(page,pageSize);
        // 构造条件查询器
        LambdaQueryWrapper<Employee> queryWrapper =new LambdaQueryWrapper<>();
        // 增加查询条件
        queryWrapper.like(StringUtils.isNotBlank(name),Employee::getName,name);
        // 查询
        this.page(pageinfo,queryWrapper);
        return R.success(pageinfo);

    }

    @Override
    public R<String > inserEmp(HttpServletRequest request, Employee employee) {
        // 1. 获取当前登录用户的id
        Long empId = (Long) request.getSession().getAttribute("employee");

        // 2.获取当前要新增的员工的username
        String username = employee.getUsername();

        // 3. 判断数据库中是否存在相同的username
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,username);
        Employee one = this.getOne(queryWrapper);
        if (one!=null){
            return R.error("不能存在同名的用户");
        }

        // 4. 对公共字段进行填充--使用元数据处理
        // todo:优化：尝试spingmvc的拦截器，然后登录的时候获取id，线程共享，免得每个任务都要获取一次id
        BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));

        // 5. 新增员工--设置初始密码123456，并且对密码进行加密
        employee.setPassword("123456");
        String password = employee.getPassword();
        String handlePassword = DigestUtils.md5DigestAsHex(password.getBytes());
        employee.setPassword(handlePassword);
        this.save(employee);
        return R.success("新增员工成功！");


    }

    @Override
    public R<Employee> updateEmployee(HttpServletRequest request, Long id) {
        // 1. 获取当前登录用户的id
        Long empId = (Long) request.getSession().getAttribute("employee");
        // 2. 根据要修改的用户的id查询数据库
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getId,id);
        this.getOne(queryWrapper);
        // 返回查询到的数据给前端页面，回显
        return R.success(this.getOne(queryWrapper));
    }

    @Override
    public void saveUpdateEmp(HttpServletRequest request, Employee employee) {
        // 1. 获取当前登录用户的id并且设置当前create_user为登录用户的id
        BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));

        // 2.保存修改的用户数据到数据库
        this.updateById(employee);
    }
}




