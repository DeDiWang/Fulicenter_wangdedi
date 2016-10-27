package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.OkHttpUtils;

/**
 * Created by 11039 on 2016/10/27.
 */
public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<CartBean> list;

    public CartAdapter(Context context, ArrayList<CartBean> cartList) {
        this.context = context;
        this.list = cartList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_cart_goods, null);
        RecyclerView.ViewHolder holder = new CartViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CartBean cartBean = list.get(position);
        GoodsDetailsBean goods = cartBean.getGoods();
        CartViewHolder cartViewHolder = (CartViewHolder) holder;
        cartViewHolder.ivSelect.setChecked(false);
        cartViewHolder.tvGoodsCount.setText(String.valueOf(cartBean.getCount()));
        if (goods != null) {
            ImageLoader.downloadImg(context, cartViewHolder.ivGoodThumb, goods.getGoodsThumb());
            cartViewHolder.tvCartGoodName.setText(goods.getGoodsName());
            cartViewHolder.tvGoodsSum.setText(String.valueOf(Integer.parseInt(
                    goods.getCurrencyPrice().substring(goods.getCurrencyPrice().indexOf("￥") + 1))
                    * cartBean.getCount()));
        }
        cartViewHolder.layoutCartGood.setTag(cartBean);

        cartViewHolder.ivSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cartBean.setChecked(b);
                context.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void initData(ArrayList<CartBean> cartList) {
        if (list != null) {
            list.clear();
        }
        list.addAll(cartList);
        notifyDataSetChanged();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
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

            //对购物车中的商品项设置长按监听
            layoutCartGood.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final CartBean cartBean = (CartBean) layoutCartGood.getTag();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    AlertDialog dialog = builder.setTitle("将该商品从购物车中删除？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    NetDao.deleteCart(context, cartBean.getId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                                        @Override
                                        public void onSuccess(MessageBean result) {
                                            CommonUtils.showShortToast("删除成功");
                                            list.remove(cartBean);
                                            notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onError(String error) {
                                            L.e("CartAdapter error=" + error);
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create();
                    dialog.show();
                    return false;
                }
            });
        }
    }
}
