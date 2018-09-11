package com.sollian.bottomsheetdemo.goods.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品种类
 *
 * @author admin on 2018/9/10.
 */
public class GoodsType implements IGoodsBase {
    private final String name;
    private final List<Goods> goodsList;

    public GoodsType(String name) {
        this.name = name;
        goodsList = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void addGoods(Goods goods) {
        goodsList.add(goods);
    }
}
