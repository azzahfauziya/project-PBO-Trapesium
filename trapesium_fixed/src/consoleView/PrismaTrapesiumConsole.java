/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consoleView;

import java.util.Scanner;
import BendaGeometri.PrismaTrapesium;

public class PrismaTrapesiumConsole {
    public static void show() {
        Scanner input = new Scanner(System.in);
        
        System.out.println("*** PRISMA TRAPESIUM ***");
        
        double sisiAtas = getDoubleInput(input, "Masukkan sisi atas (cm): ");
        double sisiBawah = getDoubleInput(input, "Masukkan sisi bawah (cm): ");
        double tinggiAlas = getDoubleInput(input, "Masukkan tinggi alas (cm): ");
        double tinggiPrisma = getDoubleInput(input, "Masukkan tinggi prisma (cm): ");
        
        PrismaTrapesium prisma = new PrismaTrapesium(sisiAtas, sisiBawah, tinggiAlas, tinggiPrisma);
        
        System.out.println("\n--- HASIL PERHITUNGAN ---");
        System.out.printf("Volume Prisma Trapesium: %.2f cm³%n", prisma.hitungVolume());
        System.out.printf("Luas Permukaan Prisma Trapesium: %.2f cm²%n", prisma.hitungLuasPermukaan());
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