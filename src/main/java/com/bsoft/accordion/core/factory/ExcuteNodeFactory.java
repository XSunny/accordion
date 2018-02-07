package com.bsoft.accordion.core.factory;


import com.bsoft.accordion.core.node.AbstractNode;

import java.util.Map;

/**
 * Created by sky on 2018/1/26.
 */
public class ExcuteNodeFactory {
    public enum TYPE {
        INPUT_DATABASE,
        OUTPUT_DATABASE,
        NORMAL_TRANSFORM,

    }

    public static AbstractNode getProcessNode(ExcuteNodeFactory.TYPE type, Map<String, Object> config){
        return null;
    }
}
