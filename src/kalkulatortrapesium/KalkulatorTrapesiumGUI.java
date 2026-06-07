package kalkulatortrapesium;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class KalkulatorTrapesiumGUI extends JFrame {

    static final Color C_BG      = new Color(10, 12, 22);
    static final Color C_PANEL   = new Color(18, 22, 40);
    static final Color C_BORDER  = new Color(35, 45, 80);
    static final Color C_ACCENT  = new Color(80, 200, 160);
    static final Color C_ACCENT2 = new Color(100, 155, 255);
    static final Color C_ACCENT3 = new Color(255, 180, 60);
    static final Color C_TEXT    = new Color(215, 225, 245);
    static final Color C_MUTED   = new Color(90, 110, 150);
    static final Color C_SUCCESS = new Color(60, 210, 130);

    static final Color[] THREAD_COLORS = {
        new Color(100, 200, 255),
        new Color(255, 140, 80),
        new Color(140, 255, 130),
        new Color(220, 110, 255)
    };

    private JPanel     cardContainer;
    private CardLayout cardLayout;

    public KalkulatorTrapesiumGUI() {
        setTitle("Kalkulator Trapesium");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 620));
        setLocationRelativeTo(null);
        getContentPane().setBackground(C_BG);
        setLayout(new BorderLayout());

        add(buildSidebar(), BorderLayout.WEST);

        cardLayout    = new CardLayout();
        cardContainer = new JPanel(cardLayout);
        cardContainer.setBackground(C_BG);
        cardContainer.add(new HomePanel(),  "home");
        cardContainer.add(new Menu1Panel(), "menu1");
        cardContainer.add(new Menu2Panel(), "menu2");
        add(cardContainer, BorderLayout.CENTER);

        cardLayout.show(cardContainer, "home");
        setVisible(true);
    }

    void showCard(String name) { cardLayout.show(cardContainer, name); }

    // ══════════════════════════════════════════════════════════════
    //  SIDEBAR
    // ══════════════════════════════════════════════════════════════
    JPanel buildSidebar() {
        JPanel sb = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, new Color(14, 18, 36), 0, getHeight(), new Color(10, 12, 22)));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(C_BORDER);
                g2.fillRect(getWidth() - 1, 0, 1, getHeight());
            }
        };
        sb.setPreferredSize(new Dimension(200, 0));
        sb.setLayout(new BorderLayout());
        sb.setOpaque(false);

        JPanel logo = new JPanel();
        logo.setOpaque(false);
        logo.setLayout(new BoxLayout(logo, BoxLayout.Y_AXIS));
        logo.setBorder(BorderFactory.createEmptyBorder(28, 20, 20, 20));
        JLabel icon  = mkLabel("◇", 32, Font.BOLD, C_ACCENT);  icon.setAlignmentX(CENTER_ALIGNMENT);
        JLabel title = mkLabel("TRAPESIUM", 13, Font.BOLD, C_TEXT); title.setAlignmentX(CENTER_ALIGNMENT);
        JLabel sub   = mkLabel("KALKULATOR", 10, Font.PLAIN, C_MUTED); sub.setAlignmentX(CENTER_ALIGNMENT);
        logo.add(icon); logo.add(Box.createVerticalStrut(6)); logo.add(title); logo.add(sub);
        sb.add(logo, BorderLayout.NORTH);

        JPanel nav = new JPanel();
        nav.setOpaque(false);
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        nav.add(navBtn("⌂  Beranda",        "home",  C_ACCENT2));
        nav.add(Box.createVerticalStrut(6));
        nav.add(navBtn("✎  Input Manual",   "menu1", C_ACCENT));
        nav.add(Box.createVerticalStrut(6));
        nav.add(navBtn("⟳  Multithreading", "menu2", C_ACCENT3));
        sb.add(nav, BorderLayout.CENTER);

        JLabel ver = mkLabel("v1.0 · Java Swing", 10, Font.ITALIC, C_MUTED);
        ver.setHorizontalAlignment(SwingConstants.CENTER);
        ver.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));
        sb.add(ver, BorderLayout.SOUTH);
        return sb;
    }

    JButton navBtn(String text, String card, Color accent) {
        JButton b = new JButton(text) {
            boolean hov = false;
            { setOpaque(false); setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
              setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
              addMouseListener(new MouseAdapter() {
                  public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                  public void mouseExited (MouseEvent e) { hov = false; repaint(); }
              });
            }
            @Override protected void paintComponent(Graphics g) {
                if (hov) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 40));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setForeground(C_TEXT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        b.addActionListener(e -> showCard(card));
        return b;
    }

    // ══════════════════════════════════════════════════════════════
    //  HOME PANEL
    // ══════════════════════════════════════════════════════════════
    class HomePanel extends JPanel {
        HomePanel() {
            setBackground(C_BG);
            setLayout(new GridBagLayout());
            JPanel box = new JPanel();
            box.setOpaque(false);
            box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

            JLabel h   = mkLabel("Kalkulator Trapesium", 28, Font.BOLD, C_TEXT);
            JLabel sub = mkLabel("2D · Prisma · Limas",  14, Font.PLAIN, C_MUTED);
            h.setAlignmentX(CENTER_ALIGNMENT);
            sub.setAlignmentX(CENTER_ALIGNMENT);
            box.add(h); box.add(Box.createVerticalStrut(6)); box.add(sub);
            box.add(Box.createVerticalStrut(36));

            for (String[] item : new String[][]{
                {"✎", "Input Manual",   "Hitung Trapesium 2D, Prisma, atau Limas\ndengan memasukkan sisi secara manual.", "menu1"},
                {"⟳", "Multithreading", "Generate data random, proses paralel\ndengan thread pool dan visualisasi.",      "menu2"}
            }) {
                JButton card = homeCard(item[0], item[1], item[2], item[3]);
                card.setAlignmentX(CENTER_ALIGNMENT);
                box.add(card);
                box.add(Box.createVerticalStrut(14));
            }
            add(box);
        }

        JButton homeCard(String ico, String ttl, String desc, String card) {
            JButton b = new JButton() {
                boolean hov = false;
                { setOpaque(false); setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
                  setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                  setPreferredSize(new Dimension(400, 90)); setMaximumSize(new Dimension(400, 90));
                  addMouseListener(new MouseAdapter() {
                      public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                      public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                  });
                }
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(hov ? new Color(26, 32, 58) : C_PANEL);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                    g2.setColor(hov ? C_ACCENT : C_BORDER);
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 14, 14);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 28)); g2.setColor(C_ACCENT);
                    g2.drawString(ico, 20, 52);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 14)); g2.setColor(C_TEXT);
                    g2.drawString(ttl, 66, 34);
                    g2.setFont(new Font("Segoe UI", Font.PLAIN, 11)); g2.setColor(C_MUTED);
                    String[] lines = desc.split("\n");
                    for (int i = 0; i < lines.length; i++) g2.drawString(lines[i], 66, 52 + i * 15);
                    g2.dispose();
                }
            };
            b.addActionListener(e -> showCard(card));
            return b;
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  MENU 1 — INPUT MANUAL
    // ══════════════════════════════════════════════════════════════
    class Menu1Panel extends JPanel {
        JTextField[] f2D = new JTextField[5];
        JTextField[] fPr = new JTextField[6];
        JTextField[] fLi = new JTextField[6];
        JTextArea result;
        JLabel    resultTitle;

        Menu1Panel() {
            setBackground(C_BG);
            setLayout(new BorderLayout());
            add(buildHeader("✎  Input Manual",
                "Masukkan panjang sisi untuk menghitung luas, keliling, volume, dan luas permukaan."),
                BorderLayout.NORTH);

            JPanel body = new JPanel(new BorderLayout(14, 0));
            body.setBackground(C_BG);
            body.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));

            JTabbedPane tabs = darkTabs();
            tabs.addTab("Trapesium 2D", inputForm2D());
            tabs.addTab("Prisma",        inputFormPrisma());
            tabs.addTab("Limas",         inputFormLimas());
            tabs.setPreferredSize(new Dimension(340, 0));
            body.add(tabs, BorderLayout.WEST);

            JPanel right = new JPanel(new BorderLayout(0, 8));
            right.setBackground(C_BG);
            resultTitle = mkLabel("Hasil Perhitungan", 13, Font.BOLD, C_ACCENT2);
            right.add(resultTitle, BorderLayout.NORTH);

            result = new JTextArea("Pilih jenis bangun dan tekan Hitung...");
            result.setEditable(false);
            result.setBackground(C_PANEL);
            result.setForeground(C_TEXT);
            result.setFont(new Font("Consolas", Font.PLAIN, 12));
            result.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
            result.setLineWrap(true);
            JScrollPane rsp = new JScrollPane(result);
            rsp.setBorder(BorderFactory.createLineBorder(C_BORDER));
            right.add(rsp, BorderLayout.CENTER);
            body.add(right, BorderLayout.CENTER);
            add(body, BorderLayout.CENTER);
        }

        JPanel inputForm2D() {
            JPanel p = formPanel();
            String[] lbl = {"Sisi Atas","Sisi Bawah","Tinggi","Sisi Kiri","Sisi Kanan"};
            for (int i = 0; i < 5; i++) { f2D[i] = field(); addRow(p, lbl[i], f2D[i]); }
            JButton btn = actionBtn("Hitung Trapesium 2D", C_ACCENT2);
            btn.addActionListener(e -> hitung2D());
            p.add(Box.createVerticalStrut(12)); p.add(btn);
            return wrap(p);
        }

        JPanel inputFormPrisma() {
            JPanel p = formPanel();
            String[] lbl = {"Sisi Atas","Sisi Bawah","Tinggi Alas","Sisi Kiri","Sisi Kanan","Panjang Prisma"};
            for (int i = 0; i < 6; i++) { fPr[i] = field(); addRow(p, lbl[i], fPr[i]); }
            JButton btn = actionBtn("Hitung Prisma", C_ACCENT);
            btn.addActionListener(e -> hitungPrisma());
            p.add(Box.createVerticalStrut(12)); p.add(btn);
            return wrap(p);
        }

        JPanel inputFormLimas() {
            JPanel p = formPanel();
            String[] lbl = {"Sisi Atas","Sisi Bawah","Tinggi Alas","Sisi Kiri","Sisi Kanan","Tinggi Limas"};
            for (int i = 0; i < 6; i++) { fLi[i] = field(); addRow(p, lbl[i], fLi[i]); }
            JButton btn = actionBtn("Hitung Limas", C_ACCENT3);
            btn.addActionListener(e -> hitungLimas());
            p.add(Box.createVerticalStrut(12)); p.add(btn);
            return wrap(p);
        }

        void hitung2D() {
            try {
                double atas=dbl(f2D[0]),bawah=dbl(f2D[1]),tinggi=dbl(f2D[2]),kiri=dbl(f2D[3]),kanan=dbl(f2D[4]);
                Trapesium t = new Trapesium(atas,bawah,tinggi,kiri,kanan);
                double L=t.hitungLuas(), K=t.hitungKeliling();
                resultTitle.setText("Hasil — Trapesium 2D");
                result.setText(
                    "══ TRAPESIUM 2D ══════════════════════\n\n" +
                    "  Input:\n" +
                    "    Sisi Atas  = "+atas+"\n    Sisi Bawah = "+bawah+
                    "\n    Tinggi    = "+tinggi+"\n    Sisi Kiri  = "+kiri+"\n    Sisi Kanan = "+kanan+"\n\n"+
                    "  ── Luas ─────────────────────────────\n"+
                    "  L = ½ × (atas + bawah) × tinggi\n"+
                    String.format("    = ½ × (%.2f + %.2f) × %.2f\n",atas,bawah,tinggi)+
                    String.format("    = %.2f\n\n",L)+
                    "  ── Keliling ─────────────────────────\n"+
                    "  K = atas + bawah + kiri + kanan\n"+
                    String.format("    = %.2f + %.2f + %.2f + %.2f\n",atas,bawah,kiri,kanan)+
                    String.format("    = %.2f\n",K));
            } catch(Exception ex){ showErr(ex.getMessage()); }
        }

        void hitungPrisma() {
            try {
                double a=dbl(fPr[0]),b=dbl(fPr[1]),t=dbl(fPr[2]),ki=dbl(fPr[3]),ka=dbl(fPr[4]),p=dbl(fPr[5]);
                PrismaTrapesium pr = new PrismaTrapesium(a,b,t,ki,ka,p);
                double L=pr.hitungLuas(a,b,t), K=pr.hitungKeliling(a,b,ki,ka);
                double V=pr.hitungVolume(a,b,t), LP=pr.hitungLuasPermukaan(a,b,ka,ki,t);
                resultTitle.setText("Hasil — Prisma Trapesium");
                result.setText(
                    "══ PRISMA TRAPESIUM ══════════════════\n\n"+
                    "  Input:\n    Atas="+a+" Bawah="+b+" Tinggi="+t+
                    "\n    Kiri="+ki+" Kanan="+ka+" Panjang="+p+"\n\n"+
                    "  ── Luas Alas ────────────────────────\n"+
                    String.format("  L  = ½×(%.2f+%.2f)×%.2f = %.2f\n\n",a,b,t,L)+
                    "  ── Keliling Alas ────────────────────\n"+
                    String.format("  K  = %.2f\n\n",K)+
                    "  ── Volume ───────────────────────────\n"+
                    "  V  = Luas Alas × Panjang\n"+
                    String.format("     = %.2f × %.2f = %.2f\n\n",L,p,V)+
                    "  ── Luas Permukaan ───────────────────\n"+
                    "  LP = (2×L) + (a+b+ki+ka)×panjang\n"+
                    String.format("     = (2×%.2f)+(%.2f+%.2f+%.2f+%.2f)×%.2f\n",L,a,b,ki,ka,p)+
                    String.format("     = %.2f\n",LP));
            } catch(Exception ex){ showErr(ex.getMessage()); }
        }

        void hitungLimas() {
            try {
                double a=dbl(fLi[0]),b=dbl(fLi[1]),t=dbl(fLi[2]),ki=dbl(fLi[3]),ka=dbl(fLi[4]),tl=dbl(fLi[5]);
                LimasTrapesium li = new LimasTrapesium(a,b,t,ki,ka,tl);
                double L=li.hitungLuas(a,b,t), K=li.hitungKeliling(a,b,ki,ka);
                double V=li.hitungVolume(a,b,t), LP=li.hitungLuasPermukaan(a,b,t,ki,ka);
                double pAB=(b-a)/2.0, pKK=t/2.0;
                double apAB=Math.sqrt(tl*tl+pAB*pAB), apKK=Math.sqrt(tl*tl+pKK*pKK);
                resultTitle.setText("Hasil — Limas Trapesium");
                result.setText(
                    "══ LIMAS TRAPESIUM ═══════════════════\n\n"+
                    "  Input:\n    Atas="+a+" Bawah="+b+" Tinggi="+t+
                    "\n    Kiri="+ki+" Kanan="+ka+" TinggiLimas="+tl+"\n\n"+
                    "  ── Luas Alas ────────────────────────\n"+
                    String.format("  L  = ½×(%.2f+%.2f)×%.2f = %.2f\n\n",a,b,t,L)+
                    "  ── Keliling Alas ────────────────────\n"+
                    String.format("  K  = %.2f\n\n",K)+
                    "  ── Apotema ──────────────────────────\n"+
                    String.format("  apAB = √(%.2f²+%.2f²) = %.4f\n",tl,pAB,apAB)+
                    String.format("  apKK = √(%.2f²+%.2f²) = %.4f\n\n",tl,pKK,apKK)+
                    "  ── Volume ───────────────────────────\n"+
                    String.format("  V  = ⅓ × %.2f × %.2f = %.2f\n\n",L,tl,V)+
                    "  ── Luas Permukaan ───────────────────\n"+
                    String.format("  LP = %.2f\n",LP));
            } catch(Exception ex){ showErr(ex.getMessage()); }
        }

        void showErr(String msg) {
            resultTitle.setText("⚠ Input Tidak Valid");
            result.setText("Pastikan semua field diisi dengan angka.\n\nDetail: " + msg);
        }
        double dbl(JTextField tf) { return Double.parseDouble(tf.getText().trim()); }
    }

    // ══════════════════════════════════════════════════════════════
    //  MENU 2 — MULTITHREADING VISUAL
    // ══════════════════════════════════════════════════════════════
    class Menu2Panel extends JPanel {

        JSpinner          spinner;
        JButton           runBtn;
        JLabel            statusLabel;
        JProgressBar      progressBar;
        ThreadVisualPanel visualPanel;
        JTextArea         logArea;
        DefaultTableModel tableModel;

        // kolom tabel hasil
        static final String[] COLS = {
            "#", "Jenis", "Atas", "Bawah", "Tinggi", "Kiri", "Kanan", "Extra",
            "Luas", "Keliling", "Volume", "Luas Permukaan", "Thread", "ms"
        };

        Menu2Panel() {
            setBackground(C_BG);
            setLayout(new BorderLayout());
            add(buildHeader("⟳  Multithreading",
                "Generate data random, proses paralel dengan thread pool, visualisasi real-time."),
                BorderLayout.NORTH);

            JPanel body = new JPanel(new BorderLayout(0, 8));
            body.setBackground(C_BG);
            body.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
            body.add(buildControlBar(), BorderLayout.NORTH);

            // Split utama: atas = visual+log, bawah = tabel
            JSplitPane mainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            mainSplit.setBackground(C_BG);
            mainSplit.setDividerSize(7);
            mainSplit.setResizeWeight(0.45);
            mainSplit.setBorder(null);

            // ── Bagian atas: visual thread + log (split horizontal)
            JSplitPane topSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            topSplit.setBackground(C_BG);
            topSplit.setDividerSize(5);
            topSplit.setResizeWeight(0.65);
            topSplit.setBorder(null);

            // Panel visual thread
            visualPanel = new ThreadVisualPanel();
            JScrollPane vsp = new JScrollPane(visualPanel);
            vsp.setBorder(BorderFactory.createLineBorder(C_BORDER));
            vsp.getViewport().setBackground(new Color(14, 18, 34));
            topSplit.setTopComponent(vsp);

            // Panel log
            JPanel logPanel = new JPanel(new BorderLayout(0, 4));
            logPanel.setBackground(C_BG);
            logPanel.add(mkLabel("  🧵 Thread Log", 11, Font.BOLD, C_MUTED), BorderLayout.NORTH);
            logArea = new JTextArea();
            logArea.setEditable(false);
            logArea.setBackground(new Color(8, 10, 20));
            logArea.setForeground(new Color(130, 210, 130));
            logArea.setFont(new Font("Consolas", Font.PLAIN, 11));
            logArea.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
            JScrollPane lsp = new JScrollPane(logArea);
            lsp.setBorder(BorderFactory.createLineBorder(C_BORDER));
            logPanel.add(lsp, BorderLayout.CENTER);
            topSplit.setBottomComponent(logPanel);

            mainSplit.setTopComponent(topSplit);

            // ── Bagian bawah: tabel hasil
            JPanel tablePanel = new JPanel(new BorderLayout(0, 4));
            tablePanel.setBackground(C_BG);
            tablePanel.add(mkLabel("  📋 Tabel Hasil Perhitungan", 11, Font.BOLD, C_MUTED), BorderLayout.NORTH);

            tableModel = new DefaultTableModel(COLS, 0) {
                @Override public boolean isCellEditable(int r, int c) { return false; }
            };
            JTable table = new JTable(tableModel);
            styleTable(table);
            JScrollPane tsp = new JScrollPane(table);
            tsp.setBorder(BorderFactory.createLineBorder(C_BORDER));
            tsp.getViewport().setBackground(C_PANEL);
            tablePanel.add(tsp, BorderLayout.CENTER);
            mainSplit.setBottomComponent(tablePanel);

            body.add(mainSplit, BorderLayout.CENTER);

            // Progress bar
            JPanel progRow = new JPanel(new BorderLayout(8, 0));
            progRow.setBackground(C_BG);
            progRow.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
            progressBar = new JProgressBar(0, 100);
            progressBar.setStringPainted(true);
            progressBar.setFont(new Font("Segoe UI", Font.BOLD, 11));
            progressBar.setForeground(C_ACCENT);
            progressBar.setBackground(new Color(22, 28, 52));
            progressBar.setPreferredSize(new Dimension(0, 20));
            statusLabel = mkLabel("Siap.", 11, Font.ITALIC, C_MUTED);
            progRow.add(progressBar, BorderLayout.CENTER);
            progRow.add(statusLabel, BorderLayout.EAST);
            body.add(progRow, BorderLayout.SOUTH);

            add(body, BorderLayout.CENTER);
        }

        JPanel buildControlBar() {
            JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
            bar.setBackground(C_BG);
            bar.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

            bar.add(mkLabel("Jumlah Data:", 13, Font.PLAIN, C_TEXT));

            // Tidak ada batas maksimal (Integer.MAX_VALUE)
            spinner = new JSpinner(new SpinnerNumberModel(6, 1, Integer.MAX_VALUE, 1));
            spinner.setPreferredSize(new Dimension(90, 30));
            JSpinner.NumberEditor ed = new JSpinner.NumberEditor(spinner, "#");
            spinner.setEditor(ed);
            ed.getTextField().setBackground(C_PANEL);
            ed.getTextField().setForeground(C_TEXT);
            ed.getTextField().setFont(new Font("Consolas", Font.PLAIN, 13));
            bar.add(spinner);

            runBtn = actionBtn("▶  Jalankan", C_ACCENT);
            runBtn.addActionListener(e -> jalankan());
            bar.add(runBtn);

            JButton clrBtn = actionBtn("✕  Bersihkan", new Color(80, 40, 40));
            clrBtn.addActionListener(e -> {
                visualPanel.clear();
                logArea.setText("");
                tableModel.setRowCount(0);
                progressBar.setValue(0);
                statusLabel.setText("Siap.");
            });
            bar.add(clrBtn);

            bar.add(mkLabel("  ⚠ Thread pool: 4 thread paralel", 11, Font.ITALIC, C_MUTED));
            return bar;
        }

        void styleTable(JTable t) {
            t.setBackground(C_PANEL);
            t.setForeground(C_TEXT);
            t.setGridColor(new Color(30, 42, 72));
            t.setRowHeight(24);
            t.setFont(new Font("Consolas", Font.PLAIN, 11));
            t.setSelectionBackground(new Color(30, 60, 100));
            t.setSelectionForeground(Color.WHITE);
            t.setShowHorizontalLines(true);
            t.setShowVerticalLines(true);
            t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            JTableHeader hdr = t.getTableHeader();
            hdr.setBackground(new Color(20, 28, 55));
            hdr.setForeground(C_ACCENT2);
            hdr.setFont(new Font("Segoe UI", Font.BOLD, 11));
            hdr.setReorderingAllowed(false);

            // lebar kolom
            int[] w = {36, 90, 52, 52, 52, 52, 52, 60, 72, 76, 86, 106, 150, 52};
            for (int i = 0; i < w.length && i < t.getColumnCount(); i++)
                t.getColumnModel().getColumn(i).setPreferredWidth(w[i]);

            // alternating row color
            t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override public Component getTableCellRendererComponent(
                        JTable tbl, Object val, boolean sel, boolean foc, int row, int col) {
                    super.getTableCellRendererComponent(tbl, val, sel, foc, row, col);
                    if (!sel) {
                        Object jenis = tbl.getValueAt(row, 1);
                        if ("Trapesium 2D".equals(jenis))   setBackground(new Color(18, 26, 50));
                        else if ("Prisma".equals(jenis))    setBackground(new Color(16, 30, 48));
                        else                                setBackground(new Color(18, 28, 44));
                    }
                    setForeground(sel ? Color.WHITE : C_TEXT);
                    setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
                    return this;
                }
            });
        }

        // ── Jalankan ──────────────────────────────────────────────
        void jalankan() {
            int n = (int) spinner.getValue();
            visualPanel.clear();
            logArea.setText("");
            tableModel.setRowCount(0);
            progressBar.setValue(0);
            statusLabel.setText("Memulai " + n + " job...");
            runBtn.setEnabled(false);

            Random rng     = new Random();
            int poolSize   = Math.min(n, 4);
            visualPanel.initThreads(poolSize, n);

            ExecutorService pool   = Executors.newFixedThreadPool(poolSize);
            AtomicInteger   done   = new AtomicInteger(0);
            long[]          gStart = {System.currentTimeMillis()};

            // Untuk data banyak, log hanya 500 baris terakhir supaya tidak lag
            final int LOG_LIMIT = 500;

            for (int i = 0; i < n; i++) {
                final int    idx    = i + 1;
                final int    type   = i % 3;
                final double atas   = Math.round((rng.nextDouble() * 8  + 2)    * 10.0) / 10.0;
                final double bawah  = Math.round((rng.nextDouble() * 8  + atas) * 10.0) / 10.0;
                final double tinggi = Math.round((rng.nextDouble() * 6  + 2)    * 10.0) / 10.0;
                final double kiri   = Math.round((rng.nextDouble() * 5  + 2)    * 10.0) / 10.0;
                final double kanan  = Math.round((rng.nextDouble() * 5  + 2)    * 10.0) / 10.0;
                final double extra  = Math.round((rng.nextDouble() * 8  + 3)    * 10.0) / 10.0;

                pool.submit(() -> {
                    String tn   = Thread.currentThread().getName();
                    int    tIdx = threadIndex(tn, poolSize);
                    long   t0   = System.currentTimeMillis();

                    // update visual hanya setiap 50 job untuk data banyak (performa)
                    boolean doVisual = (idx <= 200) || (idx % 50 == 0);

                    if (doVisual) {
                        SwingUtilities.invokeLater(() -> visualPanel.setJobActive(tIdx, idx, type));
                    }

                    String jenis;
                    double luas = 0, kel = 0, vol = Double.NaN, lp = Double.NaN;

                    if (type == 0) {
                        jenis = "Trapesium 2D";
                        Trapesium tt = new Trapesium(atas, bawah, tinggi, kiri, kanan);
                        luas = tt.hitungLuas(atas, bawah, tinggi);
                        kel  = tt.hitungKeliling(atas, bawah, kiri, kanan);
                    } else if (type == 1) {
                        jenis = "Prisma";
                        PrismaTrapesium pr = new PrismaTrapesium(atas, bawah, tinggi, kiri, kanan, extra);
                        luas = pr.hitungLuas(atas, bawah, tinggi);
                        kel  = pr.hitungKeliling(atas, bawah, kiri, kanan);
                        vol  = pr.hitungVolume(atas, bawah, tinggi);
                        lp   = pr.hitungLuasPermukaan(atas, bawah, kanan, kiri, tinggi);
                    } else {
                        jenis = "Limas";
                        LimasTrapesium li = new LimasTrapesium(atas, bawah, tinggi, kiri, kanan, extra);
                        luas = li.hitungLuas(atas, bawah, tinggi);
                        kel  = li.hitungKeliling(atas, bawah, kiri, kanan);
                        vol  = li.hitungVolume(atas, bawah, tinggi);
                        lp   = li.hitungLuasPermukaan(atas, bawah, tinggi, kiri, kanan);
                    }

                    long   elapsed = System.currentTimeMillis() - t0;
                    int    d       = done.incrementAndGet();
                    String fJenis  = jenis;
                    double fLuas   = luas, fKel = kel, fVol = vol, fLp = lp;
                    String volStr  = Double.isNaN(vol) ? "—" : String.format("%.2f", vol);
                    String lpStr   = Double.isNaN(lp)  ? "—" : String.format("%.2f", lp);
                    String shortTn = shortThread(tn);

                    SwingUtilities.invokeLater(() -> {
                        // tabel — selalu tambahkan
                        tableModel.addRow(new Object[]{
                            idx, fJenis,
                            String.format("%.1f", atas),
                            String.format("%.1f", bawah),
                            String.format("%.1f", tinggi),
                            String.format("%.1f", kiri),
                            String.format("%.1f", kanan),
                            type == 0 ? "—" : String.format("%.1f", extra),
                            String.format("%.2f", fLuas),
                            String.format("%.2f", fKel),
                            volStr, lpStr,
                            shortTn, elapsed + " ms"
                        });

                        // visual thread
                        if (doVisual) {
                            visualPanel.setJobDone(tIdx, idx, fJenis, fLuas, fKel, fVol, fLp, elapsed);
                        }

                        // log — batasi supaya tidak lag di data besar
                        if (d <= LOG_LIMIT) {
                            appendLog("[" + shortTn + "] ✔ #" + idx + " " + fJenis
                                + " L=" + String.format("%.2f", fLuas)
                                + " | " + elapsed + "ms");
                        } else if (d == LOG_LIMIT + 1) {
                            appendLog("... (log dibatasi " + LOG_LIMIT + " baris untuk performa) ...");
                        }

                        // progress
                        int pct = d * 100 / n;
                        progressBar.setValue(pct);
                        progressBar.setString(pct + "%  (" + d + "/" + n + ")");

                        if (d == n) {
                            long total = System.currentTimeMillis() - gStart[0];
                            statusLabel.setText("✔ Selesai " + n + " job dalam " + total + " ms");
                            appendLog("══ SELESAI: " + n + " job | " + total + " ms | pool=" + poolSize + " thread ══");
                            runBtn.setEnabled(true);
                        }
                    });
                });
            }
            pool.shutdown();
        }

        void appendLog(String msg) {
            logArea.append(msg + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }

        String shortThread(String tn) {
            String[] p = tn.split("-");
            return "T-" + p[p.length - 1];
        }

        int threadIndex(String tn, int poolSize) {
            try {
                String[] p = tn.split("-");
                return (Integer.parseInt(p[p.length - 1]) - 1) % poolSize;
            } catch (Exception e) { return 0; }
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  THREAD VISUAL PANEL
    // ══════════════════════════════════════════════════════════════
    static class ThreadVisualPanel extends JPanel {

        static class JobCard {
            int    idx;
            String jenis, status;
            double luas, kel, vol, lp;
            long   ms;
            Color  accent;
            JobCard(int idx, Color c) { this.idx=idx; this.jenis=""; this.status="idle"; this.accent=c; }
        }

        static class ThreadRow {
            int         tIdx;
            String      name;
            Color       color;
            List<JobCard> cards = new ArrayList<>();
            int         activeJob = -1;
            // ringkasan untuk data besar
            int         totalDone = 0;
            long        totalMs   = 0;
            ThreadRow(int i, Color c) { tIdx=i; color=c; name="Thread-"+(i+1); }
        }

        List<ThreadRow> rows      = new ArrayList<>();
        int             totalJobs = 0;
        // batas kartu yang ditampilkan per baris (performa)
        static final int MAX_CARDS_SHOWN = 80;

        ThreadVisualPanel() {
            setBackground(new Color(14, 18, 34));
            setLayout(null);
        }

        void initThreads(int nThreads, int nJobs) {
            rows.clear();
            totalJobs = nJobs;
            for (int i = 0; i < nThreads; i++)
                rows.add(new ThreadRow(i, THREAD_COLORS[i % THREAD_COLORS.length]));
            revalidate(); repaint();
        }

        void clear() { rows.clear(); totalJobs = 0; repaint(); }

        void setJobActive(int tIdx, int jobIdx, int type) {
            if (tIdx >= rows.size()) return;
            ThreadRow row = rows.get(tIdx);
            // hanya simpan kartu jika belum melebihi batas tampil
            if (row.cards.size() < MAX_CARDS_SHOWN) {
                JobCard card = new JobCard(jobIdx, row.color);
                card.jenis  = type == 0 ? "2D" : type == 1 ? "Prisma" : "Limas";
                card.status = "active";
                row.cards.add(card);
            }
            row.activeJob = jobIdx;
            updatePrefSize();
            repaint();
        }

        void setJobDone(int tIdx, int jobIdx, String jenis,
                        double luas, double kel, double vol, double lp, long ms) {
            if (tIdx >= rows.size()) return;
            ThreadRow row = rows.get(tIdx);
            for (JobCard c : row.cards) {
                if (c.idx == jobIdx) {
                    c.status = "done"; c.jenis = jenis;
                    c.luas = luas; c.kel = kel; c.vol = vol; c.lp = lp; c.ms = ms;
                    break;
                }
            }
            if (row.activeJob == jobIdx) row.activeJob = -1;
            row.totalDone++;
            row.totalMs += ms;
            updatePrefSize();
            repaint();
        }

        void updatePrefSize() {
            int maxCards = rows.stream().mapToInt(r -> Math.min(r.cards.size(), MAX_CARDS_SHOWN)).max().orElse(1);
            int w = Math.max(getParent() != null ? getParent().getWidth() : 700, maxCards * 120 + 170);
            int h = rows.size() * 110 + 20;
            setPreferredSize(new Dimension(w, h));
            revalidate();
        }

        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (rows.isEmpty()) { paintEmpty(g); return; }
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int y = 14;
            for (ThreadRow row : rows) { paintRow(g2, row, y); y += 108; }
        }

        void paintEmpty(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(50, 60, 90));
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            String msg = "Tekan ▶ Jalankan untuk memulai simulasi thread";
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2);
        }

        void paintRow(Graphics2D g2, ThreadRow row, int y) {
            int rowH = 96;
            g2.setColor(new Color(20, 26, 46));
            g2.fillRoundRect(8, y, getWidth() - 16, rowH, 10, 10);
            g2.setColor(row.color.darker().darker());
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawRoundRect(8, y, getWidth() - 16, rowH, 10, 10);

            int lw = 130;
            g2.setColor(new Color(row.color.getRed(), row.color.getGreen(), row.color.getBlue(), 28));
            g2.fillRoundRect(10, y+2, lw-4, rowH-4, 8, 8);

            // status dot
            boolean active = row.activeJob >= 0;
            g2.setColor(active ? row.color : row.color.darker());
            g2.fillOval(20, y + 14, 12, 12);
            if (active) {
                g2.setColor(new Color(row.color.getRed(), row.color.getGreen(), row.color.getBlue(), 70));
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(16, y + 10, 20, 20);
            }

            g2.setFont(new Font("Segoe UI", Font.BOLD, 12)); g2.setColor(row.color);
            g2.drawString(row.name, 38, y + 22);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2.setColor(active ? C_SUCCESS : new Color(120, 140, 180));
            g2.drawString(active ? "● AKTIF" : "○ IDLE", 38, y + 35);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
            g2.setColor(new Color(90, 110, 160));
            g2.drawString("Done: " + row.totalDone, 38, y + 48);
            if (row.totalDone > 0)
                g2.drawString("Avg: " + (row.totalMs / row.totalDone) + "ms", 38, y + 59);

            // apabila data sangat banyak, tampilkan info ringkas
            if (row.totalDone > MAX_CARDS_SHOWN) {
                g2.setFont(new Font("Segoe UI", Font.ITALIC, 9));
                g2.setColor(new Color(80, 100, 140));
                g2.drawString("(tampil " + MAX_CARDS_SHOWN + " kartu)", 38, y + 70);
            }

            // divider
            g2.setColor(new Color(35, 45, 80));
            g2.setStroke(new BasicStroke(1f));
            g2.drawLine(lw + 4, y + 8, lw + 4, y + rowH - 8);

            // kartu job
            int cx = lw + 10;
            for (JobCard card : row.cards) {
                if (cx + 108 > getWidth() - 10) break; // jangan keluar batas layar
                paintCard(g2, card, cx, y + 6, 105, rowH - 12);
                cx += 110;
            }
        }

        void paintCard(Graphics2D g2, JobCard card, int x, int y, int w, int h) {
            boolean active = "active".equals(card.status);
            boolean done   = "done".equals(card.status);

            Color bg  = active ? new Color(card.accent.getRed(), card.accent.getGreen(), card.accent.getBlue(), 30)
                               : done ? new Color(22, 32, 52) : new Color(16, 20, 36);
            Color bdr = active ? card.accent : done ? card.accent.darker() : new Color(32, 42, 68);

            g2.setColor(bg);
            g2.fillRoundRect(x, y, w, h, 8, 8);
            g2.setColor(bdr);
            g2.setStroke(new BasicStroke(active ? 1.8f : 1f));
            g2.drawRoundRect(x, y, w, h, 8, 8);

            // badge jenis
            Color badge = card.jenis.contains("2D")   ? C_ACCENT2 :
                          card.jenis.contains("Pris")  ? C_ACCENT  : C_ACCENT3;
            g2.setColor(badge);
            g2.fillRoundRect(x+3, y+3, w-6, 13, 4, 4);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 8));
            g2.setColor(Color.WHITE);
            FontMetrics fm = g2.getFontMetrics();
            String bj = card.jenis.length() > 11 ? card.jenis.substring(0,9)+"…" : card.jenis;
            g2.drawString(bj, x + (w - fm.stringWidth(bj)) / 2, y + 12);

            // nomor job
            g2.setFont(new Font("Segoe UI", Font.BOLD, 15));
            g2.setColor(active ? card.accent : done ? C_TEXT : new Color(70, 90, 130));
            String num = "#" + card.idx;
            FontMetrics fm2 = g2.getFontMetrics();
            g2.drawString(num, x + (w - fm2.stringWidth(num)) / 2, y + 36);

            if (active) {
                long tick = System.currentTimeMillis() / 150;
                for (int d = 0; d < 4; d++) {
                    float alpha = (d == (tick % 4)) ? 1f : 0.2f;
                    g2.setColor(new Color(
                        card.accent.getRed(), card.accent.getGreen(), card.accent.getBlue(),
                        (int)(255 * alpha)));
                    g2.fillOval(x + w/2 - 10 + d*7, y + h - 14, 5, 5);
                }
                // trigger repaint animasi
//                Timer t = new Timer(150, ev -> repaint());
//                t.setRepeats(false); t.start();
            }

            if (done) {
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                g2.setColor(C_SUCCESS);
                FontMetrics fm3 = g2.getFontMetrics();
                g2.drawString("✔", x + (w - fm3.stringWidth("✔")) / 2, y + 52);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 8));
                g2.setColor(new Color(90, 120, 160));
                String msStr = card.ms + "ms";
                FontMetrics fm4 = g2.getFontMetrics();
                g2.drawString(msStr, x + (w - fm4.stringWidth(msStr)) / 2, y + h - 4);
            }
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  SHARED HELPERS
    // ══════════════════════════════════════════════════════════════
    static JLabel mkLabel(String text, int size, int style, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", style, size));
        l.setForeground(color);
        return l;
    }

    static JPanel buildHeader(String title, String sub) {
        JPanel h = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0,0,new Color(18,24,46),getWidth(),0,new Color(12,16,32)));
                g2.fillRect(0,0,getWidth(),getHeight());
                g2.setColor(C_BORDER); g2.fillRect(0, getHeight()-1, getWidth(), 1);
            }
        };
        h.setLayout(new BoxLayout(h, BoxLayout.Y_AXIS));
        h.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        JLabel t = mkLabel(title, 18, Font.BOLD, C_TEXT);
        JLabel s = mkLabel(sub,   12, Font.PLAIN, C_MUTED);
        h.add(t); h.add(Box.createVerticalStrut(3)); h.add(s);
        return h;
    }

    static JTextField field() {
        JTextField tf = new JTextField();
        tf.setBackground(new Color(28, 34, 60));
        tf.setForeground(C_TEXT);
        tf.setCaretColor(C_ACCENT);
        tf.setFont(new Font("Consolas", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDER),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        return tf;
    }

    static JPanel formPanel() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        return p;
    }

    static void addRow(JPanel p, String label, JTextField tf) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        JLabel l = mkLabel(label, 12, Font.PLAIN, C_MUTED);
        l.setPreferredSize(new Dimension(120, 28));
        row.add(l, BorderLayout.WEST);
        row.add(tf, BorderLayout.CENTER);
        p.add(row); p.add(Box.createVerticalStrut(5));
    }

    static JPanel wrap(JPanel p) {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(C_PANEL);
        outer.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        outer.add(p, BorderLayout.NORTH);
        return outer;
    }

    static JTabbedPane darkTabs() {
        JTabbedPane tp = new JTabbedPane();
        tp.setBackground(C_PANEL);
        tp.setForeground(C_TEXT);
        tp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tp.setBorder(BorderFactory.createLineBorder(C_BORDER));
        return tp;
    }

    static JButton actionBtn(String text, Color base) {
        JButton b = new JButton(text) {
            boolean hov = false;
            { setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
              setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
              addMouseListener(new MouseAdapter() {
                  public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                  public void mouseExited (MouseEvent e) { hov = false; repaint(); }
              });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? base.brighter() : base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        return b;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(KalkulatorTrapesiumGUI::new);
    }
}