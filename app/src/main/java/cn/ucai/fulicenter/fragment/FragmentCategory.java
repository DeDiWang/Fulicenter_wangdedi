package cn.ucai.fulicenter.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.CategoryActivity;
import cn.ucai.fulicenter.adapter.CategoryAdapter;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.OkHttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategory extends Fragment {

    @BindView(R.id.elvCategory)
    ExpandableListView elvCategory;
    
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;
    CategoryAdapter mAdapter;

    public FragmentCategory() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        mContext=getContext();
        mGroupList=new ArrayList<>();
        mChildList=new ArrayList<>();
        initView();
        initData();
        setListener();
        return view;
    }

    private void setListener() {
        elvCategory.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String groupName = mGroupList.get(i).getName();
                int childId = mChildList.get(i).get(i1).getId();
                ArrayList<CategoryChildBean> list = mChildList.get(i);
                mContext.startActivity(new Intent(mContext, CategoryActivity.class).
                        putExtra("groupName",groupName)
                        .putExtra("childId",childId)
                        .putExtra("list",list));
                return false;
            }
        });
    }

    private void initData() {
        NetDao.downloadCategoryGroupList(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if(result!=null){
                    ArrayList<CategoryGroupBean> groupBeenList = ConvertUtils.array2List(result);
                    if(groupBeenList!=null){
                        mGroupList=groupBeenList;
                        int i=0;//大类别第一个
                        for(CategoryGroupBean groupBean:groupBeenList){
                            int parentId = groupBean.getId();
                            mChildList.add(new ArrayList<CategoryChildBean>());
                            findCategoryChildList(parentId,i);
                            i++;
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    int groupCount=0;
    private void findCategoryChildList(int parentId, final int i) {
        NetDao.downloadCategoryChildList(mContext, parentId, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                if(result!=null){
                    ArrayList<CategoryChildBean> childBeenList = ConvertUtils.array2List(result);
                    if(childBeenList!=null){
                        mChildList.set(i,childBeenList);
                    }
                }
                if(groupCount==mGroupList.size()){
                    L.e("mChildList>>>"+mChildList.toString());
                    L.e("mChildList.size="+mChildList.size());
                    mAdapter.addAll(mGroupList,mChildList);
                    L.e(mGroupList.toString()+">>>>>>>>>>>>>>>>>>>>>>"+mChildList.toString());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        mAdapter=new CategoryAdapter(mContext,mGroupList,mChildList);
        elvCategory.setAdapter(mAdapter);
        elvCategory.setGroupIndicator(null);
        elvCategory.setChildIndicator(null);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.release();
    }
}
