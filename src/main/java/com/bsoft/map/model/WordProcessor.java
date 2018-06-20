package com.bsoft.map.model;
import java.util.*;

/**
 * Created by sky on 2018/5/15.
 */
public class WordProcessor {

    // 约定， 逐级向下查找，遇到null则停止，如果第一参数为null, 则直接返回null
    public static String getNLPValue (DocTree tree, WordEntry wordEntry){//重新构建查找过程
        List<Map<String, Object>> entry = new ArrayList<>();
        //一级
        List<Tree> entries = findTrees(wordEntry.getTargetSection(), tree.getParserTree());//一级分类名
        if (entries != null)
        for (Tree node: entries){
            try {
                List<Tree> nodes = findTrees(wordEntry.getTargetName(), node);//实体名称（查找条件）二级
                if (null != nodes && isEmptyStr(wordEntry.getPropType())){//属性类型 三级
                    for (Tree left :nodes){
                        List<Tree> result = left.find(wordEntry.getPropType());
                        if (isEmptyStr(wordEntry.getPropName())){//属性名 四级
                            for (Tree word: result)
                                if (wordEntry.getPropName().equals(word.prop.get("NAME"))){
                                    entry.add(word.prop);
                                }
                        }else {
                            insertEntry(entry, result);
    //                        entry.add(result.get(0).prop);
                        }
                    }
                }else {
                    insertEntry(entry, nodes);
                }
            }catch (NullPointerException e){
                ;
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        String value = wordEntry.processor.process(entry, wordEntry);//策略
        return value;
    }

    private static boolean isEmptyStr(String wordEntry) {
        return wordEntry != null && !"".equals(wordEntry);
    }

    private static List<Tree> findTrees(String keyWord, Tree node) {
        List<Tree> nodes;
        if (keyWord.contains(";")){
            nodes = new ArrayList<>();
            for (String key :keyWord.split(";")){
                nodes.addAll(findTrees(key, node));
            }
            return nodes;
        }
        if ("*".equals(keyWord)){
            nodes = DocTree.getEntry(node, keyWord, DocTree.FIND_MODE_ALL);
        }
        else if (keyWord != null && keyWord.contains("*")){
            nodes = DocTree.getEntry(node, keyWord.replace("*", ""), DocTree.FIND_MODE_CONTAINS);
        }else {
            nodes = DocTree.getEntry(node, keyWord);
        }
        return nodes;
    }

    private static void insertEntry(List<Map<String, Object>> entry, List<Tree> result) {
        for (Tree node :result){
            entry.add(node.prop);
        }
    }
}
