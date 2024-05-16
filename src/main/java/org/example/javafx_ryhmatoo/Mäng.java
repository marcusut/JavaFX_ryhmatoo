package org.example.javafx_ryhmatoo;

import java.util.*;

class Mäng {
    private static final int TAVATÄHE_LÄVI = 89;
    private static final int HARULDASE_TÄHE_LÄVI = 97;
    private static final int VÄGA_HARULDASE_TÄHE_LÄVI = 100;
    private static final Scanner scanner = new Scanner(System.in);
    Lemmad lemmad;
    Kontroll kontroll;
    List<String> arvatudSõnad;
    String tähed;
    boolean mängAktiivne = true;

    public Mäng() {
        this.lemmad = new Lemmad();
        this.kontroll = new Kontroll();
        this.arvatudSõnad = new ArrayList<>();
        this.tähed = "";
    }

    static void alustaMäng() {
        while (true) {
            Mäng mäng = valiMängutüüp();
            if (mäng == null) {
                break;
            }
            System.out.println("Mängu saab sulgeda, sisestades 'quit', mängu lõpetamiseks ja menüüsse naasmiseks, sisesta 'menüüsse'");
            System.out.println("Uue tähe saamiseks sisesta '+'");
            mäng.mänguTsükkel();
        }
    }

    private static Mäng valiMängutüüp() {
        while (true) {
            System.out.println("Vali mängutüüp: Endless või Aeg. Väljumiseks sisesta 'Quit'.");
            String mängutüüp = scanner.nextLine();

            if (mängutüüp.equalsIgnoreCase("Endless")) {
                return new EndlessMode();
            } else if (mängutüüp.equalsIgnoreCase("Aeg")) {
                return new TimeAttack();
            } else if (mängutüüp.equalsIgnoreCase("Quit")) {
                return null;
            } else {
                System.out.println("Vale mängutüüp. Proovi uuesti.");
            }
        }
    }

    void mänguTsükkel() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            sb.append(genereeriÜksTäht());
        }
        tähed = sb.toString();
        while (!kontroll.saabTehaSõna(tähed)) {
            sb = new StringBuilder();
            for (int i = 0; i < 7; i++) {
                sb.append(genereeriÜksTäht());
            }
            tähed = sb.toString();
        }
        while (mängAktiivne) {
            System.out.println("Siin on sinu tähed: " + tähed);
            String sõna = scanner.nextLine();
            if (mängijaArvab(sõna, tähed)) {
                tähed = eemaldaKasutatudTähed(sõna, tähed);
                while (tähed.length() < 7 || !kontroll.saabTehaSõna(tähed)) {
                    tähed += genereeriÜksTäht();
                }
            }
        }
    }

    String genereeriÜksTäht() {
        String tähed = "abdeghijklmnoprstuv";
        String haruldasedTähed = "fõäöü";
        String vägaHaruldasedTähed = "cqšzžwxy";
        Random rand = new Random();
        int valik = rand.nextInt(VÄGA_HARULDASE_TÄHE_LÄVI);
        if (valik < TAVATÄHE_LÄVI) {
            return String.valueOf(getRandomChar(tähed, rand));
        } else if (valik < HARULDASE_TÄHE_LÄVI) {
            return String.valueOf(getRandomChar(haruldasedTähed, rand));
        } else {
            return String.valueOf(getRandomChar(vägaHaruldasedTähed, rand));
        }
    }

    // genereeriÜksTäht abimeetod
    private char getRandomChar(String str, Random rand) {
        int index = rand.nextInt(str.length());
        return str.charAt(index);
    }

    boolean sõnaOnMoodustatudPakutudTähtedest(String sõna, String tähed) {
        Map<Character, Integer> tähedKaart = new HashMap<>();
        for (char c : tähed.toCharArray()) {
            tähedKaart.put(c, tähedKaart.getOrDefault(c, 0) + 1);
        }

        for (char c : sõna.toCharArray()) {
            if (!tähedKaart.containsKey(c) || tähedKaart.get(c) == 0) {
                return false;
            }
            tähedKaart.put(c, tähedKaart.get(c) - 1);
        }
        return true;
    }

    // kui sõna on õieti ära arvatud, siis lisame selle arvatudSõnad listi
    boolean arvaSõna(String sõna) {
        sõna = sõna.toLowerCase();
        if (lemmad.sõnaEksisteerib(sõna)) {
            arvatudSõnad.add(lemmad.getSõnaOriginaalVorm(sõna));
            lemmad.eemaldaSõna(sõna);
            return true;
        }
        return false;
    }

    boolean mängijaArvab(String sõna, String tähed) {
        if (sõna.equalsIgnoreCase("quit")) {
            System.exit(0);
        }
        if (sõna.equalsIgnoreCase("menüüsse")) {
            lõpetaMäng();
            return false;
        }
        if (sõna.equalsIgnoreCase("+")) {
            StringBuilder sb = new StringBuilder(this.tähed);
            sb.append(genereeriÜksTäht());
            this.tähed = sb.toString();
            return false;
        }
        if (!sõnaOnMoodustatudPakutudTähtedest(sõna, tähed)) {
            System.out.println("Sõna ei saa moodustada pakutud tähtedest. Proovi uuesti.");
            return false;
        }
        if (arvaSõna(sõna)) {
            System.out.println("Õige sõna!");
            tähed = eemaldaKasutatudTähed(sõna, tähed);
            while ((tähed.length() < 7 || !kontroll.saabTehaSõna(tähed)) && tähed.length() < 10) {
                StringBuilder sb = new StringBuilder(tähed);
                sb.append(genereeriÜksTäht());
                tähed = sb.toString();
            }
            System.out.println("");
            return true;
        } else {
            System.out.println("Vale sõna. Proovi uuesti.");
            return false;
        }
    }

    String eemaldaKasutatudTähed(String sõna, String tähed) {
        for (char c : sõna.toCharArray()) {
            tähed = tähed.replaceFirst(String.valueOf(c), "");
        }
        return tähed;
    }

    void lõpetaMäng() {
        System.out.println("Mäng on läbi! Teie arvatud sõnad olid:");
        for (String sõna : arvatudSõnad) {
            System.out.println(sõna);
        }
        mängAktiivne = false;
    }

    public Kontroll getKontroll() {
        return kontroll;
    }
}

