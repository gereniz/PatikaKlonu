package com.patikadev.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void optionPageTR(){
        UIManager.put("OptionPane.okButtonText","Tamam");
        UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayır");

    }
    public static void showMessage(String str){
        optionPageTR();
        String message,title;
        switch (str){
            case  "fill":
                message = "Lütfen boş alan bırakmayınız";
                title = "HATA";
                break;
            case "done":
                message = "İşlem başarılı";
                title = "Sonuç";
                break;
            case "error":
                message ="İşlem başarısız";
                title = "Sonuç";
            default:
                message = str;
                title = "Mesaj";
        }
        JOptionPane.showMessageDialog(null ,message,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static  boolean confirm(String str){
        optionPageTR();
        String message;
        switch (str){
            case  "sure" :
                message = "Emin misiniz ?";
                break;
            default:
                message = str;
        }
        return JOptionPane.showConfirmDialog(null,message,"Sil",JOptionPane.YES_NO_OPTION) == 0;
    }



    public static  boolean isEmptyField(JTextField field){
        return field.getText().trim().length() == 0;
    }


    public static void setLayout(){
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static int screenCenter(String axis, Dimension size){
        int point = 0;
        switch (axis){
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width-size.width)/2;
                break;
            case  "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height- size.height)/2;
                break;
            default:
                point=0;
        }
        return  point;

    }
}
