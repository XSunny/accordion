package com.bsoft.map.face;

import com.bsoft.map.model.WordEntry;

import java.util.List;
import java.util.Map;

/**
 * Created by sky on 2018/5/17.
 */
public abstract class AbstracetProcessor implements Processor {

    @Override
    public String process(List<Map<String, Object>> values, WordEntry entry) {
//        System.out.println(values);
        return null;
    }
}
