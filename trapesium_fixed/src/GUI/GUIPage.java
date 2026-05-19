/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.*;
import java.awt.*;

public class GUIPage extends JFrame {
    private JButton btnTrapesium, btnLimasTrapesium, btnPrismaTrapesium, btnExit;
    
    public GUIPage() {
        initComponents();
        setTitle("Menu Utama - Geometri Trapesium");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("APLIKASI GEOMETRI TRAPESIUM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        
        btnTrapesium = new JButton("Trapesium (2D)");
        btnTrapesium.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        add(btnTrapesium, gbc);
        
        btnLimasTrapesium = new JButton("Limas Trapesium (3D)");
        btnLimasTrapesium.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 2;
        add(btnLimasTrapesium, gbc);
        
        btnPrismaTrapesium = new JButton("Prisma Trapesium (3D)");
        btnPrismaTrapesium.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 3;
        add(btnPrismaTrapesium, gbc);
        
        btnExit = new JButton("Keluar");
        btnExit.setFont(new Font("Arial", Font.PLAIN, 14));
        btnExit.setBackground(Color.RED);
        btnExit.setForeground(Color.WHITE);
        gbc.gridy = 4;
        add(btnExit, gbc);
        
        // Action Listeners
        btnTrapesium.addActionListener(e -> {
            new GUI_Trapesium().setVisible(true);
            dispose();
        });
        
        btnLimasTrapesium.addActionListener(e -> {
            new GUI_LimasTrapesium().setVisible(true);
            dispose();
        });
        
        btnPrismaTrapesium.addActionListener(e -> {
            new GUI_PrismaTrapesium().setVisible(true);
            dispose();
        });
        
        btnExit.addActionListener(e -> System.exit(0));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIPage().setVisible(true));
    }
}