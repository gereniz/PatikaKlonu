package com.patikadev.Model;

import com.patikadev.Helper.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int id;
    private String name;

    public Patika(int id, String name) {

        this.id = id;
        this.name = name;
    }

    public static ArrayList<Patika> getList(){
        ArrayList<Patika> patikaList = new ArrayList<>();
        Patika object;

        try {
            Statement statement = DbConnection.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT *FROM patika");
            while(resultSet.next()){
                object = new Patika(resultSet.getInt("id"),resultSet.getString("name"));
                patikaList.add(object);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  patikaList;
    }

    public static  boolean update(int id,String name){
        String query = "UPDATE patika SET name = ? WHERE id = ?";
        try {
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            statement.setString(1,name);
            statement.setInt(2,id);
            return  statement.executeUpdate() !=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  false;
    }

    public static boolean add(String name){
        String query = "INSERT INTO patika (name) VALUES (?)";
        try {
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            statement.setString(1,name);
            return  statement.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  true;

    }

    public static Patika getFetch(int id){
        Patika object = null;
        String query = "SELECT * FROM patika WHERE id = ?";

        try {
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            statement.setInt(1,id);
            ResultSet resultSet =statement.executeQuery();
            if (resultSet.next()){
                object = new Patika(resultSet.getInt("id"),resultSet.getString("name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  object;
    }

    public static boolean remove(int id){
        String query = "DELETE FROM patika WHERE id = ? ";
        ArrayList<Course> courses = Course.getList();
        for(Course course : courses){
            if(course.getPatika().getId() == id) {
                Course.remove(course.getId());
            }
        }
        try {
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            statement.setInt(1,id);

            return statement.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
