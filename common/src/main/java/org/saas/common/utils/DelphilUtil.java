package org.saas.common.utils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * @author yuefeng.liu
 * @deprecated 识别验证码
 */
public class DelphilUtil {

    public static void main(String[] args) {
        System.out.println(getCode("http://chn.coolsdaq.co/captcha/image/48f43ec9c280a4b3618f2f02e1b0f0ef0eed9817/"));
    }

    /**
     * 获取验证码
     * @param imgUrl
     * @return
     */
    public static String getCode(String imgUrl){
        String host = "https://ali-checkcode.showapi.com";
        String path = "/checkcode";
        String method = "POST";
        String appcode = "7a47f14a9ecb4adca52681ff8c87e068";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("convert_to_jpg", "0");
        bodys.put("img_base64", Base64Util.getImageStrFromUrl(imgUrl));
        bodys.put("typeId", "26");


        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            String s = EntityUtils.toString(response.getEntity());
            Map<String, Object> resultMap = (Map<String, Object>)JSON.parse(s);
            JSONObject resBody = (JSONObject)resultMap.get("showapi_res_body");
            System.out.println("验证码识别成功："+ resBody.get("Result"));
            return (String)resBody.get("Result");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
