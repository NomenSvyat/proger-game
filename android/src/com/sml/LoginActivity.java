package com.sml;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sml.github.CodeFetcher;
import com.sml.models.Models;
import com.sml.network.RestClient;
import com.sml.utils.AlertService;
import com.sml.utils.SettingsService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    private EditText loginText;
    private EditText passwordText;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkSavedData();
    }

    private void setupUI() {
        loginText = (EditText) findViewById(R.id.login_text);
        passwordText = (EditText) findViewById(R.id.password_text);
        loginBtn = (Button) findViewById(R.id.login_btn);
    }

    private void login() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Login...");
        pd.setCancelable(false);
        pd.show();

        final String login = loginText.getText().toString();
        String password = passwordText.getText().toString();

        final String credentials = Credentials.createCredetials(login, password);
        RestClient.getInstance().login(credentials)
                .enqueue(new Callback<Credentials.Token>() {
                    @Override
                    public void onResponse(Call<Credentials.Token> call, Response<Credentials.Token> response) {
                        pd.dismiss();
                        if (response != null && response.errorBody() != null) {
                            AlertService.showMessage(LoginActivity.this, "Invalid login/password.");
                        } else {
                            onLoginSuccess(credentials, login);
                        }
                    }

                    @Override
                    public void onFailure(Call<Credentials.Token> call, Throwable t) {
                        pd.dismiss();
                        AlertService.showMessage(LoginActivity.this, "Check internet connection");
                    }
                });
    }

    private void onLoginSuccess(String creds, String username) {
        SettingsService.getInstance().saveString(this, "credentials", creds);
        SettingsService.getInstance().saveString(this, "username", username);

        fetchCodeFromGithub();
    }

    private void fetchCodeFromGithub() {
        String username = SettingsService.getInstance().getString("username");
        String q = "size>64+user:" + username;

        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Loading...");
        loading.setCancelable(false);
        loading.show();

        RestClient.getInstance()
                .search(SettingsService.getInstance().getString("credentials"), q)
                .enqueue(new Callback<Models.FirstModel>() {
            @Override
            public void onResponse(Call<Models.FirstModel> call, Response<Models.FirstModel> response) {
                if (response.errorBody() == null) {
                    ArrayList<String> urls = new ArrayList<String>();
                    for (int i = 0; i < 5; i++) {
                        try { urls.add(response.body().items.get(i).url); }
                        catch (IndexOutOfBoundsException e) { break; }
                    }
                    final CodeFetcher fetcher = new CodeFetcher(urls);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            fetcher.fetch(LoginActivity.this, loading);
                        }
                    }).start();
                } else {
                    noCodeDialog();
                }
            }

            @Override
            public void onFailure(Call<Models.FirstModel> call, Throwable t) {
                loading.dismiss();
                noCodeDialog();
            }
        });
    }

    private void noCodeDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Error while downloading your files from github.\nUsing sample code background.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(LoginActivity.this, AndroidLauncher.class));
                    }
                })
                .create().show();
    }

    private boolean validate() {
        return !(loginText.getText().toString().equals("")
                &&passwordText.getText().toString().equals(""));
    }

    private void checkSavedData() {
        String creds = SettingsService.getInstance().getString("credentials");
        if (!creds.isEmpty()) {
            loginBtn.setText("PLAY");
            passwordText.setVisibility(View.INVISIBLE);
            loginText.setVisibility(View.INVISIBLE);
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fetchCodeFromGithub();
                }
            });
        } else {
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validate()) {
                        login();
                    } else {
                        AlertService.showMessage(LoginActivity.this, getString(R.string.bad_credentials_message));
                    }
                }
            });
        }
    }
}
