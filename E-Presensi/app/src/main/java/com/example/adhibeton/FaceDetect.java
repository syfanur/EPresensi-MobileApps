package com.example.adhibeton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.adhibeton.helper.GraphicOverlay;
import com.example.adhibeton.helper.RectOverlay;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.CameraSource;
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

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class FaceDetect extends AppCompatActivity {

    private Button faceDetectButton;
    private GraphicOverlay graphicOverlay;
    AlertDialog alertDialog;
    CameraView preview;
    androidx.appcompat.app.AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextView mStatus, mTanggal, mJam;

    @SuppressLint("SimpleDateFormat")
    DateFormat jf = new SimpleDateFormat("h:mm a");
    @SuppressLint("SimpleDateFormat")
    DateFormat tf = new SimpleDateFormat("EEE, d MMM yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detect);
        faceDetectButton=findViewById(R.id.btn_detect);
        graphicOverlay=findViewById(R.id.grapic_overlay);
        preview=findViewById(R.id.camera_view);


        alertDialog= new SpotsDialog.Builder()
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
                        getFaceResult(firebaseVisionFaces);
                        dialog = new androidx.appcompat.app.AlertDialog.Builder(FaceDetect.this);
                        inflater = getLayoutInflater();
                        dialogView = inflater.inflate(R.layout.absen_datang, null);
                        dialog.setView(dialogView);
                        dialog.setCancelable(false);
                        dialog.setTitle("Detail Absen");
                        mStatus=(TextView)dialogView.findViewById(R.id.Status);
                        mJam=(TextView)dialogView.findViewById(R.id.jam);
                        mTanggal=(TextView)dialogView.findViewById(R.id.tanggal);
                        final String jam = jf.format(Calendar.getInstance().getTime());
                        final String tgl = tf.format(Calendar.getInstance().getTime());
                        final String status="Datang";

                        mStatus.setText(status);
                        mJam.setText(jam);
                        mTanggal.setText(tgl);

                        dialog.setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(FaceDetect.this, HomeScreen.class);
                                intent.putExtra("status", status);
                                intent.putExtra("jam",jam );
                                intent.putExtra("tanggal", tgl);
                                startActivity(intent);

                            }
                        });
                        dialog.show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FaceDetect.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFaceResult(List<FirebaseVisionFace> firebaseVisionFaces) {
        int counter=0;
        for (FirebaseVisionFace face : firebaseVisionFaces){
            Rect rect = face.getBoundingBox();
            RectOverlay rectOverlay= new RectOverlay(graphicOverlay, rect);

            graphicOverlay.add(rectOverlay);
            counter=counter+1;
        }
        alertDialog.dismiss();
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