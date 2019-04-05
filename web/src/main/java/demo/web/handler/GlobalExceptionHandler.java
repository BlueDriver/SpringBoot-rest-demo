package demo.web.handler;

import demo.web.controller.base.ResponseCode;
import demo.web.controller.base.ResponseDTO;
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
     * 处理多参校验异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    private ResponseDTO paramHandler(Exception e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(ResponseCode.PARAM_INVALID);
        FieldError error;// = new FieldError("obj", "field", "default msg");
        //入参为对象(json)
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            error = ex.getBindingResult().getFieldError();//如果错误不止一个，亲测好象是随机获取一个错误
        } else {// if (e instanceof BindException) {
            //入参为键值对
            BindException ex = (BindException) e;
            error = ex.getBindingResult().getFieldError();
        }
        responseDTO.setMsg("参数非法：" + error.getDefaultMessage() + ": " +
                "[" + error.getField() + "=" + error.getRejectedValue() + "]");
        responseDTO.setExt(e.getClass().getName());
        return responseDTO;
    }

    /**
     * 单个参数校验（键值对)
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public ResponseDTO handleBindGetException(ConstraintViolationException e) {
        log.error("单个参数校验异常", e);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(ResponseCode.PARAM_INVALID)
                .setMsg("参数非法：" + e.getMessage())
                .setExt(e.getClass().getName());
        return responseDTO;
    }


    /**
     * 文件上传大小超限制
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResponseDTO handleMax(MaxUploadSizeExceededException e) {
        log.error("文件过大", e);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(ResponseCode.PARAM_INVALID)
                .setMsg("上传文件过大")
                .setExt(e.getClass().getName());
        return responseDTO;
    }

    /**
     * 默认异常处理，入参需要哪些参数可根据需求而定
     */
    @ExceptionHandler(value = Exception.class)
    private ResponseDTO defaultExceptionHandler(HttpServletRequest req, HttpServletResponse resp,
                                                HttpSession session, Exception e) {
        log.error("exception handler: ", e);
        return ResponseDTO.exceptionObj(e);
    }


    /**
     *  处理自定义异常必须继承自RuntimeException
     */

}
