package com.myself.modules.timedTask.demo;



import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity {

    private ScheduledExecutorService executorService;

    public void executor(String date) throws Exception {
        Long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
        //传入毫秒值-当前毫秒值
        long initialDelay = time - System.currentTimeMillis();
        if (initialDelay <= 0) {
            return;
        }

        long oneDay = 24 * 60 * 60 * 1000;
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() { //do something } },initialDelay,period, TimeUnit.HOURS)  // 1000 启动后延迟时间  initDelay 间隔时间  MILLISECONDS单位毫秒
                System.out.println("哇塞，真的执行了耶！");
                executorService.shutdown();

            }
        }, initialDelay, oneDay, TimeUnit.MILLISECONDS);
    }
}
