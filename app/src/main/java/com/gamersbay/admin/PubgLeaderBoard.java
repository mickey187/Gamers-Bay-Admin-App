package com.gamersbay.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PubgLeaderBoard extends AppCompatActivity implements EditLeaderboardDialog.EditDialogListener {

    private FirebaseFirestore firestore;
    private RecyclerView pubg_leaderboard_list_firestore;
    private FirestoreRecyclerAdapter adapter;
    DocumentReference documentReference;
    FirebaseAuth mAuth;

    String exported_data_from_dialog;
    FloatingActionButton add_user;

    String name_field;
    String docID_edit;
    String game_field;
    String wins_field;
    String kills_field;
    String rank_field;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setUpSharedPreferences();
        setContentView(R.layout.activity_pubg_leader_board);

       // PreferenceManager.getDefaultSharedPreferences(this);


        pubg_leaderboard_list_firestore= findViewById(R.id.pubg_leaderboard_list_recycler_test);
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        add_user = findViewById(R.id.floating_button);

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddUserDialog();
            }
        });


        Query query = firestore.collection("pubg_leaderboard").orderBy("Wins", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<PubgLeaderboardModel> options = new FirestoreRecyclerOptions.Builder<PubgLeaderboardModel>()
                .setQuery(query, PubgLeaderboardModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<PubgLeaderboardModel, PubgLeaderBoard.PubgLeadeboardViewHolder>(options){


            @NonNull
            @Override
            public PubgLeadeboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pubg_leaderboard_list,parent,false);
                return new PubgLeadeboardViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PubgLeadeboardViewHolder holder, int position, @NonNull PubgLeaderboardModel model) {

                holder.Pubg_Name.setText("Name: "+model.getIn_Game_Name());
                holder.ID.setText("ID: "+String.valueOf(model.getID()));
                holder.Rank.setText(String.valueOf(model.getRank()));
                holder.Wins.setText("Wins: "+String.valueOf(model.getWins()));
                holder.Games.setText("Games: "+String.valueOf(model.getGames()));
                holder.Kills.setText("Kills: "+String.valueOf(model.getKills()));


                holder.Pubg_Name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       docID_edit = String.valueOf(model.getID());
                       name_field = String.valueOf(model.getIn_Game_Name());
                        game_field = String.valueOf(model.getGames());
                        wins_field = String.valueOf(model.getWins());
                        kills_field = String.valueOf(model.getKills());
                        rank_field = String.valueOf(model.getRank());

                        bundle.putString("name_field",name_field);
                        bundle.putString("docID_edit",docID_edit);
                        bundle.putString("game_field",game_field);
                        bundle.putString("wins_field",wins_field);
                        bundle.putString("kills_field",kills_field);
                        bundle.putString("rank_field",rank_field);

                        openEditDialog(bundle);



                    }
                });

                holder.Wins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });
            }
        };


        pubg_leaderboard_list_firestore.setAdapter(adapter);
        pubg_leaderboard_list_firestore.setHasFixedSize(true);
        pubg_leaderboard_list_firestore.setLayoutManager(new LinearLayoutManager(PubgLeaderBoard.this));
    }

    private void openAddUserDialog() {

        AddDataLeaderboardDialog addDataLeaderboardDialog = new AddDataLeaderboardDialog();
        addDataLeaderboardDialog.show(getSupportFragmentManager(),"Add User Dialog");
    }

    private void openEditDialog(Bundle bundle) {

        EditLeaderboardDialog leaderboardDialog = new EditLeaderboardDialog();
        leaderboardDialog.setArguments(bundle);
        leaderboardDialog.show((PubgLeaderBoard.this).getSupportFragmentManager(),"Edit Dialog");
    }

    @Override
    public void applyTexts(String editted_value) {

        exported_data_from_dialog = editted_value;

    }

//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//
//        if (key.equals(getString(R.string.dark_mode))){
//            loadDarkModeFromPreference(sharedPreferences);
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//        }
//
//    }

//    private void loadDarkModeFromPreference(SharedPreferences sharedPreferences) {
//
//        if (sharedPreferences.getBoolean(getString(R.string.dark_mode),false)){
//            setTheme(R.style.darkTheme);
//
//        }
//    }

//    public void setUpSharedPreferences(){
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        loadDarkModeFromPreference(sharedPreferences);
//        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
//    }

    private class PubgLeadeboardViewHolder extends RecyclerView.ViewHolder{

        private TextView ID;
        private TextView Games;
        private TextView Wins;
        private TextView Kills;
        private TextView Rank;
        private TextView Pubg_Name;
        private CardView cardView;


        public PubgLeadeboardViewHolder(@NonNull View itemView) {
            super(itemView);

            ID = itemView.findViewById(R.id.pubg_id);
            Games = itemView.findViewById(R.id.number_of_games);
            Wins = itemView.findViewById(R.id.number_of_wins);
            Kills = itemView.findViewById(R.id.number_of_kills);
            Rank = itemView.findViewById(R.id.pubg_rank_textview);
            cardView = itemView.findViewById(R.id.pubg_leaderboard_cards);
            Pubg_Name = itemView.findViewById(R.id.pubg_name);



        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        PreferenceManager.getDefaultSharedPreferences(this);
    }
}