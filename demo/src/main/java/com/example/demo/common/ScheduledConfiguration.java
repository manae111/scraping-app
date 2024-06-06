package com.example.demo.common;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * バッチ処理を定時に呼び出すクラス
 * Sakai
 */
@Configuration
@EnableScheduling
public class ScheduledConfiguration {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job myjob;

    // @Scheduled(cron = "0 0 12 * * ?")
    // public void scheduledSendmail() {
    //     JobParameters jobParameters = new JobParametersBuilder()
    //         .addLong("time",System.currentTimeMillis())
    //         .toJobParameters();
    //     try {
    //         jobLauncher.run(myjob, jobParameters);
    //     } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
    //     // ここで例外を処理します
    //     e.printStackTrace();
    //     }

    //     // DB上に登録時priceよりもバッチ処理後priceが安い商品一覧の
    //     // メールを送信するメソッドを呼び出す

    // }
}
