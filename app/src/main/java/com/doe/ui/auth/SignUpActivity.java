package com.doe.ui.auth;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.doe.R;
import com.doe.consumer.user.UserConsumer;
import com.doe.models.Organization;
import com.doe.models.User;
import com.doe.ui.utils.MaskType;
import com.doe.ui.utils.MaskUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Patterns.EMAIL_ADDRESS;
import static android.util.Patterns.PHONE;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";

    private String userType;

    @BindView(R.id.input_name) EditText nameText;
    @BindView(R.id.input_email) EditText emailText;
    @BindView(R.id.input_password) EditText passwordText;
    @BindView(R.id.btn_signup) Button signupButton;
    @BindView(R.id.rb_contributor) RadioButton rbContributor;
    @BindView(R.id.rb_ong) RadioButton rbOng;
    @BindView(R.id.rg_user_type) RadioGroup rgUserType;
    @BindView(R.id.input_cpf) EditText cpfText;
    @BindView(R.id.input_cnpj) EditText cnpjText;
    @BindView(R.id.input_endereco) EditText enderecoText;
    @BindView(R.id.input_fone) EditText foneText;
    @BindView(R.id.btn_login) TextView loginLink;
    @BindView(R.id.contributor_data) LinearLayout containerContributor;
    @BindView(R.id.ong_data) LinearLayout containerOng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        cpfText.addTextChangedListener(MaskUtil.insert(cpfText, MaskType.CPF));
        cnpjText.addTextChangedListener(MaskUtil.insert(cnpjText, MaskType.CNPJ));
        foneText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        signupButton.setOnClickListener(this);
        loginLink.setOnClickListener(this);
        rgUserType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton button = group.findViewById(checkedId);
                userType = button.getText().toString();
                updateUi(userType);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void signUp(String email, String password) {
        if (!validate()) {
            onSignupFailed();
            return;
        }
        signupButton.setEnabled(false);
        onSignupSuccess();
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);

        if (this.userType.equals("Colaborador")) {
            saveUser();
        } else if (this.userType.equals("Organização")) {
            saveOrganization();
        }

        Intent toLogin = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(toLogin);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Dados inválidos", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("pelo menos 3 caracteres");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("endereço de e-mail inválido");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordText.setError("pelo menos 6 caracteres");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (containerContributor.getVisibility() == View.VISIBLE) {
            String cpf = cpfText.getText().toString();

            if (cpf.isEmpty()) {
                cpfText.setError("CPF inválido");
                valid = false;
            } else {
                cpfText.setError(null);
            }
        }

        if (containerOng.getVisibility() == View.VISIBLE) {
            String cnpj = cnpjText.getText().toString();
            String phone = foneText.getText().toString();
            String endereco = enderecoText.getText().toString();

            if (cnpj.isEmpty()) {
                cnpjText.setError("CPF inválido");
                valid = false;
            } else {
                cnpjText.setError(null);
            }

            if (endereco.isEmpty()) {
                enderecoText.setError("Endereço inválido");
                valid = false;
            }

            if (foneText.isDirty() || !PHONE.matcher(phone).matches()) {
                foneText.setError("Telefone inválido");
                valid = false;
            }

        }

        if (!rbOng.isChecked() && !rbContributor.isChecked()) {
            valid = false;
        }

        return valid;
    }

    private void saveUser() {
        UserConsumer userConsumer = new UserConsumer();
        User user = new User();

        user.setName(this.nameText.getText().toString());
        user.setEmail(this.emailText.getText().toString());
        user.setPassword(this.passwordText.getText().toString());
        user.setCpf(cpfText.getText().toString());

        userConsumer.postCadastro(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Foi cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignUpActivity.this,"Deu ruim", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveOrganization() {
        Organization organization = new Organization();

        organization.setName(this.nameText.getText().toString());
        organization.setDescOrganization(this.nameText.getText().toString());
        organization.setEmail(this.emailText.getText().toString());
        organization.setPassword(this.passwordText.getText().toString());
        organization.setCnpj(cnpjText.getText().toString());
        organization.setEndereco(enderecoText.getText().toString());
        organization.setFone(foneText.getText().toString());

    }

    public void updateUi(String userType) {
        if (userType.equals("Colaborador")) {
            containerContributor.setVisibility(View.VISIBLE);
            containerOng.setVisibility(View.GONE);
        } else if (userType.equals("Organização")) {
            containerOng.setVisibility(View.VISIBLE);
            containerContributor.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_signup) {
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            signUp(email, password);

        } else if (id == R.id.btn_login) {
            Intent toLogin = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(toLogin);
            finish();
        }
    }
}
