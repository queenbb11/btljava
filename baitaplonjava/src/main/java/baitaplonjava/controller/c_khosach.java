package baitaplonjava.controller;

import baitaplonjava.model.m_khosach;
import baitaplonjava.view.v_khosach;
import baitaplonjava.view.v_trangchu;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class c_khosach {

    private v_khosach v;
    private v_trangchu home;
    private int selectedRow = -1;
    private boolean daLuu = true; // cờ kiểm tra đã lưu hay chưa

    private final String url = "jdbc:mysql://localhost:3306/baitaplon";
    private final String user = "root";
    private final String pass = "1234567890";

    public c_khosach(v_khosach view, JFrame trangchu) {
        this.v = view;
        this.home = (v_trangchu) trangchu;

        // Gắn sự kiện nút
        v.bt_them(e -> handleThem());
        v.bt_luu(e -> handleLuu());
        v.bt_sua(e -> handleSua());
        v.bt_xoa(e -> handleXoa());
        v.bt_back(e -> handleBack());
        v.bt_timkiem(e -> handleTimKiem());
        v.bt_xuatfile(e -> handleXuatFile());
        // Click bảng
        v.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = v.table.getSelectedRow();
                if (selectedRow != -1) {
                    v.txtMaK.setText(v.table.getValueAt(selectedRow, 0).toString());
                    v.cbMaS.setSelectedItem(v.table.getValueAt(selectedRow, 1).toString());
                    v.txtNhap.setText(v.table.getValueAt(selectedRow, 2).toString());
                    v.txtXuat.setText(v.table.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        loadComboSach();
        loadData();
    }
    // ===== Thêm vào bảng tạm =====
    private void handleThem() {
        m_khosach k = v.get_khosach();
        if (k == null) return;
        // kiểm tra trùng MaK + MaS
        for (int i = 0; i < v.model.getRowCount(); i++) {
            String maK = v.model.getValueAt(i, 0).toString().trim();
            String maS = v.model.getValueAt(i, 1).toString().trim();
            if (maK.equalsIgnoreCase(k.getMaK().trim())
                    && maS.equalsIgnoreCase(k.getMaS().trim())) {
                JOptionPane.showMessageDialog(v, "Kho + Mã sách này đã tồn tại trong bảng!");
                return;
            }
        }

        v.model.addRow(new Object[]{
                k.getMaK(), k.getMaS(),
                k.getSoluongN(), k.getSoluongX(), k.getSoluongT()
        });
        daLuu = false;
    }

    // ===== Lưu toàn bộ bảng xuống DB =====
    private void handleLuu() {
        if (v.model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(v, "Bảng trống, không có gì để lưu!");
            return;
        }

        String sql = "INSERT INTO Khosach(MaK, MaS, SoluongN, SoluongX, SoluongT) VALUES (?,?,?,?,?)";

        try (Connection c = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = c.prepareStatement(sql)) {

            int ok = 0, loi = 0;
            for (int i = 0; i < v.model.getRowCount(); i++) {
                try {
                    String mak = v.model.getValueAt(i, 0).toString();
                    String mas = v.model.getValueAt(i, 1).toString();
                    int n  = Integer.parseInt(v.model.getValueAt(i, 2).toString());
                    int x  = Integer.parseInt(v.model.getValueAt(i, 3).toString());
                    int ton = Integer.parseInt(v.model.getValueAt(i, 4).toString());

                    ps.setString(1, mak);
                    ps.setString(2, mas);
                    ps.setInt(3, n);
                    ps.setInt(4, x);
                    ps.setInt(5, ton);
                    ps.executeUpdate();
                    ok++;
                } catch (Exception ex) {
                    loi++;
                }
            }

            JOptionPane.showMessageDialog(v,
                    "Lưu kho xong!\nThành công: " + ok + "\nLỗi/Trùng: " + loi);
            loadData();
            daLuu = true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi lưu kho: " + e.getMessage());
        }
    }

    // ===== Sửa một dòng =====
    private void handleSua() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(v, "Vui lòng chọn một dòng để sửa!");
            return;
        }

        m_khosach k = v.get_khosach();
        if (k == null) return;

        String sql = "UPDATE Khosach SET SoluongN=?, SoluongX=?, SoluongT=? WHERE MaK=? AND MaS=?";

        try (Connection c = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, k.getSoluongN());
            ps.setInt(2, k.getSoluongX());
            ps.setInt(3, k.getSoluongT());
            ps.setString(4, k.getMaK());
            ps.setString(5, k.getMaS());

            int aff = ps.executeUpdate();
            if (aff > 0) {
                v.model.setValueAt(k.getSoluongN(), selectedRow, 2);
                v.model.setValueAt(k.getSoluongX(), selectedRow, 3);
                v.model.setValueAt(k.getSoluongT(), selectedRow, 4);
                JOptionPane.showMessageDialog(v, "Sửa thành công!");
                daLuu = false;
            } else {
                JOptionPane.showMessageDialog(v, "Không tìm thấy dòng để sửa (MaK/MaS không khớp)!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi sửa: " + e.getMessage());
        }
    }

    // ===== Xóa 1 dòng =====
    private void handleXoa() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(v, "Vui lòng chọn dòng cần xóa!");
            return;
        }

        int cf = JOptionPane.showConfirmDialog(v, "Bạn chắc chắn muốn xóa?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (cf != JOptionPane.YES_OPTION) return;

        try (Connection c = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = c.prepareStatement("DELETE FROM Khosach WHERE MaK=? AND MaS=?")) {

            ps.setString(1, v.txtMaK.getText().trim());
            ps.setString(2, v.cbMaS.getSelectedItem().toString());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(v, "Đã xóa!");
            loadData();
            selectedRow = -1;
            daLuu = false;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Không thể xóa: " + e.getMessage());
        }
    }

    // ===== Tìm kiếm =====
    private void handleTimKiem() {
        String kw = v.txttimkiem.getText().trim();
        if (kw.isEmpty()) {
            loadData();
            return;
        }

        v.model.setRowCount(0);
        String sql = "SELECT * FROM Khosach WHERE MaK LIKE ? OR MaS LIKE ?";

        try (Connection c = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = c.prepareStatement(sql)) {

            String p = "%" + kw + "%";
            ps.setString(1, p);
            ps.setString(2, p);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                v.model.addRow(new Object[]{
                        rs.getString("MaK"),
                        rs.getString("MaS"),
                        rs.getInt("SoluongN"),
                        rs.getInt("SoluongX"),
                        rs.getInt("SoluongT")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi tìm kiếm: " + e.getMessage());
        }
    }

    // ===== Load toàn bộ dữ liệu kho =====
    private void loadData() {
        v.model.setRowCount(0);
        try (Connection c = DriverManager.getConnection(url, user, pass);
             ResultSet rs = c.createStatement().executeQuery("SELECT * FROM Khosach")) {

            while (rs.next()) {
                v.model.addRow(new Object[]{
                        rs.getString("MaK"),
                        rs.getString("MaS"),
                        rs.getInt("SoluongN"),
                        rs.getInt("SoluongX"),
                        rs.getInt("SoluongT")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi tải dữ liệu kho: " + e.getMessage());
        }
    }

    // ===== Load combobox mã sách =====
    private void loadComboSach() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            v.cbMaS.removeAllItems();
            
            ResultSet rsS = conn.createStatement().executeQuery("SELECT MaS FROM Sach");
            while (rsS.next()) v.cbMaS.addItem(rsS.getString("MaS"));
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi load combobox: " + e.getMessage());
        }
    }

    // ===== Xuất file CSV =====
    private void handleXuatFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn nơi lưu file kho sách");
        chooser.setSelectedFile(new java.io.File("khosach.csv"));

        if (chooser.showSaveDialog(v) != JFileChooser.APPROVE_OPTION) return;

        java.io.File file = chooser.getSelectedFile();
        String path = file.getAbsolutePath();
        if (!path.toLowerCase().endsWith(".csv")) {
            path += ".csv";
        }
        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(path)) {
            // BOM UTF-8
            fos.write(0xEF);
            fos.write(0xBB);
            fos.write(0xBF);
            try (java.io.OutputStreamWriter osw =
                         new java.io.OutputStreamWriter(fos, java.nio.charset.StandardCharsets.UTF_8);
                 java.io.PrintWriter pw = new java.io.PrintWriter(osw)) {
                // Tiêu đề
                pw.println("MaKho;MaSach;Nhap;Xuat;Ton");
                // Dữ liệu
                for (int i = 0; i < v.table.getRowCount(); i++) {
                    StringBuilder row = new StringBuilder();
                    for (int j = 0; j < v.table.getColumnCount(); j++) {
                        Object val = v.table.getValueAt(i, j);
                        row.append(val != null ? val.toString() : "");
                        if (j < v.table.getColumnCount() - 1) row.append(";");
                    }
                    pw.println(row.toString());
                }
            }

            JOptionPane.showMessageDialog(v, "Xuất file kho sách thành công!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi xuất file: " + e.getMessage());
        }
    }
    // ===== Quay lại trang chủ =====
    private void handleBack() {
        if (!daLuu && v.model.getRowCount() > 0) {
            int cf = JOptionPane.showConfirmDialog(
                    v,
                    "Dữ liệu có thể chưa được lưu vào CSDL.\nBạn có muốn quay lại không?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (cf != JOptionPane.YES_OPTION) return;
        }
        v.dispose();
        if (home != null) home.setVisible(true);
    }
}
