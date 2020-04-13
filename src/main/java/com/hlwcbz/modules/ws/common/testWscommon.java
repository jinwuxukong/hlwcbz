package com.hlwcbz.modules.ws.common;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class testWscommon {
    private static String url = "http://localhost:9567/services/WsUserService?wsdl";//提供接口的地址
    private static String soapaction = "http://webservice.hutu.com/";   //域名，这是在server定义的

    public static String exec(String method, Map<String, String> param) {
        Service service = new Service();
        try {
            Call call = (Call)service.createCall();
            call.setTargetEndpointAddress(url);
            call.setOperationName(new QName(soapaction, method)); //设置要调用哪个方法
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//（标准的类型）
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(soapaction + method);
            String[] params = {};
            if (param != null && param.size() > 0) {
                List<String> list = new ArrayList<>();
                for (String key : param.keySet()) {
                    call.addParameter(new QName(soapaction, key), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); //设置要传递的参数
                    list.add(param.get(key));
                }
                params = list.toArray(new String[list.size()]);
//            call.addParameter(new QName(soapaction, "EndDate"), //设置要传递的参数
//                    org.apache.axis.encoding.XMLType.XSD_STRING,
//                    javax.xml.rpc.ParameterMode.IN);
//            call.setReturnType(new QName(soapaction, "GetAdmOrgUnitByUpdateTime"), Vector.class); //要返回的数据类型（自定义类型）
            }
            String v = (String) call.invoke(params);//调用方法并传递参数
            System.out.println("result is " + v);
            return v;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        Map<String, String> param = new HashMap<>();
        param.put("userName","123");
        exec("WsUserService",param);
    }
}
