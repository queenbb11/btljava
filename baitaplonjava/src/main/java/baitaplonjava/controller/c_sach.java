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

    // cờ đang tìm kiếm (bảng đang lọc)
    private boolean isSearching = false;

    private final String url = "jdbc:mysql://localhost:3306/baitaplon";
    private final String user = "root";
    private final String pass = "123456789";

    public c_sach(v_sach view, JFrame trangchu) {
        this.v = view;
        this.viewtrangchu = (v_trangchu) trangchu;

        // nút
        this.v.bt_them_action_listener(e -> handleThem());
        this.v.bt_luu_action_listener(e -> handleLuu());
        this.v.bt_sua_action_listener(e -> handleSua());
        this.v.bt_xoa_action_listener(e -> handleXoa());
        this.v.bt_timkiem_action_listener(e -> handleTimKiem());
        this.v.bt_xuatfile_action_listener(e -> handleXuatFile());
        this.v.bt_quaylai_action_listener(e -> handleQuayLai());

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
                    v.txtTinhTrang.setText(v.table.getValueAt(selectedRow, 6).toString());
                    v.txtMoTa.setText(v.table.getValueAt(selectedRow, 7).toString());
                    v.txtMaS.setEditable(false); // chọn dòng -> khóa mã
                }
            }
        });

        loadComboBoxData();
        loadData();
        resetForm();
    }

    // ===== reset form =====
    private void resetForm() {
        v.txtMaS.setText("");
        v.txtTenS.setText("");
        v.txtNamXB.setText("");
        v.txtTinhTrang.setText("");
        v.txtMoTa.setText("");

        v.txtMaS.setEditable(true);
        v.table.clearSelection();
        selectedRow = -1;

        v.txtMaS.requestFocus();
    }

    // ===== nếu đang search thì trả bảng về full trước khi thao tác =====
    private void ensureFullTable() {
        if (isSearching) {
            loadData();
            isSearching = false;
            resetForm();
        }
    }

    // ===== THÊM: thêm vào bảng tạm (JTable) =====
    private void handleThem() {
        ensureFullTable();

        m_sach s = v.get_sach();
        if (s == null) return;

        // check trùng MaS trên bảng
        for (int i = 0; i < v.model.getRowCount(); i++) {
            String maDangCo = v.model.getValueAt(i, 0).toString().trim();
            if (maDangCo.equalsIgnoreCase(s.getMaS().trim())) {
                JOptionPane.showMessageDialog(v, "Mã sách đã có trong bảng!");
                return;
            }
        }

        v.model.addRow(new Object[]{
                s.getMaS(), s.getTenS(), s.getMaTL(), s.getMaNXB(),
                s.getMaTG(), s.getNamxuatban(), s.getTinhtrang(), s.getMota()
        });

        JOptionPane.showMessageDialog(v, "Đã thêm vào danh sách tạm!");
        resetForm();
    }

    // ===== LƯU: lưu toàn bộ bảng xuống DB (trùng thì bỏ qua) =====
    private void handleLuu() {
        ensureFullTable();

        if (v.model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(v, "Bảng đang trống, không có gì để lưu!");
            return;
        }

        String sql = "INSERT INTO Sach (MaS, TenS, MaTL, MaNXB, MaTG, Namxuatban, Tinhtrang, Mota) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int demThanhCong = 0;
            int demLoi = 0;

            for (int i = 0; i < v.model.getRowCount(); i++) {
                String ma  = v.model.getValueAt(i, 0).toString().trim();
                String ten = v.model.getValueAt(i, 1).toString().trim();
                String maTL  = v.model.getValueAt(i, 2).toString().trim();
                String maNXB = v.model.getValueAt(i, 3).toString().trim();
                String maTG  = v.model.getValueAt(i, 4).toString().trim();

                int namXB;
                try {
                    namXB = Integer.parseInt(v.model.getValueAt(i, 5).toString().trim());
                } catch (Exception ex) {
                    demLoi++;
                    continue;
                }

                String tinhtrang = v.model.getValueAt(i, 6) != null ? v.model.getValueAt(i, 6).toString() : "";
                String mota      = v.model.getValueAt(i, 7) != null ? v.model.getValueAt(i, 7).toString() : "";

                try {
                    ps.setString(1, ma);
                    ps.setString(2, ten);
                    ps.setString(3, maTL);
                    ps.setString(4, maNXB);
                    ps.setString(5, maTG);
                    ps.setInt(6, namXB);
                    ps.setString(7, tinhtrang);
                    ps.setString(8, mota);

                    ps.executeUpdate();
                    demThanhCong++;
                } catch (SQLException ex) {
                    demLoi++;
                }
            }

            JOptionPane.showMessageDialog(v,
                    "Lưu xong!\nThành công: " + demThanhCong + "\nKhông lưu (trùng/lỗi): " + demLoi);

            loadData();
            isSearching = false;
            resetForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi lưu: " + e.getMessage());
        }
    }

    // ===== SỬA =====
    private void handleSua() {
        ensureFullTable();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(v, "Chọn sách cần sửa!");
            return;
        }

        m_sach s = v.get_sach();
        if (s == null) return;

        String sql = "UPDATE Sach SET TenS=?, MaTL=?, MaNXB=?, MaTG=?, Namxuatban=?, Tinhtrang=?, Mota=? WHERE MaS=?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getTenS());
            ps.setString(2, s.getMaTL());
            ps.setString(3, s.getMaNXB());
            ps.setString(4, s.getMaTG());
            ps.setInt(5, s.getNamxuatban());
            ps.setString(6, s.getTinhtrang());
            ps.setString(7, s.getMota());
            ps.setString(8, s.getMaS());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(v, "Sửa thành công!");
            loadData();
            isSearching = false;
            resetForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi sửa: " + e.getMessage());
        }
    }

    // ===== XÓA =====
    private void handleXoa() {
        ensureFullTable();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(v, "Chọn sách cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(v, "Xóa sách này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Sach WHERE MaS=?")) {

            ps.setString(1, v.txtMaS.getText().trim());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(v, "Đã xóa!");
            loadData();
            isSearching = false;
            resetForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Không thể xóa! (Có thể sách đang được dùng ở bảng khác)");
        }
    }

    // ===== TÌM KIẾM =====
    private void handleTimKiem() {
        String kw = v.txttimkiem.getText().trim();

        // rỗng -> load all
        if (kw.isEmpty()) {
            loadData();
            isSearching = false;
            resetForm();
            return;
        }

        v.model.setRowCount(0);

        try (Connection conn = DriverManager.getConnection(url, user, pass);
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
                        rs.getString("Tinhtrang"),
                        rs.getString("Mota")
                });
            }

            isSearching = true;
            resetForm();

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

                pw.println("Mã Sách;Tên Sách;Mã TL;Mã NXB;Mã TG;Năm XB;Tình Trạng;Mô Tả");

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
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Sach")) {

            while (rs.next()) {
                v.model.addRow(new Object[]{
                        rs.getString("MaS"),
                        rs.getString("TenS"),
                        rs.getString("MaTL"),
                        rs.getString("MaNXB"),
                        rs.getString("MaTG"),
                        rs.getInt("Namxuatban"),
                        rs.getString("Tinhtrang"),
                        rs.getString("Mota")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    // ===== Load combobox =====
    private void loadComboBoxData() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
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
