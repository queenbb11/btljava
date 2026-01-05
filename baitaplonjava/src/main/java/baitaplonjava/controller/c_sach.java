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
    private v_trangchu trangchu;
    private int k = -1; // dòng đang chọn

    // Thông tin DB
    private final String url  = "jdbc:mysql://localhost:3306/baitaplon";
    private final String user = "root";
    private final String pass = "123456789";

    public c_sach(v_sach view, v_trangchu viewtrangchu) {
        this.v = view;
        this.trangchu = viewtrangchu;

        // Gắn sự kiện nút giống c_theloai
        v.bt_them_action_listener(new action_them());
        v.bt_sua_action_listener(new action_sua());
        v.bt_xoa_action_listener(new action_xoa());
        v.bt_reset_action_listener(new action_reset());
        v.bt_timkiem_action_listener(new action_timkiem());
        v.bt_xuatfile_action_listener(new action_xuatfile());
        v.bt_quaylai_action_listener(e -> quaylai());

        // Click bảng -> hiển thị lên form
        v.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                k = v.table.getSelectedRow();
                hienThiLenForm();
            }
        });

        loadComboBoxData();
        loadData();
        resetForm();
    }

    // =============== KẾT NỐI DB ===============
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }

    // =============== LOAD DATA ===============
    public void loadData() {
        v.model.setRowCount(0);
        try (Connection conn = getConnection()) {

            String sql = "SELECT * FROM Sach ORDER BY MaS";
            PreparedStatement ps = conn.prepareStatement(sql);
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

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(v, "Lỗi tải dữ liệu sách: " + e.getMessage());
        }
    }

    // =============== HIỂN THỊ LÊN FORM ===============
    private void hienThiLenForm() {
        if (k < 0) return;

        v.txtMaS.setText(v.table.getValueAt(k, 0).toString());
        v.txtTenS.setText(v.table.getValueAt(k, 1).toString());
        v.cbMaTL.setSelectedItem(v.table.getValueAt(k, 2).toString());
        v.cbMaNXB.setSelectedItem(v.table.getValueAt(k, 3).toString());
        v.cbMaTG.setSelectedItem(v.table.getValueAt(k, 4).toString());
        v.txtNamXB.setText(v.table.getValueAt(k, 5).toString());
        v.txtMoTa.setText(v.table.getValueAt(k, 6).toString());

        v.txtMaS.setEditable(false);
    }

    // =============== RESET FORM ===============
    private void resetForm() {
        v.txtMaS.setText("");
        v.txtTenS.setText("");
        v.txtNamXB.setText("");
        v.txtMoTa.setText("");
        v.txttimkiem.setText("");

        v.cbMaTL.setSelectedIndex(0);
        v.cbMaNXB.setSelectedIndex(0);
        v.cbMaTG.setSelectedIndex(0);

        v.txtMaS.setEditable(true);
        v.table.clearSelection();
        k = -1;
        v.txtMaS.requestFocus();
    }

    // =============== THÊM ===============
    class action_them implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            m_sach s = v.get_sach(); 
            if (s == null) return;

            String ma  = s.getMaS().trim();
            String ten = s.getTenS().trim();

            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(v, "Mã sách và Tên sách không được để trống!");
                return;
            }

            try (Connection conn = getConnection()) {

                // check trùng mã
                String checkSql = "SELECT MaS FROM Sach WHERE MaS = ?";
                PreparedStatement psCheck = conn.prepareStatement(checkSql);
                psCheck.setString(1, ma);
                if (psCheck.executeQuery().next()) {
                    JOptionPane.showMessageDialog(v, "Mã sách đã tồn tại trong CSDL!");
                    return;
                }

                String sql = "INSERT INTO Sach (MaS, TenS, MaTL, MaNXB, MaTG, Namxuatban, Mota) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, s.getMaS());
                ps.setString(2, s.getTenS());
                ps.setString(3, s.getMaTL());
                ps.setString(4, s.getMaNXB());
                ps.setString(5, s.getMaTG());
                ps.setInt   (6, s.getNamxuatban());
                ps.setString(7, s.getMota());
                ps.executeUpdate();

                JOptionPane.showMessageDialog(v, "Thêm sách thành công!");
                loadData();
                resetForm();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi thêm sách: " + ex.getMessage());
            }
        }
    }

    // =============== SỬA ===============
    class action_sua implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String ma = v.txtMaS.getText().trim();
            if (ma.isEmpty()) {
                JOptionPane.showMessageDialog(v, "Vui lòng chọn sách cần sửa!");
                return;
            }

            m_sach s = v.get_sach();  // đọc lại dữ liệu từ form
            if (s == null) {
                JOptionPane.showMessageDialog(v, "Dữ liệu không hợp lệ, kiểm tra lại form!");
                return;
            }

            try (Connection conn = getConnection()) {

                String sql = """
                    UPDATE Sach
                    SET TenS = ?, MaTL = ?, MaNXB = ?, MaTG = ?, Namxuatban = ?, Mota = ?
                    WHERE MaS = ?
                """;

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, s.getTenS());
                ps.setString(2, s.getMaTL());
                ps.setString(3, s.getMaNXB());
                ps.setString(4, s.getMaTG());
                ps.setInt   (5, s.getNamxuatban());
                ps.setString(6, s.getMota());
                ps.setString(7, s.getMaS());

                int aff = ps.executeUpdate();
                if (aff > 0) {
                    JOptionPane.showMessageDialog(v, "Sửa sách thành công!");
                    loadData();
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(v, "Không tìm thấy sách để sửa (MaS không khớp)!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi sửa sách: " + ex.getMessage());
            }
        }
    }

    // =============== XÓA ===============
    class action_xoa implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String ma = v.txtMaS.getText().trim();
            if (ma.isEmpty()) {
                JOptionPane.showMessageDialog(v, "Vui lòng chọn sách cần xóa!");
                return;
            }

            int ch = JOptionPane.showConfirmDialog(v,
                    "Bạn có chắc muốn xóa sách này không?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (ch != JOptionPane.YES_OPTION) return;

            try (Connection conn = getConnection()) {

                String sql = "DELETE FROM Sach WHERE MaS = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, ma);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(v, "Xóa sách thành công!");
                loadData();
                resetForm();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v,
                        "Không thể xóa sách (có thể sách đang được dùng ở bảng khác)!");
            }
        }
    }

    // =============== RESET (NÚT RESET) ===============
    class action_reset implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetForm();
            loadData();
            loadComboBoxData();
        }
    }

    // =============== TÌM KIẾM ===============
    class action_timkiem implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String key = v.txttimkiem.getText().trim();

            if (key.isEmpty()) {
                loadData();
                resetForm();
                return;
            }

            v.model.setRowCount(0);
            try (Connection conn = getConnection()) {
                String sql = """
                    SELECT * FROM Sach
                    WHERE MaS LIKE ? OR TenS LIKE ?
                """;
                PreparedStatement ps = conn.prepareStatement(sql);
                String p = "%" + key + "%";
                ps.setString(1, p);
                ps.setString(2, p);

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

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi tìm kiếm sách!");
            }
        }
    }

    // =============== XUẤT FILE CSV ===============
    class action_xuatfile implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        if (v.model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(v, "Không có dữ liệu để xuất!");
            return;
        }

        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn nơi lưu file");
        fc.setSelectedFile(new java.io.File("sach.csv"));

        int choose = fc.showSaveDialog(v);
        if (choose != JFileChooser.APPROVE_OPTION) return;

        java.io.File file = fc.getSelectedFile();

        try (
            // Ghi UTF-8 + BOM 
            java.io.OutputStreamWriter osw =
                new java.io.OutputStreamWriter(
                    new java.io.FileOutputStream(file),
                    java.nio.charset.StandardCharsets.UTF_8
                );
            java.io.BufferedWriter bw = new java.io.BufferedWriter(osw)
        ) {
            // Ghi BOM UTF-8
            bw.write("\uFEFF");

            // HEADER (tùy đang có mấy cột trong table Sách)
            bw.write("MaS;TenS;MaTL;MaNXB;MaTG;Namxuatban;Mota");
            bw.newLine();

            // DATA
            for (int i = 0; i < v.table.getRowCount(); i++) {
                String maS   = v.table.getValueAt(i, 0).toString();
                String tenS  = v.table.getValueAt(i, 1).toString();
                String maTL  = v.table.getValueAt(i, 2).toString();
                String maNXB = v.table.getValueAt(i, 3).toString();
                String maTG  = v.table.getValueAt(i, 4).toString();
                String namXB = v.table.getValueAt(i, 5).toString();
                String mota  = v.table.getValueAt(i, 6) != null
                               ? v.table.getValueAt(i, 6).toString()
                               : "";

                // Nối lại thành 1 dòng
                String line = maS + ";" + tenS + ";" + maTL + ";" +
                              maNXB + ";" + maTG + ";" + namXB + ";" + mota;

                bw.write(line);
                bw.newLine();
            }
            JOptionPane.showMessageDialog(v, "Xuất file sách thành công!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(v, "Lỗi xuất file sách: " + ex.getMessage());
        }
    }
}
    // =============== LOAD DỮ LIỆU CHO COMBOBOX ===============
    private void loadComboBoxData() {
        try (Connection conn = getConnection()) {
            v.cbMaTL.removeAllItems();
            v.cbMaTG.removeAllItems();
            v.cbMaNXB.removeAllItems();
            ResultSet rsTL = conn.createStatement().executeQuery("SELECT MaTL FROM Theloai");
            while (rsTL.next()) v.cbMaTL.addItem(rsTL.getString("MaTL"));
             //vi dụ muón lấy thêm TenTL
            // ResultSet rsTL = conn.createStatement().executeQuery("SELECT MaTL, TenTL FROM Theloai");
            // while (rsTL.next())  v.cbMaTL.addItem(rsTL.getString("MaTL") + " - " + rsTL.getString("TenTL"));
            ResultSet rsTG = conn.createStatement().executeQuery("SELECT MaTG FROM Tacgia");
            while (rsTG.next()) v.cbMaTG.addItem(rsTG.getString("MaTG"));
            ResultSet rsNXB = conn.createStatement().executeQuery("SELECT MaNXB FROM Nhaxuatban");
            while (rsNXB.next()) v.cbMaNXB.addItem(rsNXB.getString("MaNXB"));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(v, "Lỗi load dữ liệu combobox Sách: " + e.getMessage());
        }
    }
    // =============== QUAY LẠI ===============
    private void quaylai() {
        v.dispose();
        if (trangchu != null) {
            trangchu.setVisible(true);
        }
    }
}
