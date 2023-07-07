package com.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class Jobtest implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String DefaultFilepath = "C:\\Users\\kyz98\\OneDrive\\바탕 화면\\DailyReport\\daily.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(DefaultFilepath));
            StringBuilder builder = new StringBuilder();
            Scanner sc = new Scanner(System.in);
            LocalDate now = LocalDate.now();
            DayOfWeek dayOfWeek = now.getDayOfWeek();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

            String day = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN); // 오늘의 요일
            String reportDate = now.format(formatter);    // 오늘의 날짜 (년.월.일)
            String NewFile = "C:\\Users\\kyz98\\OneDrive\\바탕 화면\\DailyReport\\" + reportDate + ".txt";
            String report = "- ";       // 오늘 보고 할 내용 양식..?
            int check = 0;  // 일일보고 맞추기 위한 체크포인트

            System.out.println("금일 일일보고 내용을 입력하세요.");
            report = report + sc.nextLine();

            String str;
            while((str = reader.readLine()) != null){

                if(str.contains("-")){
                    check++;
                    if(check == 1){
                        str = report;
                    }
                }else if(str.contains("*")){
                    str = "* " + reportDate + "(" + day.substring(0,1) + ")" + " 일일업무 진행 내역입니다.";
                }

                builder.append(str);
                builder.append("\n");
            }
            System.out.println("builder = " + builder);

            BufferedWriter fw = new BufferedWriter(new FileWriter(NewFile, false));
            fw.write(String.valueOf(builder));
            fw.flush();

            fw.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //System.out.println("되나?");
    }
}
