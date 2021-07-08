package com.patikadev.Model;

import com.patikadev.Helper.DbConnection;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Users {
    private int id;
    private String name;
    private String username;
    private String password;
    private String type;

    public Users(){}


    public Users(int id, String name, String username, String password, String type) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public static ArrayList<Users> getList(){
        ArrayList<Users> userList = new ArrayList<>();
        String query = "SELECT * FROM users";
        Users object;
        try {
            Statement statement = DbConnection.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                object = new Users();
                object.setId(resultSet.getInt("id"));
                object.setName(resultSet.getString("name"));
                object.setUsername(resultSet.getString("username"));
                object.setPassword(resultSet.getString("password"));
                object.setType(resultSet.getString("type"));
                userList.add(object);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    public static boolean add(Users user){
        String query= "INSERT INTO users(name,username,password,type) VALUES (?,?,?,?)";
        Users findUser = Users.getFetch(user.getUsername());
        if(findUser != null){
            Helper.showMessage("Bu kullanıcı adı bulunmaktadır.Lütfen başka bir kullanıcı adı giriniz");
            return false;
        }
        try {
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            statement.setString(1,user.getName());
            statement.setString(2,user.getUsername());
            statement.setString(3,user.getPassword());
            statement.setString(4,user.getType());
            int response = statement.executeUpdate();
            if(response == -1){
                Helper.showMessage("error");
            }
            return response != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  true;
    }

    public static boolean remove(int id){
        String query = "DELETE FROM users WHERE id = ?";
        ArrayList<Course> courses = Course.getListByUser(id);
        for(Course course : courses){
            Course.remove(course.getId());
        }
        try {
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            statement.setInt(1,id);

            return  statement.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static ArrayList<Users> searchUserList(String query){
        ArrayList<Users> userList = new ArrayList<>();

        Users object;
        try {
            Statement statement = DbConnection.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                object = new Users();
                object.setId(resultSet.getInt("id"));
                object.setName(resultSet.getString("name"));
                object.setUsername(resultSet.getString("username"));
                object.setPassword(resultSet.getString("password"));
                object.setType(resultSet.getString("type"));
                userList.add(object);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }
    public  static  String searchQuery(String name,String username ,String type){
        String query = "SELECT * FROM users WHERE username ILIKE '%{{username}}%' AND name ILIKE '%{{name}}%'";
        query = query.replace("{{username}}",username);
        query = query.replace("{{name}}",name);
        if(!type.isEmpty()){
            query+=" AND type ='{{type}}'";
            query=query.replace("{{type}}",type);
        }



        System.out.println(query);
        return query;
    }
    public static boolean update(Users user){
        String query = "UPDATE users SET name = ? ,username = ? , password = ? , type = ? WHERE id = ?  ";
        Users findUser = Users.getFetch(user.getUsername());
        if(findUser != null && findUser.getId() != user.getId()){
            Helper.showMessage("Bu kullanıcı adı bulunmaktadır.Lütfen başka bir kullanıcı adı giriniz");
            return false;
        }
        if(!user.getType().equals("operator") && !user.getType().equals( "student") && !user.getType().equals("educator")){
            Helper.showMessage("error");
            return  false;
        }
        try {
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            statement.setString(1,user.getName());
            statement.setString(2,user.getUsername());
            statement.setString(3,user.getPassword());
            statement.setString(4,user.getType());
            statement.setInt(5,user.getId());
            return statement.executeUpdate() != -1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;

    }
    public static  Users getFetch(String username){
        Users object = null;
        String query = "SELECT * FROM users WHERE username = ?";

        try {
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            statement.setString(1,username);
            ResultSet resultSet =statement.executeQuery();
            if (resultSet.next()){
                object = new Users();
                object.setId(resultSet.getInt("id"));
                object.setName(resultSet.getString("name"));
                object.setUsername(resultSet.getString("username"));
                object.setPassword(resultSet.getString("password"));
                object.setType(resultSet.getString("type"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  object;
    }

    public static  Users getFetch(String username,String password){
        Users object = null;
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try {
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet resultSet =statement.executeQuery();
            if (resultSet.next()){
                switch (resultSet.getString("type")){
                    case  "operator":
                        object = new Operator();
                        break;
                    default:
                        object = new Users();
                }
                object.setId(resultSet.getInt("id"));
                object.setName(resultSet.getString("name"));
                object.setUsername(resultSet.getString("username"));
                object.setPassword(resultSet.getString("password"));
                object.setType(resultSet.getString("type"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  object;
    }
    public static  Users getFetch(int id){
        Users object = null;
        String query = "SELECT * FROM users WHERE id = ?";

        try {
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            statement.setInt(1,id);
            ResultSet resultSet =statement.executeQuery();
            if (resultSet.next()){
                object = new Users();
                object.setId(resultSet.getInt("id"));
                object.setName(resultSet.getString("name"));
                object.setUsername(resultSet.getString("username"));
                object.setPassword(resultSet.getString("password"));
                object.setType(resultSet.getString("type"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  object;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

