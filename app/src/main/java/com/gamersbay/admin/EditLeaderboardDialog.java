package com.gamersbay.admin;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditLeaderboardDialog extends DialogFragment {

    private static final String TAG = "failed";
    private TextInputEditText edit_data;
    private EditDialogListener listener;
    private TextView doc_names_textview;

    private TextInputLayout textInputLayout_1;
    private TextInputLayout textInputLayout_2;
    private TextInputLayout textInputLayout_3;
    private TextInputLayout textInputLayout_4;
    private TextInputLayout textInputLayout_5;

    String data_from_activity_doc;
    String editted_data;
    FirebaseFirestore firestore  = FirebaseFirestore.getInstance();


    String name_field;
    String docID_edit;
    String game_field;
    String wins_field;
    String kills_field;
    String rank_field;


    private TextInputEditText name_input;
    private TextInputEditText game_input;
    private TextInputEditText wins_input;
    private TextInputEditText rank_input;
    private TextInputEditText kills_input;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_leaderboard_dialog,null);

        textInputLayout_1 = view.findViewById(R.id.TextInputLayout_1);
        textInputLayout_2 = view.findViewById(R.id.TextInputLayout_2);
        textInputLayout_3 = view.findViewById(R.id.TextInputLayout_3);
        textInputLayout_4 = view.findViewById(R.id.TextInputLayout_4);
        textInputLayout_5 = view.findViewById(R.id.TextInputLayout_5);

        name_input = view.findViewById(R.id.textField_edit_data_name);
        game_input = view.findViewById(R.id.textField_edit_data_games);
        wins_input = view.findViewById(R.id.textField_edit_data_wins);
        rank_input = view.findViewById(R.id.textField_edit_data_rank);
        kills_input = view.findViewById(R.id.textField_edit_data_kills);



        docID_edit = getArguments().getString("docID_edit","");
        name_field =  getArguments().getString("name_field","");
        game_field = getArguments().getString("game_field","");
        wins_field = getArguments().getString("wins_field","");
        kills_field = getArguments().getString("kills_field","");
        rank_field =  getArguments().getString("rank_field","");


        name_input.setText(name_field);
        game_input.setText(game_field);
        wins_input.setText(wins_field);
        rank_input.setText(rank_field);
        kills_input.setText(kills_field);

        textInputLayout_1.setHint("In_Game_Name");
        textInputLayout_2.setHint("Games");
        textInputLayout_3.setHint("Wins");
        textInputLayout_4.setHint("Kills");
        textInputLayout_5.setHint("Rank");






        builder.setView(view)
                .setTitle("Edit")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                listener.applyTexts(editted_data);

                String edit_name = name_input.getText().toString();
                int games_edit = Integer.parseInt(game_input.getText().toString());
                int wins_edit = Integer.parseInt(wins_input.getText().toString());
                int kills_edit = Integer.parseInt(kills_input.getText().toString());
                int rank_edit = Integer.parseInt(rank_input.getText().toString());

                firestore.collection("pubg_leaderboard").document(docID_edit)
                        .update("In_Game_Name",edit_name,"Games",games_edit,"Wins",wins_edit,"Kills",kills_edit
                        ,"Rank",rank_edit);

            }
        });




        //edit_data = view.findViewById(R.id.textField_edit_data_name);
        doc_names_textview = view.findViewById(R.id.doc_name);
        doc_names_textview.setText(data_from_activity_doc);



        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (EditDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement EditDialogListener");
        }
    }

    public interface EditDialogListener{

        void applyTexts(String editted_value);
    }


}
