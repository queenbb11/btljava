
package baitaplonjava.model;


public class m_phieuphat {
    private String MaPP;
    private String MaPM;
    private int TienPhat;
    private String MaNV;

    public m_phieuphat(String MaPP, String MaPM, String MaNV, int TienPhat) {
        this.MaPP = MaPP;
        this.MaPM = MaPM;
        this.TienPhat = TienPhat;
        this.MaNV = MaNV;
    }

    public String getMaPP() { return MaPP; }
    public void setMaPP(String MaPP) { this.MaPP = MaPP; }

    public String getMaPM() { return MaPM; }
    public void setMaPM(String MaPM) { this.MaPM = MaPM; }

    public int getTienPhat() { return TienPhat; }
    public void setTienPhat(int tienPhat) { this.TienPhat = tienPhat; }
    
    public String getMaNV() { return MaNV; }
    public void setMaNV(String MaNV) { this.MaNV = MaNV;}
}
