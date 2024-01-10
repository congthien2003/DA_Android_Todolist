package com.example.appplanandnote.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appplanandnote.MainActivity;
import com.example.appplanandnote.R;
import com.example.appplanandnote.dao.BoardDAO;
import com.example.appplanandnote.dao.ListDAO;
import com.example.appplanandnote.model.Board;
import com.example.appplanandnote.model.ListOfBoard;

import java.util.ArrayList;
import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private List<Board> boardList;
    private List<ListOfBoard> listOfBoards;
    private ListAdapter adapter;

    public BoardAdapter(List<Board> boardList) {
        this.boardList = boardList;
    }
    @NonNull
    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewBoard = LayoutInflater.from( parent.getContext()).inflate(R.layout.item_board, parent, false);
        return new ViewHolder(viewBoard);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardAdapter.ViewHolder holder, int position) {
        Board b = boardList.get(position);
        holder.txtBoardName.setText(b.getBoardName());
        final int finalPositon = position;
        //Board Dialog
        holder.btnEditBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                View viewDialog = inflater.inflate(R.layout.dialog_board, null);

                //Xử lý đổ dữ liệu tên board vào Dialog - DONE
                EditText etxtBoardName = viewDialog.findViewById(R.id.etxtBoardName);
                etxtBoardName.setText(b.getBoardName().toString());
                //Đổ dữ liệu vào rcvList - DONE
                listOfBoards = new ArrayList<>();
                RecyclerView rcvList = viewDialog.findViewById(R.id.rcvList);
                adapter = new ListAdapter(listOfBoards);
                rcvList.setAdapter(adapter);
                rcvList.setLayoutManager(new LinearLayoutManager(context));
                ListDAO listDAO = new ListDAO(listOfBoards,context,adapter);
                listDAO.GetListByBoardID(boardList.get(finalPositon).getBoardID());


                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setView(viewDialog);
                AlertDialog alert = builder.create();
                alert.show();


                Button btnSaveBoard = viewDialog.findViewById(R.id.btnSaveBoard);
                Button btnDeleteBoard = viewDialog.findViewById(R.id.btnDeleteBoard);
                //Xử lý sự kiện Save Board trong Dialog - DONE
                btnSaveBoard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BoardDAO boardDAO = new BoardDAO(context);
                        boardDAO.UpdateBoard(boardList.get(finalPositon).getBoardID(), new Board(etxtBoardName.getText().toString()));
                        boardList.get(finalPositon).setBoardName(etxtBoardName.getText().toString());

                        //MainActivity.adapter.notifyDataSetChanged();
                        MainActivity.adapter.notifyItemChanged(finalPositon);
                        alert.dismiss();
                    }
                });

                //Xử lý sự kiện Delete Board trong Dialog
                btnDeleteBoard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BoardDAO boardDAO = new BoardDAO(context);
                        boardDAO.DeleteBoard(boardList.get(finalPositon).getBoardID());
                        boardList.remove(finalPositon);
                        //MainActivity.adapter.notifyDataSetChanged();
                        MainActivity.adapter.notifyItemRemoved(finalPositon);
                        alert.dismiss();
                    }
                });
            }
        });
        //Xử lý sự kiện hiện thị Board trong Fragment ở Main Activity
        holder.txtBoardName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getContext() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) v.getContext();
                    mainActivity.selectItem(boardList.get(finalPositon).getBoardID());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return boardList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtBoardName;
        Button btnEditBoard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBoardName = (TextView) itemView.findViewById(R.id.txtBoardName);
            btnEditBoard = (Button) itemView.findViewById(R.id.btnEditBoard);

        }

    }


}
