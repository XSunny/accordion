package com.bsoft.map.model;

import java.util.*;
import com.bsoft.map.xml.XMLParser;
import com.bsoft.util.TextStructApiWraper;

/**
 * Created by sky on 2018/5/25.
 */
public class ParserApi {

    static XMLParser fieldMappers = XMLParser.getInstance();

    public static Map<String,Object> getFormalResult(String context){
        String text = TextStructApiWraper.getResponse(context);
        DocTree tree = new DocTree(text);
        Map<String, Object> result = new HashMap<>();
        for (final WordEntry entry:fieldMappers.parseXml()) {//经 测试 在循环在 100+ 左右的情况下， 多线程性能不如 单线程性能，原因可能是线程切换、创建的开销大于串行执行开销；
            result.put(entry.getSection()+"-"+entry.getField(), ""+WordProcessor.getNLPValue(tree, entry));
        }
        return result;
    }

}
