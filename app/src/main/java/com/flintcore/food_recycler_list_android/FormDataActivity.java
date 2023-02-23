package com.flintcore.food_recycler_list_android;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import com.flintcore.food_recycler_list_android.actionsUtils.ImageManager;
import com.flintcore.food_recycler_list_android.database.FoodConnection;
import com.flintcore.food_recycler_list_android.databinding.ActivityFormDataBinding;
import com.flintcore.food_recycler_list_android.models.Food;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class FormDataActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 100;
    private ActivityFormDataBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binder = ActivityFormDataBinding.inflate(getLayoutInflater());
        setContentView(this.binder.getRoot());

        this.binder.imageHolder.setOnClickListener(
                v -> {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);
                }
        );

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
                        Snackbar.make(v, "Ingrese una imagen valida", Snackbar.LENGTH_LONG).show();
                        return;
                    }

                    Food f = new Food(text, image);
                    FoodConnection.insertFood(f);

                    setResult(RESULT_OK);
                    Snackbar.make(v, "Creado exitosamente", Snackbar.LENGTH_LONG).show();
                    finish();

                }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            this.binder.imageHolder.setImageURI(imageUri);
        }
    }
}