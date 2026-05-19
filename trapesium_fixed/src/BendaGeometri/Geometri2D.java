/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BendaGeometri;

/**
 *
 * @author liulu
 */
public abstract class Geometri2D implements BendaGeometri {
    protected String namaBangun;
    
    public Geometri2D() {
        this.namaBangun = "Bangun 2D";
    }
    
    public Geometri2D(String namaBangun) {
        this.namaBangun = namaBangun;
    }
    
    @Override
    public double hitungVolume() {
        return 0;
    }
    
    @Override
    public double hitungLuasPermukaan() {
        return 0;
    }
    
    public String getNamaBangun() {
        return namaBangun;
    }
}
