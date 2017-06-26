package com.example.konrad.qrdemoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ReaderActivity extends AppCompatActivity {
    private Button scan_btn;
    private TextView qr_description;
    private TextView qr_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        qr_description = (TextView)findViewById(R.id.qr_description);
        qr_code = (TextView)findViewById(R.id.qr_code);
        final Activity activity = this;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan QR or barcode");
                integrator.setCameraId(0);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if(result.getContents()==null)
            {
                Toast.makeText(this, "You cancelled the sccanning", Toast.LENGTH_LONG).show();
            }
            else
            {
                qr_code.setText(result.getContents());
                qr_description.setText(result.toString());
                MediaPlayer scanSound = MediaPlayer.create(this, R.raw.beep);
                scanSound.start();
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
}
}
