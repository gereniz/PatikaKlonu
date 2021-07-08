package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Course;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Patika;
import com.patikadev.Model.Users;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_name;
    private JTextField fld_username;
    private JTextField fld_password;
    private JComboBox cmb_type;
    private JButton btn_add;
    private JTextField fld_id;
    private JButton btn_delete;
    private JTextField fld_search_name;
    private JTextField fld_search_username;
    private JComboBox cmb_search_type;
    private JButton btn_search;
    private JPanel pnl_patika_list;
    private JScrollPane scroll_patika;
    private JTable tbl_patika_list;
    private JTextField fld_patika_name;
    private JButton btn_patika_add;
    private JPanel pnl_course_list;
    private JScrollPane scroll_course;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField fld_course_name;
    private JTextField fld_course_language;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_educator;
    private JButton btn_course_add;
    private JTextField fld_course_id;
    private JButton btn_course_delete;

    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;

    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patika_menu;

    private DefaultTableModel mdl_course_list;
    private  Object[] row_course_list;

    private final Operator operator;

    public OperatorUI(Operator operator) {
        this.operator = operator;

        add(wrapper);
        setSize(1000,500);
        setResizable(false);
        int x = Helper.screenCenter("x",getSize());
        int y = Helper.screenCenter("y",getSize());
        setLocation(x,y);
        setTitle(Config.PROJECT_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldin " + operator.getName());

        mdl_user_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column == 0 ){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        patika_menu = new JPopupMenu();
        JMenuItem update_menu = new JMenuItem("Güncelle");
        JMenuItem delete_menu = new JMenuItem("Sil");
        patika_menu.add(update_menu);
        patika_menu.add(delete_menu);




        //---------course--------
        mdl_course_list = new DefaultTableModel();
        Object[] col_course_list = {"ID","Kurs Adı","Programlama Dili","Patika","Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];
        loadCourseModel();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(100);
        loadPatikaCombo();
        loadEducatorCombo();

        tbl_course_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_course_id = tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),0).toString();
                fld_course_id.setText(selected_course_id);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        });
//---patika-----------------
        mdl_patika_list = new DefaultTableModel();
        Object[] col_patika_list = {"ID","Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new  Object[col_patika_list.length];
        loadPatikaModel();
        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.setComponentPopupMenu(patika_menu);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(100);

        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_patika_list.rowAtPoint(point);
                tbl_patika_list.setRowSelectionInterval(selected_row,selected_row);
            }
        });
//--------------------------------user-------------------
        Object[] col_user_list = {"ID" , "AD SOYAD" ,"KULLANICI ADI", "ŞİFRE" , "ÜYELİK TİPİ"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false); //Kolon adlarının yer değişimi
        row_user_list = new Object[col_user_list.length];

        loadUserModel();


        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString();
                fld_id.setText(selected_user_id);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        });

        //UPDATE
        tbl_user_list.getModel().addTableModelListener(e -> {
            if(e.getType() == TableModelEvent.UPDATE){
                Users user = new Users();
                user.setId( Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString()));
                user.setName(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),1).toString());
                user.setUsername(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),2).toString());
                user.setPassword(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),3).toString());
                user.setType(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),4).toString());

                if(Users.update(user)){
                    Helper.showMessage("done");

                }
                loadUserModel();
                loadEducatorCombo();
                loadCourseModel();
            }
        });
        //EKLEME
        btn_add.addActionListener(e -> {

            if(Helper.isEmptyField(fld_name) || Helper.isEmptyField(fld_username) || Helper.isEmptyField(fld_password)){
                Helper.showMessage("fill");
            }else{
                Users user = new Users();
                user.setName(fld_name.getText());
                user.setUsername(fld_username.getText());
                user.setPassword(fld_password.getText());
                user.setType(cmb_type.getSelectedItem().toString());

                if(Users.add(user)){
                    Helper.showMessage("done");
                    loadUserModel();
                    loadEducatorCombo();
                    fld_name.setText(null);
                    fld_username.setText(null);
                    fld_password.setText(null);
                }
            }
        });

        //SİLME
        btn_delete.addActionListener(e -> {
            if(Helper.isEmptyField(fld_id)){
                Helper.showMessage("fill");
            }else{
               if(Helper.confirm("sure")){
                   int user_id = Integer.parseInt(fld_id.getText());
                   if(Users.remove(user_id)){
                       Helper.showMessage("done");
                       loadUserModel();
                       loadEducatorCombo();
                       loadCourseModel();
                       fld_id.setText(null);
                   }else{
                       Helper.showMessage("error");
                   }
               }
            }
        });

        //search
        btn_search.addActionListener(e -> {
            String name = fld_search_name.getText();
            String username = fld_username.getText();
            String type = cmb_search_type.getSelectedItem().toString();
            String query = Users.searchQuery(name,username,type);
            ArrayList<Users> searchingUser = Users.searchUserList(query);
            loadUserModel(searchingUser);
        //logout
        });
        btn_logout.addActionListener(e ->
                {
                    dispose();
                    LoginGUI loginGUI = new LoginGUI();
                });

        //------------------------------patika-------------------------
        //add
        btn_patika_add.addActionListener(e -> {
            if(Helper.isEmptyField(fld_patika_name)){
              Helper.showMessage("fill");
            }else{
                if(Patika.add(fld_patika_name.getText())){
                    Helper.showMessage("done");
                    loadPatikaModel();
                    loadPatikaCombo();
                    fld_patika_name.setText(null);
                }else{
                    Helper.showMessage("error");
                }
            }
        });
        update_menu.addActionListener(e -> {
            int selected_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
            UpdatePatikaGUI updatePatikaGUI = new UpdatePatikaGUI(Patika.getFetch(selected_id));
            updatePatikaGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();
                }
            });
        });

        delete_menu.addActionListener(e -> {
            if(Helper.confirm("sure")){
                int selected_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
                if(Patika.remove(selected_id)){
                    Helper.showMessage("done");
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();
                }else{
                    Helper.showMessage("error");
                }
            }
        });


        btn_course_add.addActionListener(e -> {
            Item patika_item = (Item) cmb_course_patika.getSelectedItem();
            Item educator_item = (Item) cmb_course_educator.getSelectedItem();
            if(Helper.isEmptyField(fld_course_name) || Helper.isEmptyField(fld_course_language)){
                Helper.showMessage("fill");
            }else{
                if(Course.add(fld_course_name.getText(),fld_course_language.getText(),educator_item.getKey(),patika_item.getKey())){
                    Helper.showMessage("done");
                    loadCourseModel();
                }else{
                    Helper.showMessage("error");
                }
            }
        });
        btn_course_delete.addActionListener(e -> {
            if(Helper.isEmptyField(fld_course_id)){
                Helper.showMessage("fill");
            }else{
                if(Helper.confirm("sure")){
                    int id = Integer.parseInt(fld_course_id.getText());
                    if(Course.remove(id)){
                        Helper.showMessage("done");
                        loadCourseModel();
                    }else{
                        Helper.showMessage("error");
                    }
                }
            }
        });
