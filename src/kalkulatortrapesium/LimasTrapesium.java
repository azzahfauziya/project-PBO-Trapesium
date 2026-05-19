package kalkulatortrapesium;

/**
 * Kelas LimasTrapesium - bangun ruang limas beralas trapesium
 *
 * ✅ PEWARISAN    - mewarisi Trapesium (mendapat hitungLuas & hitungKeliling)
 * ✅ POLIMORFISME - override hitungVolume dan hitungLuasPermukaan
 * ✅ MULTITHREADING - setiap perhitungan dijalankan di thread terpisah
 */
public class LimasTrapesium extends Trapesium implements Geometri3D {

    private double tinggiLimas;       // tinggi limas dari alas ke puncak
    private double hasilVolume;
    private double hasilLuasPermukaan;

    // ✅ OVERLOADING - konstruktor kosong (tanpa parameter)
    public LimasTrapesium() {
        super(); // panggil konstruktor Trapesium kosong
        this.tinggiLimas = 0;
    }

    // ✅ OVERLOADING - konstruktor dengan parameter lengkap
    public LimasTrapesium(double atas, double bawah, double tinggi,
                          double kiri, double kanan, double tinggiLimas) {
        super(atas, bawah, tinggi, kiri, kanan); // panggil konstruktor Trapesium berparameter
        this.tinggiLimas = tinggiLimas;
    }

    // ======================== GETTER & SETTER ========================

    public double getTinggiLimas()               { return tinggiLimas; }
    public void setTinggiLimas(double tinggiLimas) { this.tinggiLimas = tinggiLimas; }
    public double getHasilVolume()               { return hasilVolume; }
    public double getHasilLuasPermukaan()        { return hasilLuasPermukaan; }

    // ======================== HITUNG VOLUME ========================

    /**
     * Menghitung volume limas trapesium menggunakan thread terpisah
     * Rumus: Volume = (1/3) × Luas Alas × Tinggi Limas
     * ✅ MULTITHREADING - perhitungan dijalankan di thread terpisah
     * ✅ POLIMORFISME   - override dari interface Geometri3D
     */
    @Override
    public double hitungVolume() {
        // Hitung luas alas terlebih dahulu (mewarisi dari Trapesium)
        double luasAlas = hitungLuas(); // luas alas trapesium dari kelas induk

        // Buat thread untuk menghitung volume limas
        Thread threadVolume = new Thread(() -> {
            double sepertiga    = luasAlas / 3;            // sepertiga dari luas alas
            hasilVolume         = sepertiga * tinggiLimas; // kalikan dengan tinggi limas
        });

        threadVolume.start(); // mulai thread

        try {
            threadVolume.join(); // tunggu thread selesai
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread volume limas terganggu: " + e.getMessage());
        }

        return hasilVolume;
    }

    // ======================== HITUNG LUAS PERMUKAAN ========================

    /**
     * Menghitung luas permukaan limas trapesium menggunakan thread terpisah
     *
     * Limas trapesium memiliki:
     *   - 1 alas berbentuk trapesium
     *   - 4 sisi tegak berbentuk segitiga
     *
     * Rumus sisi tegak: setiap sisi = 1/2 × panjang sisi alas × tinggi segitiga tegak
     * Tinggi segitiga tegak (apotema) ≈ sqrt(tinggiLimas² + (selisihSisi/2)²)
     *
     * ✅ MULTITHREADING - perhitungan dijalankan di thread terpisah
     * ✅ POLIMORFISME   - override dari interface Geometri3D
     */
    @Override
    public double hitungLuasPermukaan() {
        // Ambil nilai sisi dari getter (warisan Trapesium)
        double a      = getAtas();
        double b      = getBawah();
        double t      = getTinggi();  // tinggi trapesium (alas)
        double kiri   = getKiri();
        double kanan  = getKanan();
        double luasAlas = hitungLuas(); // luas alas trapesium

        // Buat thread untuk menghitung luas permukaan limas
        Thread threadLP = new Thread(() -> {
            // Hitung apotema (tinggi sisi tegak) untuk masing-masing sisi
            // Apotema sisi depan (sisi atas trapesium)
            double apotemaAtas  = Math.sqrt((tinggiLimas * tinggiLimas) + (a / 2) * (a / 2));
            // Apotema sisi belakang (sisi bawah trapesium)
            double apotemaBawah = Math.sqrt((tinggiLimas * tinggiLimas) + (b / 2) * (b / 2));
            // Apotema sisi kiri (sisi miring kiri trapesium)
            double apotemaKiri  = Math.sqrt((tinggiLimas * tinggiLimas) + (kiri / 2) * (kiri / 2));
            // Apotema sisi kanan (sisi miring kanan trapesium)
            double apotemaKanan = Math.sqrt((tinggiLimas * tinggiLimas) + (kanan / 2) * (kanan / 2));

            // Luas masing-masing sisi segitiga tegak = 1/2 × alas × apotema
            double luasSisiAtas  = 0.5 * a     * apotemaAtas;
            double luasSisiBawah = 0.5 * b     * apotemaBawah;
            double luasSisiKiri  = 0.5 * kiri  * apotemaKiri;
            double luasSisiKanan = 0.5 * kanan * apotemaKanan;

            // Total luas permukaan = luas alas + semua sisi tegak
            double totalSisiTegak = luasSisiAtas + luasSisiBawah + luasSisiKiri + luasSisiKanan;
            hasilLuasPermukaan    = luasAlas + totalSisiTegak;
        });

        threadLP.start(); // mulai thread

        try {
            threadLP.join(); // tunggu thread selesai
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread luas permukaan limas terganggu: " + e.getMessage());
        }

        return hasilLuasPermukaan;
    }

    // ======================== TO STRING ========================

    @Override
    public String toString() {
        return String.format("LimasTrapesium[atas=%.2f, bawah=%.2f, tinggi=%.2f, " +
                "kiri=%.2f, kanan=%.2f, tinggiLimas=%.2f]",
                getAtas(), getBawah(), getTinggi(), getKiri(), getKanan(), tinggiLimas);
    }
}