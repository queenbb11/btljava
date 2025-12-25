/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.model;

public class m_sach {
    private String MaS;
    private String TenS;
    private String MaTL;   // Khóa lấy từ m_theloai
    private String MaNXB;  // Khóa lấy từ m_nhaxuatban
    private String MaTG;   // Khóa lấy từ m_tacgia
    private int Namxuatban;
    private String Tinhtrang;
    private String Mota;

    public m_sach() {}

    public m_sach(String MaS, String TenS, String MaTL, String MaNXB, String MaTG, int Namxuatban, String Tinhtrang, String Mota) {
        this.MaS = MaS;
        this.TenS = TenS;
        this.MaTL = MaTL;
        this.MaNXB = MaNXB;
        this.MaTG = MaTG;
        this.Namxuatban = Namxuatban;
        this.Tinhtrang = Tinhtrang;
        this.Mota = Mota;
    }

    // Getters và Setters
    public String getMaS() { return MaS; }
    public void setMaS(String MaS) { this.MaS = MaS; }

    public String getTenS() { return TenS; }
    public void setTenS(String TenS) { this.TenS = TenS; }

    public String getMaTL() { return MaTL; }
    public void setMaTL(String MaTL) { this.MaTL = MaTL; }

    public String getMaNXB() { return MaNXB; }
    public void setMaNXB(String MaNXB) { this.MaNXB = MaNXB; }

    public String getMaTG() { return MaTG; }
    public void setMaTG(String MaTG) { this.MaTG = MaTG; }

    public int getNamxuatban() { return Namxuatban; }
    public void setNamxuatban(int Namxuatban) { this.Namxuatban = Namxuatban; }

    public String getTinhtrang() { return Tinhtrang; }
    public void setTinhtrang(String Tinhtrang) { this.Tinhtrang = Tinhtrang; }

    public String getMota() { return Mota; }
    public void setMota(String Mota) { this.Mota = Mota; }
}