package com.hlwcbz.modules.ws.config;

import com.hutu.modules.ws.service.WsDemoService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * webService配置
 * @author hutu
 * @date 2018/4/2 11:01
 */
@Configuration
public class CxfConfig {

    @Autowired
    private Bus bus;

    @Autowired
    WsDemoService wsDemoService;

    /** JAX-WS **/
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, wsDemoService);
        endpoint.publish("/WsUserService");
        return endpoint;
    }
}
