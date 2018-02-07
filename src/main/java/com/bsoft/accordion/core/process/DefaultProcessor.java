package com.bsoft.accordion.core.process;


import com.bsoft.accordion.core.MetaData;

/**
 * Created by sky on 2018/1/25.
 */
public class DefaultProcessor implements Processor {
    @Override
    public MetaData process(MetaData data) {
        return data;
    }
}
