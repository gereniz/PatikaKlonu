package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Users;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI  extends  JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_login_username;
    private JButton btn_login;
    private JPasswordField fld_login_password;

    public  LoginGUI(){
        add(wrapper);
        setSize(400,400);
        setResizable(false);
        int x = Helper.screenCenter("x",getSize());
        int y = Helper.screenCenter("y",getSize());
        setLocation(x,y);
        setTitle(Config.PROJECT_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        btn_login.addActionListener(e -> {
            if(Helper.isEmptyField(fld_login_username) || Helper.isEmptyField(fld_login_password)){
                Helper.showMessage("fill");
            }else{
                Users user = Users.getFetch(fld_login_username.getText(),fld_login_password.getText());
                if(user == null){
                    Helper.showMessage("Kullanıcı Bulunamadı");
                }else{
                    Helper.showMessage("done");
                    switch (user.getType()){
                        case "operator":
                            OperatorUI operatorUI = new OperatorUI((Operator) user);
                            break;
                        case  "educator":
                            EducatorGUI educatorGUI = new EducatorGUI();
                            break;
                        case "student":
                            StudentGUI studentGUI = new StudentGUI();
                            break;
                    }
                    dispose();

                }
            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI loginGUI = new LoginGUI();
    }
}
