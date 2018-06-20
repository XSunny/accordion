package com.bsoft.map.model;

import java.util.*;

/**
 * Created by sky on 2018/5/15.
 */
public class Tree {

    Map<String, Object> prop = new HashMap<>();

    Map<String, List<Tree>> children = new HashMap<>();

    public Tree(){};

    public Tree(Map<String, Object> o) {
        prop.putAll(o);
    }

    public static Tree cast2Tree(Map<String, Object> o){
        return new Tree(o);
    }

    public List<Tree> find(String key){
        List<Tree> o = this.children.get(key);
        return o;
    }

    public List<Tree> findContains(String key){
        List<Tree> result = new ArrayList<>();
        for (String fullKey : this.children.keySet()) {
            if (fullKey.contains(key)){
                result.addAll(this.children.get(fullKey));
            }
        }
        return result;
    }

    public void hang(String key, Tree o){
        if ( null == children.get(key)){
            List<Tree> node = new ArrayList<>();
            node.add(o);
            children.put(key, node);
        }else {
            List<Tree> node = children.get(key);
            node.add(o);
        }
    }
}
