package com.example.appplanandnote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appplanandnote.R;
import com.example.appplanandnote.dao.CardDAO;
import com.example.appplanandnote.model.CardOfList;
import com.example.appplanandnote.model.Reminder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private List<Reminder> reminders;
    private List<CardOfList> cards;
    private Context context;
    CardDAO cardDAO = new CardDAO();
    public ReminderAdapter(List<Reminder> reminders, Context context){
        this.reminders = reminders;
        this.context = context;

    }
    public ReminderAdapter(List<Reminder> reminders, Context context, List<CardOfList> cards){
        this.reminders = reminders;
        this.context = context;
        this.cards = cards;
    }
    @NonNull
    @Override
    public ReminderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewReminder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new ViewHolder(viewReminder);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderAdapter.ViewHolder holder, int position) {


        Reminder r = reminders.get(position);
        CardOfList c = cards.get(position);
        LocalDate localDate = LocalDate.parse(r.getDueDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate l = localDate.plusDays(1);
        String s = l.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        holder.txtReminderDate.setText(s);
        holder.txtReminderCardName.setText(c.getCardName());
        // Thiết lập callback để xử lý khi dữ liệu được tải xong
//        cardDAO.setCardLoadCallback(new CardLoadCallback() {
//            @Override
//            public void onCardLoaded(CardOfList card) {
//                LocalDate localDate = LocalDate.parse(r.getDueDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                LocalDate l = localDate.plusDays(1);
//                String s = l.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                holder.txtReminderDate.setText(s);
//
//                if (card != null && card.getCardName() != null) {
//                    holder.txtReminderCardName.setText(card.getCardName());
//                }
//                notifyDataSetChanged();
//            }
//        });

        // Gọi phương thức để tải dữ liệu
        //cardDAO.GetCardBycardID(r.getCardID());


    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtReminderCardName;
        TextView txtReminderDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtReminderCardName = (TextView) itemView.findViewById(R.id.reminder_cardName);
            txtReminderDate = (TextView) itemView.findViewById(R.id.reminder_date);
        }
    }
}
