
package baitaplonjava.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class view_theloai extends JFrame {
    // Khai báo các thành phần cần thiết để Controller tương tác (ví dụ: JTable, các nút Add/Edit/Delete)
    private JTable tableTheLoai;
    // ... các nút chức năng khác

    public view_theloai() {
        initUI();
    }

    private void initUI() {
        this.setTitle("Quản Lý Thể Loại Sách");
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
        // Khi đóng cửa sổ này, chỉ đóng cửa sổ hiện tại (không thoát toàn bộ chương trình)
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        this.setLayout(new BorderLayout());
        
        // Tiêu đề
        JLabel lblTitle = new JLabel("DANH SÁCH THỂ LOẠI", JLabel.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(20f));
        this.add(lblTitle, BorderLayout.NORTH);

        // Khởi tạo JTable
        String[] columnNames = {"Mã Thể Loại", "Tên Thể Loại"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tableTheLoai = new JTable(model);
        
        JScrollPane scrollPane = new JScrollPane(tableTheLoai);
        this.add(scrollPane, BorderLayout.CENTER);
        
        // Panel chứa các nút chức năng (Thêm, Sửa, Xóa)
        JPanel pnlFunctions = new JPanel();
        // ... Thêm các nút chức năng vào đây
        
        this.add(pnlFunctions, BorderLayout.SOUTH);
    }
    
    // public JTable getTableTheLoai() { return tableTheLoai; }
    // ... Các getter cho các nút chức năng
}