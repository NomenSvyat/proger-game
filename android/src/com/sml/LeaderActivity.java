package com.sml;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sml.adapters.ScoreAdapter;
import com.sml.models.ScoreModel;
import com.sml.network.RestClient;
import com.sml.utils.Const;
import com.sml.utils.SettingsService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderActivity extends AppCompatActivity {

    private RecyclerView scoreList;
    private ScoreAdapter adapter;
    private ProgressBar progressBar;
    private TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader);
        setupUI();

        int score = getIntent().getIntExtra("score", 1);
        scoreText.setText(String.valueOf(score));

        RestClient.getInstance().getAllScores(Const.GET_SCORE_URL)
                .enqueue(new Callback<ArrayList<ScoreModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ScoreModel>> call, Response<ArrayList<ScoreModel>> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (response.errorBody() == null) {
                            adapter = new ScoreAdapter(response.body());
                            scoreList.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ScoreModel>> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

        String username = SettingsService.getInstance().getString(this, "username");
        String url = Const.SEND_SCORE_URL + String.format("?username=%s&score=%s", username, String.valueOf(score));
        RestClient.getInstance().sendScore(url).enqueue(new Callback<ScoreModel>() {
            @Override
            public void onResponse(Call<ScoreModel> call, Response<ScoreModel> response) {

            }

            @Override
            public void onFailure(Call<ScoreModel> call, Throwable t) {

            }
        });

    }

    private void setupUI() {
        scoreList = (RecyclerView) findViewById(R.id.scoreList);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        scoreText = (TextView) findViewById(R.id.scoreText);
        findViewById(R.id.retryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeaderActivity.this, AndroidLauncher.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        findViewById(R.id.menuBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeaderActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        scoreList.setLayoutManager(new LinearLayoutManager(this));
    }
}
