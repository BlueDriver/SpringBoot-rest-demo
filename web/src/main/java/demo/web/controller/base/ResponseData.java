package demo.web.controller.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * parent
 * demo.web.controller.base
 * 统一响应数据
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/01 10:03 Monday
 */
@Data
@Accessors(chain = true)
public class ResponseData {
    /**
     * 返回码
     *
     * @see ResponseCode
     */
    private Integer code;
    /**
     * 说明
     */
    private String msg;
    /**
     * 数据
     */
    private Map<String, Object> data = new HashMap<>();
    /**
     * 扩展数据（根据实际需要赋值）
     */
    private Object ext;

    /**
     * 辅助构造方法
     * 成功返回，剩下的只需要赋值data即可
     */
    public static ResponseData successObj() {
        return successObj(null);
    }

    /**
     * 辅助构造方法，带data设置
     * 成功返回
     */
    public static ResponseData successObj(Map<String, Object> data) {
        ResponseData responseData = new ResponseData();
        responseData.setCode(ResponseCode.SUCCESS);
        responseData.setMsg("处理成功");
        responseData.setData(data == null ? new HashMap<>() : data);
        return responseData;
    }

    /**
     * 设置data
     */
    public ResponseData putData(String key, Object value) {
        this.getData().put(key, value);
        return this;
    }

    /**
     * 辅助构造方法
     * 异常返回，使用默认异常消息
     */
    public static ResponseData exceptionObj(Exception e) {
        return exceptionObj(e, "发生异常：" + e.getMessage());
    }

    /**
     * 辅助构造方法
     * 异常返回，带异常消息
     */
    public static ResponseData exceptionObj(Exception e, String msg) {
        ResponseData responseData = new ResponseData();
        responseData.setCode(ResponseCode.EXCEPTION);
        responseData.setMsg(msg);
        //为便于排查异常，将异常类名赋值为ext
        responseData.setExt(e.getClass().getName());
        return responseData;
    }
}