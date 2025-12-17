
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

    public JTextField txtmatheloai, txttentheloai;
    public JButton btnthem, btnsua, btnxoa, btnluu;
    public JTable table;
    public DefaultTableModel model;

    public view_theloai() {
        initUI();
    }

    private void initUI() {
        this.setTitle("Quản Lý Thể Loại Sách");
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.setLayout(null);
        JLabel lbMa = new JLabel("Mã Thể Loại:");
        lbMa.setBounds(50, 30, 100, 30);
        add(lbMa);

        JLabel lbTen = new JLabel("Tên Thể Loại:");
        lbTen.setBounds(50, 70, 100, 30);
        add(lbTen);

        txtmatheloai = new JTextField();
        txtmatheloai.setBounds(150, 30, 200, 30);
        add(txtmatheloai);

        txttentheloai = new JTextField();
        txttentheloai.setBounds(150, 70, 200, 30);
        add(txttentheloai);

        String[] columns = {"Mã Thể Loại", "Tên Thể Loại"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 120, 700, 200);
        add(scrollPane);

        // Các nút bấm
        btnthem = new JButton("Thêm");
        btnthem.setBounds(100, 350, 100, 30);
        add(btnthem);

        btnsua = new JButton("Sửa");
        btnsua.setBounds(250, 350, 100, 30);
        add(btnsua);

        btnxoa = new JButton("Xóa");
        btnxoa.setBounds(400, 350, 100, 30);
        add(btnxoa);

        btnluu = new JButton("Lưu");
        btnluu.setBounds(550, 350, 100, 30);
        add(btnluu);
    }

    // Các phương thức lắng nghe sự kiện
    public void bt_them_action_listenner(ActionListener a) { btnthem.addActionListener(a); }
    public void bt_sua_action_listenner(ActionListener a) { btnsua.addActionListener(a); }
    public void bt_xoa_action_listenner(ActionListener a) { btnxoa.addActionListener(a); }
    public void bt_luu_action_listenner(ActionListener a) { btnluu.addActionListener(a); }

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