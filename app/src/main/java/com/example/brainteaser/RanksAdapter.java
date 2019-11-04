package com.example.brainteaser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RanksAdapter extends ArrayAdapter<Ranks>
{
    private final Context context;
    private final ArrayList<Ranks> values;

    public RanksAdapter(@NonNull Context context, ArrayList<Ranks> list) {
        super(context, R.layout.leader_board, list);
        this.context = context;
        this.values = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.leader_board, parent, false);

        TextView tvRank = rowView.findViewById(R.id.tvRank);
        TextView tvScore = rowView.findViewById(R.id.tvScore);
        TextView tvUser = rowView.findViewById(R.id.tvUser);

        ImageView ivRank = rowView.findViewById(R.id.ivRank);

        tvRank.setText(Integer.toString(values.get(position).getRank()));
        tvUser.setText(values.get(position).getUsername());
        tvScore.setText(Integer.toString(values.get(position).getScore()));

        if(values.get(position).getRank() == 1)
        {
            ivRank.setImageResource(R.drawable.first);
        }
        else if (values.get(position).getRank() == 2)
        {
            ivRank.setImageResource(R.drawable.second);
        }
        else if (values.get(position).getRank() == 3)
        {
            ivRank.setImageResource(R.drawable.third);
        }
        else
        {
            ivRank.setImageResource(R.drawable.none);
        }

        return rowView;
    }
}
