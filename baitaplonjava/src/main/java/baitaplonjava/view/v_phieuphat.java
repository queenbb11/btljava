
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.view;



import baitaplonjava.model.m_phieuphat;
import java.awt.event.ActionListener;
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
public class v_phieuphat extends JFrame {
    public JTextField txtMaPP, txtTienPhat, txtTimkiem;
    public JComboBox<String> cbMaPM, cbMaNV;
    public JTable table;
    public DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnReset, btnTimkiem, btnBack, btnNhapFile, btnXuatFile;

    public v_phieuphat() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Quản Lý Phiếu Phạt");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lbMaPP = new JLabel("Mã phiếu phạt:");
        lbMaPP.setBounds(50, 30, 120, 30);
        add(lbMaPP);

        JLabel lbMaPM = new JLabel("Mã phiếu mượn:");
        lbMaPM.setBounds(50, 70, 120, 30);
        add(lbMaPM);

        JLabel lbTienPhat = new JLabel("Tiền phạt:");
        lbTienPhat.setBounds(50, 110, 120, 30);
        add(lbTienPhat);
        
        JLabel lbMaNV = new JLabel("Người lập:");
        lbMaNV.setBounds(50, 150, 120, 30);
        add(lbMaNV);

        JLabel lbTimkiem = new JLabel("Tìm kiếm:");
        lbTimkiem.setBounds(50, 190, 120, 30);
        add(lbTimkiem);

        txtMaPP = new JTextField();
        txtMaPP.setBounds(170, 30, 200, 30);
        add(txtMaPP);

        cbMaPM = new JComboBox<>();
        cbMaPM.setBounds(170, 70, 200, 30);
        add(cbMaPM);

        txtTienPhat = new JTextField();
        txtTienPhat.setBounds(170, 110, 200, 30);
        txtTienPhat.setEditable(false);
        add(txtTienPhat);
        
        cbMaNV = new JComboBox<>();
        cbMaNV.setBounds(170, 150, 200, 30);
        add(cbMaNV);

        txtTimkiem = new JTextField();
        txtTimkiem.setBounds(170, 190, 200, 30);
        add(txtTimkiem);

        btnThem = new JButton("Thêm");
        btnThem.setBounds(400, 30, 100, 35);
        add(btnThem);

        btnSua = new JButton("Sửa");
        btnSua.setBounds(400, 75, 100, 35);
        add(btnSua);

        btnXoa = new JButton("Xóa");
        btnXoa.setBounds(400, 120, 100, 35);
        add(btnXoa);

        btnReset = new JButton("Reset");
        btnReset.setBounds(520, 190, 100, 35);
        add(btnReset);

        btnTimkiem = new JButton("Tìm");
        btnTimkiem.setBounds(400, 190, 100, 35);
        add(btnTimkiem);

        btnXuatFile = new JButton("Xuất File");
        btnXuatFile.setBounds(50, 500, 100, 35);
        add(btnXuatFile);

        btnNhapFile = new JButton("Nhập File");
        btnNhapFile.setBounds(170, 500, 100, 35);
        add(btnNhapFile);

        btnBack = new JButton("Quay lại");
        btnBack.setBounds(600, 500, 100, 35);
        add(btnBack);

        String[] columns = {"Mã PP", "Mã PM","Người lập", "Tiền Phạt"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 250, 700, 250);
        add(sp);

        setVisible(true);
    }

    public m_phieuphat getPhieuPhat() {
        String maPP = txtMaPP.getText().trim();
        String maPM = (String) cbMaPM.getSelectedItem();
        String maNV = (String) cbMaNV.getSelectedItem(); 
        int tienPhat = 0;
        try {
            tienPhat = Integer.parseInt(txtTienPhat.getText().trim());
        } catch (Exception e) {}
        if (maPP.isEmpty() || maPM == null || maNV == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return null;
        }
        return new m_phieuphat(maPP, maPM, maNV, tienPhat);
    }

    public void resetForm() {
        txtMaPP.setText("");
        txtTienPhat.setText("");
        txtTimkiem.setText("");
        cbMaPM.setSelectedIndex(-1);
        cbMaNV.setSelectedIndex(-1);
        txtMaPP.setEditable(true); 
    }

    public void bt_them_action_listener(ActionListener a) { btnThem.addActionListener(a); }
    public void bt_sua_action_listener(ActionListener a) { btnSua.addActionListener(a); }
    public void bt_xoa_action_listener(ActionListener a) { btnXoa.addActionListener(a); }
    public void bt_reset_action_listener(ActionListener a) { btnReset.addActionListener(a); }
    public void bt_timkiem_action_listener(ActionListener a) { btnTimkiem.addActionListener(a); }
    public void bt_quaylai_action_listener(ActionListener a) { btnBack.addActionListener(a); }
    public void bt_xuatfile_action_listener(ActionListener a) { btnXuatFile.addActionListener(a); }
    public void bt_nhapfile_action_listener(ActionListener a) { btnNhapFile.addActionListener(a); }
}