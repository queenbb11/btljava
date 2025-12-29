
package baitaplonjava.model;


public class m_trangthaisach {
    private String MaS;
    private String TenTrangThai;
    private String Mota;

    public m_trangthaisach() {
    }

    public m_trangthaisach(String MaS, String TenTrangThai, String Mota) {
        this.MaS = MaS;
        this.TenTrangThai = TenTrangThai;
        this.Mota = Mota;
    }

    public String getMaS() {
        return MaS;
    }

    public String getTenTrangThai() {
        return TenTrangThai;
    }

    public String getMota() {
        return Mota;
    }

    public void setMaS(String MaS) {
        this.MaS = MaS;
    }

    public void setTenTrangThai(String TenTrangThai) {
        this.TenTrangThai = TenTrangThai;
    }

    public void setMota(String Mota) {
        this.Mota = Mota;
    }
    
    
}
