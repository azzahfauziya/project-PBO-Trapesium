package kalkulatortrapesium;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ============================================================
 *  KalkulatorTrapesiumGUI  –  Entry point berbasis Swing
 * ============================================================
 *  Hanya FILE INI yang baru / berubah.
 *  Semua kelas lain (Trapesium, PrismaTrapesium, LimasTrapesium,
 *  Geometri2D, Geometri3D) TIDAK dimodifikasi.
 *
 *  Cara pakai:
 *   1. Jalankan kelas ini (bukan KalkulatorTrapesium.java lagi)
 *   2. Isi "Jumlah Data" dan "Jumlah Thread" di panel kiri
 *   3. Klik "▶  HITUNG"
 *   4. Hasil & waktu eksekusi muncul di tab tabel kanan
 * ============================================================
 */
public class KalkulatorTrapesiumGUI extends JFrame {

    // ── Konstanta warna (color palette baru) ──────────────
    private static final Color BG_DARK      = new Color(22, 38, 96);      // #162660
    private static final Color BG_PANEL     = new Color(22, 38, 96);      // #162660
    private static final Color BG_CARD      = new Color(22, 38, 96);      // #162660
    private static final Color BG_INPUT     = new Color(22, 38, 96);      // #162660
    private static final Color ACCENT_BLUE  = new Color(208, 230, 253);   // #D0E6FD
    private static final Color ACCENT_DARK  = new Color(13, 17, 28);
    private static final Color ACCENT_CYAN  = new Color(0, 40, 42);   // #D0E6FD
    private static final Color ACCENT_GOLD  = new Color(241, 228, 209);   // #F1E4D1
    private static final Color TEXT_PRIMARY = new Color(241, 228, 209);   // #F1E4D1
    private static final Color TEXT_MUTED   = new Color(208, 230, 253);   // #D0E6FD
    private static final Color BORDER_COLOR = new Color(208, 230, 253);   // #D0E6FD
    private static final Color ROW_ALT      = new Color(35, 55, 120);     // lebih terang dari #162660
    private static final Color HEADER_TBL   = new Color(208, 230, 253);   // #D0E6FD

    // ── Font yang diperbesar ─────────────────────────────────────────────────
    private static final Font FONT_TITLE  = new Font("Serif", Font.BOLD, 28);
    private static final Font FONT_SUB    = new Font("Monospaced", Font.PLAIN, 14);
    private static final Font FONT_LABEL  = new Font("SansSerif", Font.BOLD, 16);
    private static final Font FONT_INPUT  = new Font("Monospaced", Font.PLAIN, 18);
    private static final Font FONT_BTN    = new Font("SansSerif", Font.BOLD, 16);
    private static final Font FONT_TABLE  = new Font("Monospaced", Font.PLAIN, 14);
    private static final Font FONT_HEADER = new Font("SansSerif", Font.BOLD, 14);
    private static final Font FONT_STAT   = new Font("Monospaced", Font.BOLD, 24);

    // ── Komponen input ────────────────────────────────────────────────────────
    private JSpinner spnData;
    private JSpinner spnThread;
    private JButton  btnHitung;
    private JButton  btnReset;

    // ── Komponen output ───────────────────────────────────────────────────────
    private JLabel lblTimePrisma, lblTimeLimas, lblTimeTotal;
    private JTable tblPrisma, tblLimas;
    private DefaultTableModel modelPrisma, modelLimas;
    private JTabbedPane tabs;
    private JLabel lblStatus;
    private JProgressBar progressBar;

    // ── Konstanta data (sama dengan KalkulatorTrapesium asli) ─────────────────
    static final double MIN_SISI = 5.0;
    static final double MAX_SISI = 50.0;

