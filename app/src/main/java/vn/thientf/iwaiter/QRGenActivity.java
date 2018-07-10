package vn.thientf.iwaiter;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

public class QRGenActivity extends AppCompatActivity {

    EditText text;
    Button btn_gen;
    int cTable;
  //  ImageView image;
   // String text2qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgen);
        text = (EditText)findViewById(R.id.textQR);
        btn_gen = (Button)findViewById(R.id.genQR_btn);
     //   image = (ImageView)findViewById(R.id.imageQR);
        ActivityCompat.requestPermissions( QRGenActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1 );
        final File path = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DCIM );
        //File file = new File(path,"iWaiter");
                btn_gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cTable = Integer.parseInt( text.getText().toString() );
                GenQRCode( cTable,path );
            }
        });
    }

    void GenQRCode(int n, File path)
    {
        for(int i=0;i<n;i++)
        {
            String code = makeTableCodeFromInt(i);
            Bitmap bitmap = makeBitmapQRFromString( code );
            if(bitmap!=null) {
                createPictureFromBitmap( "iWaiterQR" + code + ".png", bitmap, path );
            }
        }
    }

    Bitmap makeBitmapQRFromString(String qrtext)
    {
        Bitmap bitmap=null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(qrtext, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder  barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return  bitmap;
    }

    void createPictureFromBitmap(String name,Bitmap bitmap, File path)
    {
        FileOutputStream fileOutputStream =null;
        String filename =  path.getAbsolutePath() + "/" + name;
        File newFile =  new File( filename );
        try {
            fileOutputStream = new FileOutputStream( newFile );
            bitmap.compress( Bitmap.CompressFormat.JPEG ,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE );
        intent.setData( Uri.fromFile(path) );
        sendBroadcast( intent);
    }

    String makeTableCodeFromInt(int id)
    {
        id=id+1000;
        return "T" + Integer.toString( id ).substring( 1 );
    }
}
