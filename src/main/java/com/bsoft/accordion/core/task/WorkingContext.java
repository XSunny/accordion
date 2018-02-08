package com.bsoft.accordion.core.task;

import com.bsoft.accordion.core.MetaData;
import com.bsoft.accordion.core.factory.NodeFactory;
import com.bsoft.accordion.core.node.AbstractNode;
import com.bsoft.accordion.core.node.Config;
import com.bsoft.accordion.core.process.Processor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky on 2018/1/25.
 *
 * 执行上下文，全局
 *
 */
public class WorkingContext {

    public static void main(String []args){

        List<NodeMeta> nodes = new ArrayList<>();

        nodes.add(createNodeMeta("inputNode#1",  NodeFactory.TYPE.INPUT_DATABASE, null, new Processor() {
            int count =0;
            @Override
            public MetaData process(MetaData data) {
                data.getData().put("datacount", count++);
                data.getData().put("normalData", "name1");
                System.out.println("Intput 0 meta:" + data.getData());
                if (count > 1000){
                    throw new NullPointerException();
                }
                return data;
            }
        }));
        nodes.add(createNodeMeta("normalNode#1",  NodeFactory.TYPE.INPUT_DATABASE, null, new Processor() {
            @Override
            public MetaData process(MetaData data) {
                int num = (Integer) data.getData().get("datacount")- 1;
                data.getData().put("addData1", num);
                System.out.println("normalnode 1 excute! meta:"+data.getData());
                return data;
            }
        }));
        nodes.add(createNodeMeta("normalNode#2", NodeFactory.TYPE.INPUT_DATABASE, null, new Processor() {
            @Override
            public MetaData process(MetaData data) {
                int num = (Integer) data.getData().get("datacount")+ 1;
                data.getData().put("addData2", num);
                System.out.println("normalnode 2 excute! meta:"+data.getData());
                return data;
            }
        }));
        nodes.add(createNodeMeta("normalNode#3", NodeFactory.TYPE.NORMAL_TRANSFORM, null, (data) -> {
                int sub = (Integer) data.getData().get("addData1")+ (Integer) data.getData().get("addData2");
                data.getData().put("sub", sub);
                System.out.println("sub node excute! meta:"+data.getData());
                return data;
            }
        ));

        List<Link> links =new ArrayList<>();
        links.add(new Link("inputNode#1", "normalNode#1"));
        links.add(new Link("inputNode#1", "normalNode#2"));
        links.add(new Link("normalNode#1", "normalNode#3"));
        links.add(new Link("normalNode#2", "normalNode#3"));

        TaskBuilder.createTask(nodes, links).excute();
    }

    private static NodeMeta createNodeMeta(String nodeId, NodeFactory.TYPE type,  Config config, Processor processor) {
        NodeMeta meta = new NodeMeta();
        meta.setNodeType(type);
        meta.setProcessor(processor);
        meta.setNodeId(nodeId);
        meta.setConfig(config);
        return meta;
    }

}
