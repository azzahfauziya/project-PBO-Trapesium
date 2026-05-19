/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BendaGeometri.Trapesium;
import javax.swing.*;
import java.awt.*;

public class GUI_Trapesium extends JFrame {
    private JTextField tfSisiAtas, tfSisiBawah, tfTinggi;
    private JLabel lblHasilLuas, lblHasilKeliling, lblSisiMiring;
    private JButton btnHitung, btnKembali;
    private JTextArea txtRumus;
    
    public GUI_Trapesium() {
        setTitle("Trapesium - Kalkulator 2D");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Panel Utama
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel title = new JLabel("TRAPESIUM", SwingConstants.CENTER);
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
        
        gbcInput.gridx = 0; gbcInput.gridy = 0;
        inputPanel.add(new JLabel("Sisi Atas (a) :"), gbcInput);
        tfSisiAtas = new JTextField(12);
        gbcInput.gridx = 1;
        inputPanel.add(tfSisiAtas, gbcInput);
        
        gbcInput.gridx = 0; gbcInput.gridy = 1;
        inputPanel.add(new JLabel("Sisi Bawah (b) :"), gbcInput);
        tfSisiBawah = new JTextField(12);
        gbcInput.gridx = 1;
        inputPanel.add(tfSisiBawah, gbcInput);
        
        gbcInput.gridx = 0; gbcInput.gridy = 2;
        inputPanel.add(new JLabel("Tinggi (t) :"), gbcInput);
        tfTinggi = new JTextField(12);
        gbcInput.gridx = 1;
        inputPanel.add(tfTinggi, gbcInput);
        
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
        
        lblHasilLuas = new JLabel("Luas: -");
        lblHasilLuas.setFont(new Font("Arial", Font.BOLD, 14));
        lblHasilLuas.setForeground(Color.BLUE);
        gbcHasil.gridx = 0; gbcHasil.gridy = 0;
        hasilPanel.add(lblHasilLuas, gbcHasil);
        
        lblHasilKeliling = new JLabel("Keliling: -");
        lblHasilKeliling.setFont(new Font("Arial", Font.BOLD, 14));
        lblHasilKeliling.setForeground(new Color(0, 100, 0));
        gbcHasil.gridy = 1;
        hasilPanel.add(lblHasilKeliling, gbcHasil);
        
        lblSisiMiring = new JLabel("Sisi Miring: -");
        lblSisiMiring.setFont(new Font("Arial", Font.BOLD, 14));
        lblSisiMiring.setForeground(new Color(128, 0, 128));
        gbcHasil.gridy = 2;
        hasilPanel.add(lblSisiMiring, gbcHasil);
        
        gbc.gridy = 3;
        mainPanel.add(hasilPanel, gbc);
        
        // Rumus Panel (JTextArea)
        JPanel rumusPanel = new JPanel(new BorderLayout());
        rumusPanel.setBorder(BorderFactory.createTitledBorder("DETAIL RUMUS & PERHITUNGAN"));
        txtRumus = new JTextArea(10, 40);
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
        
        // Action Listeners
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
    
    private void hitung() {
        try {
            double a = Double.parseDouble(tfSisiAtas.getText());
            double b = Double.parseDouble(tfSisiBawah.getText());
            double t = Double.parseDouble(tfTinggi.getText());
            
            if (a <= 0 || b <= 0 || t <= 0) {
                JOptionPane.showMessageDialog(this, "Nilai harus lebih dari 0!");
                return;
            }
            
            Trapesium tp = new Trapesium(a, b, t);
            
            double luas = tp.hitungLuas();
            double keliling = tp.hitungKeliling();
            double sisiMiring = hitungSisiMiring(a, b, t);
            
            lblHasilLuas.setText(String.format("Luas: %.2f cm²", luas));
            lblHasilKeliling.setText(String.format("Keliling: %.2f cm", keliling));
            lblSisiMiring.setText(String.format("Sisi Miring: %.2f cm", sisiMiring));
            
            // Tampilkan detail rumus
            txtRumus.setText("");
            txtRumus.append("========== DETAIL PERHITUNGAN TRAPESIUM ==========\n\n");
            txtRumus.append("DATA YANG DIINPUT:\n");
            txtRumus.append(String.format("  Sisi Atas (a)   = %.2f cm\n", a));
            txtRumus.append(String.format("  Sisi Bawah (b)  = %.2f cm\n", b));
            txtRumus.append(String.format("  Tinggi (t)      = %.2f cm\n\n", t));
            
            txtRumus.append("RUMUS SISI MIRING:\n");
            txtRumus.append("  s = √((|a - b| / 2)² + t²)\n");
            txtRumus.append(String.format("    = √((|%.2f - %.2f| / 2)² + %.2f²)\n", a, b, t));
            txtRumus.append(String.format("    = √((%.2f)² + %.2f²)\n", Math.abs(a - b) / 2, t));
            txtRumus.append(String.format("    = √(%.4f + %.4f)\n", Math.pow(Math.abs(a - b) / 2, 2), Math.pow(t, 2)));
            txtRumus.append(String.format("    = √%.4f\n", Math.pow(Math.abs(a - b) / 2, 2) + Math.pow(t, 2)));
            txtRumus.append(String.format("    = %.2f cm\n\n", sisiMiring));
            
            txtRumus.append("RUMUS LUAS:\n");
            txtRumus.append("  Luas = ½ × (a + b) × t\n");
            txtRumus.append(String.format("       = ½ × (%.2f + %.2f) × %.2f\n", a, b, t));
            txtRumus.append(String.format("       = ½ × %.2f × %.2f\n", a + b, t));
            txtRumus.append(String.format("       = %.2f cm²\n\n", luas));
            
            txtRumus.append("RUMUS KELILING:\n");
            txtRumus.append("  Keliling = a + b + (2 × s)\n");
            txtRumus.append(String.format("          = %.2f + %.2f + (2 × %.2f)\n", a, b, sisiMiring));
            txtRumus.append(String.format("          = %.2f + %.2f + %.2f\n", a, b, 2 * sisiMiring));
            txtRumus.append(String.format("          = %.2f cm\n", keliling));
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}