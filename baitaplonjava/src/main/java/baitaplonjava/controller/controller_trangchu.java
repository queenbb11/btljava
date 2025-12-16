
package baitaplonjava.controller;
import baitaplonjava.view.view_trangchu;
// Bổ sung các import cần thiết
import baitaplonjava.view.view_theloai; 
import baitaplonjava.view.view_login;    
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class controller_trangchu implements ActionListener {
    
    private view_trangchu viewHome;
    private view_theloai viewTheloai; 

    public controller_trangchu(view_trangchu viewHome) {
        this.viewHome = viewHome;
        
        // Gắn sự kiện cho tất cả các nút cần xử lý
        attachListeners();
    }
    
    private void attachListeners() {
        // Gắn 'this' (controller_trangchu) làm ActionListener cho tất cả các nút
        this.viewHome.getBtnQuanLySach().addActionListener(this);
        // Bổ sung: QL Thể Loại
        this.viewHome.getBtnTheloai().addActionListener(this); 
        // Bổ sung: Đăng Xuất
        this.viewHome.getBtnDangXuat().addActionListener(this);    
        // Bạn có thể thêm các nút khác vào đây
        // this.viewHome.getBtnDocGia().addActionListener(this);
    }

    public void hienThiTrangChu() {
        viewHome.setVisible(true);
    }

    // Hàm mở cửa sổ QL Thể Loại
    private void openQuanLyTheLoaiView() {
    if (viewTheloai == null) {
        viewTheloai = new view_theloai();
        // Khởi tạo controller_theloai và truyền view vào
        new controller_theloai(viewTheloai); // <--- Dòng này quan trọng!
    }
    viewTheloai.setVisible(true);
}

    // Hàm xử lý Đăng Xuất
    private void handleDangXuat() {
        int confirm = JOptionPane.showConfirmDialog(viewHome, 
                "Bạn có chắc chắn muốn đăng xuất?", 
                "Xác nhận Đăng xuất", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // 1. Ẩn cửa sổ Trang Chủ
            viewHome.dispose();
            
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
        
        if (source == viewHome.getBtnQuanLySach()) {
            JOptionPane.showMessageDialog(viewHome, "Mở chức năng Quản lý Sách");
            // viewHome.setVisible(false); // Tùy chọn: Ẩn Trang chủ khi mở chức năng
            
        } else if (source == viewHome.getBtnTheloai()) {
            // Xử lý khi nhấn nút QL Thể Loại
            openQuanLyTheLoaiView();
            
        } else if (source == viewHome.getBtnDangXuat()) {
            // Xử lý khi nhấn nút Đăng Xuất
            handleDangXuat();
        }
        // Thêm các else if cho các nút khác ở đây
    }
}
