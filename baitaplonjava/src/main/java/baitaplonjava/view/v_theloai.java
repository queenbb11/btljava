package baitaplonjava.view;

import baitaplonjava.model.m_theloai;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class v_theloai extends JFrame {
    // Khai báo thuộc tính giao diện
    public JTextField txtMaTL, txtTenTL, txttimkiem;
    public JButton btnthem, btnsua, btnxoa, btnreset, btntimkiem, btnxuatfile, btnback;
    public JTable table;
    public DefaultTableModel model; // lưu dl cho bảng

    public v_theloai() {
        initUI();
    }

    private void initUI() {
        this.setTitle("Hệ Thống Quản Lý Thể Loại Sách");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(900, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));
        this.getContentPane().setBackground(Color.WHITE);

        // --- 1. TIÊU ĐỀ (NORTH) ---
        JLabel lblTitle = new JLabel("DANH MỤC THỂ LOẠI SÁCH", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        this.add(lblTitle, BorderLayout.NORTH);

        // --- 2. VÙNG ĐIỀU KHIỂN (CENTER) ---
        JPanel mainCenter = new JPanel();
        mainCenter.setLayout(new BoxLayout(mainCenter, BoxLayout.Y_AXIS));
        mainCenter.setBackground(Color.WHITE);
        mainCenter.setBorder(new EmptyBorder(10, 30, 10, 30));

        // ===== Panel Nhập liệu  =====
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        txtMaTL = new JTextField();
        setupInputField(txtMaTL);

        txtTenTL = new JTextField();
        setupInputField(txtTenTL);

        inputPanel.add(createLabel("Mã Thể Loại:"));
        inputPanel.add(txtMaTL);
        inputPanel.add(createLabel("Tên Thể Loại:"));
        inputPanel.add(txtTenTL);

        // ===== Panel Tìm kiếm =====
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 20));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        txttimkiem = new JTextField();
        setupInputField(txttimkiem);
        txttimkiem.setPreferredSize(new Dimension(550, 40));

        btntimkiem = new JButton("Tìm Kiếm");
        btntimkiem.setPreferredSize(new Dimension(120, 40));

        searchPanel.add(txttimkiem);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btntimkiem);

        // ===== Bảng hiển thị =====
        String[] columns = {"Mã Thể Loại", "Tên Thể Loại"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);

        mainCenter.add(inputPanel);
        mainCenter.add(searchPanel);
        mainCenter.add(scrollPane);
        this.add(mainCenter, BorderLayout.CENTER);

        // --- 3. PANEL NÚT CHỨC NĂNG (SOUTH) ---
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        actionPanel.setBackground(new Color(245, 245, 245));

        btnthem    = new JButton("Thêm mới");
        btnsua     = new JButton("Sửa");
        btnxoa     = new JButton("Xóa");
        btnreset   = new JButton("Reset");
        btnxuatfile= new JButton("Xuất File");
        btnback    = new JButton("Quay Lại");

        Dimension btnSize = new Dimension(110, 40);
        btnthem.setPreferredSize(btnSize);
        btnsua.setPreferredSize(btnSize);
        btnxoa.setPreferredSize(btnSize);
        btnreset.setPreferredSize(btnSize);
        btnxuatfile.setPreferredSize(btnSize);
        btnback.setPreferredSize(btnSize);

        actionPanel.add(btnthem);
        actionPanel.add(btnsua);
        actionPanel.add(btnxoa);
        actionPanel.add(btnreset);
        actionPanel.add(btnxuatfile);
        actionPanel.add(btnback);

        this.add(actionPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return lbl;
    }

    private void setupInputField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 210, 210), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(232, 242, 252));
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(0, 35));
    }

    // --- Các Listener  ---
    public void bt_them_action_listenner(ActionListener a)    { btnthem.addActionListener(a); }
    public void bt_sua_action_listenner(ActionListener a)     { btnsua.addActionListener(a); }
    public void bt_xoa_action_listenner(ActionListener a)     { btnxoa.addActionListener(a); }
    public void bt_reset_action_listenner(ActionListener a)   { btnreset.addActionListener(a); }
    public void bt_timkiem_action_listenner(ActionListener a) { btntimkiem.addActionListener(a); }
    public void bt_xuatfile_action_listenner(ActionListener a){ btnxuatfile.addActionListener(a); }
    public void bt_quaylai_action_listenner(ActionListener a) { btnback.addActionListener(a); }

    public m_theloai get_theloai() {
        String MaTL = txtMaTL.getText().trim();
        String TenTL = txtTenTL.getText().trim();
        if (MaTL.isEmpty() || TenTL.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return null;
        }
        return new m_theloai(MaTL, TenTL);
    }
}
