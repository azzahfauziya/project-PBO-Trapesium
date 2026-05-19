/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consoleView;

import java.util.Scanner;
import BendaGeometri.LimasTrapesium;

public class LimasTrapesiumConsole {
    public static void show() {
        Scanner input = new Scanner(System.in);
        
        System.out.println("*** LIMAS TRAPESIUM ***");
        
        double sisiAtas = getDoubleInput(input, "Masukkan sisi atas (cm): ");
        double sisiBawah = getDoubleInput(input, "Masukkan sisi bawah (cm): ");
        double tinggiAlas = getDoubleInput(input, "Masukkan tinggi alas (cm): ");
        double tinggiLimas = getDoubleInput(input, "Masukkan tinggi limas (cm): ");
        double tinggiTegak1 = getDoubleInput(input, "Masukkan tinggi sisi tegak 1 (cm): ");
        double tinggiTegak2 = getDoubleInput(input, "Masukkan tinggi sisi tegak 2 (cm): ");
        
        LimasTrapesium limas = new LimasTrapesium(sisiAtas, sisiBawah, tinggiAlas, 
                                                   tinggiLimas, tinggiTegak1, tinggiTegak2);
        
        System.out.println("\n--- HASIL PERHITUNGAN ---");
        System.out.printf("Volume Limas Trapesium: %.2f cm³%n", limas.hitungVolume());
        System.out.printf("Luas Permukaan Limas Trapesium: %.2f cm²%n", limas.hitungLuasPermukaan());
        System.out.println();
    }
    
    private static double getDoubleInput(Scanner input, String prompt) {
        double value = 0;
        while (true) {
            try {
                System.out.print(prompt);
                value = Double.parseDouble(input.nextLine());
                if (value <= 0) {
                    System.out.println("Nilai harus lebih dari 0!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        }
        return value;
    }
}