package demo.web.controller;

import demo.web.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * parent
 * demo.web.controller
 * 随机验证码图片
 * header相关说明：https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/03 14:47 Wednesday
 */
@RestController
public class VerifyCodeController {
    @Autowired
    private VerifyCodeUtils verifyCodeUtils;
    /**
     * 验证码长度
     */
    private int codeLength = 5;

    /**
     * 生成随机验证码
     * http://localhost:8080/random
     */
    @GetMapping("/random")
    public void randomCode(HttpServletResponse response, HttpSession session) throws IOException {
        String code = verifyCodeUtils.getRandomNum(codeLength);
        //将code存入session，用于后期校验
        session.setAttribute("code", code);
        BufferedImage image = verifyCodeUtils.getImage(code);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setDateHeader(HttpHeaders.EXPIRES, -1L);
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");//http 1.1
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");//http 1.0
        //将图片写入响应输出流
        ImageIO.write(image, "jpg", response.getOutputStream());
    }
}