package com.wzj.learn.autotest.utils;

import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Http 工具类
 * <p>
 * Created by wangzhenjiang on 6/10/17.
 */
public class HttpUtil {

    private static RequestConfig requestConfig;
    private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

    static {
        poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 200
        poolingHttpClientConnectionManager.setMaxTotal(200);
        // Increase default max connection per route to 20
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20);
        // Increase max connections for localhost to 50
        poolingHttpClientConnectionManager.setMaxPerRoute(new HttpRoute(new HttpHost("localhost")), 50);

        requestConfig = RequestConfig.custom()
                .setSocketTimeout(2000)//
                .setConnectTimeout(2000)
                .setConnectionRequestTimeout(2000)
                .setCookieSpec(CookieSpecs.DEFAULT)
                .build();
    }

    public static String doGet(String url, CookieStore cookieStore) throws IOException {
        return doGet(url, null, cookieStore);
    }

    public static String doGet(String url, Map<String, String> headers, CookieStore cookieStore) throws IOException {
        HttpClient httpClient = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        if (headers != null && headers.size() > 0) {
            headers.forEach((K, V) -> httpGet.setHeader(K, V));
        }
        HttpResponse httpResponse = httpClient.execute(httpGet);
        //将cookie保存到cookieStore中，以备下一次请求使用
        saveCookies(cookieStore, httpResponse);
        //TODO#####################测试代码#################
        System.out.println("Response Status: " + httpResponse.getStatusLine());
        // Header[] resHeaders = httpResponse.getAllHeaders();
        Header[] cookieHeaders = httpResponse.getHeaders("Set-Cookie");
        System.out.println("Response Headers: " + Arrays.toString(cookieHeaders));
        //TODO#####################测试代码#################
        return EntityUtils.toString(httpResponse.getEntity(), "utf-8");
    }

    public static String doPost(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        return doPost(url, params, headers, null);
    }

    public static String doPost(String url, Map<String, String> params, Map<String, String> headers, CookieStore cookieStore) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (params != null && params.size() > 0) {
            List<NameValuePair> nameValuePairs = new ArrayList<>(params.size());
            params.forEach((K, V) -> nameValuePairs.add(new BasicNameValuePair(K, V)));
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
//            urlEncodedFormEntity.setContentEncoding("utf-8");
//            urlEncodedFormEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(urlEncodedFormEntity);
        }
        if (headers != null && headers.size() > 0) {
            headers.forEach((K, V) -> httpPost.setHeader(K, V));
        }
        HttpClient httpClient = url.startsWith("https")
                ? cookieStore == null ? getHttpsClient() : getHttpsClient(cookieStore)
                : cookieStore == null ? getHttpClient() : getHttpClient(cookieStore);
//        httpPost.setConfig(requestConfig);
//        httpClient.getParams().setParameter("http.useragent", "Custom Browser");
//        httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        saveCookies(cookieStore, httpResponse);
        //TODO#####################测试代码#################
        System.out.println("Response Status: " + httpResponse.getStatusLine());
        // Header[] resHeaders = httpResponse.getAllHeaders();
        Header[] cookieHeaders = httpResponse.getHeaders("Set-Cookie");
        System.out.println("Response Headers: " + Arrays.toString(cookieHeaders));
        //TODO#####################测试代码#################
        String response = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
        EntityUtils.consume(httpResponse.getEntity());
        return response;
    }

    private static void saveCookies(CookieStore cookieStore, HttpResponse httpResponse) {
        Header[] cookieHeaders = httpResponse.getHeaders("Set-Cookie");
        if (cookieHeaders != null && cookieHeaders.length > 0) {
            Arrays.stream(cookieHeaders).forEach(header -> {
                String targetCookieStr = header.getValue().split(";")[0];
                String[] targetCookieArray = targetCookieStr.split("=", 2);
                System.out.println("targetCookieArray: " + Arrays.toString(targetCookieArray));
                cookieStore.addCookie(new BasicClientCookie(targetCookieArray[0], targetCookieArray[1]));
            });
        }
    }


    public static CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    public static CloseableHttpClient getHttpClient(CookieStore cookieStore) {
        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setDefaultCookieStore(cookieStore)
                .build();
    }

    public static CloseableHttpClient getHttpsClient() {
        return HttpClients.custom()
                .setSSLSocketFactory(createSSLSocketFactory())
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    public static CloseableHttpClient getHttpsClient(CookieStore cookieStor) {
        return HttpClients.custom()
                .setSSLSocketFactory(createSSLSocketFactory())
                .setDefaultRequestConfig(requestConfig)
                .setDefaultCookieStore(cookieStor)
                .build();
    }

    private static LayeredConnectionSocketFactory createSSLSocketFactory() {
        SSLContext sslContext = SSLContexts.createSystemDefault();
        return new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
    }
}
