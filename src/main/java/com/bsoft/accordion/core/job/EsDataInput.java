package com.bsoft.accordion.core.job;

import com.bsoft.accordion.core.es.EsService;
import com.bsoft.accordion.core.es.RestfulEsService;
import com.bsoft.accordion.core.jdbc.DataSource;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


/**
 *
 *  1. 数据同步问题
 *
 *  1）如何解决全量数据翻页问题？                                          A    sql 附加翻页解决，由迭代组件实现
 *  2）数据更新导致的数据不一致，如何解决 （updatetime 字段来解决）        B    暂不考虑
 *  3）字典翻译问题，某些关键字段需要进行翻译                              A    在获得sql结果之后（List<Map>），附加字段翻译，翻译规则配置实现。
 *
 *  2.搜索方式问题。
 *  1）如何界定相似病历？ 相似度如何计算？（评分）
 *  2）如何对搜索结果进行展示 ？
 *
 * Created by sky on 2018/7/6.
 */
public class EsDataInput implements DataInput{

    DataSource datasource;

    String sql;

    public void doInputData(){

//        final Map<String,Object> prop = new HashMap<String, Object>();
//        prop.put("user", "root");
//        prop.put("password", "bsoft1234");
//        DataSource dataSource2 = new DataSource("jdbc:mysql://10.8.0.54:3306/metadata", "com.mysql.jdbc.Driver", prop);
        List<Map<String,Object>> results = null;

        try {
            results =  datasource.getConnect().executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        EsService esService = RestfulEsService.getInstance();

        for (Map<String ,Object> result: results) {
            esService.putDocument(RestfulEsService.indexStr, "type", "queryid", map2Json(result));
        }


    }

    String map2Json( Map<String, Object> record){
        XContentBuilder builder = null;
        try {
            builder = jsonBuilder();
            for (String key :record.keySet()) {
                builder.field(key, record.get(key));
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{}";
    }
}
