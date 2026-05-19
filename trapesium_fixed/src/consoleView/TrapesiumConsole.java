/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consoleView; //berada di package consoleView

import java.util.Scanner;
import BendaGeometri.Trapesium; //mengambil class Trapesium dari package bendageometri

public class TrapesiumConsole {
    public static void show() {
        Scanner input = new Scanner(System.in); //membaca input
        
        System.out.println("* TRAPESIUM *");
        //input data
        double sisiAtas = getDoubleInput(input, "Masukkan sisi atas (cm): ");
        double sisiBawah = getDoubleInput(input, "Masukkan sisi bawah (cm): ");
        double tinggi = getDoubleInput(input, "Masukkan tinggi (cm): ");
        
        Trapesium t = new Trapesium(sisiAtas, sisiBawah, tinggi);
        //menampilkan hasil
        System.out.println("\n--- HASIL PERHITUNGAN ---");
        System.out.printf("Luas Trapesium: %.2f cm²%n", t.hitungLuas());
        System.out.printf("Keliling Trapesium: %.2f cm%n", t.hitungKeliling());
        System.out.println();
    }
    //encapsulation, method dibuat private sehingga hanya dapat digunakan dalam class ini saja
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