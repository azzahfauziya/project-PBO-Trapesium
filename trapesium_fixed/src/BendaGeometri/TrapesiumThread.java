/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BendaGeometri;

// mengimplementasikan Runnable utk menjalankan perhitungan trapesium dalam thread terpisah
// dgn tujuan Demonstrasi pemrosesan multithreading utk menghitung luas dan keliling trapesium
public class TrapesiumThread implements Runnable {
    private int nomor;      // Nomor identifikasi utk thread ini
    
    // Konstruktor, menerima nomor thread sebagai parameter
    public TrapesiumThread(int nomor) {
        this.nomor = nomor;
    }
    
    // Method utama yg akan dijalankan saat thread dimulai (wajib diimplementasi dari Runnable)
    @Override
    public void run() {
        try {
            // Memberikan jeda acak 0-300ms untuk mensimulasikan:
            // 1. Simulasi workload/processing time
            // 2. Membuat thread berjalan tidak serempak (menunjukkan sifat asinkron)
            // 3. Memungkinkan scheduler thread untuk melakukan context switching
            Thread.sleep((long)(Math.random() * 300));
        } catch (InterruptedException e) {
            // Menangkap exception jika thread diinterupsi saat sleep
            e.printStackTrace();
        }
        
        // Mendapatkan nama thread yang sedang berjalan, contoh: Thread-0, Thread-1, dll
        String threadName = Thread.currentThread().getName();
        
        // Membangkitkan nilai acak untuk sisi-sisi trapesium (5-25 cm)
        // Math.random() menghasilkan nilai 0.0 - 1.0, dikalikan 20 hasilnya 0-20, ditambah 5 jadi 5-25
        double sisiSejajar1 = 5 + Math.random() * 20;
        double sisiSejajar2 = 5 + Math.random() * 20;
        double tinggi = 5 + Math.random() * 20;
        
        // Membuat objek Trapesium dengan nilai-nilai acak yang telah dibangkitkan
        Trapesium tp = new Trapesium(sisiSejajar1, sisiSejajar2, tinggi);
        
        // DEMONSTRASI METHOD OVERLOADING
        // Menghitung luas menggunakan method tanpa parameter (menggunakan nilai dari atribut objek)
        double luas1 = tp.hitungLuas();
        // Menghitung luas menggunakan method dengan parameter (overloading dari method di atas)
        double luas2 = tp.hitungLuas(sisiSejajar1, sisiSejajar2, tinggi);
        // Menghitung keliling menggunakan method tanpa parameter
        double keliling1 = tp.hitungKeliling();
        // Menghitung keliling menggunakan method dengan parameter (overloading)
        double keliling2 = tp.hitungKeliling(sisiSejajar1, sisiSejajar2, tinggi);
        
        // OUTPUT HASIL PERHITUNGAN
        // Menampilkan header thread dengan nomor identitas dan nama thread
        System.out.printf("Thread Trapesium #%d (%s)%n", nomor, threadName);
        // Menampilkan nilai-nilai input (sisi dan tinggi) dengan 2 digit desimal
        System.out.printf("Sisi Sejajar 1: %.2f cm | Sisi Sejajar 2: %.2f cm | Tinggi: %.2f cm%n", sisiSejajar1, sisiSejajar2, tinggi);
        // Menampilkan hasil luas dari kedua method untuk perbandingan (hasilnya harus sama)
        System.out.printf("Luas (default): %.2f cm²%n", luas1);
        System.out.printf("Luas (overload): %.2f cm²%n", luas2);
        // Menampilkan hasil keliling dari kedua method untuk perbandingan (hasilnya harus sama)
        System.out.printf("Keliling (default): %.2f cm%n", keliling1);
        System.out.printf("Keliling (overload): %.2f cm%n", keliling2);
        System.out.println();
    }
}