package com.chatserver;

import Database.Database;

public class Main {
    public static void main(String[] args) throws Exception {
        //Test
        Database.getInstance();
        System.out.println(Database.get_user_list());
    }
}
