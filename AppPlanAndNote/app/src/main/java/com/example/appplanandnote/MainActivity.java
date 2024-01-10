package com.example.appplanandnote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appplanandnote.adapter.BoardAdapter;
import com.example.appplanandnote.adapter.ReminderAdapter;
import com.example.appplanandnote.dao.AccountDAO;
import com.example.appplanandnote.dao.BoardDAO;
import com.example.appplanandnote.dao.CardDAO;
import com.example.appplanandnote.dao.ListDAO;
import com.example.appplanandnote.dao.ReminderDAO;
import com.example.appplanandnote.model.AppUser;
import com.example.appplanandnote.model.Board;
import com.example.appplanandnote.model.CardOfList;
import com.example.appplanandnote.model.Reminder;
import com.example.appplanandnote.service.IBoardService;
import com.example.appplanandnote.service.RetrofitInstance;
import com.example.appplanandnote.ui.ChangePasswordActivity;
import com.example.appplanandnote.ui.LoginActivity;
import com.example.appplanandnote.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appplanandnote.databinding.ActivityMainBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    public static List<Board> boardList;
    public static BoardAdapter adapter;
    public List<CardOfList> cards = new ArrayList<>();
    public List<Reminder> reminderList;
    public ReminderAdapter reminderAdapter;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        CardDAO cardDAO = new CardDAO(cards);
        cardDAO.GetCardAll();
        drawer = findViewById(R.id.drawer_layout);
/////////
//        setSupportActionBar(binding.appBarMain.toolbar);
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


//        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        //Cập nhật email trong drawer - DONE
        String email = getIntent().getExtras().getString("email");
        TextView txtEmail = navigationView.getHeaderView(0).findViewById(R.id.txtEmail);
        txtEmail.setText(email);

        //Cập nhật RecycleView danh sách các bảng hiện có của user - DONE
        int userID = getIntent().getExtras().getInt("userID");
        GetListBoardByUserID(userID);
        RecyclerView rcvBoard = findViewById(R.id.rcvBoard);
        boardList = new ArrayList<>();
        adapter = new BoardAdapter(boardList);
        rcvBoard.setAdapter(adapter);
        rcvBoard.setLayoutManager(new LinearLayoutManager(this));

        //Xử sự kiện chọn Board để hiện thị
//        rcvBoard.addOnItemTouchListener(new RecyclerTouchListener(this, rcvBoard, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//                //   Toast.makeText(getActivity(), position+ " is selected successfully", Toast.LENGTH_SHORT).show();
//                selectItem(boardList.get(position).getBoardID());
////                rcvBoard.findViewById(R.id.txtBoardName).setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        selectItem(boardList.get(position).getBoardID());
////                    }
////                });
//                //handle click event
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

        //Xử lý sự kiện thêm bảng - DONE
        Button btnAddBoard = binding.navbarBtnAddBoard;
        btnAddBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocalDate date = LocalDate.now();
                String formatDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Board board = new Board("New Board", 0, formatDate, userID);
//                BoardDAO boardDAO = new BoardDAO(MainActivity.this, adapter, boardList);
                BoardDAO boardDAO = new BoardDAO(MainActivity.this, adapter, boardList);
                boardDAO.AddBoard(board);
            }
        });
        //Xử lý sự kiện Logout - DONE
        FloatingActionButton btnLogout = binding.navbarBtnLogOut;
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Toast.makeText(MainActivity.this, "ĐĂNG XUẤT THÀNH CÔNG", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }
        });
        //Xử lý sự kiện ChangePassword - DONE
        FloatingActionButton btnChangePassword = binding.navbarBtnChangePassword;
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_ChangePassword = new Intent(MainActivity.this, ChangePasswordActivity.class);
                intent_ChangePassword.putExtra("KEY_userID", userID);
                startActivity(intent_ChangePassword);
            }
        });
        //Xử lý sự kiện bật thông báo
        Button btnOpenReminder = binding.navbarBtnOpenReminder;
        btnOpenReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View viewDialog = getLayoutInflater().inflate(R.layout.dialog_reminder, null);
                //Đổ dữ liệu vào rcvReminder
//                reminderList = new ArrayList<>();
//                RecyclerView rcvReminder = viewDialog.findViewById(R.id.rcvReminder);
//                reminderAdapter = new ReminderAdapter(reminderList, v.getContext());
//                rcvReminder.setAdapter(reminderAdapter);
//                rcvReminder.setLayoutManager(new LinearLayoutManager(v.getContext()));
//                ReminderDAO reminderDao = new ReminderDAO(v.getContext(), reminderList, reminderAdapter);
//                reminderDao.GetReminderByUserID(userID);

                reminderList = new ArrayList<>();
                List<CardOfList> temps_Card = new ArrayList<>();
                RecyclerView rcvReminder = viewDialog.findViewById(R.id.rcvReminder);
                reminderAdapter = new ReminderAdapter(reminderList, v.getContext(), temps_Card);
                rcvReminder.setAdapter(reminderAdapter);
                rcvReminder.setLayoutManager(new LinearLayoutManager(v.getContext()));
                ReminderDAO reminderDao = new ReminderDAO(v.getContext(), reminderList, reminderAdapter, cards, temps_Card);
                reminderDao.GetReminderByUserID(userID);


                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setView(viewDialog);
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setOpenableLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
        if (savedInstanceState == null) {
            displayDefaultFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    // Xử lý sự kiện khi nhấn nút bật Navigation Drawer
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void GetListBoardByUserID(int userID){
        Call<List<Board>> call = RetrofitInstance.getRetrofitInstance().create(IBoardService.class).getBoardsByUserId(userID);
        call.enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
                if(response.isSuccessful()){
                    boardList.clear();
                    boardList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    //Toast.makeText(getBaseContext(), "Total boards: " + boardList.size(), Toast.LENGTH_SHORT).show();
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<List<Board>> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
                Toast.makeText(getBaseContext(), "LỖI KHI LẤY BOARD", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void displayDefaultFragment() {
        // Thực hiện thay đổi Fragment ở đây
        Fragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, fragment)
                .commit();
    }

    public void selectItem(int boardID_) {
        // Thực hiện xác định Fragment cần thay đổi dựa trên position
        Fragment fragment = getFragmentBasedOnPosition(boardID_);

        // Thực hiện thay đổi Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, fragment)
                .commit();

        // Đóng Navigation Drawer sau khi chọn item
        drawer.closeDrawer(GravityCompat.START);
    }

    public Fragment getFragmentBasedOnPosition(int boardID_) {
        // Xác định Fragment cần thay đổi dựa trên position
        // ...

        Fragment fragment = new HomeFragment(boardID_);
        return fragment;
    }

}