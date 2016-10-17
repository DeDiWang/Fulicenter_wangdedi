package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.NewGoodsBean;

/**
 * Created by 11039 on 2016/10/17.
 */
public class GoodAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<NewGoodsBean> goodsList;

    public GoodAdapter(Context context, ArrayList<NewGoodsBean> goodsList) {
        this.context = context;
        this.goodsList = goodsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(context, R.layout.item_footer, null));
        } else {
            holder = new GoodViewHolder(View.inflate(context, R.layout.item_goods, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==I.TYPE_FOOTER){
            FooterViewHolder footerViewHolder= (FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText("加载更多...");
        }
        GoodViewHolder goodViewHolder= (GoodViewHolder) holder;
        NewGoodsBean good = goodsList.get(position);
        goodViewHolder.ivGoodAvatar.setImageResource(R.mipmap.goods_thumb);
        goodViewHolder.tvGoodName.setText(good.getGoodsName());
        goodViewHolder.tvGoodPrice.setText(good.getCurrencyPrice());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return goodsList != null ? goodsList.size() + 1 : 1;
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class GoodViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivGoodAvatar)
        ImageView ivGoodAvatar;
        @BindView(R.id.tvGoodName)
        TextView tvGoodName;
        @BindView(R.id.tvGoodPrice)
        TextView tvGoodPrice;

        GoodViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
