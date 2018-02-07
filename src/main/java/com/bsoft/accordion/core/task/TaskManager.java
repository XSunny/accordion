package com.bsoft.accordion.core.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sky on 2018/1/25.
 */
public abstract class TaskManager <T extends Runnable>{

    private ExecutorService service = Executors.newFixedThreadPool(50);

    public void submit(T task){
        // 处理运行记录，保证数据库记录。
        service.submit(task);
    }
}
