/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BendaGeometri;

// [OOP - CLASS]
// [OOP - INHERITANCE] PrismaTrapesium extends Geometri3D yang implements BendaGeometri
// sehingga PrismaTrapesium mewarisi semua method abstract dari Geometri3D
public class PrismaTrapesium extends Geometri3D {

    // [OOP - ENCAPSULATION] Atribut dideklarasikan private agar tidak bisa diakses langsung dari luar class
    private Trapesium alas;       // objek alas bertipe Trapesium (komposisi/aggregasi)
    private double tinggiPrisma;  // tinggi prisma dalam satuan cm

    // [OOP - OBJECT] Constructor default — digunakan untuk membuat objek
    // PrismaTrapesium tanpa parameter, nilai alas dan tinggi diinisialisasi default (0)
    public PrismaTrapesium() {
        // [OOP - INHERITANCE] Memanggil konstruktor superclass Geometri3D
        super("Prisma Trapesium");
        this.alas = new Trapesium(); // membuat objek Trapesium kosong sebagai alas
        this.tinggiPrisma = 0;       // tinggi prisma default = 0
    }

    // [OOP - OBJECT] Constructor berparameter — digunakan untuk membuat objek
    // PrismaTrapesium dengan nilai yang ditentukan saat instansiasi
    public PrismaTrapesium(double sisiAtas, double sisiBawah, double tinggiAlas, double tinggiPrisma) {
        // [OOP - INHERITANCE] Memanggil konstruktor superclass Geometri3D
        super("Prisma Trapesium");
        // membuat objek Trapesium dengan ukuran yang diberikan sebagai alas prisma
        this.alas = new Trapesium(sisiAtas, sisiBawah, tinggiAlas);
        this.tinggiPrisma = tinggiPrisma; // menyimpan tinggi prisma ke atribut
    }

    // [OOP - INHERITANCE] Override method abstract hitungVolume() dari Geometri3D
    // [OOP - ABSTRACTION] Method hitungVolume() menyembunyikan detail perhitungan
    // volume dari pengguna — pengguna cukup memanggil method ini tanpa tahu cara kerjanya
    // Rumus Volume Prisma = Luas Alas × Tinggi Prisma
    @Override
    public double hitungVolume() {
        double luasAlas = alas.hitungLuas(); // memanggil method dari objek Trapesium
        return luasAlas * tinggiPrisma;      // mengembalikan hasil perhitungan volume
    }

    // [OOP - POLYMORPHISM] Method hitungVolume() di-overload (method overloading)
    // — method dengan nama sama namun menerima parameter berbeda (luas alas & tinggi
    // diberikan langsung), sehingga memiliki perilaku yang lebih fleksibel
    public double hitungVolume(double luasAlas, double tinggiPrisma) {
        return luasAlas * tinggiPrisma; // menghitung volume menggunakan parameter yang diberikan
    }

    // [OOP - INHERITANCE] Override method abstract hitungLuasPermukaan() dari Geometri3D
    // [OOP - ABSTRACTION] Method hitungLuasPermukaan() mengabstraksi perhitungan
    // luas permukaan prisma — kompleksitas perhitungan disembunyikan dari pengguna
    // Rumus: Luas Permukaan = (2 × Luas Alas) + (Keliling Alas × Tinggi Prisma)
    @Override
    public double hitungLuasPermukaan() {
        double luasAlas = alas.hitungLuas();         // ambil luas alas dari objek Trapesium
        double kelilingAlas = alas.hitungKeliling(); // ambil keliling alas dari objek Trapesium
        return (2 * luasAlas) + (kelilingAlas * tinggiPrisma); // rumus luas permukaan prisma
    }

    // [OOP - POLYMORPHISM] Method hitungLuasPermukaan() di-overload (method overloading)
    // — versi alternatif yang menerima parameter luas alas, keliling alas, dan tinggi prisma
    // secara langsung tanpa bergantung pada atribut internal objek
    public double hitungLuasPermukaan(double luasAlas, double kelilingAlas, double tinggiPrisma) {
        return (2 * luasAlas) + (kelilingAlas * tinggiPrisma); // menggunakan parameter langsung
    }

    // [OOP - ENCAPSULATION] Getter method getAlas() — menyediakan akses baca (read-only)
    // terhadap atribut private 'alas' dari luar class, menjaga integritas data
    public Trapesium getAlas() { return alas; }

    // [OOP - ENCAPSULATION] Getter method getTinggiPrisma() — menyediakan akses baca
    // terhadap atribut private 'tinggiPrisma', tanpa mengizinkan perubahan langsung
    public double getTinggiPrisma() { return tinggiPrisma; }
}
