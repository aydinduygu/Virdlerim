package com.codeforlite.virdlerim.ScreenClasses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codeforlite.virdlerim.DB_Interaction;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.Vird_Classes.Dua;
import com.codeforlite.virdlerim.Vird_Classes.Salavat;
import com.codeforlite.virdlerim.Vird_Classes.Tesbih;
import com.codeforlite.virdlerim.VirdlerimApplication;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.blurry.Blurry;

public class AddVird_Secreen extends AppCompatActivity {

    private EditText editText_Title;
    private EditText editText_TurkishText;
    private EditText editText_Meaning;
    private TextView textView_FileName;
    private Button button_TakePhoto;
    private Button button_AccessGalery;
    private Button button_kaydet;
    private Bitmap photoTaken;
    private ConstraintLayout rootView;
    private CircularFillableLoaders fillableLoader;
    private String previousActivity;
    private String photoPath;
    private Intent intentForNextActivity;
    private DB_Interaction db_interaction;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static final int PICK_IMAGE = 1;
    public static final int GALLERY_ACCESS_PERMISSION_REQUEST=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.codeforlite.virdlerim.R.layout.activity_add_vird__secreen);

        //initializations
        rootView=findViewById(R.id.layout_add_vird_screen);
        editText_Title=findViewById(R.id.editText_addvird_title);
        editText_TurkishText=findViewById(R.id.editText_addvird_turkishtext);
        editText_Meaning=findViewById(R.id.editText_addvird_meaning);
        textView_FileName=findViewById(R.id.textView_filename);
        button_AccessGalery=findViewById(R.id.button_accessgallery);
        button_TakePhoto=findViewById(R.id.button_takephoto);
        button_kaydet=findViewById(R.id.button_addvird_kaydet);
        fillableLoader=findViewById(R.id.fillableprogressbar);
        db_interaction= new DB_Interaction(VirdlerimApplication.getAppContext(),VirdlerimApplication.getDbHelper());

        photoPath=null;

        //get the name of previous activity
        previousActivity=getIntent().getStringExtra("class");

        //return to previous activity as new activity and delete activities on stack
        intentForNextActivity=new Intent(AddVird_Secreen.this,defineNextActivity(previousActivity));
        intentForNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //take photo button
        button_TakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.CAMERA},MY_CAMERA_PERMISSION_CODE);
                }
                else {

                    takePhoto();

                }

            }
        });

        //pick image from gallery button
        button_AccessGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},GALLERY_ACCESS_PERMISSION_REQUEST);
                }
                else {

                    Intent galeryIntent=new Intent();
                    galeryIntent.setType("image/*");
                    galeryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(galeryIntent,"Select Picture"),PICK_IMAGE);
                }

            }
        });

        //save button
        button_kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title=editText_Title.getText().toString();
                String  turkishText=editText_TurkishText.getText().toString();
                String meaning=editText_Meaning.getText().toString();

                //put blur on screen
                Blurry.with(getApplicationContext())
                        .radius(10)
                        .sampling(8)
                        .color(Color.argb(20, 255, 255, 255))
                        .async().animate(100)
                        .onto(rootView);

                //show logo animation
                fillableLoader.setVisibility(View.VISIBLE);

                Timer timer=new Timer();

                TimerTask timerTask=new TimerTask() {
                    @Override
                    public void run() {

                        Looper.prepare();

                        if (title!=null&&!title.isEmpty()) {

                            if (photoTaken!=null||!turkishText.isEmpty()){

                                switch (previousActivity){

                                    case "Tesbih":{

                                        Tesbih tesbih=new Tesbih(null,title,null,null,turkishText,meaning);

                                        if (photoTaken!=null){
                                            tesbih.setImage_inbitmap(photoTaken);
                                        }

                                        db_interaction.insertData(tesbih);

                                        startActivity(intentForNextActivity);

                                        break;

                                    }
                                    case "Dua":{

                                        Dua dua=new Dua(null,title,null,null,turkishText,meaning);

                                        if (photoTaken!=null){
                                            dua.setImage_inbitmap(photoTaken);
                                        }

                                        db_interaction.insertData(dua);

                                        startActivity(intentForNextActivity);

                                        break;
                                    }
                                    case "Salavat":{

                                        Salavat salavat=new Salavat(null,title,null,null,turkishText,meaning);

                                        if (photoTaken!=null){
                                            salavat.setImage_inbitmap(photoTaken);

                                        }

                                        db_interaction.insertData(salavat);

                                        startActivity(intentForNextActivity);

                                        break;
                                    }
                                    default:{}

                                }

                            }
                            else{

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        fillableLoader.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(),"Vird metni yada fotoğraf eklemelisiniz!",Toast.LENGTH_LONG).show();
                                        Blurry.delete(rootView);

                                    }
                                });

                            }
                        }

                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fillableLoader.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(),"Başlık kısmı boş bırakılamaz",Toast.LENGTH_SHORT).show();
                                    Blurry.delete(rootView);
                                }
                            });

                             }

                        Looper.loop();
                    }
                };

                timer.schedule(timerTask,1000);


                //Blurry.with(getApplicationContext()).radius(0);

            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==MY_CAMERA_PERMISSION_CODE){

            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                takePhoto();
            }

            else {
                Toast.makeText(this,"Kamera izni verilmedi!",Toast.LENGTH_SHORT).show();
            }
        }

        else if(requestCode==GALLERY_ACCESS_PERMISSION_REQUEST){

            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent galeryIntent=new Intent();
                galeryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galeryIntent.setType("image/*");
                startActivityForResult(galeryIntent,PICK_IMAGE);
            }

            else {
                Toast.makeText(this,"Galeriye erişim izni verilmedi!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {



            photoTaken=BitmapFactory.decodeFile(photoPath);

            ExifInterface exifdata=null;
            int rotation=-1;
            try {
                exifdata=new ExifInterface(photoPath);
                rotation=exifdata.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
                int rotationInDegrees = exifToDegrees(rotation);
                Matrix m=new Matrix();
                if (rotation!=0){m.preRotate(rotationInDegrees);}
                photoTaken=Bitmap.createBitmap(photoTaken,0,0,photoTaken.getWidth(),photoTaken.getHeight(),m,true);

            } catch (IOException e) {
                e.printStackTrace();
            }

            File file=new File(photoPath);
            file.delete();
            textView_FileName.setText("Fotoğraf eklendi.");
            Toast.makeText(VirdlerimApplication.getAppContext(),"Fotoğraf eklendi.",Toast.LENGTH_SHORT).show();
        }
        else if(requestCode==PICK_IMAGE&&resultCode==Activity.RESULT_OK){
            Uri uri=data.getData();
            try {
                photoTaken=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            textView_FileName.setText("Resim eklendi.");
            Toast.makeText(VirdlerimApplication.getAppContext(),"Resim eklendi.",Toast.LENGTH_SHORT).show();


        }

    }

    private Class defineNextActivity(String previousActivity){

        Class<Tesbih_Screen> nextClassTesbih=Tesbih_Screen.class;
        Class<Salavat_Screen> nextClassSalavat=Salavat_Screen.class;
        //Class<Dua_Screen> nextClassDua=Dua_Screen.class;


        switch (previousActivity){

            case "Tesbih":{
                return nextClassTesbih;
            }
            case "Salavat":{
                return nextClassSalavat;
            }
            case "Dua":{
               // return nextClassDua;
            }

        }
        return null;
    }

    private File getImageFile() throws IOException {

        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName="jpg_"+timeStamp+"_";
        File storageFileDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile=File.createTempFile(imageName,".jpg",storageFileDir);

        return imageFile;
    }

    private void takePhoto(){
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager())!=null){

            File imageFile=null;

            try {
                imageFile=getImageFile();

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (imageFile!=null){
                Uri imageUri= FileProvider.getUriForFile(getApplicationContext(),"com.codeforlite.fileprovider",imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(cameraIntent,CAMERA_REQUEST);
                this.photoPath=imageFile.getPath();

            }

        }

    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
        return 0;
    }
}