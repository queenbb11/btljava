package baitaplonjava.model;

public class m_khosach {

    private String MaK;
    private String MaS;
    private int SoluongN;
    private int SoluongX;
    private int SoluongT;

    public m_khosach() {}

    public m_khosach(String MaK, String MaS, int SoluongN, int SoluongX) {
        this.MaK = MaK;
        this.MaS = MaS;
        this.SoluongN = SoluongN;
        this.SoluongX = SoluongX;
        this.SoluongT = SoluongN - SoluongX;
    }

    public String getMaK() { return MaK; }
    public void setMaK(String MaK) { this.MaK = MaK; }

    public String getMaS() { return MaS; }
    public void setMaS(String MaS) { this.MaS = MaS; }

    public int getSoluongN() { return SoluongN; }
    public void setSoluongN(int SoluongN) { this.SoluongN = SoluongN; }

    public int getSoluongX() { return SoluongX; }
    public void setSoluongX(int SoluongX) { this.SoluongX = SoluongX; }

    public int getSoluongT() { return SoluongT; }
    public void setSoluongT(int SoluongT) { this.SoluongT = SoluongT; }
}
