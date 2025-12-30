/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.controller;


import baitaplonjava.model.m_phieuphat;
import baitaplonjava.view.v_phieuphat;
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
import javax.swing.JOptionPane;


/**
 *
 * @author SCN
 */
public class c_phieuphat {

    private v_phieuphat v;
    private v_trangchu trangchu;
    private int k = -1;
    private final int tienMoiNgay = 5000; // Tiền phạt mỗi ngày

    public c_phieuphat(v_phieuphat viewPhat, v_trangchu viewTrangChu) {
        this.v = viewPhat;
        this.trangchu = viewTrangChu;

        // ===== ACTION LISTENER =====
        v.bt_them_action_listener(new action_them());
        v.bt_sua_action_listener(new action_sua());
        v.bt_xoa_action_listener(new action_xoa());
        v.bt_reset_action_listener(e -> reset());
        v.bt_timkiem_action_listener(new action_timkiem());
        v.bt_xuatfile_action_listener(new action_xuatfile());
        v.bt_quaylai_action_listener(e -> quaylai());
        v.cbMaPM.addActionListener(e -> tinhTienPhat());

        // ===== CLICK TABLE =====
        v.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                k = v.table.getSelectedRow();
                hienThiLenForm();
            }
        });

        loadMaPMQuaHan();
        loadData();
    }

    // ================= KẾT NỐI DB =================
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/baitaplon","root","123456789");
    }

    // ================= LOAD COMBOBOX CHỈ NHỮNG PHIẾU MƯỢN QUÁ HẠN =================
    private void loadMaPMQuaHan() {
        v.cbMaPM.removeAllItems();
        try (Connection c = getConnection()) {
            String sql = """
                SELECT pm.MaPM, pm.HanTra, pt.NgayTra
                FROM Phieumuon pm
                LEFT JOIN Phieutra pt ON pm.MaPM = pt.MaPM
                WHERE DATEDIFF(IFNULL(pt.NgayTra, NOW()), pm.HanTra) > 0
            """;
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                v.cbMaPM.addItem(rs.getString("MaPM"));
            }
            v.cbMaPM.setSelectedIndex(-1);
            v.txtTienPhat.setText("");
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(v, "Lỗi load combo box Mã phiếu mượn!");
        }
    }

    // ================= TÍNH TIỀN PHẠT =================
    private void tinhTienPhat() {
        String maPM = (String) v.cbMaPM.getSelectedItem();
        if(maPM == null) return;
        try (Connection c = getConnection()) {
            String sql = "SELECT HanTra, NgayTra FROM Phieumuon LEFT JOIN Phieutra USING(MaPM) WHERE MaPM=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, maPM);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                java.util.Date hanTra = rs.getTimestamp("HanTra");
                java.util.Date ngayTra = rs.getTimestamp("NgayTra");
                if(ngayTra == null) ngayTra = new java.util.Date();
                long diff = ngayTra.getTime() - hanTra.getTime();
                int soNgayTre = (int)(diff / (1000*60*60*24));
                if(soNgayTre > 0) v.txtTienPhat.setText(String.valueOf(soNgayTre * tienMoiNgay));
                else v.txtTienPhat.setText("0");
            }
        } catch(Exception e) { e.printStackTrace(); }
    }

    // ================= LOAD DỮ LIỆU =================
    private void loadData() {
        v.model.setRowCount(0);
        try (Connection c = getConnection()) {
            String sql = "SELECT * FROM Phieuphat";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                v.model.addRow(new Object[]{
                    rs.getString("MaPP"),
                    rs.getString("MaPM"),
                    rs.getInt("TienPhat")
                });
            }
        } catch(Exception e) { e.printStackTrace(); }
    }

    // ================= HIỂN THỊ LÊN FORM =================
    private void hienThiLenForm() {
        if(k<0) return;
        v.txtMaPP.setText(v.table.getValueAt(k,0).toString());
        v.cbMaPM.setSelectedItem(v.table.getValueAt(k,1).toString());
        v.txtTienPhat.setText(v.table.getValueAt(k,2).toString());
        v.txtMaPP.setEditable(false);
    }

    // ================= RESET FORM =================
    private void reset() {
        v.resetForm();
        k = -1;
        loadMaPMQuaHan();
        loadData();
    }

    // ================= THÊM =================
    class action_them implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            m_phieuphat pp = v.getPhieuPhat();
            if(pp==null) return;
            try (Connection c = getConnection()) {
                // check trùng mã
                String check = "SELECT MaPP FROM Phieuphat WHERE MaPP=?";
                PreparedStatement ps = c.prepareStatement(check);
                ps.setString(1, pp.getMaPP());
                if(ps.executeQuery().next()) {
                    JOptionPane.showMessageDialog(v,"Mã phiếu phạt đã tồn tại!");
                    return;
                }
                String sql = "INSERT INTO Phieuphat VALUES (?,?,?)";
                ps = c.prepareStatement(sql);
                ps.setString(1, pp.getMaPP());
                ps.setString(2, pp.getMaPM());
                ps.setInt(3, pp.getTienPhat());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(v,"Thêm phiếu phạt thành công!");
                reset();
            } catch(Exception ex) { ex.printStackTrace(); }
        }
    }

    // ================= SỬA =================
    class action_sua implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            m_phieuphat pp = v.getPhieuPhat();
            if(pp==null) return;
            try (Connection c = getConnection()) {
                String sql = "UPDATE Phieuphat SET MaPM=?, TienPhat=? WHERE MaPP=?";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, pp.getMaPM());
                ps.setInt(2, pp.getTienPhat());
                ps.setString(3, pp.getMaPP());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(v,"Sửa phiếu phạt thành công!");
                reset();
            } catch(Exception ex) { ex.printStackTrace(); }
        }
    }

    // ================= XÓA =================
    class action_xoa implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String maPP = v.txtMaPP.getText().trim();
            if(maPP.isEmpty()) return;
            int c = JOptionPane.showConfirmDialog(v,"Xóa phiếu phạt này?");
            if(c!=JOptionPane.YES_OPTION) return;
            try (Connection cn = getConnection()) {
                String sql = "DELETE FROM Phieuphat WHERE MaPP=?";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setString(1, maPP);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(v,"Xóa phiếu phạt thành công!");
                reset();
            } catch(Exception ex) { ex.printStackTrace(); }
        }
    }

    // ================= TÌM KIẾM =================
    class action_timkiem implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String key = v.txtTimkiem.getText().trim();
            if (key.isEmpty()) {
                loadData();
                return;
            }
            v.model.setRowCount(0);
            try (Connection c = getConnection()) {
                String sql = "SELECT * FROM Phieuphat WHERE MaPP LIKE ? OR MaPM LIKE ?";
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, "%" + key + "%");
                ps.setString(2, "%" + key + "%");
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    v.model.addRow(new Object[]{
                        rs.getString("MaPP"),
                        rs.getString("MaPM"),
                        rs.getInt("TienPhat")
                    });
                }
            } catch(Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(v, "Lỗi tìm kiếm!");
            }
        }
    }

    // ================= XUẤT FILE CSV =================
    class action_xuatfile implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (v.model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(v, "Không có dữ liệu để xuất!");
                return;
            }
            javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
            fc.setDialogTitle("Chọn nơi lưu file");
            fc.setSelectedFile(new java.io.File("phieuphat.csv"));
            int choose = fc.showSaveDialog(v);
            if (choose != javax.swing.JFileChooser.APPROVE_OPTION) return;
            java.io.File file = fc.getSelectedFile();
            try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file),
                    java.nio.charset.StandardCharsets.UTF_8);
                 BufferedWriter bw = new BufferedWriter(osw)) {
                bw.write("\uFEFF"); // BOM cho Excel
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
                JOptionPane.showMessageDialog(v, "Xuất file phiếu phạt thành công!");
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
