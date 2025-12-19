package baitaplonjava.model;

public class m_theloai {
    private String MaTL;
    private String TenTL;

    public m_theloai() {
    }

    public m_theloai(String MaTL, String TenTL) {
        this.MaTL = MaTL;
        this.TenTL = TenTL;
    }

    public String getMaTL() {
        return MaTL;
    }

    public void setMaTL(String MaTL) {
        this.MaTL = MaTL;
    }

    public String getTenTL() {
        return TenTL;
    }

    public void setTenTL(String TenTL) {
        this.TenTL = TenTL;
    }
}