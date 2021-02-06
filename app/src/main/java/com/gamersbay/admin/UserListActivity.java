package com.gamersbay.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class UserListActivity extends AppCompatActivity {

    private RecyclerViewUsersAdapter adapter;
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference getAllUserId =  db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        Query query = getAllUserId.orderBy("Full_name",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query,User.class)
                .build();
        adapter = new RecyclerViewUsersAdapter(options);
        recyclerView = findViewById(R.id.recycler_user_list_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserListActivity.this));
        recyclerView.setAdapter(adapter);
        adapter.setOnCLickListener(new RecyclerViewUsersAdapter.onClickListener() {
            @Override
            public void onClick(int position, String ID,String name) {
                openDialog(ID, name);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
              //  openDialog(adapter.getItem(viewHolder.getAdapterPosition()).getID());
            }
        });
    }

    private void openDialog(String id, String name) {
        TextInputEditText title, message;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_dialog,null);
        // intialization of edit texts
        title = view.findViewById(R.id.notification_title_dialog_textfield);
        message = view.findViewById(R.id.notification_message_dialog_textfield);
        builder.setView(view).setTitle("Sending Message to "+ name)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String stTitle = title.getText().toString().trim();
                        String stMessage = message.getText().toString().trim();

                        if(TextUtils.isEmpty(stTitle) || TextUtils.isEmpty(stMessage)){
                            title.setError("Bado new");
                            message.setError("Bado new");
                            return;
                        }
                        sendNotification(stTitle,stMessage,id);
                    }
                });
        builder.create();
        builder.show();
    }

    private void sendNotification(String stTitle, String stMessage, String id) {
        CollectionReference reference = db.collection("NotificationTest").document(id).collection("notification");
        Notification notification = new Notification(stTitle,stMessage);
        reference.add(notification);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}