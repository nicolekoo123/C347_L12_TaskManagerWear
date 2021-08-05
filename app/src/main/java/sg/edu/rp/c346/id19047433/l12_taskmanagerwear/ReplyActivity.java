package sg.edu.rp.c346.id19047433.l12_taskmanagerwear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.RemoteInput;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ReplyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        CharSequence reply = null;
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null){
            DBHelper dbh = new DBHelper(ReplyActivity.this);
            dbh.deleteTask(id);
            dbh.close();
            reply = remoteInput.getCharSequence("status");
            finish();
        }

        if(reply != null){
            Toast.makeText(ReplyActivity.this, "You have indicated: " + reply,
                    Toast.LENGTH_SHORT).show();
        }

    }
}