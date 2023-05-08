package com.example.kulinerkita.Model;

import java.util.List;

public class ModelResponse {
    private String kode, pesan;     // harus sama dengan yang ada di API
    private List<ModelKuliner> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelKuliner> getData() {
        return data;
    }
}
