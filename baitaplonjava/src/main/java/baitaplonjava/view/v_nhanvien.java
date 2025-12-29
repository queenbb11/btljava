package baitaplonjava.view;

import baitaplonjava.model.m_nhanvien;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class v_nhanvien extends JFrame {
    public JTextField txtMaNV, txtTenNV, txtDienThoai, txtEmail, txtDiaChi, txttimkiem;
    public JComboBox<String> cbGioiTinh;
    public JFormattedTextField dateNS;

    public JButton btnthem, btnsua, btnxoa, btnluu, btntimkiem, btnxuatfile, btnback;
    public JTable table;
    public DefaultTableModel model;

    public v_nhanvien() {
        initUI();
    }

    private void initUI() {
        setTitle("QUẢN LÝ NHÂN VIÊN");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("QUẢN LÝ NHÂN VIÊN", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(new EmptyBorder(10, 30, 10, 30));

        JPanel input = new JPanel(new GridLayout(4, 4, 15, 15));

        txtMaNV = txt();
        txtTenNV = txt();
        txtDienThoai = txt();
        txtEmail = txt();
        txtDiaChi = txt();

        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});

        DateFormatter df = new DateFormatter(new SimpleDateFormat("dd/MM/yyyy"));
        dateNS = new JFormattedTextField(df);
        dateNS.setToolTipText("dd/MM/yyyy");

        input.add(lbl("Mã NV")); input.add(txtMaNV);
        input.add(lbl("Tên NV")); input.add(txtTenNV);
        input.add(lbl("Ngày sinh")); input.add(dateNS);
        input.add(lbl("Giới tính")); input.add(cbGioiTinh);
        input.add(lbl("Điện thoại")); input.add(txtDienThoai);
        input.add(lbl("Email")); input.add(txtEmail);
        input.add(lbl("Địa chỉ")); input.add(txtDiaChi);

        JPanel search = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txttimkiem = txt();
        txttimkiem.setPreferredSize(new Dimension(700, 40));
        btntimkiem = btn("Tìm kiếm");
        search.add(txttimkiem);
        search.add(btntimkiem);

        String[] cols = {"Mã NV", "Tên NV", "Giới tính", "Ngày sinh", "Địa chỉ", "Điện thoại", "Email"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        JTableHeader h = table.getTableHeader();
        h.setFont(new Font("Segoe UI", Font.BOLD, 14));

        center.add(input);
        center.add(search);
        center.add(new JScrollPane(table));
        add(center, BorderLayout.CENTER);

        JPanel action = new JPanel(new FlowLayout());
        btnthem = btn("Thêm");
        btnsua = btn("Sửa");
        btnxoa = btn("Xóa");
        btnluu = btn("Lưu");
        btnxuatfile = btn("Xuất file");
        btnback = btn("Quay lại");

        action.add(btnthem);
        action.add(btnsua);
        action.add(btnxoa);
        action.add(btnluu);
        action.add(btnxuatfile);
        action.add(btnback);
        add(action, BorderLayout.SOUTH);
    }

    public m_nhanvien getNhanVien() {
        try {
            String ma = txtMaNV.getText().trim();
            String ten = txtTenNV.getText().trim();
            Date ns = (Date) dateNS.getValue();
            if (ma.isEmpty() || ten.isEmpty() || ns == null) return null;
            return new m_nhanvien(
                    ma,
                    ten,
                    ns,
                    cbGioiTinh.getSelectedItem().toString(),
                    txtDienThoai.getText(),
                    txtEmail.getText(),
                    txtDiaChi.getText()
            );
        } catch (Exception e) {
            return null;
        }
    }

    private JTextField txt() {
        JTextField t = new JTextField();
        t.setBorder(new CompoundBorder(new LineBorder(Color.GRAY), new EmptyBorder(5, 10, 5, 10)));
        return t;
    }

    private JLabel lbl(String s) {
        JLabel l = new JLabel(s);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return l;
    }

    private JButton btn(String s) {
        JButton b = new JButton(s);
        b.setPreferredSize(new Dimension(120, 40));
        return b;
    }
    
}
