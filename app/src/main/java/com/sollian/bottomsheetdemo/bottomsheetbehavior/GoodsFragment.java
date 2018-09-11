package com.sollian.bottomsheetdemo.bottomsheetbehavior;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sollian.bottomsheetdemo.goods.beans.Goods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 展示商品的fragment
 *
 * @author admin on 2018/9/10.
 */
public class GoodsFragment extends GoodsBaseFragment {
    public static GoodsFragment newInstance(List<Goods> goodsList) {
        GoodsFragment fragment = new GoodsFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", new ArrayList<>(goodsList));
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        Collection<Goods> goodsList = (Collection<Goods>) bundle.getSerializable("data");

        goodsBaseList.clear();
        if (goodsList != null && !goodsList.isEmpty()) {
            goodsBaseList.addAll(goodsList);
        }
    }

    public void update(List<Goods> goodsList) {
        goodsBaseList.clear();

        if (goodsList != null && !goodsList.isEmpty()) {
            goodsBaseList.addAll(goodsList);

            //更新arguments中保存的值
            Bundle bundle = getArguments();
            if (bundle != null) {
                bundle.putSerializable("data", new ArrayList<>(goodsList));
            }
        }

        notifyDataSetChanged();
    }
}
