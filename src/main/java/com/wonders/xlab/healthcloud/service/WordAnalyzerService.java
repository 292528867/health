package com.wonders.xlab.healthcloud.service;

import java.util.Map;

/**
 * Created by wukai on 15/7/3.
 */
public interface WordAnalyzerService {
    /**
     * 分析聊天语句是否包含药品，并进行后续处理
     * @param text
     */
    Map<String, String> analyzeText(String text);
}
