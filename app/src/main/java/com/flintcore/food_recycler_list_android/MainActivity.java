package com.flintcore.food_recycler_list_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flintcore.food_recycler_list_android.adapters.DataNotification;
import com.flintcore.food_recycler_list_android.database.DatabaseConnection;
import com.flintcore.food_recycler_list_android.database.FoodConnection;
import com.flintcore.food_recycler_list_android.databinding.ActivityMainBinding;
import com.flintcore.food_recycler_list_android.listener.EditDataIntentListener;
import com.flintcore.food_recycler_list_android.listener.IntentKeys;
import com.flintcore.food_recycler_list_android.models.Food;

public class MainActivity extends AppCompatActivity {

    public static final int INSERT_CODE = 0x21;
    public static final int UPDATE_CODE = 0x22;
    private ActivityMainBinding binder;
    private RecyclerCustomAdapter recyclerAdapter;
    private FoodConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binder.getRoot());

        this.connection = FoodConnection.getConnection(getApplicationContext(), null, 1);

        this.recyclerAdapter = new RecyclerCustomAdapter(this.connection, this);

        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(RecyclerView.VERTICAL);

        this.binder.recyclerList.setLayoutManager(layout);

        this.binder.recyclerList.setAdapter(this.recyclerAdapter);

        this.binder.addFloating.setOnClickListener(
                v -> {
                    Intent intentCreate = new Intent(this, FormDataActivity.class);
                    startActivityForResult(intentCreate, INSERT_CODE);
                }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            this.recyclerAdapter.updateData();
        }

    }

}