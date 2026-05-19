/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BendaGeometri;

// [OOP - INHERITANCE] LimasTrapesium extends Geometri3D yang implements BendaGeometri
// sehingga LimasTrapesium mewarisi semua method abstract dari Geometri3D
public class LimasTrapesium extends Geometri3D {
    private Trapesium alas;          // object alas pakai class trapesium, jadi konsepnya: limasTrapesium 'punya' trapesium sbg alas
    private double tinggiLimas;      // tinggi limas dari alas ke titik puncak
    private double tinggiSisiTegak1; // tinggi sisi tegak segitiga pada limas
    private double tinggiSisiTegak2;

    public LimasTrapesium() {
        // [OOP - INHERITANCE] Memanggil konstruktor superclass Geometri3D
        super("Limas Trapesium");
        this.alas = new Trapesium();
        this.tinggiLimas = 0;
        this.tinggiSisiTegak1 = 0;
        this.tinggiSisiTegak2 = 0;
    }

    // constructor parameter
    // dipakai untuk mengisi data limas langsung saat object dibuat
    public LimasTrapesium(double sisiAtas, double sisiBawah, double tinggiAlas,
                          double tinggiLimas, double tinggiSisiTegak1, double tinggiSisiTegak2) {
        // [OOP - INHERITANCE] Memanggil konstruktor superclass Geometri3D
        super("Limas Trapesium");
        this.alas = new Trapesium(sisiAtas, sisiBawah, tinggiAlas);
        this.tinggiLimas = tinggiLimas;
        this.tinggiSisiTegak1 = tinggiSisiTegak1;
        this.tinggiSisiTegak2 = tinggiSisiTegak2;
    }

    // [OOP - INHERITANCE] Override method abstract hitungVolume() dari Geometri3D
    // Rumus: 1/3 x luas alas x tinggi limas
    @Override
    public double hitungVolume() {
        double luasAlas = alas.hitungLuas();
        return (1.0 / 3.0) * luasAlas * tinggiLimas;
    }

    // Method overloading hitungVolume dengan parameter langsung
    public double hitungVolume(double luasAlas, double tinggiLimas) {
        return (1.0 / 3.0) * luasAlas * tinggiLimas;
    }

    // [OOP - INHERITANCE] Override method abstract hitungLuasPermukaan() dari Geometri3D
    // Method menghitung luas permukaan limas
    @Override
    public double hitungLuasPermukaan() {
        double luasAlas = alas.hitungLuas();
        double sisiMiring = alas.getSisiMiring();

        double luasSisiTegak1 = 0.5 * alas.getSisiSejajar1() * tinggiSisiTegak1;
        double luasSisiTegak2 = 0.5 * alas.getSisiSejajar2() * tinggiSisiTegak2;
        double luasSisiMiring1 = 0.5 * sisiMiring * tinggiSisiTegak1;
        double luasSisiMiring2 = 0.5 * sisiMiring * tinggiSisiTegak2;

        return luasAlas + luasSisiTegak1 + luasSisiTegak2 + luasSisiMiring1 + luasSisiMiring2;
    }

    // overloading method luas permukaan
    public double hitungLuasPermukaan(double luasAlas, double sisiAtas, double tinggiTegak1,
                                       double sisiBawah, double tinggiTegak2, double sisiMiring) {
        return luasAlas + (0.5 * sisiAtas * tinggiTegak1) + (0.5 * sisiBawah * tinggiTegak2) +
               (0.5 * sisiMiring * tinggiTegak1) + (0.5 * sisiMiring * tinggiTegak2);
    }

    // getter dipakai untuk mengambil nilai atribut
    public Trapesium getAlas() { return alas; }
    public double getTinggiLimas() { return tinggiLimas; }
    public double getTinggiSisiTegak1() { return tinggiSisiTegak1; }
    public double getTinggiSisiTegak2() { return tinggiSisiTegak2; }
}
