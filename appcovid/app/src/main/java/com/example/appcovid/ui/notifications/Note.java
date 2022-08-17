package com.example.appcovid.ui.notifications;

import java.util.HashMap;
import java.util.Map;

public class Note {
    private String tieude;
    private String noidung;
    private String ghichu;

    public Note(){

    }

    public Note(String tieude, String noidung, String ghichu){
        this.tieude = tieude;
        this.noidung = noidung;
        this.ghichu = ghichu;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("tieude",tieude);
        result.put("noidung",noidung);
        result.put("ghichu",ghichu);
        return result;

    }
}
