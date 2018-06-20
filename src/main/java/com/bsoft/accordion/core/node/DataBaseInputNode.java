package com.bsoft.accordion.core.node;

import com.bsoft.accordion.core.metadata.MetaData;
import com.bsoft.accordion.core.consts.Const;
import com.bsoft.accordion.core.jdbc.GenerJDBC;
import com.bsoft.accordion.core.process.Processor;

/**
 * Created by sky on 2018/4/9.
 *
 *
 *   注意 今天开发数据比对功能，获得数据比对能力，即两个不同数据库的sql语句执行，获得数据质量信息
 *
 */
public class DataBaseInputNode extends AbstractNode{

    private GenerJDBC connection;//数据库连接，注入方式。

    public void init(){

        connection = (GenerJDBC) this.config.get(Const.CONNECTION);
        final String sql = (String) this.config.get(Const.SQL);

        this.processor = new Processor() {

            public MetaData process(MetaData data) throws Exception{
                connection.executeQuery(sql);
                return data;
            }
        };
    }

}
