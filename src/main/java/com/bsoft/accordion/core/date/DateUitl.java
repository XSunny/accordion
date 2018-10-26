package com.bsoft.accordion.core.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sky on 2018/10/10.
 */
public class DateUitl {

    public static String getStandardDateStr(Date data){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(data);
    }
}
