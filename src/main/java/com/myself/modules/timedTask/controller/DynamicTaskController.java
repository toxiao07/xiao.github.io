package com.myself.modules.timedTask.controller;


import com.myself.modules.timedTask.service.MyRunnable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 * 任务定时
 *
 * </p>
 *
 * @author admin
 * @since 2020-07-30
 */

@RestController
@Api(tags = "定时任务")
@RequestMapping("/quartz/task")
public class DynamicTaskController {

    private static final Logger logger = LoggerFactory.getLogger(DynamicTaskController.class);

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledExecutorService executorService;

    private ScheduledFuture<?> future;



    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @GetMapping("/startCron1")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "time", value = "时间", required = true, dataType = "String"),
    })
    @ApiOperation("一次定时任务")
    public void startCron1(String time) {
        executorService = Executors.newSingleThreadScheduledExecutor();
        try {
            Long date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).getTime();
            // 传入毫秒值-当前毫秒值
            long initialDelay = date - System.currentTimeMillis();
            if (initialDelay <= 0) {
                return;
            }
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {

                    //关闭任务
                    executorService.shutdown();
                }
            }, initialDelay, 24 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            logger.error("设置定时异常！", e);
        }
    }

    @GetMapping("/startCron2")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "time", value = "时间", required = true, dataType = "String"),
    })
    @ApiOperation("每天定时任务")
    public void startCron2(String time) {
        stopCron();
        if (StringUtils.isEmpty(time)) {
            return ;
        }
        try {
            String[] split = time.split(":");
            String cron = "0 " + split[1] + " " + split[0] + " * * ?";
            System.out.println(cron);
            future = threadPoolTaskScheduler.schedule(new MyRunnable(), new Trigger() {
                @Override
                public Date nextExecutionTime(TriggerContext triggerContext) {
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
            });
        } catch (Exception e) {
            logger.error("设置定时异常！", e);
        }
    }

    @GetMapping("/stopCron")
    @ApiOperation("关闭定时任务")
    public void stopCron() {

        try {
            if (future != null) {
                future.cancel(true);
            }
            if (executorService != null) {
                executorService.shutdownNow();
            }

        } catch (Exception e) {
            logger.error("关闭定时任务异常！", e);
        }
        return;
    }
}
