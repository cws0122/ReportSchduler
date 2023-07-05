package com.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class test {
    private static Logger logger = LoggerFactory.getLogger(test.class);

    public static void main(String args[]){
        try{
            // 기본 세팅이 되어있는 스케줄러 객체 생성
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            //수행할 Job 객체를 생성한다 (이름과 그룹을 정해 사용)
            JobDetail jobDetail = JobBuilder.newJob(Jobtest.class)
                    .withIdentity("myJob" , "group1")
                    .build();
            // 트리거 객체 생성 , 이름과 그룹을 지어준 뒤
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger" , "group1")
                    .startNow()  //당장 시작
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule() //간단한 스케줄 빌더로
                            .withIntervalInSeconds(10)  //아마 10초마다
                            .repeatForever()) // 무한반복
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            
            scheduler.start();
            Thread.sleep(60000);
            scheduler.shutdown();




        }catch(Exception e){
            System.out.println("에러 Log");
            e.printStackTrace();
        }

    }
}
