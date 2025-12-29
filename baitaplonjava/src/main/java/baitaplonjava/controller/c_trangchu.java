
package baitaplonjava.controller;

import baitaplonjava.view.v_trangthaisach;
import baitaplonjava.view.v_tracuu;
import baitaplonjava.view.v_thongke;
import baitaplonjava.view.v_docgia;
import baitaplonjava.view.v_nhaxuatban;
import baitaplonjava.view.v_tacgia;
import baitaplonjava.view.v_khosach;
import baitaplonjava.view.v_login;
import baitaplonjava.view.v_sach;
import baitaplonjava.view.v_nhanvien;
import baitaplonjava.view.v_theloai;
import baitaplonjava.view.v_trangchu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class c_trangchu implements ActionListener {
    
    private v_trangchu viewtrangchu;
    private v_theloai viewtheloai; 
    private v_sach viewsach;
    private v_khosach viewkhosach;

    private v_nhaxuatban viewnhaxuatban;
    private v_tacgia viewtacgia;
    private v_docgia viewdocgia;
    private v_nhanvien viewnhanvien;
    private v_trangthaisach viewtrangthaisach;
    private v_tracuu viewtracuu;
    private v_thongke viewthongke;

    public c_trangchu(v_trangchu viewtrangchu) {
        this.viewtrangchu = viewtrangchu;
       
        // Gắn sự kiện cho tất cả các nút cần xử lý
        attachListeners();
    }
    
    private void attachListeners() {
       //QL sácg
        this.viewtrangchu.getBtnSach().addActionListener(this);
        //  QL Thể Loại
        this.viewtrangchu.getBtnTheloai().addActionListener(this); 
        // QL: kho sach
         this.viewtrangchu.getBtnKhosach().addActionListener(this);
        //QL Nhà xuất bản
        this.viewtrangchu.getBtnNhaxuatban().addActionListener(this);
        //QL tác giả
        this.viewtrangchu.getBtnTacgia().addActionListener(this);
        //QL độc giả
        this.viewtrangchu.getBtnDocgia().addActionListener(this);
        //Đăng xuất
        this.viewtrangchu.getBtnDangXuat().addActionListener(this);    
       // Nhà xuất bản
        this.viewtrangchu.getBtnNhaxuatban().addActionListener(this);
        //Tacs giả
        this.viewtrangchu.getBtnTacgia().addActionListener(this);
        //Độc giả
        this.viewtrangchu.getBtnDocgia().addActionListener(this);
        // Nhân viên
        this.viewtrangchu.getBtnNhanvien().addActionListener(this);
        //Trạng thái sách
        this.viewtrangchu.getBtnTrangthaisach().addActionListener(this);
        //Tra cứu
        this.viewtrangchu.getBtnTracuu().addActionListener(this); 
        //Thống kế
        this.viewtrangchu.getBtnThongke().addActionListener(this); 
    }

    public void hienThiTrangChu() {
        viewtrangchu.setVisible(true);
    }
    
    private void moQuanlytheloaiview() {
        if (viewtheloai == null) {
            viewtheloai = new v_theloai(); 
            new c_theloai(viewtheloai, viewtrangchu); 
        }
        viewtheloai.setVisible(true);
        viewtrangchu.setVisible(false); 
    }
    
     private void moQuanlysachview() {
        if (viewsach == null) {
            viewsach = new v_sach(); 
            new c_sach(viewsach, viewtrangchu); 
        }
        viewsach.setVisible(true);
        viewtrangchu.setVisible(false); 
    }
    
      private void moQuanlykhosachview() {
        if (viewkhosach == null) {
            viewkhosach = new v_khosach(); 
            new c_khosach(viewkhosach, viewtrangchu); 
        }
        viewkhosach.setVisible(true);
        viewtrangchu.setVisible(false); 
    }

      
      private void moQuanlyNhaxuatbanview() {
        if (viewnhaxuatban == null) {
            viewnhaxuatban = new v_nhaxuatban(); 
            new c_nhaxuatban(viewnhaxuatban, viewtrangchu); 
        }
        viewnhaxuatban.setVisible(true);
        viewtrangchu.setVisible(false); 
    }
      
      private void moQuanlyTacgiaview() {
        if (viewtacgia == null) {
            viewtacgia = new v_tacgia(); 
            new c_tacgia(viewtacgia, viewtrangchu); 
        }
        viewtacgia.setVisible(true);
        viewtrangchu.setVisible(false); 
    }
      
      private void moQuanlyDocgiaview() {
        if (viewdocgia == null) {
            viewdocgia = new v_docgia(); 
            new c_docgia(viewdocgia, viewtrangchu); 
        }
        viewdocgia.setVisible(true);
        viewtrangchu.setVisible(false); 
    }
     
      
      private void moQuanlyTrangthaisachview() {
        if (viewtrangthaisach == null) {
            viewtrangthaisach = new v_trangthaisach(); 

            new c_trangthaisach(viewtrangthaisach, viewtrangchu); 
        }
        viewtrangthaisach.setVisible(true);
        viewtrangchu.setVisible(false); 
    }
      
      private void moQuanlyTracuuview() {
        if (viewtracuu == null) {
            viewtracuu = new v_tracuu(); 

            new c_tracuu(viewtracuu, viewtrangchu); 
        }
        viewtracuu.setVisible(true);
        viewtrangchu.setVisible(false); 
    }
      
      
      private void moThongkeview() {
        if (viewthongke == null) {
            viewthongke = new v_thongke(); 
            new c_thongke(viewthongke, viewtrangchu); 
        }
        viewthongke.setVisible(true);
        viewtrangchu.setVisible(false); 
    }
    
    private void moNhanvienview(){
    if (viewnhanvien == null) {
            viewnhanvien = new v_nhanvien(); 
            new c_nhanvien(viewnhanvien, viewtrangchu); 
        }
        viewnhanvien.setVisible(true);
        viewtrangchu.setVisible(false); 
    }
    
    
    
    
    
    
    // Hàm xử lý Đăng Xuất
    private void handleDangXuat() {
        int confirm = JOptionPane.showConfirmDialog(viewtrangchu, 
                "Bạn có chắc chắn muốn đăng xuất?", 
                "Xác nhận Đăng xuất", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // 1. Ẩn cửa sổ Trang Chủ
            viewtrangchu.dispose();
            // 2. Mở lại cửa sổ Đăng Nhập
            v_login vLogin = new v_login();
            new c_login(); 
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        // QL sách
        if (source == viewtrangchu.getBtnSach()) {
           moQuanlysachview();
        } else if (source == viewtrangchu.getBtnTheloai()) {
            // Xử lý khi nhấn nút QL Thể Loại
            moQuanlytheloaiview();      
        } 
        else if (source == viewtrangchu.getBtnKhosach()) {
            // Xử lý khi nhấn nút QL kho sách
            moQuanlykhosachview();      
        } 
        else if (source == viewtrangchu.getBtnDangXuat()) {
            // Xử lý khi nhấn nút Đăng Xuất
            handleDangXuat();
        }
        else if (source == viewtrangchu.getBtnNhaxuatban()) {
            // Xử lý khi nhấn nút QL nhà xuất bản
            moQuanlyNhaxuatbanview();
        }
        
        else if (source == viewtrangchu.getBtnTacgia()) {
            // Xử lý khi nhấn nút QL tác giả
            moQuanlyTacgiaview();
        }
        
        else if (source == viewtrangchu.getBtnDocgia()) {
            // Xử lý khi nhấn nút QL độc giả
            moQuanlyDocgiaview();
        }
        
        else if (source == viewtrangchu.getBtnTrangthaisach()) {
            // Xử lý khi nhấn nút QL Trạng thái sách
            moQuanlyTrangthaisachview();
        }
        
        else if (source == viewtrangchu.getBtnTracuu()) {
            // Xử lý khi nhấn nút QL Tra cứu
            moQuanlyTracuuview();
        }
        else if (source == viewtrangchu.getBtnThongke()) {
            // Xử lý khi nhấn nút Thống kê
            moThongkeview();
        }
        else if (source == viewtrangchu.getBtnNhanvien()) {
            // Xử lý khi nhấn nút Nhân viên
            moNhanvienview();
        }
    }
}
