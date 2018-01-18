package com.example.demo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.IView;
import com.example.demo.R;
import com.example.demo.bean.AddBean;
import com.example.demo.bean.LoginBean;
import com.example.demo.presenter.MyPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView {

    @BindView(R.id.login_username)
    EditText mLoginUsername;
    @BindView(R.id.login_password)
    EditText mLoginPassword;
    @BindView(R.id.login_regist)
    TextView mLoginRegist;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    private MyPresenter presenter;
    private String url = "http://120.27.23.105/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MyPresenter();
        presenter.attach(this);
    }

    @OnClick({R.id.login_regist, R.id.btn_login})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login_regist:
                Intent intent = new Intent(this, RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                //获取属性
                String username = mLoginUsername.getText().toString().trim();
                String password = mLoginPassword.getText().toString().trim();
                login(username, password);
                break;
        }
    }

    //逻辑判断
    private void login(String username, String password) {
        if (password.length() < 6) {
            Toast.makeText(this, "密码不能小于6位", Toast.LENGTH_SHORT).show();
        } else {
            presenter.getData(url, "login", username, password);

        }
    }

    @Override
    public void onSuccess(Object o) {
        LoginBean bean = (LoginBean) o;
        if (bean != null) {
            String msg = bean.getMsg();
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

            String code = bean.getCode();
            if (code.equals("0")) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onFailed(Exception e) {

    }

    @Override
    public void success(AddBean loginBean) {

    }

}
