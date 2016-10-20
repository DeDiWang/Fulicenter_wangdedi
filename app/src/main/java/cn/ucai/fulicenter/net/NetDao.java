package cn.ucai.fulicenter.net;

import android.content.Context;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.utils.OkHttpUtils;

/**
 * Created by 11039 on 2016/10/17.
 */
public class NetDao {
    /**
     * 下载新品首页数据或精品二级页面数据
     * @param context
     * @param pageId
     * @param listener
     */
    public static void downloadGoodsList(Context context,int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener){
    OkHttpUtils<NewGoodsBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,catId+"")
                .addParam(I.PAGE_ID,pageId+"")
                .addParam(I.PAGE_SIZE,I.PAGE_SIZE_DEFAULT+"")
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }

    /**
     * 下载指定商品详情数据
     * @param context
     * @param goodsId
     * @param listener
     */
    public static void downloadGoodDetails(Context context, int goodsId,
                  OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener){
        OkHttpUtils<GoodsDetailsBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID,goodsId+"")
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }

    /**
     * 下载精品首页商品数据
     * @param context
     * @param listener
     */
    public static void downloadBoutique(Context context,
                 OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener){
        OkHttpUtils<BoutiqueBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }

    /**
     * 下载分类首页Group数据
     * @param context
     * @param listener
     */
    public static void downloadCategoryGroupList(Context context,
                 OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener){
        OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }
    /**
     * 下载分类首页Child数据
     * @param context
     * @param parentId
     * @param listener
     */
    public static void downloadCategoryChildList(Context context,
                 int parentId,
                 OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener){
        OkHttpUtils<CategoryChildBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID,parentId+"")
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }
}
