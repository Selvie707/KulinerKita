package com.example.kulinerkita.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kulinerkita.API.APIRequestData;
import com.example.kulinerkita.API.RetroServer;
import com.example.kulinerkita.Model.ModelResponse;
import com.example.kulinerkita.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {
    private String yId, yNama, yAsal, yDeskSingkat, yDeskLengkap, yFoto;
    private String nama, asal, deskSingkat, deskLengkap, foto;
    private EditText etNama, etAsal, etDeskSingkat, etDeskLengkap, etFoto;
    private Button btnUbah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        Intent intent = getIntent();
        yId = intent.getStringExtra("xId");
        yNama = intent.getStringExtra("xNama");
        yAsal = intent.getStringExtra("xAsal");
        yDeskSingkat = intent.getStringExtra("xDeskSingkat");
        yDeskLengkap = intent.getStringExtra("xDeskLengkap");
        yFoto = intent.getStringExtra("xFoto");

        Log.d("Di sini",  yId + yNama + yAsal + yDeskSingkat + yDeskLengkap + yFoto);

        etNama = findViewById(R.id.et_ubah_nama);
        etAsal = findViewById(R.id.et_ubah_asal);
        etDeskSingkat = findViewById(R.id.et_ubah_desksingkat);
        etDeskLengkap = findViewById(R.id.et_ubah_desklengkap);
        etFoto = findViewById(R.id.et_ubah_photo);
        btnUbah = findViewById(R.id.btn_ubah_ubah);

        etNama.setText(yNama);
        etNama.setText(yAsal);
        etNama.setText(yDeskSingkat);
        etNama.setText(yDeskLengkap);
        etNama.setText(yFoto);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                asal = etAsal.getText().toString();
                deskSingkat = etDeskSingkat.getText().toString();
                deskLengkap = etDeskLengkap.getText().toString();
                foto = etFoto.getText().toString();

                if (nama.trim().equals("")) {
                    etNama.setError("Nama harus diisi");
                } else if (asal.trim().equals("")) {
                    etAsal.setError("Nama harus diisi");
                } else if (deskSingkat.trim().equals("")) {
                    etDeskSingkat.setError("Nama harus diisi");
                } else if (deskLengkap.trim().equals("")) {
                    etDeskLengkap.setError("Nama harus diisi");
                } else if (foto.trim().equals("")) {
                    etFoto.setError("Nama harus diisi");
                } else {
                    ubahMenu();
                }
            }
        });
    }

    private void ubahMenu() {
        String id = yId;

        APIRequestData api = RetroServer.connectRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = api.ardUpdate(id, nama, asal, deskSingkat, deskLengkap, foto);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "Kode : " + kode + "| Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Gagal menghubungi server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}