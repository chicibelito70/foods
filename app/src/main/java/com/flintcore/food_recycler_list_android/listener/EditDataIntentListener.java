package com.flintcore.food_recycler_list_android.listener;

import static com.flintcore.food_recycler_list_android.listener.IntentKeys.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flintcore.food_recycler_list_android.FormDataActivity;
import com.flintcore.food_recycler_list_android.FormEditDataActivity;
import com.flintcore.food_recycler_list_android.R;
import com.flintcore.food_recycler_list_android.RecyclerCustomAdapter;
import com.flintcore.food_recycler_list_android.adapters.DataNotification;
import com.flintcore.food_recycler_list_android.database.FoodConnection;
import com.flintcore.food_recycler_list_android.models.Food;

/**
 * StartActivityByResult
 */
public class EditDataIntentListener {

    private EditDataIntentListener() {
    }

    public static Intent getIntent(Context context, Food foodSelected) {
        Intent editActionIntent = new Intent(context, FormEditDataActivity.class);

        editActionIntent.putExtra(FOOD_EXPECTED.name(), foodSelected);

        return editActionIntent;
    }
}
