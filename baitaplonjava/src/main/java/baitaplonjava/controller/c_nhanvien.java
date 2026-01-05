package baitaplonjava.controller;

import baitaplonjava.view.v_nhanvien;
import baitaplonjava.view.v_trangchu;
import baitaplonjava.model.m_nhanvien;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class c_nhanvien {

    private v_nhanvien v;
    private v_trangchu home;
    private int row = -1;
// ==============================kết nối DB========================================================================================================================
    private final String url = "jdbc:mysql://localhost:3306/baitaplon?useUnicode=true&characterEncoding=UTF-8";
    private final String user = "root";
    private final String pass = "1234567890";

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
// ==============================Gắn sự kiện cho nút================================================================================================================
    public c_nhanvien(v_nhanvien view, JFrame h) {
        v = view;
        home = (v_trangchu) h;

        v.btnthem.addActionListener(e -> them());
        v.btnsua.addActionListener(e -> sua());
        v.btnxoa.addActionListener(e -> xoa());
        v.btnluu.addActionListener(e -> luu());
        v.btntimkiem.addActionListener(e -> timkiem());
        v.btnxuatfile.addActionListener(e -> xuatFileCSV());
        v.btnR.addActionListener(e -> resetForm());

        v.btnback.addActionListener(e -> back());

        v.table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                row = v.table.getSelectedRow();
                showForm();
            }
        });

        loadData();
    }
//==============================Hàm kết nối Database==================================================================================================================
    private Connection conn() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
//Đổ dữ liệu bảng lên form==============================================================================================================
    private void showForm() {
        if (row == -1) return;

        v.txtMaNV.setText(v.model.getValueAt(row, 0).toString());
        v.txtTenNV.setText(v.model.getValueAt(row, 1).toString());
        v.cbGioiTinh.setSelectedItem(v.model.getValueAt(row, 2));
        try {
            v.dateNS.setValue(sdf.parse(v.model.getValueAt(row, 3).toString()));
        } catch (Exception ignored) {}
        v.txtDiaChi.setText(v.model.getValueAt(row, 4).toString());
        v.txtDienThoai.setText(v.model.getValueAt(row, 5).toString());
        v.txtEmail.setText(v.model.getValueAt(row, 6).toString());

        v.txtMaNV.setEditable(false);
    }
// ==============================CHECK TRÙNG MÃ NV =======================================================================================
    private boolean isMaNVExists(String maNV) {

        // 1️⃣ Check trùng trong JTable
        for (int i = 0; i < v.model.getRowCount(); i++) {
            if (v.model.getValueAt(i, 0).toString().equalsIgnoreCase(maNV)) {
                return true;
            }
        }
        return false;
    }
// ================================================== THÊM ===============================================================================
    private void them() {
        m_nhanvien nv = v.getNhanVien();
        if (nv == null) {
            JOptionPane.showMessageDialog(v, "Vui lòng nhập đầy đủ thông tin");
        return;
    }
    if (isMaNVExists(nv.getMaNV())) {
        JOptionPane.showMessageDialog(v, "Mã nhân viên đã tồn tại!");
        v.txtMaNV.requestFocus();
        return;
    }
        v.model.addRow(new Object[]{
                nv.getMaNV(),
                nv.getTenNV(),
                nv.getGioiTinh(),
                sdf.format(nv.getNgaySinh()),
                nv.getDiaChi(),
                nv.getDienThoai(),
                nv.getEmail()
        });
       
        resetForm();
    }
