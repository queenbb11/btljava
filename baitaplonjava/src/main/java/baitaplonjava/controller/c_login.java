package baitaplonjava.controller;


import baitaplonjava.model.m_login;
import baitaplonjava.view.v_login;
import baitaplonjava.view.v_trangchu;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class c_login {

    private Connection con;
    private v_login view;

    public c_login() {
        connectDB();

        view = new v_login();
        view.setVisible(true);

        view.check_login_listener(new login_listener());
        view.register_listener(e -> moFormDangKy());
    }

    // ================= KẾT NỐI DATABASE =================
    private void connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/baitaplon",
                    "root",
                    "123456789"
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối CSDL!");
        }
    }

    // ================= ĐĂNG NHẬP =================
    private void check_login(m_login tk) {

        String sql = "SELECT * FROM dangnhap WHERE username=? AND password=?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, tk.getUser());
            pst.setString(2, tk.getPass());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(view, "Đăng nhập thành công!");
                view.dispose();
                chuyenSangTrangChu();
            } else {
                JOptionPane.showMessageDialog(view, "Sai tài khoản hoặc mật khẩu!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi đăng nhập!");
        }
    }

    // ================= MỞ FORM ĐĂNG KÝ =================
    private void moFormDangKy() {
        new c_dangky(con); 
    }

    // ================= TRANG CHỦ =================
    private void chuyenSangTrangChu() {
        v_trangchu vtrangchu = new v_trangchu();
        new c_trangchu(vtrangchu).hienThiTrangChu();
    }

    // ================= LISTENER =================
    class login_listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            check_login(view.get_tk());
        }
    }
}
