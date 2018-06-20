package com.bsoft.util;

import com.bsoft.accordion.core.jdbc.DataSource;
import com.bsoft.map.model.ParserApi;

import java.io.*;
import java.util.*;

/**
 * Created by sky on 2018/5/28.
 */
public class CSVUtil {

    static String text = "姓名:***|性别:男|工作单位:#|年龄:24岁|入院日期:2017年08月3015:49|婚姻:未婚|病史采集日期:2017年08月3015:49|民族:汉族|病史陈述者:患者本人及家属|出生地:**市|病史可靠程度:可靠|职业(工种):无|过敏史:未发现|主诉:外伤致右颧面部肿痛伴张口受限34小时余。|现病史:患者34小时余前骑摩托车不慎摔倒致右侧颧面部擦伤,肿痛,右踝部裂伤、出血,伴昏迷4-5小时,醒来后遗忘受伤当时情况。伴张口受限,无恶心、呕吐,无胸闷、心悸,无腹痛、腹胀等不适,无鼻腔及外耳道渗血及清亮液体,无大小便失禁,急诊于福建省福清市第二医院,行右踝部清创缝合;并行CT检查1.颅脑平扫未见明显异常。2.右侧上颌窦前壁骨折并腔内积血。3.双肺、肝胆胰脾及双肾未见明显异常;踝部CT示:1.右足第四跖骨近端皮质旁骨性赘生物。2.右足部诸骨未见明显骨折;给予抗炎、补液营养等处理,具体不详;目前病情稳定,神志清楚,建议上级医院进一步手术治疗面部骨折;今患者经人介绍,就诊我科,门诊拟\"右颧骨颧弓折\"收治入院。受伤以来患者精神可,进食、言语稍受影响,睡眠及大小便正常,体重无明显变化。|既往史:平素健康状况可;否认\"结核、肝炎\"等传染病史,否认\"高血压病、血友病、心脏病及糖尿病\"病史,未发现有过食物、药物过敏史,否认手术、其他外伤史,否认输血史。|个人史:出生居住原籍,无疫区及疫水接触史,无毒物及放射性物质接触史,4年抽烟史,半包/天,机会性饮酒。|婚育史:未婚未育。|家族史:父母及2姐体健。否认家族中有\"高血压病、糖尿病、血友病及心脏病\"等遗传性病史。否认肿瘤病史及类似病史。|体格检查|T:37.2oCP:80次/分R:19次/分BP:128/90mmHg|一般情况:神志清楚,发育正常,营养中等,体型正常,正常面容,自动体位,查体合作,步行入院。|皮肤粘膜:右踝部一长约10cm清创后伤口,对合可,少许渗出,未拆线;余皮肤色泽正常,未见黄染,未见皮下出血。皮温正常,湿度正常,无水肿,未见肝掌,未见蜘蛛痣。|淋巴结:双侧颌下、颈部、腋下、锁骨上、腹股沟等处浅表淋巴结未触及。|头颅:头颅正常,头发分布正常,眼睑无浮肿,眼球无异常,巩膜无黄染,右结膜充血,右眶下区皮肤见少许淤血,左结膜正常,角膜透明,瞳孔等大等圆,直径2.5mm,对光反射灵敏,外耳正常。鼻腔通气畅,无副鼻窦区压痛。";


        private static void createCSVFile(List exportData, String outPutPath, String filename) {
            createCSVFile(exportData, outPutPath, filename, "GB2312");
        }


        public static File createCSVFile(List<Map> exportData,
                                         String outPutPath, String filename, String codeSet) {

            File csvFile = null;
            BufferedWriter csvFileOutputStream = null;
            try {
                csvFile = new File(outPutPath + filename + ".csv");
                // csvFile.getParentFile().mkdir();
                File parent = csvFile.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                csvFile.createNewFile();

                // GB2312使正确读取分隔符","
                csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(csvFile), "GB2312"), 1024);
                // 写入文件头部
                for (Iterator propertyIterator = exportData.get(0).entrySet().iterator(); propertyIterator.hasNext();) {
                    java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator
                            .next();
                    csvFileOutputStream.write("\""
                            + propertyEntry.getKey().toString() + "\"");
                    if (propertyIterator.hasNext()) {
                        csvFileOutputStream.write(",");
                    }
                }
                csvFileOutputStream.newLine();


                // 写入文件内容
                for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
                    // Object row = (Object) iterator.next();
                    Map row = (Map) iterator.next();
                    System.out.println(row);

                    for (Iterator propertyIterator = row.entrySet().iterator(); propertyIterator.hasNext();) {
                        java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
                        // System.out.println( BeanUtils.getProperty(row, propertyEntry.getKey().toString()));
                        if (propertyEntry.getValue() != null){
                            csvFileOutputStream.write("\""
                                    +  propertyEntry.getValue().toString() + "\"");
                        }else {
                            csvFileOutputStream.write("\"null\"");
                        }
                        if (propertyIterator.hasNext()) {
                            csvFileOutputStream.write(",");
                        }
                    }
                    if (iterator.hasNext()) {
                        csvFileOutputStream.newLine();
                    }
                }
                csvFileOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    csvFileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return csvFile;
        }

        public static void main(String[] args) {
            List<Map<String, Object>> results = null;
            final Map<String,Object> prop = new HashMap<String, Object>();
            prop.put("user", (String)ConfigUtil.getConfig().get("ex.datasource.user"));
            prop.put("password", (String)ConfigUtil.getConfig().get("ex.datasource.passoword"));

            final DataSource dataSource1 = new DataSource((String)ConfigUtil.getConfig().get("ex.datasource.url"), (String)ConfigUtil.getConfig().get("ex.datasource.driver"), prop);
            try {
                results = dataSource1.getConnect().executeQuery((String) ConfigUtil.getConfig().get("ex.querysql"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String text = getAllText(results);
            String visitid = "";
            String hospitationid ="";
            if (results.size() > 0){
                visitid = (String) results.get(0).get("visitid".toUpperCase());
                hospitationid = (String) results.get(0).get("HOSPIZATIONID".toUpperCase());
            }
            List exportData = new ArrayList<Map>();
            exportData.addAll(CubeUtil.tran2col(visitid, hospitationid,new TreeMap(ParserApi.getFormalResult(text))));
            CSVUtil.createCSVFile(exportData, "", "new");
        }

    private static String getAllText(List<Map<String, Object>> results) {
        StringBuilder text = new StringBuilder();
        for (Map<String, Object> record : results) {
            for (String key :record.keySet()) {
                if (!noneTextField(key)){
                    text.append(record.get(key)+ "  ");
                }
            }
        }
        return text.toString();
    }

    private static boolean noneTextField(String key) {
        if ("VISITID".equals(key.toUpperCase())){
            return true;
        }
        if ("HOSPITATIONID".equals(key)){
            return true;
        }
        if ("ADMISSIONDATETIME".equals(key)){
            return true;
        }
        return false;
    }


}
