package com.yfc.my.shop.commoms.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * httpClient 工具类
 * <p>title:HttpClientUtils</p>
 * <p>description:</p>
 *
 *@author yfc
 *@version 1.0.0
 *@date 2020/1/6 20:28
*/
public class HttpClientUtils {
    public static final String GET = "get";
    public static final String POST = "post";

    public static final String REQUEST_HEADER_CONNECTION = "keep-alive";
    public static final String REQUEST_HEADER_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36";

    /**
     * GET请求
     * @param url 请求地址
     * @return
     */
    public static String doGet(String url){
        return HttpClientUtils.createRequest(url,GET,null);
    }

    /**
     * GET请求
     * @param url 请求地址
     * @param cookie cookie
     * @return
     */
    public static String doGet(String url,String cookie){
        return HttpClientUtils.createRequest(url,GET,cookie);
    }
    /**
     * POST请求
     * @param url 请求地址
     * @param params 请求参数(可选）
     * @return
     */
    public static String doPost(String url,BasicNameValuePair... params){
        return HttpClientUtils.createRequest(url,POST,null,params);
    }
    /**
     * POST请求
     * @param url 请求地址
     * @param params 请求参数(可选）
     * @param cookie cookie
     * @return
     */
    public static String doPost(String url,String cookie,BasicNameValuePair... params){
        return HttpClientUtils.createRequest(url,POST,cookie,params);
    }

    /**
     *创建请求
     * @param url 请求地址
     * @param requestMethod 请求方式 get/post
     * @param cookie
     * @param params 请求参数，仅仅限于post
     * @return
     */
    public static String createRequest(String url, String requestMethod,String cookie, BasicNameValuePair... params){
        //返回结果
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //请求方式
        HttpGet httpGet = null;
        HttpPost httpPost = null;
        //响应
        CloseableHttpResponse httpResponse = null;
        try {
            //get请求
            if(GET.equals(requestMethod)){
                httpGet = new HttpGet(url);
                httpGet.setHeader("Connection",REQUEST_HEADER_CONNECTION);
                httpGet.setHeader("Cookie",cookie);
                httpGet.setHeader("User-Agent",REQUEST_HEADER_USER_AGENT);
                httpResponse = httpClient.execute(httpGet);
            }
            //post请求
            else if(POST.equals(requestMethod)){
                httpPost = new HttpPost(url);
                httpPost.setHeader("Connection",REQUEST_HEADER_CONNECTION);
                httpPost.setHeader("Cookie",cookie);
                httpPost.setHeader("User-Agent",REQUEST_HEADER_USER_AGENT);
                //有参数进来
                if(params!=null&& params.length>0){
                    httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(params),"UTF-8"));
                }
                httpResponse = httpClient.execute(httpPost);
            }
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(httpClient!=null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
