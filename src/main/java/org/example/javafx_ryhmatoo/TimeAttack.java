package org.example.javafx_ryhmatoo;

class TimeAttack extends Mäng {
    int aeg;

    public TimeAttack() {
        super();
        System.out.println("Tere tulemast Time Attack Mode'i!.");
        System.out.println("Uue tähe küsimine võtab maha sekundi Teie ajast.");
        this.aeg = 60; // mäng kestab 60 sekundit

        // Alusta taimerit eraldi lõimes
        new Thread(() -> {
            while (aeg > 0) {
                try {
                    Thread.sleep(1000); // oota 1 sekund
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                aeg--;
            }

            // Kui aeg on otsas, lõpeta mäng
            super.lõpetaMäng();
        }).start();

    }

    @Override
    boolean mängijaArvab(String sõna, String tähed) {
        int vanaAeg = aeg; // Määrame vanaAeg muutuja väärtuse enne sõna arvamist
        boolean result = super.mängijaArvab(sõna, tähed);
        if (sõna.equalsIgnoreCase("+")) {
            aeg -= 1; // iga uue tähe küsimine võtab maha sekundi
        } else if (result) {
            aeg += 5; // iga õige sõna lisab 5 sekundit
        } else {
            aeg -= 5; // iga vale sõna vähendab aega 5 sekundi võrra
        }
        int ajaMuutus = aeg - vanaAeg;
        System.out.println("Aega " + (ajaMuutus > 0 ? "juurde: " : "maha: ") + Math.abs(ajaMuutus) + " sekundit.");
        System.out.println("Alles on " + aeg + " sekundit.");
        return result;
    }
}
