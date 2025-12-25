package baitaplonjava.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class v_dangky extends JFrame {

    private JTextField tf_username;
    private JPasswordField tf_password;
    private JPasswordField tf_repassword;
    private JButton btn_register;

    public v_dangky() {
        initUI();
    }

    private void initUI() {
        this.setTitle("Đăng Ký Tài Khoản");
        this.setSize(480, 550); // Chiều cao lớn hơn một chút để chứa 3 ô nhập
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.WHITE);

        // --- 1. TIÊU ĐỀ (NORTH) ---
        JLabel lblTitle = new JLabel("TẠO TÀI KHOẢN MỚI", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(new EmptyBorder(40, 0, 20, 0));
        this.add(lblTitle, BorderLayout.NORTH);

        // --- 2. PANEL NHẬP LIỆU (CENTER) ---
        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(Color.WHITE);
        panelInput.setBorder(new EmptyBorder(10, 60, 10, 60));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        // Username
        JLabel lblUser = new JLabel("Tên đăng nhập");
        lblUser.setFont(labelFont);
        tf_username = new JTextField();
        setupInputField(tf_username);

        // Password
        JLabel lblPass = new JLabel("Mật khẩu");
        lblPass.setFont(labelFont);
        lblPass.setBorder(new EmptyBorder(20, 0, 0, 0)); 
        tf_password = new JPasswordField();
        setupInputField(tf_password);

        // Re-Password
        JLabel lblRePass = new JLabel("Xác nhận mật khẩu");
        lblRePass.setFont(labelFont);
        lblRePass.setBorder(new EmptyBorder(20, 0, 0, 0)); 
        tf_repassword = new JPasswordField();
        setupInputField(tf_repassword);

        panelInput.add(lblUser);
        panelInput.add(Box.createVerticalStrut(8));
        panelInput.add(tf_username);
        panelInput.add(lblPass);
        panelInput.add(Box.createVerticalStrut(8));
        panelInput.add(tf_password);
        panelInput.add(lblRePass);
        panelInput.add(Box.createVerticalStrut(8));
        panelInput.add(tf_repassword);

        this.add(panelInput, BorderLayout.CENTER);

        // --- 3. PANEL NÚT BẤM (SOUTH) ---
        // Nhích nút lên bằng cách giảm vgap của FlowLayout và thêm Border phía dưới
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panelButtons.setBackground(Color.WHITE);
        panelButtons.setBorder(new EmptyBorder(0, 0, 30, 0));

        btn_register = new JButton("Đăng ký ngay");
        // Sử dụng màu xanh dương cho nút Đăng ký để đồng bộ với nút Đăng ký ở trang Login
        styleButton(btn_register, new Color(52, 152, 219)); 

        panelButtons.add(btn_register);
        this.add(panelButtons, BorderLayout.SOUTH);
    }

    private void setupInputField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(300, 45));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(210, 210, 210), 1), 
            new EmptyBorder(5, 15, 5, 15)
        ));
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 50)); 
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

    public String getUsername() {
        return tf_username.getText().trim();
    }

    public String getPassword() {
        return new String(tf_password.getPassword());
    }

    public String getRePassword() {
        return new String(tf_repassword.getPassword());
    }

    public void register_listener(ActionListener a) {
        btn_register.addActionListener(a);
    }
}