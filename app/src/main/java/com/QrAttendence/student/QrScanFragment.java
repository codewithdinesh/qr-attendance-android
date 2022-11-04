package com.QrAttendence.student;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.QrAttendence.R;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.Arrays;
import java.util.List;

public class QrScanFragment extends Fragment {
    QRCodeReader qrCodeReader;
    int REQUEST_CODE = 100;
    SharedPreferences userDetails;
    String authID;

    public QrScanFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_qr_scan, container, false);

        ReadQRCode();

        return v;
    }


    public void ReadQRCode() {
//        Intent QrIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(QrIntent, REQUEST_CODE);
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.forSupportFragment(QrScanFragment.this).initiateScan();
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan QR code");
        integrator.setCameraId(0);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Log.d("REQUEST CODE", "" + resultCode);
        if (resultCode != RESULT_OK) {
            return;
        }

        String Scanned = null;

        if (requestCode == IntentIntegrator.REQUEST_CODE) {

            IntentResult scanResult = IntentIntegrator.parseActivityResult(resultCode, data);


            if (scanResult != null) {
                Scanned = scanResult.getContents();

                if (Scanned.startsWith("class")) {

                    userDetails = getActivity().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                    authID = userDetails.getString("authID", "");

                    if (authID.trim().isEmpty()) {
                        Toast.makeText(getActivity().getApplicationContext(), "Please Login to attendence", Toast.LENGTH_SHORT).show();
                    } else {

                        String classID = Scanned.split(":")[1];

                        attendence(classID, authID);
                        Toast.makeText(getActivity().getApplicationContext(), "classID: " + classID, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Class Does not found..Please scan class QR", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Nothing Found", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void attendence(String classID, String studentID) {

    }

}