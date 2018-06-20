package com.bsoft.accordion.core.job;

/**
 * Created by sky on 2018/4/27.
 */
public class SQL_Const {

    public static String TOTAL_COUNT = "total_count";

    public static String DAY_COUNT = "day_count";

    public static String MONTH_COUNT = "month_count";

    public static String WEEK_COUNT = "week_count";

    public static String SOURCE_PREFIX = "s_";

    public static String TARGET_PREFIX = "t_";

    public static String T_TOTAL_COUNT = SOURCE_PREFIX +TOTAL_COUNT;

    public static String S_TOTAL_COUNT = TARGET_PREFIX +TOTAL_COUNT;

    public static String S_DAY_COUNT = SOURCE_PREFIX + DAY_COUNT;

    public static String T_DAY_COUNT = TARGET_PREFIX + DAY_COUNT;

    public static String S_WEEK_COUNT=  SOURCE_PREFIX + WEEK_COUNT;

    public static String T_WEEK_COUNT = TARGET_PREFIX + WEEK_COUNT;

    public static String S_MONTH_COUNT=  SOURCE_PREFIX + MONTH_COUNT;

    public static String T_MONTH_COUNT=  TARGET_PREFIX + MONTH_COUNT;

}
