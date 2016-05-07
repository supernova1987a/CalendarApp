package com.example.summit.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Date;

public class AddEvent extends AppCompatActivity {

    String title;
    String location;
   // String durationMinutes;
   // String durationHours;
    String description;
    Boolean reminder;
    Date date;
    Time time;
    Date endDate;
    Time endTime;
    DatabaseEvent db;

    EditText editTitle, editLocation, editDescription;
    Switch switchReminder;
    DatePicker datePicker;
    TimePicker timePicker;
    TimePicker endTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseEvent(this);

        editTitle = (EditText)findViewById(R.id.editText_title);
        editLocation = (EditText)findViewById(R.id.editText_location);
        //editDuration = (EditText)findViewById(R.id.editText_duration);
        editDescription = (EditText)findViewById(R.id.editText_description);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        endTimePicker = (TimePicker)findViewById(R.id.endTimePicker);
        switchReminder = (Switch)findViewById(R.id.switch_reminder);

        Bundle extras = getIntent().getExtras();
        if( extras!=null) {
            editTitle.setText(extras.getString("passTitle"));
            editLocation.setText(extras.getString("passLocation"));
            //editDuration.setText(extras.getString("passDuration"));
            editDescription.setText(extras.getString("passDescription"));
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
        return true;
    }


    public void confirm()
    {
        int valueDay = datePicker.getDayOfMonth();
        int valueMonth =datePicker.getMonth()+1;
        int valueYear = datePicker.getYear();

        int valueHour = timePicker.getCurrentHour();
        int valueMinutes = timePicker.getCurrentMinute();
        int endHour =endTimePicker.getCurrentHour();
        int endMinutes =endTimePicker.getCurrentMinute();



        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            boolean isInserted = db.insertData(editTitle.getText().toString(), valueDay, valueMonth, valueYear, valueHour, valueMinutes, endHour, endMinutes, editLocation.getText().toString(), editDescription.getText().toString());
            if (isInserted == true) {
                Toast.makeText(AddEvent.this, "Event Scheduled", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            } else
                Toast.makeText(AddEvent.this, "Event Could Not Be Scheduled", Toast.LENGTH_SHORT).show();
        }

        if (extras!=null){
            String id = extras.getString("idToEdit");
            boolean isUpdated = db.updateData(Integer.parseInt(id),editTitle.getText().toString(), valueDay, valueMonth, valueYear, valueHour, valueMinutes, endHour, endMinutes, editLocation.getText().toString(), editDescription.getText().toString());
            if (isUpdated == true) {
                Toast.makeText(AddEvent.this, "Event Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            } else
                Toast.makeText(AddEvent.this, "Event Could Not Be Updated", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_save:
                confirm();
                break;
            case R.id.action_cancel:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
