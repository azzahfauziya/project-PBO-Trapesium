/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BendaGeometri.LimasTrapesium;
import javax.swing.*;
import java.awt.*;

public class GUI_LimasTrapesium extends JFrame {
    private JTextField tfSisiAtas, tfSisiBawah, tfTinggiAlas, tfTinggiLimas, tfTinggiTegak1, tfTinggiTegak2;
    private JLabel lblVolume, lblLuasPermukaan;
    private JButton btnHitung, btnKembali;
    private JTextArea txtRumus;
    
    public GUI_LimasTrapesium() {
        setTitle("Limas Trapesium - Kalkulator 3D");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel title = new JLabel("LIMAS TRAPESIUM", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(title, gbc);
        
        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("INPUT DATA"));
        GridBagConstraints gbcInput = new GridBagConstraints();
        gbcInput.insets = new Insets(5, 5, 5, 5);
        gbcInput.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        gbcInput.gridx = 0; gbcInput.gridy = row;
        inputPanel.add(new JLabel("Sisi Atas (cm):"), gbcInput);
        tfSisiAtas = new JTextField(12);
        gbcInput.gridx = 1;
        inputPanel.add(tfSisiAtas, gbcInput);
        
        row++;
        gbcInput.gridx = 0; gbcInput.gridy = row;
        inputPanel.add(new JLabel("Sisi Bawah (cm):"), gbcInput);
        tfSisiBawah = new JTextField(12);
        gbcInput.gridx = 1;
        inputPanel.add(tfSisiBawah, gbcInput);
        
        row++;
        gbcInput.gridx = 0; gbcInput.gridy = row;
        inputPanel.add(new JLabel("Tinggi Alas (cm):"), gbcInput);
        tfTinggiAlas = new JTextField(12);
        gbcInput.gridx = 1;
        inputPanel.add(tfTinggiAlas, gbcInput);
        
        row++;
        gbcInput.gridx = 0; gbcInput.gridy = row;
        inputPanel.add(new JLabel("Tinggi Limas (cm):"), gbcInput);
        tfTinggiLimas = new JTextField(12);
        gbcInput.gridx = 1;
        inputPanel.add(tfTinggiLimas, gbcInput);
        
        row++;
        gbcInput.gridx = 0; gbcInput.gridy = row;
        inputPanel.add(new JLabel("Tinggi Sisi Tegak 1 (cm):"), gbcInput);
        tfTinggiTegak1 = new JTextField(12);
        gbcInput.gridx = 1;
        inputPanel.add(tfTinggiTegak1, gbcInput);
        
        row++;
        gbcInput.gridx = 0; gbcInput.gridy = row;
        inputPanel.add(new JLabel("Tinggi Sisi Tegak 2 (cm):"), gbcInput);
        tfTinggiTegak2 = new JTextField(12);
        gbcInput.gridx = 1;
        inputPanel.add(tfTinggiTegak2, gbcInput);
        
        gbc.gridwidth = 2;
        gbc.gridy = 1;
        mainPanel.add(inputPanel, gbc);
        
        // Button Hitung
        btnHitung = new JButton("HITUNG");
        btnHitung.setFont(new Font("Arial", Font.BOLD, 16));
        btnHitung.setBackground(new Color(0, 150, 0));
        btnHitung.setForeground(Color.WHITE);
        btnHitung.setPreferredSize(new Dimension(150, 40));
        gbc.gridy = 2;
        mainPanel.add(btnHitung, gbc);
        
        // Hasil Panel
        JPanel hasilPanel = new JPanel(new GridBagLayout());
        hasilPanel.setBorder(BorderFactory.createTitledBorder("HASIL PERHITUNGAN"));
        GridBagConstraints gbcHasil = new GridBagConstraints();
        gbcHasil.insets = new Insets(5, 5, 5, 5);
        gbcHasil.fill = GridBagConstraints.HORIZONTAL;
        
        lblVolume = new JLabel("Volume: -");
        lblVolume.setFont(new Font("Arial", Font.BOLD, 14));
        lblVolume.setForeground(Color.BLUE);
        gbcHasil.gridx = 0; gbcHasil.gridy = 0;
        hasilPanel.add(lblVolume, gbcHasil);
        
        lblLuasPermukaan = new JLabel("Luas Permukaan: -");
        lblLuasPermukaan.setFont(new Font("Arial", Font.BOLD, 14));
        lblLuasPermukaan.setForeground(new Color(0, 100, 0));
        gbcHasil.gridy = 1;
        hasilPanel.add(lblLuasPermukaan, gbcHasil);
        
        gbc.gridy = 3;
        mainPanel.add(hasilPanel, gbc);
        
        // Rumus Panel
        JPanel rumusPanel = new JPanel(new BorderLayout());
        rumusPanel.setBorder(BorderFactory.createTitledBorder("DETAIL RUMUS & PERHITUNGAN"));
        txtRumus = new JTextArea(12, 45);
        txtRumus.setEditable(false);
        txtRumus.setFont(new Font("Monospaced", Font.PLAIN, 11));
        txtRumus.setBackground(new Color(250, 250, 250));
        JScrollPane scrollPane = new JScrollPane(txtRumus);
        rumusPanel.add(scrollPane, BorderLayout.CENTER);
        
        gbc.gridy = 4;
        mainPanel.add(rumusPanel, gbc);
        
        // Button Kembali
        btnKembali = new JButton("KEMBALI");
        btnKembali.setFont(new Font("Arial", Font.BOLD, 14));
        btnKembali.setBackground(Color.RED);
        btnKembali.setForeground(Color.WHITE);
        btnKembali.setPreferredSize(new Dimension(120, 35));
        gbc.gridy = 5;
        mainPanel.add(btnKembali, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        btnHitung.addActionListener(e -> hitung());
        btnKembali.addActionListener(e -> {
            dispose();
            new GUIPage().setVisible(true);
        });
    }
    
    private double hitungSisiMiring(double a, double b, double t) {
        double selisih = Math.abs(a - b) / 2;
        return Math.sqrt(Math.pow(selisih, 2) + Math.pow(t, 2));
    }
    
    private double hitungLuasAlas(double a, double b, double t) {
        return 0.5 * (a + b) * t;
    }
    
    private void hitung() {
        try {
            double a = Double.parseDouble(tfSisiAtas.getText());
            double b = Double.parseDouble(tfSisiBawah.getText());
            double tAlas = Double.parseDouble(tfTinggiAlas.getText());
            double T = Double.parseDouble(tfTinggiLimas.getText());
            double t1 = Double.parseDouble(tfTinggiTegak1.getText());
            double t2 = Double.parseDouble(tfTinggiTegak2.getText());
            
            if (a <= 0 || b <= 0 || tAlas <= 0 || T <= 0 || t1 <= 0 || t2 <= 0) {
                JOptionPane.showMessageDialog(this, "Semua nilai harus lebih dari 0!");
                return;
            }
            
            LimasTrapesium limas = new LimasTrapesium(a, b, tAlas, T, t1, t2);
            
            double sisiMiring = hitungSisiMiring(a, b, tAlas);
            double luasAlas = hitungLuasAlas(a, b, tAlas);
            double volume = limas.hitungVolume();
            double luasPermukaan = limas.hitungLuasPermukaan();
            
            lblVolume.setText(String.format("Volume: %.2f cm³", volume));
            lblLuasPermukaan.setText(String.format("Luas Permukaan: %.2f cm²", luasPermukaan));
            
            txtRumus.setText("");
            txtRumus.append("========== DETAIL PERHITUNGAN LIMAS TRAPESIUM ==========\n\n");
            
            txtRumus.append("DATA YANG DIINPUT:\n");
            txtRumus.append(String.format("  Sisi Atas (a)           = %.2f cm\n", a));
            txtRumus.append(String.format("  Sisi Bawah (b)          = %.2f cm\n", b));
            txtRumus.append(String.format("  Tinggi Alas (t)         = %.2f cm\n", tAlas));
            txtRumus.append(String.format("  Tinggi Limas (T)        = %.2f cm\n", T));
            txtRumus.append(String.format("  Tinggi Tegak 1 (t1)     = %.2f cm\n", t1));
            txtRumus.append(String.format("  Tinggi Tegak 2 (t2)     = %.2f cm\n\n", t2));
            
            txtRumus.append("--- PERHITUNGAN ALAS (TRAPESIUM) ---\n");
            txtRumus.append("Sisi Miring (s) = √((|a - b| / 2)² + t²)\n");
            txtRumus.append(String.format("                = √((|%.2f - %.2f| / 2)² + %.2f²)\n", a, b, tAlas));
            txtRumus.append(String.format("                = √(%.2f² + %.2f²)\n", Math.abs(a - b) / 2, tAlas));
            txtRumus.append(String.format("                = √(%.4f + %.4f)\n", Math.pow(Math.abs(a - b) / 2, 2), Math.pow(tAlas, 2)));
            txtRumus.append(String.format("                = √%.4f\n", Math.pow(Math.abs(a - b) / 2, 2) + Math.pow(tAlas, 2)));
            txtRumus.append(String.format("                = %.2f cm\n\n", sisiMiring));
            
            txtRumus.append("Luas Alas = ½ × (a + b) × t\n");
            txtRumus.append(String.format("         = ½ × (%.2f + %.2f) × %.2f\n", a, b, tAlas));
            txtRumus.append(String.format("         = ½ × %.2f × %.2f\n", a + b, tAlas));
            txtRumus.append(String.format("         = %.2f cm²\n\n", luasAlas));
            
            txtRumus.append("--- PERHITUNGAN VOLUME ---\n");
            txtRumus.append("Volume = ⅓ × Luas Alas × Tinggi Limas\n");
            txtRumus.append(String.format("       = ⅓ × %.2f × %.2f\n", luasAlas, T));
            txtRumus.append(String.format("       = %.2f cm³\n\n", volume));
            
            txtRumus.append("--- PERHITUNGAN LUAS PERMUKAAN ---\n");
            txtRumus.append("Luas Permukaan = Luas Alas + (½×a×t1) + (½×b×t2) + (½×s×t1) + (½×s×t2)\n");
            txtRumus.append(String.format("               = %.2f + (½×%.2f×%.2f) + (½×%.2f×%.2f) + (½×%.2f×%.2f) + (½×%.2f×%.2f)\n",
                              luasAlas, a, t1, b, t2, sisiMiring, t1, sisiMiring, t2));
            txtRumus.append(String.format("               = %.2f + %.2f + %.2f + %.2f + %.2f\n", 
                              luasAlas, 0.5*a*t1, 0.5*b*t2, 0.5*sisiMiring*t1, 0.5*sisiMiring*t2));
            txtRumus.append(String.format("               = %.2f cm²\n", luasPermukaan));
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}