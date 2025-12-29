package baitaplonjava.model;

import java.util.Date;

public class m_nhanvien {

    private String maNV;
    private String tenNV;
    private Date ngaySinh;      
    private String gioiTinh;
    private String dienThoai;
    private String email;
    private String diaChi;

    public m_nhanvien(String maNV, String tenNV, Date ngaySinh,
                      String gioiTinh, String dienThoai,
                      String email, String diaChi) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.dienThoai = dienThoai;
        this.email = email;
        this.diaChi = diaChi;
    }

    // ===== GETTER =====
    public Date getNgaySinh() {
        return ngaySinh;
    }

    public java.sql.Date getNgaySinhSQL() {
        return new java.sql.Date(ngaySinh.getTime());
    }

    public String getMaNV() { return maNV; }
    public String getTenNV() { return tenNV; }
    public String getGioiTinh() { return gioiTinh; }
    public String getDienThoai() { return dienThoai; }
    public String getEmail() { return email; }
    public String getDiaChi() { return diaChi; }
}
