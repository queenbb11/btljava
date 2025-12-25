
package baitaplonjava.view;

import baitaplonjava.model.m_login;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class v_login extends JFrame {
    JTextField tf_username;
    JPasswordField tf_password;
    JButton btn_login;
    JButton btn_register;

    public v_login() {
        initUI();
    }

    private void initUI() {
        this.setTitle("Đăng Nhập Hệ Thống");
        this.setSize(480, 480); 
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.WHITE); // Nền trắng cho sạch sẽ

        // --- 1. TIÊU ĐỀ (NORTH) ---
        // Thay đổi câu tiêu đề tại đây
        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ THƯ VIỆN", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(44, 62, 80)); // Màu chữ xám đen sang trọng
        lblTitle.setBorder(new EmptyBorder(40, 0, 20, 0));
        this.add(lblTitle, BorderLayout.NORTH);

        // --- 2. PANEL NHẬP LIỆU (CENTER) ---
        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(Color.WHITE);
        panelInput.setBorder(new EmptyBorder(10, 60, 10, 60));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        
        // Ô nhập Username
        JLabel lblUser = new JLabel("Tên đăng nhập");
        lblUser.setFont(labelFont);
        tf_username = new JTextField();
        setupInputField(tf_username);

        // Ô nhập Password
        JLabel lblPass = new JLabel("Mật khẩu");
        lblPass.setFont(labelFont);
        lblPass.setBorder(new EmptyBorder(20, 0, 0, 0)); 
        tf_password = new JPasswordField();
        setupInputField(tf_password);

        panelInput.add(lblUser);
        panelInput.add(Box.createVerticalStrut(10));
        panelInput.add(tf_username);
        panelInput.add(lblPass);
        panelInput.add(Box.createVerticalStrut(10));
        panelInput.add(tf_password);

        this.add(panelInput, BorderLayout.CENTER);

        // --- 3. PANEL NÚT BẤM (SOUTH) ---
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 40));
        panelButtons.setBackground(Color.WHITE);
        
        btn_login = new JButton("Đăng nhập");
        btn_register = new JButton("Đăng ký");

        // Màu xanh lá hiện đại (Emerald) cho Đăng nhập
        styleButton(btn_login, new Color(46, 204, 113)); 
        // Màu xanh dương (Peter River) cho Đăng ký
        styleButton(btn_register, new Color(52, 152, 219));

        panelButtons.add(btn_login);
        panelButtons.add(btn_register);
        this.add(panelButtons, BorderLayout.SOUTH);
    }

    private void setupInputField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(300, 45)); // Ô nhập to và dễ bấm
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        // Bo viền nhẹ cho ô nhập liệu
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
        button.setPreferredSize(new Dimension(140, 48));
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
    public m_login get_tk() {
        return new m_login(tf_username.getText(), new String(tf_password.getPassword()));
    }
    public void check_login_listener(ActionListener a) { btn_login.addActionListener(a); }
    public void register_listener(ActionListener a) { btn_register.addActionListener(a); }
}