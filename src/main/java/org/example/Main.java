package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        Bitcask db = new Bitcask("data.log");
        db.put("name", "Alice");
        db.put("city", "Paris");

        System.out.println(db.get("city")); // Paris

    }
}

