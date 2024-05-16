package org.example.javafx_ryhmatoo;

class Kontroll {
    Lemmad lemmad;

    public Kontroll() {
        this.lemmad = new Lemmad();
    }

    private boolean genereeriKombinatsioonid(String praegune, String tähed, boolean[] kasutatud) {
        if (!praegune.equals("") && lemmad.sõnaEksisteerib(praegune)) {
            return true;
        }
        for (int i = 0; i < tähed.length(); i++) {
            if (!kasutatud[i]) {
                kasutatud[i] = true;
                if (genereeriKombinatsioonid(praegune + tähed.charAt(i), tähed, kasutatud)) {
                    return true;
                }
                kasutatud[i] = false;
            }
        }
        return false;
    }

    boolean saabTehaSõna(String tähed) {
        return genereeriKombinatsioonid("", tähed, new boolean[tähed.length()]);
    }
}
