/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
//import library & class
import GUI.GUIPage; //untuk tampilan GUI
import consoleView.*; //mengimpor semua class di consoleView
import BendaGeometri.*; //mengimpor semua class-thread di package BendaGeometri
import java.util.Scanner; //untuk menerima input dari

public class Main {
    public static void main(String[] args) {
        //membuat scanner dan variabel loop, scanner = membaca input | loop = mengulang hingga user pilih keluar
        Scanner inputUser = new Scanner(System.in);
        boolean loop = true;
        
        System.out.println("=".repeat(50));
        System.out.println("     PROGRAM GEOMETRI TRAPESIUM");
        System.out.println("=".repeat(50));
        
        //menampilkan menu
        while (loop) {
            System.out.println("\n--- MENU UTAMA ---");
            System.out.println("[1] Trapesium (2D) - Console");
            System.out.println("[2] Limas Trapesium (3D) - Console");
            System.out.println("[3] Prisma Trapesium (3D) - Console");
            System.out.println("[4] Trapesium (2D) - Multi-thread");
            System.out.println("[5] Limas Trapesium (3D) - Multi-thread");
            System.out.println("[6] Prisma Trapesium (3D) - Multi-thread");
            System.out.println("[7] Buka GUI");
            System.out.println("[0] Keluar");
            System.out.print("Pilih menu: ");
            
            int pilihMenu = 0;
            try {
                pilihMenu = inputUser.nextInt();
                inputUser.nextLine();
            } catch (Exception e) {
                System.out.println("Input harus angka!");
                inputUser.nextLine();
                continue;
            }
            
            int jumlahLooping;
            
            switch (pilihMenu) { // switch untuk menentukan aksi berdasar menu
                case 1:
                    TrapesiumConsole.show();
                    break;
                case 2:
                    LimasTrapesiumConsole.show();
                    break;
                case 3:
                    PrismaTrapesiumConsole.show();
                    break;
                case 4: //multi-thread
                    System.out.print("Berapa kali perhitungan? ");
                    jumlahLooping = inputUser.nextInt();
                    for (int i = 1; i <= jumlahLooping; i++) { //membuat thread baru sebanyak jumlahLooping
                        new Thread(new TrapesiumThread(i)).start(); //tiap thread menjalankan TrapesiumThread(i)
                    }
                    try { Thread.sleep(1000); } catch (Exception e) {} //memberi jeda 1 detik agar thread sempat selesai sebelum menu muncul kembali
                    break;
                case 5: //multi-thread
                    System.out.print("Berapa kali perhitungan? ");
                    jumlahLooping = inputUser.nextInt();
                    for (int i = 1; i <= jumlahLooping; i++) {
                        new Thread(new LimasTrapesiumThread(i)).start();
                    }
                    try { Thread.sleep(1000); } catch (Exception e) {}
                    break;
                case 6: //multi-thread
                    System.out.print("Berapa kali perhitungan? ");
                    jumlahLooping = inputUser.nextInt();
                    for (int i = 1; i <= jumlahLooping; i++) {
                        new Thread(new PrismaTrapesiumThread(i)).start();
                    }
                    try { Thread.sleep(1000); } catch (Exception e) {}
                    break;
                case 7:
                    java.awt.EventQueue.invokeLater(() -> {
                        new GUIPage().setVisible(true);
                    });
                    break;
                case 0:
                    System.out.println("Terima kasih!");
                    loop = false;
                    break;
                default:
                    System.out.println("Menu tidak tersedia!");
            }
        }
        inputUser.close();
    }
}