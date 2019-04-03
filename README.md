##  **SpringBoot-rest-demo**

> SpringBoot+maven多模块项目常用功能集锦，主要用于rest前后端分离项目，内容取自网络和自身经验，文中如有疏漏还请指正！

本项目SpringBoot版本为`2.1.3.RELEASE`， 代码中会嵌入部分链接作为参考

### 包含示例

1. SpringBoot+maven多模块项目配置（入口启动类`demo.web.WebApplication`）
2. 拦截器的使用`HandlerInterceptorAdapter`
3. 统一异常处理`ControllerAdvice，ExceptionHandler`
4. controller层参数校验（json参数、键值对参数、单个参数）`Validated, Valid`
5. AOP记录日志`Aspect`
6. 跨域访问
7. 文件上传（拦截器控制大小限制）`MultipartFile`
8. 邮件发送（结合JetBrick模板引擎）
9. 异步任务（多线程）`EnableAsync, Async`
10. 计划任务（定时任务）`Scheduled`
11. 第三方接口调用`RestTemplate`
12. 常用配置（见application.properties）
13. 随机验证码生成`VerifyCodeUtils`

### 更多内容，持续更新

---

### 一些注意点：
#### maven模块配置

父pom.xml文件中

```xml
<groupId>demo</groupId>
<artifactId>parent</artifactId>
<version>0.0.1-SNAPSHOT</version>
<name>parent</name>
<!--必须指定为pom-->
<packaging>pom</packaging>
<!--指定子模块-->
<modules>
    <module>web</module>
    <module>service</module>
</modules>
```

子模块pom.xml中

```xml
<!--指定parent-->
<parent>
    <groupId>demo</groupId>
    <artifactId>parent</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <!--上面的三个可以直接copy父pom.xml中的内容-->
    <relativePath>../pom.xml</relativePath>
</parent>
```

详见本项目代码


#### 参数校验

若入参是对象，则给该对象的类加上`@Validated`, 注意`@Valid`不要漏了，否则不会生效

```java
/**
 * 参数校验2，入参为json数据
 * 异常类型：{@link org.springframework.web.bind.MethodArgumentNotValidException}
 */
@PostMapping("/param1")
public User param1(@Valid @RequestBody User user) {
    return user;
}
```

```java
@Validated
@Data
public class User {
    private String id;//用户ID
    
    @NotNull(message = "昵称不能为空")
    @Size(min = 2, max = 64, message = "昵称长度必须在2-64个字符")
    private String username;/
    
    @Email(message = "邮箱必须合法")
    @NotNull(message = "邮箱不能为空")
    private String email;
}
```

如果`User`类上没加`@Validated`，在Controller类上加`@Validated`，效果是一样的，**建议两个地方都加上**，因为针对单个参数的校验，必须在Controller上加 `@Validated`

```java
@RestController
@Validated//校参注解
public class RestfulController {
    //单个参数校验
	@RequestMapping("/param2")
    public Integer param2(@Range(min = 1, max = 100, message = "age必须在1和100之间") @RequestParam Integer age) {
        return age;
    }
}
```
具体情况还是亲自运行看看吧
#### 文件上传

文件上传大小限制为什么用拦截器实现呢？其实SpringBoot自带参数设置可以限制文件大小，如下：

```properties
# 上传文件单个最大值
spring.servlet.multipart.max-file-size=1MB
# 上传文件总最大值
spring.servlet.multipart.max-request-size=10MB
```

但是会有一个问题，就是当上传的文件大小超过限制时，会抛出`MaxUploadSizeExceededException`异常，此异常无法被`ControllerAdvice`捕获，故使用拦截器来判断，若你有什么更好建议，欢迎提出！

#### More

更多内容，持续更新，感谢支持！

