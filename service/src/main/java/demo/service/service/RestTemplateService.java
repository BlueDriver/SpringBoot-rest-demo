package demo.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * parent
 * demo.service.service
 * RestTemplate调用第三方接口（示例中调的是本地web模块中的接口）
 * https://blog.csdn.net/weixin_40461281/article/details/83540604
 * https://www.jianshu.com/p/8cc05da7a6a2
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/29 21:23 Friday
 */
@Service
public class RestTemplateService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取url返回的字符串
     */
    public String getStringFromUrl(String url) {
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * post请求url获得结果
     *
     * @param url           接口地址
     * @param requestParam  请求参数封装的类
     * @param responseClass 返回参数类型
     */
    public <T, K> K postObjectFromUrl(String url, T requestParam, Class<K> responseClass) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(requestParam);
        return postObjectFromUrl(url, json, responseClass);
    }

    /**
     * post请求url获得结果
     *
     * @param url               接口地址
     * @param requestJsonString json字符串格式参数
     * @param responseClass     返回类型
     */
    public <K> K postObjectFromUrl(String url, String requestJsonString, Class<K> responseClass) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);//设置contentType为json
        HttpEntity<String> entity = new HttpEntity<>(requestJsonString, headers);
        return restTemplate.postForObject(url, entity, responseClass);
    }

    /**
     * 获取返回状态码
     */
    public int postStatusCode(String url) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, null, String.class);
        return responseEntity.getStatusCode().value();
    }


}