package com.flintcore.food_recycler_list_android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.flintcore.food_recycler_list_android.models.Food;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FoodConnection extends DatabaseConnection {
    public static final String TABLE = "foods";
    public static final String COLUNM_ID = "id";
    public static final String COLUNM_NAME = "nameFood";
    public static final String COLUNM_IMAGE = "imageFood";

    private final Query createTable = () -> "CREATE TABLE IF NOT EXISTS " +
            TABLE + "(" + COLUNM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUNM_NAME + " TEXT, " +
            COLUNM_IMAGE + " BLOB)";

    private final Query dropTable = () -> "DROP TABLE IF EXISTS " + TABLE;

    /**
     * Formatted query: values name, image
     */
    public static final Query insertData = () -> "INSERT INTO " + TABLE + "(name, image) VALUES (?,?)";

    /**
     * Formatted query: <br/>
     * - name<br/>
     * - image<br/>
     * - id
     */
    public static final Query updateData = () ->
            "UPDATE " + TABLE + " SET " +
                    "name = ?," +
                    "image = ? " +
                    "WHERE id = ?";

    private static FoodConnection connection;

    public static FoodConnection getConnection(@Nullable Context context,
                                               @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        if (Objects.isNull(connection)) {
            connection = new FoodConnection(context, "list_food", factory, version);
        }

        return connection;
    }

    public static FoodConnection getConnection() {
        return connection;
    }

    private FoodConnection(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTable.get());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(dropTable.get());
        sqLiteDatabase.execSQL(createTable.get());
    }

    public List<Food> getItems() {


        Cursor cursor = connection.getReadableDatabase()
                .query(FoodConnection.TABLE, new String[]{COLUNM_ID, COLUNM_NAME, COLUNM_IMAGE},
                        null, null, null, null, null);

        LinkedList<Food> list = new LinkedList<>();

        if (!cursor.moveToFirst()) {
            return list;
        }

        do {
            list.add(this.fillFoodAndGet(cursor));
        } while (cursor.moveToNext());

        return list;
    }

    private Food fillFoodAndGet(Cursor cursor) {
        Food f = new Food();

        int columnIndexId = cursor.getColumnIndex(COLUNM_ID);
        f.setId(cursor.getInt(columnIndexId));

        int columnIndexName = cursor.getColumnIndex(COLUNM_NAME);
        f.setName(cursor.getString(columnIndexName));

        int columnIndexImage = cursor.getColumnIndex(COLUNM_IMAGE);
        f.setImage(cursor.getBlob(columnIndexImage));

        return f;
    }

    public static boolean insertFood(Food food) {
        ContentValues values = new ContentValues();
        values.put(COLUNM_NAME, food.getName());
        values.put(COLUNM_IMAGE, food.getImage());

        long result = connection.getWritableDatabase()
                .insert(TABLE, null, values);

        return result < 0;

    }

    public static Food getFood(Integer id) {
        Food food = new Food();

        String[] selectionArgs = {id.toString()};
        Cursor cursor = connection.getReadableDatabase()
                .query(FoodConnection.TABLE, new String[]{COLUNM_ID, COLUNM_NAME, COLUNM_IMAGE},
                        COLUNM_ID.concat("= ?"), selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            food = connection.fillFoodAndGet(cursor);
        }

        return food;
    }

    public static boolean deleteFood(Integer id) {
        String condition = COLUNM_ID.concat(" = ?");
        String[] conditionValues = {id.toString()};

        int result = connection.getWritableDatabase().delete(TABLE, condition, conditionValues);

        return result > 0;
    }

    public static boolean updateFood(Food food) {
        ContentValues values = new ContentValues();
        values.put(COLUNM_NAME, food.getName());
        values.put(COLUNM_IMAGE, food.getImage());

        String whereParam = COLUNM_ID.concat("= ?");
        String[] whereArgs = {String.valueOf(food.getId())};
        int result = connection.getWritableDatabase()
                .update(TABLE, values, whereParam, whereArgs);

        return result > 0;
    }
}
