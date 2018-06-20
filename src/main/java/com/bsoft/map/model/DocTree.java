package com.bsoft.map.model;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

import com.bsoft.accordion.core.proxy.ProxyCenter;
import com.bsoft.accordion.core.proxy.TestFace;
import com.bsoft.accordion.core.proxy.Wrapper;
import com.bsoft.map.xml.XMLParser;
import com.bsoft.util.TextStructApiWraper;
import org.json.JSONObject;

import static java.lang.System.currentTimeMillis;

/**
 * Created by sky on 2018/5/15.
 */
public class DocTree{

    public final static String FIND_MODE_CONTAINS = "contains";
    public static final String FIND_MODE_ALL = "ALL";


    private Tree parserTree = new Tree();

    public DocTree(String response){
        init(response);
    }


    public void init(String requsetText){// 填充 parser tree, 形成树状结构, 每个tree 有一些子节点，放到 nodes里面
        JSONObject result = null;
        if (!requsetText.contains("ERROR :")){
            JSONObject sourceJson = new JSONObject(requsetText);
            for (Object sectiont: sourceJson.getJSONArray("Doc_Rst")) {
                JSONObject section = (JSONObject)sectiont;
                Tree sectionTree = new Tree();
                String sectionName = (String) section.get("ne_type");
                for (Object node :section.getJSONArray("ne_list")){
                    JSONObject wordEntry = (JSONObject)node;
                    String FORMAL = (String) wordEntry.get("FORMAL");
                    Tree wordTree_F =  new Tree();
                    String NAME = (String) wordEntry.get("NAME");
                    Tree wordTree_N =  new Tree();
                    String CORE = null;
                    Tree wordTree_C =  new Tree();

                    try {
                        CORE = (String) wordEntry.get("CORE");
                    }
                    catch (Exception e){
                        //e.printStackTrace();
                    }

                    for (Object prop:wordEntry.getJSONArray("PROPERTY")){
                        JSONObject wordProp = (JSONObject)prop;
                        String key = (String) wordProp.get("TYPE");
                        Map<String, Object> value = json2Map(wordProp);
                        wordTree_F.hang(key, Tree.cast2Tree(value));
                        wordTree_N.hang(key, Tree.cast2Tree(value));
                        wordTree_C.hang(key, Tree.cast2Tree(value));
                    }
                    sectionTree.hang(FORMAL, wordTree_F);
                    if (!NAME.equals(FORMAL)){
                        sectionTree.hang(NAME, wordTree_N);
                    }
                    if (CORE!= null && !"".equals(CORE) && (!NAME.equals(CORE) || !FORMAL.equals(CORE))){
                        sectionTree.hang(CORE, wordTree_C);
                    }
                };
                parserTree.hang(sectionName, sectionTree);
            }
        }else {
            System.out.println(requsetText);
        }
    }

    private Map<String,Object> json2Map(JSONObject wordProp) {
        Map<String, Object> result = new HashMap<>();
        result.putAll(wordProp.toMap());
        return result;
    }

    public static List<Tree> getEntry(Tree tree, String targetSection, String mode) {
        List<Tree> nodes = null;
        if (targetSection == null){
            return null;
        }
        if (FIND_MODE_CONTAINS.equals(mode)){
            nodes = tree.findContains(targetSection);
        }else if (FIND_MODE_ALL.equals(mode)){
            nodes = tree.findContains("");
        }else {
            nodes = tree.find(targetSection);

        }
        return nodes;

    }

    public static List<Tree> getEntry(Tree tree, String targetSection) {
        return getEntry(tree, targetSection, null);
    }

    public Tree getParserTree() {
        return parserTree;
    }


}
