package com.bsoft.map.face;

import com.bsoft.map.Const;
import com.bsoft.map.model.WordEntry;

import java.util.List;
import java.util.Map;

/**
 * Created by sky on 2018/5/23.
 */
public class ExistProcessor extends AbstracetProcessor{
    @Override
    public String process(List<Map<String, Object>> values, WordEntry entry) {
        super.process(values, entry);
        if(values.size() >= 1){
            return Const.TRUE;
        }
        return Const.FALSE;
    }
}
