package com.wonders.xlab.healthcloud.dto.steward;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Created by lixuanwu on 2015/5/15.
 * 百度返回结果实体
 */
public class LBSResult {

    private int status;
    private int total;
    private int size;
    private List<LBSResultContent> contents;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<LBSResultContent> getContents() {
        return contents;
    }

    public void setContents(List<LBSResultContent> contents) {
        this.contents = contents;
    }
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
