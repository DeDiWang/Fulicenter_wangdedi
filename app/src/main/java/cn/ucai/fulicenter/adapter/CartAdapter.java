package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.utils.ImageLoader;

/**
 * Created by 11039 on 2016/10/27.
 */
public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<CartBean> list;

    public CartAdapter(Context context, ArrayList<CartBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_cart_goods, null);
        RecyclerView.ViewHolder holder = new CartViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CartBean cartBean = list.get(position);
        GoodsDetailsBean goods = cartBean.getGoods();
        CartViewHolder cartViewHolder= (CartViewHolder) holder;
        cartViewHolder.ivSelect.setChecked(cartBean.isChecked());
        if(goods!=null){
            ImageLoader.downloadImg(context,cartViewHolder.ivGoodThumb,goods.getGoodsThumb());
            cartViewHolder.tvCartGoodName.setText(goods.getGoodsName());
            cartViewHolder.tvGoodsSum.setText(goods.getCurrencyPrice());
        }
    }

    @Override
    public int getItemCount() {
        return list!=null? list.size():0;
    }

    public void initData(ArrayList<CartBean> cartList) {
        if(list!=null){
            list.clear();
        }
        list.addAll(cartList);
        notifyDataSetChanged();
    }

    class CartViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivSelect)
        CheckBox ivSelect;
        @BindView(R.id.ivGoodThumb)
        ImageView ivGoodThumb;
        @BindView(R.id.tvCartGoodName)
        TextView tvCartGoodName;
        @BindView(R.id.tvGoodsCount)
        TextView tvGoodsCount;
        @BindView(R.id.tvGoodsSum)
        TextView tvGoodsSum;
        @BindView(R.id.layout_cart_good)
        RelativeLayout layoutCartGood;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
