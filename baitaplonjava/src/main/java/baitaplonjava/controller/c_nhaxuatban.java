/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.controller;

import baitaplonjava.model.m_nhaxuatban;
import baitaplonjava.view.v_nhaxuatban;
import baitaplonjava.view.v_trangchu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class c_nhaxuatban {
    private v_nhaxuatban v;
    private v_trangchu trangchu;
    private int k = -1;

    public c_nhaxuatban(v_nhaxuatban viewnhaxuatban, v_trangchu viewtrangchu) {

        this.v = viewnhaxuatban;
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
                "root", "123456789"
        );
    }

    // ================= LOAD DATA =================
    private void loadData() {
        v.model.setRowCount(0);
        try (Connection c = getConnection()) {

            String sql = "SELECT * FROM Nhaxuatban";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                v.model.addRow(new Object[]{
                    rs.getString("MaNXB"),
                    rs.getString("TenNXB"),
                    rs.getString("DienthoaiNXB"),
                    rs.getString("EmaiNXB"),
                    rs.getString("DiachiNXB")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= HIỂN THỊ LÊN FORM =================
    private void hienThiLenForm() {
        if (k < 0) return;

        v.txtMaNXB.setText(v.table.getValueAt(k, 0).toString());
        v.txtTenNXB.setText(v.table.getValueAt(k, 1).toString());
        v.txtDienthoai.setText(v.table.getValueAt(k, 2).toString());
        v.txtEmail.setText(v.table.getValueAt(k, 3).toString());
        v.txtDiachi.setText(v.table.getValueAt(k, 4).toString());

        v.txtMaNXB.setEditable(false);
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

            m_nhaxuatban nxb = v.getNXB();
            if (nxb == null) return;

            try (Connection c = getConnection()) {

                // check trùng
                String check = "SELECT MaNXB FROM Nhaxuatban WHERE MaNXB=?";
                PreparedStatement psCheck = c.prepareStatement(check);
                psCheck.setString(1, nxb.getMaNXB());
                if (psCheck.executeQuery().next()) {
                    JOptionPane.showMessageDialog(v, "Mã NXB đã tồn tại!");
                    return;
                }

                String sql = "INSERT INTO Nhaxuatban VALUES (?,?,?,?,?)";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, nxb.getMaNXB());
                ps.setString(2, nxb.getTenNXB());
                ps.setString(3, nxb.getDienthoaiNXB());
                ps.setString(4, nxb.getEmainXB());
                ps.setString(5, nxb.getDiachiNXB());
                ps.executeUpdate();

                JOptionPane.showMessageDialog(v, "Thêm thành công!");
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

            if (v.txtMaNXB.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(v, "Chọn Nhà Xuất Bản cần sửa!");
                return;
            }

            try (Connection c = getConnection()) {

                String sql = """
                    UPDATE Nhaxuatban
                    SET TenNXB=?, DienthoaiNXB=?, EmaiNXB=?, DiachiNXB=?
                    WHERE MaNXB=?
                """;

                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, v.txtTenNXB.getText().trim());
                ps.setString(2, v.txtDienthoai.getText().trim());
                ps.setString(3, v.txtEmail.getText().trim());
                ps.setString(4, v.txtDiachi.getText().trim());
                ps.setString(5, v.txtMaNXB.getText().trim());
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

            String ma = v.txtMaNXB.getText().trim();
            if (ma.isEmpty()) return;

            int c = JOptionPane.showConfirmDialog(v, "Xóa Nhà Xuất Bản này?");
            if (c != JOptionPane.YES_OPTION) return;

            try (Connection cn = getConnection()) {

                String sql = "DELETE FROM Nhaxuatban WHERE MaNXB=?";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setString(1, ma);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(v, "Xóa thành công!");
                loadData();
                reset();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Không thể xóa (có thể đang được sử dụng)!");
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
                    SELECT * FROM Nhaxuatban
                    WHERE MaNXB LIKE ? OR TenNXB LIKE ?
                """;

                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, "%" + key + "%");
                ps.setString(2, "%" + key + "%");

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    v.model.addRow(new Object[]{
                        rs.getString("MaNXB"),
                        rs.getString("TenNXB"),
                        rs.getString("DienthoaiNXB"),
                        rs.getString("EmaiNXB"),
                        rs.getString("DiachiNXB")
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
        fc.setSelectedFile(new java.io.File("nhaxuatban.csv"));

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

            // BOM UTF-8 để Excel đọc tiếng Việt
            bw.write("\uFEFF");

            // ===== GHI HEADER =====
            for (int i = 0; i < v.model.getColumnCount(); i++) {
                bw.write(v.model.getColumnName(i));
                if (i < v.model.getColumnCount() - 1) bw.write(",");
            }
            bw.newLine();

            // ===== GHI DATA =====
            for (int i = 0; i < v.model.getRowCount(); i++) {
                for (int j = 0; j < v.model.getColumnCount(); j++) {
                    Object val = v.model.getValueAt(i, j);
                    bw.write(val == null ? "" : val.toString());
                    if (j < v.model.getColumnCount() - 1) bw.write(",");
                }
                bw.newLine();
            }

            JOptionPane.showMessageDialog(v, "Xuất file thành công!");

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

    
   
    

