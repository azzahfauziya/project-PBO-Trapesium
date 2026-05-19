/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BendaGeometri;

/**
 *
 * @author liulu
 */
public abstract class Geometri3D implements BendaGeometri {
    protected String namaBangun;
    
    public Geometri3D() {
        this.namaBangun = "Bangun 3D";
    }
    
    public Geometri3D(String namaBangun) {
        this.namaBangun = namaBangun;
    }
    
    @Override
    public double hitungLuas() {
        return 0;
    }
    
    @Override
    public double hitungKeliling() {
        return 0;
    }
    
    public String getNamaBangun() {
        return namaBangun;
    }
}
