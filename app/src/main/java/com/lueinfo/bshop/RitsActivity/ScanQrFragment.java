package com.lueinfo.bshop.RitsActivity;


import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.lueinfo.bshop.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanQrFragment extends Fragment implements QRCodeReaderView.OnQRCodeReadListener, View.OnClickListener {

    private TextView mresultTextView;
    private QRCodeReaderView qrCodeReaderView;
    ImageView mbackimage;
    private  int QRCODE = 1;
    int i =0;


    public ScanQrFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan_qr, container, false);

        qrCodeReaderView = (QRCodeReaderView) view.findViewById(R.id.qrdecoderview);
        mresultTextView = (TextView) view.findViewById(R.id.resulttextview);
        mbackimage = (ImageView) view.findViewById(R.id.back_image);
        mbackimage.setOnClickListener(this);

        qrCodeReaderView.setOnQRCodeReadListener(this);

        qrCodeReaderView.setQRDecodingEnabled(true);

        qrCodeReaderView.setTorchEnabled(true);
        qrCodeReaderView.setBackCamera();

        return  view;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

    }
}
