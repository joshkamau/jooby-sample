package com.mycompany;

import forms.ContactForm;
import java.util.List;
import javax.sql.DataSource;
import models.User;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.jooby.Jooby;
import org.jooby.jdbc.Jdbc;
import org.jooby.json.Jackson;

/**
 * @author jooby generator
 */
public class App extends Jooby {

    {

        use(new Jackson());
        use(new Jdbc("db"));
        use(new MyModule());
        
        before((req,res)->{
            System.out.println("Before ");
            DataSource dataSource = require(DataSource.class);
            Base.open(dataSource);
        });
        
        
        complete((req, res, result)->{
            System.out.println("Request completed");
            Base.close();
        });
        

        get("/", (req, res) -> {
            MyService myService = require(MyService.class);
            LazyList<User> users = myService.loadUsers();
            res.send(users.toMaps());
        });

        post("/user/create", (req, res) -> {
            MyService myService = require(MyService.class);
            ContactForm contactForm = req.params(ContactForm.class);
            String firstName = contactForm.getFirstName();
            String lastName = contactForm.getLastName();
            User user = myService.createUser(firstName, lastName);
            res.send(user.toMap());
        });

        get("/user/show/:id", (req, res) -> {
            MyService myService = require(MyService.class);

            Long userID = req.params("id").longValue();
            User user = myService.loadUserByID(userID);
            res.send(user);
        });

        post("/user/update/:id", (req, res) -> {
            MyService myService = require(MyService.class);
            ContactForm contactForm = req.params(ContactForm.class);
            String firstName = contactForm.getFirstName();
            String lastName = contactForm.getLastName();
            Long userID = req.params("id").longValue();
            User user = myService.updateUser(userID, firstName, lastName);
            res.send(user);
        });

    }

    public static void main(final String[] args) {
        run(App::new, args);
    }

}
