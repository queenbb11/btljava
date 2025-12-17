package baitaplonjava.model;

public class model_theloai {

    private String matheloai;
    private String tentheloai;

    public model_theloai() {
    }
    public model_theloai(String matheloai, String tentheloai) {
        this.matheloai = matheloai;
        this.tentheloai = tentheloai;
    }
    public String getmatheloai() {
        return matheloai;
    }

    public String gettentheloai() {
        return tentheloai;
    }

    public void setmatheloai(String matheloai) {
        this.matheloai = matheloai;
    }

    public void setTenTheLoai(String tentheloai) {
        this.tentheloai = tentheloai;
    }
}
