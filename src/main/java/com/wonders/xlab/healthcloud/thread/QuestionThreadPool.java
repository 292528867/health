package com.wonders.xlab.healthcloud.thread;

import com.wonders.xlab.healthcloud.dto.emchat.TexMessagesRequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by wukai on 15/7/13.
 */
public class QuestionThreadPool implements Serializable {
    /**
     * log
     */
    private static final Logger LOG = LoggerFactory.getLogger(QuestionThreadPool.class);

    /**
     * 休眠时间 S
     */
    private static final int SLEEP_MILLIS = 100;

    /**
     * 最大线程数
     */
    private static final int MAX_JOB_PER_THREAD_POOL = 5;

    /**
     * alive time
     */
    private static final long KEEP_ALIVE_TIME = 10;

    /**
     * unit time
     */
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    /**
     * 因子
     */
    public static final int FACTOR = 2;

    /**
     * work queue
     */
    private ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(MAX_JOB_PER_THREAD_POOL);

    private ThreadFactory threadFactory = Executors.defaultThreadFactory();
    /**
     * executor
     */
    private transient ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, MAX_JOB_PER_THREAD_POOL, KEEP_ALIVE_TIME,
            TIME_UNIT, workQueue, threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 尚未执行的
     */
    private List<Future<String>> futureList = null;

    public void doThread(TexMessagesRequestBody requestBody){
        futureList = new ArrayList<>();
        try{
            for(int i=0; i<5; i++){
                System.out.println("进入doThread " + i);
                futureList.add(threadPool.submit(new SendQuestionsThread(requestBody)));
                Thread.sleep(3000);
            }
//            waitForThreadDone();
        } catch (Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage());
        } finally {
          release();
        }

    }

    /**
     * 释放资源
     */
    private void release() {
        try {
            if (threadPool != null) {
                threadPool.shutdown();
                threadPool = null;
            }
            if (futureList != null) {
                futureList.clear();
                futureList = null;
            }
            if (workQueue != null) {
                workQueue.clear();
                workQueue = null;
            }
        } catch (Exception e) {
            LOG.error("Release error:", e);
        }
    }

    /**
     * 等待线程结束
     * @return
     */
    private int waitForThreadDone() {
        int count = 0;
        for (Future<String> f : futureList) {
            try {
                if ("1".equals(f.get())) {
                    count++;
                }
            } catch (Exception e) {
                LOG.error("CheckExeResult error: ", e);
            }
        }
        return count;
    }

}
