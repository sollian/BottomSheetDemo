package com.sollian.bottomsheetdemo.bottomsheetdialogfragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sollian.bottomsheetdemo.R;
import com.sollian.bottomsheetdemo.goods.GoodsManager;
import com.sollian.bottomsheetdemo.goods.beans.Goods;
import com.sollian.bottomsheetdemo.goods.beans.GoodsType;
import com.sollian.bottomsheetdemo.goods.beans.IGoodsBase;
import com.sollian.bottomsheetdemo.other.GoodsBaseAdapter;
import com.sollian.bottomsheetdemo.other.OnGoodsBaseClickListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 继承自BottomSheetDialogFragment，
 * 由于是使用Dialog实现，所以不可以添加其他的fragment
 *
 * @author admin on 2018/9/11.
 */
public class GoodsDialogFragment extends BottomSheetDialogFragment implements OnGoodsBaseClickListener, View.OnClickListener {
    protected RecyclerView vList;
    private TextView vTitle;
    private View vBack;

    protected final List<IGoodsBase> goodsBaseList = new ArrayList<>();

    private GoodsBaseAdapter adapter;

    private boolean isGoodsTypeShowing;

    private MyDialog dialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog = new MyDialog(getContext(), getTheme());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goods_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vBack = view.findViewById(R.id.back);
        vTitle = view.findViewById(R.id.title);

        view.findViewById(R.id.close).setOnClickListener(this);
        vBack.setOnClickListener(this);
        vBack.setVisibility(View.INVISIBLE);
        vTitle.setText("商品种类");

        vList = view.findViewById(R.id.list);
        vList.setLayoutManager(new LinearLayoutManager(getActivity()));
        goodsBaseList.clear();
        goodsBaseList.addAll(GoodsManager.getInstance().getGoodsTypeList());
        adapter = new GoodsBaseAdapter(goodsBaseList, this, getLayoutInflater());
        vList.post(new Runnable() {
            @Override
            public void run() {
                vList.setAdapter(adapter);
            }
        });

        isGoodsTypeShowing = true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //去掉父布局的背景
        View view = getView();
        if (view != null) {
            View parent = (View) view.getParent();
            if (parent != null) {
                parent.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                backToGoodsType();
                break;
            case R.id.close:
                dialog.cancel();
                break;
            default:
                break;
        }
    }

    protected void backToGoodsType() {
        goodsBaseList.clear();
        goodsBaseList.addAll(GoodsManager.getInstance().getGoodsTypeList());
        adapter.notifyDataSetChanged();

        vTitle.setText("商品种类");
        vBack.setVisibility(View.INVISIBLE);
        isGoodsTypeShowing = true;
    }

    @Override
    public void onGoodsBaseClick(IGoodsBase goodsBase) {
        if (goodsBase instanceof GoodsType) {
            //点击商品种类，跳转到该种类商品
            List<Goods> list = ((GoodsType) goodsBase).getGoodsList();
            goodsBaseList.clear();
            goodsBaseList.addAll(list);
            adapter.notifyDataSetChanged();

            vBack.setVisibility(View.VISIBLE);
            vTitle.setText(goodsBase.getName());
            isGoodsTypeShowing = false;
        } else if (goodsBase instanceof Goods) {
            //点击商品，弹出提示
            Toast.makeText(getActivity(), goodsBase.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    private class MyDialog extends BottomSheetDialog {
        MyDialog(@NonNull Context context, int theme) {
            super(context, theme);
        }

        @Override
        public void onBackPressed() {
            if (isGoodsTypeShowing) {
                cancel();
            } else {
                backToGoodsType();
            }
        }

        @Override
        public void cancel() {
            BottomSheetBehavior<?> behavior = getBehavior();
            if (behavior == null || behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                super.cancel();
            } else {
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        }


        @Override
        protected void onStart() {
            setup();
            super.onStart();
            final BottomSheetBehavior<?> behavior = getBehavior();
            if (behavior != null) {
                getView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }, 300);
            }
        }

        private void setup() {
            BottomSheetBehavior<?> behavior = getBehavior();
            if (behavior == null) {
                return;
            }
            behavior.setSkipCollapsed(true);
            behavior.setPeekHeight(0);
        }

        private BottomSheetBehavior<?> getBehavior() {
            try {
                Field field = BottomSheetDialog.class.getDeclaredField("mBehavior");
                if (field == null) {
                    return null;
                }
                field.setAccessible(true);
                return (BottomSheetBehavior<?>) field.get(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
