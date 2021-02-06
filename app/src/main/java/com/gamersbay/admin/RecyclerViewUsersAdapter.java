package com.gamersbay.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class RecyclerViewUsersAdapter extends FirestoreRecyclerAdapter<User, RecyclerViewUsersAdapter.MyHolder>{

    private onClickListener listener;

    public RecyclerViewUsersAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull User model) {
            holder.userName.setText(model.getFull_name());
            holder.id.setText(model.getID());
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user,parent,false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private TextView id;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name_id);
            id = itemView.findViewById(R.id.user_id_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(getAdapterPosition(), id.getText().toString(),userName.getText().toString());
                }
            });
        }
    }

    public interface onClickListener{
        public void onClick(int position, String ID, String name);
    }

    public void setOnCLickListener(onClickListener listener){
        this.listener = listener;
    }
}
