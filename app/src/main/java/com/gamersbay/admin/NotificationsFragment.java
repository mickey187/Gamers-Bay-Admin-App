package com.gamersbay.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
    private FirebaseAuth auth;
    private Spinner spinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        spinner = view.findViewById(R.id.spinner);
        sendNewMatchAddedNotification();
        return view;
    }


    private void sendNewMatchAddedNotification() {
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
                    sendNotification(queryDocumentSnapshot.getId());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,list);
                arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
            }
        });
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