
package baitaplonjava.controller;
import baitaplonjava.view.view_trangchu;
// Bổ sung các import cần thiết
import baitaplonjava.view.view_theloai; 
import baitaplonjava.view.view_login;    
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class controller_trangchu implements ActionListener {
    
    private view_trangchu viewtrangchu;
    private view_theloai viewtheloai; 

    public controller_trangchu(view_trangchu viewtrangchu) {
        this.viewtrangchu = viewtrangchu;
        
        // Gắn sự kiện cho tất cả các nút cần xử lý
        attachListeners();
    }
    
    private void attachListeners() {
        // Gắn 'this' (controller_trangchu) làm ActionListener cho tất cả các nút
        this.viewtrangchu.getBtnQuanLySach().addActionListener(this);
        // Bổ sung: QL Thể Loại
        this.viewtrangchu.getBtnTheloai().addActionListener(this); 
        // Bổ sung: Đăng Xuất
        this.viewtrangchu.getBtnDangXuat().addActionListener(this);    
        // Bạn có thể thêm các nút khác vào đây
        // this.viewHome.getBtnDocGia().addActionListener(this);
    }

    public void hienThiTrangChu() {
        viewtrangchu.setVisible(true);
    }

    private void openQuanLyTheLoaiView() {
        if (viewtheloai == null) {
            viewtheloai = new view_theloai(); 
            // SỬA DÒNG 42: Truyền viewHome (JFrame) thay vì truyền 'this' (Controller)
            new controller_theloai(viewtheloai, viewtrangchu); 
        }
        viewtheloai.setVisible(true);
        // SỬA DÒNG 45: Gọi setVisible thông qua đối tượng viewHome
        viewtrangchu.setVisible(false); 
    }
    
    
    // Hàm xử lý Đăng Xuất
    private void handleDangXuat() {
        int confirm = JOptionPane.showConfirmDialog(viewtrangchu, 
                "Bạn có chắc chắn muốn đăng xuất?", 
                "Xác nhận Đăng xuất", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // 1. Ẩn cửa sổ Trang Chủ
            viewtrangchu.dispose();
            
            // 2. Mở lại cửa sổ Đăng Nhập
            view_login vLogin = new view_login();
            // Lưu ý: Nếu bạn muốn logic login được xử lý lại, 
            // bạn cần tạo lại controller_login và truyền vLogin vào.
            new controller_login(); // Giả sử controller_login có constructor nhận view_login
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == viewtrangchu.getBtnQuanLySach()) {
            JOptionPane.showMessageDialog(viewtrangchu, "Mở chức năng Quản lý Sách");
            // viewHome.setVisible(false); // Tùy chọn: Ẩn Trang chủ khi mở chức năng
            
        } else if (source == viewtrangchu.getBtnTheloai()) {
            // Xử lý khi nhấn nút QL Thể Loại
            openQuanLyTheLoaiView();
            
        } else if (source == viewtrangchu.getBtnDangXuat()) {
            // Xử lý khi nhấn nút Đăng Xuất
            handleDangXuat();
        }
        // Thêm các else if cho các nút khác ở đây
    }
}
