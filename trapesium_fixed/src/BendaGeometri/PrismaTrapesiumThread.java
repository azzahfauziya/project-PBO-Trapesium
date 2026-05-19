/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BendaGeometri;

// [OOP - CLASS] PrismaTrapesiumThread adalah class yang mengimplementasikan
// interface Runnable agar dapat dijalankan sebagai Thread secara paralel
// [OOP - INHERITANCE/INTERFACE] implements Runnable merupakan bentuk implementasi
// interface — class ini "mewarisi kontrak" dari Runnable untuk menyediakan method run()
public class PrismaTrapesiumThread implements Runnable {

    // [OOP - ENCAPSULATION] Atribut 'nomor' dideklarasikan private agar hanya
    // dapat diakses dan dimodifikasi dari dalam class ini sendiri
    private int nomor; // nomor urut thread untuk identifikasi saat output ditampilkan

    // [OOP - OBJECT] Constructor berparameter — digunakan untuk membuat objek
    // PrismaTrapesiumThread dengan nomor urut tertentu saat instansiasi
    public PrismaTrapesiumThread(int nomor) {
        this.nomor = nomor; // menyimpan nomor urut thread ke atribut private
    }

    // [OOP - POLYMORPHISM] Method run() merupakan bentuk method overriding —
    // method ini menimpa (override) implementasi run() dari interface Runnable
    // sehingga perilakunya disesuaikan dengan kebutuhan class ini
    // Anotasi @Override memastikan compiler memvalidasi bahwa method ini
    // benar-benar menimpa method dari interface/superclass
    @Override
    public void run() {
        try {
            // menunda eksekusi thread secara acak antara 0–300 ms
            // untuk mensimulasikan proses yang membutuhkan waktu berbeda-beda
            Thread.sleep((long)(Math.random() * 300));
        } catch (InterruptedException e) {
            // menangani exception jika thread dihentikan paksa saat sedang tidur
            e.printStackTrace();
        }

        // mengambil nama thread yang sedang berjalan dari JVM untuk ditampilkan di output
        String threadName = Thread.currentThread().getName();

        // membangkitkan nilai dimensi trapesium secara acak dalam rentang tertentu
        double sisiAtas    = 5 + Math.random() * 15; // sisi atas trapesium: 5–20 cm
        double sisiBawah   = 5 + Math.random() * 15; // sisi bawah trapesium: 5–20 cm
        double tinggiAlas  = 5 + Math.random() * 10; // tinggi alas trapesium: 5–15 cm
        double tinggiPrisma = 5 + Math.random() * 15; // tinggi prisma: 5–20 cm

        // [OOP - OBJECT] Membuat objek baru dari class PrismaTrapesium
        // menggunakan constructor berparameter — setiap thread membuat objek sendiri
        // sehingga data antar thread tidak saling mengganggu (thread-safe per objek)
        PrismaTrapesium pt = new PrismaTrapesium(sisiAtas, sisiBawah, tinggiAlas, tinggiPrisma);

        // menampilkan identitas thread yang sedang mengeksekusi perhitungan ini
        System.out.printf("Thread Prisma Trapesium #%d (%s)%n", nomor, threadName);

        // menampilkan dimensi alas trapesium yang dibangkitkan secara acak
        System.out.printf("Sisi Atas: %.2f cm | Sisi Bawah: %.2f cm | Tinggi Alas: %.2f cm%n",
                          sisiAtas, sisiBawah, tinggiAlas);

        // menampilkan tinggi prisma yang dibangkitkan secara acak
        System.out.printf("Tinggi Prisma: %.2f cm%n", tinggiPrisma);

        // [OOP - ABSTRACTION] Memanggil hitungVolume() — pengguna (thread) tidak perlu
        // tahu detail rumus, cukup memanggil method dan mendapatkan hasilnya
        System.out.printf("Volume: %.2f cm³%n", pt.hitungVolume());

        // [OOP - ABSTRACTION] Memanggil hitungLuasPermukaan() — kompleksitas rumus
        // luas permukaan prisma sepenuhnya tersembunyi di dalam class PrismaTrapesium
        System.out.printf("Luas Permukaan: %.2f cm²%n", pt.hitungLuasPermukaan());

        System.out.println(); // baris kosong sebagai pemisah antar output thread
    }
}
