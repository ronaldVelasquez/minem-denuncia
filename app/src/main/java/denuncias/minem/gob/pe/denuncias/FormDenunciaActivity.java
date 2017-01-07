package denuncias.minem.gob.pe.denuncias;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class FormDenunciaActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LatLng myLatLng;
    private TextView tvPosition;
    private ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_denuncia);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvPosition = (TextView) findViewById(R.id.tv_position);
        ivPhoto = (ImageView) findViewById(R.id.iv_photo);
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
                break;
        }
    }
}
