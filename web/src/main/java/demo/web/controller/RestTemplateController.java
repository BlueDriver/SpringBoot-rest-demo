package demo.web.controller;

import demo.service.service.RestTemplateService;
import demo.web.controller.req.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * parent
 * demo.web.controller
 * 请求第三方接口（此处为本项目内的接口），服务依赖service模块
 * https://www.jianshu.com/p/8cc05da7a6a2
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/29 20:56 Friday
 */
@RestController
public class RestTemplateController {
    /**
     * 服务来自service模块
     */
    @Autowired
    private RestTemplateService restTemplateService;

    /**
     * 返回普通文本
     */
    @RequestMapping("/hello")
    public String getHello() {
        return restTemplateService.getStringFromUrl("http://localhost:8080/");
    }

    /**
     * 返回对象
     */
    @RequestMapping("/param11")
    public User getParam1(@RequestBody User user) throws IOException {
        return restTemplateService.postObjectFromUrl("http://localhost:8080/param1", user, User.class);
    }

    /**
     * 获取返回状态码
     */
    @RequestMapping("/code")
    public int getStatus() {
        return restTemplateService.postStatusCode("http://localhost:8080/ex");
    }

}
