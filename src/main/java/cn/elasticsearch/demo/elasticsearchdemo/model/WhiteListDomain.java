package cn.elasticsearch.demo.elasticsearchdemo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class WhiteListDomain {
    private Long id;
    private Integer bizType;
    private Integer subType;
    private Long venderId;
    private Integer businessType;
    private String venderCode;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modified;
    private Integer yn;
    private String operator;
    private String source;
    private Long ruleId;
    private Integer reportCause;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportStartDate;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportEndDate;
}