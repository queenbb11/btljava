package baitaplonjava.controller;

import baitaplonjava.view.v_tracuu;
import baitaplonjava.view.v_trangchu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class c_tracuu {

    private v_tracuu v;
    private v_trangchu trangchu;

    public c_tracuu(v_tracuu view, v_trangchu viewTrangChu) {
        this.v = view;
        this.trangchu = viewTrangChu;

        v.bt_timkiem_action_listener(new action_timkiem());
        v.bt_quaylai_action_listener(e -> quaylai());
    }

    // ================= KẾT NỐI DB =================
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/baitaplon",
                "root", "1234567890"
        );
    }

    // ================= TÌM KIẾM =================
    class action_timkiem implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String key = v.getTuKhoa();
            String tieuChi = v.getTieuChi();

            if (key.isEmpty()) {
                JOptionPane.showMessageDialog(v, "Vui lòng nhập từ khóa tìm kiếm!");
                return;
            }

            v.model.setRowCount(0);

            String sql = buildSQL(tieuChi);

            try (Connection c = getConnection()) {
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, "%" + key + "%");

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    v.model.addRow(new Object[]{
                        rs.getString("MaS"),
                        rs.getString("TenS"),
                        rs.getString("TenTL"),
                        rs.getString("TenTG"),
                        rs.getString("TenNXB"),
                        rs.getString("Tentrangthai")
                    });
                }

                if (v.model.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(v, "Không tìm thấy kết quả phù hợp!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi tra cứu dữ liệu!");
            }
        }
    }

    // ================= TẠO SQL THEO TIÊU CHÍ =================
    private String buildSQL(String tieuChi) {

        String baseSQL = """
            SELECT s.MaS, s.TenS, tl.TenTL, tg.TenTG, nxb.TenNXB, tt.Tentrangthai
            FROM Sach s
            JOIN Theloai tl ON s.MaTL = tl.MaTL
            JOIN Tacgia tg ON s.MaTG = tg.MaTG
            JOIN Nhaxuatban nxb ON s.MaNXB = nxb.MaNXB
            LEFT JOIN Trangthaisach tt ON s.MaS = tt.MaS
        """;

        switch (tieuChi) {
            case "Tên sách":
                return baseSQL + " WHERE s.TenS LIKE ?";
            case "Thể loại":
                return baseSQL + " WHERE tl.TenTL LIKE ?";
            case "Tác giả":
                return baseSQL + " WHERE tg.TenTG LIKE ?";
            case "Nhà xuất bản":
                return baseSQL + " WHERE nxb.TenNXB LIKE ?";
            case "Trạng thái":
                return baseSQL + " WHERE tt.Tentrangthai LIKE ?";
            default:
                return baseSQL;
        }
    }

    // ================= QUAY LẠI =================
    private void quaylai() {
        v.dispose();
        trangchu.setVisible(true);
    }
}
