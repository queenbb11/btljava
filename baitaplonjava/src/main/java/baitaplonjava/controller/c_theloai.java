
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

    // Cấu hình kết nối Database
    private final String url = "jdbc:mysql://localhost:3306/baitaplon";
    private final String user = "root";
    private final String pass = "123456789";

    public c_theloai(v_theloai view, JFrame trangchu) {
        this.v = view;
        this.viewtrangchu = (v_trangchu) trangchu;

        // --- Gán các sự kiện (Listeners) ---
        // Nút Thêm: Đưa dữ liệu từ Textfield lên JTable (giống Controller_nv)
        this.v.bt_them_action_listenner(e -> handleThem());
        
        // Nút Lưu: Lưu dữ liệu hiện tại vào Cơ sở dữ liệu
        this.v.bt_luu_action_listenner(e -> handleLuu());
        
        this.v.bt_sua_action_listenner(e -> handleSua());
        this.v.bt_xoa_action_listenner(e -> handleXoa());
        this.v.bt_timkiem_action_listenner(e -> handleTimKiem());
        this.v.bt_xuatfile_action_listenner(e -> handleXuatFile());
        this.v.bt_quaylai_action_listenner(e -> handleQuayLai());

        // Sự kiện click chuột vào bảng
        this.v.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = v.table.getSelectedRow();
                if (selectedRow != -1) {
                    v.txtMaTL.setText(v.table.getValueAt(selectedRow, 0).toString());
                    v.txtTenTL.setText(v.table.getValueAt(selectedRow, 1).toString());
                    v.txtMaTL.setEditable(false); // Không cho sửa mã khi đang chọn dòng
                }
            }
        });

        // Tải dữ liệu ban đầu từ DB
        loadData();
    }

    // 1. Xử lý nút THÊM (Đưa dữ liệu lên bảng hiển thị)
    private void handleThem() {
        m_theloai tl = v.get_theloai(); // Giả sử View đã có hàm get_theloai() lấy từ textfield
        if (tl != null) {
            // Thêm trực tiếp vào model của JTable
            v.model.addRow(new Object[]{
                tl.getMaTL(),
                tl.getTenTL()
            });
            JOptionPane.showMessageDialog(v, "Đã thêm vào danh sách tạm. Nhấn 'Lưu' để lưu vào CSDL!");
        }
    }

    // 2. Xử lý nút LƯU (Lưu dữ liệu vào SQL)
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
                JOptionPane.showMessageDialog(v, "Lưu vào CSDL thành công!");
                loadData(); // Tải lại bảng để đảm bảo đồng bộ với DB
                clearFields();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi: Mã '" + tl.getMaTL() + "' đã tồn tại!");
        }
    }

    // 3. Xử lý nút SỬA (Update theo MaTL)
    private void handleSua() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(v, "Vui lòng chọn một dòng để sửa!");
            return;
        }
        
        String sql = "UPDATE Theloai SET TenTL = ? WHERE MaTL = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, v.txtTenTL.getText().trim());
            ps.setString(2, v.txtMaTL.getText().trim());
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(v, "Cập nhật thành công!");
            loadData();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. Xử lý nút XÓA
    private void handleXoa() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(v, "Vui lòng chọn dòng cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(v, "Bạn chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM Theloai WHERE MaTL = ?";
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                
                ps.setString(1, v.txtMaTL.getText());
                ps.executeUpdate();
                
                JOptionPane.showMessageDialog(v, "Đã xóa thành công!");
                loadData();
                clearFields();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(v, "Không thể xóa vì thể loại này đang được sử dụng ở bảng khác!");
            }
        }
    }

    // 5. Xử lý TÌM KIẾM
    private void handleTimKiem() {
        String keyword = v.txttimkiem.getText().trim();
        String sql = "SELECT * FROM Theloai WHERE TenTL LIKE ? OR MaTL LIKE ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            
            ResultSet rs = ps.executeQuery();
            v.model.setRowCount(0); // Xóa bảng để hiển thị kết quả tìm kiếm
            
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

           // 6. XỬ LÝ XUẤT FILE CSV
private void handleXuatFile() {
    JFileChooser fileChooser = new JFileChooser();
    if (fileChooser.showSaveDialog(v) == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        String filePath = file.getAbsolutePath();
        if (!filePath.endsWith(".csv")) {
            filePath += ".csv";
        }

        // Sử dụng FileOutputStream để ghi byte trực tiếp
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            
            // BƯỚC 1: Ghi 3 byte của BOM UTF-8 (EF BB BF) - Quan trọng nhất để Excel nhận diện tiếng Việt
            fos.write(0xEF);
            fos.write(0xBB);
            fos.write(0xBF);

            // BƯỚC 2: Bọc FileOutputStream bằng OutputStreamWriter với encoding UTF-8
            try (OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                 PrintWriter pw = new PrintWriter(osw)) {

                // BƯỚC 3: Dòng này giúp Excel tự động chia cột khi dùng dấu phẩy
                pw.println("sep=,");

                // BƯỚC 4: Ghi tiêu đề (Header)
                pw.println("STT,Ma The Loai,Ten The Loai");

                // BƯỚC 5: Ghi dữ liệu từ Table
                for (int i = 0; i < v.table.getRowCount(); i++) {
                    int stt = i + 1;
                    
                    // Lấy dữ liệu và ép kiểu an toàn
                    Object maObj = v.table.getValueAt(i, 0);
                    Object tenObj = v.table.getValueAt(i, 1);
                    
                    String ma = (maObj != null) ? maObj.toString() : "";
                    String ten = (tenObj != null) ? tenObj.toString() : "";

                    // Ghi dòng dữ liệu
                    pw.println(stt + "," + ma + "," + ten);
                }
                
                // Đẩy dữ liệu từ bộ nhớ đệm xuống file
                pw.flush();
            }
            
            JOptionPane.showMessageDialog(v, "Xuất file thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi hệ thống: " + e.getMessage());
        }
    }
}
    // 7. QUAY LẠI Trang chủ
    private void handleQuayLai() {
        v.dispose(); 
        if (viewtrangchu != null) viewtrangchu.setVisible(true);
    }

    // Hàm loadData: Đồng bộ JTable với Cơ sở dữ liệu
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

    // Làm mới các ô nhập liệu
    private void clearFields() {
        v.txtMaTL.setText("");
        v.txtTenTL.setText("");
        v.txtMaTL.setEditable(true);
        v.txtMaTL.requestFocus();
        selectedRow = -1;
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