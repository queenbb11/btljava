
package baitaplonjava.controller;

  
import baitaplonjava.view.v_khosach;
import baitaplonjava.view.v_login;
import baitaplonjava.view.v_sach;
import baitaplonjava.view.v_theloai;
import baitaplonjava.view.v_trangchu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class c_trangchu implements ActionListener {
    
    private v_trangchu viewtrangchu;
    private v_theloai viewtheloai; 
    private v_sach viewsach;
    private v_khosach viewkhosach;

    public c_trangchu(v_trangchu viewtrangchu) {
        this.viewtrangchu = viewtrangchu;
        
        // Gắn sự kiện cho tất cả các nút cần xử lý
        attachListeners();
    }
    
    private void attachListeners() {
        // Gắn 'this' (controller_trangchu) làm ActionListener cho tất cả các nút
        this.viewtrangchu.getBtnSach().addActionListener(this);
        // Bổ sung: QL Thể Loại
        this.viewtrangchu.getBtnTheloai().addActionListener(this); 
        // QL: kho ssach
         this.viewtrangchu.getBtnKhosach().addActionListener(this);
        // Bổ sung: Đăng Xuất
        this.viewtrangchu.getBtnDangXuat().addActionListener(this);    
        // Bạn có thể thêm các nút khác vào đây
        // this.viewHome.getBtnDocGia().addActionListener(this);
    }

    public void hienThiTrangChu() {
        viewtrangchu.setVisible(true);
    }

    private void moQuanlytheloaiview() {
        if (viewtheloai == null) {
            viewtheloai = new v_theloai(); 
            // SỬA DÒNG 42: Truyền viewHome (JFrame) thay vì truyền 'this' (Controller)
            new c_theloai(viewtheloai, viewtrangchu); 
        }
        viewtheloai.setVisible(true);
        // SỬA DÒNG 45: Gọi setVisible thông qua đối tượng viewHome
        viewtrangchu.setVisible(false); 
    }
    
     private void moQuanlysachview() {
        if (viewsach == null) {
            viewsach = new v_sach(); 
            // SỬA DÒNG 42: Truyền viewHome (JFrame) thay vì truyền 'this' (Controller)
            new c_sach(viewsach, viewtrangchu); 
        }
        viewsach.setVisible(true);
        // SỬA DÒNG 45: Gọi setVisible thông qua đối tượng viewHome
        viewtrangchu.setVisible(false); 
    }
    
      private void moQuanlykhosachview() {
        if (viewkhosach == null) {
            viewkhosach = new v_khosach(); 
            // SỬA DÒNG 42: Truyền viewHome (JFrame) thay vì truyền 'this' (Controller)
            new c_khosach(viewkhosach, viewtrangchu); 
        }
        viewkhosach.setVisible(true);
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
            v_login vLogin = new v_login();
            // Lưu ý: Nếu bạn muốn logic login được xử lý lại, 
            // bạn cần tạo lại controller_login và truyền vLogin vào.
            new c_login(); // Giả sử controller_login có constructor nhận view_login
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == viewtrangchu.getBtnSach()) {
           moQuanlysachview();
            
        } else if (source == viewtrangchu.getBtnTheloai()) {
            // Xử lý khi nhấn nút QL Thể Loại
            moQuanlytheloaiview();      
        } 
        else if (source == viewtrangchu.getBtnKhosach()) {
            // Xử lý khi nhấn nút QL Thể Loại
            moQuanlykhosachview();      
        } 
        else if (source == viewtrangchu.getBtnDangXuat()) {
            // Xử lý khi nhấn nút Đăng Xuất
            handleDangXuat();
        }
        // Thêm các else if cho các nút khác ở đây
    }
}
