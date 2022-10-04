/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Priklad_3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author livhc
 */
public class Rekord {

    Path soubor;
    String[] rozdeleno = null;

    public Rekord(Path soubor) {
        this.soubor = soubor;
        this.rozdeleno = rozdeleno;
    }

    public void nacti() throws IOException {
        try {
            //projdeme v�echny ��dky v souboru, ka�d� rozd�l�me na ��sti podle st�edn�k�
            //rozd�len� ��sti pak pou�ijeme pro p�id�n� nov�ho u�ivatele
            for (String radek : Files.readAllLines(soubor)) {
                try {
                    this.rozdeleno = radek.split(";");
                } catch (Exception e) {
                    //this.rozdeleno = null;
                }
            }
        } catch (IOException ex) {
            System.out.println(
                    "Rekord se nepodařilo načíst, soubor zřejmě neexistuje: "
                    + ex.getMessage());
        }
    }
    
    public boolean porovnej(int body, long cas) {
        if (this.rozdeleno == null) {
            return true;
        } else {
            return (body >= Integer.parseInt(this.rozdeleno[1]))
                    && (cas <= Long.parseLong(this.rozdeleno[2]));
        }
    }
    
    public void uloz(String jmeno, int body, long cas) throws IOException {
        Files.writeString(soubor, "", StandardOpenOption.TRUNCATE_EXISTING);
        Files.writeString(soubor, 
                jmeno.replace(";", ",") + ";" + body + ";" + cas, 
                StandardOpenOption.APPEND);
    }

    @Override
    public String toString() {
        if (this.rozdeleno == null) {
            return "";
        } else {
            return String.format("Nynější rekord drží %s s %s body.",
                    this.rozdeleno[0], this.rozdeleno[1]);
        }
    }
}
