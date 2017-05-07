package com.example.administrator.mytest2.ActivityClass;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.mytest2.BuildConfig;
import com.example.administrator.mytest2.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.jar.Manifest;

public class ChoosePictureActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivDisplay;
    public static final int CHOOSE_PHOTO = 2;
    public static final int TAKE_PHOTO = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_picture);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        Button btnChoose = (Button) findViewById(R.id.btn_choose);
        Button btnChoose1 = (Button) findViewById(R.id.btn_choose1);
        ivDisplay = (ImageView) findViewById(R.id.iv_display);
        Button btnNext3= (Button) findViewById(R.id.btn_next3);
        btnChoose.setOnClickListener(this);
        btnChoose1.setOnClickListener(this);
        btnNext3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_choose:
                //将摄像头拍摄的照片放在关联缓存目录下
                File outImageFile = new File(getExternalCacheDir(), "outPut_image.jpg");
                if (outImageFile.exists())
                    outImageFile.delete();
                try {
                    outImageFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(ChoosePictureActivity.this,
                            "com.example.administrator.mytest2.ActivityClass.fileProvider", outImageFile);
                } else
                    imageUri = Uri.fromFile(outImageFile);
                //启动相机程序 并用回调函数接收  隐式的intent，系统会自己找到程序响应这个intent
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //指定图片输出地址
                startActivityForResult(intent, TAKE_PHOTO);
                break;
            case R.id.btn_choose1:
                if (ContextCompat.checkSelfPermission(ChoosePictureActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ChoosePictureActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else
                    openAlbum();
                break;
            case R.id.btn_next3:
                Intent intent1=new Intent(ChoosePictureActivity.this,WEBTestActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }

    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);  //打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else
                    Toast.makeText(ChoosePictureActivity.this, "获取权限失败", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        //将照片解析为Bitmap对象并显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        ivDisplay.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {

                        handleImageOnKat(data);
                    }
                    else
                        handleImageBeforeKat(data);

                }
                break;
            default:
                break;
        }

    }
   //安卓4.4以下用这个方法处理图片
    private void handleImageBeforeKat(Intent data) {
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }
    //安卓4.4以上用这个方法处理图片
    private void handleImageOnKat(Intent data) {
        //解析得到相册中的图片路径
        String imagePath = null;
        Uri uri = data.getData();

        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            //手机拍照相册里的图片
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split("：")[1]; //解析出数字格式的ID
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }
            //图片下载文件夹下的图片
            else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }

        }
           //内容提供者里的图片
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(uri, null);
           //文件夹里的图片
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {

                imagePath = uri.getPath();
            }

            displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        //将图片显示出来
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            ivDisplay.setImageBitmap(bitmap);
        } else
            Toast.makeText(ChoosePictureActivity.this, "解析出错", Toast.LENGTH_LONG).show();
    }

    private String getImagePath(Uri uri, String selection) {
        //获取图片真实路径
           String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
      return  path;
    }

}
