package com.gamersbay.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NotificationsFragment extends Fragment {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();;
    private CollectionReference reference = firestore.collection("Users");
    private RelativeLayout singleUser;
    private TextView groupedUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        singleUser = view.findViewById(R.id.single_user_data);
        groupedUser = view.findViewById(R.id.group_user_data);
        singleUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),UserListActivity.class));
            }
        });
        groupedUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }


    private void sendNotification(String s) {
        Map<String,Object> data = new HashMap<>();
            data.put("title","match name");
            data.put("description", "description");
            data.put("timestamp", Calendar.getInstance().getTime().toGMTString());
            data.put("notify",false);
        reference =  firestore.collection("NotificationTest");
        reference.document(s).collection("notification").add(data);
    }
}