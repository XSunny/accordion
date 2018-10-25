package com.bsoft.adapter.ws;

import com.bsoft.accordion.core.jdbc.DataSource;
import com.bsoft.accordion.core.jdbc.JDBCConenct;
import com.google.gson.Gson;
import net.java.dev.jaxb.array.StringArray;

import java.lang.*;
import java.lang.Exception;
import java.util.*;

/**
 * Created by sky on 2018/7/16.
 */
public class WebClient {

    public static void main(String [] args){

        List<Map<String,Object>> result = new ArrayList<>();
        JDBCConenct source = new JDBCConenct( "io.crate.client.jdbc.CrateDriver", "crate://10.10.0.39,10.10.33:5432/", "crate" , "bsoft1234");
        try {
            result = source.executeQuery("select * from bdb.registerrecord limit 100;");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            source.close();
        }

        Gson gson = new Gson();
        String json = gson.toJson(result.get(0));
        System.out.println(json);
        WebServiceEntryService service = new WebServiceEntryService();
        try {
            String text = service.getWebServiceEntryPort().invoke("EMR01", "123", "adapter.transportService", "transportDataByJson", new StringArray(json));
            System.out.println(text);
        } catch (Exception_Exception e) {
            e.printStackTrace();
        }
    }
}
