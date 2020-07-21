package com.example.adhibeton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adhibeton.helper.GraphicOverlay;
import com.example.adhibeton.helper.RectOverlay;
import com.example.adhibeton.model.ModelAbsen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

@RequiresApi(api = Build.VERSION_CODES.O)
public class FaceDetectPulang extends AppCompatActivity {
    private Button faceDetectButton, mButtonPulang;
    private GraphicOverlay graphicOverlay;
    AlertDialog alertDialog;
    CameraView preview;
    androidx.appcompat.app.AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextView mStatus, mTanggal, mJam,mLokasi;
    String status = "";
    String Lokasi_Absen="";
    public Uri donwload;

    FirebaseStorage storage;
    StorageReference storageReference;

    DateTimeFormatter formattertime = DateTimeFormatter.ofPattern("h:mm a");
    DateTimeFormatter formatterdate = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");

    //GetBulan
    LocalDate thismonth = LocalDate.now();
    Month currentMonth = thismonth.getMonth();
    String bln = String.valueOf(currentMonth);

    //GetTahun
    LocalDate thisyear = LocalDate.now();
    int currentYear = thisyear.getYear();
    String thn = String.valueOf(currentYear);

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Kehadiran");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detect_pulang);
        faceDetectButton = findViewById(R.id.btn_detectPulang);
        graphicOverlay = findViewById(R.id.grapic_overlayPulang);
        preview = findViewById(R.id.camera_viewPulang);

        Lokasi_Absen= FaceDetectPulang.this.getIntent().getStringExtra("lokasiAbsenPulang");;

        dialog = new androidx.appcompat.app.AlertDialog.Builder(FaceDetectPulang.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.absen_pulang, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        mStatus = (TextView) dialogView.findViewById(R.id.StatusPulang);
        mJam = (TextView) dialogView.findViewById(R.id.jamPulang);
        mTanggal = (TextView) dialogView.findViewById(R.id.tanggalPulang);
        mLokasi=(TextView)dialogView.findViewById(R.id.txt_lokasiPulang);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Please Wait, Processing...")
                .setCancelable(false)
                .build();


        faceDetectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preview.start();
                preview.captureImage();
                graphicOverlay.clear();

            }
        });

        preview.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                alertDialog.show();
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, preview.getWidth(), preview.getHeight(), false);
                preview.stop();
                proccessFaceDetection(bitmap);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
    }

    private void proccessFaceDetection(final Bitmap bitmap) {
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionFaceDetectorOptions firebaseVisionFaceDetectorOptions = new FirebaseVisionFaceDetectorOptions.Builder().build();
        FirebaseVisionFaceDetector firebaseVisionFaceDetector = FirebaseVision.getInstance()
                .getVisionFaceDetector(firebaseVisionFaceDetectorOptions);
        firebaseVisionFaceDetector.detectInImage(firebaseVisionImage)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                        if (getFaceResult(firebaseVisionFaces)) {
                            getFaceResult(firebaseVisionFaces);
                            Toast.makeText(FaceDetectPulang.this, "Wajah Berhasil Terdeteksi", Toast.LENGTH_SHORT).show();
                            //GetTanggal
                            LocalDate today = LocalDate.now();
                            final String tgl = today.format(formatterdate);

                            //GetJam
                            LocalTime now = LocalTime.now();
                            final String jam = now.format(formattertime);


                            checkPulang(jam);
                            checkWeekDay();

                            final String jenis = "Pulang";
                            StorageReference ref
                                    = storageReference
                                    .child("absenPulang/" + UUID.randomUUID().toString());
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] data = stream.toByteArray();
                            UploadTask uploadTask = ref.putBytes(data);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(FaceDetectPulang.this, "Gagal upload ", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isSuccessful()) ;
                                    donwload = uriTask.getResult();

                                    //Input Pulang ke database
                                    myRef.child(Prevalent.currentOnlineUser.getNpp()).child(thn).child(bln).child(tgl).child("waktuPulang").setValue(jam);
                                    myRef.child(Prevalent.currentOnlineUser.getNpp()).child(thn).child(bln).child(tgl).child("absenPulang").setValue("Pulang");
                                    myRef.child(Prevalent.currentOnlineUser.getNpp()).child(thn).child(bln).child(tgl).child("statusPulang").setValue(status);
                                    myRef.child(Prevalent.currentOnlineUser.getNpp()).child(thn).child(bln).child(tgl).child("imagePulang").setValue(donwload.toString());

                                }
                            });

                            mStatus.setText(jenis);
                            mJam.setText(jam);
                            mTanggal.setText(tgl);
                            mLokasi.setText(Lokasi_Absen);

                            dialog.setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(FaceDetectPulang.this, HomeScreen.class);
                                    intent.putExtra("status", status);
                                    intent.putExtra("jam", jam);
                                    intent.putExtra("tanggal", tgl);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            dialog.show();


                        } else {
                            Toast.makeText(FaceDetectPulang.this, "Tidak ada wajah yang terdeteksi", Toast.LENGTH_SHORT).show();
                            Intent home = new Intent(FaceDetectPulang.this, HalamanSelfie.class);
                            startActivity(home);
                        }
                    }


                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FaceDetectPulang.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean getFaceResult(List<FirebaseVisionFace> firebaseVisionFaces) {
        int counter = 0;
        for (FirebaseVisionFace face : firebaseVisionFaces) {
            Rect rect = face.getBoundingBox();
            RectOverlay rectOverlay = new RectOverlay(graphicOverlay, rect);

            graphicOverlay.add(rectOverlay);
            counter = counter + 1;
        }
        alertDialog.dismiss();
        if (counter == 0) {
            Toast.makeText(FaceDetectPulang.this, "Tidak ada wajah", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(FaceDetectPulang.this, "Ada wajah", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void checkDatang(String checkTime) {
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
            } else if (lateLocalTime.isBefore(checkLocalTime) && endLocalTime.isAfter(checkLocalTime)) {
                isTelat = true;
            }
        }

        if (isHadir) {
            status = "Tepat Waktu";
        } else if (isTelat) {
            status = "Terlambat";
        } else {
            status = "Belum Hadir";
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void checkPulang(String checkTime) {
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

    protected void checkWeekDay() {
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