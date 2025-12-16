/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.controller;
import baitaplonjava.view.view_trangchu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class controller_trangchu implements ActionListener {
    
    private view_trangchu viewHome;
   

    public controller_trangchu(view_trangchu viewHome) {
        this.viewHome = viewHome;
       

        // Gắn sự kiện cho nút ở Trang chủ
        this.viewHome.getBtnQuanLySach().addActionListener(this);

       
    }

    public void hienThiTrangChu() {
        viewHome.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewHome.getBtnQuanLySach()) {
            viewHome.setVisible(false);
            
        }
    }
}
