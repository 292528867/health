package com.wonders.xlab.healthcloud.thread;

import com.wonders.xlab.healthcloud.dto.emchat.TexMessagesRequestBody;

import java.util.concurrent.Callable;

/**
 * Created by wukai on 15/7/13.
 */
public class SendQuestionsThread implements Callable<String> {
    private TexMessagesRequestBody requestBody;

    public SendQuestionsThread(TexMessagesRequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public TexMessagesRequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(TexMessagesRequestBody requestBody) {
        this.requestBody = requestBody;
    }


    @Override
    public String call() throws Exception {
        //发送消息，获取返回结果，更新数据库状态

        return null;
    }
}
