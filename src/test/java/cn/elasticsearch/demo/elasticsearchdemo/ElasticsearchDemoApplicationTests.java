package cn.elasticsearch.demo.elasticsearchdemo;
import java.util.Date;

import cn.elasticsearch.demo.elasticsearchdemo.model.WhiteListDomain;
import cn.elasticsearch.demo.elasticsearchdemo.service.WhiteService;
import cn.elasticsearch.demo.elasticsearchdemo.util.Constant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchDemoApplicationTests {
    @Resource
    private WhiteService whiteService;

    @Test
    public void whiteListTest() throws Exception {
        for (int i=0;i<1000;i++){
            WhiteListDomain whiteListDomain = new WhiteListDomain();
            whiteListDomain.setBizType(1);
            whiteListDomain.setSubType(0);
            whiteListDomain.setVenderId(Constant.getUUIDInOrderId().longValue());
            whiteListDomain.setBusinessType(0);
            whiteListDomain.setCreated(new Date());
            whiteListDomain.setModified(new Date());
            whiteListDomain.setYn(0);
            whiteListDomain.setOperator("qwe");
            whiteListDomain.setRuleId(12L);
            whiteListDomain.setReportCause(0);
            whiteListDomain.setReportStartDate(new Date());
            whiteListDomain.setReportEndDate(new Date());

            whiteService.createProfileDocument(whiteListDomain);
        }

    }
    @Test
    public void findByIdTest() throws Exception {
        System.out.println(whiteService.findById("793686043"));
    }

    @Test
    public void findAllTest() throws Exception {
        System.out.println(whiteService.findAll());
    }

}
