package com.bsoft.accordion.core.es;

/**
 * Created by sky on 2017/4/10.
 *
 * This Class define as a  Proxy Class to support the useful function basing on ES engine.
 *
 */
public abstract  class EsService{

    public abstract boolean putDocument(String index, String type, String queryId, String doc);

    public abstract boolean upsertDocument(String index, String type, String queryId, String doc);

    public abstract String getDocument(String index, String type, String queryId);

    public abstract boolean deleteDocument(String index, String type, String queryId);

}
