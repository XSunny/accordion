package com.bsoft.map.model;

import com.bsoft.map.face.Processor;

import java.util.*;
/**
 * Created by sky on 2018/5/15.
 */
public class WordEntry {

    // 需要填写的表项
    String section;
   //表项关键字
    String field;

    //-------------------------------------------------------

    // 在文档树里面映射的字段
    String targetSection; // 目标属性字段（大类），枚举可选

    String targetName;  // 二级分类，手动指定

    String propType; //  具体分类类型

    String propName; // 具体分类下的实体名称

    //String PropKey; // 属性名称， （废弃）

    String targetValue; // 值判断类型 1.数值/具体值 2.阴阳性 3.是否存在


    Processor processor = new Processor() {
        @Override
        public String process(List<Map<String, Object>> value, WordEntry entry) {
            System.out.println(value);
            return null;
        }
    };// 用于 value -> 实际需要值的转换

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getTargetSection() {
        return targetSection;
    }

    public void setTargetSection(String targetSection) {
        this.targetSection = targetSection;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getPropType() {
        return propType;
    }

    public void setPropType(String propType) {
        this.propType = propType;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public String toString() {
        return "WordEntry{" +
                "section='" + section + '\'' +
                ", field='" + field + '\'' +
                ", targetSection='" + targetSection + '\'' +
                ", targetName='" + targetName + '\'' +
                ", propType='" + propType + '\'' +
                ", propName='" + propName + '\'' +
                ", targetValue='" + targetValue + '\'' +
                ", processor=" + processor +
                '}';
    }
}
