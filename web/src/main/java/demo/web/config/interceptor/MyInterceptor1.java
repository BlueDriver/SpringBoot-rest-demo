package demo.web.config.interceptor;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * parent
 * demo.web.config.interceptor
 * 自定义拦截器1
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/27 20:15 Wednesday
 */
public class MyInterceptor1 extends HandlerInterceptorAdapter {
    /**
     * 文件上传最大尺寸，此为一次请求所有文件（单个或多个）的总最大尺寸
     */
    private DataSize maxUploadSize = DataSize.parse("2MB");

    /**
     * 预处理回调方法，实现处理器的预处理（如检查登陆），第三个参数为响应的处理器，自定义Controller
     * 返回值：true表示继续流程（如调用下一个拦截器或处理器）；false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，
     * 此时我们需要通过response来产生响应；
     */
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
        //resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        //resp.setHeader("Access-Control-Allow-Headers", "token,Accept,Origin,XRequestedWith,Content-Type,LastModified");
        System.out.println("preHandler1 uri: " + req.getRequestURI());
        //handler为本请求的处理方法，可根据实际需要加入你的业务逻辑
        /*
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            System.out.println("method name: " + method.getName());
            Parameter[] params = method.getParameters();
            System.out.println("param length: " + params.length);
            for (Parameter param : params) {
                System.out.println(param);
            }
        }
        */
        /**
         *  文件上传大小校验
         *  https://my.oschina.net/scjelly/blog/523705
         */
        if (req != null && ServletFileUpload.isMultipartContent(req)) {
            ServletRequestContext ctx = new ServletRequestContext(req);
            long requestSize = ctx.contentLength();
            if (requestSize > maxUploadSize.toBytes()) {
                /**
                 * 将被统一异常处理捕获
                 * @see demo.web.handler.GlobalExceptionHandler#handleMax(MaxUploadSizeExceededException)
                 */
                throw new MaxUploadSizeExceededException(maxUploadSize.toBytes());
            }
        }

        return true;
    }


    /**
     * 后处理回调方法，实现处理器的后处理（但在渲染视图之前），此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，
     * modelAndView也可能为null。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandler1");
    }

    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，如性能监控中我们可以在此记录结束时间并输出消耗时间，还可以进行一些资源清理，
     * 类似于try-catch-finally中的finally，但仅调用处理器执行链中
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion1");
    }
}
