package com.lueinfo.bshop.RitsActivity;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.lueinfo.bshop.R;

public class ScanQrActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener, View.OnClickListener {

    private TextView mresultTextView;
    private QRCodeReaderView qrCodeReaderView;
    ImageView mbackimage;
    private  int QRCODE = 1;
    int i =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mresultTextView = (TextView) findViewById(R.id.resulttextview);
        mbackimage = (ImageView) findViewById(R.id.back_image);
        mbackimage.setOnClickListener(this);

        qrCodeReaderView.setOnQRCodeReadListener(this);

        qrCodeReaderView.setQRDecodingEnabled(true);

        qrCodeReaderView.setTorchEnabled(true);
        qrCodeReaderView.setBackCamera();

    }


    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        if (text == null) {
            Toast.makeText(ScanQrActivity.this,"Please Scan Qr Code....", Toast.LENGTH_LONG).show();
        } else {
            mresultTextView.setText(text);

            String qr = mresultTextView.getText().toString().trim();
//            if(!qr.isEmpty()){
//                i++;
//
//                if(i==1) {
//
//                    new Qrintro().execute();
//                }
//
//            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

    @Override
    public void onClick(View v) {
        super.onBackPressed();
    }

}
