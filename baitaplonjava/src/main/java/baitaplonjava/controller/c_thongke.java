package baitaplonjava.controller;

import baitaplonjava.view.v_thongke;
import baitaplonjava.view.v_trangchu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class c_thongke {

    private v_thongke v;
    private v_trangchu trangchu;

    public c_thongke(v_thongke viewTK, v_trangchu viewtrangchu) {
        this.v = viewTK;
        this.trangchu = viewtrangchu;

        // Gán sự kiện
        v.bt_thongke_action_listener(new action_thongke());
        v.bt_quaylai_action_listener(e -> quaylai());
    }

    // ================= KẾT NỐI DB =================
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/baitaplon",
                "root", "123456789"
        );
    }

    // ================= THỐNG KÊ =================
    class action_thongke implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String loai = v.getLoaiThongKe();

            if (loai.equals("Thống kê sách theo thể loại")) {
                thongKeTheoTheLoai();
            } else if (loai.equals("Thống kê sách theo tác giả")) {
                thongKeTheoTacGia();
            }
        }
    }

    // ================= THỐNG KÊ THEO THỂ LOẠI =================
    private void thongKeTheoTheLoai() {
        v.model.setRowCount(0);
        v.model.setColumnIdentifiers(new Object[]{
            "Tên thể loại", "Số lượng sách"
        });

        try (Connection c = getConnection()) {
            String sql =
                "SELECT tl.TenTL, COUNT(s.MaS) AS SoLuong " +
                "FROM Theloai tl " +
                "LEFT JOIN Sach s ON tl.MaTL = s.MaTL " +
                "GROUP BY tl.TenTL";

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                v.model.addRow(new Object[]{
                    rs.getString("TenTL"),
                    rs.getInt("SoLuong")
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(v, "Lỗi thống kê theo thể loại!");
        }
    }

    // ================= THỐNG KÊ THEO TÁC GIẢ =================
    private void thongKeTheoTacGia() {
        v.model.setRowCount(0);
        v.model.setColumnIdentifiers(new Object[]{
            "Tên tác giả", "Số lượng sách"
        });

        try (Connection c = getConnection()) {
            String sql =
                "SELECT tg.TenTG, COUNT(s.MaS) AS SoLuong " +
                "FROM Tacgia tg " +
                "LEFT JOIN Sach s ON tg.MaTG = s.MaTG " +
                "GROUP BY tg.TenTG";

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                v.model.addRow(new Object[]{
                    rs.getString("TenTG"),
                    rs.getInt("SoLuong")
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(v, "Lỗi thống kê theo tác giả!");
        }
    }

    // ================= QUAY LẠI =================
    private void quaylai() {
        v.dispose();
        trangchu.setVisible(true);
    }
}
