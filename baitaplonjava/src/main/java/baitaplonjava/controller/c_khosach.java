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
    private v_trangchu trangchu;
    private int k = -1; // dòng đang chọn
    // DB
    private final String url  = "jdbc:mysql://localhost:3306/baitaplon";
    private final String user = "root";
    private final String pass = "123456789";

    public c_khosach(v_khosach view, v_trangchu viewtrangchu) {
        this.v = view;
        this.trangchu = viewtrangchu;
        // Gắn sự kiện nút giống kiểu c_theloai, c_sach
        v.bt_them(e     -> new action_them().actionPerformed(e));
        v.bt_sua(e      -> new action_sua().actionPerformed(e));
        v.bt_xoa(e      -> new action_xoa().actionPerformed(e));
        v.bt_reset(e    -> new action_reset().actionPerformed(e));
        v.bt_back(e     -> quaylai());
        v.bt_timkiem(e  -> new action_timkiem().actionPerformed(e));
        v.bt_xuatfile(e -> new action_xuatfile().actionPerformed(e));

        // Click bảng
        v.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                k = v.table.getSelectedRow();
                hienThiLenForm();
            }
        });

        loadComboSach();
        loadData();
        resetForm();
    }

    // =============== KẾT NỐI DB ===============
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }

    // =============== LOAD DATA ===============
    private void loadData() {
        v.model.setRowCount(0);
        try (Connection c = getConnection();
             ResultSet rs = c.createStatement().executeQuery("SELECT * FROM Khosach ORDER BY MaK, MaS")) {
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

    // =============== HIỂN THỊ LÊN FORM ===============
    private void hienThiLenForm() {
        if (k < 0) return;

        v.txtMaK.setText(v.table.getValueAt(k, 0).toString());
        v.cbMaS.setSelectedItem(v.table.getValueAt(k, 1).toString());
        v.txtNhap.setText(v.table.getValueAt(k, 2).toString());
        v.txtXuat.setText(v.table.getValueAt(k, 3).toString());

        v.txtMaK.setEditable(false);
        v.cbMaS.setEnabled(false); // vì MaK + MaS là khóa -> không cho sửa
    }

    // =============== RESET FORM ===============
    private void resetForm() {
        v.txtMaK.setText("");
        v.txtNhap.setText("");
        v.txtXuat.setText("");
        v.txttimkiem.setText("");

        if (v.cbMaS.getItemCount() > 0)
            v.cbMaS.setSelectedIndex(0);

        v.txtMaK.setEditable(true);
        v.cbMaS.setEnabled(true);
        v.table.clearSelection();
        k = -1;
        v.txtMaK.requestFocus();
    }

    // =============== THÊM ===============
    class action_them implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {

            m_khosach k = v.get_khosach(); // đã validate trong view
            if (k == null) return;

            try (Connection c = getConnection()) {
                // kiểm tra trùng MaK + MaS
                String checkSql = "SELECT * FROM Khosach WHERE MaK=? AND MaS=?";
                try (PreparedStatement psCheck = c.prepareStatement(checkSql)) {
                    psCheck.setString(1, k.getMaK());
                    psCheck.setString(2, k.getMaS());
                    if (psCheck.executeQuery().next()) {
                        JOptionPane.showMessageDialog(v, "Kho + Mã sách này đã tồn tại trong CSDL!");
                        return;
                    }
                }

                String sql = "INSERT INTO Khosach(MaK, MaS, SoluongN, SoluongX, SoluongT) VALUES (?,?,?,?,?)";
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setString(1, k.getMaK());
                    ps.setString(2, k.getMaS());
                    ps.setInt(3, k.getSoluongN());
                    ps.setInt(4, k.getSoluongX());
                    ps.setInt(5, k.getSoluongT());
                    ps.executeUpdate();
                }

                JOptionPane.showMessageDialog(v, "Thêm kho sách thành công!");
                loadData();
                resetForm();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(v, "Lỗi thêm kho: " + ex.getMessage());
            }
        }
    }

    // =============== SỬA ===============
    class action_sua implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {

            if (k == -1) {
                JOptionPane.showMessageDialog(v, "Vui lòng chọn một dòng để sửa!");
                return;
            }

            m_khosach ks = v.get_khosach();
            if (ks == null) return; // dữ liệu form không hợp lệ

            String sql = "UPDATE Khosach SET SoluongN=?, SoluongX=?, SoluongT=? " +
                         "WHERE MaK=? AND MaS=?";

            try (Connection c = getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, ks.getSoluongN());
                ps.setInt(2, ks.getSoluongX());
                ps.setInt(3, ks.getSoluongT());
                ps.setString(4, ks.getMaK());
                ps.setString(5, ks.getMaS());
                int aff = ps.executeUpdate();
                if (aff > 0) {
                    JOptionPane.showMessageDialog(v, "Sửa thành công!");
                    loadData();
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(v, "Không tìm thấy dòng để sửa (MaK/MaS không khớp)!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(v, "Lỗi sửa kho: " + ex.getMessage());
            }
        }
    }

    // =============== XÓA ===============
    class action_xoa implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {

            if (k == -1) {
                JOptionPane.showMessageDialog(v, "Vui lòng chọn dòng cần xóa!");
                return;
            }
            int cf = JOptionPane.showConfirmDialog(v,
                    "Bạn chắc chắn muốn xóa?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (cf != JOptionPane.YES_OPTION) return;

            String mak = v.txtMaK.getText().trim();
            String mas = v.cbMaS.getSelectedItem().toString();

            try (Connection c = getConnection();
                 PreparedStatement ps = c.prepareStatement(
                         "DELETE FROM Khosach WHERE MaK=? AND MaS=?")) {

                ps.setString(1, mak);
                ps.setString(2, mas);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(v, "Đã xóa!");
                loadData();
                resetForm();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(v, "Không thể xóa: " + ex.getMessage());
            }
        }
    }

    // =============== RESET (NÚT RESET) ===============
    class action_reset implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            resetForm();
            loadData();
            loadComboSach();
        }
    }

    // =============== TÌM KIẾM ===============
    class action_timkiem implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {

            String kw = v.txttimkiem.getText().trim();
            if (kw.isEmpty()) {
                loadData();
                resetForm();
                return;
            }

            v.model.setRowCount(0);
            String sql = "SELECT * FROM Khosach WHERE MaK LIKE ? OR MaS LIKE ?";

            try (Connection c = getConnection();
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

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(v, "Lỗi tìm kiếm: " + ex.getMessage());
            }
        }
    }

    // =============== LOAD COMBOBOX MÃ SÁCH ===============
    private void loadComboSach() {
        try (Connection conn = getConnection()) {
            v.cbMaS.removeAllItems();
            ResultSet rsS = conn.createStatement().executeQuery("SELECT MaS FROM Sach");
            while (rsS.next())v.cbMaS.addItem(rsS.getString("MaS"));
            //ResultSet rsS = conn.createStatement().executeQuery("SELECT MaS,TenS FROM Sach");
           // while (rsS.next()) v.cbMaS.addItem(rsS.getString("MaS") + " - " + rsS.getString("TenS")); lấy thêm tens
        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi load combobox: " + e.getMessage());
        }
    }

    // =============== XUẤT FILE CSV ===============
    class action_xuatfile implements java.awt.event.ActionListener {
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {

        // 1. Không có dữ liệu thì không xuất
        if (v.model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(v, "Không có dữ liệu để xuất!");
            return;
        }

        // 2. Chọn nơi lưu file
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn nơi lưu file kho sách");
        chooser.setSelectedFile(new java.io.File("khosach.csv"));

        int choose = chooser.showSaveDialog(v);
        if (choose != JFileChooser.APPROVE_OPTION) return;

        java.io.File file = chooser.getSelectedFile();

        try (
            // 3. Ghi UTF-8 + BOM 
            java.io.OutputStreamWriter osw =
                new java.io.OutputStreamWriter(
                    new java.io.FileOutputStream(file),
                    java.nio.charset.StandardCharsets.UTF_8
                );
            java.io.BufferedWriter bw = new java.io.BufferedWriter(osw)
        ) {
            // BOM UTF-8 để Excel đọc đúng tiếng Việt
            bw.write("\uFEFF");

            // 4. Ghi HEADER
            bw.write("MaKho;MaSach;Nhap;Xuat;Ton");
            bw.newLine();

            // 5. Ghi DATA từng dòng từ JTable
            for (int i = 0; i < v.table.getRowCount(); i++) {
                String maKho = v.table.getValueAt(i, 0).toString();
                String maSach = v.table.getValueAt(i, 1).toString();
                String nhap   = v.table.getValueAt(i, 2).toString();
                String xuat   = v.table.getValueAt(i, 3).toString();
                Object tonObj = v.table.getValueAt(i, 4);
                String ton    = (tonObj != null) ? tonObj.toString() : "";

                String line = maKho + ";" + maSach + ";" + nhap + ";" + xuat + ";" + ton;
                bw.write(line);
                bw.newLine();
            }

            JOptionPane.showMessageDialog(v, "Xuất file kho sách thành công!");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(v, "Lỗi xuất file kho sách: " + ex.getMessage());
        }
    }
}

    // =============== QUAY LẠI TRANG CHỦ ===============
    private void quaylai() {
        v.dispose();
        if (trangchu != null) trangchu.setVisible(true);
    }
}
