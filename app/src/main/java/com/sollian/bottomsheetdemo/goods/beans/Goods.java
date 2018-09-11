package com.sollian.bottomsheetdemo.goods.beans;

/**
 * 商品
 *
 * @author admin on 2018/9/10.
 */
public class Goods implements IGoodsBase {
    private final String name;

    public Goods(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
