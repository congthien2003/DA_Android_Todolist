package com.example.appplanandnote;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appplanandnote.adapter.CardAdapter;
import com.example.appplanandnote.dao.CardDAO;
import com.example.appplanandnote.model.CardOfList;

import java.util.List;

public class SwipeItem extends ItemTouchHelper.SimpleCallback{

    List<CardOfList> cards;
    CardAdapter adapter;
    Context context;


    public SwipeItem(CardAdapter adapter, Context context, List<CardOfList> cards) {
        super(0, ItemTouchHelper.LEFT);
        this.adapter = adapter;
        this.context = context;
        this.cards = cards;

    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }
    @Override
    // Swipte to delete
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();
        int id = this.adapter.getCards().get(pos).getCardID();
        CardDAO cardDAO = new CardDAO(context);
        cardDAO.DeleteCard(id);
        cards.remove(pos);
        //adapter.notifyDataSetChanged();
        adapter.notifyItemRemoved(pos);
//        adapter.notifyItemRangeChanged(pos, adapter.getItemCount());
    }
}
