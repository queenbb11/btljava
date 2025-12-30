/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.view;



import baitaplonjava.model.m_phieumuon;
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
public class v_phieumuon extends JFrame {
    public JTextField txtMaPM, txtTimkiem;
    public JComboBox<String> cbMaDG, cbMaS;
    public JDateChooser dcNgayMuon, dcHanTra;
    private JButton btnThem, btnSua, btnXoa, btnReset, btnTimkiem, btnBack, btnXuatFile, btnNhapFile;
    public JTable table;
    public DefaultTableModel model;

    public v_phieumuon() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Quản Lý Phiếu Mượn");
        setSize(1000, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // ===== LABELS =====
        JLabel lbMaPM = new JLabel("Mã phiếu mượn:");
        lbMaPM.setBounds(50, 30, 120, 30);
        add(lbMaPM);

        JLabel lbMaDG = new JLabel("Mã độc giả:");
        lbMaDG.setBounds(50, 70, 120, 30);
        add(lbMaDG);

        JLabel lbMaS = new JLabel("Mã sách:");
        lbMaS.setBounds(50, 110, 120, 30);
        add(lbMaS);

        JLabel lbNgayMuon = new JLabel("Ngày mượn:");
        lbNgayMuon.setBounds(50, 150, 120, 30);
        add(lbNgayMuon);

        JLabel lbHanTra = new JLabel("Hạn trả:");
        lbHanTra.setBounds(50, 190, 120, 30);
        add(lbHanTra);

        JLabel lbTimkiem = new JLabel("Tìm kiếm:");
        lbTimkiem.setBounds(50, 250, 120, 30);
        add(lbTimkiem);

        // ===== INPUTS =====
        txtMaPM = new JTextField();
        txtMaPM.setBounds(170, 30, 260, 30);
        add(txtMaPM);

        cbMaDG = new JComboBox<>();
        cbMaDG.setBounds(170, 70, 260, 30);
        add(cbMaDG);

        cbMaS = new JComboBox<>();
        cbMaS.setBounds(170, 110, 260, 30);
        add(cbMaS);

        dcNgayMuon = new JDateChooser();
        dcNgayMuon.setBounds(170, 150, 260, 30);
        dcNgayMuon.setDateFormatString("dd/MM/yyyy");
        add(dcNgayMuon);

        dcHanTra = new JDateChooser();
        dcHanTra.setBounds(170, 190, 260, 30);
        dcHanTra.setDateFormatString("dd/MM/yyyy");
        add(dcHanTra);

        txtTimkiem = new JTextField();
        txtTimkiem.setBounds(170, 250, 260, 30);
        add(txtTimkiem);

        // ===== BUTTONS =====
        btnThem = new JButton("Thêm");
        btnThem.setBounds(480, 30, 110, 35);
        add(btnThem);

        btnSua = new JButton("Sửa");
        btnSua.setBounds(480, 75, 110, 35);
        add(btnSua);

        btnXoa = new JButton("Xóa");
        btnXoa.setBounds(480, 120, 110, 35);
        add(btnXoa);

        btnReset = new JButton("Reset");
        btnReset.setBounds(480, 165, 110, 35);
        add(btnReset);

        btnTimkiem = new JButton("Tìm");
        btnTimkiem.setBounds(480, 250, 110, 35);
        add(btnTimkiem);
        
        // ===== Nút Xuất / Nhập File =====
        btnXuatFile = new JButton("Xuất File");
        btnXuatFile.setBounds(50, 570, 100, 35);
        add(btnXuatFile);

        btnNhapFile = new JButton("Nhập File");
        btnNhapFile.setBounds(190, 570, 100, 35);
        add(btnNhapFile);
        
        btnBack = new JButton("Quay lại");
        btnBack.setBounds(850, 570, 100, 35);
        add(btnBack);

        // ===== TABLE =====
        String[] columns = {"Mã PM", "Mã ĐG", "Mã Sách", "Ngày Mượn", "Hạn Trả"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 300, 900, 250);
        add(sp);

        setVisible(true);
    }

    // ===== LẤY DỮ LIỆU PHIẾU MƯỢN =====
    public m_phieumuon getPhieuMuon() {
        String maPM = txtMaPM.getText().trim();
        String maDG = (String) cbMaDG.getSelectedItem();
        String maS = (String) cbMaS.getSelectedItem();
        Date ngayMuon = dcNgayMuon.getDate();
        Date hanTra = dcHanTra.getDate();

        if (maPM.isEmpty() || maDG == null || maS == null || ngayMuon == null || hanTra == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return null;
        }

        if (hanTra.before(ngayMuon)) {
            JOptionPane.showMessageDialog(this, "Hạn trả phải sau ngày mượn!");
            return null;
        }

        return new m_phieumuon(maPM, maDG, maS, ngayMuon, hanTra);
    }

    // ===== RESET FORM =====
    public void resetForm() {
        txtMaPM.setText("");
        txtTimkiem.setText("");
        cbMaDG.setSelectedIndex(-1);
        cbMaS.setSelectedIndex(-1);
        dcNgayMuon.setDate(null);
        dcHanTra.setDate(null);
        txtMaPM.setEditable(true);
    }

    // ===== ACTION LISTENER =====
    public void bt_them_action_listenner(ActionListener a) { btnThem.addActionListener(a); }
    public void bt_sua_action_listenner(ActionListener a) { btnSua.addActionListener(a); }
    public void bt_xoa_action_listenner(ActionListener a) { btnXoa.addActionListener(a); }
    public void bt_reset_action_listenner(ActionListener a) { btnReset.addActionListener(a); }
    public void bt_timkiem_action_listenner(ActionListener a) { btnTimkiem.addActionListener(a); }
    public void bt_quaylai_action_listenner(ActionListener a) { btnBack.addActionListener(a); }
    public void bt_xuatfile_action_listenner(ActionListener a) { btnXuatFile.addActionListener(a); }
}