// ================================================== SỬA ===============================================================================

    private void sua() {
        if (row == -1) return;

        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement(
                     "UPDATE nhanvien SET TenNV=?, NgaysinhNV=?, GioitinhNV=?, DienthoaiNV=?, EmailNV=?, DiachiNV=? WHERE MaNV=?")) {

            ps.setString(1, v.txtTenNV.getText());
            ps.setDate(2, new java.sql.Date(((java.util.Date) v.dateNS.getValue()).getTime()));
            ps.setString(3, v.cbGioiTinh.getSelectedItem().toString());
            ps.setString(4, v.txtDienThoai.getText());
            ps.setString(5, v.txtEmail.getText());
            ps.setString(6, v.txtDiaChi.getText());
            ps.setString(7, v.txtMaNV.getText());
            ps.executeUpdate();

            v.model.setValueAt(v.txtTenNV.getText(), row, 1);
            v.model.setValueAt(v.cbGioiTinh.getSelectedItem(), row, 2);
            v.model.setValueAt(sdf.format(v.dateNS.getValue()), row, 3);
            v.model.setValueAt(v.txtDiaChi.getText(), row, 4);
            v.model.setValueAt(v.txtDienThoai.getText(), row, 5);
            v.model.setValueAt(v.txtEmail.getText(), row, 6);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi sửa");
        }
    }
// ================================================== XÓA ===============================================================================

    private void xoa() {
        if (row == -1) return;

        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement("DELETE FROM nhanvien WHERE MaNV=?")) {

            ps.setString(1, v.txtMaNV.getText());
            ps.executeUpdate();
            v.model.removeRow(row);
            row = -1;
           
            resetForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Không xóa được");
        }
        
    }
    
// ================================================== LƯU ===============================================================================

//    private void luu() {
//        try (Connection c = conn()) {
//            for (int i = 0; i < v.model.getRowCount(); i++) {
//                PreparedStatement ps = c.prepareStatement(
//                        "INSERT INTO nhanvien VALUES (?,?,?,?,?,?,?)");
//
//                ps.setString(1, v.model.getValueAt(i, 0).toString());
//                ps.setString(2, v.model.getValueAt(i, 1).toString());
//                ps.setDate(3, new java.sql.Date(sdf.parse(v.model.getValueAt(i, 3).toString()).getTime()));
//                ps.setString(4, v.model.getValueAt(i, 2).toString());
//                ps.setString(5, v.model.getValueAt(i, 5).toString());
//                ps.setString(6, v.model.getValueAt(i, 6).toString());
//                ps.setString(7, v.model.getValueAt(i, 4).toString());
//                ps.executeUpdate();
//            }
//            JOptionPane.showMessageDialog(v, "Lưu thành công");
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(v, "Lỗi lưu");
//        }
//    }
    
    private void luu() {
    int savedCount = 0;
    try (Connection c = conn()) {
        // Chuẩn bị câu lệnh kiểm tra tồn tại
        PreparedStatement psCheck = c.prepareStatement("SELECT COUNT(*) FROM nhanvien WHERE MaNV = ?");
        
        // Chuẩn bị câu lệnh INSERT đúng thứ tự cột trong DB
        PreparedStatement psInsert = c.prepareStatement(
            "INSERT INTO nhanvien (MaNV, TenNV, NgaysinhNV, GioitinhNV, DienthoaiNV, EmailNV, DiachiNV) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)");

        for (int i = 0; i < v.model.getRowCount(); i++) {
            String maNV = v.model.getValueAt(i, 0).toString();

            // Kiểm tra xem MaNV đã tồn tại trong DB chưa
            psCheck.setString(1, maNV);
            ResultSet rs = psCheck.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                continue; // Bỏ qua nếu đã tồn tại (đã được thêm trước đó hoặc sửa/xóa riêng)
            }

            // Nếu chưa tồn tại → INSERT
            psInsert.setString(1, maNV);
            psInsert.setString(2, v.model.getValueAt(i, 1).toString()); // TenNV
            psInsert.setDate(3, new java.sql.Date(sdf.parse(v.model.getValueAt(i, 3).toString()).getTime())); // Ngaysinh
            psInsert.setString(4, v.model.getValueAt(i, 2).toString()); // Gioitinh
            psInsert.setString(5, v.model.getValueAt(i, 5).toString()); // Dienthoai (cột 5 trong table)
            psInsert.setString(6, v.model.getValueAt(i, 6).toString()); // Email (cột 6)
            psInsert.setString(7, v.model.getValueAt(i, 4).toString()); // Diachi (cột 4)

            psInsert.executeUpdate();
            savedCount++;
        }

        if (savedCount > 0) {
            JOptionPane.showMessageDialog(v, "Lưu thành công " + savedCount + " nhân viên mới!");
            loadData(); // Tải lại dữ liệu từ DB để đồng bộ (tùy chọn)
        } else {
            JOptionPane.showMessageDialog(v, "Không có nhân viên mới để lưu.");
        }

    } catch (Exception e) {
        e.printStackTrace(); // Rất quan trọng: in lỗi ra console
        JOptionPane.showMessageDialog(v, "Lỗi khi lưu: " + e.getMessage());
    }
}
// ================================================== TÌM KIẾM ===============================================================================

    private void timkiem() {
        v.model.setRowCount(0);
        try (Connection c = conn();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT * FROM nhanvien WHERE MaNV LIKE ? OR TenNV LIKE ? OR GioitinhNV LIKE ?")) {

            ps.setString(1, "%" + v.txttimkiem.getText() + "%");
            ps.setString(2, "%" + v.txttimkiem.getText() + "%");
            ps.setString(3, "%" + v.txttimkiem.getText() + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                v.model.addRow(new Object[]{
                        rs.getString("MaNV"),
                        rs.getString("TenNV"),
                        rs.getString("GioitinhNV"),
                        sdf.format(rs.getDate("NgaysinhNV")),
                        rs.getString("DiachiNV"),
                        rs.getString("DienthoaiNV"),
                        rs.getString("EmailNV")
                });
            }
        } catch (Exception ignored) {}
    }
