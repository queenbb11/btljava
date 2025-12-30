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
public class m_phieutra {
    private String MaPT;   // Mã phiếu trả
    private String MaPM;   // Mã phiếu mượn
    private Date NgayTra;  // Ngày trả

    public m_phieutra() {
    }

    public m_phieutra(String MaPT, String MaPM, Date NgayTra) {
        this.MaPT = MaPT;
        this.MaPM = MaPM;
        this.NgayTra = NgayTra;
    }

    // ===== GETTER & SETTER =====
    public String getMaPT() {
        return MaPT;
    }

    public void setMaPT(String MaPT) {
        this.MaPT = MaPT;
    }

    public String getMaPM() {
        return MaPM;
    }

    public void setMaPM(String MaPM) {
        this.MaPM = MaPM;
    }

    public Date getNgayTra() {
        return NgayTra;
    }

    public void setNgayTra(Date NgayTra) {
        this.NgayTra = NgayTra;
    }
}