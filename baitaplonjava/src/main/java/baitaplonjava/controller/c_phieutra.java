/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.controller;



import baitaplonjava.model.m_phieutra;
import baitaplonjava.view.v_phieutra;
import baitaplonjava.view.v_trangchu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author SCN
 */
public class c_phieutra {
    private v_phieutra v;
    private v_trangchu trangchu;
    private int k = -1;

    public c_phieutra(v_phieutra viewtra, v_trangchu viewtrangchu) {
        this.v = viewtra;
        this.trangchu = viewtrangchu;

        // ===== ACTION LISTENER =====
        v.bt_them_action_listener(new action_them());
        v.bt_sua_action_listener(new action_sua());
        v.bt_xoa_action_listener(new action_xoa());
        v.bt_reset_action_listener(e -> reset());
        v.bt_timkiem_action_listener(new action_timkiem());
        v.bt_xuatfile_action_listener(new action_xuatfile());
        v.bt_quaylai_action_listener(e -> quaylai());

        // click row trên table
        v.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                k = v.table.getSelectedRow();
                hienThiLenForm();
            }
        });

        loadData();
        loadComboBox();
    }

    // ================= KẾT NỐI DB =================
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/baitaplon",
            "root", "1234567890"
        );
    }

    // ================= LOAD DATA =================
    private void loadData() {
        v.model.setRowCount(0);
        try (Connection c = getConnection()) {
            String sql = "SELECT * FROM Phieutra";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                Date ngayTra = rs.getTimestamp("NgayTra");
                v.model.addRow(new Object[]{
                    rs.getString("MaPT"),
                    rs.getString("MaPM"),
                    ngayTra == null ? "" : sdf.format(ngayTra)
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= LOAD COMBOBOX MÃ PHIẾU MƯỢN =================
    private void loadComboBox() {
        try (Connection c = getConnection()) {
            var st = c.createStatement();
            var rs = st.executeQuery("SELECT MaPM FROM Phieumuon");
            v.cbMaPM.removeAllItems();
            while (rs.next()) v.cbMaPM.addItem(rs.getString("MaPM"));
            v.cbMaPM.setSelectedIndex(-1);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(v, "Lỗi load combo box Mã phiếu mượn!");
        }
    }

    // ================= HIỂN THỊ LÊN FORM =================
    private void hienThiLenForm() {
        if (k < 0) return;
        v.txtMaPT.setText(v.table.getValueAt(k, 0).toString());
        v.cbMaPM.setSelectedItem(v.table.getValueAt(k, 1).toString());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date ngayTra = sdf.parse(v.table.getValueAt(k, 2).toString());
            v.dcNgayTra.setDate(ngayTra);
        } catch (Exception e) {
            v.dcNgayTra.setDate(null);
        }
        v.txtMaPT.setEditable(false);
    }

    // ================= RESET =================
    private void reset() {
        v.resetForm();
        v.table.clearSelection();
        k = -1;
        loadData();
    }

    // ================= THÊM =================
    class action_them implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            m_phieutra pt = v.getPhieuTra();
            if (pt == null) return;
            try (Connection c = getConnection()) {
                // check trùng mã
                String check = "SELECT MaPT FROM Phieutra WHERE MaPT=?";
                PreparedStatement psCheck = c.prepareStatement(check);
                psCheck.setString(1, pt.getMaPT());
                if (psCheck.executeQuery().next()) {
                    JOptionPane.showMessageDialog(v, "Mã phiếu trả đã tồn tại!");
                    return;
                }
                String sql = "INSERT INTO Phieutra VALUES (?,?,?)";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, pt.getMaPT());
                ps.setString(2, pt.getMaPM());
                ps.setTimestamp(3, new Timestamp(pt.getNgayTra().getTime()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(v, "Thêm phiếu trả thành công!");
                loadData();
                reset();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi thêm dữ liệu!");
            }
        }
    }

    // ================= SỬA =================
    class action_sua implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (v.txtMaPT.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(v, "Chọn phiếu trả cần sửa!");
                return;
            }
            m_phieutra pt = v.getPhieuTra();
            if (pt == null) return;
            try (Connection c = getConnection()) {
                String sql = "UPDATE Phieutra SET MaPM=?, NgayTra=? WHERE MaPT=?";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, pt.getMaPM());
                ps.setTimestamp(2, new Timestamp(pt.getNgayTra().getTime()));
                ps.setString(3, pt.getMaPT());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(v, "Sửa phiếu trả thành công!");
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
            if (k < 0) return;
            String maPT = v.table.getValueAt(k, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(v, "Xóa phiếu trả này?");
            if (confirm != JOptionPane.YES_OPTION) return;
            try (Connection c = getConnection()) {
                String sql = "DELETE FROM Phieutra WHERE MaPT=?";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, maPT);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(v, "Xóa phiếu trả thành công!");
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
            String key = v.txtTimkiem.getText().trim();
            if (key.isEmpty()) {
                loadData();
                return;
            }
            v.model.setRowCount(0);
            try (Connection c = getConnection()) {
                String sql = "SELECT * FROM Phieutra WHERE MaPT LIKE ? OR MaPM LIKE ?";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, "%" + key + "%");
                ps.setString(2, "%" + key + "%");
                ResultSet rs = ps.executeQuery();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                while (rs.next()) {
                    Date ngayTra = rs.getTimestamp("NgayTra");
                    v.model.addRow(new Object[]{
                        rs.getString("MaPT"),
                        rs.getString("MaPM"),
                        ngayTra == null ? "" : sdf.format(ngayTra)
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
            fc.setDialogTitle("Chọn nơi lưu file");
            fc.setSelectedFile(new java.io.File("phieutra.csv"));
            int choose = fc.showSaveDialog(v);
            if (choose != javax.swing.JFileChooser.APPROVE_OPTION) return;
            java.io.File file = fc.getSelectedFile();
            try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file),
                    java.nio.charset.StandardCharsets.UTF_8);
                 BufferedWriter bw = new BufferedWriter(osw)) {
                bw.write("\uFEFF");
                for (int i = 0; i < v.model.getColumnCount(); i++) {
                    bw.write(v.model.getColumnName(i));
                    if (i < v.model.getColumnCount() - 1) bw.write(",");
                }
                bw.newLine();
                for (int i = 0; i < v.model.getRowCount(); i++) {
                    for (int j = 0; j < v.model.getColumnCount(); j++) {
                        Object val = v.model.getValueAt(i, j);
                        bw.write(val == null ? "" : val.toString());
                        if (j < v.model.getColumnCount() - 1) bw.write(",");
                    }
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(v, "Xuất file phiếu trả thành công!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi xuất file!");
            }
        }
    }

    // ================= QUAY LẠI =================
    private void quaylai() {
        reset();
        v.dispose();
        trangchu.setVisible(true);
    }
}