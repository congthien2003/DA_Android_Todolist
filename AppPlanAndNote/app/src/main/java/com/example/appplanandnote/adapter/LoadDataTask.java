package com.example.appplanandnote.adapter;

import android.os.AsyncTask;

import com.example.appplanandnote.dao.CardDAO;
import com.example.appplanandnote.model.CardOfList;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LoadDataTask {
    CardDAO cardDAO = new CardDAO();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final int cardID;
    private final LoadDataCallback callback;

    public LoadDataTask(int cardID, LoadDataCallback callback) {
        this.cardID = cardID;
        this.callback = callback;
    }

    public void execute() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Thực hiện truy vấn (void)
                cardDAO.GetCardBycardID(cardID);

                // Sau khi thực hiện xong, gọi callback để thông báo rằng dữ liệu đã được tải xong
                callback.onDataLoaded(cardDAO.temp);
            }
        });
    }
}
