/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.controller;


import baitaplonjava.model.m_phieumuon;
import baitaplonjava.view.v_phieumuon;
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
public class c_phieumuon {
    private v_phieumuon v;
    private v_trangchu trangchu;
    private int k = -1;

    public c_phieumuon(v_phieumuon viewmuon, v_trangchu viewtrangchu) {
        this.v = viewmuon;
        this.trangchu = viewtrangchu;

        // ===== ACTION LISTENER =====
        v.bt_them_action_listenner(new action_them());
        v.bt_sua_action_listenner(new action_sua());
        v.bt_xoa_action_listenner(new action_xoa());
        v.bt_reset_action_listenner(e -> reset());
        v.bt_timkiem_action_listenner(new action_timkiem());
        v.bt_xuatfile_action_listenner(new action_xuatfile());
        v.bt_quaylai_action_listenner(e -> quaylai());

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
            String sql = "SELECT * FROM Phieumuon";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                Date ngayMuon = rs.getTimestamp("NgayMuon");
                Date hanTra = rs.getTimestamp("HanTra");
                v.model.addRow(new Object[]{
                    rs.getString("MaPM"),
                    rs.getString("MaDG"),
                    rs.getString("MaS"),
                    ngayMuon == null ? "" : sdf.format(ngayMuon),
                    hanTra == null ? "" : sdf.format(hanTra)
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= LOAD COMBOBOX =================
    private void loadComboBox() {
        try (Connection c = getConnection()) {
            var st = c.createStatement();
            var rs = st.executeQuery("SELECT MaDG FROM Docgia");
            v.cbMaDG.removeAllItems();
            while (rs.next()) v.cbMaDG.addItem(rs.getString("MaDG"));
            v.cbMaDG.setSelectedIndex(-1);

            rs = st.executeQuery("SELECT MaS FROM Sach");
            v.cbMaS.removeAllItems();
            while (rs.next()) v.cbMaS.addItem(rs.getString("MaS"));
            v.cbMaS.setSelectedIndex(-1);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(v, "Lỗi load combo box!");
        }
    }

    // ================= HIỂN THỊ LÊN FORM =================
    private void hienThiLenForm() {
        if (k < 0) return;
        v.txtMaPM.setText(v.table.getValueAt(k, 0).toString());
        v.cbMaDG.setSelectedItem(v.table.getValueAt(k, 1).toString());
        v.cbMaS.setSelectedItem(v.table.getValueAt(k, 2).toString());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date ngayMuon = sdf.parse(v.table.getValueAt(k, 3).toString());
            Date hanTra = sdf.parse(v.table.getValueAt(k, 4).toString());
            v.dcNgayMuon.setDate(ngayMuon);
            v.dcHanTra.setDate(hanTra);
        } catch (Exception e) {
            v.dcNgayMuon.setDate(null);
            v.dcHanTra.setDate(null);
        }
        v.txtMaPM.setEditable(false);
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
            m_phieumuon pm = v.getPhieuMuon();
            if (pm == null) return;
            try (Connection c = getConnection()) {
                // check trùng mã
                String check = "SELECT MaPM FROM Phieumuon WHERE MaPM=?";
                PreparedStatement psCheck = c.prepareStatement(check);
                psCheck.setString(1, pm.getMaPM());
                if (psCheck.executeQuery().next()) {
                    JOptionPane.showMessageDialog(v, "Mã phiếu mượn đã tồn tại!");
                    return;
                }
                String sql = "INSERT INTO Phieumuon VALUES (?,?,?,?,?)";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, pm.getMaPM());
                ps.setString(2, pm.getMaDG());
                ps.setString(3, pm.getMaS());
                ps.setTimestamp(4, new Timestamp(pm.getNgayMuon().getTime()));
                ps.setTimestamp(5, new Timestamp(pm.getHanTra().getTime()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(v, "Thêm phiếu mượn thành công!");
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
            if (v.txtMaPM.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(v, "Chọn phiếu mượn cần sửa!");
                return;
            }
            m_phieumuon pm = v.getPhieuMuon();
            if (pm == null) return;
            try (Connection c = getConnection()) {
                String sql = "UPDATE Phieumuon SET MaDG=?, MaS=?, NgayMuon=?, HanTra=? WHERE MaPM=?";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, pm.getMaDG());
                ps.setString(2, pm.getMaS());
                ps.setTimestamp(3, new Timestamp(pm.getNgayMuon().getTime()));
                ps.setTimestamp(4, new Timestamp(pm.getHanTra().getTime()));
                ps.setString(5, pm.getMaPM());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(v, "Sửa phiếu mượn thành công!");
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
            String maPM = v.table.getValueAt(k, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(v, "Xóa phiếu mượn này?");
            if (confirm != JOptionPane.YES_OPTION) return;
            try (Connection c = getConnection()) {
                String sql = "DELETE FROM Phieumuon WHERE MaPM=?";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, maPM);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(v, "Xóa phiếu mượn thành công!");
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
                String sql = "SELECT * FROM Phieumuon WHERE MaPM LIKE ? OR MaDG LIKE ?";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, "%" + key + "%");
                ps.setString(2, "%" + key + "%");
                ResultSet rs = ps.executeQuery();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                while (rs.next()) {
                    Date ngayMuon = rs.getTimestamp("NgayMuon");
                    Date hanTra = rs.getTimestamp("HanTra");
                    v.model.addRow(new Object[]{
                        rs.getString("MaPM"),
                        rs.getString("MaDG"),
                        rs.getString("MaS"),
                        ngayMuon == null ? "" : sdf.format(ngayMuon),
                        hanTra == null ? "" : sdf.format(hanTra)
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
            fc.setSelectedFile(new java.io.File("phieumuon.csv"));
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
                JOptionPane.showMessageDialog(v, "Xuất file phiếu mượn thành công!");
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