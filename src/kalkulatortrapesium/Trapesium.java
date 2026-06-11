package kalkulatortrapesium;

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
        validasi(atas, bawah, tinggi, kiri, kanan);
        this.atas   = atas;
        this.bawah  = bawah;
        this.tinggi = tinggi;
        this.kiri   = kiri;
        this.kanan  = kanan;
    }

    // VALIDASI - dipanggil oleh constructor Trapesium dan subclass-nya
    protected void validasi(double atas, double bawah, double tinggi, double kiri, double kanan) {
        if (atas <= 0 || bawah <= 0 || tinggi <= 0 || kiri <= 0 || kanan <= 0) {
            throw new IllegalArgumentException("Semua sisi dan tinggi harus bernilai lebih besar dari 0.");
        }
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
    public double hitungKeliling() {  // sisi miring kiri dan kanan
        keliling = atas + bawah + kanan + kiri; // total semua sisi
        return keliling;
    }
    
    public double hitungKeliling(double atas, double bawah, double kiri, double kanan) {
        keliling = atas + bawah + kanan + kiri; // total semua sisi
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