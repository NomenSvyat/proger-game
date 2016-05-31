package com.sml.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sml.R;
import com.sml.models.ScoreModel;

import java.util.ArrayList;

/**
 * Created on 31.05.16.
 *
 * @author Timofey Plotnikov <timofey.plot@gmail.com>
 */
public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreHolder> {

    private ArrayList<ScoreModel> items;

    public ScoreAdapter(ArrayList<ScoreModel> items) {
        this.items = items;
    }

    @Override
    public ScoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false);
        return new ScoreHolder(view);
    }

    @Override
    public void onBindViewHolder(ScoreHolder holder, int position) {
        ScoreModel model = items.get(position);
        holder.index.setText(String.valueOf(position + 1));
        holder.username.setText(model.username);
        holder.scoreText.setText(String.valueOf(model.score));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ScoreHolder extends RecyclerView.ViewHolder {

        private TextView index;
        private TextView username;
        private TextView scoreText;

        public ScoreHolder(View itemView) {
            super(itemView);

            index = (TextView) itemView.findViewById(R.id.scoreIndex);
            username = (TextView) itemView.findViewById(R.id.username);
            scoreText = (TextView) itemView.findViewById(R.id.scoreCount);
        }
    }
}
