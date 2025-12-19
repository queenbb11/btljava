/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.model;

import java.util.Date;

public class m_tacgia {
    private String MaTG;
    private String TenTG;
    private Date NgaysinhTG;
    private String GioitinhTG;
    private String DienthoaiTG;
    private String DiachiTG;

    public m_tacgia() {}

    public m_tacgia(String MaTG, String TenTG, Date NgaysinhTG, String GioitinhTG, String DienthoaiTG, String DiachiTG) {
        this.MaTG = MaTG;
        this.TenTG = TenTG;
        this.NgaysinhTG = NgaysinhTG;
        this.GioitinhTG = GioitinhTG;
        this.DienthoaiTG = DienthoaiTG;
        this.DiachiTG = DiachiTG;
    }

    // Getters và Setters
    public String getMaTG() { return MaTG; }
    public void setMaTG(String MaTG) { this.MaTG = MaTG; }

    public String getTenTG() { return TenTG; }
    public void setTenTG(String TenTG) { this.TenTG = TenTG; }

    public Date getNgaysinhTG() { return NgaysinhTG; }
    public void setNgaysinhTG(Date NgaysinhTG) { this.NgaysinhTG = NgaysinhTG; }

    public String getGioitinhTG() { return GioitinhTG; }
    public void setGioitinhTG(String GioitinhTG) { this.GioitinhTG = GioitinhTG; }

    public String getDienthoaiTG() { return DienthoaiTG; }
    public void setDienthoaiTG(String DienthoaiTG) { this.DienthoaiTG = DienthoaiTG; }

    public String getDiachiTG() { return DiachiTG; }
    public void setDiachiTG(String DiachiTG) { this.DiachiTG = DiachiTG; }
}