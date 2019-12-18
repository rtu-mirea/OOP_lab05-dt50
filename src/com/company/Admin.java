package com.company;

import java.io.Serializable;

public class Admin extends User implements Serializable {

    public Admin() {
        add("Admin", "admin", "123");
    }
}
