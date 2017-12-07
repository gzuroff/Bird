package com.gregoryzuroff.bird;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import static android.content.ContentValues.TAG;

public class lastSighting extends Activity implements Button.OnClickListener {

    private EditText editTextZip2;
    private Button buttonGetLast;
    private TextView textViewBird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_sighting);

        editTextZip2 = findViewById(R.id.editTextZip2);
        buttonGetLast = findViewById(R.id.buttonGetLast);
        textViewBird = findViewById(R.id.textViewBird);

        buttonGetLast.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        if(view == buttonGetLast){
            String zip = editTextZip2.getText().toString();

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(zip);

            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Bird lastBird = new Bird();
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        lastBird = postSnapshot.getValue(Bird.class);

                    }

                    String name = lastBird.birdName;
                    String obs = lastBird.obsName;
                    String date = lastBird.dateObs;
                    String zip = lastBird.zip;
                    String zip1 = editTextZip2.getText().toString();

                    if(lastBird.birdName != null){
                        textViewBird.setText("Last Reported Sighting in Zip Code\n" + zip + "\n" + "Bird Name: " + name + "\n"
                                + "Reporter Name: " + obs + "\n" + "Date of Sighting: " + date + "\n\n" +
                                "Thank you for using BirdWatcher");
                    }
                    else{
                        if(zip1.length() == 0){
                            textViewBird.setText("Your Zip Code Field is Empty");
                        }
                        else {
                            textViewBird.setText("No Bird Sightings in Zip Code: " + zip1);
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
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
