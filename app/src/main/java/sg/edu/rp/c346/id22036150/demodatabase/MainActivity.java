package sg.edu.rp.c346.id22036150.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnGetTasks;
    TextView tvResults;
    EditText etTask, etDate;
    ListView lv;
    ArrayAdapter<String> aaTasks;
    ArrayList<Task> tasks;
    ArrayList<String> alTasks;
    DBHelper db = new DBHelper(MainActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResults);
        lv = findViewById(R.id.lv);
        etTask = findViewById(R.id.etTask);
        etDate = findViewById(R.id.etDate);

        alTasks = new ArrayList<>();

        aaTasks = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,alTasks);
        lv.setAdapter(aaTasks);

        registerForContextMenu(lv);
        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Create the DBHelper object, passing in the
                // activity's Context
                DBHelper db = new DBHelper(MainActivity.this);

                // Insert a task
                db.insertTask(etTask.getText().toString(), etDate.getText().toString());

            }
        });

        btnGetTasks.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Create the DBHelper object, passing in the
                // activity's Context

                ArrayList<String> data = db.getTaskContentAsc();
                tasks = db.getTasks();
                db.close();


                String txt = "";
                for (int i = 0; i < data.size(); i++) {
                    Log.d("Database Content", i +". "+ data.get(i));
                    txt += i + ". " + data.get(i) + "\n";
                    String lvTxt = tasks.get(i).toString();
                    alTasks.add(lvTxt);
                }
                tvResults.setText(txt);

            }
        });



    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.asc) {
            // Sort ListView in ascending order
            ArrayList<String> data = db.getTaskContentAsc();
            tasks = db.getTasksAsc();
            db.close();

            alTasks.clear(); // Clear the existing tasks before adding new ones

            String txt = "";
            for (int i = 0; i < data.size(); i++) {
                Log.d("Database Content", i +". "+ data.get(i));
                txt += i + ". " + data.get(i) + "\n";
                String lvTxt = tasks.get(i).toString();
                alTasks.add(lvTxt);
            }
            tvResults.setText(txt);
            aaTasks.notifyDataSetChanged(); // Notify the ArrayAdapter that the data has changed

            return true;
        } else if (id == R.id.desc) {
            // Sort ListView in descending order
            ArrayList<String> data = db.getTaskContentDesc();
            tasks = db.getTasksDesc();
            db.close();

            alTasks.clear(); // Clear the existing tasks before adding new ones

            String txt = "";
            for (int i = 0; i < data.size(); i++) {
                Log.d("Database Content", i +". "+ data.get(i));
                txt += i + ". " + data.get(i) + "\n";
                String lvTxt = tasks.get(i).toString();
                alTasks.add(lvTxt);
            }
            tvResults.setText(txt);
            aaTasks.notifyDataSetChanged(); // Notify the ArrayAdapter that the data has changed

            return true;
        }

        return super.onContextItemSelected(item);
    }







}