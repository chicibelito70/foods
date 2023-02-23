package com.flintcore.food_recycler_list_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.flintcore.food_recycler_list_android.actionsUtils.AlertView;
import com.flintcore.food_recycler_list_android.actionsUtils.ImageManager;
import com.flintcore.food_recycler_list_android.database.FoodConnection;
import com.flintcore.food_recycler_list_android.databinding.ActivityFormDeleteDataBinding;
import com.flintcore.food_recycler_list_android.listener.IntentKeys;
import com.flintcore.food_recycler_list_android.models.Food;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class FormEditDataActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 100;
    private ActivityFormDeleteDataBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binder = ActivityFormDeleteDataBinding.inflate(getLayoutInflater());
        setContentView(this.binder.getRoot());

        Intent dataIntent = getIntent();

        Food foodSelected = ((Food) dataIntent
                .getSerializableExtra(IntentKeys.FOOD_EXPECTED.name()));

        setDeletedAction(foodSelected);

        setEditAction(foodSelected);

        byte[] image = foodSelected.getImage();
        Bitmap bitmapPhoto = BitmapFactory.decodeByteArray(image, 0, image.length);

        this.binder.imageHolder.setOnClickListener(
                v -> {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);
                }
        );

        this.binder.imageHolder.setImageBitmap(bitmapPhoto);
        this.binder.nameHolder.setText(foodSelected.getName());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            this.binder.imageHolder.setImageURI(imageUri);
        }
    }

    private void setEditAction(Food foodSelected) {
        this.binder.saveBtn.setOnClickListener(
                v -> {
                    String text = this.binder.nameHolder.getText().toString();
                    if (text.trim().isEmpty()) {
                        Snackbar.make(v, "Ingrese un nombre valido", Snackbar.LENGTH_LONG).show();
                        return;
                    }

                    Drawable drawable = this.binder.imageHolder.getDrawable();

                    byte[] image = ImageManager.convertImage(drawable);

                    if (Objects.isNull(image)) {
                        Snackbar.make(v, "Ingrese una imagen valida", Snackbar.LENGTH_LONG)
                                .show();
                        return;
                    }

                    foodSelected.setName(text);
                    foodSelected.setImage(image);
                    boolean edited = FoodConnection.updateFood(foodSelected);

                    if (edited) {
                        setResult(RESULT_OK);
                        Snackbar.make(v, "Editado!", Snackbar.LENGTH_LONG).show();
                        finish();
                    }

                    Snackbar.make(v, "No fue posible editar", Snackbar.LENGTH_LONG).show();

                }
        );
    }

    private void setDeletedAction(Food foodSelected) {
        AlertView.CallOnClick positiveClick = () -> {
            boolean deleted = FoodConnection.deleteFood(foodSelected.getId());

            if (deleted){
                setResult(RESULT_OK);
                Snackbar.make(this.binder.getRoot(), "Eliminado!", Snackbar.LENGTH_LONG).show();
                finish();
            }
        };

        AlertView.CallOnClick negativeClick = () -> {
        };

        binder.deleteBtn.setOnClickListener(v ->
                AlertView.getDialog(this, R.layout.msg_alert_layout, R.id.info_tv,
                        R.string.delete_msg, positiveClick, negativeClick).show()
        );
    }
}