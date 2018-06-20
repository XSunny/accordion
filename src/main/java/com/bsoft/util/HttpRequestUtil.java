package com.bsoft.util;

/**
 * Created by sky on 2016/12/6.
 */

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpRequestUtil {

    /**
     * @param url
     * @return
     */
	public static String httpRequest(String url){
		return httpRequest(url, "", "");
	}
	
    public static String httpRequest(String url, String cookie, String hostname) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpGet httpGet = new HttpGet(url);

        if(!"".equals(hostname)){
            HttpHost proxy = new HttpHost(hostname, 80, "http");
            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
            httpGet.setConfig(config);
        }

        if(!"".equals(cookie)){
            httpGet.addHeader("Cookie",cookie);
        }

        httpGet.addHeader("max-age",String.valueOf(Integer.MAX_VALUE));
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        //httpGet.addHeader("Accept-Charset", "utf-8");ha
        httpGet.addHeader("Accept-Encoding", "gzip,deflate,sdch");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,es;q=0.4");
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
        try {
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);

            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                String content = EntityUtils.toString(entity,"UTF-8");
                return content;
            }
        } catch (IOException e) {

        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    

    /**
     * @param url
     * @param cookie
     * @param hostname
     * @param code
     * @return
     */
    public static byte[] httpRequestDeCodeBytes(String url, String cookie,String hostname,String code) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpGet httpGet = new HttpGet(url);

        if(!"".equals(hostname)){
            HttpHost proxy = new HttpHost(hostname, 80, "http");
            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
            httpGet.setConfig(config);
        }

        if(!"".equals(cookie)){
            httpGet.addHeader("Cookie",cookie);
        }
        httpGet.addHeader("max-age",String.valueOf(Integer.MAX_VALUE));
        httpGet.addHeader("Accept", "application/json;");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,es;q=0.4");
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
        try {
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);

            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                byte[] content = EntityUtils.toByteArray(entity);
                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

   

    /**
     * @param url
     * @param cookie
     * @param hostname
     * @param param
     * @return
     */
    public static String httpRequestPost(String url, String cookie,String hostname,Map<String,String> param) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);

        List <NameValuePair> params = new ArrayList<NameValuePair>();
        for (Map.Entry<String ,String> entry : param.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
//			   System.out.println("key=" + key + " value=" + value);
            params.add(new BasicNameValuePair(key, value));
        }

        if(!"".equals(hostname)){
            HttpHost proxy = new HttpHost(hostname, 80, "http");
            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
            httpPost.setConfig(config);
        }
//
        if(!"".equals(cookie)){
            httpPost.addHeader("Cookie",cookie);
        }
        httpPost.addHeader("max-age",String.valueOf(Integer.MAX_VALUE));
        httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,es;q=0.4");
        httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));

            HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                String content = EntityUtils.toString(entity,"UTF-8");
                return content;
            }
        } catch (IOException e) {

        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    /**
     * @param url
     * @return
     */
    public static String httpRequestPost(String url, String data) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("max-age",String.valueOf(Integer.MAX_VALUE));
        httpPost.addHeader("Accept", "application/json,text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,es;q=0.4");
        httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
        try {
//            httpPost.setEntity(EntityBuilder.create().setContentEncoding("utf-8").setText(new String(data.getBytes(),"utf-8")).build());

            StringEntity jsonEntity = new StringEntity(data,"UTF-8");
            jsonEntity.setContentType("application/json;charset=UTF-8");
            httpPost.setEntity(jsonEntity);
            httpPost.setHeader("Content-type", "application/json;charset=UTF-8");
            HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                String content = EntityUtils.toString(entity,"UTF-8");
                return content;
            }
        } catch (IOException e) {

        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
            }
        }
        return null;
    }


    public static String httpRequsetPostAuth2(String url, String hostname, int port, String user, String password, String data){
        DefaultHttpClient httpclient = new DefaultHttpClient();
//        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);
        String context = null;

//        List <NameValuePair> params = new ArrayList<NameValuePair>();
//        for (Map.Entry<String ,String> entry : param.entrySet()) {
//            String key = entry.getKey().toString();
//            String value = entry.getValue().toString();
////			   System.out.println("key=" + key + " value=" + value);
//            params.add(new BasicNameValuePair(key, value));
//        }

//        httpPost.addHeader("Authorization", "Basic YWRtaW46YWRtaW4=");
        httpPost.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Character Encoding", "UTF-8");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");

        try {
            //添加证书
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(hostname, port),
                    new UsernamePasswordCredentials(user, password));

            httpPost.setEntity(new ByteArrayEntity(data.getBytes("UTF-8")));

            System.out.println("executing request" + httpPost.getRequestLine());
            //执行请求
            HttpResponse response = httpclient.execute(httpPost);
            //获得响应实体
            HttpEntity entity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
                context = EntityUtils.toString(entity,"UTF-8");
