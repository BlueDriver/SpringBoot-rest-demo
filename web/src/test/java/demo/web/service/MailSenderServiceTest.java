package demo.web.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * parent
 * demo.web.service
 * 邮件发送Test
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/31 16:40 Sunday
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailSenderServiceTest {
    @Autowired
    private MailSenderService service;

    @Test
    public void send() {
        service.sendSimple("cpwu@foxmail.com", "hello", "subject");
    }

    @Test
    public void sendHTMLMessage() throws UnsupportedEncodingException, MessagingException {
        service.sendHTMLMessage("cpwu@foxmail.com",
                "<h1>Hello</h1>" +
                        "<p style='color: red;'>abcd</p>",
                "html msg", "cpwu@foxmail.com",
                "BlueDriver");
    }

    @Test
    public void sendTemplateMessage() throws MessagingException, IOException {
        Map<String, Object> param = new HashMap<>();
        param.put("name", "BlueDriver");
        param.put("gender", "male");
        List<String> hobby = new ArrayList<>();
        hobby.add("C");
        hobby.add("C++");
        hobby.add("Java");
        param.put("hobby", hobby);
        service.sendTemplateMessage("cpwu@foxmail.com", param, "templates/template.html",
                "template", "cpwu@foxmail.com", "BlueDriver");
    }
}