package sg.edu.rp.c346.id19045083.p09_ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class EditActivity extends AppCompatActivity {

    TextView tvEditID, tvEditTitle, tvEditSingers, tvEditYear, tvEditStars;
    EditText etEditID, etEditTitle, etEditSingers, etEditYear;
    RatingBar rbStars;
    Button btnUpdate, btnDelete, btnCancel;
    Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tvEditID = findViewById(R.id.textViewEditID);
        tvEditTitle = findViewById(R.id.textViewEditTitle);
        tvEditSingers = findViewById(R.id.textViewEditSingers);
        tvEditYear = findViewById(R.id.textViewEditYear);
        tvEditStars = findViewById(R.id.textViewEditStars);

        etEditID = findViewById(R.id.editTextEditID);
        etEditTitle = findViewById(R.id.editTextEditTitle);
        etEditSingers = findViewById(R.id.editTextEditSingers);
        etEditYear = findViewById(R.id.editTextEditYear);
        rbStars = findViewById(R.id.ratingBarEditStars);

        btnUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);
        btnCancel = findViewById(R.id.buttonCancel);

        Intent intent = getIntent();
        song = (Song) intent.getSerializableExtra("data");

        etEditID.setText(String.valueOf(song.get_id()));
        etEditTitle.setText(song.getTitle());
        etEditSingers.setText(song.getSingers());
        etEditYear.setText(String.valueOf(song.getYear()));
        rbStars.setRating(song.getStars());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(EditActivity.this);

                if (etEditTitle.getText().toString().isEmpty()||
                    etEditSingers.getText().toString().isEmpty()||
                    etEditYear.getText().toString().isEmpty()) {
                    Toast.makeText(EditActivity.this, "Input cannot be empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    song.setTitle(etEditTitle.getText().toString());
                    song.setSingers(etEditSingers.getText().toString());
                    song.setYear(Integer.parseInt(etEditYear.getText().toString()));
                    song.setStars((int) rbStars.getRating());
                    db.updateSong(song);
                    db.close();
                }
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(EditActivity.this);
                db.deleteSong(song.get_id());
                finish();
            }
        });
    }
}