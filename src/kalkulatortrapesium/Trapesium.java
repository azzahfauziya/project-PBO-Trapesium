package kalkulatortrapesium;

/**
 * Kelas abstrak Trapesium yang mengimplementasikan Geometri2D
 *
 * ✅ ABSTRAKSI    - kelas ini abstract, tidak bisa diinstansiasi langsung
 * ✅ ENKAPSULASI  - atribut bersifat private, diakses lewat getter/setter
 * ✅ PEWARISAN    - diwarisi oleh PrismaTrapesium dan LimasTrapesium
 * ✅ MULTITHREADING - perhitungan dilakukan di thread terpisah
 */
public abstract class Trapesium implements Geometri2D {

    // ✅ ENKAPSULASI - semua atribut private
    private double atas;     // panjang sisi atas trapesium
    private double bawah;    // panjang sisi bawah trapesium
    private double tinggi;   // tinggi trapesium
    private double kiri;     // panjang sisi kiri trapesium
    private double kanan;    // panjang sisi kanan trapesium

    // Hasil perhitungan disimpan agar bisa diakses setelah thread selesai
    private double hasilLuas;
    private double hasilKeliling;

    // ✅ OVERLOADING - konstruktor tanpa parameter (kosong)
    public Trapesium() {
        this.atas    = 0;
        this.bawah   = 0;
        this.tinggi  = 0;
        this.kiri    = 0;
        this.kanan   = 0;
    }

    // ✅ OVERLOADING - konstruktor dengan parameter lengkap
    public Trapesium(double atas, double bawah, double tinggi, double kiri, double kanan) {
        this.atas   = atas;
        this.bawah  = bawah;
        this.tinggi = tinggi;
        this.kiri   = kiri;
        this.kanan  = kanan;
    }

    // ======================== GETTER & SETTER ========================

    public double getAtas()    { return atas; }
    public double getBawah()   { return bawah; }
    public double getTinggi()  { return tinggi; }
    public double getKiri()    { return kiri; }
    public double getKanan()   { return kanan; }

    public void setAtas(double atas)      { this.atas = atas; }
    public void setBawah(double bawah)    { this.bawah = bawah; }
    public void setTinggi(double tinggi)  { this.tinggi = tinggi; }
    public void setKiri(double kiri)      { this.kiri = kiri; }
    public void setKanan(double kanan)    { this.kanan = kanan; }

    public double getHasilLuas()      { return hasilLuas; }
    public double getHasilKeliling()  { return hasilKeliling; }

    // ======================== HITUNG LUAS ========================

    /**
     * Menghitung luas trapesium menggunakan thread terpisah
     * Rumus: Luas = 1/2 × (atas + bawah) × tinggi
     * ✅ MULTITHREADING - perhitungan dijalankan di thread terpisah
     */
    @Override
    public double hitungLuas() {
        // Buat thread untuk menghitung luas
        Thread threadLuas = new Thread(() -> {
            // Setengah dari jumlah sisi sejajar dikalikan tinggi
            double jumlahSisiSejajar = atas + bawah;  // jumlahkan sisi atas dan bawah
            double setengah = jumlahSisiSejajar / 2;  // bagi dua
            hasilLuas = setengah * tinggi;             // kalikan dengan tinggi
        });

        threadLuas.start(); // mulai thread

        try {
            threadLuas.join(); // tunggu thread selesai sebelum melanjutkan
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread luas trapesium terganggu: " + e.getMessage());
        }

        return hasilLuas;
    }

    // ======================== HITUNG KELILING ========================

    /**
     * Menghitung keliling trapesium menggunakan thread terpisah
     * Rumus: Keliling = atas + bawah + kiri + kanan
     * ✅ MULTITHREADING - perhitungan dijalankan di thread terpisah
     */
    @Override
    public double hitungKeliling() {
        // Buat thread untuk menghitung keliling
        Thread threadKeliling = new Thread(() -> {
            // Jumlahkan semua sisi trapesium
            double jumlahAtasBawah = atas + bawah;   // sisi sejajar atas dan bawah
            double jumlahKiriKanan = kiri + kanan;   // sisi miring kiri dan kanan
            hasilKeliling = jumlahAtasBawah + jumlahKiriKanan; // total semua sisi
        });

        threadKeliling.start(); // mulai thread

        try {
            threadKeliling.join(); // tunggu thread selesai
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread keliling trapesium terganggu: " + e.getMessage());
        }

        return hasilKeliling;
    }

    // ======================== TO STRING ========================

    @Override
    public String toString() {
        return String.format("Trapesium[atas=%.2f, bawah=%.2f, tinggi=%.2f, kiri=%.2f, kanan=%.2f]",
                atas, bawah, tinggi, kiri, kanan);
    }
}