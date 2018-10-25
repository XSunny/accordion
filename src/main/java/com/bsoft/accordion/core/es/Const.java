package com.bsoft.accordion.core.es;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky on 2017/7/18.
 */
public class Const {

    public final static String FILTER_DOC_TYPE = "type";

    public final static String FILTER_DATE = "date";

    public static final String STRUCTCODE = "903";

    public static final String Disease_CODE = "295";

    public static final String Symptom_CODE = "294";

    public static final String Durg_CODE = "296";

    public static List<String> STRUCTCODES = new ArrayList<>();

    static {
        STRUCTCODES.add(Disease_CODE);
        STRUCTCODES.add(Symptom_CODE);
        STRUCTCODES.add(Durg_CODE);
    }
}
