package cn.heckman.modulecommon.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ParesUrlUtils {

    public static Map<String, String> deserialize(String URL) {
        Map<String, String> mapRequest = new HashMap<>();
        String[] arrSplit = null;
        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        //每个键值为一组
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }

    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim();
        if (strURL.startsWith("?")) {
            arrSplit = strURL.split("[?]");
            if (strURL.length() > 1) {
                if (arrSplit.length > 1) {
                    if (arrSplit[1] != null) {
                        strAllParam = arrSplit[1];
                    }
                }
            }
        } else {
            strAllParam = strURL;
        }
        return strAllParam;
    }

//    public static void main(String[] args) {
//		/*String url = "msg_id=0904d5125d6d44000002&sp_code=10659078123456&src_mobile=手机号码&msg_content=%E5%8F%91%E9%80%81%E7%9F%AD%E4%BF%A1%E4%B8%8B%E8%A1%8C%E6%B5%8B%E8%AF%&recv_time=2015-03-18 10:03:12";
//		String tr = TruncateUrlPage(url);
//		System.out.println(tr);
//
//		Map<String, String> map =  deserialize(url);
//		System.out.println(JSON.toJSONString(map));*/
//
//        String a = "%e6%b5%8b%e8%af%95";
//        System.out.println(URLDecoder.decode(a));
//    }

}
