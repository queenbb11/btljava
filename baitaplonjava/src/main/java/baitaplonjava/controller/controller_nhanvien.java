package baitaplonjava.controller;

import baitaplonjava.model.model_nhanvien;
import baitaplonjava.view.view_nhanvien;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat; // Thêm thư viện xử lý ngày
import java.util.Date;             // Thêm thư viện ngày

public class controller_nhanvien { 

    private view_nhanvien view;

    public controller_nhanvien(view_nhanvien view) {
        this.view = view;
        
        // Sự kiện nút Lưu
        this.view.btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                luuNhanVien();
            }
        });
    }

    private Connection connect() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/baitaplon?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "admin"; // Điền đúng mật khẩu của bạn

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }

    private void luuNhanVien() {
        try {
            // 1. Lấy dữ liệu từ giao diện
            String ma = view.txtMaNV.getText();
            String ten = view.txtTenNV.getText();
            String nsRaw = view.txtNgaysinhNV.getText(); 
            String gt = view.txtGioitinhNV.getText();
            String dt = view.txtDienthoaiNV.getText();
            String email = view.txtEmailNV.getText();
            String dc = view.txtDiachiNV.getText();
            
            // Validate: Kiểm tra rỗng
            if(ma.isEmpty()){
                view.thongBao("Mã nhân viên không được để trống!");
                return;
            }

            String nsMySQL = ""; 
            try {
                String dateStr = nsRaw.replace("/", "-"); 
                SimpleDateFormat inputFmt = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat outputFmt = new SimpleDateFormat("yyyy-MM-dd");
                Date date = inputFmt.parse(dateStr);
                nsMySQL = outputFmt.format(date); 
            } catch (Exception e) {
                view.thongBao("Lỗi ngày sinh! Vui lòng nhập đúng dạng: dd-MM-yyyy");
                return; 
            }
            model_nhanvien nv = new model_nhanvien(ma, ten, nsRaw, gt, dt, email, dc);
            String sql = "INSERT INTO Nhanvien(MaNV, TenNV, NgaysinhNV, GioitinhNV, DienthoaiNV, EmailNV, DiachiNV) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            try(Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {
                
                ps.setString(1, nv.getMaNV());
                ps.setString(2, nv.getTenNV());
                ps.setString(3, nsMySQL); 
                ps.setString(4, nv.getGioitinhNV());
                ps.setString(5, nv.getDienthoaiNV());
                ps.setString(6, nv.getEmailNV());
                ps.setString(7, nv.getDiachiNV());
                
                ps.executeUpdate();
            }

            // Cập nhật lên bảng View
            view.themDong(new Object[]{
                nv.getMaNV(), nv.getTenNV(), nv.getNgaysinhNV(),
                nv.getGioitinhNV(), nv.getDienthoaiNV(), nv.getEmailNV(), nv.getDiachiNV()
            });
            
            view.thongBao("✅ Lưu Nhân viên thành công!");
            xoaForm();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            view.thongBao("Lỗi: " + ex.getMessage());
        }
    }

    private void xoaForm() {
        view.txtMaNV.setText("");
        view.txtTenNV.setText("");
        view.txtNgaysinhNV.setText("");
        view.txtGioitinhNV.setText("");
        view.txtDienthoaiNV.setText("");
        view.txtEmailNV.setText("");
        view.txtDiachiNV.setText("");
    }
}