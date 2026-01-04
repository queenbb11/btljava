package baitaplonjava.controller;

import baitaplonjava.model.m_sach;
import baitaplonjava.view.v_sach;

import baitaplonjava.view.v_trangchu;

import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import javax.swing.*;

public class c_sach {
    private v_sach v;
    private v_trangchu viewtrangchu;
    private int selectedRow = -1;

    private final String url  = "jdbc:mysql://localhost:3306/baitaplon";
    private final String user = "root";
    private final String pass = "123456789";

    public c_sach(v_sach view, JFrame trangchu) {
        this.v = view;
        this.viewtrangchu = (v_trangchu) trangchu;

        // Gắn sự kiện nút
        this.v.bt_them_action_listener(e   -> handleThem());
        this.v.bt_sua_action_listener(e    -> handleSua());
        this.v.bt_xoa_action_listener(e    -> handleXoa());
        this.v.bt_reset_action_listener(e  -> handleReset());     
        this.v.bt_timkiem_action_listener(e-> handleTimKiem());
        this.v.bt_xuatfile_action_listener(e-> handleXuatFile());
        this.v.bt_quaylai_action_listener(e-> handleQuayLai());

        // click bảng
        this.v.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = v.table.getSelectedRow();
                if (selectedRow != -1) {
                    v.txtMaS.setText(v.table.getValueAt(selectedRow, 0).toString());
                    v.txtTenS.setText(v.table.getValueAt(selectedRow, 1).toString());
                    v.cbMaTL.setSelectedItem(v.table.getValueAt(selectedRow, 2).toString());
                    v.cbMaNXB.setSelectedItem(v.table.getValueAt(selectedRow, 3).toString());
                    v.cbMaTG.setSelectedItem(v.table.getValueAt(selectedRow, 4).toString());
                    v.txtNamXB.setText(v.table.getValueAt(selectedRow, 5).toString());
                    v.txtMoTa.setText(v.table.getValueAt(selectedRow, 6).toString());
                    v.txtMaS.setEditable(false);
                }
            }
        });

        loadComboBoxData();
        loadData();
        resetForm();
    }

    // ===== RESET FORM =====
    private void resetForm() {
        v.txtMaS.setText("");
        v.txtTenS.setText("");
        v.txtNamXB.setText("");
        v.txtMoTa.setText("");
        v.txtMaS.setEditable(true);
        v.table.clearSelection();
        v.txttimkiem.setText("");
        selectedRow = -1;
        v.txtMaS.requestFocus();
    }

    // ===== KẾT NỐI DB =====
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    // ===== THÊM: thêm trực tiếp vào DB =====
    private void handleThem() {
        m_sach s = v.get_sach();
        if (s == null) return;

        String ma  = s.getMaS().trim();

        try (Connection conn = getConnection()) {
            // check trùng mã
            String checkSql = "SELECT MaS FROM Sach WHERE MaS = ?";
            try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                psCheck.setString(1, ma);
                if (psCheck.executeQuery().next()) {
                    JOptionPane.showMessageDialog(v, "Mã sách đã tồn tại trong CSDL!");
                    return;
                }
            }

            String sql = "INSERT INTO Sach (MaS, TenS, MaTL, MaNXB, MaTG, Namxuatban, Mota) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, s.getMaS());
                ps.setString(2, s.getTenS());
                ps.setString(3, s.getMaTL());
                ps.setString(4, s.getMaNXB());
                ps.setString(5, s.getMaTG());
                ps.setInt   (6, s.getNamxuatban());
                ps.setString(7, s.getMota());
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(v, "Thêm sách thành công!");
            loadData();
            resetForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi thêm: " + e.getMessage());
        }
    }

    // ===== SỬA =====
    private void handleSua() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(v, "Chọn sách cần sửa!");
            return;
        }

        m_sach s = v.get_sach();
        if (s == null) {
            JOptionPane.showMessageDialog(v, "Dữ liệu không hợp lệ, kiểm tra lại form!");
            return;
        }

        String sql = "UPDATE Sach SET TenS = ?, MaTL = ?, MaNXB = ?, MaTG = ?, " +
                     "Namxuatban = ?, Mota = ? WHERE MaS = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getTenS());
            ps.setString(2, s.getMaTL());
            ps.setString(3, s.getMaNXB());
            ps.setString(4, s.getMaTG());
            ps.setInt   (5, s.getNamxuatban());
            ps.setString(6, s.getMota());
            ps.setString(7, s.getMaS());

            int affected = ps.executeUpdate();

            if (affected > 0) {
                // Cập nhật lại ngay trên JTable
                v.model.setValueAt(s.getTenS(),       selectedRow, 1);
                v.model.setValueAt(s.getMaTL(),       selectedRow, 2);
                v.model.setValueAt(s.getMaNXB(),      selectedRow, 3);
                v.model.setValueAt(s.getMaTG(),       selectedRow, 4);
                v.model.setValueAt(s.getNamxuatban(), selectedRow, 5);
                v.model.setValueAt(s.getMota(),       selectedRow, 6);

                JOptionPane.showMessageDialog(v, "Sửa thành công!");
            } else {
                JOptionPane.showMessageDialog(v, "Không tìm thấy sách để sửa (MaS không khớp)!");
            }

            resetForm();
            loadData();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi sửa: " + e.getMessage());
        }
    }

    // ===== XÓA =====
    private void handleXoa() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(v, "Chọn sách cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(v, "Xóa sách này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String ma = v.txtMaS.getText().trim();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Sach WHERE MaS=?")) {
            ps.setString(1, ma);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(v, "Đã xóa!");
            loadData();
            resetForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Không thể xóa! (Có thể sách đang được dùng ở bảng khác)");
        }
    }

    // ===== RESET (nút Reset) =====
    private void handleReset() {
        resetForm();
        loadData();
    }

    // ===== TÌM KIẾM =====
    private void handleTimKiem() {
        String kw = v.txttimkiem.getText().trim();

        if (kw.isEmpty()) {
            loadData();
            resetForm();
            return;
        }

        v.model.setRowCount(0);
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM Sach WHERE TenS LIKE ? OR MaS LIKE ?")) {
            ps.setString(1, "%" + kw + "%");
            ps.setString(2, "%" + kw + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                v.model.addRow(new Object[]{
                        rs.getString("MaS"),
                        rs.getString("TenS"),
                        rs.getString("MaTL"),
                        rs.getString("MaNXB"),
                        rs.getString("MaTG"),
                        rs.getInt("Namxuatban"),
                        rs.getString("Mota")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi tìm kiếm: " + e.getMessage());
        }
    }

    // ===== XUẤT FILE CSV =====
    private void handleXuatFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(v) != JFileChooser.APPROVE_OPTION) return;
        File file = fileChooser.getSelectedFile();
        String path = file.getAbsolutePath();
        if (!path.toLowerCase().endsWith(".csv")) path += ".csv";

        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(0xEF); fos.write(0xBB); fos.write(0xBF); // BOM UTF-8
            try (OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                 PrintWriter pw = new PrintWriter(osw)) {

                pw.println("Mã Sách;Tên Sách;Mã TL;Mã NXB;Mã TG;Năm XB;Mô Tả");

                for (int i = 0; i < v.table.getRowCount(); i++) {
                    StringBuilder row = new StringBuilder();
                    for (int j = 0; j < v.table.getColumnCount(); j++) {
                        Object val = v.table.getValueAt(i, j);
                        row.append(val != null ? val.toString() : "");
                        if (j < v.table.getColumnCount() - 1) row.append(";");
                    }
                    pw.println(row.toString());
                }
                pw.flush();
            }
            JOptionPane.showMessageDialog(v, "Xuất file thành công!");
            resetForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi xuất file: " + e.getMessage());
        }
    }

    // ===== LOAD DATA =====
    public void loadData() {
        v.model.setRowCount(0);
        try (Connection conn = getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Sach")) {
            while (rs.next()) {
                v.model.addRow(new Object[]{
                        rs.getString("MaS"),
                        rs.getString("TenS"),
                        rs.getString("MaTL"),
                        rs.getString("MaNXB"),
                        rs.getString("MaTG"),
                        rs.getInt("Namxuatban"),
                        rs.getString("Mota")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    // ===== Load combobox =====
    private void loadComboBoxData() {
        try (Connection conn = getConnection()) {
            v.cbMaTL.removeAllItems();
            v.cbMaTG.removeAllItems();
            v.cbMaNXB.removeAllItems();

            ResultSet rsTL = conn.createStatement().executeQuery("SELECT MaTL FROM Theloai");
            while (rsTL.next()) v.cbMaTL.addItem(rsTL.getString("MaTL"));

            ResultSet rsTG = conn.createStatement().executeQuery("SELECT MaTG FROM Tacgia");
            while (rsTG.next()) v.cbMaTG.addItem(rsTG.getString("MaTG"));

            ResultSet rsNXB = conn.createStatement().executeQuery("SELECT MaNXB FROM Nhaxuatban");
            while (rsNXB.next()) v.cbMaNXB.addItem(rsNXB.getString("MaNXB"));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi load combobox: " + e.getMessage());
        }
    }

    // ===== QUAY LẠI =====
    private void handleQuayLai() {
        v.dispose();
        if (viewtrangchu != null) viewtrangchu.setVisible(true);
    }
}
