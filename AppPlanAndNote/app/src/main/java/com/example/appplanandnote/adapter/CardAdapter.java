package com.example.appplanandnote.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appplanandnote.R;
import com.example.appplanandnote.dao.CardDAO;
import com.example.appplanandnote.dao.ListDAO;
import com.example.appplanandnote.dao.ReminderDAO;
import com.example.appplanandnote.model.CardOfList;
import com.example.appplanandnote.model.Reminder;

import java.util.Calendar;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<CardOfList> cards;
    private Calendar calendar;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public List<CardOfList> getCards() {
        return cards;
    }

    public CardAdapter(List<CardOfList> cards){
        this.cards = cards;
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCard = LayoutInflater.from( parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(viewCard);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        CardOfList c = cards.get(position);

        holder.txtCardName.setText(c.getCardName());
        if(c.getCardStatus() == 1){
            holder.btnCheckBox.toggle();
        }
        //Xử lý sự kiện khi nhấn vào CheckBox sẽ update trong cơ sở dữ liệu
        holder.btnCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    cards.get(holder.getAdapterPosition()).setCardStatus(1);
                else
                    cards.get(holder.getAdapterPosition()).setCardStatus(0);
                CardDAO cardDao = new CardDAO();
                cardDao.UpdateCard(c.getCardID(), cards.get(holder.getAdapterPosition()));
            }
        });
        //Xử lý sự kiện click vào Card để bật Dialog Card
        holder.txtCardName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                View viewDialog = inflater.inflate(R.layout.dialog_card, null);
                //Đổ dữ liệu vào editText CardName
                EditText etxtCardName = viewDialog.findViewById(R.id.etxtCardName);
                etxtCardName.setText(c.getCardName());
                //Xử lý sự kiện khi nội dung etxtCardName thay đổi - Sau 1s sẽ gửi nội dung hiện tại editText về cho server để cập nhật dữ liệu
                etxtCardName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        handler.removeCallbacksAndMessages(null);

                        // Đặt callback mới sau một khoảng thời gian trễ
                        handler.postDelayed(() -> {
                            cards.get(holder.getAdapterPosition()).setCardName(etxtCardName.getText().toString());
                            CardDAO dao = new CardDAO();
                            dao.UpdateCard(c.getCardID(), cards.get(holder.getAdapterPosition()));
                            notifyDataSetChanged();
                        }, 1000);
                    }
                });

                //Đổ dữ liệu vào Edit Text Content
                EditText etxtContentCard = viewDialog.findViewById(R.id.detailCard_cardContent);
                etxtContentCard.setText(cards.get(holder.getAdapterPosition()).getContent());
                //Xử lý kiện khi content của Card thay đổi - Cứ 1.5 sẽ cập nhật dữ liệu 1 lần, tính từ khi người dùng thay đổi content
                etxtContentCard.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        handler.removeCallbacksAndMessages(null);

                        // Đặt callback mới sau một khoảng thời gian trễ
                        handler.postDelayed(() -> {
                            cards.get(holder.getAdapterPosition()).setContent(etxtContentCard.getText().toString());
                            CardDAO dao = new CardDAO();
                            dao.UpdateCard(c.getCardID(), cards.get(holder.getAdapterPosition()));
                            notifyDataSetChanged();
                        }, 1500);
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setView(viewDialog);
                AlertDialog alert = builder.create();
                alert.show();
                //Xử lý sự kiện xóa Card
                Button btnDeleteCard = viewDialog.findViewById(R.id.btnDeleteCard);
                btnDeleteCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CardDAO cardDAO = new CardDAO(cards, v.getContext());
                        cardDAO.DeleteCard(cards.get(holder.getAdapterPosition()).getCardID());
                        cards.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        alert.dismiss();
                    }
                });
                //Xử lý sự kiện bật Reminder
                calendar = Calendar.getInstance();
                Button btnReminderCard = viewDialog.findViewById(R.id.btnReminderCard);
                btnReminderCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog(v.getContext(), cards.get(holder.getAdapterPosition()).getCardID());

                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtCardName;
        CheckBox btnCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCardName = (TextView) itemView.findViewById(R.id.txtCardName);
            btnCheckBox = (CheckBox) itemView.findViewById(R.id.checkBoxCard);

        }
    }
    private void showDatePickerDialog(Context context, int cardID_) {

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {

                        String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;

                        Reminder r = new Reminder("REMINDER", null, selectedDate, 1, 0, cardID_);
                        ReminderDAO reminderDAO = new ReminderDAO(context);
                        reminderDAO.AddReminder(r);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }
}
