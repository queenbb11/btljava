/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.model;

import java.util.Date;

/**
 *
 * @author Lenovo
 */
public class m_docgia {
    private String MaDG;
    private String TenDG;
    private Date NgaysinhDG;
    private String GioitinhDG;
    private String DienthoaiDG;
    private String EmailDG;
    private String DiachiDG;

    public m_docgia() {
    }

    public m_docgia(String MaDG, String TenDG, Date NgaysinhDG, String GioitinhDG, String DienthoaiDG, String EmailDG, String DiachiDG) {
        this.MaDG = MaDG;
        this.TenDG = TenDG;
        this.NgaysinhDG = NgaysinhDG;
        this.GioitinhDG = GioitinhDG;
        this.DienthoaiDG = DienthoaiDG;
        this.EmailDG = EmailDG;
        this.DiachiDG = DiachiDG;
    }

    public String getMaDG() {
        return MaDG;
    }

    public void setMaDG(String MaDG) {
        this.MaDG = MaDG;
    }

    public String getTenDG() {
        return TenDG;
    }

    public void setTenDG(String TenDG) {
        this.TenDG = TenDG;
    }

    public Date getNgaysinhDG() {
        return NgaysinhDG;
    }

    public void setNgaysinhDG(Date NgaysinhDG) {
        this.NgaysinhDG = NgaysinhDG;
    }

    public String getGioitinhDG() {
        return GioitinhDG;
    }

    public void setGioitinhDG(String GioitinhDG) {
        this.GioitinhDG = GioitinhDG;
    }

    public String getDienthoaiDG() {
        return DienthoaiDG;
    }

    public void setDienthoaiDG(String DienthoaiDG) {
        this.DienthoaiDG = DienthoaiDG;
    }

    public String getEmailDG() {
        return EmailDG;
    }

    public void setEmailDG(String EmailDG) {
        this.EmailDG = EmailDG;
    }

    public String getDiachiDG() {
        return DiachiDG;
    }

    public void setDiachiDG(String DiachiDG) {
        this.DiachiDG = DiachiDG;
    }

    
    public String HienthiDG() {
        return "m_docgia{" + "MaDG=" + MaDG + ", TenDG=" + TenDG + ", NgaysinhDG=" + NgaysinhDG + ", GioitinhDG=" + GioitinhDG + ", DienthoaiDG=" + DienthoaiDG + ", EmailDG=" + EmailDG + ", DiachiDG=" + DiachiDG + '}';
    }
    
    
    
}
