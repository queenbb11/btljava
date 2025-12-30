/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.view;


import baitaplonjava.model.m_phieutra;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SCN
 */
public class v_phieutra extends JFrame {
    public JTextField txtMaPT, txtTimkiem;
    public JComboBox<String> cbMaPM;
    public JDateChooser dcNgayTra;
    private JButton btnThem, btnSua, btnXoa, btnReset, btnTimkiem, btnBack, btnNhapFile, btnXuatFile;
    public JTable table;
    public DefaultTableModel model;

    public v_phieutra() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Quản Lý Phiếu Trả");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lbMaPT = new JLabel("Mã phiếu trả:");
        lbMaPT.setBounds(50, 30, 120, 30);
        add(lbMaPT);

        JLabel lbMaPM = new JLabel("Mã phiếu mượn:");
        lbMaPM.setBounds(50, 70, 120, 30);
        add(lbMaPM);

        JLabel lbNgayTra = new JLabel("Ngày trả:");
        lbNgayTra.setBounds(50, 110, 120, 30);
        add(lbNgayTra);

        JLabel lbTimkiem = new JLabel("Tìm kiếm:");
        lbTimkiem.setBounds(50, 165, 120, 30);
        add(lbTimkiem);

        txtMaPT = new JTextField();
        txtMaPT.setBounds(170, 30, 260, 30);
        add(txtMaPT);

        cbMaPM = new JComboBox<>();
        cbMaPM.setBounds(170, 70, 260, 30);
        add(cbMaPM);

        dcNgayTra = new JDateChooser();
        dcNgayTra.setBounds(170, 110, 260, 30);
        dcNgayTra.setDateFormatString("dd/MM/yyyy");
        add(dcNgayTra);

        txtTimkiem = new JTextField();
        txtTimkiem.setBounds(170, 165, 260, 30);
        add(txtTimkiem);

        // ===== BUTTONS =====
        btnThem = new JButton("Thêm");
        btnThem.setBounds(450, 30, 100, 35);
        add(btnThem);

        btnSua = new JButton("Sửa");
        btnSua.setBounds(450, 75, 100, 35);
        add(btnSua);

        btnXoa = new JButton("Xóa");
        btnXoa.setBounds(450, 120, 100, 35);
        add(btnXoa);

        btnReset = new JButton("Reset");
        btnReset.setBounds(450, 210, 100, 35);
        add(btnReset);

        btnTimkiem = new JButton("Tìm");
        btnTimkiem.setBounds(450, 165, 100, 35);
        add(btnTimkiem);

        btnXuatFile = new JButton("Xuất File");
        btnXuatFile.setBounds(50, 450, 100, 35);
        add(btnXuatFile);

        btnNhapFile = new JButton("Nhập File");
        btnNhapFile.setBounds(170, 450, 100, 35);
        add(btnNhapFile);

        btnBack = new JButton("Quay lại");
        btnBack.setBounds(650, 450, 100, 35);
        add(btnBack);

        // ===== TABLE =====
        String[] columns = {"Mã PT", "Mã PM", "Ngày Trả"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 250, 700, 200);
        add(sp);

        setVisible(true);
    }

    // ===== LẤY DỮ LIỆU PHIẾU TRẢ =====
    public m_phieutra getPhieuTra() {
        String maPT = txtMaPT.getText().trim();
        String maPM = (String) cbMaPM.getSelectedItem();
        Date ngayTra = dcNgayTra.getDate();

        if (maPT.isEmpty() || maPM == null || ngayTra == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return null;
        }
        return new m_phieutra(maPT, maPM, ngayTra);
    }

    // ===== RESET FORM =====
    public void resetForm() {
        txtMaPT.setText("");
        txtTimkiem.setText("");
        cbMaPM.setSelectedIndex(-1);
        dcNgayTra.setDate(null);
        txtMaPT.setEditable(true);
    }

    // ===== ACTION LISTENER =====
    public void bt_them_action_listener(ActionListener a) { btnThem.addActionListener(a); }
    public void bt_sua_action_listener(ActionListener a) { btnSua.addActionListener(a); }
    public void bt_xoa_action_listener(ActionListener a) { btnXoa.addActionListener(a); }
    public void bt_reset_action_listener(ActionListener a) { btnReset.addActionListener(a); }
    public void bt_timkiem_action_listener(ActionListener a) { btnTimkiem.addActionListener(a); }
    public void bt_quaylai_action_listener(ActionListener a) { btnBack.addActionListener(a); }
    public void bt_xuatfile_action_listener(ActionListener a) { btnXuatFile.addActionListener(a); }
    public void bt_nhapfile_action_listener(ActionListener a) { btnNhapFile.addActionListener(a); }
}