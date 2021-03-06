package com.example.adhibeton;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
public class Reminder extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private TextView mTextView;
    TextView Datang, Pulang;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        mTextView = findViewById(R.id.textView);
       TextView buttonTimePicker = findViewById(R.id.button_timepicker);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        TextView buttonCancelAlarm = findViewById(R.id.button_cancel);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });


        Datang = (TextView) findViewById(R.id.absen_datang);
        Pulang = (TextView) findViewById(R.id.absen_pulang);

        datangdisplay(Datang);
        pulangdisplay(Pulang);

    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        updateTimeText(c);
        startAlarm(c);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void pulangdisplay(TextView pulang) {
        DateTimeFormatter formatterdate = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
        //GetBulan
        LocalDate today = LocalDate.now();
        Month currentMonth = today.getMonth();
        String bln = String.valueOf(currentMonth);

        //GetTahun
        LocalDate thisyear = LocalDate.now();
        int currentYear = thisyear.getYear();
       String thn = String.valueOf(currentYear);


        //GetTanggal
        LocalDate todaay = LocalDate.now();
        String tgl = todaay.format(formatterdate);
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Kehadiran")
                .child(Prevalent.currentOnlineUser.getNpp()).child(thn).child(bln).child(tgl);
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("waktuPulang").exists())
                    {


                        String pulang = dataSnapshot.child("waktuPulang").getValue().toString();

                        Pulang.setText(pulang);


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void datangdisplay(TextView datang) {
        DateTimeFormatter formatterdate = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
        //GetBulan
        LocalDate today = LocalDate.now();
        Month currentMonth = today.getMonth();
        String bln = String.valueOf(currentMonth);

        //GetTahun
        LocalDate thisyear = LocalDate.now();
        int currentYear = thisyear.getYear();
        String thn = String.valueOf(currentYear);


        //GetTanggal
        LocalDate todaay = LocalDate.now();
        String tgl = todaay.format(formatterdate);

        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Kehadiran")
                .child(Prevalent.currentOnlineUser.getNpp()).child(thn).child(bln).child(tgl);
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("waktuDatang").exists())
                    {


                        String datang = dataSnapshot.child("waktuDatang").getValue().toString();

                        Datang.setText(datang);


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void updateTimeText(Calendar c) {
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        mTextView.setText(timeText);
    }
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        mTextView.setText("Alarm canceled");
    }
}