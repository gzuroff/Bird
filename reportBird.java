package com.gregoryzuroff.bird;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class reportBird extends Activity implements Button.OnClickListener{

    private EditText editTextBirdName, editTextObsName, editTextZip, editTextDate;
    private Button buttonReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bird);

        editTextBirdName = findViewById(R.id.editTextBirdName);
        editTextDate = findViewById(R.id.editTextDate);
        editTextObsName = findViewById(R.id.editTextObsName);
        editTextZip = findViewById(R.id.editTextZip);

        buttonReport = findViewById(R.id.buttonReport);

        buttonReport.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        if(view == buttonReport){
            Bird temp = new Bird(editTextBirdName.getText().toString(), editTextObsName.getText().toString(), editTextZip.getText().toString(), editTextDate.getText().toString());
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(temp.zip);

            myRef.push().setValue(temp);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(
                R.menu.main, menu
        );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.lastSighting:
                startActivity(new Intent(getApplicationContext(), lastSighting.class));
                return true;
            case R.id.reportSighting:
                startActivity(new Intent(getApplicationContext(), reportBird.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
