package com.example.contacts2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> dataList;
    private Activity context;
    private RecyclerViewClickListener listener;

    public ContactAdapter(Activity context, List<Contact> dataList, RecyclerViewClickListener listener){
        this.dataList = dataList;
        this.context = context;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        Contact data = dataList.get(position);
        holder.name.setText(data.getFirstName() + ' ' + data.getLastName());
        holder.job.setText(data.getJob());
        holder.email.setText(data.getEmail());
        holder.phone.setText(data.getPhone());
        holder.callBtn.setOnClickListener((ev) -> {
            Uri number = Uri.parse("tel:" + holder.phone.getText());
            Intent intent = new Intent(Intent.ACTION_DIAL, number);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener {
        TextView name, job, email, phone;
        ImageButton callBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            job = itemView.findViewById(R.id.job);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            callBtn = itemView.findViewById(R.id.callBtn);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onLongClick(v, getAdapterPosition());
            return true;
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
}
