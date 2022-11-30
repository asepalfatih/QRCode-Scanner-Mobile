package com.example.qrcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //view object
    private Button buttonScanning;
    private TextView textViewName, textViewClass, textViewId;

    //QR code scanner
    private IntentIntegrator qrScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view object
        buttonScanning = (Button) findViewById(R.id.button);
        textViewName = (TextView) findViewById(R.id.textNama);
        textViewClass = (TextView) findViewById(R.id.textkelas);
        textViewId = (TextView) findViewById(R.id.textNIM);

        //inisialisasi scan object
        qrScan = new IntentIntegrator(this);

        //implementasi oncilck listener
        buttonScanning.setOnClickListener(this);
    }
        //unutk hasil scanning
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                //jika qrcode tidak ada sama sekali
                if (result.getContents() == null) {
                    Toast.makeText(this, "Hasil SCANNING tidak ada", Toast.LENGTH_LONG).show();
                }else {
                    //jika qrcode ada/ditemukan datanya
                    try {
                        //Konversi datanya ke json
                        JSONObject obj = new JSONObject(result.getContents());
                        //di set nilai datanya ke textview
                        textViewName.setText(obj.getString("nama"));
                        textViewClass.setText(obj.getString("kelas"));
                        textViewId.setText(obj.getString("nim"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

        @Override
        public void onClick(View v) {
            qrScan.initiateScan();
        }
}
