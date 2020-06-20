package com.example.adhibeton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.adhibeton.HalamanAbsen;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Homepage extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;

    TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        address=(TextView)findViewById(R.id.lokasi);

        ImageSlider imageSlider = findViewById(R.id.slider);

        List<SlideModel> slideModels= new ArrayList<>();
        slideModels.add(new SlideModel("https://ekrutassets.s3.ap-southeast-1.amazonaws.com/blogs/images/000/000/932/original/training-karyawan-EKRUT","Pelatihan Skill Karyawan"));
        slideModels.add(new SlideModel("https://www.enisa.europa.eu/news/member-states/cyber-security-breaches-survey-2018/@@images/1883790d-a6f2-4ea8-a48c-521737e4c96a.jpeg","Cyber Security Attack"));
        slideModels.add(new SlideModel("https://www.cigna.co.id/sites/default/files/field/image/Web%20Banner%20Coronavirus_1009%20px%20x%20468%20px.jpg","Info Pencegahan Corona Virus"));
        slideModels.add(new SlideModel("https://img.okezone.com/content/2019/08/25/320/2096454/cara-jitu-agar-meeting-lebih-efisien-dan-tak-bertele-tele-st5jasxjgI.jpg","Metting seluruh karyawan"));
        imageSlider.setImageList(slideModels,true);

        locationManager =(LocationManager) this.getSystemService(LOCATION_SERVICE);
        boolean isGPS_enabled= locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(isGPS_enabled){
            locationListener= new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double longitude= location.getLongitude();
                    double latitude= location.getLatitude();

                    try {
                        Geocoder geocoder = new Geocoder(Homepage.this, Locale.getDefault());
                        List<Address> addressesList = geocoder.getFromLocation(latitude, longitude,1);

                        address.setText(addressesList.get(0).getAddressLine(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, locationListener);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if ( grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                address.setText("Getting Location");
            }
        }else {
            address.setText("Access not granted");
        }
    }

    public void MoveToAbsen(View view) {
        Intent i = new Intent(Homepage.this, HalamanAbsen.class);
        startActivity(i);
    }

    public void MoveToKehadiran(View view) {
        Intent i = new Intent(Homepage.this,HalamanKehadiran.class);
        startActivity(i);
    }
}

