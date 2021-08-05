package sg.edu.rp.c346.id19047433.l12_taskmanagerwear;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    Button btnAdd, btnCancel;
    EditText etName, etDesc, etSec;
    int reqCode = 12345;
    ArrayList<Tasks> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnAdd = findViewById(R.id.btnAdd2);
        btnCancel = findViewById(R.id.btnCancel);
        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        etSec = findViewById(R.id.etSec);

        DBHelper db = new DBHelper(AddActivity.this);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(etName.getText());
                String desc = String.valueOf(etDesc.getText());
                db.insertTask(name, desc);
                al = new ArrayList<>();
                al = db.getItemsOfTasks();
                int index = al.size()-1;
                Tasks tasks = al.get(index);
                int id = tasks.getId();
                db.close();
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, Integer.parseInt(etSec.getText().toString()));

                Intent intent = new Intent(AddActivity.this,
                        ScheduledNotificationReceiver.class);
                intent.putExtra("id", id);
                intent.putExtra("name",name);
                intent.putExtra("desc",desc);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        AddActivity.this, reqCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);

                Intent i =new Intent(AddActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(AddActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

}