package com.example.kulinerkita.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kulinerkita.API.APIRequestData;
import com.example.kulinerkita.API.RetroServer;
import com.example.kulinerkita.Adapter.AdapterKuliner;
import com.example.kulinerkita.Model.ModelKuliner;
import com.example.kulinerkita.Model.ModelResponse;
import com.example.kulinerkita.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ProgressBar pbKuliner;
    private RecyclerView rvKuliner;
    private RecyclerView.Adapter adKuliner;
    private FloatingActionButton fabKuliner;
    private RecyclerView.LayoutManager lmKuliner;
    private ImageView iva;
    private List<ModelKuliner> listKuliner = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbKuliner = findViewById(R.id.pbKuliner);
        rvKuliner = findViewById(R.id.rv_kuliner);
        fabKuliner = findViewById(R.id.fab_kuliner);

        lmKuliner = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvKuliner.setLayoutManager(lmKuliner);

        fabKuliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });
    }

        @Override
        protected void onResume() {
            super.onResume();
            retrieverKuliner();
        }
        public void retrieverKuliner(){
            pbKuliner.setVisibility(View.VISIBLE);

            APIRequestData API = RetroServer.connectRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.ardRetrieve();
            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    listKuliner = response.body().getData();

                    adKuliner = new AdapterKuliner(MainActivity.this, listKuliner);
                    rvKuliner.setAdapter(adKuliner);
                    adKuliner.notifyDataSetChanged();

                    pbKuliner.setVisibility(View.GONE);
                }
                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Gagal menghubungi server : " + t.getMessage(),
                    Toast.LENGTH_SHORT).show();

                    pbKuliner.setVisibility(View.GONE);
                }
            });
        }
}