/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.controller;

import baitaplonjava.model.m_docgia;
import baitaplonjava.view.v_docgia;
import baitaplonjava.view.v_trangchu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class c_docgia {
    private v_docgia v;
    private v_trangchu trangchu;
    private int k = -1;

    public c_docgia(v_docgia viewdocgia, v_trangchu viewtrangchu) {

        this.v = viewdocgia;
        this.trangchu = viewtrangchu;

        v.bt_them_action_listenner(new action_them());
        v.bt_sua_action_listenner(new action_sua());
        v.bt_xoa_action_listenner(new action_xoa());
        v.bt_reset_action_listenner(e -> reset());
        v.bt_timkiem_action_listenner(new action_timkiem());
        v.bt_xuatfile_action_listenner(new action_xuatfile());
        v.bt_quaylai_action_listenner(e -> quaylai());

        v.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                k = v.table.getSelectedRow();
                hienThiLenForm();
            }
        });

        loadData();
    }

    // ================= KẾT NỐI DB =================
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/baitaplon",
                "root", "123456"
        );
    }

    // ================= LOAD DATA =================
    private void loadData() {
        v.model.setRowCount(0);
        try (Connection c = getConnection()) {

            String sql = "SELECT * FROM Docgia";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            while (rs.next()) {
                Date ns = rs.getTimestamp("NgaysinhDG");

                v.model.addRow(new Object[]{
                    rs.getString("MaDG"),
                    rs.getString("TenDG"),
                    ns == null ? "" : sdf.format(ns),
                    rs.getString("GioitinhDG"),
                    rs.getString("DienthoaiDG"),
                    rs.getString("EmailDG"),
                    rs.getString("DiachiDG")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= HIỂN THỊ LÊN FORM =================
    private void hienThiLenForm() {
        if (k < 0) return;

        v.txtMaDG.setText(v.table.getValueAt(k, 0).toString());
        v.txtTenDG.setText(v.table.getValueAt(k, 1).toString());
        v.txtNgaysinh.setText(v.table.getValueAt(k, 2).toString());
        v.txtGioitinh.setText(v.table.getValueAt(k, 3).toString());
        v.txtDienthoai.setText(v.table.getValueAt(k, 4).toString());
        v.txtEmail.setText(v.table.getValueAt(k, 5).toString());
        v.txtDiachi.setText(v.table.getValueAt(k, 6).toString());

        v.txtMaDG.setEditable(false);
    }

    // ================= RESET =================
    private void reset() {
        v.resetForm();
        v.table.clearSelection();
        k = -1;
    }

    // ================= THÊM =================
    class action_them implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            m_docgia dg = v.getDocgia();
            if (dg == null) return;

            try (Connection c = getConnection()) {

                // check trùng
                String check = "SELECT MaDG FROM Docgia WHERE MaDG=?";
                PreparedStatement psCheck = c.prepareStatement(check);
                psCheck.setString(1, dg.getMaDG());
                if (psCheck.executeQuery().next()) {
                    JOptionPane.showMessageDialog(v, "Mã độc giả đã tồn tại!");
                    return;
                }

                String sql = "INSERT INTO Docgia VALUES (?,?,?,?,?,?,?)";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, dg.getMaDG());
                ps.setString(2, dg.getTenDG());
                ps.setTimestamp(3, new java.sql.Timestamp(dg.getNgaysinhDG().getTime()));
                ps.setString(4, dg.getGioitinhDG());
                ps.setString(5, dg.getDienthoaiDG());
                ps.setString(6, dg.getEmailDG());
                ps.setString(7, dg.getDiachiDG());
                ps.executeUpdate();

                JOptionPane.showMessageDialog(v, "Thêm độc giả thành công!");
                loadData();
                reset();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi thêm dữ liệu!");
            }
        }
    }

    // ================= SỬA =================
    class action_sua implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (v.txtMaDG.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(v, "Chọn độc giả cần sửa!");
                return;
            }

            try (Connection c = getConnection()) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date ns = sdf.parse(v.txtNgaysinh.getText().trim());

                String sql = """
                    UPDATE Docgia
                    SET TenDG=?, NgaysinhDG=?, GioitinhDG=?, 
                        DienthoaiDG=?, EmailDG=?, DiachiDG=?
                    WHERE MaDG=?
                """;

                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, v.txtTenDG.getText().trim());
                ps.setTimestamp(2, new java.sql.Timestamp(ns.getTime()));
                ps.setString(3, v.txtGioitinh.getText().trim());
                ps.setString(4, v.txtDienthoai.getText().trim());
                ps.setString(5, v.txtEmail.getText().trim());
                ps.setString(6, v.txtDiachi.getText().trim());
                ps.setString(7, v.txtMaDG.getText().trim());
                ps.executeUpdate();

                JOptionPane.showMessageDialog(v, "Sửa thành công!");
                loadData();
                reset();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi sửa dữ liệu!");
            }
        }
    }

    // ================= XÓA =================
    class action_xoa implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String ma = v.txtMaDG.getText().trim();
            if (ma.isEmpty()) return;

            int c = JOptionPane.showConfirmDialog(v, "Xóa độc giả này?");
            if (c != JOptionPane.YES_OPTION) return;

            try (Connection cn = getConnection()) {

                String sql = "DELETE FROM Docgia WHERE MaDG=?";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setString(1, ma);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(v, "Xóa thành công!");
                loadData();
                reset();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Không thể xóa!");
            }
        }
    }

    // ================= TÌM KIẾM =================
    class action_timkiem implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String key = v.txtTimkiem.getText().trim();
            if (key.isEmpty()) {
                loadData();
                return;
            }

            v.model.setRowCount(0);

            try (Connection c = getConnection()) {

                String sql = """
                    SELECT * FROM Docgia
                    WHERE MaDG LIKE ? OR TenDG LIKE ?
                """;

                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, "%" + key + "%");
                ps.setString(2, "%" + key + "%");

                ResultSet rs = ps.executeQuery();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                while (rs.next()) {
                    Date ns = rs.getTimestamp("NgaysinhDG");

                    v.model.addRow(new Object[]{
                        rs.getString("MaDG"),
                        rs.getString("TenDG"),
                        ns == null ? "" : sdf.format(ns),
                        rs.getString("GioitinhDG"),
                        rs.getString("DienthoaiDG"),
                        rs.getString("EmailDG"),
                        rs.getString("DiachiDG")
                    });
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi tìm kiếm!");
            }
        }
    }

    // ================= XUẤT FILE CSV =================
    class action_xuatfile implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        if (v.model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(v, "Không có dữ liệu để xuất!");
            return;
        }

        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        fc.setDialogTitle("Chọn nơi lưu file");
        fc.setSelectedFile(new java.io.File("docgia.csv"));

        int choose = fc.showSaveDialog(v);
        if (choose != javax.swing.JFileChooser.APPROVE_OPTION) return;

        java.io.File file = fc.getSelectedFile();

        try (
            java.io.OutputStreamWriter osw =
                new java.io.OutputStreamWriter(
                    new java.io.FileOutputStream(file),
                    java.nio.charset.StandardCharsets.UTF_8
                );
            java.io.BufferedWriter bw = new java.io.BufferedWriter(osw)
        ) {

            // BOM UTF-8 cho Excel đọc đúng tiếng Việt
            bw.write("\uFEFF");

            // ===== HEADER =====
            for (int i = 0; i < v.model.getColumnCount(); i++) {
                bw.write(v.model.getColumnName(i));
                if (i < v.model.getColumnCount() - 1) bw.write(",");
            }
            bw.newLine();

            // ===== DATA =====
            for (int i = 0; i < v.model.getRowCount(); i++) {
                for (int j = 0; j < v.model.getColumnCount(); j++) {
                    Object val = v.model.getValueAt(i, j);
                    bw.write(val == null ? "" : val.toString());
                    if (j < v.model.getColumnCount() - 1) bw.write(",");
                }
                bw.newLine();
            }

            JOptionPane.showMessageDialog(v, "Xuất file độc giả thành công!");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(v, "Lỗi xuất file!");
        }
    }
    }

    // ================= QUAY LẠI =================
    private void quaylai() {
        v.dispose();
        trangchu.setVisible(true);
    }
}
