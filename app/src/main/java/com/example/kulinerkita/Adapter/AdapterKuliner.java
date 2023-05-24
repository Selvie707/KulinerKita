package com.example.kulinerkita.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kulinerkita.API.APIRequestData;
import com.example.kulinerkita.API.RetroServer;
import com.example.kulinerkita.Activity.MainActivity;
import com.example.kulinerkita.Activity.UbahActivity;
import com.example.kulinerkita.Model.ModelKuliner;
import com.example.kulinerkita.Model.ModelResponse;
import com.example.kulinerkita.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterKuliner extends RecyclerView.Adapter<AdapterKuliner.VHKuliner> {
    private Context ctx;
    private List<ModelKuliner> listKuliner;

    public AdapterKuliner(Context ctx, List<ModelKuliner> listKuliner) {
        this.ctx = ctx;
        this.listKuliner = listKuliner;
    }

    @NonNull
    @Override
    public VHKuliner onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_kuliner, parent, false);
        return new VHKuliner(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHKuliner holder, int position) {
        ModelKuliner MRS = listKuliner.get(position);
        holder.tvId.setText(MRS.getId());
        holder.tvNama.setText(MRS.getNama());
        holder.tvAsal.setText(MRS.getAsal());
        holder.tvDeskripsiSingkat.setText(MRS.getDeskripsi_singkat());
        holder.tvDeskripsiPanjang.setText(MRS.getDeskripsi_lengkap());
        Glide.with(holder.itemView.getContext())
                .load(MRS.getPhoto())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.ivPhoto);

        holder.ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tvDeskripsiPanjang.getVisibility() == View.GONE) {
                    holder.ivArrow.setImageResource(R.drawable.ic_arrow_up);
                    holder.tvDeskripsiPanjang.setVisibility(View.VISIBLE);
                }
                else {
                    holder.ivArrow.setImageResource(R.drawable.ic_arrow_down);
                    holder.tvDeskripsiPanjang.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listKuliner.size();
    }

    public class VHKuliner extends RecyclerView.ViewHolder {
        ImageView ivPhoto, ivArrow;
        TextView tvId, tvNama, tvAsal, tvDeskripsiSingkat, tvDeskripsiPanjang;
        public VHKuliner(@NonNull View itemView) {
            super(itemView);

            ivArrow = itemView.findViewById(R.id.iv_arrow);
            ivPhoto = itemView.findViewById(R.id.iv_listitemkuliner_photo);
            tvId = itemView.findViewById(R.id.tv_listitemkuliner_id);
            tvNama = itemView.findViewById(R.id.tv_listitemkuliner_nama);
            tvAsal = itemView.findViewById(R.id.tv_listitemkuliner_asal);
            tvDeskripsiSingkat = itemView.findViewById(R.id.tv_listitemkuliner_deskripsisingkat);
            tvDeskripsiPanjang = itemView.findViewById(R.id.tv_listitemkuliner_deskripsilengkap);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String idRumahSakit = tvId.getText().toString();
                    AlertDialog.Builder dialog = new
                            AlertDialog.Builder(ctx);
                    dialog.setTitle("Perhatian");
                    dialog.setMessage("Pilih perintah yang diinginkan");
                    dialog.setCancelable(true);
                    dialog.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            hapusRumahSakit(idRumahSakit);
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(ctx, UbahActivity.class);
                            intent.putExtra("xId", tvId.getText().toString());
                            intent.putExtra("xNama", tvNama.getText().toString());
                            intent.putExtra("xAsal", tvAsal.getText().toString());
                            intent.putExtra("xDeskSingkat", tvDeskripsiSingkat.getText().toString());
                            intent.putExtra("xDeskLengkap", tvDeskripsiPanjang.getText().toString());
                            intent.putExtra("xFoto", (String) ivPhoto.getTag());
                            ctx.startActivity(intent);
                        }
                    });
                    dialog.show();
                }
            });
        }
        private void hapusRumahSakit(String id){
            APIRequestData API =
                    RetroServer.connectRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.ardDelete(id);
            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    Toast.makeText(ctx, "Kode: "+kode+ "| Pesan:" +pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retrieverKuliner();
                }
                @Override
                public void onFailure(Call<ModelResponse> call,
                                      Throwable t) {
                    Toast.makeText(ctx, "Gagal menghubungi server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
