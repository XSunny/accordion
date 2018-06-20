package com.bsoft.util;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by sky on 2017/8/30.
 */
public class TextStructApiWraper {

    public static final String ADD_URL = "http://10.10.0.116:7707";

    public static final String SEARCH_URL = "http://10.10.0.116:7702";
    //public static final String ADD_URL = "http://10.10.0.116:9900";
    //public static final String ADD_URL = "http://10.10.0.116:9901";

    public static String getResponse(String context) {
        //TODO
//        HttpRequestUtil.httpRequestPost(url,cookie,hostname,parms);
        String data = "";
        try {
            XContentBuilder builder = jsonBuilder();
            builder.startObject()
                    .field("Patient_Id", "")
                    .field("Doc_Title", "")
                    .field("Doc_Content", context)
                    .field("Hospital_Id", "")
                    .endObject();
//        JSONObject obj = new JSONObject();
//        obj.put("Patient_Id", "");
//        obj.put("Doc_Title", "");
//        obj.put("Doc_Content", context);
//        obj.put("Hospital_Id", "");
            data = HttpRequestUtil.httpRequestPost(ADD_URL, builder.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String getSearchResponse(String context) {
        //TODO
//        HttpRequestUtil.httpRequestPost(url,cookie,hostname,parms);
        String data = "";
        try {
            XContentBuilder builder = jsonBuilder();
            builder.startObject()
                    .field("action", "suggest")
                    .field("type", "DISEASE")
                    .field("raw_term", context)
                    .field("user_id", "00000000")
                    .field("version", "v1.0.0.0")
                    .endObject();
//        JSONObject obj = new JSONObject();
//        obj.put("Patient_Id", "");
//        obj.put("Doc_Title", "");
//        obj.put("Doc_Content", context);
//        obj.put("Hospital_Id", "");
            data = HttpRequestUtil.httpRequestPost(SEARCH_URL, builder.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
