package com.gamersbay.admin;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddDataLeaderboardDialog extends DialogFragment {

    TextInputEditText name_input;
    TextInputEditText id_input;
    TextInputEditText games_played_input;
    TextInputEditText kills_input;
    TextInputEditText rank_input;
    TextInputEditText wins_input;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_data_to_leaderboard,null);

        name_input = view.findViewById(R.id.add_user_data_name);
        id_input = view.findViewById(R.id.add_user_data_id);
        games_played_input = view.findViewById(R.id.add_user_data_games);
        kills_input = view.findViewById(R.id.add_user_data_kills);
        rank_input = view.findViewById(R.id.add_user_data_rank);
        wins_input = view.findViewById(R.id.add_user_data_wins);

        builder.setView(view)
                .setTitle("Add new user")
                .setNegativeButton( "Cancel",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Add",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String name = name_input.getText().toString();
                String  id = id_input.getText().toString();
                String games_played = games_played_input.getText().toString();
                String kills = kills_input.getText().toString();
                String rank = rank_input.getText().toString();
                String wins = wins_input.getText().toString();

                int id_parsed = Integer.parseInt(id);
                int games_parsed = Integer.parseInt(games_played);
                int kills_parsed = Integer.parseInt(kills);
                int rank_parsed = Integer.parseInt(rank);
                int wins_parsed = Integer.parseInt(wins);



                Map <String , Object> add_user = new HashMap<>();
                add_user.put("In_Game_Name",name);
                add_user.put("ID",id_parsed);
                add_user.put("Games",games_parsed);
                add_user.put("Kills",kills_parsed);
                add_user.put("Wins",wins_parsed);
                add_user.put("Rank",rank_parsed);


                firestore.collection("pubg_leaderboard").document(id).set(add_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });

            }
        });





        return builder.create();
    }
}
