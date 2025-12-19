package baitaplonjava.model;

public class m_trangchu {

    private String username;
    private boolean dangDangNhap;

    public m_trangchu() {
        dangDangNhap = false;
    }

    // ================== LOGIN STATE ==================

    public void dangNhap(String username) {
        this.username = username;
        this.dangDangNhap = true;
    }

    public void dangXuat() {
        this.username = null;
        this.dangDangNhap = false;
    }

    // ================== GETTER ==================

    public String getUsername() {
        return username;
    }

    public boolean isDangDangNhap() {
        return dangDangNhap;
    }
}
