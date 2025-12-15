/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.view;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class view_trangchu extends JFrame {

    JFrame frame;

    public view_trangchu() {
        frame = new JFrame("Trang chủ - Quản lý thư viện");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       

        frame.setVisible(true);
    }
}