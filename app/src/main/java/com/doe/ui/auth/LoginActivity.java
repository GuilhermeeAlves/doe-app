package com.doe.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.doe.ui.MenuActivity;
import com.doe.ui.donation.DonationListActivity;
import com.doe.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.et_email) EditText etEmail;
    @BindView(R.id.et_password) EditText etPassword;
    @BindView(R.id.bt_login) Button btnLogin;
    @BindView(R.id.bt_cadastrar) Button btnCadastrar;

    private SharedPreferences spLogin;
    private SharedPreferences.Editor editor;
    public static final String NOME_ARQUIVO = "login_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.spLogin = getApplicationContext().getSharedPreferences(NOME_ARQUIVO, MODE_APPEND);
        this.editor = this.spLogin.edit();
        this.editor.apply();

        if(userIsAuthenticated()) {
            startMenuActivity();
        }

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMenuActivity();
            }
        });
    }

    public void login(String email) {
        editor.putString("login", email);
        editor.commit();
    }

    private boolean userIsAuthenticated() {
        boolean authenticated = false;
        String login = this.spLogin.getString("login", null);

        if(login != null) {
            authenticated = true;
        }

        return authenticated;
    }

    private void startMenuActivity() {
        Intent nextIntent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(nextIntent);
    }
}
