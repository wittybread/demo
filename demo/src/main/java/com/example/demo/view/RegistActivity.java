package com.example.demo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demo.IView;
import com.example.demo.R;
import com.example.demo.bean.AddBean;
import com.example.demo.bean.RegistBean;
import com.example.demo.presenter.MyPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends AppCompatActivity implements IView {

    @BindView(R.id.regist_back)
    ImageView mRegistBack;
    @BindView(R.id.regist_username)
    EditText mRegistUsername;
    @BindView(R.id.regist_password)
    EditText mRegistPassword;
    @BindView(R.id.regist_password_again)
    EditText mRegistPasswordAgain;
    @BindView(R.id.regist_email)
    EditText mRegistEmail;
    @BindView(R.id.regist_login)
    Button mRegistLogin;
    private MyPresenter presenter;
    private String url="http://120.27.23.105/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        presenter = new MyPresenter();
        presenter.attach(this);
    }

    @OnClick({R.id.regist_back, R.id.regist_login})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.regist_back:
                finish();
                break;
            case R.id.regist_login:
                String username = mRegistUsername.getText().toString().trim();
                String password = mRegistPassword.getText().toString().trim();
                String password2 = mRegistPasswordAgain.getText().toString().trim();
                String email = mRegistEmail.getText().toString().trim();


                regist(username,password,password2,email);
                break;
        }
    }
        //注册的有参方法
    private void regist(String username, String password, String password2, String email) {
        if(password.length()<6){
            Toast.makeText(this,"密码不能小于6位",Toast.LENGTH_SHORT).show();
        } else if(!password2.equals(password)){
            Toast.makeText(this,"两次密码不一致",Toast.LENGTH_SHORT).show();

        }else if(email.length()==0){
            Toast.makeText(this,"邮箱不能为空",Toast.LENGTH_SHORT).show();

        }else{
            presenter.getData(url,"reg",username,password);

        }
    }

    @Override
    public void onSuccess(Object o) {
        RegistBean bean = (RegistBean)o;
        if(bean!=null){
            String msg = bean.getMsg();
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();

            String code = bean.getCode();
            if(code.equals("0")){
                finish();
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
