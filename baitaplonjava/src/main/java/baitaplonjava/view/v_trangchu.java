
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

public class v_trangchu extends JFrame {

    private JButton btnDocgia;
    private JButton btnTacgia;
    private JButton btnNhaxuatban;
    private JButton btnQuanlysach;
    private JButton btnKhosach;
    private JButton btnMuonTra;
    private JButton btnTheloai;
    private JButton btnDangXuat;
    private JLabel lblUserLogin; 
    
    public v_trangchu() {
        initComponents();
    }

    private void initComponents() {
        this.setTitle("Hệ Thống Quản Lý Thư Viện - Dashboard");
        // Dòng này giúp cửa sổ tự động phóng to toàn màn hình khi mở
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(1100, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); 
        this.setLayout(new BorderLayout());

        // 2. Tạo phần Header
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(45, 118, 232)); 
        pnlHeader.setPreferredSize(new Dimension(1000, 100)); 
        pnlHeader.setLayout(new BorderLayout());
        
        JLabel lblTitle = new JLabel("TRANG CHỦ QUẢN TRỊ THƯ VIỆN", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        pnlHeader.add(lblTitle, BorderLayout.CENTER);
        // 3. Tạo phần Menu
        JPanel pnlMenu = new JPanel();
        pnlMenu.setBackground(new Color(245, 245, 245)); 
        pnlMenu.setLayout(new GridLayout(3, 5, 20, 20)); 
        pnlMenu.setBorder(new EmptyBorder(30, 30, 30, 30));

        btnQuanlysach = createStyledButton("QL Sách", "📚");
        pnlMenu.add(btnQuanlysach);
        
        btnKhosach = createStyledButton("QL Kho sách", "📄");
        pnlMenu.add(btnKhosach);
        
        btnMuonTra = createStyledButton("QL Mượn Trả", "🔄");
        pnlMenu.add(btnMuonTra);
        
        btnTacgia = createStyledButton("QL Tác Giả", "✍️");
        pnlMenu.add(btnTacgia);
        
        btnNhaxuatban = createStyledButton("QL NXB", "🏢");
        pnlMenu.add(btnNhaxuatban);
        
        btnDocgia = createStyledButton("Độc giả", "⚙️");
        pnlMenu.add(btnDocgia);
        
        btnTheloai = createStyledButton("QL Thể Loại", "🔖");
        pnlMenu.add(btnTheloai);
        
        
        pnlMenu.add(createStyledButton("QL Nhân Viên", "🆔"));
        
        pnlMenu.add(createStyledButton("Nhập Sách", "📥"));
        
        pnlMenu.add(createStyledButton("Thanh Lý", "📤"));
        
        pnlMenu.add(createStyledButton("Thống Kê", "📈"));
        
        pnlMenu.add(createStyledButton("Báo Cáo", "📄"));
        
        pnlMenu.add(createStyledButton("Quy Định", "📜"));
        
        pnlMenu.add(createStyledButton("Đổi Mật Khẩu", "🔑"));
        
        btnDangXuat = createStyledButton("Đăng Xuất", "🚪");
        btnDangXuat.setForeground(new Color(200, 50, 50)); 
        pnlMenu.add(btnDangXuat);

        this.add(pnlHeader, BorderLayout.NORTH);
        this.add(pnlMenu, BorderLayout.CENTER);
        
        JLabel lblFooter = new JLabel("Quản lí thư viện _ Nhóm 3", JLabel.CENTER);
        lblFooter.setBorder(new EmptyBorder(10,0,10,0));
        this.add(lblFooter, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, String icon) {
        JButton btn = new JButton("<html><center><span style='font-size:24px'>" + icon + "</span><br><span style='font-size:12px'>" + text + "</span></center></html>");
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(50, 50, 50));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return btn;
    }

    // Khi đăng nhập thành công, Controller sẽ gọi hàm này để hiện tên thật
    public void setDisplayName(String name) {
        lblUserLogin.setText(name);
    }

    // --- GETTER ---
    public JButton getBtnSach() { return btnQuanlysach; }
    public JButton getBtnKhosach() { return btnKhosach; }
    public JButton getBtnMuonTra() { return btnMuonTra; }
    public JButton getBtnTheloai() { return btnTheloai; }
    public JButton getBtnDangXuat() { return btnDangXuat; }
    public JButton getBtnNhaxuatban() { return btnNhaxuatban; }
    public JButton getBtnTacgia() { return btnTacgia; }
    public JButton getBtnDocgia() { return btnDocgia; }
}