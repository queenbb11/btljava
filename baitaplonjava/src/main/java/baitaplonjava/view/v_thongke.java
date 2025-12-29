package baitaplonjava.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class v_thongke extends JFrame {

    public JComboBox<String> cbLoaiThongKe;
    public JButton btnThongKe, btnQuayLai;
    public JTable table;
    public DefaultTableModel model;

    public v_thongke() {
        initUI();
    }

    private void initUI() {
        this.setTitle("Thống Kê Sách");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(1100, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));
        this.getContentPane().setBackground(Color.WHITE);

        // ===== TITLE =====
        JLabel lblTitle = new JLabel("THỐNG KÊ SÁCH", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        this.add(lblTitle, BorderLayout.NORTH);

        // ===== CENTER =====
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(new EmptyBorder(10, 30, 10, 30));

        // ---- Panel chọn loại thống kê ----
        JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        optionPanel.setBackground(Color.WHITE);
        optionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        cbLoaiThongKe = new JComboBox<>(new String[]{
            "Thống kê sách theo thể loại",
            "Thống kê sách theo tác giả"
        });
        cbLoaiThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbLoaiThongKe.setPreferredSize(new Dimension(300, 40));

        btnThongKe = new JButton("Thống kê");
        styleButton(btnThongKe, new Color(46, 204, 113));

        optionPanel.add(new JLabel("Chọn loại thống kê:"));
        optionPanel.add(cbLoaiThongKe);
        optionPanel.add(btnThongKe);

        // ---- Bảng kết quả ----
        String[] cols = {"Tên", "Số lượng sách"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(230, 230, 230)));

        centerPanel.add(optionPanel);
        centerPanel.add(scrollPane);

        this.add(centerPanel, BorderLayout.CENTER);

        // ===== SOUTH =====
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        actionPanel.setBackground(new Color(245, 245, 245));

        btnQuayLai = new JButton("Quay Lại");
        styleButton(btnQuayLai, new Color(52, 73, 94));

        actionPanel.add(btnQuayLai);
        this.add(actionPanel, BorderLayout.SOUTH);
    }

    // ===== Helper UI =====
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 40));
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
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

    // ===== Listener =====
    public void bt_thongke_action_listener(ActionListener a) {
        btnThongKe.addActionListener(a);
    }

    public void bt_quaylai_action_listener(ActionListener a) {
        btnQuayLai.addActionListener(a);
    }

    // ===== Getter =====
    public String getLoaiThongKe() {
        return cbLoaiThongKe.getSelectedItem().toString();
    }
}