//                System.out.println("Response content : " + context);
            }
            //销毁实体
            EntityUtils.consume(entity);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            // 当不再需要HttpClient实例时,关闭连接管理器以确保释放所有占用的系统资源
            httpclient.getConnectionManager().shutdown();
        }

        return context;
    }
    public static String httpRequsetPostAuth(String url, String hostname, int port, String user, String password, Map<String,String> param){
        DefaultHttpClient httpclient = new DefaultHttpClient();
//        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);
        String context = null;

        List <NameValuePair> params = new ArrayList<NameValuePair>();
        for (Map.Entry<String ,String> entry : param.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
//			   System.out.println("key=" + key + " value=" + value);
            params.add(new BasicNameValuePair(key, value));
        }

//        httpPost.addHeader("Authorization", "Basic YWRtaW46YWRtaW4=");
        httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml,application/json,text/javascript;q=0.9,image/webp,*/*;q=0.8");
        httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");
        httpPost.addHeader("Character Encoding", "UTF-8");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");

        try {
            //添加证书
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(hostname, port),
                    new UsernamePasswordCredentials(user, password));

            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));

            System.out.println("executing request" + httpPost.getRequestLine());
            //执行请求
            HttpResponse response = httpclient.execute(httpPost);
            //获得响应实体
            HttpEntity entity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
                context = EntityUtils.toString(entity,"UTF-8");
                System.out.println("Response content : " + context);
            }
            //销毁实体
            EntityUtils.consume(entity);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            // 当不再需要HttpClient实例时,关闭连接管理器以确保释放所有占用的系统资源
            httpclient.getConnectionManager().shutdown();
        }

        return context;
    }

    public static String httpRequsetGetAuth(String url, String hostname, String port, String user, String password, Map<String,String> param){
        return httpRequsetGetAuth(url, hostname, Integer.parseInt(port), user, password, param);
    }

    public static String httpRequsetGetAuth(String url, String hostname, int port, String user, String password, Map<String,String> param){
        DefaultHttpClient httpclient = new DefaultHttpClient();
//        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpGet httpGet = new HttpGet(url);
        HttpClientContext context = HttpClientContext.create();

//        httpPost.addHeader("Authorization", "Basic YWRtaW46YWRtaW4=");
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.addHeader("Accept-Encoding", "gzip,deflate,sdch");
        httpGet.addHeader("Character Encoding", "UTF-8");
        httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");

        try {
            //添加证书
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(hostname, port),
                    new UsernamePasswordCredentials(user, password));

//            httpGet.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));

            System.out.println("executing request" + httpGet.getRequestLine());
            //执行请求
            HttpResponse response = httpclient.execute(httpGet);
            //获得响应实体
            HttpEntity entity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (entity != null) {
                String content = EntityUtils.toString(entity,"UTF-8");
//                CookieStore cookieStore = context.getCookieStore();
//                List<Cookie> cookies = cookieStore.getCookies();
//                for (Cookie co:  cookies) {
//                    System.out.println(co);
//
//                }
                return content;

            }
            //销毁实体
            EntityUtils.consume(entity);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            // 当不再需要HttpClient实例时,关闭连接管理器以确保释放所有占用的系统资源
            httpclient.getConnectionManager().shutdown();
        }

        return null;
    }

   
}
