/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.model;


 
    

import java.util.Date;

/**
 *
 * @author SCN
 */
public class m_phieumuon {
    private String maPM;
    private String maDG;
    private String maS;
    private Date ngayMuon;
    private Date hanTra;

    public m_phieumuon() {}

    public m_phieumuon(String maPM, String maDG, String maS, Date ngayMuon, Date hanTra) {
        this.maPM = maPM;
        this.maDG = maDG;
        this.maS = maS;
        this.ngayMuon = ngayMuon;
        this.hanTra = hanTra;
    }

    public String getMaPM() {
        return maPM;
    }

    public void setMaPM(String maPM) {
        this.maPM = maPM;
    }

    public String getMaDG() {
        return maDG;
    }

    public void setMaDG(String maDG) {
        this.maDG = maDG;
    }

    public String getMaS() {
        return maS;
    }

    public void setMaS(String maS) {
        this.maS = maS;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public Date getHanTra() {
        return hanTra;
    }

    public void setHanTra(Date hanTra) {
        this.hanTra = hanTra;
    }
}
