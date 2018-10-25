package com.bsoft.accordion.core.es;

import org.elasticsearch.search.SearchHits;

/**
 * Created by sky on 2018/1/23.
 */
public interface EsSearchService {

    public SearchHits query(String statement, String mode);

    public SearchHits query(String statement);// default mode


}