    // ─────────────────────────────────────────────────────────────────────────
    public KalkulatorTrapesiumGUI() {
        setTitle("Kalkulator Trapesium");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1400, 800));
        setPreferredSize(new Dimension(1600, 950));
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout(0, 0));

        add(buildHeader(),  BorderLayout.NORTH);
        add(buildCenter(),  BorderLayout.CENTER);
        add(buildFooter(),  BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  HEADER
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                // gradient latar header
                GradientPaint gp = new GradientPaint(0, 0, BG_DARK,
                        getWidth(), 0, new Color(30, 50, 110));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // garis bawah accent
                g2.setColor(ACCENT_BLUE);
                g2.setStroke(new BasicStroke(3));
                g2.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2);
            }
        };
        p.setPreferredSize(new Dimension(0, 100));
        p.setBorder(new EmptyBorder(20, 32, 20, 32));

        // kiri: judul
        JPanel left = new JPanel(new GridLayout(2, 1, 0, 5));
        left.setOpaque(false);
        JLabel title = new JLabel("KALKULATOR TRAPESIUM");
        title.setFont(FONT_TITLE);
        title.setForeground(ACCENT_GOLD);
        JLabel sub = new JLabel("Prisma & Limas  ·  Concurrent Multithreading  ·  Java Swing GUI");
        sub.setFont(FONT_SUB);
        sub.setForeground(ACCENT_BLUE);
        left.add(title);
        left.add(sub);

        // kanan: badge versi
        JLabel badge = new JLabel("Kelompok 2");
        badge.setFont(new Font("Monospaced", Font.BOLD, 14));
        badge.setForeground(ACCENT_BLUE);
        badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_BLUE, 2, true),
                new EmptyBorder(6, 14, 6, 14)));

        p.add(left,  BorderLayout.WEST);
        p.add(badge, BorderLayout.EAST);
        return p;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  AREA TENGAH  (panel kiri input + panel kanan hasil)
    // ══════════════════════════════════════════════════════════════════════════
    private JSplitPane buildCenter() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildLeftPanel(), buildRightPanel());
        split.setDividerLocation(350);
        split.setDividerSize(5);
        split.setBorder(null);
        split.setBackground(BG_DARK);
        return split;
    }

    // ─── PANEL KIRI ───────────────────────────────────────────────────────────
    private JPanel buildLeftPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_PANEL);
        p.setBorder(new EmptyBorder(25, 25, 25, 25));

        p.add(sectionTitle("⚙  KONFIGURASI"));
        p.add(Box.createVerticalStrut(20));

        // ── input jumlah data ──
        p.add(inputLabel("Jumlah Data", "Banyak trapesium yang digenerate secara acak"));
        p.add(Box.createVerticalStrut(8));
        spnData = styledSpinner(100, 1, 100_000, 50);
        p.add(spnData);
        p.add(Box.createVerticalStrut(20));

        // ── input jumlah thread ──
        p.add(inputLabel("Jumlah Thread", "Ukuran thread pool (ExecutorService)"));
        p.add(Box.createVerticalStrut(8));
        spnThread = styledSpinner(4, 1, 64, 1);
        p.add(spnThread);
        p.add(Box.createVerticalStrut(30));

        // ── tombol ──
        btnHitung = buildButton("▶   HITUNG", ACCENT_BLUE);
        btnHitung.addActionListener(e -> jalankanPerhitungan());
        btnHitung.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnHitung.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        p.add(btnHitung);
        p.add(Box.createVerticalStrut(12));

        btnReset = buildButton("↺   RESET", ACCENT_GOLD);
        btnReset.addActionListener(e -> resetUI());
        btnReset.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnReset.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        p.add(btnReset);

        p.add(Box.createVerticalGlue());

        // ── kartu OOP concepts ──
        p.add(oopCard());

        return p;
    }

    // ─── PANEL KANAN ──────────────────────────────────────────────────────────
    private JPanel buildRightPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 0));
        p.setBackground(BG_DARK);

        // --- baris statistik waktu ---
        p.add(buildStatRow(), BorderLayout.NORTH);

        // --- tabel ---
        tabs = new JTabbedPane();
        tabs.setFont(FONT_LABEL);
        tabs.setBackground(BG_DARK);
        tabs.setForeground(ACCENT_GOLD);

        // tab Prisma
        modelPrisma = buildTableModel(
                new String[]{"No","Atas","Bawah","Tinggi","Kiri","Kanan","Panjang",
                        "Luas Alas","Keliling","Volume","Luas Permukaan"});
        tblPrisma = buildTable(modelPrisma);
        tabs.addTab("  ▦  Prisma Trapesium  ", wrapTable(tblPrisma));

        // tab Limas
        modelLimas = buildTableModel(
                new String[]{"No","Atas","Bawah","Tinggi","Kiri","Kanan","Tinggi Limas",
                        "Luas Alas","Keliling","Volume","Luas Permukaan"});
        tblLimas = buildTable(modelLimas);
        tabs.addTab("  △  Limas Trapesium  ", wrapTable(tblLimas));

        styleTabPane(tabs);
        p.add(tabs, BorderLayout.CENTER);
        return p;
    }

    // ─── BARIS STATISTIK WAKTU ────────────────────────────────────────────────
    private JPanel buildStatRow() {
        JPanel row = new JPanel(new GridLayout(1, 3, 12, 0));
        row.setBackground(BG_DARK);
        row.setBorder(new EmptyBorder(16, 20, 16, 20));

        lblTimePrisma = new JLabel("— ms", SwingConstants.CENTER);
        lblTimeLimas  = new JLabel("— ms", SwingConstants.CENTER);
        lblTimeTotal  = new JLabel("— ms", SwingConstants.CENTER);

        row.add(statCard("PRISMA",   lblTimePrisma, ACCENT_BLUE));
        row.add(statCard("LIMAS",    lblTimeLimas,  ACCENT_BLUE));
        row.add(statCard("TOTAL",    lblTimeTotal,  ACCENT_GOLD));
        return row;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  FOOTER
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildFooter() {
        JPanel p = new JPanel(new BorderLayout(15, 0));
        p.setBackground(BG_DARK);
        p.setBorder(new CompoundBorder(
                new MatteBorder(2, 0, 0, 0, ACCENT_BLUE),
                new EmptyBorder(10, 22, 10, 22)));

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(false);
        progressBar.setPreferredSize(new Dimension(250, 14));
        progressBar.setBackground(new Color(50, 70, 130));
        progressBar.setForeground(ACCENT_GOLD);
        progressBar.setBorderPainted(false);

        lblStatus = new JLabel("Siap. Masukkan parameter lalu klik Hitung.");
        lblStatus.setFont(FONT_SUB);
        lblStatus.setForeground(ACCENT_BLUE);

        p.add(lblStatus,    BorderLayout.WEST);
        p.add(progressBar,  BorderLayout.EAST);
        return p;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  LOGIKA PERHITUNGAN  (sama persis dengan KalkulatorTrapesium.java asli)
    // ══════════════════════════════════════════════════════════════════════════
    private void jalankanPerhitungan() {
        int jumlahData   = (int) spnData.getValue();
        int jumlahThread = (int) spnThread.getValue();

        // kosongkan tabel lama
        modelPrisma.setRowCount(0);
        modelLimas.setRowCount(0);
        setStatus("Membuat " + jumlahData + " data random...", true);
        btnHitung.setEnabled(false);

        // jalankan di background agar UI tidak freeze
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            List<PrismaTrapesium> listPrisma;
            List<LimasTrapesium>  listLimas;
            long durasiPrisma, durasiLimas;

            @Override
            protected Void doInBackground() {
                publish("Membuat data random...");
                listPrisma = buatDataPrisma(jumlahData);
                listLimas  = buatDataLimas(jumlahData);

                publish("Menghitung Prisma Trapesium dengan " + jumlahThread + " thread...");
                long t0 = System.currentTimeMillis();
                hitungDenganThread(listPrisma, jumlahThread, "Prisma");
                durasiPrisma = System.currentTimeMillis() - t0;

                publish("Menghitung Limas Trapesium dengan " + jumlahThread + " thread...");
                t0 = System.currentTimeMillis();
                hitungDenganThread(listLimas, jumlahThread, "Limas");
                durasiLimas = System.currentTimeMillis() - t0;

                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                setStatus(chunks.get(chunks.size() - 1), true);
            }

            @Override
            protected void done() {
                // isi label waktu
                lblTimePrisma.setText(durasiPrisma + " ms");
                lblTimeLimas.setText(durasiLimas  + " ms");
                lblTimeTotal.setText((durasiPrisma + durasiLimas) + " ms");

                // isi tabel Prisma
                for (int i = 0; i < listPrisma.size(); i++) {
                    PrismaTrapesium p = listPrisma.get(i);
                    modelPrisma.addRow(new Object[]{
                            i + 1,
                            fmt(p.getAtas()), fmt(p.getBawah()), fmt(p.getTinggi()),
                            fmt(p.getKiri()), fmt(p.getKanan()), fmt(p.getPanjang()),
                            fmt(p.getHasilLuas()), fmt(p.getHasilKeliling()),
                            fmt(p.getHasilVolume()), fmt(p.getHasilLuasPermukaan())
                    });
                }

                // isi tabel Limas
                for (int i = 0; i < listLimas.size(); i++) {
                    LimasTrapesium l = listLimas.get(i);
                    modelLimas.addRow(new Object[]{
                            i + 1,
                            fmt(l.getAtas()), fmt(l.getBawah()), fmt(l.getTinggi()),
                            fmt(l.getKiri()), fmt(l.getKanan()), fmt(l.getTinggiLimas()),
                            fmt(l.getHasilLuas()), fmt(l.getHasilKeliling()),
                            fmt(l.getHasilVolume()), fmt(l.getHasilLuasPermukaan())
                    });
                }

                setStatus("Selesai. " + jumlahData + " data dihitung dengan "
                        + jumlahThread + " thread.", false);
                btnHitung.setEnabled(true);
                tabs.setSelectedIndex(0);
            }
        };
        worker.execute();
    }

    // ── Sama persis dengan metode di KalkulatorTrapesium (copy paste) ─────────

    static List<PrismaTrapesium> buatDataPrisma(int jumlah) {
        Random rng = new Random();
        List<PrismaTrapesium> list = new ArrayList<>();
        for (int i = 0; i < jumlah; i++)
            list.add(new PrismaTrapesium(acak(rng), acak(rng), acak(rng),
                    acak(rng), acak(rng), acak(rng)));
        return list;
    }

    static List<LimasTrapesium> buatDataLimas(int jumlah) {
        Random rng = new Random();
        List<LimasTrapesium> list = new ArrayList<>();
        for (int i = 0; i < jumlah; i++)
            list.add(new LimasTrapesium(acak(rng), acak(rng), acak(rng),
                    acak(rng), acak(rng), acak(rng)));
        return list;
    }

    static double acak(Random rng) {
        return MIN_SISI + (rng.nextDouble() * (MAX_SISI - MIN_SISI));
    }

    static void hitungDenganThread(
        List<? extends Trapesium> list,
        int jumlahThread, 
        String jenis) {
        ExecutorService pool = Executors.newFixedThreadPool(jumlahThread);
        for (Trapesium obj : list) {
            pool.submit(() -> {
                if (obj instanceof PrismaTrapesium p) {
                    p.hitungLuas(); p.hitungKeliling();
                    p.hitungVolume(); p.hitungLuasPermukaan();
                } else if (obj instanceof LimasTrapesium l) {
                    l.hitungLuas(); l.hitungKeliling();
                    l.hitungVolume(); l.hitungLuasPermukaan();
                }
            });
        }
        pool.shutdown();
        try { pool.awaitTermination(60, TimeUnit.SECONDS); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  HELPER UI
    // ══════════════════════════════════════════════════════════════════════════

    /** Format angka 2 desimal */
    private String fmt(double v) { return String.format("%.2f", v); }

    /** Set teks status bar + progress indeterminate */
    private void setStatus(String msg, boolean busy) {
        SwingUtilities.invokeLater(() -> {
            lblStatus.setText(msg);
            progressBar.setIndeterminate(busy);
        });
    }

    /** Reset semua output */
    private void resetUI() {
        modelPrisma.setRowCount(0);
        modelLimas.setRowCount(0);
        lblTimePrisma.setText("— ms");
        lblTimeLimas.setText("— ms");
        lblTimeTotal.setText("— ms");
        setStatus("Direset. Siap untuk perhitungan baru.", false);
    }

    /** Spinner bergaya dark */
    private JSpinner styledSpinner(int val, int min, int max, int step) {
        JSpinner sp = new JSpinner(new SpinnerNumberModel(val, min, max, step));
        sp.setFont(FONT_INPUT);
        sp.setBackground(BG_INPUT);
        sp.setForeground(ACCENT_GOLD);
        JComponent editor = sp.getEditor();
        editor.setBackground(BG_INPUT);
        if (editor instanceof JSpinner.DefaultEditor de) {
            de.getTextField().setBackground(BG_INPUT);
            de.getTextField().setForeground(ACCENT_DARK);
            de.getTextField().setCaretColor(ACCENT_BLUE);
            de.getTextField().setBorder(new EmptyBorder(6, 10, 6, 10));
            de.getTextField().setFont(FONT_INPUT);
        }
        sp.setBorder(BorderFactory.createLineBorder(ACCENT_BLUE, 2));
        sp.setAlignmentX(Component.LEFT_ALIGNMENT);
        sp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        return sp;
    }

    /** Label input dengan tooltip kecil */
    private JPanel inputLabel(String label, String hint) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(ACCENT_GOLD);

        JLabel hint2 = new JLabel(hint);
        hint2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        hint2.setForeground(ACCENT_BLUE);

        p.add(lbl);
        p.add(hint2);
        return p;
    }

    /** Judul seksi */
    private JLabel sectionTitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 18));
        l.setForeground(ACCENT_GOLD);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }
    
    private void addInfoRow(JPanel parent, String key, String val) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        JLabel k = new JLabel(key);
        k.setFont(new Font("SansSerif", Font.PLAIN, 13));
        k.setForeground(ACCENT_BLUE);
        JLabel v = new JLabel(val);
        v.setFont(new Font("Monospaced", Font.PLAIN, 13));
        v.setForeground(ACCENT_GOLD);
        row.add(k, BorderLayout.WEST);
        row.add(v, BorderLayout.EAST);
        parent.add(row);
        parent.add(Box.createVerticalStrut(6));
    }

    /** Kartu OOP concepts */
    private JPanel oopCard() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(30, 50, 110));
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_GOLD, 2, true),
                new EmptyBorder(14, 18, 14, 18)));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("Konsep OOP yang digunakan");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        title.setForeground(ACCENT_GOLD);
        p.add(title);
        p.add(Box.createVerticalStrut(10));

        for (String s : new String[]{
                "✔  Abstraksi  (Trapesium abstract)",
                "✔  Enkapsulasi  (private + getter/setter)",
                "✔  Pewarisan  (extends Trapesium)",
                "✔  Polimorfisme  (override hitungVolume)",
                "✔  Overloading  (konstruktor ganda)",
                "✔  Interface  (Geometri2D, Geometri3D)",
                "✔  Multithreading  (Thread + ExecutorService)"}) {
            JLabel l = new JLabel(s);
            l.setFont(new Font("Monospaced", Font.PLAIN, 12));
            l.setForeground(ACCENT_BLUE);
            p.add(l);
            p.add(Box.createVerticalStrut(4));
        }
        return p;
    }

    /** Tombol bergaya */
    private JButton buildButton(String text, Color bg) {
        JButton b = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                Color c = getModel().isPressed()  ? bg.darker()
                        : getModel().isRollover() ? bg.brighter()
                        : bg;
                g2.setColor(c);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(bg.brighter());
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                super.paintComponent(g);
            }
        };
        b.setFont(FONT_BTN);
        b.setForeground(BG_DARK);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(false);
        return b;
    }

    /** Kartu statistik waktu */
    private JPanel statCard(String label, JLabel valueLabel, Color accent) {
        JPanel p = new JPanel(new GridLayout(3, 1, 0, 4));
        p.setBackground(new Color(30, 50, 110));
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accent, 2, true),
                new EmptyBorder(14, 20, 14, 20)));

        JLabel lbl = new JLabel(label, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        lbl.setForeground(ACCENT_BLUE);

        valueLabel.setFont(FONT_STAT);
        valueLabel.setForeground(accent);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel sub = new JLabel("waktu eksekusi", SwingConstants.CENTER);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sub.setForeground(ACCENT_BLUE);

        p.add(lbl);
        p.add(valueLabel);
        p.add(sub);
        return p;
    }

    /** Buat model tabel dengan kolom tertentu */
    private DefaultTableModel buildTableModel(String[] cols) {
        return new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
    }

    /** Buat JTable bergaya dark */
    private JTable buildTable(DefaultTableModel model) {
        JTable t = new JTable(model);
        t.setFont(FONT_TABLE);
        t.setForeground(ACCENT_GOLD);
        t.setBackground(BG_DARK);
        t.setGridColor(ACCENT_BLUE);
        t.setRowHeight(30);
        t.setSelectionBackground(new Color(208, 230, 253, 100));
        t.setSelectionForeground(ACCENT_GOLD);
        t.setShowVerticalLines(true);
        t.setShowHorizontalLines(true);
        t.setAutoCreateRowSorter(true); // klik header untuk sort

        // header
        JTableHeader header = t.getTableHeader();
        header.setFont(FONT_HEADER);
        header.setBackground(HEADER_TBL);
        header.setForeground(BG_DARK);
        header.setReorderingAllowed(false);

        // renderer warna baris selang-seling
        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? BG_DARK : ROW_ALT);
                    setForeground(col == 0 ? ACCENT_BLUE : ACCENT_GOLD);
                }
                setFont(col == 0 ? new Font("Monospaced", Font.BOLD, 14) : FONT_TABLE);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                setHorizontalAlignment(col == 0 ? CENTER : RIGHT);
                return this;
            }
        });

        // lebar kolom
        int[] widths = {45, 70, 70, 70, 70, 70, 90, 100, 90, 110, 120};
        for (int i = 0; i < Math.min(widths.length, t.getColumnCount()); i++)
            t.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        return t;
    }

    /** Bungkus tabel dalam scroll pane bergaya */
    private JScrollPane wrapTable(JTable t) {
        JScrollPane sp = new JScrollPane(t);
        sp.setBackground(BG_DARK);
        sp.getViewport().setBackground(BG_DARK);
        sp.setBorder(BorderFactory.createLineBorder(ACCENT_BLUE, 2));
        sp.getVerticalScrollBar().setBackground(new Color(30, 50, 110));
        sp.getHorizontalScrollBar().setBackground(new Color(30, 50, 110));
        return sp;
    }

    /** Gaya tab pane */
    private void styleTabPane(JTabbedPane tp) {
        tp.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override protected void installDefaults() {
                super.installDefaults();
                highlight = BG_DARK;
                lightHighlight = BG_DARK;
                shadow = BG_DARK;
                darkShadow = BG_DARK;
                focus = BG_DARK;
            }
        });
        tp.setBackground(BG_DARK);
        tp.setForeground(ACCENT_GOLD);
        tp.setBorder(null);
        tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MAIN
    // ══════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        // Gunakan FlatLaf atau system L&F untuk tampilan modern;
        // fallback ke Nimbus jika tidak ada
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) { }

        SwingUtilities.invokeLater(KalkulatorTrapesiumGUI::new);
    }
}