// ================================================== LOAD DỮ LIỆU  ===============================================================================

    private void loadData() {
        v.model.setRowCount(0);
        try (Connection c = conn();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM nhanvien")) {

            while (rs.next()) {
                v.model.addRow(new Object[]{
                        rs.getString("MaNV"),
                        rs.getString("TenNV"),
                        rs.getString("GioitinhNV"),
                        sdf.format(rs.getDate("NgaysinhNV")),
                        rs.getString("DiachiNV"),
                        rs.getString("DienthoaiNV"),
                        rs.getString("EmailNV")
                });
            }
        } catch (Exception ignored) {}
    }
    // ================================================== XUẤT FILE ===============================================================================

    private void xuatFileCSV() {
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Chọn nơi lưu file");
    chooser.setSelectedFile(new java.io.File("nhanvien.csv"));

    if (chooser.showSaveDialog(v) != JFileChooser.APPROVE_OPTION) return;

    try (java.io.FileWriter fw = new java.io.FileWriter(chooser.getSelectedFile())) {

        // ghi tiêu đề cột
        for (int i = 0; i < v.table.getColumnCount(); i++) {
            fw.write(v.table.getColumnName(i));
            if (i < v.table.getColumnCount() - 1) fw.write(",");
        }
        fw.write("\n");

        // ghi dữ liệu
        for (int i = 0; i < v.table.getRowCount(); i++) {
            for (int j = 0; j < v.table.getColumnCount(); j++) {
                fw.write(v.table.getValueAt(i, j).toString());
                if (j < v.table.getColumnCount() - 1) fw.write(",");
            }
            fw.write("\n");
        }

        JOptionPane.showMessageDialog(v, "Xuất file CSV thành công");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(v, "Lỗi xuất file");
    }
}
// ================================================== THOÁT ===============================================================================

    private void back() {
        v.dispose();
        home.setVisible(true);
    }
// ================================================== RESERT ===============================================================================

private void resetForm() {
    v.txtMaNV.setText("");
    v.txtTenNV.setText("");
    v.dateNS.setValue(null);
    v.txtDiaChi.setText("");
    v.txtDienThoai.setText("");
    v.txtEmail.setText("");
    v.cbGioiTinh.setSelectedIndex(0);
    v.txtMaNV.setEditable(true);
    row = -1; 
    v.table.clearSelection();
}


}
