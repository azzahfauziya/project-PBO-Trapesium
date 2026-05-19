/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BendaGeometri;

// [OOP - INHERITANCE] Trapesium extends Geometri2D yang implements BendaGeometri
// sehingga Trapesium mewarisi semua method abstract dari Geometri2D
// Kelas utk merepresentasikan bangun datar Trapesium
public class Trapesium extends Geometri2D {
    // Atribut protected agar dapat diakses oleh kelas turunan
    protected double sisiSejajar1;
    protected double sisiSejajar2;
    protected double tinggi;

    public Trapesium() {
        // [OOP - INHERITANCE] Memanggil konstruktor superclass Geometri2D
        super("Trapesium");
        // Konstruktor default, menginisialisasi semua nilai dengan 0
        this.sisiSejajar1 = 0;
        this.sisiSejajar2 = 0;
        this.tinggi = 0;
    }

    // Konstruktor overloading dengan parameter double
    public Trapesium(double sisiSejajar1, double sisiSejajar2, double tinggi) {
        // [OOP - INHERITANCE] Memanggil konstruktor superclass Geometri2D
        super("Trapesium");
        this.sisiSejajar1 = sisiSejajar1;
        this.sisiSejajar2 = sisiSejajar2;
        this.tinggi = tinggi;
    }

    // Konstruktor overloading dengan parameter int (konversi otomatis ke double)
    public Trapesium(int sisiSejajar1, int sisiSejajar2, int tinggi) {
        this((double) sisiSejajar1, (double) sisiSejajar2, (double) tinggi);
    }

    // [OOP - INHERITANCE] Override method abstract hitungLuas() dari Geometri2D
    // Method menghitung luas trapesium menggunakan atribut
    // Rumus luas = 1/2 × (sisi sejajar1 + sisi sejajar2) × tinggi
    @Override
    public double hitungLuas() throws IllegalArgumentException {
        // Validasi, semua sisi dan tinggi harus positif
        if (sisiSejajar1 <= 0 || sisiSejajar2 <= 0 || tinggi <= 0) {
            throw new IllegalArgumentException("Panjang sisi dan tinggi tidak boleh 0 atau negatif");
        }
        return 0.5 * (sisiSejajar1 + sisiSejajar2) * tinggi;
    }

    // Method overloading menghitung luas dgn parameter langsung
    public double hitungLuas(double sisiSejajar1, double sisiSejajar2, double tinggi) throws IllegalArgumentException {
        if (sisiSejajar1 <= 0 || sisiSejajar2 <= 0 || tinggi <= 0) {
            throw new IllegalArgumentException("Panjang sisi dan tinggi tidak boleh 0 atau negatif");
        }
        return 0.5 * (sisiSejajar1 + sisiSejajar2) * tinggi;
    }

    // [OOP - INHERITANCE] Override method abstract hitungKeliling() dari Geometri2D
    // Method menghitung keliling trapesium menggunakan atribut
    // Keliling = jumlah semua sisi = sisi1 + sisi2 + sisi miring kiri + sisi miring kanan
    @Override
    public double hitungKeliling() {
        // Validasi input
        if (sisiSejajar1 <= 0 || sisiSejajar2 <= 0 || tinggi <= 0) {
            throw new IllegalArgumentException("Panjang sisi dan tinggi tidak boleh 0 atau negatif");
        }
        // Menghitung setengah selisih sisi sejajar untuk mencari panjang sisi miring
        double selisihSisiSejajar = Math.abs(sisiSejajar1 - sisiSejajar2) / 2;
        // Menggunakan teorema Pythagoras untuk mencari sisi miring
        double sisiMiring = Math.sqrt(Math.pow(selisihSisiSejajar, 2) + Math.pow(tinggi, 2));
        return (sisiSejajar1 + sisiSejajar2) + (2 * sisiMiring);
    }

    // Method overloading menghitung keliling dgn parameter langsung
    public double hitungKeliling(double sisiSejajar1, double sisiSejajar2, double tinggi) {
        if (sisiSejajar1 <= 0 || sisiSejajar2 <= 0 || tinggi <= 0) {
            throw new IllegalArgumentException("Panjang sisi dan tinggi tidak boleh 0 atau negatif");
        }
        double selisihSisiSejajar = Math.abs(sisiSejajar1 - sisiSejajar2) / 2;
        double sisiMiring = Math.sqrt(Math.pow(selisihSisiSejajar, 2) + Math.pow(tinggi, 2));
        return (sisiSejajar1 + sisiSejajar2) + (2 * sisiMiring);
    }

    // Method utk mendapatkan panjang sisi miring trapesium (getter)
    public double getSisiMiring() {
        double selisihSisiSejajar = Math.abs(sisiSejajar1 - sisiSejajar2) / 2;
        return Math.sqrt(Math.pow(selisihSisiSejajar, 2) + Math.pow(tinggi, 2));
    }

    // Getter methods (accessor) utk mengakses nilai atribut private/protected
    public double getSisiSejajar1() { return sisiSejajar1; }
    public double getSisiSejajar2() { return sisiSejajar2; }
    public double getTinggi() { return tinggi; }
}
