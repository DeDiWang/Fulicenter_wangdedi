package cn.ucai.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etNick)
    EditText etNick;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etPasswordDouble)
    EditText etPasswordDouble;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnRegister)
    public void onClick() {
        String userName = etUserName.getText().toString();
        if (userName == null || userName.length() == 0) {
            etUserName.setError("用户名不能为空");
            etUserName.requestFocus();
            return;
        }
        if (!userName.matches("[a-zA-Z]\\w{5,15}")) {
            etUserName.setError("用户名以字母开头，6-16位");
            etUserName.requestFocus();
            return;
        }
        String nick = etNick.getText().toString();
        if (nick == null || nick.length() == 0) {
            etNick.setError("昵称不能为空");
            etNick.requestFocus();
            return;
        }
        String password = etPassword.getText().toString();
        if (password == null || password.length() == 0) {
            etPassword.setError("密码不能为空");
            etPassword.requestFocus();
            return;
        }
        String passwordDouble = etPasswordDouble.getText().toString();
        if (!password.equals(passwordDouble)) {
            etPasswordDouble.setError("两次密码不一致");
            etPasswordDouble.requestFocus();
            return;
        }
        //向服务端发注册请求
        NetDao.register(this, userName, nick, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if (result.getRetCode() == 0) {
                    Toast.makeText(RegisterActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onError(String error) {

            }
        });
        //跳转会登录界面
        startActivity(new Intent(this, LoginActivity.class).putExtra("userName", userName));
        //并关掉注册界面
        MFGT.finish(this);
    }

    @OnClick(R.id.ivBack)
    public void onBackClick() {
        MFGT.finish(this);
    }
}
