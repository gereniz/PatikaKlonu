package com.patikadev.Model;

import com.patikadev.Helper.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String language;

    private Patika patika;
    private Users user;


    public Course(){}

    public Course(int id, int user_id, int patika_id, String name, String language) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.language = language;
        this.patika = Patika.getFetch(patika_id);
        this.user = Users.getFetch(user_id);
    }


    public static ArrayList<Course> getList(){
        ArrayList<Course> courseList = new ArrayList<>();
        String query = "SELECT * FROM courses";
        Course object;
        try {
            Statement statement = DbConnection.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String language = resultSet.getString("language");
                int patika_id = resultSet.getInt("patika_id");
                int user_id = resultSet.getInt("user_id");
                object = new Course(id,user_id,patika_id,name,language);
                courseList.add(object);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseList;
    }

    public static ArrayList<Course> getListByUser(int userid){
        ArrayList<Course> courseList = new ArrayList<>();
        String query = "SELECT * FROM courses WHERE user_id = " + userid;
        Course object;
        try {
            Statement statement = DbConnection.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String language = resultSet.getString("language");
                int patika_id = resultSet.getInt("patika_id");
                int user_id = resultSet.getInt("user_id");
                object = new Course(id,user_id,patika_id,name,language);
                courseList.add(object);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseList;
    }

/*
    public static  boolean update(Course course){
        String query = "UPDATE course SET name = ? ,language = ?,user_id = ? ,patika_id = ?   WHERE id = ?";
        try {
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            statement.setString(1,course.getName());
            statement.setString(2,course.getLanguage());
            statement.setInt(3,course.user_id);
            statement.setInt(4,course.patika_id);
            statement.setInt(5,course.getId());
            return  statement.executeUpdate() !=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  false;
    }
*/
    public static boolean add(String name,String language,int user_id,int patika_id){
        String query = "INSERT INTO  courses (name,language,user_id,patika_id) VALUES (?,?,?,?)";
        try {
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            statement.setString(1,name);
            statement.setString(2,language);
            statement.setInt(3,user_id);
            statement.setInt(4,patika_id);
            return  statement.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  true;

    }
/*
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
    }*/

    public static boolean remove(int id){
        String query = "DELETE FROM courses WHERE id = ? ";
        try {
            PreparedStatement statement = DbConnection.getInstance().prepareStatement(query);
            statement.setInt(1,id);
            return statement.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
