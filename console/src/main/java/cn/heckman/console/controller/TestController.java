package cn.heckman.console.controller;

import cn.heckman.moduleservice.entity.WxUser;
import cn.heckman.moduleservice.service.HelloSyncService;
import cn.heckman.moduleservice.service.WxUserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
@RestController
public class TestController {

    @Autowired
    private HelloSyncService helloSyncService;

    @Autowired
    private WxUserService wxUserService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() throws Exception {

        WxUser user = wxUserService.getWxUser("34234324324324");


        return helloSyncService.saySync("hello");
    }

    @RequestMapping(value = "/test3",method = RequestMethod.GET)
    public String test3(){
        return "1\n1,62638105,13190906512";
    }

    @RequestMapping("/test2")
    public JSONObject test2() {
        String a = "{\"appInfo\": {\"isNewRecord\": true,\"remarks\": \"\",\"code\": \"SMSSRV1\",\"type\": \"SRV\",\"queues\": 30,\"status\": \"1\",\"machineid\": \"4\",\"appRealIp\": \"172.16.243.2\",\"appIp\": \"116.62.19.167\",\"appPort\": \"8801\",\"serviceIp\": \"\",\"servicePort\": \"\",\"createTime\": \"2018-05-28 22:02:10\",\"monitorFlag\": \"0\"},\"gwList\": [{\"id\": \"8\",\"gwCode\": \"LT5001\",\"appCode\": \"SMSSRV1\",\"gwName\": \"视频短信（上海联通-全网）\",\"gwRemark\": \"\",\"gwStatus\": 1,\"gwType\": \"LT\",\"gwProto\": \"HTTP\",\"gwProtoVersion\": \"1\",\"gwProtoClass\": \"com.sanerzone.mmsprovider.gateway.hander.http.impl.MmsApiSHLT\",\"gwProtoExtparam\": \"{\"username\":\"konsone_0001\",\"password\":\"ADSRV#konsone\",\"secrect\":\"S_AD_101\",\"apiUrl\":\"https://211.95.17.123:29088\",\"phoneFileUrl\":\"http://116.62.19.167:8801/phonefile\",\"title\":\"彩信主题1\",\"taskname\":\"彩信任务1\"}\",\"gwServerIp\": \"192.168.1.182\",\"gwServerPort\": \"7890\",\"gwUsername\": \"1\",\"gwPassword\": \"1\",\"gwSpNumber\": \"1069001\",\"gwCorpId\": \"\",\"gwServiceId\": \"\",\"gwReceiveModel\": \"1\",\"smsSignModel\": \"0\",\"smsSignNose\": \"0\",\"gwLongsms\": \"0\",\"gwOnceSubmit\": \"10000\",\"gwSendRate\": \"100\",\"gwConnections\": \"1\",\"gwMms\": \"3\"}]}";

        String test = "{" +
                "\"appInfo\": {" +
                "\"isNewRecord\": true," +
                "\"remarks\": \"\"," +
                "\"code\": \"SMSSRV1\"," +
                "\"type\": \"SRV\"," +
                "\"queues\": 30," +
                "\"status\": \"1\"," +
                "\"machineid\": \"4\"," +
                "\"appRealIp\": \"172.16.243.2\"," +
                "\"appIp\": \"116.62.19.167\"," +
                "\"appPort\": \"8801\"," +
                "\"serviceIp\": \"\"," +
                "\"servicePort\": \"\"," +
                "\"createTime\": \"2018-05-28 22:02:10\"," +
                "\"monitorFlag\": \"0\"" +
                "}," +
                "\"gwList\": [{" +
                "\"id\": \"8\"," +
                "\"gwCode\": \"LT5001\"," +
                "\"appCode\": \"SMSSRV1\"," +
                "\"gwName\": \"视频短信（上海联通-全网）\"," +
                "\"gwRemark\": \"\"," +
                "\"gwStatus\": 1," +
                "\"gwType\": \"LT\"," +
                "\"gwProto\": \"HTTP\"," +
                "\"gwProtoVersion\": \"1\"," +
                "\"gwProtoClass\": \"\"," +
                "\"gwProtoExtparam\": \"\"," +
                "\"gwServerIp\": \"192.168.1.182\"," +
                "\"gwServerPort\": \"7890\"," +
                "\"gwUsername\": \"1\"," +
                "\"gwPassword\": \"1\"," +
                "\"gwSpNumber\": \"1069001\"," +
                "\"gwCorpId\": \"\"," +
                "\"gwServiceId\": \"\"," +
                "\"gwReceiveModel\": \"1\"," +
                "\"smsSignModel\": \"0\"," +
                "\"smsSignNose\": \"0\"," +
                "\"gwLongsms\": \"0\"," +
                "\"gwOnceSubmit\": \"10000\"," +
                "\"gwSendRate\": \"100\"," +
                "\"gwConnections\": \"1\"," +
                "\"gwMms\": \"3\"" +
                "}]" +
                "}";


        return JSONObject.parseObject(a);
    }


    public static void main(String[] args) {
//        RestClient restClient = RestClient.builder(new HttpHost("106.75.215.115", 9200, "http")).build();

        try {
//            Response response = restClient.performRequest("GET", "/", Collections.singletonMap("pretty", "true"));
//            System.out.println(EntityUtils.toString(response.getEntity()));


//            Response response = restClient.performRequest("HEAD", "/product/pdt", Collections.<String, String>emptyMap());
//            System.out.println(response.getStatusLine().getReasonPhrase().equals("OK"));

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("q", "3");
            paramMap.put("pretty", "true");
//            Response response = restClient.performRequest("PUT", "/oil");
//            System.out.println(EntityUtils.toString(response.getEntity()));

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
//            if (restClient != null) {
//                try {
////                    restClient.close();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
        }

    }
}
