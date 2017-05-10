/*
 * Copyright 2017 josh.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mycompany;

import javax.inject.Inject;
import javax.sql.DataSource;

import models.User;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author josh
 */
public class MyServiceImpl implements MyService {
    
    @Inject
    DataSource dataSource;
   

    @Override
    public LazyList<User> loadUsers() {
        LazyList<User> users = User.findAll();
       return users;
    }

    @Override
    public User createUser(String firstName, String lastName) {
        User user = new User();
        user.set("first_name", firstName);
        user.set("last_name", lastName);
        user.saveIt();
        return user;
    }

    @Override
    public User loadUserByID(Long userID) {
        User user = User.findById(userID);
        return user;
    }

    @Override
    public User updateUser(Long userID, String firstName, String lastName) {
        User user = User.findById(userID);
        user.set("first_name", firstName);
        user.set("last_name", lastName);
        user.save();
        return user;
    }
    
}
