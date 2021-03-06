package com.bsoft.accordion.core.factory;


import com.bsoft.accordion.core.node.*;

import java.util.Map;

/**
 * Created by sky on 2018/1/26.
 */
public class NodeFactory {
    public enum TYPE {
        INPUT_DATABASE,
        OUTPUT_DATABASE,
        NORMAL_TRANSFORM,

    }

    public static AbstractNode getProcessNode(NodeFactory.TYPE type, Config config){
        switch (type){
            case INPUT_DATABASE:
                return new DataInputNode(config);
            case OUTPUT_DATABASE:
                return new DataOutputNode(config);
            case NORMAL_TRANSFORM:
                return new NormalNode(config);
            default:
                return new NormalNode(config);
        }
    }

}
