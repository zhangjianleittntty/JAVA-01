package geektime.coding.zjl.camps.week2.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Date;

/**
 * @Program: zjl-project
 * @ClassName: HttpClientGetNettyService
 * @Author: zhangjl
 * @Date: 2021-01-22 10:33
 * @Description: 通过HTTPClient方式
 */
@Slf4j
public class HttpClientGetNettyService {

    public static void main(String[] args) {
        doHttpClientGetTest();
    }

    public static void doHttpClientGetTest() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();      // 获取Http客户端
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8808/test");          // 创建Get请求
        CloseableHttpResponse response = null;           // 响应

        try {
            log.error("Begin with Httpclient:{}", DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
            response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();        // 获取响应实体
            log.error("The repsonse status:{}",response.getStatusLine());
            //System.out.println("The repsonse status: " + response.getStatusLine());
            if (httpEntity != null) {
                log.error("The response of length:{}",httpEntity.getContentLength());
                log.error("The response of content:{}", EntityUtils.toString(httpEntity));
                //System.out.println("The response of length: " + httpEntity.getContentLength());
                //System.out.println("The response of content: " + EntityUtils.toString(httpEntity));
            }
        } catch (ClientProtocolException e) {
            log.error("The clientprotocol of resource error from to http",e);
        } catch (ParseException e) {
            log.error("The parse of resource error from to http",e);
        } catch (IOException e) {
            log.error("The I/O of resource error from to http",e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("The close of resource error from to http",e);
            }
        }

    }

    public static void doHttpClientPostTest() {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建Post请求
        HttpPost httpPost = new HttpPost("http://localhost:12345/doPostControllerOne");
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
