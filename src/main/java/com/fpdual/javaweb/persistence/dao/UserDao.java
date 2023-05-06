package com.fpdual.javaweb.persistence.dao;

import lombok.Data;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


@Data

public class UserDao {
    private int id;
    private String name, surname1, surname2, email, password;
    private Date createTime;

    public UserDao() {
    }

    public UserDao(ResultSet result) {

        try {

            this.id = result.getInt("id");
            this.name = result.getString("name");
            this.surname1 = result.getString("surname1");
            this.surname2 = result.getString("surname2");
            this.email = result.getString("email");
            this.password = result.getString("password");
            this.createTime = result.getDate("create_time");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}