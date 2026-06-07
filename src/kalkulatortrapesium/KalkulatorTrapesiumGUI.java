package kalkulatortrapesium;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class KalkulatorTrapesiumGUI extends JFrame {

    // ======================== KONSTANTA WARNA ========================
    static final Color BG_DARK     = new Color(22, 38, 96);
    static final Color BG_CARD     = new Color(30, 50, 110);
    static final Color ACCENT_BLUE = new Color(208, 230, 253);
    static final Color ACCENT_GOLD = new Color(241, 228, 209);
    static final Color ROW_ALT     = new Color(35, 55, 120);
    static final Color HEADER_TBL  = new Color(208, 230, 253);

    // ======================== FONT ========================
    static final Font FONT_TITLE  = new Font("Serif",      Font.BOLD,  26);
    static final Font FONT_SUB    = new Font("Monospaced", Font.PLAIN, 13);
    static final Font FONT_LABEL  = new Font("SansSerif",  Font.BOLD,  15);
    static final Font FONT_INPUT  = new Font("Monospaced", Font.PLAIN, 17);
    static final Font FONT_BTN    = new Font("SansSerif",  Font.BOLD,  15);
    static final Font FONT_TABLE  = new Font("Monospaced", Font.PLAIN, 13);
    static final Font FONT_HEADER = new Font("SansSerif",  Font.BOLD,  13);
    static final Font FONT_STAT   = new Font("Monospaced", Font.BOLD,  22);
    static final Font FONT_LOG    = new Font("Monospaced", Font.PLAIN, 12);

    // ======================== DEKLARASI KOMPONEN ========================
    JSpinner          spnData;
    JSpinner          spnThread;
    JButton           btnHitung;
    JButton           btnReset;
    JLabel            lblTimePrisma;
    JLabel            lblTimeLimas;
    JLabel            lblTimeTotal;
    JTable            tblPrisma;
    JTable            tblLimas;
    DefaultTableModel modelPrisma;
    DefaultTableModel modelLimas;
    JTabbedPane       tabs;
    JLabel            lblStatus;
    JProgressBar      progressBar;
    JTextArea         areaLog;

    static final double MIN_SISI = 5.0;
    static final double MAX_SISI = 50.0;

    // ======================== KONSTRUKTOR ========================
    public KalkulatorTrapesiumGUI() {
        setTitle("Kalkulator Trapesium - Menu 2: Data Random + Multithreading");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1400, 820));
        setPreferredSize(new Dimension(1600, 960));
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ======================== HEADER ========================
    JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG_DARK);
        p.setPreferredSize(new Dimension(0, 90));
        p.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 2, 0, ACCENT_BLUE),
                new EmptyBorder(18, 30, 18, 30)));

        JPanel left = new JPanel(new GridLayout(2, 1, 0, 4));
        left.setOpaque(false);
        JLabel title = new JLabel("KALKULATOR TRAPESIUM");
        title.setFont(FONT_TITLE);
        title.setForeground(ACCENT_GOLD);
        JLabel sub = new JLabel("Menu 2 — Data Random · Multithreading · Java Swing GUI");
        sub.setFont(FONT_SUB);
        sub.setForeground(ACCENT_BLUE);
        left.add(title);
        left.add(sub);

        JLabel badge = new JLabel("Kelompok 2");
        badge.setFont(new Font("Monospaced", Font.BOLD, 13));
        badge.setForeground(ACCENT_BLUE);
        badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_BLUE, 2, true),
                new EmptyBorder(6, 14, 6, 14)));

        p.add(left,  BorderLayout.WEST);
        p.add(badge, BorderLayout.EAST);
        return p;
    }

    // ======================== CENTER ========================
    JSplitPane buildCenter() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildLeftPanel(), buildRightPanel());
        split.setDividerLocation(320);
        split.setDividerSize(4);
        split.setBorder(null);
        split.setBackground(BG_DARK);
        return split;
    }

    // ── Panel Kiri: input + log thread ──
    JPanel buildLeftPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_DARK);
        p.setBorder(new EmptyBorder(22, 22, 22, 22));

        p.add(sectionTitle("⚙  KONFIGURASI"));
        p.add(Box.createVerticalStrut(16));

        p.add(inputLabel("Jumlah Data", "Banyak trapesium yang digenerate acak"));
        p.add(Box.createVerticalStrut(6));
        spnData = styledSpinner(100, 1, 100000, 50);
        p.add(spnData);
        p.add(Box.createVerticalStrut(16));

        p.add(inputLabel("Jumlah Thread", "Ukuran thread pool (ExecutorService)"));
        p.add(Box.createVerticalStrut(6));
        spnThread = styledSpinner(4, 1, 64, 1);
        p.add(spnThread);
        p.add(Box.createVerticalStrut(22));

        btnHitung = buildButton("▶  HITUNG", ACCENT_BLUE);
        btnHitung.addActionListener(e -> jalankanPerhitungan());
        btnHitung.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnHitung.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        p.add(btnHitung);
        p.add(Box.createVerticalStrut(10));

        btnReset = buildButton("↺  RESET", ACCENT_GOLD);
        btnReset.addActionListener(e -> resetUI());
        btnReset.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnReset.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        p.add(btnReset);
        p.add(Box.createVerticalStrut(22));

        // ── Log thread ──
        p.add(sectionTitle("🧵  LOG THREAD"));
        p.add(Box.createVerticalStrut(8));

        areaLog = new JTextArea();
        areaLog.setFont(FONT_LOG);
        areaLog.setBackground(new Color(10, 18, 50));
        areaLog.setForeground(new Color(100, 220, 130));
        areaLog.setEditable(false);
        areaLog.setLineWrap(true);
        areaLog.setWrapStyleWord(true);
        areaLog.setText("[ Log thread akan muncul di sini... ]\n");

        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setPreferredSize(new Dimension(0, 260));
        scrollLog.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));
        scrollLog.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollLog.setBorder(BorderFactory.createLineBorder(ACCENT_BLUE, 1));
        scrollLog.getViewport().setBackground(new Color(10, 18, 50));
        p.add(scrollLog);

        return p;
    }

    // ── Panel Kanan: statistik waktu + tabel ──
    JPanel buildRightPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG_DARK);

        p.add(buildStatRow(), BorderLayout.NORTH);

        tabs = new JTabbedPane();
        tabs.setFont(FONT_LABEL);
        tabs.setBackground(BG_DARK);
        tabs.setForeground(ACCENT_GOLD);

        modelPrisma = buildTableModel(new String[]{
            "No","Atas","Bawah","Tinggi","Kiri","Kanan","TinggiPrisma",
            "Luas Alas","Keliling","Volume","Luas Permukaan"});
        tblPrisma = buildTable(modelPrisma);
        tabs.addTab("  ▦  Prisma Trapesium  ", wrapTable(tblPrisma));

        modelLimas = buildTableModel(new String[]{
            "No","Atas","Bawah","Tinggi","Kiri","Kanan","TinggiLimas",
            "Luas Alas","Keliling","Volume","Luas Permukaan"});
        tblLimas = buildTable(modelLimas);
        tabs.addTab("  △  Limas Trapesium  ", wrapTable(tblLimas));

        p.add(tabs, BorderLayout.CENTER);
        return p;
    }

    // ── Baris statistik waktu ──
    JPanel buildStatRow() {
        JPanel row = new JPanel(new GridLayout(1, 3, 12, 0));
        row.setBackground(BG_DARK);
        row.setBorder(new EmptyBorder(14, 18, 14, 18));

        lblTimePrisma = new JLabel("— ms", SwingConstants.CENTER);
        lblTimeLimas  = new JLabel("— ms", SwingConstants.CENTER);
        lblTimeTotal  = new JLabel("— ms", SwingConstants.CENTER);

        row.add(statCard("PRISMA",  lblTimePrisma, ACCENT_BLUE));
        row.add(statCard("LIMAS",   lblTimeLimas,  ACCENT_BLUE));
        row.add(statCard("TOTAL",   lblTimeTotal,  ACCENT_GOLD));
        return row;
    }

    // ======================== FOOTER ========================
    JPanel buildFooter() {
        JPanel p = new JPanel(new BorderLayout(15, 0));
        p.setBackground(BG_DARK);
        p.setBorder(new CompoundBorder(
                new MatteBorder(2, 0, 0, 0, ACCENT_BLUE),
                new EmptyBorder(10, 22, 10, 22)));

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(false);
        progressBar.setPreferredSize(new Dimension(220, 13));
        progressBar.setBackground(new Color(50, 70, 130));
        progressBar.setForeground(ACCENT_GOLD);
        progressBar.setBorderPainted(false);

        lblStatus = new JLabel("Siap. Masukkan parameter lalu klik Hitung.");
        lblStatus.setFont(FONT_SUB);
        lblStatus.setForeground(ACCENT_BLUE);

        p.add(lblStatus,   BorderLayout.WEST);
        p.add(progressBar, BorderLayout.EAST);
        return p;
    }

    // ======================== LOGIKA PERHITUNGAN ========================
    void jalankanPerhitungan() {
        int jumlahData   = (int) spnData.getValue();
        int jumlahThread = (int) spnThread.getValue();

        modelPrisma.setRowCount(0);
        modelLimas.setRowCount(0);
        areaLog.setText("");
        setStatus("Membuat " + jumlahData + " data random...", true);
        btnHitung.setEnabled(false);

        SwingWorker<Void, String> worker = new SwingWorker<>() {
            List<PrismaTrapesium> listPrisma;
            List<LimasTrapesium>  listLimas;
            long durasiPrisma;
            long durasiLimas;

            @Override
            protected Void doInBackground() {
                publish("Membuat data random...");
                listPrisma = buatDataPrisma(jumlahData);
                listLimas  = buatDataLimas(jumlahData);

                // ── Hitung Prisma ──
                publish("Menghitung Prisma dengan " + jumlahThread + " thread...");
                long t0 = System.currentTimeMillis();
                hitungDenganThread(listPrisma, jumlahThread, "Prisma");
                durasiPrisma = System.currentTimeMillis() - t0;

                // ── Hitung Limas ──
                publish("Menghitung Limas dengan " + jumlahThread + " thread...");
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
                lblTimePrisma.setText(durasiPrisma + " ms");
                lblTimeLimas.setText(durasiLimas   + " ms");
                lblTimeTotal.setText((durasiPrisma + durasiLimas) + " ms");

                for (int i = 0; i < listPrisma.size(); i++) {
                    PrismaTrapesium p = listPrisma.get(i);
                    modelPrisma.addRow(new Object[]{
                        i + 1,
                        fmt(p.atas), fmt(p.bawah), fmt(p.tinggi),
                        fmt(p.kiri), fmt(p.kanan), fmt(p.tinggiPrisma),
                        fmt(p.luas), fmt(p.keliling),
                        fmt(p.volume), fmt(p.luasPermukaan)
                    });
                }

                for (int i = 0; i < listLimas.size(); i++) {
                    LimasTrapesium l = listLimas.get(i);
                    modelLimas.addRow(new Object[]{
                        i + 1,
                        fmt(l.atas), fmt(l.bawah), fmt(l.tinggi),
                        fmt(l.kiri), fmt(l.kanan), fmt(l.tinggiLimas),
                        fmt(l.luas), fmt(l.keliling),
                        fmt(l.volume), fmt(l.luasPermukaan)
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

    // ======================== GENERATE DATA ========================
    List<PrismaTrapesium> buatDataPrisma(int jumlah) {
        Random rng = new Random();
        List<PrismaTrapesium> list = new ArrayList<>();
        for (int i = 0; i < jumlah; i++) {
            double atas    = acak(rng);
            double bawah   = acak(rng);
            double tinggi  = acak(rng);
            double kiri    = acak(rng);
            double kanan   = acak(rng);
            double panjang = acak(rng);
            list.add(new PrismaTrapesium(atas, bawah, tinggi, kiri, kanan, panjang));
        }
        return list;
    }

    List<LimasTrapesium> buatDataLimas(int jumlah) {
        Random rng = new Random();
        List<LimasTrapesium> list = new ArrayList<>();
        for (int i = 0; i < jumlah; i++) {
            double atas        = acak(rng);
            double bawah       = acak(rng);
            double tinggi      = acak(rng);
            double kiri        = acak(rng);
            double kanan       = acak(rng);
            double tinggiLimas = acak(rng);
            list.add(new LimasTrapesium(atas, bawah, tinggi, kiri, kanan, tinggiLimas));
        }
        return list;
    }

    static double acak(Random rng) {
        return MIN_SISI + (rng.nextDouble() * (MAX_SISI - MIN_SISI));
    }

    // ======================== MULTITHREADING ========================
    void hitungDenganThread(List<? extends Trapesium> list,
                            int jumlahThread, String jenis) {
        ExecutorService pool = Executors.newFixedThreadPool(jumlahThread);
        AtomicInteger selesai = new AtomicInteger(0);
        int total = list.size();

        for (Trapesium obj : list) {
            pool.submit(() -> {
                String namaThread = Thread.currentThread().getName();

                // Jalankan run() dari masing-masing objek
                obj.run();

                // Catat ke log
                int n = selesai.incrementAndGet();
                String logLine = "[" + namaThread + "] " + jenis
                        + " #" + n + "/" + total + " selesai\n";
                SwingUtilities.invokeLater(() -> {
                    areaLog.append(logLine);
                    areaLog.setCaretPosition(areaLog.getDocument().getLength());
                });
            });
        }

        pool.shutdown();
        try {
            pool.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ======================== HELPER UI ========================
    String fmt(double v) {
        return String.format("%.2f", v);
    }

    void setStatus(String msg, boolean busy) {
        SwingUtilities.invokeLater(() -> {
            lblStatus.setText(msg);
            progressBar.setIndeterminate(busy);
        });
    }

    void resetUI() {
        modelPrisma.setRowCount(0);
        modelLimas.setRowCount(0);
        lblTimePrisma.setText("— ms");
        lblTimeLimas.setText("— ms");
        lblTimeTotal.setText("— ms");
        areaLog.setText("[ Log thread akan muncul di sini... ]\n");
        setStatus("Direset. Siap untuk perhitungan baru.", false);
    }

    JSpinner styledSpinner(int val, int min, int max, int step) {
        JSpinner sp = new JSpinner(new SpinnerNumberModel(val, min, max, step));
        sp.setFont(FONT_INPUT);
        sp.setBackground(BG_DARK);
        JComponent editor = sp.getEditor();
        editor.setBackground(BG_DARK);
        if (editor instanceof JSpinner.DefaultEditor de) {
            de.getTextField().setBackground(new Color(10, 20, 60));
            de.getTextField().setForeground(ACCENT_GOLD);
            de.getTextField().setCaretColor(ACCENT_BLUE);
            de.getTextField().setBorder(new EmptyBorder(6, 10, 6, 10));
            de.getTextField().setFont(FONT_INPUT);
        }
        sp.setBorder(BorderFactory.createLineBorder(ACCENT_BLUE, 2));
        sp.setAlignmentX(Component.LEFT_ALIGNMENT);
        sp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        return sp;
    }

    JPanel inputLabel(String label, String hint) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(ACCENT_GOLD);
        JLabel h = new JLabel(hint);
        h.setFont(new Font("SansSerif", Font.PLAIN, 12));
        h.setForeground(ACCENT_BLUE);
        p.add(lbl);
        p.add(h);
        return p;
    }

    JLabel sectionTitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 16));
        l.setForeground(ACCENT_GOLD);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    JButton buildButton(String text, Color bg) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                Color c = getModel().isPressed()  ? bg.darker()
                        : getModel().isRollover() ? bg.brighter()
                        : bg;
                g2.setColor(c);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
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

    JPanel statCard(String label, JLabel valueLabel, Color accent) {
        JPanel p = new JPanel(new GridLayout(3, 1, 0, 4));
        p.setBackground(BG_CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accent, 2, true),
                new EmptyBorder(12, 18, 12, 18)));
        JLabel lbl = new JLabel(label, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(ACCENT_BLUE);
        valueLabel.setFont(FONT_STAT);
        valueLabel.setForeground(accent);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel sub = new JLabel("waktu eksekusi", SwingConstants.CENTER);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(ACCENT_BLUE);
        p.add(lbl);
        p.add(valueLabel);
        p.add(sub);
        return p;
    }

    DefaultTableModel buildTableModel(String[] cols) {
        return new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
    }

    JTable buildTable(DefaultTableModel model) {
        JTable t = new JTable(model);
        t.setFont(FONT_TABLE);
        t.setForeground(ACCENT_GOLD);
        t.setBackground(BG_DARK);
        t.setGridColor(ACCENT_BLUE);
        t.setRowHeight(28);
        t.setSelectionBackground(new Color(208, 230, 253, 100));
        t.setSelectionForeground(ACCENT_GOLD);
        t.setShowVerticalLines(true);
        t.setShowHorizontalLines(true);
        t.setAutoCreateRowSorter(true);

        JTableHeader header = t.getTableHeader();
        header.setFont(FONT_HEADER);
        header.setBackground(HEADER_TBL);
        header.setForeground(BG_DARK);
        header.setReorderingAllowed(false);

        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? BG_DARK : ROW_ALT);
                    setForeground(col == 0 ? ACCENT_BLUE : ACCENT_GOLD);
                }
                setFont(col == 0 ? new Font("Monospaced", Font.BOLD, 13) : FONT_TABLE);
                setBorder(new EmptyBorder(0, 8, 0, 8));
                setHorizontalAlignment(col == 0 ? CENTER : RIGHT);
                return this;
            }
        });

        int[] widths = {40, 65, 65, 65, 65, 65, 90, 95, 85, 100, 115};
        for (int i = 0; i < Math.min(widths.length, t.getColumnCount()); i++)
            t.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        return t;
    }

    JScrollPane wrapTable(JTable t) {
        JScrollPane sp = new JScrollPane(t);
        sp.setBackground(BG_DARK);
        sp.getViewport().setBackground(BG_DARK);
        sp.setBorder(BorderFactory.createLineBorder(ACCENT_BLUE, 2));
        return sp;
    }

    // ======================== ENTRY POINT ========================
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(KalkulatorTrapesiumGUI::new);
    }
}