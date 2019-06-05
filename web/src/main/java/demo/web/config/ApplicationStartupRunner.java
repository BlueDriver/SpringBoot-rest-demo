package demo.web.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * parent
 * demo.web.config
 * Spring Boot的CommandLineRunner接口主要用于实现在应用初始化后，去执行一段代码块逻辑，如初始化数据等
 * 这段初始化代码在整个应用生命周期内只会执行一次。
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/06/05 09:01 Wednesday
 * 参考：https://www.cnblogs.com/chenpi/p/9696310.html
 */
@Component
@Order(1)//如果有多个实现CommandLineRunner的类，指定执行顺序，会从小到大一次执行
public class ApplicationStartupRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.err.println(">>>>>>>>>>>>>>>服务启动后执行，可用户执行初始化数据等操作<<<<<<<<<<<<");
    }
}
