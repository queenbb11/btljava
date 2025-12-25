/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.view;

import baitaplonjava.model.m_tacgia;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class v_tacgia extends JFrame {
    public JTextField txtMaTG, txtTenTG, txtNgaysinh, txtGioitinh,
            txtDienthoai, txtDiachi, txtTimkiem;

    private JButton btnThem, btnSua, btnXoa,
            btnReset, btnTimkiem, btnXuatfile, btnBack;

    public JTable table;
    public DefaultTableModel model;

    public v_tacgia() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Quản Lý Tác Giả");
        setSize(1000, 660);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // ===== TIÊU ĐỀ =====
        JLabel lblTitle = new JLabel("QUẢN LÝ TÁC GIẢ");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setBounds(380, 10, 300, 40);
        add(lblTitle);

        // ===== LABEL + TEXTFIELD =====
        int y = 70;

        addLabel("Mã TG:", 50, y);
        txtMaTG = addTextField(170, y);

        addLabel("Tên TG:", 50, y += 40);
        txtTenTG = addTextField(170, y);

        addLabel("Ngày sinh:", 50, y += 40);
        txtNgaysinh = addTextField(170, y);
        txtNgaysinh.setToolTipText("dd/MM/yyyy (vd: 15/08/2001)");

        addLabel("Giới tính:", 50, y += 40);
        txtGioitinh = addTextField(170, y);

        addLabel("Điện thoại:", 50, y += 40);
        txtDienthoai = addTextField(170, y);

        addLabel("Địa chỉ:", 50, y += 40);
        txtDiachi = addTextField(170, y);

        // ===== BUTTON CRUD =====
        int bx = 480, by = 70;
        btnThem = addButton("Thêm", bx, by);
        btnSua = addButton("Sửa", bx, by += 45);
        btnXoa = addButton("Xóa", bx, by += 45);
        btnReset = addButton("Reset", bx, by += 45);

        // ===== TÌM KIẾM + XUẤT FILE =====
        JLabel lbSearch = new JLabel("Tìm kiếm:");
        lbSearch.setBounds(50, 360, 80, 30);
        add(lbSearch);

        txtTimkiem = new JTextField();
        txtTimkiem.setBounds(130, 360, 300, 30);
        add(txtTimkiem);

        btnTimkiem = addButton("Tìm", 440, 360);
        btnXuatfile = addButton("Xuất file", 560, 360);
        btnBack = addButton("Quay lại", 700, 360);

        // ===== TABLE =====
        String[] cols = {
            "Mã TG", "Tên TG", "Ngày sinh",
            "Giới tính", "Điện thoại", "Địa chỉ"
        };
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 410, 900, 200);
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
        tf.setBounds(x, y, 260, 30);
        add(tf);
        return tf;
    }

    private JButton addButton(String text, int x, int y) {
        JButton b = new JButton(text);
        b.setBounds(x, y, 110, 35);
        add(b);
        return b;
    }

    // ===== GET MODEL + VALIDATE =====
    public m_tacgia getTacgia() {

        String ma = txtMaTG.getText().trim();
        String ten = txtTenTG.getText().trim();
        String nsText = txtNgaysinh.getText().trim();

        if (ma.isEmpty() || ten.isEmpty() || nsText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập Mã, Tên và Ngày sinh!");
            return null;
        }

        Date ns;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            ns = sdf.parse(nsText);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Ngày sinh không hợp lệ!\nĐịnh dạng: dd/MM/yyyy");
            return null;
        }

        return new m_tacgia(
                ma,
                ten,
                ns,
                txtGioitinh.getText().trim(),
                txtDienthoai.getText().trim(),
                txtDiachi.getText().trim()
        );
    }

    // ===== RESET =====
    public void resetForm() {
        txtMaTG.setText("");
        txtTenTG.setText("");
        txtNgaysinh.setText("");
        txtGioitinh.setText("");
        txtDienthoai.setText("");
        txtDiachi.setText("");
        txtTimkiem.setText("");
        txtMaTG.setEditable(true);
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
