
package baitaplonjava.view;
import baitaplonjava.model.m_sach;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class v_sach extends JFrame {

    public JTextField txtMaS, txtTenS, txtNamXB, txtMoTa, txttimkiem;
    public JComboBox<String> cbMaTL, cbMaNXB, cbMaTG;
    public JButton btnthem, btnsua, btnxoa, btnluu, btntimkiem, btnxuatfile, btnback;
    public JTable table;
    public DefaultTableModel model;

    public v_sach() {
        initUI();
    }

    private void initUI() {
        this.setTitle("Hệ Thống Quản Lý Sách");
        // Dòng này giúp cửa sổ tự động phóng to toàn màn hình khi mở
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(1100, 800); // Rộng hơn một chút vì nhiều cột
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));
        this.getContentPane().setBackground(Color.WHITE);

        // --- 1. TIÊU ĐỀ (NORTH) ---
        JLabel lblTitle = new JLabel("QUẢN LÝ DANH MỤC SÁCH", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        this.add(lblTitle, BorderLayout.NORTH);

        // --- 2. VÙNG ĐIỀU KHIỂN (CENTER) ---
        JPanel mainCenter = new JPanel();
        mainCenter.setLayout(new BoxLayout(mainCenter, BoxLayout.Y_AXIS));
        mainCenter.setBackground(Color.WHITE);
        mainCenter.setBorder(new EmptyBorder(10, 30, 10, 30));

        // Panel Nhập liệu (Dùng GridLayout 4 hàng, 4 cột để tối ưu không gian)
        JPanel inputPanel = new JPanel(new GridLayout(4, 4, 15, 15));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        txtMaS = new JTextField(); setupInputField(txtMaS);
        txtTenS = new JTextField(); setupInputField(txtTenS);
        txtNamXB = new JTextField(); setupInputField(txtNamXB);
        txtMoTa = new JTextField(); setupInputField(txtMoTa);

        cbMaTL = new JComboBox<>();
        cbMaNXB = new JComboBox<>();
        cbMaTG = new JComboBox<>();

        // Hàng 1
        inputPanel.add(createLabel("Mã Sách:")); inputPanel.add(txtMaS);
        inputPanel.add(createLabel("Tên Sách:")); inputPanel.add(txtTenS);
        // Hàng 2
        inputPanel.add(createLabel("Thể Loại:")); inputPanel.add(cbMaTL);
        inputPanel.add(createLabel("Tác Giả:")); inputPanel.add(cbMaTG);
        // Hàng 3
        inputPanel.add(createLabel("Nhà Xuất Bản:")); inputPanel.add(cbMaNXB);
        inputPanel.add(createLabel("Năm Xuất Bản:")); inputPanel.add(txtNamXB);
        // Hàng 4
        inputPanel.add(createLabel("Mô tả:")); inputPanel.add(txtMoTa);
        // Panel Tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 20));
        searchPanel.setBackground(Color.WHITE);
        
        txttimkiem = new JTextField();
        setupInputField(txttimkiem);
        txttimkiem.setPreferredSize(new Dimension(750, 40));
        
        btntimkiem = new JButton("Tìm Kiếm");
        styleButton(btntimkiem, new Color(52, 152, 219));
        searchPanel.add(txttimkiem);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btntimkiem);

        // --- Bảng hiển thị ---
        String[] columns = {"Mã Sách", "Tên Sách", "Mã TL", "Mã NXB", "Mã TG", "Năm XB", "Mô Tả"};
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

        btnthem = new JButton("Thêm Mới"); styleButton(btnthem, new Color(46, 204, 113));
        btnsua = new JButton("Sửa"); styleButton(btnsua, new Color(241, 196, 15));
        btnxoa = new JButton("Xóa"); styleButton(btnxoa, new Color(231, 76, 60));
        btnluu = new JButton("Lưu"); styleButton(btnluu, new Color(155, 89, 182));
        btnxuatfile = new JButton("Xuất File"); styleButton(btnxuatfile, new Color(149, 165, 166));
        btnback = new JButton("Quay Lại"); styleButton(btnback, new Color(52, 73, 94));

        actionPanel.add(btnthem); actionPanel.add(btnsua); actionPanel.add(btnxoa);
        actionPanel.add(btnluu); actionPanel.add(btnxuatfile); actionPanel.add(btnback);

        this.add(actionPanel, BorderLayout.SOUTH);
    }

    // Helper: Tạo Label nhanh
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
        table.setSelectionBackground(new Color(232, 242, 252));
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(0, 35));
    }

    // --- Listeners ---
    public void bt_them_action_listener(ActionListener a) { btnthem.addActionListener(a); }
    public void bt_sua_action_listener(ActionListener a) { btnsua.addActionListener(a); }
    public void bt_xoa_action_listener(ActionListener a) { btnxoa.addActionListener(a); }
    public void bt_luu_action_listener(ActionListener a) { btnluu.addActionListener(a); }
    public void bt_timkiem_action_listener(ActionListener a) { btntimkiem.addActionListener(a); }
    public void bt_xuatfile_action_listener(ActionListener a) { btnxuatfile.addActionListener(a); }
    public void bt_quaylai_action_listener(ActionListener a) { btnback.addActionListener(a); }

    // Lấy đối tượng m_sach từ Form
    public m_sach get_sach() {
        try {
            String MaS = txtMaS.getText().trim();
            String TenS = txtTenS.getText().trim();
            String MaTL = cbMaTL.getSelectedItem().toString();
            String MaNXB = cbMaNXB.getSelectedItem().toString();
            String MaTG = cbMaTG.getSelectedItem().toString();
            int NamXB = Integer.parseInt(txtNamXB.getText().trim());
 
            String MoTa = txtMoTa.getText().trim();

            if (MaS.isEmpty() || TenS.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã và Tên sách!");
                return null;
            }
            return new m_sach(MaS, TenS, MaTL, MaNXB, MaTG, NamXB, MoTa);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập liệu: Năm xuất bản phải là số!");
            return null;
        }
    }
}