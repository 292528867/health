package com.wonders.xlab.healthcloud.service;

import com.wonders.xlab.healthcloud.service.cache.LuceneCacheService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

/**
 * Created by wukai on 15/7/3.
 */
@Service
public class WordAnalyzerSvcImpl implements WordAnalyzerService {

    @Autowired
    private LuceneCacheService luceneCache;

    /**
     * 分析语音消息中是否包含药品名，如果有，生成URL扩展消息。没有则返回null
     * @param text
     * @return
     */
    @Override
    public Map<String, String> analyzeText(String text) {
        Map<String, String> resultMap = null;
        //构建IK分词器，为true使用smart分词模式
        Analyzer analyzer = new IKAnalyzer(false);
        //获取Lucene的TokenStream对象
        TokenStream ts = null;
        try {
            ts = analyzer.tokenStream("myfield", new StringReader(text));
            //获取词元位置属性
            OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
            //获取词元文本属性
            CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
            //获取词元文本属性
            TypeAttribute type = ts.addAttribute(TypeAttribute.class);

            //重置TokenStream（重置StringReader）
            ts.reset();
            //迭代获取分词结果
            while (ts.incrementToken()) {
                String key = term.toString();
                //单个字单分词直接跳过
				if(key.length() == 1){
					continue;
				}
                //如果缓存中是否有药名匹配,进行后续处理
                if(luceneCache.exist(key)){
                    //TODO 生成URL
                    resultMap.put(key, "");
                }
//                System.out.println(offset.startOffset() + " - " + offset.endOffset() + " : " + key + " | " + type.type());
            }
            //关闭TokenStream（关闭StringReader）
            ts.end();   // Perform end-of-stream operations, e.g. set the final offset.
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放TokenStream的所有资源
            if(ts != null){
                try {
                    ts.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultMap;
    }
}
