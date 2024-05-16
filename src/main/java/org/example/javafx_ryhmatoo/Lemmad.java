package org.example.javafx_ryhmatoo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Lemmad {
    Map<String, String> lemmad = new HashMap<>();

    public Lemmad() {
        loefailist("src/main/resources/lemmad.txt");
    }

    void loefailist(String failinimi) {
        try {
            File fail = new File(failinimi);
            Scanner scanner = new Scanner(fail, "windows-1252");
            while (scanner.hasNextLine()) {
                String lemma = scanner.nextLine();
                lemmad.put(lemma.toLowerCase(), lemma);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Faili ei leitud: " + e.getMessage());
        }
    }

    boolean sõnaEksisteerib(String lemma) {
        return lemmad.containsKey(lemma.toLowerCase());
    }

    String getSõnaOriginaalVorm(String lemma) {
        return lemmad.get(lemma.toLowerCase());
    }

    void eemaldaSõna(String sõna) {
        lemmad.remove(sõna.toLowerCase());
    }
}
