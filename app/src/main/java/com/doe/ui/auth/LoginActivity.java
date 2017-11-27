package com.doe.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doe.ui.MenuActivity;
import com.doe.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btnLogin.setOnClickListener(this);
        btnCadastrar.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(userIsAuthenticated()) {
            startMenuActivity();
        }
    }

    public void login(String email, String password) {
        if (!validate(email, password)) {
            Toast.makeText(getApplicationContext(), "Login inválido.", Toast.LENGTH_SHORT).show();
            return;
        }
        btnLogin.setEnabled(false);

        editor.putString("loginEmail", email);
        editor.putString("loginPassword", password);
        editor.commit();

        startMenuActivity();
    }

    public boolean validate(String email, String password) {
        boolean valid = true;

        if (email.isEmpty() ) {
            etEmail.setError("endereço de e-mail inválido");
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("obrigatório");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (password.isEmpty()) {
            etPassword.setError("obrigatório");
            valid = false;
        } else if (password.length() < 6) {
            etPassword.setError("pelo menos 6 caracteres");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;
    }

    private boolean userIsAuthenticated() {
        boolean authenticated = false;
        String email = this.spLogin.getString("loginEmail", null);
        String password = this.spLogin.getString("loginPassword", null);

        if(email != null && password != null) {
            authenticated = true;
        }

        return authenticated;
    }

    private void startMenuActivity() {
        Intent nextIntent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(nextIntent);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.bt_login) {
            login(etEmail.getText().toString(), etPassword.getText().toString());
        } else if (id == R.id.bt_cadastrar) {

            // Start the Signup activity
            Intent toSignUp = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(toSignUp);
        }
    }
}
