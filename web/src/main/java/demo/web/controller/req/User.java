package demo.web.controller.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * parent
 * demo.web.controller.req
 * 入参User
 * 属性校验注解参见：
 * https://blog.csdn.net/u011851478/article/details/51842157
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/28 13:29 Thursday
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class User {

    private String id;//用户ID
    //参考：https://www.jianshu.com/p/46eda1f96abe
    @NotNull(message = "{username.isNull}")//错误信息见ValidationMessages.properties文件
    @Length(min = 2, max = 64, message = "昵称长度必须在2-64个字符")
    private String username;//用户名（昵称）

    @Email(message = "邮箱必须合法")
    @NotNull(message = "邮箱不能为空")
    private String email;// 邮箱
}
