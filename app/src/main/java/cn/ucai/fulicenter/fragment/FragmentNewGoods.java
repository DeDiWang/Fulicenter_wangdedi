package cn.ucai.fulicenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.GoodAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNewGoods extends Fragment {

    @BindView(R.id.rvGoodsList)
    RecyclerView rvGoodsList;

    public FragmentNewGoods() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        rvGoodsList.setAdapter(new GoodAdapter(getContext(),null));
        ButterKnife.bind(this, view);
        return view;
    }

}
