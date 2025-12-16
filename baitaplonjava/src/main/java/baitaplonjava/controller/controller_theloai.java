/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.controller;

import baitaplonjava.view.view_theloai;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Controller xử lý logic cho giao diện Quản lý Thể Loại (view_theloai)
 */
public class controller_theloai implements ActionListener {
    
    // Khai báo view mà controller này quản lý
    private view_theloai viewTheLoai; 
    
    // Bạn có thể thêm model_theloai ở đây để tương tác với Database

    // Constructor nhận view_theloai
    public controller_theloai(view_theloai viewTheLoai) {
        this.viewTheLoai = viewTheLoai;
        
        // Gắn sự kiện cho các nút chức năng trong viewTheLoai
        attachListeners();
        
        // Tải dữ liệu ban đầu
        loadData();
    }
    
    // Hàm này giả định viewTheLoai có các getter cho các nút (getBtnThem, getBtnSua, getBtnXoa...)
    private void attachListeners() {
        // Ví dụ: Gắn sự kiện cho nút Thêm/Sửa/Xóa trong viewTheLoai
        /*
        viewTheLoai.getBtnThem().addActionListener(this);
        viewTheLoai.getBtnSua().addActionListener(this);
        viewTheLoai.getBtnXoa().addActionListener(this);
        */
        
        // Hiện tại, ta cần giả định các nút này tồn tại trong view_theloai
        // Vì view_theloai của bạn chưa có các nút chức năng cụ thể, 
        // bạn cần thêm chúng và các getter tương ứng (getBtnThem(),...) vào view_theloai trước.
    }
    
    private void loadData() {
        // TODO: Viết code để tải dữ liệu thể loại từ Database và hiển thị lên JTable
        System.out.println("Tải dữ liệu thể loại...");
    }

    // Xử lý sự kiện click cho các nút
    @Override
    public void actionPerformed(ActionEvent e) {
        /* Object source = e.getSource();
        
        if (source == viewTheLoai.getBtnThem()) {
            JOptionPane.showMessageDialog(viewTheLoai, "Chức năng Thêm Thể Loại");
            // TODO: Xử lý thêm mới
        } else if (source == viewTheLoai.getBtnSua()) {
            JOptionPane.showMessageDialog(viewTheLoai, "Chức năng Sửa Thể Loại");
            // TODO: Xử lý sửa
        } else if (source == viewTheLoai.getBtnXoa()) {
            JOptionPane.showMessageDialog(viewTheLoai, "Chức năng Xóa Thể Loại");
            // TODO: Xử lý xóa
        }
        */
    }
}