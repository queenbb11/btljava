package baitaplonjava.controller;

import baitaplonjava.model.m_khosach;
import baitaplonjava.view.v_khosach;
import baitaplonjava.view.v_trangchu;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class c_khosach {

    private v_khosach v;
    private v_trangchu home;
    private int selectedRow = -1;

    private final String url = "jdbc:mysql://localhost:3306/baitaplon";
    private final String user = "root";
    private final String pass = "123456789";

    public c_khosach(v_khosach view, JFrame trangchu) {
        this.v = view;
        this.home = (v_trangchu) trangchu;

        v.bt_them(e -> handleThem());
        v.bt_luu(e -> handleLuu());
        v.bt_sua(e -> handleSua());
        v.bt_xoa(e -> handleXoa());
        v.bt_back(e -> handleBack());

        v.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = v.table.getSelectedRow();
                if (selectedRow != -1) {
                    v.txtMaK.setText(v.table.getValueAt(selectedRow, 0).toString());
                    v.cbMaS.setSelectedItem(v.table.getValueAt(selectedRow, 1).toString());
                    v.txtNhap.setText(v.table.getValueAt(selectedRow, 2).toString());
                    v.txtXuat.setText(v.table.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        loadComboSach();
        loadData();
    }

    private void handleThem() {
        m_khosach k = v.get_khosach();
        if (k == null) return;

        v.model.addRow(new Object[]{
                k.getMaK(), k.getMaS(),
                k.getSoluongN(), k.getSoluongX(), k.getSoluongT()
        });
    }

    private void handleLuu() {
        String sql = "INSERT INTO Khosach VALUES (?,?,?,?,?)";
        try (Connection c = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = c.prepareStatement(sql)) {

            for (int i = 0; i < v.model.getRowCount(); i++) {
                ps.setString(1, v.model.getValueAt(i, 0).toString());
                ps.setString(2, v.model.getValueAt(i, 1).toString());
                ps.setInt(3, Integer.parseInt(v.model.getValueAt(i, 2).toString()));
                ps.setInt(4, Integer.parseInt(v.model.getValueAt(i, 3).toString()));
                ps.setInt(5, Integer.parseInt(v.model.getValueAt(i, 4).toString()));
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(v, "Lưu kho thành công!");
            loadData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi lưu kho!");
        }
    }

    private void handleSua() {
        if (selectedRow == -1) return;
        m_khosach k = v.get_khosach();

        String sql = "UPDATE Khosach SET SoluongN=?, SoluongX=?, SoluongT=? WHERE MaK=? AND MaS=?";
        try (Connection c = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, k.getSoluongN());
            ps.setInt(2, k.getSoluongX());
            ps.setInt(3, k.getSoluongT());
            ps.setString(4, k.getMaK());
            ps.setString(5, k.getMaS());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(v, "Sửa thành công!");
            loadData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Lỗi sửa!");
        }
    }

    private void handleXoa() {
        if (selectedRow == -1) return;

        try (Connection c = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = c.prepareStatement(
                     "DELETE FROM Khosach WHERE MaK=? AND MaS=?")) {

            ps.setString(1, v.txtMaK.getText());
            ps.setString(2, v.cbMaS.getSelectedItem().toString());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(v, "Đã xóa!");
            loadData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Không thể xóa!");
        }
    }

    private void loadData() {
        v.model.setRowCount(0);
        try (Connection c = DriverManager.getConnection(url, user, pass);
             ResultSet rs = c.createStatement().executeQuery("SELECT * FROM Khosach")) {

            while (rs.next()) {
                v.model.addRow(new Object[]{
                        rs.getString("MaK"),
                        rs.getString("MaS"),
                        rs.getInt("SoluongN"),
                        rs.getInt("SoluongX"),
                        rs.getInt("SoluongT")
                });
            }
        } catch (Exception e) {}
    }

    private void loadComboSach() {
        try (Connection c = DriverManager.getConnection(url, user, pass);
             ResultSet rs = c.createStatement().executeQuery("SELECT MaS FROM Sach")) {
            while (rs.next()) {
                v.cbMaS.addItem(rs.getString("MaS"));
            }
        } catch (Exception e) {}
    }

    private void handleBack() {
        v.dispose();
        home.setVisible(true);
    }
}
