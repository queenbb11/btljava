/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.view;

import baitaplonjava.model.model_login;
import java.awt.BorderLayout;
import java.awt.FlowLayout; // Thêm import này
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ADMIN
 */
public class view_login extends JFrame{
    JTextField tf_username;
    JPasswordField tf_password;
    JButton btn_login;
    // Bổ sung: Nút Đăng ký
    JButton btn_register; 

    public view_login() {
        initUI();
    }

    private void initUI() {
        this.setTitle("Đăng nhập");
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));

        tf_username = new JTextField();
        tf_password = new JPasswordField();

        // Panel chứa các trường nhập liệu
        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));

        panelInput.add(new JLabel("Username"));
        panelInput.add(tf_username);
        panelInput.add(new JLabel("Password"));
        panelInput.add(tf_password);

        btn_login = new JButton("Đăng nhập");
        // Bổ sung: Khởi tạo nút Đăng ký
        btn_register = new JButton("Đăng ký");

        // Panel chứa các nút (để đặt chúng cạnh nhau)
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelButtons.add(btn_login);
        panelButtons.add(btn_register); 

        this.add(panelInput, BorderLayout.CENTER);
        this.add(panelButtons, BorderLayout.SOUTH); 
    }

    public model_login get_tk() {
        return new model_login(
            tf_username.getText(),
            new String(tf_password.getPassword())
        );
    }

    public void check_login_listener(ActionListener a) {
        btn_login.addActionListener(a);
    }
    
    public void register_listener(ActionListener a) {
        btn_register.addActionListener(a);
    }
    
}