package com.wonders.xlab.healthcloud.dto.emchat;

/**
 * Created by lixuanwu on 15/7/6.
 */
public class ChatGroupsRequestBody {

    /**
     * 群组名称, 此属性为必须的
     */
    private String groupname;

    /**
     * 群组描述, 此属性为必须的
     */
    private String desc;

    /**
     * 是否是公开群, 此属性为必须的,为false时为私有群
     */
    private Boolean _public;

    /**
     * 群组成员最大数(包括群主), 值为数值类型,默认值200,此属性为可选的
     */
    private Integer maxusers;

    /**
     * 加入公开群是否需要批准, 默认值是false（加群不需要群主批准）, 此属性为可选的,只作用于公开群
     */
    private Boolean approval;

    /**
     * 群组的管理员, 此属性为必须的
     */
    private String owner;


    public ChatGroupsRequestBody(String groupname, String desc, Boolean _public, Integer maxusers, Boolean approval, String owner) {
        this.groupname = groupname;
        this.desc = desc;
        this._public = _public;
        this.maxusers = maxusers;
        this.approval = approval;
        this.owner = owner;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean get_public() {
        return _public;
    }

    public void set_public(Boolean _public) {
        this._public = _public;
    }

    public Integer getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(Integer maxusers) {
        this.maxusers = maxusers;
    }

    public Boolean getApproval() {
        return approval;
    }

    public void setApproval(Boolean approval) {
        this.approval = approval;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
