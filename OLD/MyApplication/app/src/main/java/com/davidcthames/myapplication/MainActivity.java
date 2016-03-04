package com.davidcthames.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyActivity extends Activity {q

    Button btpic, btnup;
    private Uri fileUri;
    String picturePath;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    public static String URL = "Paste your URL here";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        btpic = (Button) findViewById(R.id.cpic);
        btpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickpic();
            }
        });

        btnup = (Button) findViewById(R.id.up);
        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }

    private void upload() {
        // Image location URL
        Log.e("path", "----------------" + picturePath);

        // Image
        Bitmap bm = BitmapFactory.decodeFile(picturePath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        ba1 = Base64.encodeBytes(ba);

        Log.e("base64", "-----" + ba1);

        // Upload image to server
        new uploadToServer().execute();

    }

    private void clickpic() {
        // Check Camera
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // Open default camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, 100);

        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {

            selectedImage = data.getData();
            photo = (Bitmap) data.getExtras().get("data");

            // Cursor to get image uri to display

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageView imageView = (ImageView) findViewById(R.id.Imageprev);
            imageView.setImageBitmap(photo);
        }
    }

    public class uploadToServer extends AsyncTask<Void, Void, String> {

        private ProgressDialog pd = new ProgressDialog(MyActivity.this);
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Wait image uploading!");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
            nameValuePairs.add(new BasicNameValuePair("ImageName", System.currentTimeMillis() + ".jpg"));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String st = EntityUtils.toString(response.getEntity());
                Log.v("log_tag", "In the try Loop" + st);

            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
            }
            return "Success";

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();
        }
    }
}

public class MainActivity extends ActionBarActivity {

    Button btpic, btnup;
    String ba1;
    public static String URL = "http://zapponomics.net/dev/trinityimage.php";
    String mCurrentPhotoPath;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btpic = (Button) findViewById(R.id.cpic);
        mImageView = (ImageView) findViewById(R.id.Imageprev);
        btpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });

        btnup = (Button) findViewById(R.id.up);
        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }

    private void upload() {
        Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        ba1 = Base64.encodeBytes(ba);

        // Upload image to server
        new uploadToServer().execute();

    }

    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, 100);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            setPic();
        }
    }

    public class uploadToServer extends AsyncTask<Void, Void, String> {

        private ProgressDialog pd = new ProgressDialog(MainActivity.this);

        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Wait image uploading!");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
            nameValuePairs.add(new BasicNameValuePair("ImageName", System.currentTimeMillis() + ".jpg"));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String st = EntityUtils.toString(response.getEntity());
                Log.v("log_tag", "In the try Loop" + st);

            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
            }
            return "Success";

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.e("Getpath", "Cool" + mCurrentPhotoPath);
        return image;
    }

}