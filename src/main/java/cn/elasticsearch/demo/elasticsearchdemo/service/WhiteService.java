package cn.elasticsearch.demo.elasticsearchdemo.service;

import cn.elasticsearch.demo.elasticsearchdemo.model.WhiteListDomain;
import cn.elasticsearch.demo.elasticsearchdemo.util.Constant;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author qushengxu
 * @date 2021/8/5 10:08
 **/
@Service
@Slf4j
public class WhiteService {
    private RestHighLevelClient client;


    private ObjectMapper objectMapper;

    @Autowired
    public WhiteService(RestHighLevelClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public String createProfileDocument(WhiteListDomain whiteListDomain) throws Exception {

        Integer uuid = Constant.getUUIDInOrderId();
        whiteListDomain.setId(uuid.longValue());
        log.info(JSON.toJSONString(whiteListDomain));
        IndexRequest indexRequest = new IndexRequest(Constant.INDEX, Constant.TYPE, whiteListDomain.getId().toString())
                .source(convertProfileDocumentToMap(whiteListDomain), XContentType.JSON);

        IndexResponse indexResponse = client.index(indexRequest);
        return indexResponse.getResult().name();
    }

    public WhiteListDomain findById(String id) throws Exception {

        GetRequest getRequest = new GetRequest(Constant.INDEX, Constant.TYPE, id);

        GetResponse getResponse = client.get(getRequest);
        Map<String, Object> resultMap = getResponse.getSource();

        return convertMapToProfileDocument(resultMap);

    }



    public String updateProfile(WhiteListDomain document) throws Exception {

        WhiteListDomain resultDocument = findById(document.getId().toString());

        UpdateRequest updateRequest = new UpdateRequest(
                Constant.INDEX,
                Constant.TYPE,
                resultDocument.getId().toString());

        updateRequest.doc(convertProfileDocumentToMap(document));
        UpdateResponse updateResponse = client.update(updateRequest);

        return updateResponse
                .getResult()
                .name();

    }

    public List<WhiteListDomain> findAll() throws Exception {


        SearchRequest searchRequest = buildSearchRequest(Constant.INDEX,Constant.TYPE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =
                client.search(searchRequest);

        return getSearchResult(searchResponse);
    }
//
//
//    public List<ProfileDocument> findProfileByName(String name) throws Exception{
//
//
//        SearchRequest searchRequest = new SearchRequest();
//        searchRequest.indices(INDEX);
//        searchRequest.types(TYPE);
//
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//
//        MatchQueryBuilder matchQueryBuilder = QueryBuilders
//                .matchQuery("name",name)
//                .operator(Operator.AND);
//
//        searchSourceBuilder.query(matchQueryBuilder);
//
//        searchRequest.source(searchSourceBuilder);
//
//        SearchResponse searchResponse =
//                client.search(searchRequest, RequestOptions.DEFAULT);
//
//        return getSearchResult(searchResponse);
//
//    }
//
//
//    public String deleteProfileDocument(String id) throws Exception {
//
//        DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
//        DeleteResponse response = client.delete(deleteRequest,RequestOptions.DEFAULT);
//
//        return response
//                .getResult()
//                .name();
//
//    }
//
    private Map<String, Object> convertProfileDocumentToMap(WhiteListDomain profileDocument) {
        return objectMapper.convertValue(profileDocument, Map.class);
    }

    private WhiteListDomain convertMapToProfileDocument(Map<String, Object> map){
        return objectMapper.convertValue(map,WhiteListDomain.class);
    }
//
//
//    public List<ProfileDocument> searchByTechnology(String technology) throws Exception{
//
//        SearchRequest searchRequest = buildSearchRequest(INDEX,TYPE);
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//
//        QueryBuilder queryBuilder = QueryBuilders
//                .boolQuery()
//                .must(QueryBuilders
//                        .matchQuery("technologies.name",technology));
//
//        searchSourceBuilder.query(QueryBuilders.nestedQuery("technologies",queryBuilder, ScoreMode.Avg));
//
//        searchRequest.source(searchSourceBuilder);
//
//        SearchResponse response = client.search(searchRequest,RequestOptions.DEFAULT);
//
//        return getSearchResult(response);
//    }
//
    private List<WhiteListDomain> getSearchResult(SearchResponse response) {

        SearchHit[] searchHit = response.getHits().getHits();

        List<WhiteListDomain> profileDocuments = new ArrayList<>();

        for (SearchHit hit : searchHit){
            profileDocuments
                    .add(objectMapper
                            .convertValue(hit
                                    .getSourceAsMap(), WhiteListDomain.class));
        }
       return profileDocuments;
    }
    private SearchRequest buildSearchRequest(String index, String type) {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.types(type);

        return searchRequest;
    }
}
