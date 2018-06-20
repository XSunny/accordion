package com.bsoft.accordion.core.process;


import com.bsoft.accordion.core.metadata.MetaData;

/**
 * Created by sky on 2018/1/25.
 */
public interface Processor {

    public MetaData process(MetaData data) throws Exception;//实现的时候，不能恢复的异常全部抛出，不要吞食
}
