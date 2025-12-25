package baitaplonjava.controller;

import baitaplonjava.view.v_dangky;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class c_dangky {

    private v_dangky view;
    private Connection con;

    public c_dangky(Connection con) {
        this.con = con;
        view = new v_dangky();
        view.setVisible(true);

        view.register_listener(new register_listener());
    }

    class register_listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            xuLyDangKy();
        }
    }

    private void xuLyDangKy() {

        String username = view.getUsername();
        String pass = view.getPassword();
        String repass = view.getRePassword();

        if (username.isEmpty() || pass.isEmpty() || repass.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (!pass.equals(repass)) {
            JOptionPane.showMessageDialog(view, "Mật khẩu nhập lại không khớp!");
            return;
        }

        try {
            // Kiểm tra trùng username
            String checkSql = "SELECT * FROM dangnhap WHERE username = ?";
            PreparedStatement pstCheck = con.prepareStatement(checkSql);
            pstCheck.setString(1, username);
            ResultSet rs = pstCheck.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(view, "Username đã tồn tại!");
                return;
            }

            // Insert
            String sql = "INSERT INTO dangnhap(username, password) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, pass);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(view, "Bạn đã đăng ký thành công!");
            view.dispose(); // Đóng form đăng ký

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi đăng ký!");
        }
    }
}
