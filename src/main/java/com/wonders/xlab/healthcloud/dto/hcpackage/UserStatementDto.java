package com.wonders.xlab.healthcloud.dto.hcpackage;

import java.util.Date;

/**
 * Created by mars on 15/7/8.
 */
public class UserStatementDto {

    /** 记录id */
    private Long statementId;

    /** 记录 */
    private String statement;

    /** 记录时间 */
    private Date time;

    public UserStatementDto() {
    }

    public UserStatementDto(Long statementId, String statement, Date time) {
        this.statementId = statementId;
        this.statement = statement;
        this.time = time;
    }

    public Long getStatementId() {
        return statementId;
    }

    public void setStatementId(Long statementId) {
        this.statementId = statementId;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
