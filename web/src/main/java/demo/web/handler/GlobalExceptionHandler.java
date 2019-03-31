package demo.web.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;


/**
 * parent
 * demo.web.handler
 * 全局Runtime异常处理类
 * 可处理来自Interceptor和Controller抛出的异常
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/27 22:23 Wednesday
 */

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    private Map<String, Object> paramHandler(Exception e) {
        Map<String, Object> map = new HashMap<>();
        FieldError error = new FieldError("obj", "field", "default msg");
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            error = ex.getBindingResult().getFieldError();//如果错误不知一个，亲测好象是随机获取一个错误
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            error = ex.getBindingResult().getFieldError();
        }
        map.put("msg", error.getField() + error.getDefaultMessage() + ":" + error.getRejectedValue());
        map.put("type", e.getClass().getName());
        return map;
    }

    /**
     * 单个参数校验
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Map<String, Object> handleBindGetException(ConstraintViolationException ex) {
        log.error("单个参数校验异常", ex);
        Map<String, Object> map = new HashMap<>();
        map.put("type", ex.getClass().getName());
        map.put("msg", ex.getMessage());
        return map;
    }


    /**
     * 文件上传大小超限制
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseBody
    public Map<String, Object> handleMax(MaxUploadSizeExceededException ex) {
        log.error("文件过大", ex);
        Map<String, Object> map = new HashMap<>();
        map.put("type", ex.getClass().getName());
        map.put("msg", ex.getMessage());
        map.put("desc", "文件过大");
        return map;
    }

    /**
     * 默认异常处理，入参需要哪些参数可根据需求而定
     */
    @ExceptionHandler(value = Exception.class)
    private Map<String, Object> defaultExceptionHandler(HttpServletRequest req, HttpServletResponse resp,
                                                        HttpSession session, Exception e) {
        log.error("exception handler: ", e);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", e.getMessage());
        map.put("type", e.getClass().getName());
        return map;
    }


    /**
     *  处理自定义异常必须继承自RuntimeException
     */

}
