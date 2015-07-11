package com.wonders.xlab.healthcloud.dto.market;

import com.wonders.xlab.healthcloud.entity.market.Market;

/**
 * Created by mars on 15/7/11.
 */
public class MarketDto {

    /** 名称 */
    private String name;

    /** 价格 */
    private String price;

    /** 医药地址 */
    private String medicineUrl;

    /** 图片 */
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


}
