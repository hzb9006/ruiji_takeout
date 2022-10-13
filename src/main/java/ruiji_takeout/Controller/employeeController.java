package ruiji_takeout.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ruiji_takeout.common.R;
import ruiji_takeout.pojo.Employee;
import ruiji_takeout.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/employee")
public class employeeController {
    @Autowired
    public  EmployeeService employeeService;

    // 登录功能
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        return employeeService.login(request, employee);

    }

    // 退出功能
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        return employeeService.lougout(request);
    }

    // 完成员工的分页查询功能，注意此处返回要是 R<Page> ，否则不会在前端回显，这是前端页面决定的，R是根据前端写的通用的返回类
    @GetMapping("/page")
    public R<Page> selecePage(int page,int pageSize,String name){
        return  employeeService.selectPage(page,pageSize,name);
    }

    // 新增员工，并且设置初始密码：123456
    @PostMapping
    public R<String> inserEmp(HttpServletRequest request,@RequestBody Employee employee){
        employeeService.inserEmp(request,employee);
        return R.success("新增员工成功");
    }

    // 修改用户信息的步骤一：根据id进行用户查询并且回显给前端页面
    @GetMapping("/{id}")
    public R<Employee> updateEmployee(HttpServletRequest request,@PathVariable Long id){
        return employeeService.updateEmployee(request,id);

    }

    // 修改用户信息的步骤二：根据id更新用户信息--即保存更新后的用户信息，同时这个方法也可执行启用禁用用户，因为都是更新用户的状态
    @PutMapping
    public R<String> saveUpdateEmp(HttpServletRequest request,@RequestBody Employee employee){
        employeeService.saveUpdateEmp(request,employee);
        return R.success("修改员工信息成功");
    }

}
