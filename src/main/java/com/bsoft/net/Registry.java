package com.bsoft.net;

import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

/**
 * Created by sky on 2018/10/26.
 */
public class Registry {

    static String UrlField_KEY_Name = "";
    static String UrlField_VALUE_Name = "";

    static String Default_RealUrl = "http://10.10.0.207:9392/";

    static Map<String, String> urlMapper = new ConcurrentHashMap<String, String>();

    static {
        try {
            registryUrl("/user", "http://10.10.0.207:9392/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getRealUrl(String url) {// TODO 错误控制，防止不合法url 导致程序异常
        System.out.println(url);
        String realUrl = Default_RealUrl;
        String tail = "";
        if (url.contains("?")){//有 ？ 的 url 处理
            tail = "";
        }
        List<String> urls = fastSplit(url, '/');
        urls = spliceUrl(urls);
        for (String t_url: urls){//最长优先匹配
            realUrl = urlMapper.get(t_url);
            if (realUrl != null){
                break;
            }
        }
        return realUrl+tail;
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
            subsets.add(s.substring(indexes.get(indexes.size()-1), s.length()-1));
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
}
