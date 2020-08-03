package com.myself.modules.timedTask.demo;


import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @Author: admin
 * @Description: 定时任务
 * @Date: 2020年7月29日
 */

public class TimerUtil {


    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 15);

        calendar.set(Calendar.MINUTE, 48);

        calendar.set(Calendar.SECOND, 0);


        Date time = calendar.getTime();



       timer1(time);

// timer2();

// timer3();

//        timer4();

    }


    /**
     * 设定2000毫秒后执行
     */

    public static void timer1(Date date) {
        Timer nTimer = new Timer();

        nTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (int i=0;i<5;i++){
                    if (i==2){
                        nTimer.cancel();
                        System.out.println(i);
                    }
                }
                System.out.println("----设定要指定任务-----");
               // nTimer.cancel();//使用这个方法退出任务
            }

        }, date);

    }


    /**
     * 延迟5000毫秒，每1000毫秒执行一次
     */

    public static void timer2() {

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                System.out.println("-------延迟5000毫秒，每1000毫秒执行一次--------");

            }

        }, 5000, 1000);

    }


    /**
     * 延迟5000毫秒，每1000毫秒执行一次
     */

    public static void timer3() {

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                System.err.println("-------延迟5000毫秒，每1000毫秒执行一次--------");

            }

        }, 5000, 1000);

    }

    /**
     * 设置17：56执行任务
     * <p>
     * java.util.Timer.scheduleAtFixedRate(TimerTask task, Date firstTime, long period)
     */

    public static void timer4() {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 17);

        calendar.set(Calendar.MINUTE, 26);

        calendar.set(Calendar.SECOND, 0);


        Date time = calendar.getTime();


        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                System.out.println("-------设定要指定任务--------");

            }

        }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行

    }
}