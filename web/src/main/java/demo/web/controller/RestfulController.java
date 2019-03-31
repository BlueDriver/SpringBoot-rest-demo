package demo.web.controller;

import demo.web.controller.req.User;
import demo.web.handler.GlobalExceptionHandler;
import demo.web.service.AsyncTaskService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

/**
 * parent
 * demo.web.controller
 * rest测试接口
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/27 21:53 Wednesday
 */
@RestController
@Validated//校参注解
public class RestfulController {
    /**
     * 注入属性值
     */
    @Value("${user}")
    private String user;
    /**
     * 异步任务
     */
    @Autowired
    private AsyncTaskService asyncTaskService;

    @GetMapping("/")
    public String hello() {
        return "Hello! " + user + ", now is " + new Date();
    }

    /**
     * 路径参数
     */
    @RequestMapping("/s/{id}")
    public String id(@PathVariable("id") String id) {
        return id;
    }

    /**
     * 异常处理测试
     * 异常将会被{@link GlobalExceptionHandler}处理
     */
    @GetMapping("/ex")
    public int ex() {
        int a = 2 / 0;
        return a;
    }

    /**
     * 拦截器2
     */
    @GetMapping("/admin")
    public String admin() {
        return "admin: " + user;
    }

    /**
     * 拦截器2
     */
    @GetMapping("/admin/login")
    public String adminLogin() {
        return "adminLogin: " + user;
    }

    /**
     * 异步任务
     */
    @GetMapping("/task")
    public String task() throws InterruptedException {
        asyncTaskService.exec();
        return "task ok";
    }

    /**
     * 抛错的异步任务
     */
    @GetMapping("/task1")
    public String task1() throws Exception {
        asyncTaskService.exec1();
        return "task ok";
    }

    /**
     * 参数校验1，入参为键值对形式
     * http://localhost:8080/param?email=abc@aa.com&username=alice
     * 异常类型：{@link org.springframework.validation.BindException}
     */
    @RequestMapping("/param")
    public User param(@Valid User user) {
        return user;
    }

    /**
     * 参数校验2，入参为json数据
     * 异常类型：{@link org.springframework.web.bind.MethodArgumentNotValidException}
     */
    @PostMapping("/param1")
    public User param1(@Valid @RequestBody User user) {
        return user;
    }

    /**
     * 单个参数校验（键值对）
     * http://localhost:8080/param2?age=0
     * 异常类型：: {@link javax.validation.ConstraintViolationException}
     */
    @RequestMapping("/param2")
    public Integer param2(@Range(min = 1, max = 100, message = "age必须在1和100之间") @RequestParam Integer age) {
        return age;
    }

    /**
     * 跨域测试，见ajax.html
     */
    @RequestMapping("/ajax")
    public String ajax(HttpServletRequest req, HttpServletResponse resp) {
        Cookie cookie = new Cookie("token", System.currentTimeMillis() + "");
        //cookie.setMaxAge(3);
        resp.addCookie(cookie);
        return req.getSession().getId();
    }


}
