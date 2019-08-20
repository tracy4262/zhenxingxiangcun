package com.ovit.nswy.wiki.base;


import com.github.xphsc.collect.Lists;
import com.github.xphsc.exception.UtilException;
import com.github.xphsc.page.Page;
import com.github.xphsc.page.PageImpl;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.wiki.common.search.ESQueryBuilderConstructor;
import com.ovit.nswy.wiki.common.search.ESQueryCondition;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by ${huipei.x} on 2017-10-18.
 */
@Component
public class BaseEsearchDao {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());
    private final static int MAX = 10000;
    @Autowired
    private TransportClient transportClient;


    public Object getIndex(String indexname, String type, String id){
        GetResponse response = transportClient.prepareGet(indexname, type, id).get();
        return response.getId();
    }

    public Map<String,Object> getIndexOfMap(String indexname, String type, String id){
        GetResponse response = transportClient.prepareGet(indexname, type, id).get();
        Map<String,Object> result=response.getSource();
        result.put("indexid",response.getId());
        return response.getSource();
    }

    public List<Map<String, Object>> prepareSearch(String indexname, ESQueryBuilderConstructor constructor){
        List<Map<String, Object>> result = Lists.newArrayList();
        SearchResponse searchResponse = transportClient.prepareSearch(indexname).setSize(MAX).setQuery(constructor.listBuilders()).execute().actionGet();
        SearchHit[] searchHists =  searchResponse.getHits().getHits();
        for (SearchHit sh : searchHists) {
            sh.getSource().put("indexid", sh.getId());
            result.add(sh.getSource());
        }
        return result;
    }


    public Object createIndex(String indexname, String type,String jsondata){
        IndexResponse response = transportClient.prepareIndex(indexname, type)
                .setSource(jsondata, XContentType.JSON).setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE)
                .get();
        return response.getId();
    }

    public Object bulkInsert(String index, String type, Map<String, String> data) {
        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
        data.forEach((param1, param2) -> {
            bulkRequest.add(transportClient.prepareIndex(index, type, param1)
                            .setSource(param2)
            );
        });
        BulkResponse bulkResponse = bulkRequest.get();
        return bulkResponse.getItems().length;
    }

    public Object bulkInsert(String index, String type, List<String> jsonList) {
        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
        jsonList.forEach(item -> {
            bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE).add(transportClient.prepareIndex(index, type)
                            .setSource(item)
            );
        });
        BulkResponse bulkResponse = bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE).get();
        return bulkResponse.getItems().length;
    }


    public void update(String index, String type, String id, String json) {
        try {
            UpdateRequest updateRequest = new UpdateRequest(index, type,id)
                    .doc(json).setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            transportClient.update(updateRequest).get().setForcedRefresh(true);
        } catch (Exception e) {
            throw new UtilException("update data failed.", e);
        }
    }

    public UpdateResponse updateIndexByScript(String indexname, String type, String id, String jsondata) throws ExecutionException, InterruptedException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(indexname);
        updateRequest.type(type);
        updateRequest.id(id);
        updateRequest.script(new Script("ctx._source.message = \"" + jsondata + "\""));
        UpdateResponse updateResponse =  transportClient.update(updateRequest).get();
        return updateResponse;
    }

    public UpdateResponse updateIndexByMerging(String indexname, String type, String id, String jsondata) throws ExecutionException, InterruptedException, IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(indexname);
        updateRequest.type(type);
        updateRequest.id(id);
        updateRequest.doc(XContentFactory.jsonBuilder()
                .startObject()
                .field("message", jsondata)
                .endObject());
        UpdateResponse updateResponse =  transportClient.update(updateRequest).get();
        return updateResponse;
    }


    public DeleteResponse deleteIndex(String indexname, String type, String id){
        DeleteResponse response = transportClient.prepareDelete().setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE).setIndex(indexname)
                .setType(type)
                .setId(id)
                .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE)
                .execute().actionGet();
        return  response;
    }


    public  boolean isIndexExist(String index) {
        IndicesExistsResponse inExistsResponse = transportClient.admin().indices().exists(new IndicesExistsRequest(index))
                .actionGet();
        if (inExistsResponse.isExists()) {
            logger.info("Index [" + index + "] is exist!");
        } else {
            logger.info("Index [" + index + "] is not exist!");
        }
        return inExistsResponse.isExists();
    }


    public Object isExistName(String index, String fieldName,String name){
        ESQueryBuilderConstructor constructor =new ESQueryBuilderConstructor();
        Object result=null;
        constructor.must(new ESQueryCondition().term(fieldName, name));
        //auditstatus为1时表示审核通过
        constructor.must(new ESQueryCondition().match("auditstatus", 1));
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        List<Map<String, Object>> mapList=this.prepareSearch(index, constructor);
        if(mapList.size()==1) {
            result=1;
        }else{
            result=0;
        }
        return result;
    }



    public Page<Map<String, Object>> search(String index, String type, ESQueryBuilderConstructor constructor) {
        List<Map<String, Object>> result = Lists.newArrayList();
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(index).setTypes(type).setSearchType(SearchType.QUERY_THEN_FETCH);
        if(StringUtil.isNotEmpty(constructor.getAsc())) {
            searchRequestBuilder.addSort(constructor.getAsc(), SortOrder.ASC);
        }
        if(StringUtil.isNotEmpty(constructor.getDesc())) {
            searchRequestBuilder.addSort(constructor.getDesc(), SortOrder.DESC);
        }
        if (StringUtil.isEmpty(constructor.getAsc()) && StringUtil.isEmpty(constructor.getDesc())) {
            if ("species_info".equals(index)) {
                //首页根据物种编码升序
                searchRequestBuilder.addSort("fcode.keyword", SortOrder.ASC);
            }
        }

        searchRequestBuilder.setQuery(constructor.listBuilders());
        long total=searchRequestBuilder.get().getHits().getTotalHits();
        int size = constructor.getSize();
        if (size < 0) {
            size = 0;
        }
        if (size > MAX) {
            size = MAX;
        }
        searchRequestBuilder.setSize(size);
        searchRequestBuilder.setFrom(constructor.getFrom() < 0 ?0: (constructor.getFrom()-1)*size);
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHists = hits.getHits();
        Arrays.asList(searchHists).stream().forEach(sh->{
            sh.getSource().put("indexid", sh.getId());
            result.add(sh.getSource());
                });
        return new PageImpl<Map<String, Object>>(constructor.getFrom(),size,total,result);

    }


    public List<Map<String, Object>> searchList(String index, String type, ESQueryBuilderConstructor constructor) {
        List<Map<String, Object>> result = Lists.newArrayList();
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(index).setTypes(type);
        if(StringUtil.isNotEmpty(constructor.getAsc())) {
            searchRequestBuilder.addSort(constructor.getAsc(), SortOrder.ASC);
        }
        if(StringUtil.isNotEmpty(constructor.getDesc())) {
            searchRequestBuilder.addSort(constructor.getDesc(), SortOrder.DESC);
        }
        if (StringUtil.isEmpty(constructor.getAsc()) && StringUtil.isEmpty(constructor.getDesc())) {
            if ("species_info".equals(index)) {
                //首页根据物种编码升序
                searchRequestBuilder.addSort("fcode.keyword", SortOrder.ASC);
            }
        }
        searchRequestBuilder.setQuery(constructor.listBuilders());
        searchRequestBuilder.setSize(MAX);
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHists = hits.getHits();
        Arrays.asList(searchHists).stream().forEach(sh->{
            sh.getSource().put("indexid", sh.getId());
            result.add(sh.getSource());
        });
        return result;

    }


}
