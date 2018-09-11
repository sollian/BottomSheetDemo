package com.sollian.bottomsheetdemo.other;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sollian.bottomsheetdemo.R;
import com.sollian.bottomsheetdemo.goods.beans.IGoodsBase;

import java.util.List;

/**
 * @author admin on 2018/9/11.
 */
public final class GoodsBaseAdapter extends RecyclerView.Adapter<GoodsBaseAdapter.ViewHolder> implements View.OnClickListener {
    private final List<IGoodsBase> goodsBaseList;
    private OnGoodsBaseClickListener onGoodsBaseClickListener;
    private final LayoutInflater inflater;

    public GoodsBaseAdapter(List<IGoodsBase> goodsBaseList, OnGoodsBaseClickListener onGoodsBaseClickListener, LayoutInflater inflater) {
        this.goodsBaseList = goodsBaseList;
        this.onGoodsBaseClickListener = onGoodsBaseClickListener;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.list_item_goods_base, viewGroup, false);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.width = viewGroup.getWidth() - viewGroup.getPaddingLeft() - viewGroup.getPaddingRight()
                - params.leftMargin - params.rightMargin;
        if (params.width < 0) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        view.setLayoutParams(params);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        IGoodsBase goodsBase = goodsBaseList.get(position);
        viewHolder.vName.setText(goodsBase.getName());

        viewHolder.itemView.setTag(position);
        viewHolder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return goodsBaseList.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (onGoodsBaseClickListener != null) {
            onGoodsBaseClickListener.onGoodsBaseClick(goodsBaseList.get(position));
        }
    }

    public void setOnGoodsBaseClickListener(OnGoodsBaseClickListener onGoodsBaseClickListener) {
        this.onGoodsBaseClickListener = onGoodsBaseClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView vName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            vName = itemView.findViewById(R.id.name);
        }
    }
}
