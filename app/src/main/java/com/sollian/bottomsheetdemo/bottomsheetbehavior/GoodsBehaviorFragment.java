package com.sollian.bottomsheetdemo.bottomsheetbehavior;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.NestedScrollingChild;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.sollian.bottomsheetdemo.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * 使用BottomSheetBehavior实现，可以添加其他的fragment
 *
 * @author admin on 2018/9/10.
 */
public class GoodsBehaviorFragment extends SelectGoodsFragment {
    private BottomSheetBehavior<ViewGroup> behavior;

    private final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new MyOnGlobalLayoutListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_goods_behavior, container, false);
        FrameLayout frameLayout = root.findViewById(R.id.root);
        frameLayout.addView(view, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);

        view.findViewById(R.id.touch_outside).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

        behavior = BottomSheetBehavior.from((ViewGroup) view.findViewById(R.id.root));
        behavior.setHideable(true);
        behavior.setPeekHeight(0);
        behavior.setSkipCollapsed(true);
        behavior.setBottomSheetCallback(new MyBottomSheetCallback());
        view.post(new Runnable() {
            @Override
            public void run() {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        setOnCloseListener(new OnCloseListener() {
            @Override
            public void onClose() {
                close();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        View view = getView();
        if (view != null) {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener);
        }
    }

    @Override
    public void close() {
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void onBackPressed() {
        if (goodsFragment != null && goodsFragment.isVisible()) {
            backToGoodsType();
        } else {
            close();
        }
    }

    private class MyBottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                GoodsBehaviorFragment.super.close();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    }

    private class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            updateBehavior();
        }

        /**
         * BottomSheetBehavior#mNestedScrollingChildRef字段保存了嵌套滑动的子滑动View。
         * 所以这里根据当前展示的fragment手动设置一下BottomSheetBehavior#mNestedScrollingChildRef
         */
        private void updateBehavior() {
            View list = null;
            if (goodsFragment != null && goodsFragment.isVisible()) {
                View view = goodsFragment.getView();
                list = findScrollingChild(view);

            } else if (goodsTypeFragment != null && goodsTypeFragment.isVisible()) {
                View view = goodsTypeFragment.getView();
                list = findScrollingChild(view);
            }

            if (list != null) {
                try {
                    Field field = BottomSheetBehavior.class.getDeclaredField("mNestedScrollingChildRef");
                    if (field != null) {
                        field.setAccessible(true);
                        field.set(behavior, new WeakReference<>(list));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private View findScrollingChild(View view) {
            if (view instanceof NestedScrollingChild) {
                return view;
            }
            if (view instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) view;
                for (int i = 0, count = group.getChildCount(); i < count; i++) {
                    View scrollingChild = findScrollingChild(group.getChildAt(i));
                    if (scrollingChild != null) {
                        return scrollingChild;
                    }
                }
            }
            return null;
        }
    }
}
