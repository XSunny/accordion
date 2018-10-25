package com.bsoft.accordion.core.es;

/**
 * Created by sky on 2017/5/4.
 */
public class EsServiceManager {

    public static EsServiceManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private EsServiceManager (){}

    private static class SingletonHolder {
        private static final EsServiceManager INSTANCE = new EsServiceManager();
    }

    private EsService service;

    public EsService getService() {
        return service;
    }

    public void setService(EsService service) {
        this.service = service;
    }
}
