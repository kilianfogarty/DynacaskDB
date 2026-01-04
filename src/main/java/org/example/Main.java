package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        Bitcask db = new Bitcask("data.log");
        db.put("name", "Alice");
        db.put("city", "Paris");
        db.put("name", "Kilian");

        System.out.println(db.get("name")); // Kilian

        db.put("a", "1");
        db.put("b", "2");
        db.delete("a");

        System.out.println(db.get("a")); // null
        System.out.println(db.get("b")); // 2

    }
}

