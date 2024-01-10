package com.example.appplanandnote.adapter;



import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appplanandnote.R;
import com.example.appplanandnote.dao.ListDAO;
import com.example.appplanandnote.model.ListOfBoard;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListOfBoard> listsOfBoard;
    private final Handler handler = new Handler(Looper.getMainLooper());
    public ListAdapter(List<ListOfBoard> listsOfBoard){
        this.listsOfBoard = listsOfBoard;
    }
    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewList = LayoutInflater.from( parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(viewList);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        ListOfBoard l = listsOfBoard.get(position);

        //final int finalPositon = position;
        //Xử lý sự kiện khi người dùng sửa tên của List, hệ thống sẽ chờ người dùng sửa tên trong 1,5s rồi mới gửi về tên List mới về cho server - DONE
        if (holder.txtWatcher != null) {
            holder.etxtBoard_ListName.removeTextChangedListener(holder.txtWatcher);
        }
        holder.etxtBoard_ListName.setText(l.getListName());
        holder.txtWatcher = new TextWatcher() {
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
                    if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                        ListDAO listDAO = new ListDAO();
                        listDAO.UpdateList(listsOfBoard.get(holder.getAdapterPosition()).getListID(), s.toString());
                    }
                }, 1500);
            }
        };
        holder.etxtBoard_ListName.addTextChangedListener(holder.txtWatcher);
        //Xử lý sự kiện xóa List - DONE
        holder.btnDeleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                ListDAO listDAO = new ListDAO(listsOfBoard, context);
                listDAO.DeleteList(listsOfBoard.get(holder.getAdapterPosition()).getListID());
                listsOfBoard.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listsOfBoard.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        EditText etxtBoard_ListName;
        Button btnDeleteList;
        TextWatcher txtWatcher;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etxtBoard_ListName = (EditText) itemView.findViewById(R.id.detailBoard_listName);
            btnDeleteList = (Button) itemView.findViewById(R.id.btnDeleteList);
            txtWatcher = null;
        }
    }
}
