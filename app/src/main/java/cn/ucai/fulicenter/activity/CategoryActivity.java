package cn.ucai.fulicenter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.Serializable;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CategoryChildBean;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        CategoryChildBean childBean = (CategoryChildBean) getIntent().getSerializableExtra("childBean");
        Toast.makeText(CategoryActivity.this, childBean.toString(), Toast.LENGTH_SHORT).show();
    }
}
