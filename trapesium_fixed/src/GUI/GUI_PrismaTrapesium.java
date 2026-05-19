/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BendaGeometri.PrismaTrapesium;
import javax.swing.*;
import java.awt.*;

public class GUI_PrismaTrapesium extends JFrame {
    private JTextField tfSisiAtas, tfSisiBawah, tfTinggiAlas, tfTinggiPrisma;
    private JLabel lblVolume, lblLuasPermukaan;
    private JButton btnHitung, btnKembali;
    private JTextArea txtRumus;
    
    public GUI_PrismaTrapesium() {
        setTitle("Prisma Trapesium - Kalkulator 3D");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel title = new JLabel("PRISMA TRAPESIUM", SwingConstants.CENTER);
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
        inputPanel.add(new JLabel("Tinggi Prisma (cm):"), gbcInput);
        tfTinggiPrisma = new JTextField(12);
        gbcInput.gridx = 1;
        inputPanel.add(tfTinggiPrisma, gbcInput);
        
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
        txtRumus = new JTextArea(10, 45);
        txtRumus.setEditable(false);
        txtRumus.setFont(new Font("Monospaced", Font.PLAIN, 12));
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
    
    private double hitungKelilingAlas(double a, double b, double s) {
        return a + b + (2 * s);
    }
    
    private void hitung() {
        try {
            double a = Double.parseDouble(tfSisiAtas.getText());
            double b = Double.parseDouble(tfSisiBawah.getText());
            double tAlas = Double.parseDouble(tfTinggiAlas.getText());
            double T = Double.parseDouble(tfTinggiPrisma.getText());
            
            if (a <= 0 || b <= 0 || tAlas <= 0 || T <= 0) {
                JOptionPane.showMessageDialog(this, "Semua nilai harus lebih dari 0!");
                return;
            }
            
            PrismaTrapesium prisma = new PrismaTrapesium(a, b, tAlas, T);
            
            double sisiMiring = hitungSisiMiring(a, b, tAlas);
            double luasAlas = hitungLuasAlas(a, b, tAlas);
            double kelilingAlas = hitungKelilingAlas(a, b, sisiMiring);
            double volume = prisma.hitungVolume();
            double luasPermukaan = prisma.hitungLuasPermukaan();
            
            lblVolume.setText(String.format("Volume: %.2f cm³", volume));
            lblLuasPermukaan.setText(String.format("Luas Permukaan: %.2f cm²", luasPermukaan));
            
            txtRumus.setText("");
            txtRumus.append("========== DETAIL PERHITUNGAN PRISMA TRAPESIUM ==========\n\n");
            
            txtRumus.append("DATA YANG DIINPUT:\n");
            txtRumus.append(String.format("  Sisi Atas (a)       = %.2f cm\n", a));
            txtRumus.append(String.format("  Sisi Bawah (b)      = %.2f cm\n", b));
            txtRumus.append(String.format("  Tinggi Alas (t)     = %.2f cm\n", tAlas));
            txtRumus.append(String.format("  Tinggi Prisma (T)   = %.2f cm\n\n", T));
            
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
            
            txtRumus.append("Keliling Alas = a + b + (2 × s)\n");
            txtRumus.append(String.format("             = %.2f + %.2f + (2 × %.2f)\n", a, b, sisiMiring));
            txtRumus.append(String.format("             = %.2f + %.2f + %.2f\n", a, b, 2 * sisiMiring));
            txtRumus.append(String.format("             = %.2f cm\n\n", kelilingAlas));
            
            txtRumus.append("--- PERHITUNGAN VOLUME ---\n");
            txtRumus.append("Volume = Luas Alas × Tinggi Prisma\n");
            txtRumus.append(String.format("       = %.2f × %.2f\n", luasAlas, T));
            txtRumus.append(String.format("       = %.2f cm³\n\n", volume));
            
            txtRumus.append("--- PERHITUNGAN LUAS PERMUKAAN ---\n");
            txtRumus.append("Luas Permukaan = (2 × Luas Alas) + (Keliling Alas × Tinggi Prisma)\n");
            txtRumus.append(String.format("               = (2 × %.2f) + (%.2f × %.2f)\n", luasAlas, kelilingAlas, T));
            txtRumus.append(String.format("               = %.2f + %.2f\n", 2 * luasAlas, kelilingAlas * T));
            txtRumus.append(String.format("               = %.2f cm²\n", luasPermukaan));
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}