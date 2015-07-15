package com.wonders.xlab.healthcloud.dto.store;

import com.wonders.xlab.healthcloud.entity.market.Store;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by mars on 15/7/11.
 */
public class StoreDto {

    /** 名称 */
    @NotNull(message = "名称不能为空")
    private String name;

    /** 价格 */
    @NotNull(message = "价钱不能为空")
    @Pattern(regexp = "^-?\\d+(.\\d{1,2})?$", message = "价钱格式不正确")
    private String price;

    /** 医药地址 */
    @NotNull(message = "医药链接不能为空")
    private String medicineUrl;

    /** 图片 */
    @NotNull(message = "图片图能为空")
    private String picUrl;

    /** 标签 */
    private String tag;

    /** 简述 */
    @NotNull(message = "简述不能为空")
    private String description;

    @NotNull(message = "顺序不能为空")
    private String position;

    public Store toNewStore() {
        Store store = new Store(
                name,
                Double.parseDouble(price),
                medicineUrl,
                picUrl,
                description
        );
        if (tag != null){
            store.setTag(Store.Tag.values()[Integer.parseInt(tag)]);
        }
        store.setPosition(Integer.valueOf(position));
        return store;
    }

    public Store updateStore(Store store) {
        store.setName(name);
        store.setPrice(Double.parseDouble(price));
        store.setMedicineUrl(medicineUrl);
        store.setPicUrl(picUrl);
        store.setDescription(description);
        store.setPosition(Integer.valueOf(position));
        if (tag != null)
            store.setTag(Store.Tag.values()[Integer.parseInt(tag)]);
        return store;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMedicineUrl() {
        return medicineUrl;
    }

    public void setMedicineUrl(String medicineUrl) {
        this.medicineUrl = medicineUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
