package com.bsoft.util;

import java.util.*;

/**
 * Created by sky on 2018/6/12.
 */
public class CubeUtil {
    public static List<Map<String, Object>> tran2col(String visitid, String hospitationid, Map<String, Object> tMap) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (String key :tMap.keySet()) {
            Map<String, Object> col = new HashMap<String, Object>();
            col.put("visitid", visitid);
            col.put("hospizationid", hospitationid);
            col.put("key", key);
            col.put("value", tMap.get(key));
            result.add(col);
        }
        return result;
    }
}
