package demo.web.controller.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Email;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotNull(message = "昵称不能为空")
    @Size(min = 2, max = 64, message = "昵称长度必须在2-64个字符")
    private String username;//用户名（昵称）

    @Email(message = "邮箱必须合法")
    @NotNull(message = "邮箱不能为空")
    private String email;// 邮箱
}
