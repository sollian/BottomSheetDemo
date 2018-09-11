package com.sollian.bottomsheetdemo.bottomsheetbehavior;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sollian.bottomsheetdemo.goods.GoodsManager;

/**
 * 展示商品种类的fragment
 * @author admin on 2018/9/10.
 */
public class GoodsTypeFragment extends GoodsBaseFragment {

    public static GoodsTypeFragment newInstance() {
        return new GoodsTypeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goodsBaseList.clear();
        goodsBaseList.addAll(GoodsManager.getInstance().getGoodsTypeList());
    }
}
