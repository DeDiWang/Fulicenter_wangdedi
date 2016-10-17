package cn.ucai.fulicenter.net;

import android.content.Context;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.utils.OkHttpUtils;

/**
 * Created by 11039 on 2016/10/17.
 */
public class NetDao {
    /**
     * 下载新品首页数据
     * @param context
     * @param pageId
     * @param listener
     */
    public static void downloadGoodsList(Context context, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener){
    OkHttpUtils<NewGoodsBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,I.CAT_ID+"")
                .addParam(I.PAGE_ID,pageId+"")
                .addParam(I.PAGE_SIZE,I.PAGE_SIZE_DEFAULT+"")
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }
}
