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

import com.example.demo.service.ScrapingService;

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

    @Autowired
    private ScrapingService scrapingService;

    //cron = "秒 分 時間 日 月 曜日"
    @Scheduled(cron = "0 59 9 * * ?")
    public void scheduledSendmail() {
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("time",System.currentTimeMillis())//実行時間を記録
            .toJobParameters();
        try {
            jobLauncher.run(myjob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
        //左からJobがすでに実行中、Jobが再実行された、Jobがすでに完了している、Jobのパラメータが不正
        // ここで例外を処理します。あとでロガー追加する
        e.printStackTrace();
        }

        // DB上に登録時priceよりもバッチ処理後priceが安い商品一覧の
        // メールを送信するメソッドを呼び出す
        scrapingService.sendMail();

    }
}
