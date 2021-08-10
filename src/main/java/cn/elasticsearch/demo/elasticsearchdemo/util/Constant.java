package cn.elasticsearch.demo.elasticsearchdemo.util;

import java.util.UUID;

/**
 * @author qushengxu
 * @date 2021/8/5 10:11
 **/
public interface Constant {
    String INDEX = "white_list_index";
    String TYPE = "EsWhiteListType";

    public static Integer getUUIDInOrderId(){
        Integer orderId= UUID.randomUUID().toString().hashCode();
        orderId = orderId < 0 ? -orderId : orderId; //String.hashCode() 值会为空
        return orderId;
    }
}
