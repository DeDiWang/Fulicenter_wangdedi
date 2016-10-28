package cn.ucai.fulicenter.net;

import android.app.Activity;
import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.utils.MD5;
import cn.ucai.fulicenter.utils.OkHttpUtils;

public class NetDao {
    //下载新品首页数据或精品二级页面数据
    public static void downloadGoodsList(Context context,int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener){
    OkHttpUtils<NewGoodsBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,catId+"")
                .addParam(I.PAGE_ID,pageId+"")
                .addParam(I.PAGE_SIZE,I.PAGE_SIZE_DEFAULT+"")
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }


    //下载指定商品详情数据
    public static void downloadGoodDetails(Context context, int goodsId,
                  OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener){
        OkHttpUtils<GoodsDetailsBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID,goodsId+"")
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }

    //下载精品首页商品数据
    public static void downloadBoutique(Context context,
                 OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener){
        OkHttpUtils<BoutiqueBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }

    //下载分类首页Group数据
    public static void downloadCategoryGroupList(Context context,
                 OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener){
        OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }

    //下载分类首页Child数据
    public static void downloadCategoryChildList(Context context,
                 int parentId,
                 OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener){
        OkHttpUtils<CategoryChildBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID,parentId+"")
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }

    //http://101.251.196.90:8000/FuLiCenterServerV2.0/findGoodsDetails?cat_id=345&page_id=1&page_size=10
    //下载分类二级页面一组商品数据
    public static void downloadCategory2ChildList(Context context,int catId,int pageId,
                                                  OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener){
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                .addParam(I.Category.KEY_CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }

    //用户注册
    public static void register(Context context, String userName,
                                String nick, String password,
                                OkHttpUtils.OnCompleteListener<Result> listener){
        OkHttpUtils<Result> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME,userName)
                .addParam(I.User.NICK,nick)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(Result.class)
                .post()
                .execute(listener);
    }

    //用户登录
    public static void login(Context context,String userName,String password,
                             OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,userName)
                .addParam(I.User.PASSWORD,MD5.getMessageDigest(password))
                .targetClass(String.class)
                .execute(listener);
    }

    //更新用户昵称
    public static void updateNick(Activity context, String muserName,String newNick,OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME,muserName)
                .addParam(I.User.NICK,newNick)
                .targetClass(String.class)
                .execute(listener);
    }
    //更新头像的方法
    ///storage/emulated/0/Android/data/cn.ucai.fulicenter/files/Pictures/
    // storage/emulated/0/Android/data/cn.ucai.fulicenter/files/Pictures/user_avatar/wangdedi123.jpg
    //http://101.251.196.90:8000/FuLiCenterServerV2.0/updateAvatar?name_or_hxid=a952702&avatarType=user_avatar
    public static void updateAvatar(Activity context, String muserName, File file, OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID,muserName)
                .addParam(I.AVATAR_TYPE,I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .targetClass(String.class)
                .post()
                .execute(listener);
    }
    //下载收藏商品数量
    public static void findCollectCount(Context context,String userName,
                                        OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME,userName)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    //http://101.251.196.90:8000/FuLiCenterServerV2.0/findCollects?
    // userName=a952702&page_id=1&page_size=10
    //下载收藏的商品列表
    public static void downloadCollectGoods(Context context, String userName, int pageId,
                                            OkHttpUtils.OnCompleteListener<CollectBean[]> listener){
        OkHttpUtils<CollectBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME,userName)
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(CollectBean[].class)
                .execute(listener);
    }
//http://101.251.196.90:8000/FuLiCenterServerV2.0/deleteCollect?
// goods_id=7677&userName=a952702
    //删除收藏的商品
    public static void deleteCollectGood(Context context,int goodsId,String userName,
                                         OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsId))
                .addParam(I.Collect.USER_NAME,userName)
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    //添加收藏
    public static void addCollectGood(Context context,int goodsId,String userName,
                                      OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsId))
                .addParam(I.Collect.USER_NAME,userName)
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    //http://101.251.196.90:8000/FuLiCenterServerV2.0/isCollect?goods_id=7677&userName=a952702
    //判断用户是否收藏该商品
    public static void isCollect(Context context,int goodsId,String userName,
                                 OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_IS_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsId))
                .addParam(I.Collect.USER_NAME,userName)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    //http://101.251.196.90:8000/FuLiCenterServerV2.0/addCart?
    // goods_id=7677&userName=a952702&count=1&isChecked=true
    //添加商品到购物车
    public static void addCart(Context context,int goodsId,String userName,
                               int count,boolean isChecked,
                               OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.Cart.GOODS_ID,String.valueOf(goodsId))
                .addParam(I.Cart.USER_NAME,userName)
                .addParam(I.Cart.COUNT,String.valueOf(count))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(isChecked))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    //http://101.251.196.90:8000/FuLiCenterServerV2.0/findCarts?
    // userName=a952702
    //查询购物车
    public static void findCarts(Context context, String userName,
                                 OkHttpUtils.OnCompleteListener<CartBean[]> listener){
        OkHttpUtils<CartBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME,userName)
                .targetClass(CartBean[].class)
                .execute(listener);
    }
    //http://101.251.196.90:8000/FuLiCenterServerV2.0/deleteCart?id=443
    //删除购物车中的商品
    public static void deleteCart(Context context,int id,
                                  OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID,String.valueOf(id))
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    //更新购物车商品数量
    public static void updateCartCount(Context context,int id,int count,boolean isChecked,
                                       OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID,String.valueOf(id))
                .addParam(I.Cart.COUNT,String.valueOf(count))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(isChecked))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

}
