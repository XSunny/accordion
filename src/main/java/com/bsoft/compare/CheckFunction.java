package com.bsoft.compare;

import java.util.List;
import java.util.Map;

/**
 * Created by sky on 2018/4/18.
 */
public interface CheckFunction {

    public CheckResult checkResult(List<Map<String, Object>> rs1, List<Map<String, Object>> rs2);
}
