/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.view;
import baitaplonjava.controller.controller_login;
import baitaplonjava.view.view_login;
import baitaplonjava.model.model_login;
import java.awt.BorderLayout;
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
public class view_login {

    JFrame frame;
    JTextField tf_username;
    JPasswordField tf_password;
    JLabel lbl_username, lbl_password;
    JButton btn_login;
    controller_login LoginController;
    public view_login() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout(5, 5));
        frame.setSize(500, 500);
        frame.setTitle("Dang nhap");

        tf_username = new JTextField();
        tf_password = new JPasswordField();
        lbl_username = new JLabel("Username");
        lbl_password = new JLabel("Password");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel, BorderLayout.NORTH);
        panel.add(lbl_username);
        panel.add(tf_username);

        panel.add(lbl_password);
        panel.add(tf_password);
        btn_login = new JButton("Login");
        frame.add(btn_login, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);    
    }
    public model_login get_tk() {
    model_login m = new model_login();
    m.setUser(tf_username.getText());

    char[] a = tf_password.getPassword();
    m.setPass(new String(a));

    return m;
    }
    public void check_login_listener(ActionListener a){
        btn_login.addActionListener(a);
    }

    public void close() {
        frame.dispose();
    }
}
