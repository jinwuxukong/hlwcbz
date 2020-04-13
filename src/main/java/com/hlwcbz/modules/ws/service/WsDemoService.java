package com.hlwcbz.modules.ws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * webService接口
 * @author hutu
 * @date 2018/4/2 11:02
 */
@WebService(name = "WsDemoService", // 暴露服务名称
        targetNamespace = "http://webservice.hutu.com/"// 命名空间,一般是接口的包名倒序
)
public interface WsDemoService {
    @WebMethod
    @WebResult(name = "String", targetNamespace = "")
    String sayHello(@WebParam(name = "userName") String name);
}