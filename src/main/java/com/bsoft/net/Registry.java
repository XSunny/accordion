package com.bsoft.net;

import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

/**
 * Created by sky on 2018/10/26.
 */
public class Registry {

    public static String UrlField_KEY_Name = "";
    public static String UrlField_VALUE_Name = "";

    static String Default_RealUrl = "http://10.10.0.7:9392/";

    static Map<String, String> urlMapper = new ConcurrentHashMap<String, String>();

    static {
        try {
//            registryUrl("/", "http://localhost:8080");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setMapUrlFieldName(String urlField_KEY_Name) {
        UrlField_KEY_Name = urlField_KEY_Name;
    }

    public static void setTargetUrlFieldName(String urlField_VALUE_Name) {
        UrlField_VALUE_Name = urlField_VALUE_Name;
    }

    public static void setDefaultUrl(String default_RealUrl) {
        Default_RealUrl = default_RealUrl;
    }

    public static Pair<String,String> getMatchUrl(String url) {// TODO 错误控制，防止不合法url 导致程序异常
        String realUrl = Default_RealUrl;
        //String tail = "";
        String path = "";
        try {
            if (url.contains("?")){//有 ？ 的 url 处理
                //tail = getTailStr(url);
                path = getPathUrl(url);
            }else {
                path = url;
            }
            List<String> urls = fastSplit(path, '/');
            urls = spliceUrl(urls);
            for (String t_url: urls){//最长优先匹配
                String t_realUrl = urlMapper.get(t_url);
                if (t_realUrl != null){
                    realUrl = t_realUrl + url.substring(t_url.length());//路径匹配
                    return new Pair<>(t_url, realUrl);
                    //break;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new Pair<>("",realUrl);
    }

    private static String getPathUrl(String url) {
        return url.substring(0,url.indexOf('?'));
    }

    private static String getTailStr(String url) {
        return url.substring(url.indexOf('?'));
    }

    private static List<String> spliceUrl(List<String> urls) {
        List<String> subsets = new ArrayList<>(16);
        for (int i = urls.size()-1; i >= 0; i--) {
            subsets.add(splice(urls, i));
        }
        return subsets;
    }

    private static String splice(List<String> urls, int i) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j <= i; j++){
            sb.append(urls.get(j));
        }
        return sb.toString();
    }

    public static List<String> fastSplit(String s, char c){
        List<String> subsets = new ArrayList<>(16);
        List<Integer> indexes = new ArrayList<>(16);
        for (int i = 0; i < s.length(); i++){
            if (s.charAt(i) == c){
                indexes.add(i);
            }
        }
        for (int j = 0; j < indexes.size()-1; j++){
            subsets.add(s.substring(indexes.get(j), indexes.get(j+1)));
        }
        if (indexes.get(indexes.size()-1) < s.length()){
            subsets.add(s.substring(indexes.get(indexes.size()-1)));
        }
        return subsets;
    }

    public static void registryUrl(String hostUrl, String targetUrl)throws Exception{
        urlMapper.put(hostUrl,targetUrl);
    }

    public static void loadAllUrls(List<Map<String, Object>> result) throws Exception{
        for (Map<String, Object> record: result){
            registryUrl(record.get(UrlField_KEY_Name).toString(),record.get(UrlField_VALUE_Name).toString());
        }
    }

    public static void deleteUrl(String hostUrl){
        urlMapper.remove(hostUrl);
    }
}
