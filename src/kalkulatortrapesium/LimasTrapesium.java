package kalkulatortrapesium;

public class LimasTrapesium extends Trapesium implements Geometri3D, Runnable {
    public double volume;
    public double luasPermukaan;
    private double tinggiLimas;
    
    public void setTinggiLimas(double tinggiLimas){
        this.tinggiLimas = tinggiLimas;
    }
    
    public double getTinggiLimas(){
        return tinggiLimas;
    }

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
    
    @Override
    public double hitungVolume() {
        volume = 1/3 * (super.luas * tinggiLimas); 
        return volume;
    }
    
    public double hitungVolume(double atas, double bawah, double tinggi) {
        volume = 1/3 * (super.hitungLuas(atas, bawah, tinggi) * tinggiLimas); 
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
                      + (0.5 * atas      * apotemaAtasBawah)
                      + (0.5 * bawah     * apotemaAtasBawah)
                      + (0.5 * kiri      * apotemaKiriKanan)
                      + (0.5 * kanan     * apotemaKiriKanan);
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
                      + (0.5 * atas      * apotemaAtasBawah)
                      + (0.5 * bawah     * apotemaAtasBawah)
                      + (0.5 * kiri      * apotemaKiriKanan)
                      + (0.5 * kanan     * apotemaKiriKanan);
        return luasPermukaan;
    }

    @Override
    public void run() {
    
    }
}