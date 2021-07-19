package sg.edu.rp.c346.id19045083.p09_ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    Button btnShow5Stars;
    ListView lv;
    ArrayList<Song> songArrayList;
    ArrayAdapter<Song> songArrayAdapter;
    final boolean[] checker = {true};
    Spinner spn;
    ArrayList<String> years;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        btnShow5Stars = findViewById(R.id.buttonShow5Stars);
        spn = findViewById(R.id.spinner);
        lv = findViewById(R.id.lv);

        DBHelper dbh = new DBHelper(DisplayActivity.this);
        songArrayList = new ArrayList<Song>();
        songArrayList.addAll(dbh.getAllSongs());
        songArrayAdapter = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, songArrayList);
        lv.setAdapter(songArrayAdapter);

        years = new ArrayList<String>();
        years.add("All Years");
        for (int i=0; i < songArrayList.size(); i++) {
            String yrs = String.valueOf(songArrayList.get(i).getYear());
            if (!years.contains(yrs)){
                years.add(yrs);
            }
        }
        ArrayAdapter<String> SPNaa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        SPNaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(SPNaa);

        btnShow5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spn.setSelection(0);
                songArrayList.clear();
                if (checker[0] == true) {   // Show all
                    songArrayList.addAll(dbh.getAllSongs());
                    songArrayAdapter.notifyDataSetChanged();
                    checker[0] = false;
                }
                else if (checker[0] == false) { // Show all with 5 stars only
                    songArrayList.addAll(dbh.getAllSongsWithStars(5));
                    songArrayAdapter.notifyDataSetChanged();
                    checker[0] = true;
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song data = songArrayList.get(position);
                Intent i = new Intent(DisplayActivity.this, EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select = years.get(position);
                if (position!=0) {
                    int yr = Integer.parseInt(select);
                    songArrayList.clear();
                    songArrayList.addAll(dbh.getAllSongsWithYear(yr));
                }
                else {
                    songArrayList.clear();
                    songArrayList.addAll(dbh.getAllSongs());
                }
                songArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    } //onCreate Method

    @Override
    protected void onResume() {
        super.onResume();
        checker[0] = true;
        btnShow5Stars.performClick();
    }
} //DisplayActivity class