package ruiji_takeout.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ruiji_takeout.common.R;
import ruiji_takeout.pojo.Employee;
import ruiji_takeout.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/employee")
public class LoginController {
    @Autowired
    public  EmployeeService employeeService;

    /**
     * 登录功能
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        return employeeService.login(request, employee);

    }

    /**
     * 退出功能
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        return employeeService.lougout(request);
    }

    /**
     * 完成员工的分页查询功能，注意此处返回要是 R<Page> ，否则不会在前端回显，这是前端页面决定的，R是根据前端写的通用的返回类
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> selecePage(int page,int pageSize,String name){
        return  employeeService.selectPage(page,pageSize,name);
    }



}
