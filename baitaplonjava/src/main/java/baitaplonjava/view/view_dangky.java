package baitaplonjava.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class view_dangky extends JFrame {

    private JTextField tf_username;
    private JPasswordField tf_password;
    private JPasswordField tf_repassword;
    private JButton btn_register;

    public view_dangky() {
        initUI();
    }

    private void initUI() {
        this.setTitle("Đăng ký tài khoản");
        this.setSize(400, 250);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Username:"));
        tf_username = new JTextField();
        panel.add(tf_username);

        panel.add(new JLabel("Mật khẩu:"));
        tf_password = new JPasswordField();
        panel.add(tf_password);

        panel.add(new JLabel("Nhập lại mật khẩu:"));
        tf_repassword = new JPasswordField();
        panel.add(tf_repassword);

        btn_register = new JButton("Đăng ký");
        panel.add(new JLabel());
        panel.add(btn_register);

        this.add(panel, BorderLayout.CENTER);
    }

    public String getUsername() {
        return tf_username.getText();
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
