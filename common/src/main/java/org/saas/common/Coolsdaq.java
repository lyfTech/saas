package org.saas.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.ibatis.annotations.Delete;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.saas.common.utils.DelphilUtil;

public class Coolsdaq {

    public static final String URL_DOMAIN = "http://chn.coolsdaq.co";
    public static final String URL_GET_LOGIN = "http://chn.coolsdaq.co/zh-cn/login/";
    public static final String URL_GET_ORDERS = "http://chn.coolsdaq.co/getorders";
    public static final String URL_GET_TRADE = "http://chn.coolsdaq.co/zh-cn/trade/";
    public static final String URL_GET_ENTRIES = "http://chn.coolsdaq.co/api/entries/?format=json";
    public static final String URL_GET_ENTRIES_DELETE = "http://chn.coolsdaq.co/api/entries/";

    public static final String USER_NAME = "xhh2882";//用户名
    public static final String USER_PWD = "huihui020510";//登录密码
    public static final String USER_PAY_PWD = "009430";//二级密码
    public static final int MIN_QTY = 10;//最小币
    public static final int MAX_QTY = 4000;//最大币

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static StringBuffer tmpcookies = new StringBuffer();

    public static void startLogin() throws IOException, InterruptedException {
        Document doc = gotoLoginPage();
        Element body = doc.body();
        String csrf = body.getElementsByAttributeValue("name", "csrfmiddlewaretoken").val();
        String captcha = body.getElementById("id_captcha_0").val();
        String captchaUrl = body.getElementsByClass("captcha").first().getElementsByAttribute("src").attr("src");
        HttpClient client = new HttpClient();
        NameValuePair[] nameValuePairs = {
            new NameValuePair("csrfmiddlewaretoken", csrf),
            new NameValuePair("username", USER_NAME),
            new NameValuePair("password", USER_PWD),
            new NameValuePair("captcha_0", captcha),
            new NameValuePair("captcha_1", DelphilUtil.getCode(URL_DOMAIN.concat(captchaUrl)))
        };
        PostMethod postMethod = new PostMethod(URL_GET_LOGIN);
        postMethod.setRequestHeader("cookie", tmpcookies.toString());
        postMethod.setRequestHeader("Referer", URL_GET_LOGIN);
        postMethod.setRequestHeader("Host", "chn.coolsdaq.co");
        postMethod.setRequestHeader("Origin", "http://chn.coolsdaq.co");
        postMethod.setRequestHeader("User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 "
                + "Safari/537.36");
        postMethod.setRequestHeader("Accept",
            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        postMethod.setRequestBody(nameValuePairs);
        client.executeMethod(postMethod);
        Cookie[] cookies = client.getState().getCookies();
        tmpcookies.setLength(0);
        for (Cookie c : cookies) {
            tmpcookies.append(c.toString() + ";");
        }
        if (tmpcookies.indexOf("sessionid") > -1) {
            printMsg(USER_NAME + "登录成功");
            getOrders();
        }
        printMsg(tmpcookies.toString());

    }

    public static Document gotoLoginPage() throws IOException {
        GetMethod getMethod = new GetMethod(URL_GET_LOGIN);
        HttpClient client = new HttpClient();
        int stats = client.executeMethod(getMethod);
        if (stats == HttpStatus.SC_OK) {
            Cookie[] cookies = client.getState().getCookies();
            for (Cookie c : cookies) {
                tmpcookies.append(c.toString() + ";");
            }
            StringBuffer response = new StringBuffer();
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(getMethod.getResponseBodyAsStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append(System.getProperty("line.separator"));
            }
            reader.close();
            Document doc = Jsoup.parse(response.toString());
            return doc;
        }
        return null;
    }

    public static void getOrders() throws IOException, InterruptedException {
        GetMethod getMethod = new GetMethod(URL_GET_ORDERS);
        HttpClient client = new HttpClient();
        int stats = client.executeMethod(getMethod);
        if (stats == HttpStatus.SC_OK) {
            StringBuffer response = new StringBuffer();
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(getMethod.getResponseBodyAsStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append(System.getProperty("line.separator"));
            }
            reader.close();
            JSONArray array = (JSONArray)JSON.parse(response.toString());
            if (array != null && array.size() > 0) {
                JSONObject jsonObject = (JSONObject)array.get(0);
                List<Integer> tmpQtys = new ArrayList<Integer>();
                JSONObject bgcs = (JSONObject)jsonObject.get("BGC");
                JSONArray buies = (JSONArray)bgcs.get("buy");
                if (buies == null || buies.size() < 1) {
                    printMsg("没合适的买单..继续查找..");
                    getOrders();
                }
                for (int i = 0; i < buies.size(); i++) {
                    JSONObject buy = (JSONObject)buies.get(i);
                    Integer qty = ((BigDecimal)buy.get("qty")).intValue();
                    String price = ((BigDecimal)buy.get("price")).toString();
                    tmpQtys.add(qty);
                    printMsg("【买单】：数量：" + qty + "----价格：" + price);
                }
                Arrays.sort(tmpQtys.toArray());
                Integer[] arr = tmpQtys.toArray(new Integer[tmpQtys.size()]);
                for (int x = 0; x < arr.length - 1; x++) {
                    for (int y = x + 1; y < arr.length; y++) {
                        if (arr[x] < arr[y]) {
                            int temp = arr[x];
                            arr[x] = arr[y];
                            arr[y] = temp;
                        }
                    }
                }
                String prodQty = "";
                String prodPrice = "";
                for (int i = 0; i < buies.size(); i++) {
                    JSONObject buy = (JSONObject)buies.get(i);
                    Integer qty = ((BigDecimal)buy.get("qty")).intValue();
                    String price = ((BigDecimal)buy.get("price")).toString();
                    if (qty.equals(arr[0]) && qty > MIN_QTY && qty < MAX_QTY) {
                        printMsg("【获取最优买单】：数量：" + qty + "----价格：" + price);
                        Document doc = gotoTrade();
                        if (doc != null) {
                            trade(doc, qty.toString(), price);
                            return;
                        }
                    }
                }
                printMsg("没合适的买单..继续查找..");
                getOrders();
            } else {
                getOrders();
            }
        }
    }

    public static Document gotoTrade() throws IOException {
        GetMethod getMethod = new GetMethod(URL_GET_TRADE);
        getMethod.setRequestHeader("cookie", tmpcookies.toString());
        HttpClient client = new HttpClient();
        int stats = client.executeMethod(getMethod);
        if (stats == HttpStatus.SC_OK) {
            Cookie[] cookies = client.getState().getCookies();
            for (Cookie c : cookies) {
                tmpcookies.append(c.toString() + ";");
            }
            StringBuffer response = new StringBuffer();
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(getMethod.getResponseBodyAsStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append(System.getProperty("line.separator"));
            }
            reader.close();
            Document doc = Jsoup.parse(response.toString());
            return doc;
        }
        return null;
    }

    public static void trade(Document doc, String qty, String price) throws IOException, InterruptedException {
        Element body = doc.body();
        String csrf = body.getElementsByAttributeValue("name", "csrfmiddlewaretoken").val();
        String captcha = body.getElementById("id_captcha_0").val();
        String captchaUrl = body.getElementsByClass("captcha").first().getElementsByTag("img").attr("src");
        HttpClient client = new HttpClient();
        NameValuePair[] nameValuePairs = {
            new NameValuePair("csrfmiddlewaretoken", csrf),
            new NameValuePair("action", "S"),
            new NameValuePair("qty", qty),
            new NameValuePair("price", price),
            new NameValuePair("coin", "BGC"),
            new NameValuePair("sec_password", USER_PAY_PWD),
            new NameValuePair("captcha_0", captcha),
            new NameValuePair("captcha_1", DelphilUtil.getCode(URL_DOMAIN.concat(captchaUrl)))
        };
        PostMethod postMethod = new PostMethod(URL_GET_TRADE);
        postMethod.setRequestHeader("cookie", tmpcookies.toString());
        postMethod.setRequestHeader("Referer", URL_GET_TRADE);
        postMethod.setRequestHeader("Host", "chn.coolsdaq.co");
        postMethod.setRequestHeader("Origin", "http://chn.coolsdaq.co");
        postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
        postMethod.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        postMethod.setRequestBody(nameValuePairs);
        int i = client.executeMethod(postMethod);
        printMsg("提交成功....");
        Thread.sleep(10000);
        getEntries();
    }

    public static Boolean getEntries() throws IOException, InterruptedException {
        GetMethod getMethod = new GetMethod(URL_GET_ENTRIES);
        getMethod.setRequestHeader("cookie", tmpcookies.toString());
        HttpClient client = new HttpClient();
        int stats = client.executeMethod(getMethod);
        if (stats == HttpStatus.SC_OK) {
            StringBuffer response = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append(System.getProperty("line.separator"));
            }
            reader.close();
            JSONArray array = (JSONArray)JSON.parse(response.toString());
            if (array != null && array.size() > 0) {
                for (Object obj: array) {
                   JSONObject jsonObj = (JSONObject)obj;
                    Integer id = (Integer)jsonObj.get("id");
                   String status = (String)jsonObj.get("status");
                   if ("O".equals(status)){
                       printMsg("交易未成功..卖单将取消..");
                       DeleteMethod deleteMethod = new DeleteMethod(URL_GET_ENTRIES_DELETE + id);
                       deleteMethod.setRequestHeader("cookie", tmpcookies.toString());
                       client = new HttpClient();
                       int delStats = client.executeMethod(getMethod);
                       if (stats == HttpStatus.SC_NO_CONTENT) {
                           printMsg("卖单取消成功..继续刷单...");
                           getOrders();
                       }
                   }
                }
            }
        }
        return null;
    }

    private static void printMsg(String msg) {
        System.out.println(sdf.format(new Date()) + ":-------" + msg);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        startLogin();
    }
}
