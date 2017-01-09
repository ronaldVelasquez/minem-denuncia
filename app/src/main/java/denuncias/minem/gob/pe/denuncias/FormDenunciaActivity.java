package denuncias.minem.gob.pe.denuncias;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;

public class FormDenunciaActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LatLng myLatLng;
    private TextView tvPosition;
    private ImageView ivPhoto;
    private DenunciaHelper denunciaHelper;
    private SQLiteDatabase db;
    private EditText etTitle, etName, etDescription;
    private Uri photUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_denuncia);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvPosition = (TextView) findViewById(R.id.tv_position);
        ivPhoto = (ImageView) findViewById(R.id.iv_photo);
        etTitle = (EditText) findViewById(R.id.et_title);
        etName = (EditText) findViewById(R.id.et_name);
        etDescription = (EditText) findViewById(R.id.et_description);
        toolbar.setTitle("Agregar denuncia");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(
                    ContextCompat.getDrawable(this, R.drawable.ic_back));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_take_picture:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 2000);
                break;
            case R.id.btn_show_map:
                startActivityForResult(new Intent(FormDenunciaActivity.this, MapActivity.class), 1000);
                break;
            case R.id.btn_save_denuncia:
                denunciaHelper = new DenunciaHelper(this);
                db = denunciaHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(DenunciasContract.DenunciaEntry.COLUM_NAME_TITLE, etTitle.getText().toString());
                contentValues.put(DenunciasContract.DenunciaEntry.COLUM_NAME_PERSON_NAME, etName.getText().toString());
                contentValues.put(DenunciasContract.DenunciaEntry.COLUM_NAME_DESCRIPTION, etDescription.getText().toString());
                contentValues.put(DenunciasContract.DenunciaEntry.COLUM_NAME_LATITUDE, myLatLng.latitude);
                contentValues.put(DenunciasContract.DenunciaEntry.COLUM_NAME_LONGITUDE, myLatLng.longitude);
                contentValues.put(DenunciasContract.DenunciaEntry.COLUM_NAME_PHOTO, getRealPathFromURI(photUri));
                db.insert(DenunciasContract.DenunciaEntry.TABLE_NAME, null, contentValues);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1000:
                myLatLng = new LatLng(
                        data.getDoubleExtra("latitud", 0),
                        data.getDoubleExtra("longitud", 0));
                tvPosition.setText("LAT: " + myLatLng.latitude + "\nLONG: " + myLatLng.longitude);
                break;
            case 2000:
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ivPhoto.setImageBitmap(photo);
                photUri = getImageUri(this, photo);
                break;
        }
    }

    private Uri getImageUri(Context context, Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), photo, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}
