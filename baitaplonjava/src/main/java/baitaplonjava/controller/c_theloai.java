package baitaplonjava.controller;

import baitaplonjava.model.m_theloai;
import baitaplonjava.view.v_theloai;
import baitaplonjava.view.v_trangchu;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class c_theloai {
    private v_theloai v;
    private v_trangchu trangchu;
    private int k = -1; // dòng đang chọn

    // DB
    private final String url  = "jdbc:mysql://localhost:3306/baitaplon";
    private final String user = "root";
    private final String pass = "123456789";

    public c_theloai(v_theloai view, v_trangchu viewtrangchu) {
        this.v = view;
        this.trangchu = viewtrangchu;

        // Gắn sự kiện nút
        v.bt_them_action_listenner(new action_them());
        v.bt_sua_action_listenner(new action_sua());
        v.bt_xoa_action_listenner(new action_xoa());
        v.bt_reset_action_listenner(new action_reset());   
        v.bt_timkiem_action_listenner(new action_timkiem());
        v.bt_xuatfile_action_listenner(new action_xuatfile());
        v.bt_quaylai_action_listenner(e -> quaylai());

        // Click bảng
        v.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                k = v.table.getSelectedRow();
                hienThiLenForm();
            }
        });

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

            String sql = "SELECT MaTL, TenTL FROM Theloai ORDER BY MaTL";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                v.model.addRow(new Object[]{
                        rs.getString("MaTL"),
                        rs.getString("TenTL")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(v, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    // =============== HIỂN THỊ LÊN FORM ===============
    private void hienThiLenForm() {
        if (k < 0) return;

        v.txtMaTL.setText(v.table.getValueAt(k, 0).toString());
        v.txtTenTL.setText(v.table.getValueAt(k, 1).toString());
        v.txtMaTL.setEditable(false);
    }

    // =============== RESET FORM ===============
    private void resetForm() {
        v.txtMaTL.setText("");
        v.txtTenTL.setText("");
        v.txtMaTL.setEditable(true);
        v.table.clearSelection();
        v.txttimkiem.setText("");
        k = -1;
        v.txtMaTL.requestFocus();
    }

    // =============== THÊM ===============
    class action_them implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            m_theloai tl = v.get_theloai();
            if (tl == null) return;

            String ma = tl.getMaTL().trim();
            String ten = tl.getTenTL().trim();

            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(v, "Mã và tên thể loại không được để trống!");
                return;
            }

            try (Connection conn = getConnection()) {

                // check trùng mã trong DB
                String check = "SELECT MaTL FROM Theloai WHERE MaTL = ?";
                PreparedStatement psCheck = conn.prepareStatement(check);
                psCheck.setString(1, ma);
                if (psCheck.executeQuery().next()) {
                    JOptionPane.showMessageDialog(v, "Mã thể loại đã tồn tại trong CSDL!");
                    return;
                }

                String sql = "INSERT INTO Theloai (MaTL, TenTL) VALUES (?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, ma);
                ps.setString(2, ten);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(v, "Thêm thể loại thành công!");
                loadData();
                resetForm();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi thêm thể loại: " + ex.getMessage());
            }
        }
    }

    // =============== SỬA ===============
    class action_sua implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ma = v.txtMaTL.getText().trim();
            String ten = v.txtTenTL.getText().trim();
            if (ma.isEmpty()) {
                JOptionPane.showMessageDialog(v, "Vui lòng chọn thể loại cần sửa!");
                return;
            }
            if (ten.isEmpty()) {
                JOptionPane.showMessageDialog(v, "Tên thể loại không được để trống!");
                return;
            }
            try (Connection conn = getConnection()) {
                String sql = "UPDATE Theloai SET TenTL = ? WHERE MaTL = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, ten);
                ps.setString(2, ma);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(v, "Sửa thể loại thành công!");
                loadData();
                resetForm();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi sửa thể loại: " + ex.getMessage());
            }
        }
    }

    // =============== XÓA ===============
    class action_xoa implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String ma = v.txtMaTL.getText().trim();
            if (ma.isEmpty()) {
                JOptionPane.showMessageDialog(v, "Vui lòng chọn thể loại cần xóa!");
                return;
            }
            int ch = JOptionPane.showConfirmDialog(v,
                    "Bạn có chắc muốn xóa thể loại này không?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (ch != JOptionPane.YES_OPTION) return;
            try (Connection conn = getConnection()) {
                String sql = "DELETE FROM Theloai WHERE MaTL = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, ma);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(v, "Xóa thể loại thành công!");
                loadData();
                resetForm();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v,
                        "Không thể xóa thể loại (có thể đang được dùng ở bảng Sách)!");
            }
        }
    }

    // =============== RESET (NÚT RESET) ===============
    class action_reset implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetForm();
            loadData();
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

                String sql = "SELECT MaTL, TenTL FROM Theloai WHERE MaTL LIKE ? OR TenTL LIKE ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                String p = "%" + key + "%";
                ps.setString(1, p);
                ps.setString(2, p);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    v.model.addRow(new Object[]{
                            rs.getString("MaTL"),
                            rs.getString("TenTL")
                    });
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi tìm kiếm thể loại!");
            }
        }
    }

    // =============== XUẤT FILE ===============
    class action_xuatfile implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (v.model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(v, "Không có dữ liệu để xuất!");
                return;
            }

            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Chọn nơi lưu file");
            fc.setSelectedFile(new java.io.File("theloai.csv"));

            int choose = fc.showSaveDialog(v);
            if (choose != JFileChooser.APPROVE_OPTION) return;

            java.io.File file = fc.getSelectedFile();

            try (
                java.io.OutputStreamWriter osw =
                    new java.io.OutputStreamWriter(
                        new java.io.FileOutputStream(file),
                        java.nio.charset.StandardCharsets.UTF_8
                    );
                java.io.BufferedWriter bw = new java.io.BufferedWriter(osw)
            ) {

                // Ghi BOM UTF-8
                bw.write("\uFEFF");
                // HEADER
                bw.write("MaTL,TenTL");
                bw.newLine();

                // DATA
                for (int i = 0; i < v.model.getRowCount(); i++) {
                    String ma = v.table.getValueAt(i, 0).toString();
                    String ten = v.table.getValueAt(i, 1).toString();
                    bw.write(ma + "," + ten);
                    bw.newLine();
                }

                JOptionPane.showMessageDialog(v, "Xuất file thể loại thành công!");

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi xuất file thể loại!");
            }
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
