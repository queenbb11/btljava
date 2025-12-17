/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tduy2
 */
public class view_nhanvien extends JFrame {

    public JTextField txtMaNV, txtTenNV, txtNgaysinhNV, txtGioitinhNV, txtDienthoaiNV, txtEmailNV, txtDiachiNV;
    public JButton btnLuu;
    public JTable table;
    private DefaultTableModel model;

    public view_nhanvien() {
        setTitle("Quản lý Nhân Viên");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== Form nhập dọc =====
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createTitledBorder("Nhập thông tin Nhân Viên"));

        txtMaNV = new JTextField(20);
        txtTenNV = new JTextField(20);
        txtNgaysinhNV = new JTextField(20);
        txtGioitinhNV = new JTextField(20);
        txtDienthoaiNV = new JTextField(20);
        txtEmailNV = new JTextField(20);
        txtDiachiNV = new JTextField(20);

        formPanel.add(taoField("Mã:", txtMaNV));
        formPanel.add(taoField("Họ tên:", txtTenNV));
        formPanel.add(taoField("Ngày sinh:", txtNgaysinhNV));
        formPanel.add(taoField("Giới tính:", txtGioitinhNV));
        formPanel.add(taoField("Điện thoại", txtDienthoaiNV));
        formPanel.add(taoField("Email", txtEmailNV));
        formPanel.add(taoField("Địa chỉ:", txtDiachiNV));

        btnLuu = new JButton("Lưu Nhân Viên");
        JPanel pBtn = new JPanel();
        pBtn.add(btnLuu);
        formPanel.add(pBtn);

        add(formPanel, BorderLayout.NORTH);

        // ===== Bảng hiển thị =====
        model = new DefaultTableModel();
        table = new JTable(model);
        model.setColumnIdentifiers(new String[]{"Mã NV", "Họ tên", "Ngày sinh", "Giới tính", "Điện thoại", "Email",  "Địa chỉ"});
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // Hàm tiện lợi tạo field dọc với label
    private JPanel taoField(String label, JTextField field) {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel(label), BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        return p;
    }

    public void themDong(Object[] row) {
        model.addRow(row);
    }

    public void thongBao(String msg){
        JOptionPane.showMessageDialog(this,msg);
    }
}