/*
        tbl_course_list.getModel().addTableModelListener(e -> {
            if(e.getType() == TableModelEvent.UPDATE){
                Course course = new Course();
                course.setId( Integer.parseInt(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),0).toString()));
                course.setName(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),1).toString());
                course.setLanguage(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),2).toString());
                course.setUser_id(Integer.parseInt(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(),3).toString()));
                course.setPatika_id(Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),4).toString()));

                if(Course.update(course)){
                    Helper.showMessage("done");

                }
                loadCourseModel();
            }
        });

 */
    }
//---------------model-----------------------

    public void loadCourseModel(){
        DefaultTableModel clear = (DefaultTableModel) tbl_course_list.getModel();
        clear.setRowCount(0);
        int i;
        for(Course course : Course.getList()){
            i=0;
            row_course_list[i++] = course.getId();
            row_course_list[i++] = course.getName();
            row_course_list[i++] = course.getLanguage();
            row_course_list[i++] = course.getPatika().getName();
            row_course_list[i++] = course.getUser().getName();
            mdl_course_list.addRow(row_course_list);
        }
    }

    public void loadPatikaCombo(){
        cmb_course_patika.removeAllItems();
        for (Patika patika : Patika.getList()){
            cmb_course_patika.addItem(new Item(patika.getId(),patika.getName()));
        }
    }

    public void loadEducatorCombo(){
        cmb_course_educator.removeAllItems();
        for (Users user: Users.getList()){
            if(user.getType().equals("educator")){
                cmb_course_educator.addItem(new Item(user.getId(),user.getName()));
            }

        }
    }


    public void loadPatikaModel(){
        DefaultTableModel clear = (DefaultTableModel) tbl_patika_list.getModel();
        clear.setRowCount(0);
        int i;
        for(Patika patika : Patika.getList()){
            i=0;
            row_patika_list[i++] = patika.getId();
            row_patika_list[i++] = patika.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }
    //search list

    public void loadUserModel(ArrayList<Users> list){
        DefaultTableModel clear = (DefaultTableModel) tbl_user_list.getModel();
        clear.setRowCount(0);

        for(Users user : list){
            int i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getName();
            row_user_list[i++] = user.getUsername();
            row_user_list[i++] = user.getPassword();
            row_user_list[i++] = user.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }
    //list

    public void loadUserModel(){
        DefaultTableModel clear = (DefaultTableModel) tbl_user_list.getModel();
        clear.setRowCount(0);

        for(Users user : Users.getList()){
            int i = 0;
            row_user_list[i++] = user.getId();
            row_user_list[i++] = user.getName();
            row_user_list[i++] = user.getUsername();
            row_user_list[i++] = user.getPassword();
            row_user_list[i++] = user.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

 
}