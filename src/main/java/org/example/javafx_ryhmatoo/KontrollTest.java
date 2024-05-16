package org.example.javafx_ryhmatoo;// See on cheat klass kuhu saab tähed panna ja näha mis sõnu neist oodustada saab.

import java.util.List;

public class KontrollTest {

    public static void main(String[] args) {
        Kontroll kontroll = new Kontroll();
        Lemmad lemmad = new Lemmad();

        String tähed = "qeoäaum"; // Sisestage siia tähed, millest soovite sõna moodustada

        List<String> kombinatsioonid = kontroll.genereeriKombinatsioonid(tähed);
        for (String kombinatsioon : kombinatsioonid) {
            if (lemmad.sõnaEksisteerib(kombinatsioon)) {
                System.out.println(kombinatsioon);
            }
        }
    }
}
