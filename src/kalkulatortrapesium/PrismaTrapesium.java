package kalkulatortrapesium;

public class PrismaTrapesium extends Trapesium implements Geometri3D, Runnable {

    public double tinggiPrisma;       // panjang (tinggi) prisma
    public double volume;
    public double luasPermukaan;

    // OVERLOADING - konstruktor kosong (tanpa parameter)
    public PrismaTrapesium() {
        super(); // panggil konstruktor Trapesium kosong
        this.tinggiPrisma = 0;
    }

    // OVERLOADING - konstruktor dengan parameter lengkap
    public PrismaTrapesium(double atas, double bawah, double tinggi,
                           double kiri, double kanan, double panjang) {
        super(atas, bawah, tinggi, kiri, kanan); // panggil konstruktor Trapesium berparameter
        if (panjang <= 0) {
            throw new IllegalArgumentException("Tinggi prisma harus bernilai lebih besar dari 0.");
        }
        this.tinggiPrisma = panjang;
    }

    @Override
    public double hitungVolume() {
        volume = super.luas * tinggiPrisma;
        return volume;
    }
    
    public double hitungVolume(double atas, double bawah, double tinggi) {
        volume = super.hitungLuas(atas, bawah, tinggi) * tinggiPrisma;
        return volume;
    }

    @Override
    public double hitungLuasPermukaan() {
        luasPermukaan = (2 * super.luas)
                      + (atas   * tinggiPrisma)
                      + (bawah  * tinggiPrisma)
                      + (kiri   * tinggiPrisma)
                      + (kanan  * tinggiPrisma);
        return luasPermukaan;
    }
    
    public double hitungLuasPermukaan(double atas, double bawah, double kanan, double kiri, double tinggi) {
        luasPermukaan = (2 * super.hitungLuas(atas, bawah, tinggi))
                      + (atas   * tinggiPrisma)
                      + (bawah  * tinggiPrisma)
                      + (kiri   * tinggiPrisma)
                      + (kanan  * tinggiPrisma);
        return luasPermukaan;
    }

    @Override
    public void run() {
    System.out.println("[Thread-" + Thread.currentThread().getName() + "] Menghitung Prisma: " + toString());
    hitungLuas(atas, bawah, tinggi);
    hitungKeliling(atas, bawah, kiri, kanan);
    hitungVolume(atas, bawah, tinggi);
    hitungLuasPermukaan(atas, bawah, kanan, kiri, tinggi);
    System.out.println("[Thread-" + Thread.currentThread().getName() +
            "] Selesai Prisma -> Luas=" + String.format("%.2f", luas) +
            ", Keliling=" + String.format("%.2f", keliling) +
            ", Volume=" + String.format("%.2f", volume) +
            ", LP=" + String.format("%.2f", luasPermukaan));
}
}