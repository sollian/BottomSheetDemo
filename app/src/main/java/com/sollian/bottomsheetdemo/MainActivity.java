package com.sollian.bottomsheetdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sollian.bottomsheetdemo.bottomsheetbehavior.GoodsBehaviorFragment;
import com.sollian.bottomsheetdemo.bottomsheetdialogfragment.GoodsDialogFragment;

public class MainActivity extends AppCompatActivity {
    private GoodsBehaviorFragment goodsBehaviorFragment;
    private GoodsDialogFragment goodsDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openGoodsBehaviorFragment(View view) {
        if (goodsBehaviorFragment == null) {
            goodsBehaviorFragment = new GoodsBehaviorFragment();
        }

        if (goodsBehaviorFragment.isVisible()) {
            goodsBehaviorFragment.close();
        } else {
            goodsBehaviorFragment.show(this, R.id.container);
        }
    }

    public void openGoodsDialogFragment(View view) {
        if (goodsDialogFragment == null) {
            goodsDialogFragment = new GoodsDialogFragment();
        }

        if (goodsDialogFragment.isVisible()) {
            goodsDialogFragment.dismiss();
        } else {
            goodsDialogFragment.show(getSupportFragmentManager(), null);
        }
    }

    @Override
    public void onBackPressed() {
        if (goodsBehaviorFragment != null && goodsBehaviorFragment.isVisible()) {
            goodsBehaviorFragment.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
