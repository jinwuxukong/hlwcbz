package com.hlwcbz.modules.ws.service.impl;

import com.hutu.modules.ws.service.WsDemoService;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * webService接口实现
 * @author hutu
 * @date 2018/4/2 11:02
 */
@WebService(serviceName = "WsUserService", // 与接口中指定的name一致
        targetNamespace = "http://webservice.hutu.com/", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.hutu.modules.ws.service.WsDemoService"// 接口地址
)
@Component
public class WsDemoServiceImpl implements WsDemoService {

    @Override
    public String sayHello(String name) {

        return "Hello ," + name;
    }

}