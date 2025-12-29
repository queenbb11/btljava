package baitaplonjava.view;

import baitaplonjava.model.m_trangthaisach;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class v_trangthaisach extends JFrame {

    public JTextField txtMaS, txtTenTrangThai, txttimkiem;
    public JTextArea txtMoTa;
    public JButton btnthem, btnsua, btnxoa, btnreset, btnxuatfile, btntimkiem, btnback;
    public JTable table;
    public DefaultTableModel model;

    public v_trangthaisach() {
        initUI();
    }

    private void initUI() {
        this.setTitle("Quản Lý Trạng Thái Sách");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(1100, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));
        this.getContentPane().setBackground(Color.WHITE);

        // ===== 1) TITLE =====
        JLabel lblTitle = new JLabel("QUẢN LÝ TRẠNG THÁI SÁCH", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        this.add(lblTitle, BorderLayout.NORTH);

        // ===== 2) CENTER =====
        JPanel mainCenter = new JPanel();
        mainCenter.setLayout(new BoxLayout(mainCenter, BoxLayout.Y_AXIS));
        mainCenter.setBackground(Color.WHITE);
        mainCenter.setBorder(new EmptyBorder(10, 30, 10, 30));

        // Panel nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        txtMaS = new JTextField(); setupInputField(txtMaS);
        txtTenTrangThai = new JTextField(); setupInputField(txtTenTrangThai);
        txtMoTa = new JTextArea(); 
        txtMoTa.setBorder(new LineBorder(new Color(210, 210, 210)));
        txtMoTa.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        inputPanel.add(createLabel("Mã Sách:"));
        inputPanel.add(txtMaS);
        inputPanel.add(createLabel("Tên Trạng Thái:"));
        inputPanel.add(txtTenTrangThai);
        inputPanel.add(createLabel("Mô Tả:"));
        inputPanel.add(new JScrollPane(txtMoTa)); 
        inputPanel.add(new JLabel("")); 

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 20));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        txttimkiem = new JTextField();
        setupInputField(txttimkiem);
        txttimkiem.setPreferredSize(new Dimension(700, 40));

        btntimkiem = new JButton("Tìm Kiếm");
        styleButton(btntimkiem, new Color(52, 152, 219));
        btntimkiem.setPreferredSize(new Dimension(120, 40));

        searchPanel.add(txttimkiem);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btntimkiem);

        // Bảng hiển thị
        String[] cols = {"Mã Sách", "Tên Trạng Thái", "Mô Tả"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(230, 230, 230)));

        mainCenter.add(inputPanel);
        mainCenter.add(searchPanel);
        mainCenter.add(scrollPane);

        this.add(mainCenter, BorderLayout.CENTER);

        // ===== 3) SOUTH BUTTONS =====
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        actionPanel.setBackground(new Color(245, 245, 245));

        btnthem = new JButton("Thêm"); styleButton(btnthem, new Color(46, 204, 113));
        btnsua  = new JButton("Sửa");  styleButton(btnsua, new Color(241, 196, 15));
        btnxoa  = new JButton("Xóa");  styleButton(btnxoa, new Color(231, 76, 60));
        btnreset = new JButton("Reset"); styleButton(btnreset, new Color(149, 165, 166));
        btnxuatfile = new JButton("Xuất File"); styleButton(btnxuatfile, new Color(155, 89, 182));
        btnback = new JButton("Quay Lại"); styleButton(btnback, new Color(52, 73, 94));

        actionPanel.add(btnthem);
        actionPanel.add(btnsua);
        actionPanel.add(btnxoa);
        actionPanel.add(btnreset);
        actionPanel.add(btnxuatfile);
        actionPanel.add(btnback);

        this.add(actionPanel, BorderLayout.SOUTH);
    }

    // --- Helper Methods ---
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

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { button.setBackground(bgColor.darker()); }
            @Override public void mouseExited(MouseEvent e) { button.setBackground(bgColor); }
        });
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(240, 240, 240));
        header.setPreferredSize(new Dimension(0, 35));
    }

    // --- Listener Methods (Updated to match sample names) ---
    public void bt_them_action_listenner(ActionListener a) { btnthem.addActionListener(a); }
    public void bt_sua_action_listenner(ActionListener a)  { btnsua.addActionListener(a); }
    public void bt_xoa_action_listenner(ActionListener a)  { btnxoa.addActionListener(a); }
    public void bt_reset_action_listenner(ActionListener a) { btnreset.addActionListener(a); }
    public void bt_timkiem_action_listenner(ActionListener a) { btntimkiem.addActionListener(a); }
    public void bt_xuatfile_action_listenner(ActionListener a) { btnxuatfile.addActionListener(a); }
    public void bt_quaylai_action_listenner(ActionListener a) { btnback.addActionListener(a); }

    // ===== Reset Form =====
    public void resetForm() {
        txtMaS.setText("");
        txtTenTrangThai.setText("");
        txtMoTa.setText("");
        txtMaS.setEditable(true);
        txtMaS.requestFocus();
    }

    // ===== Lấy đối tượng Trạng Thái Sách =====
    public m_trangthaisach get_trangthaisach() {
        String mas = txtMaS.getText().trim();
        String tentt = txtTenTrangThai.getText().trim();
        String mota = txtMoTa.getText().trim();

        if (mas.isEmpty() || tentt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã sách và Tên trạng thái không được để trống!");
            return null;
        }
        return new m_trangthaisach(mas, tentt, mota);
    }
}