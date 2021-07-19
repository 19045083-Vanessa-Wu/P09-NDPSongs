package sg.edu.rp.c346.id19045083.p09_ndpsongs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NDPsongs.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SONG = "Song";
    private static final String COLUMN_ID = "_id";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSQL = "CREATE TABLE " + TABLE_SONG +
                                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                    "title TEXT, singers TEXT, year INTEGER, stars INTEGER)";
        db.execSQL(createSongTableSQL);
        Log.i("INFO", "Created Tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
        onCreate(db);
    }

    public long insertSong (String Title, String Singers, int Year, int Stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", Title);
        values.put("singers", Singers);
        values.put("year", Year);
        values.put("stars", Stars);
        long result = db.insert(TABLE_SONG, null, values);
        db.close();
        Log.d("SQL Insert", "ID:" + result); //ID returned, shouldn't be -1
        if (result == -1) {
            Log.d("DBHelper", "Insert Failed");
        }
        return result;
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songs = new ArrayList<Song>();

        String selectQuery = "SELECT " + COLUMN_ID + ", title, singers, year, stars " +
                             "FROM " + TABLE_SONG;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song song = new Song(id, title, singers, year, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public int updateSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", song.getTitle());
        values.put("singers", song.getSingers());
        values.put("year", song.getYear());
        values.put("stars", song.getStars());
        String condition = COLUMN_ID + "=?";
        String[] args = {String.valueOf(song.get_id())};
        int result = db.update(TABLE_SONG, values, condition, args);
        if (result < 1) {
            Log.d("DBHelper", "Update Failed");
        }
        db.close();
        return result;
    }

    public int deleteSong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "=?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONG, condition, args);
        if (result < 1) {
            Log.d("DBHelper", "Delete Failed");
        }
        db.close();
        return result;
    }

    public ArrayList<Song> getAllSongsWithYear(int year) {
        ArrayList<Song> songs = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, "title, singers, year, stars"};
        String condition = "year == ?";
        String[] args = {String.valueOf(year)};
        Cursor cursor = db.query(TABLE_SONG, columns, condition, args, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                String Year = cursor.getString(3);
                String stars = cursor.getString(4);
                Song song = new Song(id, title, singers, Integer.parseInt(Year), Integer.parseInt(stars));
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<Song> getAllSongsWithStars(int stars) {
        ArrayList<Song> songs = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, "title, singers, year, stars"};
        String condition = "stars == ?";
        String[] args = {String.valueOf(stars)};
        Cursor cursor = db.query(TABLE_SONG, columns, condition, args, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                String Year = cursor.getString(3);
                String Stars = cursor.getString(4);
                Song song = new Song(id, title, singers, Integer.parseInt(Year), Integer.parseInt(Stars));
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

} //DBHelper class
