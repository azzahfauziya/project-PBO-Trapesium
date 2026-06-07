package kalkulatortrapesium;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║     KALKULATOR TRAPESIUM             ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Input Manual                     ║");
            System.out.println("║  2. Data Random + Multithreading     ║");
            System.out.println("║  0. Keluar                           ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Pilih menu: ");
            String pilih = sc.nextLine().trim();

            switch (pilih) {
                case "1" -> menu1();
                case "2" -> menu2();
                case "0" -> { System.out.println("Sampai jumpa!"); return; }
                default  -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  MENU 1 — INPUT MANUAL
    // ═══════════════════════════════════════════════════════════════
    static void menu1() {
        while (true) {
            System.out.println("\n┌──────────────────────────────────────┐");
            System.out.println("│  MENU 1 — Pilih Jenis Bangun         │");
            System.out.println("├──────────────────────────────────────┤");
            System.out.println("│  1. Trapesium 2D                     │");
            System.out.println("│  2. Prisma Trapesium                 │");
            System.out.println("│  3. Limas Trapesium                  │");
            System.out.println("│  0. Kembali                          │");
            System.out.println("└──────────────────────────────────────┘");
            System.out.print("Pilih: ");
            String p = sc.nextLine().trim();

            switch (p) {
                case "1" -> hitungTrapesium2D();
                case "2" -> hitungPrisma();
                case "3" -> hitungLimas();
                case "0" -> { return; }
                default  -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // ── Trapesium 2D ──────────────────────────────────────────────
    static void hitungTrapesium2D() {
        System.out.println("\n=== INPUT TRAPESIUM 2D ===");
        double atas   = inputDouble("Sisi atas   : ");
        double bawah  = inputDouble("Sisi bawah  : ");
        double tinggi = inputDouble("Tinggi      : ");
        double kiri   = inputDouble("Sisi kiri   : ");
        double kanan  = inputDouble("Sisi kanan  : ");

        Trapesium t = new Trapesium(atas, bawah, tinggi, kiri, kanan);
        double luas     = t.hitungLuas();
        double keliling = t.hitungKeliling();

        System.out.println("\n┌──────────────────────────────────────────┐");
        System.out.println("│          HASIL — TRAPESIUM 2D           │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.printf("│  Luas      = ½ × (%.2f + %.2f) × %.2f%n", atas, bawah, tinggi);
        System.out.printf("│            = %.2f%n", luas);
        System.out.printf("│  Keliling  = %.2f + %.2f + %.2f + %.2f%n", atas, bawah, kiri, kanan);
        System.out.printf("│            = %.2f%n", keliling);
        System.out.println("└──────────────────────────────────────────┘");
    }

    // ── Prisma Trapesium ──────────────────────────────────────────
    static void hitungPrisma() {
        System.out.println("\n=== INPUT PRISMA TRAPESIUM ===");
        System.out.println("-- Sisi-sisi alas trapesium --");
        double atas   = inputDouble("Sisi atas     : ");
        double bawah  = inputDouble("Sisi bawah    : ");
        double tinggi = inputDouble("Tinggi alas   : ");
        double kiri   = inputDouble("Sisi kiri     : ");
        double kanan  = inputDouble("Sisi kanan    : ");
        System.out.println("-- Dimensi prisma --");
        double panjang = inputDouble("Panjang prisma: ");

        PrismaTrapesium pr = new PrismaTrapesium(atas, bawah, tinggi, kiri, kanan, panjang);
        double luas     = pr.hitungLuas(atas, bawah, tinggi);
        double keliling = pr.hitungKeliling(atas, bawah, kiri, kanan);
        double volume   = pr.hitungVolume(atas, bawah, tinggi);
        double luasP    = pr.hitungLuasPermukaan(atas, bawah, kanan, kiri, tinggi);

        System.out.println("\n┌──────────────────────────────────────────────────┐");
        System.out.println("│            HASIL — PRISMA TRAPESIUM             │");
        System.out.println("├──────────────────────────────────────────────────┤");
        System.out.printf("│  Luas Alas       = ½ × (%.2f+%.2f) × %.2f = %.2f%n", atas, bawah, tinggi, luas);
        System.out.printf("│  Keliling Alas   = %.2f%n", keliling);
        System.out.printf("│  Volume          = %.2f × %.2f = %.2f%n", luas, panjang, volume);
        System.out.printf("│  Luas Permukaan  = (2×%.2f) + (%.2f+%.2f+%.2f+%.2f)×%.2f%n",
                luas, atas, bawah, kiri, kanan, panjang);
        System.out.printf("│                  = %.2f%n", luasP);
        System.out.println("└──────────────────────────────────────────────────┘");
    }

    // ── Limas Trapesium ───────────────────────────────────────────
    static void hitungLimas() {
        System.out.println("\n=== INPUT LIMAS TRAPESIUM ===");
        System.out.println("-- Sisi-sisi alas trapesium --");
        double atas   = inputDouble("Sisi atas      : ");
        double bawah  = inputDouble("Sisi bawah     : ");
        double tinggi = inputDouble("Tinggi alas    : ");
        double kiri   = inputDouble("Sisi kiri      : ");
        double kanan  = inputDouble("Sisi kanan     : ");
        System.out.println("-- Dimensi limas --");
        double tinggiLimas = inputDouble("Tinggi limas   : ");

        LimasTrapesium li = new LimasTrapesium(atas, bawah, tinggi, kiri, kanan, tinggiLimas);
        double luas     = li.hitungLuas(atas, bawah, tinggi);
        double keliling = li.hitungKeliling(atas, bawah, kiri, kanan);
        double volume   = li.hitungVolume(atas, bawah, tinggi);
        double luasP    = li.hitungLuasPermukaan(atas, bawah, tinggi, kiri, kanan);

        double proyAB = (bawah - atas) / 2.0;
        double proyKK = tinggi / 2.0;
        double apAB   = Math.sqrt(tinggiLimas * tinggiLimas + proyAB * proyAB);
        double apKK   = Math.sqrt(tinggiLimas * tinggiLimas + proyKK * proyKK);

        System.out.println("\n┌──────────────────────────────────────────────────────────┐");
        System.out.println("│               HASIL — LIMAS TRAPESIUM                   │");
        System.out.println("├──────────────────────────────────────────────────────────┤");
        System.out.printf("│  Luas Alas        = ½ × (%.2f+%.2f) × %.2f = %.2f%n", atas, bawah, tinggi, luas);
        System.out.printf("│  Keliling Alas    = %.2f%n", keliling);
        System.out.printf("│  Apotema AB       = √(%.2f²+%.2f²) = %.4f%n", tinggiLimas, proyAB, apAB);
        System.out.printf("│  Apotema KK       = √(%.2f²+%.2f²) = %.4f%n", tinggiLimas, proyKK, apKK);
        System.out.printf("│  Volume           = ⅓ × %.2f × %.2f = %.2f%n", luas, tinggiLimas, volume);
        System.out.printf("│  Luas Permukaan   = %.2f%n", luasP);
        System.out.println("└──────────────────────────────────────────────────────────┘");
    }

    // ═══════════════════════════════════════════════════════════════
    //  MENU 2 — RANDOM + MULTITHREADING
    // ═══════════════════════════════════════════════════════════════
    static void menu2() {
        System.out.println("\n=== MENU 2 — DATA RANDOM + MULTITHREADING ===");
        int n = (int) inputDouble("Jumlah data trapesium: ");
        if (n <= 0) { System.out.println("Jumlah harus > 0."); return; }

        Random rng = new Random();

        // Siapkan data
        record JobData(int idx, int type, double atas, double bawah, double tinggi,
                       double kiri, double kanan, double extra) {}

        List<JobData> jobs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int    type   = i % 3;
            double atas   = round(rng.nextDouble() * 8 + 2);
            double bawah  = round(rng.nextDouble() * 8 + atas);
            double tinggi = round(rng.nextDouble() * 6 + 2);
            double kiri   = round(rng.nextDouble() * 5 + 2);
            double kanan  = round(rng.nextDouble() * 5 + 2);
            double extra  = round(rng.nextDouble() * 8 + 3);
            jobs.add(new JobData(i + 1, type, atas, bawah, tinggi, kiri, kanan, extra));
        }

        // Hasil tersinkronisasi
        record Hasil(int idx, String jenis, double atas, double bawah, double tinggi,
                     double kiri, double kanan, double extra,
                     double luas, double keliling, double volume, double luasP,
                     String threadName, long waktuMs) {}

        List<Hasil> hasilList = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger done    = new AtomicInteger(0);

        int poolSize = Math.min(n, 4);
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);

        System.out.println("\n[INFO] Thread pool aktif dengan " + poolSize + " thread.");
        System.out.println("[INFO] Memulai " + n + " job...\n");

        long globalStart = System.currentTimeMillis();

        List<Future<?>> futures = new ArrayList<>();
        for (JobData job : jobs) {
            futures.add(pool.submit(() -> {
                String tn = Thread.currentThread().getName();
                long t0   = System.currentTimeMillis();

                String jenis;
                double luas = 0, kel = 0, vol = Double.NaN, lp = Double.NaN;

                System.out.printf("[%-20s] ▶ Mulai job #%d (%s)%n",
                        tn, job.idx(),
                        job.type() == 0 ? "Trapesium 2D" : job.type() == 1 ? "Prisma" : "Limas");

                if (job.type() == 0) {
                    jenis = "Trapesium 2D";
                    Trapesium t = new Trapesium(job.atas(), job.bawah(), job.tinggi(), job.kiri(), job.kanan());
                    luas = t.hitungLuas(job.atas(), job.bawah(), job.tinggi());
                    kel  = t.hitungKeliling(job.atas(), job.bawah(), job.kiri(), job.kanan());
                } else if (job.type() == 1) {
                    jenis = "Prisma";
                    PrismaTrapesium pr = new PrismaTrapesium(
                            job.atas(), job.bawah(), job.tinggi(),
                            job.kiri(), job.kanan(), job.extra());
                    luas = pr.hitungLuas(job.atas(), job.bawah(), job.tinggi());
                    kel  = pr.hitungKeliling(job.atas(), job.bawah(), job.kiri(), job.kanan());
                    vol  = pr.hitungVolume(job.atas(), job.bawah(), job.tinggi());
                    lp   = pr.hitungLuasPermukaan(job.atas(), job.bawah(), job.kanan(), job.kiri(), job.tinggi());
                } else {
                    jenis = "Limas";
                    LimasTrapesium li = new LimasTrapesium(
                            job.atas(), job.bawah(), job.tinggi(),
                            job.kiri(), job.kanan(), job.extra());
                    luas = li.hitungLuas(job.atas(), job.bawah(), job.tinggi());
                    kel  = li.hitungKeliling(job.atas(), job.bawah(), job.kiri(), job.kanan());
                    vol  = li.hitungVolume(job.atas(), job.bawah(), job.tinggi());
                    lp   = li.hitungLuasPermukaan(job.atas(), job.bawah(), job.tinggi(), job.kiri(), job.kanan());
                }

                // simulasi kerja thread (opsional, hapus bila tidak mau)
                try { Thread.sleep(rng.nextInt(50) + 10); } catch (InterruptedException ignored) {}

                long elapsed = System.currentTimeMillis() - t0;
                int d = done.incrementAndGet();

                System.out.printf("[%-20s] ✔ Selesai job #%d | %s | Luas=%.2f | Kel=%.2f%s | %d ms | progress %d/%d%n",
                        tn, job.idx(), jenis, luas, kel,
                        Double.isNaN(vol) ? "" : String.format(" | Vol=%.2f | LP=%.2f", vol, lp),
                        elapsed, d, n);

                hasilList.add(new Hasil(job.idx(), jenis,
                        job.atas(), job.bawah(), job.tinggi(), job.kiri(), job.kanan(),
                        job.type() == 0 ? 0 : job.extra(),
                        luas, kel, vol, lp, tn, elapsed));
            }));
        }

        // Tunggu semua selesai
        pool.shutdown();
        try { pool.awaitTermination(1, TimeUnit.MINUTES); } catch (InterruptedException ignored) {}

        long totalWaktu = System.currentTimeMillis() - globalStart;

        // Urutkan hasil berdasarkan idx
        hasilList.sort(Comparator.comparingInt(Hasil::idx));

        // ── Cetak tabel ───────────────────────────────────────────
        System.out.println("\n");
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                          TABEL HASIL PERHITUNGAN                                                              ║");
        System.out.println("╠════╦══════════════╦══════╦══════╦══════╦══════╦══════╦══════╦══════════╦══════════╦══════════╦══════════╦════════════════════╦══════╣");
        System.out.printf( "║ %-2s ║ %-12s ║ %-4s ║ %-4s ║ %-4s ║ %-4s ║ %-4s ║ %-4s ║ %-8s ║ %-8s ║ %-8s ║ %-8s ║ %-18s ║%-5s ║%n",
                "#", "Jenis", "Atas", "Bwh", "Tgi", "Kri", "Knn", "Extr",
                "Luas", "Keliling", "Volume", "LuasP", "Thread", "ms");
        System.out.println("╠════╬══════════════╬══════╬══════╬══════╬══════╬══════╬══════╬══════════╬══════════╬══════════╬══════════╬════════════════════╬══════╣");

        for (var h : hasilList) {
            System.out.printf("║ %-2d ║ %-12s ║ %4.1f ║ %4.1f ║ %4.1f ║ %4.1f ║ %4.1f ║ %4.1f ║ %8.2f ║ %8.2f ║ %8s ║ %8s ║ %-18s ║%5d ║%n",
                    h.idx(), h.jenis(),
                    h.atas(), h.bawah(), h.tinggi(), h.kiri(), h.kanan(), h.extra(),
                    h.luas(), h.keliling(),
                    Double.isNaN(h.volume())  ? "   —    " : String.format("%8.2f", h.volume()),
                    Double.isNaN(h.luasP())   ? "   —    " : String.format("%8.2f", h.luasP()),
                    h.threadName().length() > 18 ? h.threadName().substring(0, 18) : h.threadName(),
                    h.waktuMs());
        }

        System.out.println("╚════╩══════════════╩══════╩══════╩══════╩══════╩══════╩══════╩══════════╩══════════╩══════════╩══════════╩════════════════════╩══════╝");
        System.out.printf("%n  Total job : %d  |  Thread pool : %d  |  Total waktu : %d ms%n", n, poolSize, totalWaktu);

        // ── Ringkasan per thread ──────────────────────────────────
        Map<String, Long>  threadCount = new LinkedHashMap<>();
        Map<String, Long>  threadTotal = new LinkedHashMap<>();
        for (var h : hasilList) {
            threadCount.merge(h.threadName(), 1L, Long::sum);
            threadTotal.merge(h.threadName(), h.waktuMs(), Long::sum);
        }
        System.out.println("\n  Ringkasan Thread:");
        System.out.println("  ┌──────────────────────┬──────────┬────────────┐");
        System.out.println("  │ Thread               │ Job Done │ Total (ms) │");
        System.out.println("  ├──────────────────────┼──────────┼────────────┤");
        threadCount.forEach((t, c) ->
            System.out.printf("  │ %-20s │ %8d │ %10d │%n", t, c, threadTotal.get(t)));
        System.out.println("  └──────────────────────┴──────────┴────────────┘");
    }

    // ── Utilitas ──────────────────────────────────────────────────
    static double inputDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Double.parseDouble(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("  ⚠ Masukkan angka yang valid."); }
        }
    }

    static double round(double v) { return Math.round(v * 10.0) / 10.0; }
}