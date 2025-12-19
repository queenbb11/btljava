
package baitaplonjava.view;

import baitaplonjava.model.model_theloai;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class view_theloai extends JFrame {

    public JTextField txtmatheloai, txttentheloai, txttimkiem;
    public JButton btnthem, btnsua, btnxoa, btnluu, btntimkiem, btnxuatfile, btnback;
    public JTable table;
    public DefaultTableModel model;

    public view_theloai() {
        initUI();
    }

    private void initUI() {
        this.setTitle("Hệ Thống Quản Lý Thể Loại Sách");
        this.setSize(900, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));
        this.getContentPane().setBackground(Color.WHITE);

        // --- 1. TIÊU ĐỀ (NORTH) ---
        JLabel lblTitle = new JLabel("DANH MỤC THỂ LOẠI SÁCH", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        this.add(lblTitle, BorderLayout.NORTH);

        // --- 2. VÙNG ĐIỀU KHIỂN (CENTER) ---
        JPanel mainCenter = new JPanel();
        mainCenter.setLayout(new BoxLayout(mainCenter, BoxLayout.Y_AXIS));
        mainCenter.setBackground(Color.WHITE);
        mainCenter.setBorder(new EmptyBorder(10, 30, 10, 30));

        // Panel Nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 20, 15));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        txtmatheloai = new JTextField(); setupInputField(txtmatheloai, "Mã thể loại");
        txttentheloai = new JTextField(); setupInputField(txttentheloai, "Tên thể loại");

        inputPanel.add(createLabelPanel("Mã Thể Loại:"));
        inputPanel.add(txtmatheloai);
        inputPanel.add(createLabelPanel("Tên Thể Loại:"));
        inputPanel.add(txttentheloai);

        // Panel Tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 20));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        txttimkiem = new JTextField();
        setupInputField(txttimkiem, "Nhập mã hoặc tên cần tìm...");
        txttimkiem.setPreferredSize(new Dimension(550, 40));
        
        btntimkiem = new JButton("Tìm Kiếm");
        styleButton(btntimkiem, new Color(52, 152, 219)); // Màu xanh dương
        btntimkiem.setPreferredSize(new Dimension(120, 40));

        searchPanel.add(txttimkiem);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btntimkiem);

        // --- Bảng hiển thị ---
        String[] columns = {"Mã Thể Loại", "Tên Thể Loại"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(230, 230, 230)));

        mainCenter.add(inputPanel);
        mainCenter.add(searchPanel);
        mainCenter.add(scrollPane);
        this.add(mainCenter, BorderLayout.CENTER);

        // --- 3. PANEL NÚT CHỨC NĂNG (SOUTH) ---
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        actionPanel.setBackground(new Color(245, 245, 245));

        btnthem = new JButton("Thêm"); styleButton(btnthem, new Color(46, 204, 113)); // Xanh lá
        btnsua = new JButton("Sửa"); styleButton(btnsua, new Color(241, 196, 15)); // Vàng
        btnxoa = new JButton("Xóa"); styleButton(btnxoa, new Color(231, 76, 60)); // Đỏ
        btnluu = new JButton("Lưu"); styleButton(btnluu, new Color(155, 89, 182)); // Tím
        btnxuatfile = new JButton("Xuất File"); styleButton(btnxuatfile, new Color(149, 165, 166)); // Xám
        btnback = new JButton("Quay Lại"); styleButton(btnback, new Color(52, 73, 94)); // Xanh đen

        actionPanel.add(btnthem);
        actionPanel.add(btnsua);
        actionPanel.add(btnxoa);
        actionPanel.add(btnluu);
        actionPanel.add(btnxuatfile);
        actionPanel.add(btnback);

        this.add(actionPanel, BorderLayout.SOUTH);
    }

    private void setupInputField(JTextField field, String placeholder) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(210, 210, 210), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
    }

    private JPanel createLabelPanel(String text) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        p.add(lbl);
        return p;
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(110, 40));
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { button.setBackground(bgColor.darker()); }
            @Override
            public void mouseExited(MouseEvent e) { button.setBackground(bgColor); }
        });
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(232, 242, 252));
        table.setSelectionForeground(Color.BLACK);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(240, 240, 240));
        header.setPreferredSize(new Dimension(0, 35));
    }

    // --- Các Listener giữ nguyên như code của bạn ---
    public void bt_them_action_listenner(ActionListener a) { btnthem.addActionListener(a); }
    public void bt_sua_action_listenner(ActionListener a) { btnsua.addActionListener(a); }
    public void bt_xoa_action_listenner(ActionListener a) { btnxoa.addActionListener(a); }
    public void bt_luu_action_listenner(ActionListener a) { btnluu.addActionListener(a); }
    public void bt_timkiem_action_listenner(ActionListener a) { btntimkiem.addActionListener(a); }
    public void bt_xuatfile_action_listenner(ActionListener a) { btnxuatfile.addActionListener(a); }
    public void bt_quaylai_action_listenner(ActionListener a) { btnback.addActionListener(a); }

    public model_theloai get_theloai() {
        String matheloai = txtmatheloai.getText().trim();
        String tentheloai = txttentheloai.getText().trim();
        if (matheloai.isEmpty() || tentheloai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return null;
        }
        return new model_theloai(matheloai, tentheloai);
    }
}