package demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * parent
 * demo.web.controller
 * 跳转页面
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/29 14:56 Friday
 */
@Controller
public class PageController {
    /**
     * 跳转至demo.html
     */
    @RequestMapping("/demo1")
    public String demoPage() {
        return "demo";
    }
}
