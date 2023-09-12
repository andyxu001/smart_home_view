package com.andy.home.job;

import com.alibaba.fastjson.JSON;
import com.andy.home.po.dto.ProductDto;
import com.andy.home.po.vo.ProductVo;
import com.andy.home.po.wechat.WeChatTemplateMsg;
import com.andy.home.po.wechat.WechatToken;
import com.andy.home.service.ProductService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

@Service
@Slf4j
public class ProductJob implements Job {

    @Autowired
    private ProductService productService;

    @Value("${spring.profiles.active}")
    public String env;

    private final String APP_ID = "wx5e6a50c063cc2ec2";
    //private final String OPEN_ID_XIAOFEI = "oBDDc6fJoK9_p2ckaXv-lkOZTomY"; //小飞的微信测试账号
    //private final String OPEN_ID_XIAOKANG = "oBDDc6XwE_SoXuX-WUECSenn3zlw";
    private final String APP_SECRET = "a828fd8bc654dc97ecbbce8ee44314d1";
    private final String GRANT_TYPE = "client_credential";
    private final String TEMPLATE_ID = "Ei7g4YMogYoWR29J4AGBa8G7sxTjBohdKAhO4FRjU1o";
    private final String URL = "https://api.weixin.qq.com/cgi-bin/token?" +
            "grant_type=client_credential" +
            "&appid=wx5e6a50c063cc2ec2" +
            "&secret=a828fd8bc654dc97ecbbce8ee44314d1";

    private List<String> openIds = null;

    @PostConstruct
    public void init() {
        openIds = Arrays.asList("oBDDc6fJoK9_p2ckaXv-lkOZTomY","oBDDc6XwE_SoXuX-WUECSenn3zlw");
    }

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 每日18:00发送微信即将过期提醒
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("当前环境:"+env);
        //只有生产环境发送微信推送
        if (!"prod".equals(env)) {
            return;
        }

        List<ProductVo> data = getExpireData();
        if(Objects.isNull(data) || data.size() == 0) {
            return;
        }
        //获取token
        String token = getAccessToken();
        if (StringUtils.isEmpty(token)) {
            return;
        }
        log.info("获取到token:"+token);
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token;

        openIds.stream().forEach((openId) ->{
            data.stream().forEach((productVo -> {
                //拼接base参数
                Map<String, Object> sendBody = new HashMap<>();
                sendBody.put("touser", openId);  //openId
                sendBody.put("url", "https://www.xuyuefei.com/product.do");  //跳转网页url
                sendBody.put("template_id", TEMPLATE_ID);      // 模板Id
                sendBody.put("topcolor", "#FF0000");      // 模板背景色
                sendBody.put("data",wrapProduct(productVo));
                ResponseEntity<String> response = restTemplate.postForEntity(url, sendBody, String.class);
                log.info("返回结果:"+response.getBody());
            }));
        });
    }

    private List<ProductVo> getExpireData() {
        ProductDto dto = new ProductDto();
        dto.setUserId(1);
        dto.setPageSize(100);
        dto.setDiffDay(5);
        dto.setIsProcess(0);
        PageInfo info = productService.queryAllProduct(dto);
        return info.getList();
    }

    private Map<String,WeChatTemplateMsg> wrapProduct(ProductVo vo) {
        Map<String, WeChatTemplateMsg> data = new HashMap<>();
        data.put("diff",new WeChatTemplateMsg(String.valueOf(vo.getDiffDay()),"#173177"));
        data.put("thing",new WeChatTemplateMsg(vo.getName(),"#173177"));
        data.put("amount",new WeChatTemplateMsg(vo.getQuantity()+vo.getUnitName(),"#173177"));
        data.put("time",new WeChatTemplateMsg(vo.getExpireDate(),"#173177"));
        return data;
    }

    private String getAccessToken(){
        String response = restTemplate.getForObject(URL, String.class);
        log.info("获取accessToken返回结果:"+response);
        WechatToken wechatToken = JSON.parseObject(response, WechatToken.class);
        return wechatToken.getAccessToken();
    }
}
