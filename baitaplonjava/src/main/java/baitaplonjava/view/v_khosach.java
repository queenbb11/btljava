package baitaplonjava.view;

import baitaplonjava.model.m_khosach;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class v_khosach extends JFrame {

    public JTextField txtMaK, txtNhap, txtXuat, txttimkiem;
    public JComboBox<String> cbMaS;
    public JButton btnthem, btnsua, btnxoa, btnluu, btnxuatfile, btntimkiem, btnback;
    public JTable table;
    public DefaultTableModel model;

    public v_khosach() {
        initUI();
    }

    private void initUI() {
        this.setTitle("Hệ Thống Quản Lý Kho Sách");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(1100, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));
        this.getContentPane().setBackground(Color.WHITE);

        // ===== TITLE =====
        JLabel lblTitle = new JLabel("QUẢN LÝ KHO SÁCH", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        this.add(lblTitle, BorderLayout.NORTH);

        // ===== CENTER =====
        JPanel mainCenter = new JPanel();
        mainCenter.setLayout(new BoxLayout(mainCenter, BoxLayout.Y_AXIS));
        mainCenter.setBackground(Color.WHITE);
        mainCenter.setBorder(new EmptyBorder(10, 30, 10, 30));

        // Input
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        txtMaK = new JTextField(); setupInputField(txtMaK);
        cbMaS = new JComboBox<>();
        txtNhap = new JTextField(); setupInputField(txtNhap);
        txtXuat = new JTextField(); setupInputField(txtXuat);

        inputPanel.add(createLabel("Mã Kho:"));
        inputPanel.add(txtMaK);
        inputPanel.add(createLabel("Mã Sách:"));
        inputPanel.add(cbMaS);

        inputPanel.add(createLabel("Số lượng nhập:"));
        inputPanel.add(txtNhap);
        inputPanel.add(createLabel("Số lượng xuất:"));
        inputPanel.add(txtXuat);

        // Search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 20));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        txttimkiem = new JTextField();
        setupInputField(txttimkiem);
        txttimkiem.setPreferredSize(new Dimension(750, 40));

        btntimkiem = new JButton("Tìm Kiếm");
        styleButton(btntimkiem, new Color(52, 152, 219));

        searchPanel.add(txttimkiem);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btntimkiem);

        // Table
        String[] cols = {"Mã Kho", "Mã Sách", "Nhập", "Xuất", "Tồn"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(230, 230, 230)));

        mainCenter.add(inputPanel);
        mainCenter.add(searchPanel);
        mainCenter.add(scrollPane);

        this.add(mainCenter, BorderLayout.CENTER);

        // ===== BOTTOM BUTTONS =====
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        actionPanel.setBackground(new Color(245, 245, 245));

        btnthem     = new JButton("Thêm");      styleButton(btnthem,     new Color(46, 204, 113));
        btnsua      = new JButton("Sửa");       styleButton(btnsua,       new Color(241, 196, 15));
        btnxoa      = new JButton("Xóa");       styleButton(btnxoa,       new Color(231, 76, 60));
        btnluu      = new JButton("Lưu");       styleButton(btnluu,       new Color(155, 89, 182));
        btnxuatfile = new JButton("Xuất File"); styleButton(btnxuatfile,  new Color(149, 165, 166));
        btnback     = new JButton("Quay Lại");  styleButton(btnback,      new Color(52, 73, 94));

        actionPanel.add(btnthem);
        actionPanel.add(btnsua);
        actionPanel.add(btnxoa);
        actionPanel.add(btnluu);
        actionPanel.add(btnxuatfile);
        actionPanel.add(btnback);

        this.add(actionPanel, BorderLayout.SOUTH);
    }

    // ===== UI Helpers =====
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
            @Override public void mouseExited(MouseEvent e)  { button.setBackground(bgColor); }
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

    // ===== Listeners =====
    public void bt_them(ActionListener a)      { btnthem.addActionListener(a); }
    public void bt_sua(ActionListener a)       { btnsua.addActionListener(a); }
    public void bt_xoa(ActionListener a)       { btnxoa.addActionListener(a); }
    public void bt_luu(ActionListener a)       { btnluu.addActionListener(a); }
    public void bt_xuatfile(ActionListener a)  { btnxuatfile.addActionListener(a); }
    public void bt_back(ActionListener a)      { btnback.addActionListener(a); }
    public void bt_timkiem(ActionListener a)   { btntimkiem.addActionListener(a); }

    // ===== Lấy đối tượng kho sách =====
    public m_khosach get_khosach() {
        try {
            String mak = txtMaK.getText().trim();
            Object masObj = cbMaS.getSelectedItem();

            if (mak.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã kho!");
                return null;
            }
            if (masObj == null) {
                JOptionPane.showMessageDialog(this, "Chưa có Mã sách trong danh sách!");
                return null;
            }

            int n = Integer.parseInt(txtNhap.getText().trim());
            int x = Integer.parseInt(txtXuat.getText().trim());

            if (n < 0 || x < 0) {
                JOptionPane.showMessageDialog(this, "Số lượng không được âm!");
                return null;
            }

            String mas = masObj.toString();
            return new m_khosach(mak, mas, n, x);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nhập/Xuất phải là số!");
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
            return null;
        }
    }
}
