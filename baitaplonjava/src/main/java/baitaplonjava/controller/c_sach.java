/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.controller;

import baitaplonjava.model.m_sach;
import baitaplonjava.view.v_sach;
import baitaplonjava.view.v_trangchu;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class c_sach {

    private v_sach v;
    private v_trangchu viewtrangchu;
    private int selectedRow = -1;

    private final String url = "jdbc:mysql://localhost:3306/baitaplon";
    private final String user = "root";
    private final String pass = "123456789";

    public c_sach(v_sach view, JFrame trangchu) {
        this.v = view;
        this.viewtrangchu = (v_trangchu) trangchu;

        // --- Gán sự kiện cho các nút ---
        // Nút Thêm: Hiển thị lên bảng (giống ví dụ nhân viên)
        this.v.bt_them_action_listener(e -> handleThem());
        
        // Nút Lưu: Ghi vào Database
        this.v.bt_luu_action_listener(e -> handleLuu());
        
        this.v.bt_sua_action_listener(e -> handleSua());
        this.v.bt_xoa_action_listener(e -> handleXoa());
        this.v.bt_timkiem_action_listener(e -> handleTimKiem());
        this.v.bt_quaylai_action_listener(e -> handleQuayLai());

        // --- Sự kiện click bảng ---
        this.v.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = v.table.getSelectedRow();
                if (selectedRow != -1) {
                    v.txtMaS.setText(v.table.getValueAt(selectedRow, 0).toString());
                    v.txtTenS.setText(v.table.getValueAt(selectedRow, 1).toString());
                    v.cbMaTL.setSelectedItem(v.table.getValueAt(selectedRow, 2).toString());
                    v.cbMaNXB.setSelectedItem(v.table.getValueAt(selectedRow, 3).toString());
                    v.cbMaTG.setSelectedItem(v.table.getValueAt(selectedRow, 4).toString());
                    v.txtNamXB.setText(v.table.getValueAt(selectedRow, 5).toString());
                    v.txtTinhTrang.setText(v.table.getValueAt(selectedRow, 6).toString());
                    v.txtMoTa.setText(v.table.getValueAt(selectedRow, 7).toString());
                    v.txtMaS.setEditable(false);
                }
            }
        });

        loadComboBoxData();
        loadData();
    }

    // 1. Xử lý nút THÊM (Đưa lên bảng)
    private void handleThem() {
        m_sach s = v.get_sach();
        if (s != null) {
            v.model.addRow(new Object[]{
                s.getMaS(), s.getTenS(), s.getMaTL(), s.getMaNXB(), 
                s.getMaTG(), s.getNamxuatban(), s.getTinhtrang(), s.getMota()
            });
            JOptionPane.showMessageDialog(v, "Đã thêm vào danh sách tạm!");
        }
    }

    // 2. Xử lý nút LƯU (Ghi vào CSDL)
    private void handleLuu() {
        m_sach s = v.get_sach();
        if (s == null) return;

        String sql = "INSERT INTO Sach (MaS, TenS, MaTL, MaNXB, MaTG, Namxuatban, Tinhtrang, Mota) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, s.getMaS());
            ps.setString(2, s.getTenS());
            ps.setString(3, s.getMaTL());
            ps.setString(4, s.getMaNXB());
            ps.setString(5, s.getMaTG());
            ps.setInt(6, s.getNamxuatban());
            ps.setString(7, s.getTinhtrang());
            ps.setString(8, s.getMota());

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(v, "Lưu vào CSDL thành công!");
                loadData();
                clearFields();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Lỗi: Mã sách đã tồn tại!");
        }
    }

    // --- Các hàm hỗ trợ khác giữ nguyên logic chuẩn ---

    public void loadData() {
        v.model.setRowCount(0);
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Sach")) {
            while (rs.next()) {
                v.model.addRow(new Object[]{
                    rs.getString("MaS"), rs.getString("TenS"), rs.getString("MaTL"), 
                    rs.getString("MaNXB"), rs.getString("MaTG"), rs.getInt("Namxuatban"), 
                    rs.getString("Tinhtrang"), rs.getString("Mota")
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void loadComboBoxData() {
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            v.cbMaTL.removeAllItems();
            v.cbMaTG.removeAllItems();
            v.cbMaNXB.removeAllItems();
            
            ResultSet rsTL = conn.createStatement().executeQuery("SELECT MaTL FROM Theloai");
            while (rsTL.next()) v.cbMaTL.addItem(rsTL.getString("MaTL"));

            ResultSet rsTG = conn.createStatement().executeQuery("SELECT MaTG FROM Tacgia");
            while (rsTG.next()) v.cbMaTG.addItem(rsTG.getString("MaTG"));

            ResultSet rsNXB = conn.createStatement().executeQuery("SELECT MaNXB FROM Nhaxuatban");
            while (rsNXB.next()) v.cbMaNXB.addItem(rsNXB.getString("MaNXB"));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void handleSua() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(v, "Chọn sách cần sửa!");
            return;
        }
        m_sach s = v.get_sach();
        String sql = "UPDATE Sach SET TenS=?, MaTL=?, MaNXB=?, MaTG=?, Namxuatban=?, Tinhtrang=?, Mota=? WHERE MaS=?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getTenS());
            ps.setString(2, s.getMaTL());
            ps.setString(3, s.getMaNXB());
            ps.setString(4, s.getMaTG());
            ps.setInt(5, s.getNamxuatban());
            ps.setString(6, s.getTinhtrang());
            ps.setString(7, s.getMota());
            ps.setString(8, s.getMaS());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(v, "Sửa thành công!");
            loadData();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void handleXoa() {
        if (selectedRow == -1) return;
        int confirm = JOptionPane.showConfirmDialog(v, "Xóa sách này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM Sach WHERE MaS=?")) {
                ps.setString(1, v.txtMaS.getText());
                ps.executeUpdate();
                loadData();
                clearFields();
            } catch (Exception e) { JOptionPane.showMessageDialog(v, "Không thể xóa!"); }
        }
    }

    private void handleTimKiem() {
        String kw = v.txttimkiem.getText().trim();
        v.model.setRowCount(0);
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Sach WHERE TenS LIKE ? OR MaS LIKE ?")) {
            ps.setString(1, "%" + kw + "%");
            ps.setString(2, "%" + kw + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                v.model.addRow(new Object[]{ rs.getString("MaS"), rs.getString("TenS"), rs.getString("MaTL"), rs.getString("MaNXB"), rs.getString("MaTG"), rs.getInt("Namxuatban"), rs.getString("Tinhtrang"), rs.getString("Mota") });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void handleQuayLai() {
        v.dispose();
        if (viewtrangchu != null) viewtrangchu.setVisible(true);
    }

    private void clearFields() {
        v.txtMaS.setText("");
        v.txtTenS.setText("");
        v.txtNamXB.setText("");
        v.txtTinhTrang.setText("");
        v.txtMoTa.setText("");
        v.txtMaS.setEditable(true);
        selectedRow = -1;
    }
}