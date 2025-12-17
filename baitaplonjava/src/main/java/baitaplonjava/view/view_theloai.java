
package baitaplonjava.view;

import baitaplonjava.model.model_theloai;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class view_theloai extends JFrame {

    public JTextField txtmatheloai, txttentheloai,txttimkiem;
    public JButton btnthem, btnsua, btnxoa, btnluu, btntimkiem,btnxuatfile,btndocfile, btnback;
    public JTable table;
    public DefaultTableModel model;

    public view_theloai() {
        initUI();
    }

    private void initUI() {
        this.setTitle("Quản Lý Thể Loại Sách");
        this.setSize(800, 600); // Tăng chiều cao để không bị chật
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Thay đổi để dễ quản lý
        this.setLayout(null);

        // --- Hàng 1: Nhập liệu ---
        JLabel lbMa = new JLabel("Mã Thể Loại:");
        lbMa.setBounds(50, 20, 100, 30);
        add(lbMa);

        txtmatheloai = new JTextField();
        txtmatheloai.setBounds(150, 20, 150, 30);
        add(txtmatheloai);

        JLabel lbTen = new JLabel("Tên Thể Loại:");
        lbTen.setBounds(350, 20, 100, 30);
        add(lbTen);

        txttentheloai = new JTextField();
        txttentheloai.setBounds(450, 20, 200, 30);
        add(txttentheloai);

        // --- Hàng 2: Tìm kiếm (Tách riêng ra cho thoáng) ---
        JLabel lbtimkiem = new JLabel("Tìm kiếm tên:");
        lbtimkiem.setBounds(50, 70, 100, 30);
        add(lbtimkiem);

        txttimkiem = new JTextField();
        txttimkiem.setBounds(150, 70, 410, 30);
        add(txttimkiem);

        btntimkiem = new JButton("Tìm");
        btntimkiem.setBounds(570, 70, 80, 30);
        add(btntimkiem);

        // --- Hàng 3: Các nút chức năng ---
        int btnY = 120;
        btnthem = new JButton("Thêm");
        btnthem.setBounds(50, btnY, 90, 30); add(btnthem);

        btnsua = new JButton("Sửa");
        btnsua.setBounds(150, btnY, 90, 30); add(btnsua);

        btnxoa = new JButton("Xóa");
        btnxoa.setBounds(250, btnY, 90, 30); add(btnxoa);

        btnluu = new JButton("Lưu");
        btnluu.setBounds(350, btnY, 90, 30); add(btnluu);

        btnxuatfile = new JButton("Xuất File");
        btnxuatfile.setBounds(450, btnY, 100, 30); add(btnxuatfile);

        btndocfile = new JButton("Đọc File");
        btndocfile.setBounds(560, btnY, 100, 30); add(btndocfile);

        // Nút Quay lại
        btnback = new JButton("Quay lại");
        btnback.setBounds(670, btnY, 100, 30);
        add(btnback);

        // --- Hàng 4: Table ---
        String[] columns = {"Mã Thể Loại", "Tên Thể Loại"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 170, 700, 350);
        add(scrollPane);
    }

    // Các phương thức lắng nghe sự kiện
    public void bt_them_action_listenner(ActionListener a) { btnthem.addActionListener(a); }
    public void bt_sua_action_listenner(ActionListener a) { btnsua.addActionListener(a); }
    public void bt_xoa_action_listenner(ActionListener a) { btnxoa.addActionListener(a); }
    public void bt_luu_action_listenner(ActionListener a) { btnluu.addActionListener(a); }
    public void bt_timkiem_action_listenner(ActionListener a) { btntimkiem.addActionListener(a); }
    public void bt_xuatfile_action_listenner(ActionListener a) { btnxuatfile.addActionListener(a); }
    public void bt_docfile_action_listenner(ActionListener a) { btndocfile.addActionListener(a); }
    public void bt_quaylai_action_listenner(ActionListener a) { btnback.addActionListener(a); }
    
    public model_theloai get_theloai() {
        try {
            String matheloai = txtmatheloai.getText().trim();
            String tentheloai = txttentheloai.getText().trim();
            
            if(matheloai.isEmpty() || tentheloai.isEmpty()){
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return null;
            }
            return new model_theloai(matheloai, tentheloai);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + e.getMessage());
            return null;
        }
    }
}