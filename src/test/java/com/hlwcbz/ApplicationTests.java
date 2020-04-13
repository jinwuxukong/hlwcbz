package com.hlwcbz;


import com.alicp.jetcache.embedded.LinkedHashMapCacheBuilder;
import com.hlwcbz.common.util.Cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Test
    public void CacheTest() {
        //缓存map定时60秒过期
        Cache.put("a",123,Cache.CACHE_HOLD_TIME_60S);
        System.out.println(LocalDateTime.now());
        try {
            Thread.sleep(59000);//休眠一分钟
            System.out.println(LocalDateTime.now());
            System.out.println(Cache.get("a"));
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}