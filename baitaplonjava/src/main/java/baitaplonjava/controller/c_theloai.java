package baitaplonjava.controller;

import baitaplonjava.model.m_theloai;
import baitaplonjava.view.v_theloai;
import baitaplonjava.view.v_trangchu;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;

public class c_theloai {
    private v_theloai v;
    private v_trangchu viewtrangchu;
    private int selectedRow = -1;
    // Cờ đang tìm kiếm (bảng đang bị lọc)
    private boolean isSearching = false;
    // DB
    private final String url  = "jdbc:mysql://localhost:3306/baitaplon";
    private final String user = "root";
    private final String pass = "123456789";



    public c_theloai(v_theloai view, JFrame trangchu) {
        this.v = view;
        this.viewtrangchu = (v_trangchu) trangchu;
        // Nút
        this.v.bt_them_action_listenner(e -> handleThem());
        this.v.bt_luu_action_listenner(e -> handleLuu());
        this.v.bt_sua_action_listenner(e -> handleSua());
        this.v.bt_xoa_action_listenner(e -> handleXoa());
        this.v.bt_timkiem_action_listenner(e -> handleTimKiem());
        this.v.bt_xuatfile_action_listenner(e -> handleXuatFile());
        this.v.bt_quaylai_action_listenner(e -> handleQuayLai());
        // Click bảng
        this.v.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = v.table.getSelectedRow();
                if (selectedRow != -1) {
                    v.txtMaTL.setText(v.table.getValueAt(selectedRow, 0).toString());
                    v.txtTenTL.setText(v.table.getValueAt(selectedRow, 1).toString());
                    v.txtMaTL.setEditable(false); // chọn dòng -> khóa mã
                }
            }
        });
        loadData();
        resetForm();
    }
    // ===== RESET FORM =====
    private void resetForm() {
        v.txtMaTL.setText("");
        v.txtTenTL.setText("");
        v.txtMaTL.setEditable(true);
        v.table.clearSelection();
        selectedRow = -1;
        v.txtMaTL.requestFocus();
    }
    // ===== Nếu đang search thì trả bảng về full trước khi thao tác =====
    private void ensureFullTable() {
        if (isSearching) {
            loadData();
            isSearching = false;
            resetForm();
        }
    }

    // ===== 1) THÊM: thêm vào JTable (bảng tạm) =====
    private void handleThem() {
        ensureFullTable(); // nếu đang lọc thì trả về full
        m_theloai tl = v.get_theloai();
        if (tl == null) return;
        // check trùng mã trong bảng hiện tại
        for (int i = 0; i < v.model.getRowCount(); i++) {
            String maDangCo = v.model.getValueAt(i, 0).toString().trim();
            if (maDangCo.equalsIgnoreCase(tl.getMaTL().trim())) {
                JOptionPane.showMessageDialog(v, "Mã thể loại đã có trong bảng!");
                return;
            }
        }
        v.model.addRow(new Object[]{ tl.getMaTL(), tl.getTenTL() });
        JOptionPane.showMessageDialog(v, "Đã thêm vào danh sách tạm. Nhấn 'Lưu' để lưu vào CSDL!");
        resetForm();
    }
    // ===== 2) LƯU: lưu toàn bộ JTable xuống DB =====
    private void handleLuu() {
        ensureFullTable(); // nếu đang lọc thì trả về full
        if (v.model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(v, "Bảng đang trống, không có gì để lưu!");
            return;
        }

        String sql = "INSERT INTO Theloai (MaTL, TenTL) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int demThanhCong = 0;
            int demTrung = 0;
            for (int i = 0; i < v.model.getRowCount(); i++) {
                String ma = v.model.getValueAt(i, 0).toString().trim();
                String ten = v.model.getValueAt(i, 1).toString().trim();

                try {
                    ps.setString(1, ma);
                    ps.setString(2, ten);
                    ps.executeUpdate();
                    demThanhCong++;
                } catch (SQLException ex) {
                    // trùng mã hoặc ràng buộc -> bỏ qua
                    demTrung++;
                }
            }

            JOptionPane.showMessageDialog(v,
                    "Lưu xong!\nThành công: " + demThanhCong + "\nTrùng/không lưu: " + demTrung);

            loadData();
            isSearching = false;
            resetForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi lưu: " + e.getMessage());
        }
    }
    // ===== 3) SỬA: update theo MaTL =====
    private void handleSua() {
        ensureFullTable(); // nếu đang lọc thì trả về full
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(v, "Vui lòng chọn một dòng để sửa!");
            return;
        }
        String ma = v.txtMaTL.getText().trim();
        String ten = v.txtTenTL.getText().trim();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(v, "Tên thể loại không được để trống!");
            v.txtTenTL.requestFocus();
            return;
        }
        String sql = "UPDATE Theloai SET TenTL = ? WHERE MaTL = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ten);
            ps.setString(2, ma);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(v, "Cập nhật thành công!");
            loadData();
            isSearching = false;
            resetForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi sửa: " + e.getMessage());
        }
    }

    // ===== 4) XÓA =====
    private void handleXoa() {
        ensureFullTable(); // nếu đang lọc thì trả về full

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(v, "Vui lòng chọn dòng cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(v, "Bạn chắc chắn muốn xóa?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String ma = v.txtMaTL.getText().trim();
        String sql = "DELETE FROM Theloai WHERE MaTL = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ma);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(v, "Đã xóa thành công!");
            loadData();
            isSearching = false;
            resetForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Không thể xóa vì thể loại đang được dùng ở bảng khác!");
        }
    }

    // ===== 5) TÌM KIẾM =====
    private void handleTimKiem() {
        String keyword = v.txttimkiem.getText().trim();

        // rỗng -> load all
        if (keyword.isEmpty()) {
            loadData();
            isSearching = false;
            resetForm();
            return;
        }

        String sql = "SELECT * FROM Theloai WHERE TenTL LIKE ? OR MaTL LIKE ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String p = "%" + keyword + "%";
            ps.setString(1, p);
            ps.setString(2, p);
            ResultSet rs = ps.executeQuery();
            v.model.setRowCount(0);
            while (rs.next()) {
                v.model.addRow(new Object[]{
                        rs.getString("MaTL"),
                        rs.getString("TenTL")
                });
            }
            // đang ở chế độ lọc
            isSearching = true;
            resetForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi tìm kiếm: " + e.getMessage());
        }
    }
    // ===== 6) XUẤT FILE =====
    private void handleXuatFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(v) != JFileChooser.APPROVE_OPTION) return;
        try {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".csv")) path += ".csv";

            FileOutputStream fos = new FileOutputStream(path);
            fos.write(0xEF); fos.write(0xBB); fos.write(0xBF); // BOM UTF-8

            PrintWriter pw = new PrintWriter(new OutputStreamWriter(fos, "UTF-8"));
            pw.println("STT;Mã Thể Loại;Tên Thể Loại");

            for (int i = 0; i < v.table.getRowCount(); i++) {
                String ma = v.table.getValueAt(i, 0).toString();
                String ten = v.table.getValueAt(i, 1).toString();
                pw.println((i + 1) + ";" + ma + ";" + ten);
            }

            pw.close();
            JOptionPane.showMessageDialog(v, "Xuất file thành công!");
            resetForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi: " + e.getMessage());
        }
    }
    // ===== 7) QUAY LẠI =====
    private void handleQuayLai() {
        v.dispose();
        if (viewtrangchu != null) viewtrangchu.setVisible(true);
    }
    // ===== LOAD DATA =====
    public void loadData() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "SELECT * FROM Theloai";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            v.model.setRowCount(0);
            while (rs.next()) {
                v.model.addRow(new Object[]{
                        rs.getString("MaTL"),
                        rs.getString("TenTL")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }
}
/*
    // 4. Xử lý Đọc File (Đọc từ file CSV rồi nạp vào bảng)
    private void handleDocFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(v) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                br.readLine(); // Bỏ qua dòng header
                v.model.setRowCount(0); // Xóa bảng hiện tại
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 2) {
                        v.model.addRow(data);
                    }
                }
                JOptionPane.showMessageDialog(v, "Đọc file thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(v, "Lỗi đọc file: " + e.getMessage());
            }
        }
    }
*/