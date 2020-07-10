package com.example.adhibeton;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.adhibeton.model.ModelAbsen;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;


@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeFragment extends Fragment {

    private LocationManager locationManager;
    private LocationListener locationListener;

    private TextView Datang, Pulang;

    private TextView address;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Kehadiran");

    private DateTimeFormatter formattertime = DateTimeFormatter.ofPattern("h:mm a");
    private DateTimeFormatter formatterdate = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");

    //GetJam
    private LocalTime now = LocalTime.now();
    private String jam = now.format(formattertime);

    //GetBulan
    private LocalDate today = LocalDate.now();
    private Month currentMonth = today.getMonth();
    private String bln = String.valueOf(currentMonth);

    //GetTanggal
    private LocalDate todaay = LocalDate.now();
    private String tgl = todaay.format(formatterdate);

    //GetTahun
    private LocalDate thisyear = LocalDate.now();
    private int currentYear = thisyear.getYear();
    private String thn = String.valueOf(currentYear);

    public HomeFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        TextView nama = v.findViewById(R.id.name_user);
        TextView posisi = v.findViewById(R.id.posisi);
        CircularImageView profil = v.findViewById(R.id.profil);
        address = v.findViewById(R.id.lokasi);
        Datang = v.findViewById(R.id.absen_datang);
        Pulang = v.findViewById(R.id.absen_pulang);

        nama.setText(Prevalent.currentOnlineUser.getNama());
        posisi.setText(Prevalent.currentOnlineUser.getPosisi());
        Picasso.get().load(Prevalent.currentOnlineUser.getProfil()).into(profil);

        datangdisplay(Datang);
        pulangdisplay(Pulang);

        TextView dateTimeDisplay = v.findViewById(R.id.text_date_display);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMMM yyyy");
        String date = dateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(date);

        LinearLayout mPresensi = v.findViewById(R.id.presensi);
        mPresensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getActivity(), HalamanKehadiran.class);
                startActivity(home);
            }
        });

        LinearLayout mIzin = v.findViewById(R.id.izin);
        mIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getActivity(), Perizinan.class);
                startActivity(home);
            }
        });

        LinearLayout mMeeting = v.findViewById(R.id.meeting);
        mMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getActivity(), no_meeting.class);
                startActivity(home);
            }
        });

        LinearLayout mTeguran = v.findViewById(R.id.teguran);
        mTeguran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getActivity(), Teguran.class);
                startActivity(home);
            }
        });


        LinearLayout mKehadiran = v.findViewById(R.id.kehadiran);
        mKehadiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getActivity(), DetailKehadiran.class);
                startActivity(home);
            }
        });

        LinearLayout mLembur = v.findViewById(R.id.lembur);
        mLembur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getActivity(),Lembur.class);
                startActivity(home);
            }
        });

        LinearLayout mGaji = v.findViewById(R.id.gaji);
        mGaji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getActivity(), Gaji.class);
                startActivity(home);
            }
        });

        ImageButton reminder = v.findViewById(R.id.reminder);
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getActivity(), Reminder.class);
                startActivity(home);
            }
        });

        ImageSlider imageSlider = v.findViewById(R.id.slider);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://ekrutassets.s3.ap-southeast-1.amazonaws.com/blogs/images/000/000/932/original/training-karyawan-EKRUT", "Pelatihan Skill Karyawan"));
        slideModels.add(new SlideModel("https://www.enisa.europa.eu/news/member-states/cyber-security-breaches-survey-2018/@@images/1883790d-a6f2-4ea8-a48c-521737e4c96a.jpeg", "Cyber Security Attack"));
        slideModels.add(new SlideModel("https://www.cigna.co.id/sites/default/files/field/image/Web%20Banner%20Coronavirus_1009%20px%20x%20468%20px.jpg", "Info Pencegahan Corona Virus"));
        slideModels.add(new SlideModel("https://img.okezone.com/content/2019/08/25/320/2096454/cara-jitu-agar-meeting-lebih-efisien-dan-tak-bertele-tele-st5jasxjgI.jpg", "Meeting Seluruh Karyawan"));
        imageSlider.setImageList(slideModels, true);

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        boolean isGPS_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isGPS_enabled) {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
//
//                    try {
//                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
//                        List<Address> addressesList = geocoder.getFromLocation(latitude, longitude, 1);
//
//                        address.setText(addressesList.get(0).getAddressLine(0));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
        }


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }

        return v;

    }

    private void checkKehadiran(String checkTime){
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String lokasi = "Jl. Ciparay No 20B Kujangsari, Bandung Kidul, Kota Bandung";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.US);
        LocalTime startLocalTime = LocalTime.parse("5:00 PM", formatter);
        LocalTime endLocalTime = LocalTime.parse("11:59 PM", formatter);
        LocalTime checkLocalTime = LocalTime.parse(checkTime, formatter);

        if (Calendar.SATURDAY == dayOfWeek || Calendar.SUNDAY == dayOfWeek) {
            ModelAbsen absen = new ModelAbsen(tgl, jam, jam, "Libur", "Libur", "Libur Kerja", "Libur Kerja", "Tidak Ada", lokasi);
            myRef.child(Prevalent.currentOnlineUser.getNpp()).child(thn).child(bln).child(tgl).setValue(absen);
        }else{
            if (endLocalTime.isAfter(startLocalTime)){
                if (startLocalTime.isBefore(checkLocalTime) && endLocalTime.isAfter(checkLocalTime)){
                    ModelAbsen absen = new ModelAbsen(tgl, jam, jam, "Tidak Hadir", "Tidak Hadir", "Tidak Hadir", "Tidak Hadir", "Tidak Ada", lokasi);
                    myRef.child(Prevalent.currentOnlineUser.getNpp()).child(thn).child(bln).child(tgl).setValue(absen);
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void pulangdisplay(TextView pulang) {
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
                }else{
                    checkKehadiran(jam);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if ( grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                address.setText("Getting Location");
            }
        }else {
            address.setText("Access not granted");
        }
    }

    public void MoveToAbsen(View view) {
        Intent i = new Intent(getActivity(), HalamanAbsen.class);
        startActivity(i);
    }

    public void MoveToKehadiran(View view) {
        Intent i = new Intent(getActivity(), HalamanKehadiran.class);
        startActivity(i);
    }

    public void MoveToIzin(View view) {
        Intent i = new Intent(getActivity(), Perizinan.class);
        startActivity(i);
    }

    public void MoveToMeeting(View view) {
        Intent i = new Intent(getActivity(), no_meeting.class);
        startActivity(i);
    }

    public void MoveToTeguran(View view) {
        Intent i = new Intent(getActivity(),Teguran.class);
        startActivity(i);
    }
}

