package kalkulatortrapesium;

/**
 * Kelas PrismaTrapesium - bangun ruang prisma beralas trapesium
 *
 * ✅ PEWARISAN    - mewarisi Trapesium (mendapat hitungLuas & hitungKeliling)
 * ✅ POLIMORFISME - override hitungVolume dan hitungLuasPermukaan
 * ✅ MULTITHREADING - setiap perhitungan dijalankan di thread terpisah
 */
public class PrismaTrapesium extends Trapesium implements Geometri3D {

    private double panjang;       // panjang (tinggi) prisma
    private double hasilVolume;
    private double hasilLuasPermukaan;

    // ✅ OVERLOADING - konstruktor kosong (tanpa parameter)
    public PrismaTrapesium() {
        super(); // panggil konstruktor Trapesium kosong
        this.panjang = 0;
    }

    // ✅ OVERLOADING - konstruktor dengan parameter lengkap
    public PrismaTrapesium(double atas, double bawah, double tinggi,
                           double kiri, double kanan, double panjang) {
        super(atas, bawah, tinggi, kiri, kanan); // panggil konstruktor Trapesium berparameter
        this.panjang = panjang;
    }

    // ======================== GETTER & SETTER ========================

    public double getPanjang()             { return panjang; }
    public void setPanjang(double panjang) { this.panjang = panjang; }
    public double getHasilVolume()         { return hasilVolume; }
    public double getHasilLuasPermukaan()  { return hasilLuasPermukaan; }

    // ======================== HITUNG VOLUME ========================

    /**
     * Menghitung volume prisma trapesium menggunakan thread terpisah
     * Rumus: Volume = Luas Alas × Panjang Prisma
     * ✅ MULTITHREADING - perhitungan dijalankan di thread terpisah
     * ✅ POLIMORFISME   - override dari interface Geometri3D
     */
    @Override
    public double hitungVolume() {
        // Hitung luas alas terlebih dahulu (mewarisi dari Trapesium)
        double luasAlas = hitungLuas(); // panggil metode warisan dari Trapesium

        // Buat thread untuk menghitung volume
        Thread threadVolume = new Thread(() -> {
            // Volume = luas alas trapesium × panjang prisma
            hasilVolume = luasAlas * panjang; // kalikan luas alas dengan panjang
        });

        threadVolume.start(); // mulai thread

        try {
            threadVolume.join(); // tunggu thread selesai
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread volume prisma terganggu: " + e.getMessage());
        }

        return hasilVolume;
    }

    // ======================== HITUNG LUAS PERMUKAAN ========================

    /**
     * Menghitung luas permukaan prisma trapesium menggunakan thread terpisah
     * Rumus: LP = (2 × Luas Alas) + (Keliling Alas × Panjang Prisma)
     * ✅ MULTITHREADING - perhitungan dijalankan di thread terpisah
     * ✅ POLIMORFISME   - override dari interface Geometri3D
     */
    @Override
    public double hitungLuasPermukaan() {
        // Hitung komponen alas sebelum masuk thread
        double luasAlas     = hitungLuas();     // luas alas trapesium
        double kelilingAlas = hitungKeliling(); // keliling alas trapesium

        // Buat thread untuk menghitung luas permukaan
        Thread threadLP = new Thread(() -> {
            double duaAlas        = 2 * luasAlas;           // dua sisi alas (atas & bawah)
            double bidangSamping  = kelilingAlas * panjang; // seluruh bidang samping prisma
            hasilLuasPermukaan    = duaAlas + bidangSamping; // total luas permukaan
        });

        threadLP.start(); // mulai thread

        try {
            threadLP.join(); // tunggu thread selesai
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread luas permukaan prisma terganggu: " + e.getMessage());
        }

        return hasilLuasPermukaan;
    }

    // ======================== TO STRING ========================

    @Override
    public String toString() {
        return String.format("PrismaTrapesium[atas=%.2f, bawah=%.2f, tinggi=%.2f, " +
                "kiri=%.2f, kanan=%.2f, panjang=%.2f]",
                getAtas(), getBawah(), getTinggi(), getKiri(), getKanan(), panjang);
    }
}