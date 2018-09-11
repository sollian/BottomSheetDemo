package com.sollian.bottomsheetdemo.bottomsheetbehavior;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sollian.bottomsheetdemo.BaseFragment;
import com.sollian.bottomsheetdemo.R;
import com.sollian.bottomsheetdemo.goods.beans.IGoodsBase;
import com.sollian.bottomsheetdemo.other.GoodsBaseAdapter;
import com.sollian.bottomsheetdemo.other.OnGoodsBaseClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin on 2018/9/10.
 */
public class GoodsBaseFragment extends BaseFragment {
    protected RecyclerView vList;

    protected final List<IGoodsBase> goodsBaseList = new ArrayList<>();

    private OnGoodsBaseClickListener onGoodsBaseClickListener;
    private GoodsBaseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goods_base, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vList = view.findViewById(R.id.list);
        vList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new GoodsBaseAdapter(goodsBaseList, onGoodsBaseClickListener, getLayoutInflater());
        vList.setAdapter(adapter);
    }

    public void notifyDataSetChanged() {
        if (vList == null) {
            return;
        }

        if (adapter == null) {
            return;
        }

        adapter.notifyDataSetChanged();
    }

    public void setOnGoodsBaseClickListener(OnGoodsBaseClickListener onGoodsBaseClickListener) {
        this.onGoodsBaseClickListener = onGoodsBaseClickListener;
        if (adapter != null) {
            adapter.setOnGoodsBaseClickListener(onGoodsBaseClickListener);
        }
    }
}
