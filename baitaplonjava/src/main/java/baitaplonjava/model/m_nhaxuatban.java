/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.model;

public class m_nhaxuatban {
    private String MaNXB;
    private String TenNXB;
    private String DienthoaiNXB;
    private String EmainXB; // Giữ nguyên tên cột như trong SQL bạn viết
    private String DiachiNXB;

    public m_nhaxuatban() {}

    public m_nhaxuatban(String MaNXB, String TenNXB, String DienthoaiNXB, String EmainXB, String DiachiNXB) {
        this.MaNXB = MaNXB;
        this.TenNXB = TenNXB;
        this.DienthoaiNXB = DienthoaiNXB;
        this.EmainXB = EmainXB;
        this.DiachiNXB = DiachiNXB;
    }

    // Getters và Setters
    public String getMaNXB() { return MaNXB; }
    public void setMaNXB(String MaNXB) { this.MaNXB = MaNXB; }

    public String getTenNXB() { return TenNXB; }
    public void setTenNXB(String TenNXB) { this.TenNXB = TenNXB; }

    public String getDienthoaiNXB() { return DienthoaiNXB; }
    public void setDienthoaiNXB(String DienthoaiNXB) { this.DienthoaiNXB = DienthoaiNXB; }

    public String getEmainXB() { return EmainXB; }
    public void setEmainXB(String EmainXB) { this.EmainXB = EmainXB; }

    public String getDiachiNXB() { return DiachiNXB; }
    public void setDiachiNXB(String DiachiNXB) { this.DiachiNXB = DiachiNXB; }
}
