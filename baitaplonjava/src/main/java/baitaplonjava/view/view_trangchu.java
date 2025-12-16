/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitaplonjava.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class view_trangchu extends JFrame {

    // Khai báo các nút bấm dưới dạng thuộc tính để Controller có thể truy cập
    private JButton btnQuanLySach;
    private JButton btnDocGia;
    private JButton btnMuonTra;
    private JButton btnTheloai;
    private JButton btnDangXuat;
    // Bạn có thể khai báo thêm các nút khác nếu cần xử lý sự kiện riêng biệt
    
    public view_trangchu() {
        initComponents();
    }

    private void initComponents() {
        // 1. Cấu hình JFrame chính
        this.setTitle("Hệ Thống Quản Lý Thư Viện - Dashboard");
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Căn giữa màn hình
        this.setLayout(new BorderLayout());

        // 2. Tạo phần Header (Tiêu đề phía trên)
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(45, 118, 232)); // Màu xanh dương đậm
        pnlHeader.setPreferredSize(new Dimension(1000, 80));
        pnlHeader.setLayout(new BorderLayout());
        
        JLabel lblTitle = new JLabel("TRANG CHỦ QUẢN TRỊ THƯ VIỆN", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        pnlHeader.add(lblTitle, BorderLayout.CENTER);

        // 3. Tạo phần Menu (Chứa 15 nút bấm)
        JPanel pnlMenu = new JPanel();
        pnlMenu.setBackground(new Color(245, 245, 245)); // Màu nền xám rất nhạt
        // GridLayout: 3 hàng, 5 cột, khoảng cách 20px
        pnlMenu.setLayout(new GridLayout(3, 5, 20, 20)); 
        pnlMenu.setBorder(new EmptyBorder(30, 30, 30, 30));

        // --- TẠO CÁC BUTTON VỚI ICON EMOJI ---
        
        // Nút 1: Quản lý sách
        btnQuanLySach = createStyledButton("QL Sách", "📚");
        pnlMenu.add(btnQuanLySach);

        // Nút 2: Độc giả
        btnDocGia = createStyledButton("QL Độc Giả", "👥");
        pnlMenu.add(btnDocGia);
        
        // Nút 3: Mượn trả
        btnMuonTra = createStyledButton("QL Mượn Trả", "🔄");
        pnlMenu.add(btnMuonTra);

        // Các nút chức năng khác (Chưa cần gán biến nếu chưa làm chức năng)
        pnlMenu.add(createStyledButton("QL Tác Giả", "✍️"));
        pnlMenu.add(createStyledButton("QL NXB", "🏢"));
        //Nút: Thể loại
        btnTheloai = createStyledButton("QL Thể Loại", "🔖");
        pnlMenu.add(btnTheloai);
        pnlMenu.add(createStyledButton("QL Nhân Viên", "🆔"));
        pnlMenu.add(createStyledButton("Nhập Sách", "📥"));
        pnlMenu.add(createStyledButton("Thanh Lý", "📤"));
        pnlMenu.add(createStyledButton("Thống Kê", "📈"));
        pnlMenu.add(createStyledButton("Báo Cáo", "📄"));
        pnlMenu.add(createStyledButton("Cấu Hình", "⚙️"));
        pnlMenu.add(createStyledButton("Quy Định", "📜"));
        pnlMenu.add(createStyledButton("Đổi Mật Khẩu", "🔑"));

        // Nút 15: Đăng xuất (Cần gán biến để Controller xử lý thoát)
        btnDangXuat = createStyledButton("Đăng Xuất", "🚪");
        // Đổi màu riêng cho nút Đăng xuất để nổi bật
        btnDangXuat.setForeground(new Color(200, 50, 50)); 
        pnlMenu.add(btnDangXuat);

        // 4. Thêm Header và Menu vào Frame
        this.add(pnlHeader, BorderLayout.NORTH);
        this.add(pnlMenu, BorderLayout.CENTER);
        
        // Footer
        JLabel lblFooter = new JLabel("Quản lí thư viện _ Nhóm 3", JLabel.CENTER);
        lblFooter.setBorder(new EmptyBorder(10,0,10,0));
        this.add(lblFooter, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, String icon) {
        // Sử dụng HTML để hiển thị Icon to ở trên và Text ở dưới
        JButton btn = new JButton("<html><center><span style='font-size:24px'>" + icon + "</span><br><span style='font-size:12px'>" + text + "</span></center></html>");
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(50, 50, 50));
        btn.setFocusPainted(false); // Bỏ viền focus khi click
        btn.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        // Hiệu ứng con trỏ chuột khi di vào
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return btn;
    }

    // --- GETTER CHO CONTROLLER GỌI ---

    public JButton getBtnQuanLySach() {
        return btnQuanLySach;
    }

    public JButton getBtnDocGia() {
        return btnDocGia;
    }

    public JButton getBtnMuonTra() {
        return btnMuonTra;
    }
    
    public JButton getBtnTheloai() {
        return btnTheloai;
    }

    
    public JButton getBtnDangXuat() {
        return btnDangXuat;
    }
}
