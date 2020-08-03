package com.myself.modules.timedTask.service;

import com.myself.common.util.spring.SpringUtil;
import org.springframework.web.client.RestTemplate;


/**
 * <p>
 * 定时任务
 *
 * </p>
 *
 * @author admin
 * @since 2020-07-30
 */
public class MyRunnable implements Runnable {


    /**
     * 远程访问
     **/
    RestTemplate restTemplate = SpringUtil.getBeanByClass(RestTemplate.class);

    @Override
    public void run() {
        restTemplate.getForObject("http://localhost:8080/user?userId=id", String.class);

    }
}
