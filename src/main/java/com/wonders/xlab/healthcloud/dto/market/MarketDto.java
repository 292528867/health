package com.wonders.xlab.healthcloud.dto.market;

import com.wonders.xlab.healthcloud.entity.market.Market;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by mars on 15/7/11.
 */
public class MarketDto {

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
    private String iconUrl;

    public Market toNewMarket() {
        Market market = new Market(
                name,
                Double.parseDouble(price),
                medicineUrl,
                iconUrl
        );
        return market;
    }

    public Market updateMarket(Market market) {
        market.setName(name);
        market.setPrice(Double.parseDouble(price));
        market.setMedicineUrl(medicineUrl);
        market.setIconUrl(iconUrl);
        return market;
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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

}
