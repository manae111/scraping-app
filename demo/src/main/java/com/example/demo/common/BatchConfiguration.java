package com.example.demo.common;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * バッチ処理をするクラス
 * タスクレットモデル
 * Sakai
 */
@Configuration
public class BatchConfiguration {

    @Bean
    Step myStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("myStep", jobRepository)
                .tasklet(myTasklet(), transactionManager)
                .build();
    }

    @Bean
    Tasklet myTasklet() {
        return (contribution, chunkContext) -> {
            // ここにタスクレットのロジックを書く

            // 1. jsから最新価格を取ってくるメソッド
            // 2. DBのバッチ処理後priceを更新
            
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    Job myJob(JobRepository jobRepository, Step myStep) {
        return new JobBuilder("myJob", jobRepository)
                .start(myStep)
                .build();
    }
}
