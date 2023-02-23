package com.flintcore.food_recycler_list_android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flintcore.food_recycler_list_android.adapters.DataNotification;
import com.flintcore.food_recycler_list_android.database.FoodConnection;
import com.flintcore.food_recycler_list_android.databinding.RecyclerItemBinding;
import com.flintcore.food_recycler_list_android.listener.EditDataIntentListener;
import com.flintcore.food_recycler_list_android.models.Food;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class RecyclerCustomAdapter extends RecyclerView.Adapter<RecyclerCustomAdapter.RecyclerHolder>
        implements Serializable {

    private FoodConnection connection;
    private List<Food> listFood;
    private final Activity contextCalled;

    public RecyclerCustomAdapter(FoodConnection connection, Activity context) {
        this.connection = connection;
        this.listFood = this.connection.getItems();
        this.contextCalled = context;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {

        Food food = this.listFood.get(position);

        byte[] data = food.getImage();
        Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);

        holder.binder.imageHolder.setImageBitmap(image);

        holder.binder.nameHolder.setText(food.getName());

        holder.itemView.setOnClickListener(
                v -> Snackbar.make(v, food.getName(), Snackbar.LENGTH_LONG).show()
        );

//        To edit
        holder.binder.editBtnTv.setOnClickListener(
                v -> {
                    Intent intent = EditDataIntentListener
                            .getIntent(contextCalled, food);

                    contextCalled.startActivityForResult(intent, MainActivity.UPDATE_CODE);
                }
        );
    }

    @NonNull
    @Override
    public RecyclerCustomAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerItemBinding binder = RecyclerItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);

        // TODO: 11/26/2022 Add id

        return new RecyclerHolder(binder);
    }

    @Override
    public int getItemCount() {
        return this.listFood.size();
    }

    @Override
    public long getItemId(int position) {
        // TODO: 11/26/2022
        return this.listFood.get(position).getId();
    }

    //    My methods
    public void updateDataAs(DataNotification ref, int position) {
        this.listFood = this.connection.getItems();

        switch (ref) {
            case INSERT:
                // TODO
                this.listFood = this.connection.getItems();
                this.notifyDataSetChanged();
//                this.notifyItemInserted(position);
                break;
            case UPDATE:
                this.notifyItemChanged(position);
                break;
            case DELETE:
                this.listFood.remove(position);
                this.notifyItemRemoved(position);
                break;
            default:
                this.notifyDataSetChanged();
        }
    }

    public void updateData() {
        this.listFood = this.connection.getItems();
        this.notifyDataSetChanged();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder {

        private final RecyclerItemBinding binder;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            this.binder = RecyclerItemBinding.bind(itemView);
        }

        public RecyclerHolder(@NotNull RecyclerItemBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }

    }

}
