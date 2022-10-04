/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Priklad_3;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 *
 * @author livhc
 */
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Rekord rekord;
        Test test;
        
        try {
            Path path = Paths.get("rekord.csv");
            rekord = new Rekord(path);
            rekord.nacti();
        } catch (IOException ex) {
            System.out.println("Problém při načtení souboru s rekordem: "
                    + ex.getMessage());
            return;
        }
        
        try {
            Path path = Paths.get("slovnik.txt");
            test = new Test(path);
            test.nacti();
        } catch (IOException ex) {
            System.out.println("Problém při načtení souboru se slovníkem: "
                    + ex.getMessage());
            return;
        }
        test.slovnik();

        System.out.println("Vítej v testu anglických slovíček\n" 
                + "=================================");
        try {
            System.out.println(rekord.toString());
        } catch (Exception e) {}
        
        test.setDelkaTestu(10);
        System.out.print("Nový test spustíš stiskem klávesy enter ...");
        sc.nextLine();
        String pokracuj;
        String bodyZprava;
        do {
            LocalDateTime start = LocalDateTime.now();
            test.test();
            long cas = ChronoUnit.SECONDS.between(start, LocalDateTime.now());
            if ((test.getBody() == 0) || 
                    ((test.getBody() >= 2) && (test.getBody() <= 4))) {
                bodyZprava = "bodů";
            } else if (test.getBody() == 1) {
                bodyZprava = "bod";
            } else {
                bodyZprava = "body";
            }
            System.out.printf("Výborně, získla jsi %s %s z %s s časem %s vteřin.",
                    test.getBody(), bodyZprava, test.getDelkaTestu(), cas);
            boolean zapisRekord = rekord.porovnej(test.getBody(), cas);
            if (zapisRekord) {
                System.out.print("Máš nový rekord!\n" + 
                        "Zadej své jméno, génie: ");
                String jmeno = sc.nextLine();
                try {
                    rekord.uloz(jmeno, test.getBody(), cas);
                } catch (IOException ex) {
                    System.out.println("Nepodařilo se uložit nový rekord: "
                    + ex.getMessage());
                }
            }
            System.out.println("Chceš pokračovat? [a/n]");
            pokracuj = sc.nextLine();
            if (pokracuj.equals("a")) {
                test.setBody(0);
            }
        } while (pokracuj.equals("a"));
    }
}
