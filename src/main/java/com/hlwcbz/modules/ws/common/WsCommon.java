package com.hlwcbz.modules.ws.common;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;

/**
 * ws调用第三方接口
 * @author hutu
 * @date 2018/4/4 10:19
 */
public class WsCommon {
    private final static Logger logger = LoggerFactory.getLogger(WsCommon.class);
    /**
     * 执行调用第三方接口
     * @param  parameter
     */
    public static Map execute(Object parameter) {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(WsConstant.ADDRESS);
        Object[] objects;
        try {
            objects = client.invoke("sayHello", parameter);

            System.out.println(objects[0].toString());

            /*HashMap map = JSON.parseObject(objects[0].toString(), HashMap.class);
            if ("1".equals(map.get("Status"))){
                logger.info(objects[0].toString());
                if (StrUtil.isNotBlank(map.get("Content").toString())){
                    return (Map) map.get("Content");
                }else {
                    logger.error("掉用 "+parameter+" 无结果");
                    return null;
                }
            }else {
                logger.error(objects[0].toString());
                throw new GlobalException("webService请求错误");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ArrayList<String> objects = new ArrayList<>();
        objects.add("{\"name\":\"123\"}");
        execute(objects);
    }
}
