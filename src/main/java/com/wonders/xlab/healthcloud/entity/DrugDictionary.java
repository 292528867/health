package com.wonders.xlab.healthcloud.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by wukai on 15/7/3.
 */
@Entity
@Table(name = "drug_dictionary")
public class DrugDictionary extends AbstractPersistable<Long> {
    //药品ID
    private Long drugId;
    //统编代码
    private String drugCode;
    //药品商品名
    private String name;
    //通用名
    private String gname;
    //剂型
    private String form;
    //单位
    private String msunitno;
    //生产企业
    private String producer;
    //成分规格
    private String cSpec;
    //包装规格
    private String pSpec;
    //批准文号
    private String ratifier;
    //中西药标志 X西药 Z中药
    private String drugKind;
    //进口标志 1国产 2合资 9进口
    private String importer;
    //医保标志 1医保 2自费
    private String healthcare;
    //OTC标志 boolean
    private String otc;
    //本位码
    private String standardCode;
    //一级专业分类ID
    private String drugClass1;
    //二级专业分类ID
    private String drugClass2;
    //三级专业分类ID
    private String drugClass3;
    //四级专业分类ID
    private String drugClass4;
    private Date createDate;
    private Date modifyDate;
    private String createUser;
    //是否停用 1启用 0停用
    private String prohibit;
    //药品通用名拼音
    private String firstLetter;
    private String mark;
    private String memo1;
    //商品分类对应drug_class的二级分类
    private String goodsClass;
    //科室分类，按照科室查找药品
    private String deptClass;
    //人群分类
    private String peopleClass;

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getMsunitno() {
        return msunitno;
    }

    public void setMsunitno(String msunitno) {
        this.msunitno = msunitno;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getcSpec() {
        return cSpec;
    }

    public void setcSpec(String cSpec) {
        this.cSpec = cSpec;
    }

    public String getpSpec() {
        return pSpec;
    }

    public void setpSpec(String pSpec) {
        this.pSpec = pSpec;
    }

    public String getRatifier() {
        return ratifier;
    }

    public void setRatifier(String ratifier) {
        this.ratifier = ratifier;
    }

    public String getDrugKind() {
        return drugKind;
    }

    public void setDrugKind(String drugKind) {
        this.drugKind = drugKind;
    }

    public String getImporter() {
        return importer;
    }

    public void setImporter(String importer) {
        this.importer = importer;
    }

    public String getHealthcare() {
        return healthcare;
    }

    public void setHealthcare(String healthcare) {
        this.healthcare = healthcare;
    }

    public String getOtc() {
        return otc;
    }

    public void setOtc(String otc) {
        this.otc = otc;
    }

    public String getStandardCode() {
        return standardCode;
    }

    public void setStandardCode(String standardCode) {
        this.standardCode = standardCode;
    }

    public String getDrugClass1() {
        return drugClass1;
    }

    public void setDrugClass1(String drugClass1) {
        this.drugClass1 = drugClass1;
    }

    public String getDrugClass2() {
        return drugClass2;
    }

    public void setDrugClass2(String drugClass2) {
        this.drugClass2 = drugClass2;
    }

    public String getDrugClass3() {
        return drugClass3;
    }

    public void setDrugClass3(String drugClass3) {
        this.drugClass3 = drugClass3;
    }

    public String getDrugClass4() {
        return drugClass4;
    }

    public void setDrugClass4(String drugClass4) {
        this.drugClass4 = drugClass4;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getProhibit() {
        return prohibit;
    }

    public void setProhibit(String prohibit) {
        this.prohibit = prohibit;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    public String getGoodsClass() {
        return goodsClass;
    }

    public void setGoodsClass(String goodsClass) {
        this.goodsClass = goodsClass;
    }

    public String getDeptClass() {
        return deptClass;
    }

    public void setDeptClass(String deptClass) {
        this.deptClass = deptClass;
    }

    public String getPeopleClass() {
        return peopleClass;
    }

    public void setPeopleClass(String peopleClass) {
        this.peopleClass = peopleClass;
    }
}
