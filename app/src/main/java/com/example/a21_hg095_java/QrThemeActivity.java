package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class QrThemeActivity extends CaptureActivity {
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_theme);

        barcodeScannerView = (DecoratedBarcodeView) findViewById(R.id.db_qr);
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();



    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }


}