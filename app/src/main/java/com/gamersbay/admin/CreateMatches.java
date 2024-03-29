package com.gamersbay.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateMatches extends AppCompatActivity implements AdapterView.OnItemSelectedListener, android.app.DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Spinner spinner_game_chooser;
    Spinner spinner_game_maps_chooser;
    MaterialButton create_match_button;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String game_selected, match_name, game_map, game_type,  match_day, match_description, match_status, match_time ;
    int entry_fee,reward, max_players;
    TextInputEditText match_name_input;
    TextInputEditText game_type_input;
    TextInputEditText entry_fee_input;
    TextInputEditText reward_input;
    TextInputEditText match_status_input;
    TextInputEditText max_players_input;
    TextInputEditText match_description_input;

    Button date_picker_button;
    Button time_picker_button;
    Date date;
    String currentDate;
    String currentTime;

    String game_collection_chooser;

    private CollectionReference reference = firestore.collection("Users");

    ArrayAdapter<CharSequence> adapter ;
    ArrayAdapter<CharSequence> pubg_maps_adapter;
    ArrayAdapter<CharSequence> free_fire_maps_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_matches);


        spinner_game_chooser = findViewById(R.id.game_chooser_spinner);
        spinner_game_maps_chooser = findViewById(R.id.choose_map_spinner);

        create_match_button = findViewById(R.id.create_match_button);
        match_name_input = findViewById(R.id.textField_match_name);

        game_type_input = findViewById(R.id.textField_game_type);

        entry_fee_input = findViewById(R.id.textField_entry_fee);
        reward_input = findViewById(R.id.textField_Reward);
        match_status_input = findViewById(R.id.textField_match_status);
        max_players_input = findViewById(R.id.textField_max_players);
        match_description_input = findViewById(R.id.textField_match_description);
        date_picker_button = findViewById(R.id.pick_date_button);
        time_picker_button = findViewById(R.id.pick_time_button);



        adapter = ArrayAdapter.createFromResource(this,R.array.Games, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_game_chooser.setAdapter(adapter);
        spinner_game_chooser.setOnItemSelectedListener(this);



       date_picker_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DialogFragment datePicker = new DatePickerDialog();
               datePicker.show(getSupportFragmentManager(),"Date Picker");

           }
       });

       time_picker_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               DialogFragment timePickerFragment = new TimePickerFragment();
               timePickerFragment.show(getSupportFragmentManager(),"Time Picker");
           }
       });






        create_match_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match_name = match_name_input.getText().toString();
                game_type = game_type_input.getText().toString();
              // match_time = match_time_input.getText().toString();
               entry_fee = Integer.parseInt(entry_fee_input.getText().toString());
                reward = Integer.parseInt(reward_input.getText().toString());
                max_players = Integer.parseInt(max_players_input.getText().toString());
                match_status = match_status_input.getText().toString();
                match_description = match_description_input.getText().toString();

                Map<String, Object> match_details = new HashMap<>();
                match_details.put("match_name",match_name);
                match_details.put("game_map",game_map);
                match_details.put("type",game_type);
                match_details.put("match_date",currentDate);
                match_details.put("match_time",currentTime);
                match_details.put("entrance_fee",entry_fee);
                match_details.put("reward",reward);
                match_details.put("maximum_number_of_players",max_players);
                match_details.put("match_status",match_status);
                match_details.put("match_description",match_description);
                match_details.put("dates",date);

                firestore.collection(game_collection_chooser).document(match_name).set(match_details).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        sendNewMatchAddedNotification(match_name);
                        Toast.makeText(CreateMatches.this, "Match successfully created", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateMatches.this, "Failed to create match", Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         game_selected = parent.getItemAtPosition(position).toString();

         if (game_selected.equals("PUBG")){
             pubg_maps_adapter = ArrayAdapter.createFromResource(this,R.array.Pubg_map,android.R.layout.simple_spinner_item );
             pubg_maps_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             spinner_game_maps_chooser.setAdapter(pubg_maps_adapter);
            game_map = spinner_game_maps_chooser.getSelectedItem().toString();
            game_collection_chooser = "pubg_matches";

         } else if (game_selected.equals("Free Fire")){

             free_fire_maps_adapter = ArrayAdapter.createFromResource(this,R.array.Free_Fire_maps,android.R.layout.simple_spinner_item );
             free_fire_maps_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             spinner_game_maps_chooser.setAdapter(free_fire_maps_adapter);
             game_map = spinner_game_maps_chooser.getSelectedItem().toString();
             game_collection_chooser = "free_fire_matches";

         }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    private void sendNewMatchAddedNotification(String match_name) {
        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String[] list = new String[queryDocumentSnapshots.size()];
                int count = 0;
                for (QueryDocumentSnapshot queryDocumentSnapshot: queryDocumentSnapshots) {
                    NotificationModel model = queryDocumentSnapshot.toObject(NotificationModel.class);
                    list[count] = queryDocumentSnapshot.getId();
                    count++;
                    Log.d("TAG", "onSuccess: +"+queryDocumentSnapshot.getId());
                    System.out.println("TAG onSuccess: +"+queryDocumentSnapshot.getId());
                    sendNotification(queryDocumentSnapshot.getId(),match_name);
                }
            }
        });
    }
    private void sendNotification(String s,String match_name) {
        Map<String,Object> data = new HashMap<>();
        data.put("title","New Game");
        data.put("description","Brand new Game --"+ match_name+"--" );
        data.put("notificationTimeStamp", Calendar.getInstance().getTime().toGMTString());
        data.put("notify",false);
        data.put("notificationId","");
        reference =  firestore.collection("NotificationTest");
        reference.document(s).collection("notification").add(data);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
         currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
         
        date_picker_button.setText(currentDate);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String AM_PM = null;
        int hour = 0;
        String minutes_in_string = null;

        if (minute <10){
            minutes_in_string = "0"+String.valueOf(minute);
        }
        else if (minute >= 10){
            minutes_in_string = String.valueOf(minute);
        }

        if (hourOfDay < 12){
            AM_PM = "AM";
            hour = hourOfDay;

            if (hour==0){
                hour = hour+12;
            }
        }
        else if (hourOfDay >=12){
            AM_PM = "PM";
            hour = hourOfDay - 12;
            if (hour==0){
                hour = hour+12;
            }
        }


        currentTime = String.valueOf(hour+":"+minutes_in_string+" "+AM_PM);
        time_picker_button.setText(currentTime);

    }
}