package com.sollian.bottomsheetdemo.goods;

import com.sollian.bottomsheetdemo.goods.beans.Goods;
import com.sollian.bottomsheetdemo.goods.beans.GoodsType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin on 2018/9/10.
 */
public class GoodsManager {
    private final List<GoodsType> goodsTypeList = new ArrayList<>();

    private static final GoodsManager INSTANCE = new GoodsManager();

    public static GoodsManager getInstance() {
        return INSTANCE;
    }

    private GoodsManager() {
        int count = 20;

        String[] typeNames = {
                "家电",
                "手机",
                "数码",
                "电脑",
                "办公",
                "家居",
                "服装",
                "清洁",
                "珠宝",
                "箱包",
                "户外",
                "体育",
                "房产",
                "汽车",
                "玩具",
                "乐器",
                "视频",
                "医药",
                "图书",
                "旅游",
                "保险",
                "出行",
        };

        for (String typeName : typeNames) {
            GoodsType goodsType = new GoodsType(typeName);
            for (int i = 0; i < count; i++) {
                goodsType.addGoods(new Goods(goodsType.getName() + i));
            }
            goodsTypeList.add(goodsType);
        }
    }

    public List<GoodsType> getGoodsTypeList() {
        return goodsTypeList;
    }
}
