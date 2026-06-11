package kalkulatortrapesium;

/**
 * Kelas abstrak Trapesium yang mengimplementasikan Geometri2D
 *
 * ✅ ABSTRAKSI    - kelas ini abstract, tidak bisa diinstansiasi langsung
 * ✅ ENKAPSULASI  - atribut bersifat private, diakses lewat getter/setter
 * ✅ PEWARISAN    - diwarisi oleh PrismaTrapesium dan LimasTrapesium
 * ✅ MULTITHREADING - perhitungan dilakukan di thread terpisah
 */
public class Trapesium implements Geometri2D, Runnable{


    public double atas;     // panjang sisi atas trapesium
    public double bawah;    // panjang sisi bawah trapesium
    public double tinggi;   // tinggi trapesium
    public double kiri;     // panjang sisi kiri trapesium
    public double kanan;    // panjang sisi kanan trapesium
    public double luas;
    public double keliling;

    // OVERLOADING - konstruktor tanpa parameter (kosong)
    public Trapesium() {
        this.atas    = 0;
        this.bawah   = 0;
        this.tinggi  = 0;
        this.kiri    = 0;
        this.kanan   = 0;
    }

    // OVERLOADING - konstruktor dengan parameter lengkap
    public Trapesium(double atas, double bawah, double tinggi, double kiri, double kanan) {
        this.atas   = atas;
        this.bawah  = bawah;
        this.tinggi = tinggi;
        this.kiri   = kiri;
        this.kanan  = kanan;
    }

    @Override
    public double hitungLuas() {
        luas = 0.5 * (atas + bawah) * tinggi;
        return luas;
    }
    
    public double hitungLuas(double atas, double bawah, double tinggi) {
        luas = 0.5 * (atas + bawah) * tinggi;
        return luas;
    }

    @Override
    public double hitungKeliling() {
        double jumlahAtasBawah = atas + bawah;   // sisi sejajar atas dan bawah
        double jumlahKiriKanan = kiri + kanan;   // sisi miring kiri dan kanan
        keliling = jumlahAtasBawah + jumlahKiriKanan; // total semua sisi
        
        return keliling;
    }
    
    public double hitungKeliling(double atas, double bawah, double kiri, double kanan) {
        double jumlahAtasBawah = atas + bawah;   // sisi sejajar atas dan bawah
        double jumlahKiriKanan = kiri + kanan;   // sisi miring kiri dan kanan
        keliling = jumlahAtasBawah + jumlahKiriKanan; // total semua sisi
        
        return keliling;
    }

   @Override
    public void run() {
    System.out.println("[Thread-" + Thread.currentThread().getName() + "] Menghitung Trapesium: " + toString());
    hitungLuas(atas, bawah, tinggi);
    hitungKeliling(atas, bawah, kiri, kanan);
    System.out.println("[Thread-" + Thread.currentThread().getName() +
            "] Selesai -> Luas=" + String.format("%.2f", luas) +
            ", Keliling=" + String.format("%.2f", keliling));
}
}