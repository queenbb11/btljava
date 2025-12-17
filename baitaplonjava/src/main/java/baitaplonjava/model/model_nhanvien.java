/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.model;

/**
 *
 * @author tduy2
 */
public class model_nhanvien  {
    private String MaNV;
    private String TenNV;
    private String NgaysinhNV;
    private String GioitinhNV;
    private String DienthoaiNV;
    private String EmailNV;
    private String DiachiNV;    
    
    public model_nhanvien (){
        
    }

    public model_nhanvien(String MaNV, String TenNV, String NgaysinhNV, String GioitinhNV, String DienthoaiNV, String EmailNV, String DiachiNV) {
        this.MaNV = MaNV;
        this.TenNV = TenNV;
        this.NgaysinhNV = NgaysinhNV;
        this.GioitinhNV = GioitinhNV;
        this.DienthoaiNV = DienthoaiNV;
        this.EmailNV = EmailNV;
        this.DiachiNV = DiachiNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public void setNgaysinhNV(String NgaysinhNV) {
        this.NgaysinhNV = NgaysinhNV;
    }

    public void setGioitinhNV(String GioitinhNV) {
        this.GioitinhNV = GioitinhNV;
    }

    public void setDienthoaiNV(String DienthoaiNV) {
        this.DienthoaiNV = DienthoaiNV;
    }

    public void setEmailNV(String EmailNV) {
        this.EmailNV = EmailNV;
    }

    public void setDiachiNV(String DaichiNV) {
        this.DiachiNV = DiachiNV;
    }

    public String getMaNV() {
        return MaNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public String getNgaysinhNV() {
        return NgaysinhNV;
    }

    public String getGioitinhNV() {
        return GioitinhNV;
    }

    public String getDienthoaiNV() {
        return DienthoaiNV;
    }

    public String getEmailNV() {
        return EmailNV;
    }

    public String getDiachiNV() {
        return DiachiNV;
    }

}