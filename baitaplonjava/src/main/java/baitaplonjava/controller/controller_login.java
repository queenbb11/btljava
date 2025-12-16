/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.controller;

import baitaplonjava.view.view_login;
import baitaplonjava.model.model_login;
import baitaplonjava.view.view_trangchu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane; // <--- Quan trọng: Phải có dòng này

public class controller_login {

    private Connection con = null;
    
    // Khai báo 'view' ở đây để toàn bộ class đều dùng được
    private view_login view; 

    public controller_login() {
        // 1. Kết nối Database
        connectDB();

        // 2. Khởi tạo view và gán vào biến toàn cục
        view = new view_login();
        view.setVisible(true);

        // 3. Gắn sự kiện click nút Login
        // Lưu ý: Đảm bảo bên view_login có hàm getBtnLogin() hoặc check_login_listener
        view.check_login_listener(new check_login_listener());
    }

    private void connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/baitaplon";
            String user = "root";
            String password = "123456789"; 
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Kết nối CSDL thành công!");
        } catch (Exception e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
            // Nếu lỗi kết nối, hiện thông báo luôn
            JOptionPane.showMessageDialog(null, "Không thể kết nối CSDL!");
        }
    }

    public void check_login(model_login tk) {
        if (con == null) {
            JOptionPane.showMessageDialog(view, "Chưa kết nối được Database!");
            return;
        }

        String sql = "SELECT * FROM dangnhap WHERE username = ? AND password = ?";
        
        try {
            // Dùng PreparedStatement cho an toàn
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, tk.getUser());
            pst.setString(2, tk.getPass());

            ResultSet rec = pst.executeQuery();

            if (rec.next()) {
                System.out.println("Đăng nhập thành công!");
                view.dispose(); 
                
                // Chuyển sang Trang Chủ
                chuyenSangTrangChu();
            } else {
                // --- ĐĂNG NHẬP THẤT BẠI ---
                // KHÔNG đóng kết nối ở đây, để người dùng nhập lại
                
                // Nếu 'view' bị lỗi đỏ, hãy đổi thành 'null' -> JOptionPane.showMessageDialog(null, "...");
                JOptionPane.showMessageDialog(view, "Sai tài khoản hoặc mật khẩu!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi truy vấn: " + e.getMessage());
        }
    }

    // Hàm chuyển hướng sang Dashboard
    private void chuyenSangTrangChu() {
        view_trangchu vHome = new view_trangchu();
    
        
        // Gọi controller trang chủ để quản lý các nút bấm
        controller_trangchu ctrlTrangChu = new controller_trangchu(vHome);
        ctrlTrangChu.hienThiTrangChu();
    }

    // Lớp lắng nghe sự kiện
    class check_login_listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Lấy dữ liệu user/pass từ view
            model_login model = view.get_tk(); 
            // Gọi hàm kiểm tra
            check_login(model); 
        }
    }
}
