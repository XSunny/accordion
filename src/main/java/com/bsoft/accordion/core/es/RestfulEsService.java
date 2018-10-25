package com.bsoft.accordion.core.es;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by sky on 2017/4/11.
 */
public class RestfulEsService extends EsService{

    private static final Logger logger = LoggerFactory
            .getLogger(RestfulEsService.class);

    static {

        esUrl = (String) ConfigUtil.getConfig().get("es.esUrl");//es 的链接地址

        port = Integer.parseInt((String) ConfigUtil.getConfig().get("es.port"));

        indexStr = (String) ConfigUtil.getConfig().get("es.indexStr");// 默认检索的索引

        clusterName = (String) ConfigUtil.getConfig().get("es.cluster") == null ? "my-cluster-bsoft" : (String) ConfigUtil.getConfig().get("es.cluster");

    }

    private static final RestfulEsService service = new RestfulEsService();

    public static RestfulEsService getInstance(){
        return service;
    }

    TransportClient client;

    static String esUrl;//es 的链接地址

    static int port;

    static String clusterName;

    public static String indexStr;// 默认检索的索引


    public RestfulEsService() {
        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", clusterName)
                    .put("client.transport.sniff", true)
                    .build();

            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esUrl), port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            if(client != null){
                client.close();
            }
        }
    }

    @Override
    public boolean putDocument(String index, String type, String queryId, String jsonStr) {
        return client.prepareIndex(index, type, queryId).setSource(jsonStr).get().status() == RestStatus.OK;
    }

    @Override
    public boolean upsertDocument(String index, String type, String queryId, String jsonStr) {
//        IndexRequest indexRequest = new IndexRequest(index, type, queryId).source(jsonStr);
        UpdateRequest updateRequest = new UpdateRequest(index, type, queryId).doc(jsonStr);
        try {
            return client.update(updateRequest).get().status() == RestStatus.OK;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getDocument(String index, String type, String queryId) {
        return client.prepareGet(index, type, queryId).get().getSourceAsString();
    }

    @Override
    public boolean deleteDocument(String index, String type, String queryId) {
        return client.prepareDelete(index, type, queryId).get().status() == RestStatus.OK;
    }


    @Deprecated
    public SearchResponse queryMax(QueryBuilder qb){
        SearchRequestBuilder searchBuilder = client.prepareSearch(indexStr)
                .setFrom(0)
                .setSize(10000)
//                    .setTypes(Constants.SEARCH_TYPE_DOC)
                .setQuery(qb);
        logger.debug("--------------search Code: ------------\n" + searchBuilder);
        return searchBuilder.get();
    };

    public void close(){
        if (client != null)
            this.client.close();
    }


}
