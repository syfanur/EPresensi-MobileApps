package com.example.adhibeton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adhibeton.helper.GraphicOverlay;
import com.example.adhibeton.helper.RectOverlay;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

@RequiresApi(api = Build.VERSION_CODES.O)
public class FaceDetect extends AppCompatActivity {

    private Button faceDetectButton, mButtonPulang;
    private GraphicOverlay graphicOverlay;
    AlertDialog alertDialog;
    CameraView preview;
    androidx.appcompat.app.AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextView mStatus, mTanggal, mJam;
    String status = "";

    DateTimeFormatter formattertime = DateTimeFormatter.ofPattern("h:mm a");
    DateTimeFormatter formatterdate = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Kehadiran");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detect);
        faceDetectButton = findViewById(R.id.btn_detect);
        graphicOverlay = findViewById(R.id.grapic_overlay);
        preview = findViewById(R.id.camera_view);
        mButtonPulang = findViewById(R.id.btn_pulang);

        dialog = new androidx.appcompat.app.AlertDialog.Builder(FaceDetect.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.absen_datang, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        dialog.setTitle("Detail Absen");
        mStatus=(TextView)dialogView.findViewById(R.id.Status);
        mJam=(TextView)dialogView.findViewById(R.id.jam);
        mTanggal=(TextView)dialogView.findViewById(R.id.tanggal);

        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Please Wait, Processing...")
                .setCancelable(false)
                .build();

        faceDetectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        preview.start();
        preview.captureImage();
        graphicOverlay.clear();

        preview.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap= Bitmap.createScaledBitmap(bitmap, preview.getWidth(), preview.getHeight(),false);
                preview.stop();
                proccessFaceDetection(bitmap);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
    }

    private void proccessFaceDetection(Bitmap bitmap) {
        FirebaseVisionImage firebaseVisionImage= FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionFaceDetectorOptions firebaseVisionFaceDetectorOptions= new FirebaseVisionFaceDetectorOptions.Builder().build();
        FirebaseVisionFaceDetector firebaseVisionFaceDetector= FirebaseVision.getInstance()
                .getVisionFaceDetector(firebaseVisionFaceDetectorOptions);
        firebaseVisionFaceDetector.detectInImage(firebaseVisionImage)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                        if (getFaceResult(firebaseVisionFaces)){
                        getFaceResult(firebaseVisionFaces);
                        Toast.makeText(FaceDetect.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            LocalDate today = LocalDate.now();
                            final String tgl = today.format(formatterdate);

                            //GetJam
                            LocalTime now = LocalTime.now();
                            final String jam = now.format(formattertime);

                            //GetBulan
                            Month currentMonth = today.getMonth();
                            String bln = String.valueOf(currentMonth);


                            checkDatang(jam);
                            checkWeekDay();

                            String idUser = myRef.push().getKey();
                            assert idUser != null;
                            String idAbsen = myRef.push().getKey();
                            assert idAbsen != null;


                            final String jenis="Datang";
                            String id = myRef.push().getKey();
                            ModelAbsen absen = new ModelAbsen(tgl, jam, "Datang", status, "tidak ada", "Jalan Ciparay");
                            myRef.child("NPP").child("1202170038").child("AbsenDatang").child(bln).child(tgl).setValue(absen);


                            mStatus.setText(jenis);
                            mJam.setText(jam);
                            mTanggal.setText(tgl);

                            dialog.setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(FaceDetect.this, HomeScreen.class);
                                    intent.putExtra("status",  status);
                                    intent.putExtra("jam",jam );
                                    intent.putExtra("tanggal", tgl);
                                    startActivity(intent);

                                }
                            });
                            dialog.show();
                        }else{
                            Toast.makeText(FaceDetect.this, "Tidak Berhasil", Toast.LENGTH_SHORT).show();
                            finishAndRemoveTask();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    Toast.makeText(FaceDetect.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private boolean getFaceResult(List<FirebaseVisionFace> firebaseVisionFaces) {
        int counter=0;
        for (FirebaseVisionFace face : firebaseVisionFaces){
            Rect rect = face.getBoundingBox();
            RectOverlay rectOverlay= new RectOverlay(graphicOverlay, rect);

            graphicOverlay.add(rectOverlay);
            counter=counter+1;
        }
        alertDialog.dismiss();
        if (counter == 0){
            Toast.makeText(FaceDetect.this, "Tidak ada wajah", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            Toast.makeText(FaceDetect.this, "Ada wajah", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void checkDatang(String checkTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.US);
        LocalTime startLocalTime = LocalTime.parse("9:00 AM", formatter);
        LocalTime lateLocalTime = LocalTime.parse("9:15 AM", formatter);
        LocalTime endLocalTime = LocalTime.parse("5:00 PM", formatter);
        LocalTime checkLocalTime = LocalTime.parse(checkTime, formatter);


        boolean isHadir = false;
        boolean isTelat = false;
        if (endLocalTime.isAfter(startLocalTime) || lateLocalTime.isAfter(startLocalTime)) {
            if (startLocalTime.isBefore(checkLocalTime) && lateLocalTime.isAfter(checkLocalTime)) {
                isHadir = true;
            } else if (lateLocalTime.isBefore(checkLocalTime) && endLocalTime.isAfter(checkLocalTime)){
                isTelat = true;
            }
        }

        if (isHadir) {
            status = "Hadir";
        } else if (isTelat){
            status = "Terlambat";
        } else {
            status = "Tidak Hadir";
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void checkPulang(String checkTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.US);
        LocalTime startLocalTime = LocalTime.parse("9:00 AM", formatter);
        LocalTime endLocalTime = LocalTime.parse("5:00 PM", formatter);
        LocalTime checkLocalTime = LocalTime.parse(checkTime, formatter);

        boolean isInBetween = false;
        if (endLocalTime.isAfter(startLocalTime)) {
            if (startLocalTime.isBefore(checkLocalTime) && endLocalTime.isAfter(checkLocalTime)) {
                isInBetween = true;
            }
        } else if (checkLocalTime.isAfter(startLocalTime) || checkLocalTime.isBefore(endLocalTime)) {
            isInBetween = true;
        }

        if (isInBetween) {
            status = "Didalam Jam";
        } else {
            status = "Diluar Jam";
        }
    }

    protected void checkWeekDay(){
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        if (Calendar.SATURDAY == dayOfWeek || Calendar.SUNDAY == dayOfWeek) {
            status = "Libur Kerja";
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        preview.stop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        preview.start();
        
    }

}