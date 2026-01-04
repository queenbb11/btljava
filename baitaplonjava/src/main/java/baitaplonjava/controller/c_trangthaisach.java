package baitaplonjava.controller;

import baitaplonjava.model.m_trangthaisach;
import baitaplonjava.view.v_trangthaisach;
import baitaplonjava.view.v_trangchu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class c_trangthaisach {
    private v_trangthaisach v;
    private v_trangchu trangchu;
    private int k = -1;

    public c_trangthaisach(v_trangthaisach viewTS, v_trangchu viewtrangchu) {
        this.v = viewTS;
        this.trangchu = viewtrangchu;

        // Gán các sự kiện theo đúng mẫu hàm của bạn
        v.bt_them_action_listenner(new action_them());
        v.bt_sua_action_listenner(new action_sua());
        v.bt_xoa_action_listenner(new action_xoa());
        v.bt_reset_action_listenner(e -> reset());
        v.bt_timkiem_action_listenner(new action_timkiem());
        v.bt_xuatfile_action_listenner(new action_xuatfile());
        v.bt_quaylai_action_listenner(e -> quaylai());

        v.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                k = v.table.getSelectedRow();
                hienThiLenForm();
            }
        });

        loadData();
    }

    // ================= KẾT NỐI DB =================
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/baitaplon",
                "root", "123456789"
        );
    }

    // ================= LOAD DATA =================
    private void loadData() {
        v.model.setRowCount(0);
        try (Connection c = getConnection()) {
            String sql = "SELECT * FROM Trangthaisach";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                v.model.addRow(new Object[]{
                    rs.getString("MaS"),
                    rs.getString("Tentrangthai"),
                    rs.getString("Mota")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= HIỂN THỊ LÊN FORM =================
    private void hienThiLenForm() {
        if (k < 0) return;

        v.txtMaS.setText(v.table.getValueAt(k, 0).toString());
        v.txtTenTrangThai.setText(v.table.getValueAt(k, 1).toString());
        v.txtMoTa.setText(v.table.getValueAt(k, 2).toString());

        v.txtMaS.setEditable(false); // Không cho sửa mã sách khi đang chọn dòng
    }

    // ================= RESET =================
    private void reset() {
        v.resetForm();
        v.table.clearSelection();
        v.txtMaS.setEditable(true);
        k = -1;
    }

    // ================= THÊM =================
    class action_them implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            m_trangthaisach ts = v.get_trangthaisach();
            if (ts == null) return;

            try (Connection c = getConnection()) {
                // Check trùng mã sách trong bảng trạng thái
                String check = "SELECT MaS FROM Trangthaisach WHERE MaS=?";
                PreparedStatement psCheck = c.prepareStatement(check);
                psCheck.setString(1, ts.getMaS());
                if (psCheck.executeQuery().next()) {
                    JOptionPane.showMessageDialog(v, "Mã sách này đã có thông tin trạng thái!");
                    return;
                }

                String sql = "INSERT INTO Trangthaisach VALUES (?,?,?)";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, ts.getMaS());
                ps.setString(2, ts.getTenTrangThai());
                ps.setString(3, ts.getMota());
                ps.executeUpdate();

                JOptionPane.showMessageDialog(v, "Thêm trạng thái thành công!");
                loadData();
                reset();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi thêm dữ liệu! (Kiểm tra xem mã sách có tồn tại trong bảng Sach chưa)");
            }
        }
    }

    // ================= SỬA =================
    class action_sua implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (v.txtMaS.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(v, "Chọn trạng thái cần sửa!");
                return;
            }

            try (Connection c = getConnection()) {
                String sql = "UPDATE Trangthaisach SET Tentrangthai=?, Mota=? WHERE MaS=?";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, v.txtTenTrangThai.getText().trim());
                ps.setString(2, v.txtMoTa.getText().trim());
                ps.setString(3, v.txtMaS.getText().trim());
                ps.executeUpdate();

                JOptionPane.showMessageDialog(v, "Sửa trạng thái thành công!");
                loadData();
                reset();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi sửa dữ liệu!");
            }
        }
    }

    // ================= XÓA =================
    class action_xoa implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ma = v.txtMaS.getText().trim();
            if (ma.isEmpty()) return;

            int confirm = JOptionPane.showConfirmDialog(v, "Xóa trạng thái của sách này?");
            if (confirm != JOptionPane.YES_OPTION) return;

            try (Connection cn = getConnection()) {
                String sql = "DELETE FROM Trangthaisach WHERE MaS=?";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setString(1, ma);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(v, "Xóa thành công!");
                loadData();
                reset();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Không thể xóa!");
            }
        }
    }

    // ================= TÌM KIẾM =================
    class action_timkiem implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String key = v.txttimkiem.getText().trim();
            if (key.isEmpty()) {
                loadData();
                return;
            }

            v.model.setRowCount(0);
            try (Connection c = getConnection()) {
                String sql = "SELECT * FROM Trangthaisach WHERE MaS LIKE ? OR Tentrangthai LIKE ?";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, "%" + key + "%");
                ps.setString(2, "%" + key + "%");

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    v.model.addRow(new Object[]{
                        rs.getString("MaS"),
                        rs.getString("Tentrangthai"),
                        rs.getString("Mota")
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi tìm kiếm!");
            }
        }
    }

    // ================= XUẤT FILE CSV =================
    class action_xuatfile implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (v.model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(v, "Không có dữ liệu để xuất!");
                return;
            }

            javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
            fc.setDialogTitle("Chọn nơi lưu file trạng thái");
            fc.setSelectedFile(new java.io.File("trangthaisach.csv"));

            int choose = fc.showSaveDialog(v);
            if (choose != javax.swing.JFileChooser.APPROVE_OPTION) return;

            java.io.File file = fc.getSelectedFile();
            try (
                java.io.OutputStreamWriter osw = new java.io.OutputStreamWriter(
                    new java.io.FileOutputStream(file), java.nio.charset.StandardCharsets.UTF_8);
                java.io.BufferedWriter bw = new java.io.BufferedWriter(osw)
            ) {
                bw.write("\uFEFF"); // BOM cho Excel nhận diện UTF-8
                // Header
                for (int i = 0; i < v.model.getColumnCount(); i++) {
                    bw.write(v.model.getColumnName(i));
                    if (i < v.model.getColumnCount() - 1) bw.write(",");
                }
                bw.newLine();
                // Data
                for (int i = 0; i < v.model.getRowCount(); i++) {
                    for (int j = 0; j < v.model.getColumnCount(); j++) {
                        Object val = v.model.getValueAt(i, j);
                        bw.write(val == null ? "" : val.toString());
                        if (j < v.model.getColumnCount() - 1) bw.write(",");
                    }
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(v, "Xuất file thành công!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi xuất file!");
            }
        }
    }

    // ================= QUAY LẠI =================
    private void quaylai() {
        v.dispose();
        trangchu.setVisible(true);
    }
}