package kalkulatortrapesium;

public class LimasTrapesium extends Trapesium implements Geometri3D, Runnable {

    public double volume;
    public double luasPermukaan;
    public double tinggiLimas;   // diubah dari private ke public, hapus setter/getter

    // OVERLOADING - konstruktor kosong
    public LimasTrapesium() {
        super();
        this.tinggiLimas = 0;
    }

    // OVERLOADING - konstruktor dengan parameter lengkap
    public LimasTrapesium(double atas, double bawah, double tinggi,
                          double kiri, double kanan, double tinggiLimas) {
        super(atas, bawah, tinggi, kiri, kanan);
        this.tinggiLimas = tinggiLimas;
    }

    @Override
    public double hitungVolume() {
        volume = 1.0/3.0 * (super.luas * tinggiLimas);
        return volume;
    }

    public double hitungVolume(double atas, double bawah, double tinggi) {
        volume = 1.0/3.0 * (super.hitungLuas(atas, bawah, tinggi) * tinggiLimas);
        return volume;
    }

    @Override
    public double hitungLuasPermukaan() {
        double proyeksiAtasBawah = (bawah - atas) / 2.0;
        double proyeksiKiriKanan = tinggi / 2.0;
        double apotemaAtasBawah  = Math.sqrt((tinggiLimas * tinggiLimas)
                                           + (proyeksiAtasBawah * proyeksiAtasBawah));
        double apotemaKiriKanan  = Math.sqrt((tinggiLimas * tinggiLimas)
                                           + (proyeksiKiriKanan * proyeksiKiriKanan));
        luasPermukaan = luas
                      + (0.5 * atas   * apotemaAtasBawah)
                      + (0.5 * bawah  * apotemaAtasBawah)
                      + (0.5 * kiri   * apotemaKiriKanan)
                      + (0.5 * kanan  * apotemaKiriKanan);
        return luasPermukaan;
    }

    public double hitungLuasPermukaan(double atas, double bawah, double tinggi, double kiri, double kanan) {
        double proyeksiAtasBawah = (bawah - atas) / 2.0;
        double proyeksiKiriKanan = tinggi / 2.0;
        double apotemaAtasBawah  = Math.sqrt((tinggiLimas * tinggiLimas)
                                           + (proyeksiAtasBawah * proyeksiAtasBawah));
        double apotemaKiriKanan  = Math.sqrt((tinggiLimas * tinggiLimas)
                                           + (proyeksiKiriKanan * proyeksiKiriKanan));
        luasPermukaan = luas
                      + (0.5 * atas   * apotemaAtasBawah)
                      + (0.5 * bawah  * apotemaAtasBawah)
                      + (0.5 * kiri   * apotemaKiriKanan)
                      + (0.5 * kanan  * apotemaKiriKanan);
        return luasPermukaan;
    }

    @Override
    public void run() {
        System.out.println("[Thread-" + Thread.currentThread().getName() + "] Menghitung Limas: " + toString());
        hitungLuas(atas, bawah, tinggi);
        hitungKeliling(atas, bawah, kiri, kanan);
        hitungVolume(atas, bawah, tinggi);
        hitungLuasPermukaan(atas, bawah, tinggi, kiri, kanan);
        System.out.println("[Thread-" + Thread.currentThread().getName() +
                "] Selesai Limas -> Luas=" + String.format("%.2f", luas) +
                ", Keliling=" + String.format("%.2f", keliling) +
                ", Volume=" + String.format("%.2f", volume) +
                ", LP=" + String.format("%.2f", luasPermukaan));
    }
}