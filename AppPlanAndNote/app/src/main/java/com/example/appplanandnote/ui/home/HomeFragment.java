package com.example.appplanandnote.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appplanandnote.SwipeItem;
import com.example.appplanandnote.adapter.CardAdapter;
import com.example.appplanandnote.dao.CardDAO;
import com.example.appplanandnote.dao.ListDAO;
import com.example.appplanandnote.databinding.FragmentHomeBinding;
import com.example.appplanandnote.model.CardOfList;
import com.example.appplanandnote.model.ListOfBoard;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class HomeFragment extends Fragment {
    private int boardID;
    private List<ListOfBoard> lists;
    private ArrayAdapter<ListOfBoard> adapter;
    private CardAdapter cardAdapter;
    private RecyclerView rcvCard;
    private List<CardOfList> cards;
    public HomeFragment(){}
    public HomeFragment(int boardID){
        this.boardID = boardID;
    }
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        if(boardID != 0){
            Context context = getContext();
            lists = new ArrayList<>();
            //Xử lý đổ List vào Spinner
            Spinner spinner;
            spinner = binding.mainSpinnerList;
            adapter = new ArrayAdapter<ListOfBoard>(requireContext(), android.R.layout.simple_spinner_item, lists){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    TextView textView = (TextView) super.getView(position, convertView, parent);
                    ListOfBoard item = getItem(position);
                    if (item != null) {
                        textView.setText(item.getListName());
                    }
                    return textView;
                }

                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                    ListOfBoard item = getItem(position);
                    if (item != null) {
                        textView.setText(item.getListName());
                    }
                    return textView;
                }
            };
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            ListDAO listDAO = new ListDAO(lists, context, adapter);
            listDAO.GetListByBoardID_List(boardID);

            EditText etxtInputCardName = binding.mainInputCardName;

            //Xử lý đổ các Card dựa trên List hiện tại
            rcvCard = binding.mainRcvCard;
            cards = new ArrayList<>();
            cardAdapter = new CardAdapter(cards);
            rcvCard.setAdapter(cardAdapter);
            rcvCard.setLayoutManager(new LinearLayoutManager(requireContext()));

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    ListOfBoard selectedList = (ListOfBoard) parent.getItemAtPosition(position);
                    int selectedListID = selectedList.getListID();
                    CardDAO cardDAO = new CardDAO(cards, getContext(), cardAdapter);
                    cardDAO.GetCardByListID(selectedListID);
                    Button btnAddCard = binding.mainBtnAddCard;
                    btnAddCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CardOfList card;
                            if(etxtInputCardName.getText().toString().isEmpty())
                                card = new CardOfList("New Card", null, 0, selectedListID);
                            else
                                card = new CardOfList(etxtInputCardName.getText().toString(), null, 0, selectedListID);
                            cardDAO.AddCard(card);
                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



        }

        //Xử lý sự kiện thêm List mới
        Button btnAddList = binding.mainBtnAddList;
        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListOfBoard newList = new ListOfBoard("New List", 0, boardID);
                ListDAO listDAO = new ListDAO(lists, getContext(), adapter);
                listDAO.AddList(newList);
            }
        });
        ///////////////////////////
        /////////////////////////
        //Xử lý sự kiện Swipe Card

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeItem(cardAdapter, getContext(),cards));
        itemTouchHelper.attachToRecyclerView(rcvCard);
        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}