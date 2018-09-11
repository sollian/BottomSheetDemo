package com.sollian.bottomsheetdemo.bottomsheetbehavior;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sollian.bottomsheetdemo.BaseFragment;
import com.sollian.bottomsheetdemo.R;
import com.sollian.bottomsheetdemo.goods.beans.Goods;
import com.sollian.bottomsheetdemo.goods.beans.GoodsType;
import com.sollian.bottomsheetdemo.goods.beans.IGoodsBase;
import com.sollian.bottomsheetdemo.other.OnGoodsBaseClickListener;

/**
 * 展示商品和商品种类的包装fragment
 *
 * @author admin on 2018/9/10.
 */
public class SelectGoodsFragment extends BaseFragment implements View.OnClickListener, OnGoodsBaseClickListener {
    private TextView vTitle;
    private View vBack;

    protected GoodsFragment goodsFragment;
    protected GoodsTypeFragment goodsTypeFragment;

    private OnCloseListener onCloseListener;

    public static SelectGoodsFragment newInstance() {
        return new SelectGoodsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_goods, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vBack = view.findViewById(R.id.back);
        vTitle = view.findViewById(R.id.title);

        view.findViewById(R.id.close).setOnClickListener(this);
        vBack.setOnClickListener(this);
        vBack.setVisibility(View.INVISIBLE);
        vTitle.setText("商品种类");

        goodsTypeFragment = GoodsTypeFragment.newInstance();
        goodsTypeFragment.show(getActivity(), R.id.goods_container);
        goodsTypeFragment.setOnGoodsBaseClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                backToGoodsType();
                break;
            case R.id.close:
                if (onCloseListener != null) {
                    onCloseListener.onClose();
                } else {
                    close();
                }
                break;
            default:
                break;
        }
    }

    protected void backToGoodsType() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .hide(goodsFragment)
                .show(goodsTypeFragment)
                .commitAllowingStateLoss();

        vTitle.setText("商品种类");
        vBack.setVisibility(View.INVISIBLE);
    }

    @Override
    public void close() {
        if (goodsFragment != null && goodsFragment.isAdded()) {
            goodsFragment.close();
        }
        if (goodsTypeFragment != null && goodsTypeFragment.isAdded()) {
            goodsTypeFragment.close();
        }
        super.close();
    }

    @Override
    public void onGoodsBaseClick(IGoodsBase goodsBase) {
        if (goodsBase instanceof GoodsType) {
            //点击商品种类，跳转到该种类商品
            if (goodsFragment == null) {
                goodsFragment = GoodsFragment.newInstance(((GoodsType) goodsBase).getGoodsList());
                goodsFragment.setOnGoodsBaseClickListener(this);
            } else {
                goodsFragment.update(((GoodsType) goodsBase).getGoodsList());
            }

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                    .hide(goodsTypeFragment);
            if (goodsFragment.isAdded()) {
                transaction.show(goodsFragment);
            } else {
                transaction.add(R.id.goods_container, goodsFragment);
            }
            transaction.commitAllowingStateLoss();

            vBack.setVisibility(View.VISIBLE);
            vTitle.setText(goodsBase.getName());
        } else if (goodsBase instanceof Goods) {
            //点击商品，弹出提示
            Toast.makeText(getActivity(), goodsBase.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
    }

    public interface OnCloseListener {
        void onClose();
    }
}
