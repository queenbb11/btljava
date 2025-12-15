/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.controller;

import baitaplonjava.view.view_login;
import baitaplonjava.model.model_login;
import baitaplonjava.view.view_trangchu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class controller_login {

    Connection con = null;
    view_login view;
    model_login model;

    public controller_login() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/baitaplon";
            String user = "root";
            String password = "123456789";

            con = DriverManager.getConnection(url, user, password);
            System.out.println("Ket noi thanh cong!");

        } catch (Exception e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
        }

        view = new view_login();
        view.check_login_listener(new check_login_listener());
    }

    public void check_login(model_login tk) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rec = st.executeQuery("SELECT * FROM dangnhap");

        boolean success = false;

        while (rec.next()) {
           if (tk.getUser().equals(rec.getString("username"))
        && tk.getPass().equals(rec.getString("password"))) {
    success = true;
    break;
}
        }

        if (success) {
            System.out.println("Dang nhap thanh cong!");
           view.close();
           new view_trangchu();
        } else {
            System.out.println("Dang nhap that bai!");
        }

        con.close();
    }

    class check_login_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            model = view.get_tk();
            try {
                check_login(model);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}