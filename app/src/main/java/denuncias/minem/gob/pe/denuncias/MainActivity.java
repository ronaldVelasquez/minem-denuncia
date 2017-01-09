package denuncias.minem.gob.pe.denuncias;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rvDenuncias;
    private DenunciaHelper denunciaHelper;
    private SQLiteDatabase db;
    private RVDenuncia adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvDenuncias = (RecyclerView) findViewById(R.id.rv_denuncias);
        rvDenuncias.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RVDenuncia();
        rvDenuncias.setAdapter(adapter);
        toolbar.setTitle("Denuncias");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Añadir una denuncia", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this, FormDenunciaActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        showDenuncias();
    }

    private void showDenuncias() {
        denunciaHelper = new DenunciaHelper(this);
        db = denunciaHelper.getReadableDatabase();
        Cursor cursor = db.query(DenunciasContract.DenunciaEntry.TABLE_NAME, null, null, null, null, null, null);
        List<DenunciaEntity> denuncias = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                DenunciaEntity denuncia = new DenunciaEntity();
                denuncia.setTitle(cursor.getString(cursor.getColumnIndex(DenunciasContract.DenunciaEntry.COLUM_NAME_TITLE)));
                denuncia.setPersonName(cursor.getString(cursor.getColumnIndex(DenunciasContract.DenunciaEntry.COLUM_NAME_PERSON_NAME)));
                denuncia.setDescription(cursor.getString(cursor.getColumnIndex(DenunciasContract.DenunciaEntry.COLUM_NAME_DESCRIPTION)));
                denuncia.setPhoto(cursor.getString(cursor.getColumnIndex(DenunciasContract.DenunciaEntry.COLUM_NAME_PHOTO)));
                denuncia.setLaitude(cursor.getDouble(cursor.getColumnIndex(DenunciasContract.DenunciaEntry.COLUM_NAME_LATITUDE)));
                denuncia.setLongitude(cursor.getDouble(cursor.getColumnIndex(DenunciasContract.DenunciaEntry.COLUM_NAME_LONGITUDE)));
                denuncias.add(denuncia);
                cursor.moveToNext();
            }
        }
        cursor.close();
        adapter.addDenuncias(denuncias);
    }

}
