/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kalkulatortrapesium;

/**
 *
 * @author ACER
 */
public abstract class Trapesium implements Geometri2D{
    private double atas;
    private double bawah;
    private double luas;
    private double keliling;
    
    public Trapesium(
        double atas,
        double bawah, 
        double luas,
        double keliling
    ) {
        this.atas = atas;
        this.bawah = bawah;
        this.luas = luas;
        this.keliling = keliling;
    }

    
    @Override
    public double hitungLuas() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double hitungKeliling() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
