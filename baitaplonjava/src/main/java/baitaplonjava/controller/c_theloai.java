
package baitaplonjava.controller;
import baitaplonjava.model.m_theloai;

import baitaplonjava.view.v_theloai;
import baitaplonjava.view.v_trangchu;

import java.awt.event.*;
import java.io.*; // Để xử lý File
import java.sql.*;
import javax.swing.*;
import java.io.File;

public class c_theloai {

    private v_theloai v;
    private v_trangchu viewtrangchu; 
    private int selectedRow = -1;

    private final String url = "jdbc:mysql://localhost:3306/baitaplon";
    private final String user = "root";
    private final String pass = "123456789";

    // Cập nhật Constructor nhận thêm viewTrangChu
    public c_theloai(v_theloai view, JFrame trangchu) {
        this.v = view;
        this.viewtrangchu = (v_trangchu) trangchu;

        // --- Các sự kiện cũ của bạn ---
        this.v.bt_them_action_listenner(e -> clearFields());
        this.v.bt_luu_action_listenner(e -> handleLuu());
        this.v.bt_sua_action_listenner(e -> handleSua());
        this.v.bt_xoa_action_listenner(e -> handleXoa());

        // --- Gán thêm 4 sự kiện mới ---
        this.v.bt_timkiem_action_listenner(e -> handleTimKiem());
        this.v.bt_xuatfile_action_listenner(e -> handleXuatFile());
       // this.v.bt_docfile_action_listenner(e -> handleDocFile());
        this.v.bt_quaylai_action_listenner(e -> handleQuayLai());

        this.v.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = v.table.getSelectedRow();
                if (selectedRow != -1) {
                    v.txtMaTL.setText(v.table.getValueAt(selectedRow, 0).toString());
                    v.txtTenTL.setText(v.table.getValueAt(selectedRow, 1).toString());
                    v.txtMaTL.setEditable(false);
                }
            }
        });

        loadData();
    }

    // 1. Xử lý Quay lại
    private void handleQuayLai() {
        v.dispose(); 
        if (viewtrangchu != null) viewtrangchu.setVisible(true);
    }

    // 2. Xử lý Tìm kiếm (Tìm theo tên gần đúng)
    private void handleTimKiem() {
    String keyword = v.txttimkiem.getText().trim();
    // 1. Cập nhật SQL để kiểm tra cả mã và tên
    String sql = "SELECT * FROM Theloai WHERE TenTL LIKE ? OR MaTL LIKE ?";
    
    try (Connection conn = DriverManager.getConnection(url, user, pass);
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        // 2. Gán từ khóa vào cả 2 tham số
        String searchPattern = "%" + keyword + "%";
        ps.setString(1, searchPattern); // Cho tentheloai
        ps.setString(2, searchPattern); // Cho matheloai
        
        ResultSet rs = ps.executeQuery();
        v.model.setRowCount(0);
        
        while (rs.next()) {
            v.model.addRow(new Object[]{
                rs.getString("MaTL"), 
                rs.getString("TenTL")
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // 3. Xử lý Xuất File (CSV - mở được bằng Excel)
    private void handleXuatFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(v) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (PrintWriter pw = new PrintWriter(new File(file.getAbsolutePath() + ".csv"))) {
                // Ghi header
                pw.println("Ma The Loai, Ten The Loai");
                // Ghi dữ liệu từ Table
                for (int i = 0; i < v.table.getRowCount(); i++) {
                    pw.println(v.table.getValueAt(i, 0) + "," + v.table.getValueAt(i, 1));
                }
                JOptionPane.showMessageDialog(v, "Xuất file thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(v, "Lỗi xuất file: " + e.getMessage());
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
    // Hàm tải dữ liệu từ DB lên JTable
    public void loadData() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "SELECT * FROM Theloai";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            v.model.setRowCount(0); // Xóa dữ liệu cũ trên bảng
            while (rs.next()) {
                v.model.addRow(new Object[]{
                    rs.getString("MaTL"), 
                    rs.getString("TenTL")
                });
            }
            System.out.println("Tải dữ liệu thành công!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi nạp dữ liệu: " + e.getMessage());
        }
    }

    // Xử lý nút LƯU (Insert)
    private void handleLuu() {
        m_theloai tl = v.get_theloai();
        if (tl == null) return;

        String sql = "INSERT INTO Theloai (MaTL, TenTL) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, tl.getMaTL());
            ps.setString(2, tl.getTenTL());
            
            int check = ps.executeUpdate();
            if (check > 0) {
                JOptionPane.showMessageDialog(v, "Thêm thành công!");
                loadData();
                clearFields();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi: Mã đã tồn tại hoặc lỗi DB! " + e.getMessage());
        }
    }

    // Xử lý nút SỬA (Update)
    private void handleSua() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(v, "Vui lòng chọn 1 dòng trên bảng!");
            return;
        }
        
        String sql = "UPDATE Theloai SET TenTL = ? WHERE MaTL = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, v.txtTenTL.getText().trim());
            ps.setString(2, v.txtMaTL.getText().trim());
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(v, "Sửa thành công!");
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xử lý nút XÓA (Delete)
    private void handleXoa() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(v, "Vui lòng chọn 1 dòng để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(v, "Bạn có muốn xóa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM Theloai WHERE matheloai = ?";
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                
                ps.setString(1, v.txtMaTL.getText());
                ps.executeUpdate();
                
                JOptionPane.showMessageDialog(v, "Đã xóa!");
                loadData();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearFields() {
        v.txtMaTL.setText("");
        v.txtTenTL.setText("");
        v.txtMaTL.setEditable(true);
        v.txtMaTL.requestFocus();
        selectedRow = -1;
    }
}