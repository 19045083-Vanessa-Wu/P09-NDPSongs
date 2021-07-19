package sg.edu.rp.c346.id19045083.p09_ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvTitle, tvSingers, tvYear, tvStars;
    EditText etTitle, etSingers, etYear;
    RatingBar rbStars;
    Button btnInsert, btnShowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.textViewTitle);
        tvSingers = findViewById(R.id.textViewSingers);
        tvYear = findViewById(R.id.textViewYear);
        tvStars = findViewById(R.id.textViewStars);

        etTitle = findViewById(R.id.editTextTitle);
        etSingers = findViewById(R.id.editTextSingers);
        etYear = findViewById(R.id.editTextYear);

        rbStars = findViewById(R.id.ratingBarStars);

        btnInsert = findViewById(R.id.buttonInsert);
        btnShowList = findViewById(R.id.buttonShowList);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etTitle.getText().toString().isEmpty()|| etSingers.getText().toString().isEmpty() || etYear.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Input cannot be empty!", Toast.LENGTH_SHORT).show();

                } else {
                    String title = etTitle.getText().toString();
                    String singers = etSingers.getText().toString();
                    int year = Integer.parseInt(etYear.getText().toString());
                    int stars = (int) rbStars.getRating();

                    DBHelper dbh = new DBHelper(MainActivity.this);
                    long inserted_id = dbh.insertSong(title, singers, year, stars);
                    if (inserted_id > 0) {
                        Toast.makeText(MainActivity.this, "Insert successful", Toast.LENGTH_SHORT).show();
                    }
                }

                //Clear Text,
                etTitle.setText("");
                etSingers.setText("");
                etYear.setText("");
                rbStars.setRating(0);

                //Hide keyboard
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(intent);
            }
        });
    }
}