package besco.corporation.toutimmobilier.Models;


import com.orm.SugarRecord;

/**
 * Created by root on 24/08/2018.
 */


public class User extends SugarRecord {

    private Long id;
    private String email;


    public User() {

    }

    public User(String email) {
        this.email = email;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}