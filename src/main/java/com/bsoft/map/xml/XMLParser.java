package com.bsoft.map.xml;

import com.bsoft.map.Const;
import com.bsoft.map.face.Processor;
import com.bsoft.map.model.WordEntry;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sky on 2018/5/18.
 */
public class XMLParser {

    static final String fileName = "/fieldMapping.xml";

    public static void main(String[] args){
        XMLParser parser = new XMLParser();
        System.out.println(parser.parseXml());
    }

    static XMLParser instance;

    public static synchronized XMLParser getInstance(){
        if (instance == null){
            instance = new XMLParser();
            instance.parseXml();
        }
        return instance;
    }

    private List<WordEntry> result = new ArrayList<>();

    public List<WordEntry> parseXml(){

        if (result.size() == 0)
        try{
            //将src下面的xml转换为输入流
            InputStream inputStream = this.getClass().getResourceAsStream(fileName);
            //创建SAXReader读取器，专门用于读取xml
            SAXReader saxReader = new SAXReader();
            //根据saxReader的read重写方法可知，既可以通过inputStream输入流来读取，也可以通过file对象来读取
            Document document = saxReader.read(inputStream);

            Element rootElement = document.getRootElement();
            Iterator<Element> modulesIterator = rootElement.elements("Mapper").iterator();
            //rootElement.element("name");获取某一个子元素
            //rootElement.elements("name");获取根节点下子元素moudule节点的集合，返回List集合类型
            while(modulesIterator.hasNext()){

                try{

                    Element moduleElement = modulesIterator.next();
                    Element targetSectionElement = moduleElement.element("TargetSection");
    //                System.out.println(nameElement.getName() + ":" + nameElement.getText());
                    Element targetTypeElement = moduleElement.element("TargetName");
                    Element targetNamElement = moduleElement.element("PropType");
                    Element entryNameElement = moduleElement.element("PropName");

                    Element sectionElement = moduleElement.element("Section");
                    Element tableColElement = moduleElement.element("Field");
                    Element processorElement = moduleElement.element("Processor");
                    Element valueElement = moduleElement.element("Value");

    //                System.out.println(processorElement.getText());

                    WordEntry wordEntry = new WordEntry();
                    wordEntry.setTargetSection(getFormalStr(targetSectionElement.getText()));//枚举
                    wordEntry.setTargetName(targetTypeElement.getText());

                    if (targetNamElement != null)
                        wordEntry.setPropType(getFormalStr(targetNamElement.getText()));//枚举
                    if (entryNameElement != null)
                        wordEntry.setPropName(entryNameElement.getText());
                    if(valueElement!= null)
                        wordEntry.setTargetValue(valueElement.getText());

                    wordEntry.setProcessor((Processor) getInstanceFromClass(processorElement.getText()));
                    wordEntry.setSection(sectionElement.getText());
                    wordEntry.setField(tableColElement.getText());

                    result.add(wordEntry);

                }catch (Exception e){
                    //logger
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            //logger
            e.printStackTrace();
        }
        return result;
    }

    private Object getInstanceFromClass(String processorElement){
        Class<?> clazz = null;
        try {
            clazz = Class.forName(processorElement);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        if (clazz != null){
            try {
                return clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getFormalStr(String text){
        StringBuilder sb = new StringBuilder();
        if (text.contains(";")){
            for (String substr : text.split(";")){
                sb.append(Const.getRealValue(substr)+";");
            }
            sb.deleteCharAt(sb.length()-1);
            return sb.toString();
        }
        return Const.getRealValue(text);
    }


}
