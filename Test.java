/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Priklad_3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author livhc
 */
public class Test {

    private Path soubor;
    private Map<String, List<String>> slovnik = new HashMap<>();
    private Map<String, List<String>> vyber = new HashMap<>();
    private List<String> slovnikL = new ArrayList<>();
    private Random random = new Random();
    private Scanner sc = new Scanner(System.in, "Windows-1250");
    private int body = 0;
    private int delkaTestu;

    public Test(Path soubor) {
        this.soubor = soubor;
    }

    public void nacti() throws IOException {
        slovnikL = Files.readAllLines(soubor, Charset.forName("Windows-1250"));
    }

    public void slovnik() {
        slovnikL.stream().map(radek -> {
            String[] radekL = radek.split("    ");
            if (radekL.length != 2) {
                radekL = radek.split("   ");
            }
            if (radekL.length != 2) {
                radekL = radek.split("  ");
            }
            if (radekL.length != 2) {
                radekL = radek.split(" ");
            }
            return radekL;
        }).forEachOrdered(radekL -> {
            List<String> radekA = 
                    new ArrayList<>(Arrays.asList(radekL[1].split(",")));
            slovnik.put(radekL[0], radekA);
        });
    }

    public void test() {
        List<String> klice = new ArrayList<>(slovnik.keySet());
        List<String> nahodneKlice = new ArrayList<>();
        for (int i = 0; i < delkaTestu; i++) {
            String nahodnyKlic = klice.get(random.nextInt(klice.size()));
            if (!nahodneKlice.contains(nahodnyKlic)) {
                nahodneKlice.add(nahodnyKlic);
            }
        }

        nahodneKlice.forEach(klic -> {
            vyber.put(klic, slovnik.get(klic));
        });

        vyber.keySet().stream().map(klic -> {
            System.out.printf("Co znamená %s?\n", klic);
            return klic;
        }).forEachOrdered(klic -> {
            String odpoved = sc.nextLine().trim().toLowerCase();
            if (vyber.get(klic).contains(odpoved)) {
                System.out.println("Výborně! Pojďme dál.");
                body++;
            } else {
                String spravne = "";
                for (int i = 0; i < vyber.get(klic).size(); i++) {
                    spravne += vyber.get(klic).get(i);
                    if (i != vyber.get(klic).size() - 1) {
                        spravne += ", "; 
                    }
                }
                System.out.printf("Správný překlad je %s. Nevadí, "
                        + "zkusme to ještě jednou.\n",
                        spravne.trim());
            }
        });
        vyber.clear();
    }

    public int getBody() {
        return body;
    }

    public int getDelkaTestu() {
        return delkaTestu;
    }

    public void setDelkaTestu(int delkaTestu) {
        this.delkaTestu = delkaTestu;
    }

    public void setBody(int body) {
        this.body = body;
    }    
}
