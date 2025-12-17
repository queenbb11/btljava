
package baitaplonjava.controller;

import baitaplonjava.model.model_theloai;
import baitaplonjava.view.view_theloai;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JOptionPane;

public class controller_theloai {

    private view_theloai v;
    private int selectedRow = -1;

    // Thông tin kết nối MySQL
    private final String url = "jdbc:mysql://localhost:3306/baitaplon";
    private final String user = "root";
    private final String pass = "123456789";

    public controller_theloai(view_theloai view) {
        this.v = view;

        // 1. Gán sự kiện cho các nút bấm từ View
        this.v.bt_them_action_listenner(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        this.v.bt_luu_action_listenner(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLuu();
            }
        });

        this.v.bt_sua_action_listenner(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSua();
            }
        });

        this.v.bt_xoa_action_listenner(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleXoa();
            }
        });

        // 2. Sự kiện click bảng để lấy dữ liệu lên ô nhập
        this.v.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = v.table.getSelectedRow();
                if (selectedRow != -1) {
                    v.txtmatheloai.setText(v.table.getValueAt(selectedRow, 0).toString());
                    v.txttentheloai.setText(v.table.getValueAt(selectedRow, 1).toString());
                    // Khóa ô mã thể loại khi đang chọn dòng để tránh sửa nhầm khóa chính
                    v.txtmatheloai.setEditable(false);
                }
            }
        });

        // 3. Tải dữ liệu ngay khi khởi tạo
        loadData();
    }

    // Hàm tải dữ liệu từ DB lên JTable
    public void loadData() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String sql = "SELECT * FROM theloai";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            v.model.setRowCount(0); // Xóa dữ liệu cũ trên bảng
            while (rs.next()) {
                v.model.addRow(new Object[]{
                    rs.getString("matheloai"), 
                    rs.getString("tentheloai")
                });
            }
            System.out.println("Tải dữ liệu thành công!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi nạp dữ liệu: " + e.getMessage());
        }
    }

    // Xử lý nút LƯU (Insert)
    private void handleLuu() {
        model_theloai tl = v.get_theloai();
        if (tl == null) return;

        String sql = "INSERT INTO theloai (matheloai, tentheloai) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, tl.getmatheloai());
            ps.setString(2, tl.gettentheloai());
            
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
        
        String sql = "UPDATE theloai SET tentheloai = ? WHERE matheloai = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, v.txttentheloai.getText().trim());
            ps.setString(2, v.txtmatheloai.getText().trim());
            
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
            String sql = "DELETE FROM theloai WHERE matheloai = ?";
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                
                ps.setString(1, v.txtmatheloai.getText());
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
        v.txtmatheloai.setText("");
        v.txttentheloai.setText("");
        v.txtmatheloai.setEditable(true);
        v.txtmatheloai.requestFocus();
        selectedRow = -1;
    }
}