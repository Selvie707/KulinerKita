package com.example.kulinerkita.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class TambahActivity extends AppCompatActivity {
    private String nama, asal, deskSingkat, deskLengkap, foto;
    private EditText etNama, etAsal, etDeskSingkat, etDeskLengkap, etFoto;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_tambah_nama);
        etAsal = findViewById(R.id.et_tambah_asal);
        etDeskSingkat = findViewById(R.id.et_tambah_desksingkat);
        etDeskLengkap = findViewById(R.id.et_tambah_desklengkap);
        etFoto = findViewById(R.id.et_tambah_photo);
        btnSimpan = findViewById(R.id.btn_tambah_tambah);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                asal = etAsal.getText().toString();
                deskSingkat = etDeskSingkat.getText().toString();
                deskLengkap = etDeskLengkap.getText().toString();
                foto = etFoto.getText().toString();

                if (nama.trim().isEmpty()) {
                    etNama.setError("Nama tidak boleh kosong");
                } else if (asal.trim().isEmpty()) {
                    etNama.setError("Asal tidak boleh kosong");
                } else if (deskSingkat.trim().isEmpty()) {
                    etNama.setError("Deskripsi Singkat tidak boleh kosong");
                } else if (deskLengkap.trim().isEmpty()) {
                    etNama.setError("Deskripsi Lengkap tidak boleh kosong");
                } else if (foto.trim().isEmpty()) {
                    etNama.setError("Foto tidak boleh kosong");
                } else {
                    tambahMenu();
                }
            }
        });
    }

    private void tambahMenu() {
        APIRequestData api = RetroServer.connectRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = api.ardCreate(nama, asal, deskSingkat, deskLengkap, foto);
        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                Toast.makeText(TambahActivity.this, "Kode : " + kode + "| Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal menghubungi server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}