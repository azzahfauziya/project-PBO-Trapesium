/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BendaGeometri;

public class LimasTrapesiumThread implements Runnable {
    private int nomor;
    
    // constructor untuk kasih nomor thread
    public LimasTrapesiumThread(int nomor) {
        this.nomor = nomor;
    }
    
    // method run() otomatis dijalankan saat thread dimulai
    @Override
    public void run() {
        try {
            //kasih jeda acar supaya simulasi thread terlihat berjalan berbeda???
            Thread.sleep((long)(Math.random() * 300));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        //ambil nama thread aktif
        String threadName = Thread.currentThread().getName();
        
        // generate data random untuk simulasi supaya thread punya data limas yang beda
        double sisiAtas = 5 + Math.random() * 15;
        double sisiBawah = 5 + Math.random() * 15;
        double tinggiAlas = 5 + Math.random() * 10;
        double tinggiLimas = 5 + Math.random() * 15;
        double tinggiSisiTegak1 = 5 + Math.random() * 10;
        double tinggiSisiTegak2 = 5 + Math.random() * 10;
        
        
        //membuat object limas 
        LimasTrapesium lt = new LimasTrapesium(sisiAtas, sisiBawah, tinggiAlas, 
                                                tinggiLimas, tinggiSisiTegak1, tinggiSisiTegak2);
        
        System.out.printf("Thread Limas Trapesium #%d (%s)%n", nomor, threadName);
        System.out.printf("Sisi Atas: %.2f cm | Sisi Bawah: %.2f cm | Tinggi Alas: %.2f cm%n", 
                          sisiAtas, sisiBawah, tinggiAlas);
        System.out.printf("Tinggi Limas: %.2f cm | Tinggi Tegak1: %.2f cm | Tinggi Tegak2: %.2f cm%n",
                          tinggiLimas, tinggiSisiTegak1, tinggiSisiTegak2);
        System.out.printf("Volume: %.2f cm³%n", lt.hitungVolume());
        System.out.printf("Luas Permukaan: %.2f cm²%n", lt.hitungLuasPermukaan());
        System.out.println();
    }
}