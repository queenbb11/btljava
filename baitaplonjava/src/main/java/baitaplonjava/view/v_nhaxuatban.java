/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.view;

import baitaplonjava.model.m_nhaxuatban;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo
 */
public class v_nhaxuatban extends JFrame {
    public JTextField txtMaNXB, txtTenNXB, txtDienthoai, txtEmail, txtDiachi, txtTimkiem;
    private JButton btnThem, btnSua, btnXoa, btnTimkiem, btnXuatfile, btnReset, btnBack;
    public JTable table;
    public DefaultTableModel model;

    public v_nhaxuatban() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Quản Lý Nhà Xuất Bản");
        setSize(1000, 650); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // ===== TIÊU ĐỀ =====
        JLabel lblTitle = new JLabel("QUẢN LÝ NHÀ XUẤT BẢN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setBounds(340, 10, 350, 40);
        add(lblTitle);

        // ===== LABEL =====
        addLabel("Mã NXB:", 50, 70);
        addLabel("Tên NXB:", 50, 110);
        addLabel("Điện thoại:", 50, 150);
        addLabel("Email:", 50, 190);
        addLabel("Địa chỉ:", 50, 230);

        // ===== TEXTFIELD =====
        txtMaNXB = addTextField(170, 70);
        txtTenNXB = addTextField(170, 110);
        txtDienthoai = addTextField(170, 150);
        txtEmail = addTextField(170, 190);
        txtDiachi = addTextField(170, 230);

        // ===== BUTTON CRUD =====
        btnThem  = addButton("Thêm", 460, 70);
        btnSua   = addButton("Sửa", 460, 115);
        btnXoa   = addButton("Xóa", 460, 160);
        btnReset = addButton("Reset", 460, 205);

        // ===== TÌM KIẾM =====
        JLabel lbSearch = new JLabel("Tìm kiếm:");
        lbSearch.setBounds(50, 290, 80, 30);
        add(lbSearch);

        txtTimkiem = new JTextField();
        txtTimkiem.setBounds(130, 290, 260, 30);
        add(txtTimkiem);

        
        btnTimkiem  = addButton("Tìm",       420, 290);
        btnXuatfile = addButton("Xuất file", 560, 290);
        btnBack     = addButton("Quay lại",  720, 290);

        // ===== TABLE =====
        String[] cols = {"Mã NXB", "Tên NXB", "Điện thoại", "Email", "Địa chỉ"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 340, 900, 240);
        add(sp);

        setVisible(true);
    }

    // ===== TIỆN ÍCH UI =====
    private void addLabel(String text, int x, int y) {
        JLabel lb = new JLabel(text);
        lb.setBounds(x, y, 120, 30);
        add(lb);
    }

    private JTextField addTextField(int x, int y) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 250, 30);
        add(tf);
        return tf;
    }

    private JButton addButton(String text, int x, int y) {
        JButton b = new JButton(text);
        b.setBounds(x, y, 120, 35);
        add(b);
        return b;
    }

    // ===== GET MODEL =====
    public m_nhaxuatban getNXB() {
        String ma = txtMaNXB.getText().trim();
        String ten = txtTenNXB.getText().trim();
        String dt = txtDienthoai.getText().trim();
        String email = txtEmail.getText().trim();
        String dc = txtDiachi.getText().trim();

        if (ma.isEmpty() || ten.isEmpty() || dt.isEmpty()
                || email.isEmpty() || dc.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ thông tin Nhà Xuất Bản!");
            return null;
        }

        return new m_nhaxuatban(ma, ten, dt, email, dc);
    }

    // ===== RESET FORM =====
    public void resetForm() {
        txtMaNXB.setText("");
        txtTenNXB.setText("");
        txtDienthoai.setText("");
        txtEmail.setText("");
        txtDiachi.setText("");
        txtTimkiem.setText("");
        txtMaNXB.setEditable(true);
    }

    // ===== ACTION LISTENER =====
    public void bt_them_action_listenner(ActionListener a) {
        btnThem.addActionListener(a);
    }

    public void bt_sua_action_listenner(ActionListener a) {
        btnSua.addActionListener(a);
    }

    public void bt_xoa_action_listenner(ActionListener a) {
        btnXoa.addActionListener(a);
    }

    public void bt_reset_action_listenner(ActionListener a) {
        btnReset.addActionListener(a);
    }

    public void bt_timkiem_action_listenner(ActionListener a) {
        btnTimkiem.addActionListener(a);
    }

    public void bt_xuatfile_action_listenner(ActionListener a) {
        btnXuatfile.addActionListener(a);
    }

    public void bt_quaylai_action_listenner(ActionListener a) {
        btnBack.addActionListener(a);
    }
}


