package com.bsoft.accordion.core.job;

import com.sun.javafx.collections.MappingChange;

import java.util.*;
/**
 * Created by sky on 2018/7/6.
 */
public class DicMapper {

    public Map<String, Object> mapper(Map<String,Object> data, String fieldName, String dicPage){
//        Dic dic = findDicByName(dicPage);
        Object subject = data.get(fieldName);
//        data.put(fieldName, dic.getValue(subject));
        return data;
    }
}
