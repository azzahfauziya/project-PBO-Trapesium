package kalkulatortrapesium;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Kelas utama program Kalkulator Trapesium
 *
 * Alur program:
 * 1. User memasukkan jumlah data dan jumlah thread
 * 2. Sistem menghasilkan data random (sisi-sisi trapesium)
 * 3. Sistem menghitung dengan multithreading (ExecutorService)
 * 4. Sistem menampilkan waktu eksekusi dan hasil dalam bentuk tabel
 */
public class main {

    // ======================== KONSTANTA ========================
    static final double MIN_SISI = 5.0;   // nilai minimum sisi random
    static final double MAX_SISI = 50.0;  // nilai maksimum sisi random

    // ======================== MAIN ========================

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("============================================");
        System.out.println("   KALKULATOR TRAPESIUM - MULTITHREADING   ");
        System.out.println("============================================");

        // --- Input jumlah data ---
        System.out.print("Masukkan jumlah data yang ingin diolah : ");
        int jumlahData = scanner.nextInt();

        // --- Input jumlah thread ---
        System.out.print("Masukkan jumlah thread yang ingin digunakan: ");
        int jumlahThread = scanner.nextInt();

        System.out.println("\nMembuat " + jumlahData + " data random...\n");

        // --- Generate data random ---
        List<PrismaTrapesium> listPrisma = buatDataPrisma(jumlahData);
        List<LimasTrapesium>  listLimas  = buatDataLimas(jumlahData);

        // ========================================================
        // HITUNG PRISMA TRAPESIUM dengan multithreading
        // ========================================================
        System.out.println(">>> Menghitung PRISMA TRAPESIUM dengan " + jumlahThread + " thread...");
        long waktuMulaiPrisma = System.currentTimeMillis(); // catat waktu mulai

        hitungDenganThread(listPrisma, jumlahThread, "Prisma");

        long waktuSelesaiPrisma = System.currentTimeMillis(); // catat waktu selesai
        long durasiPrisma = waktuSelesaiPrisma - waktuMulaiPrisma; // hitung durasi

        // ========================================================
        // HITUNG LIMAS TRAPESIUM dengan multithreading
        // ========================================================
        System.out.println(">>> Menghitung LIMAS TRAPESIUM dengan " + jumlahThread + " thread...");
        long waktuMulaiLimas = System.currentTimeMillis(); // catat waktu mulai

        hitungDenganThread(listLimas, jumlahThread, "Limas");

        long waktuSelesaiLimas = System.currentTimeMillis(); // catat waktu selesai
        long durasiLimas = waktuSelesaiLimas - waktuMulaiLimas; // hitung durasi

        // ========================================================
        // TAMPILKAN HASIL
        // ========================================================
        System.out.println("\n========== WAKTU EKSEKUSI ==========");
        System.out.println("Prisma Trapesium : " + durasiPrisma + " ms");
        System.out.println("Limas Trapesium  : " + durasiLimas  + " ms");
        System.out.println("Total            : " + (durasiPrisma + durasiLimas) + " ms");

        tampilkanTabelPrisma(listPrisma);
        tampilkanTabelLimas(listLimas);

        scanner.close();
    }

    // ======================== GENERATE DATA RANDOM ========================

    /**
     * Membuat daftar PrismaTrapesium dengan nilai sisi random
     * Semua sisi dihasilkan secara acak dalam rentang MIN_SISI hingga MAX_SISI
     */
    static List<PrismaTrapesium> buatDataPrisma(int jumlah) {
        Random rng = new Random();
        List<PrismaTrapesium> list = new ArrayList<>();

        for (int i = 0; i < jumlah; i++) {
            double atas    = acak(rng); // sisi atas trapesium (random)
            double bawah   = acak(rng); // sisi bawah trapesium (random), pastikan >= atas
            double tinggi  = acak(rng); // tinggi trapesium (random)
            double kiri    = acak(rng); // sisi kiri trapesium (random)
            double kanan   = acak(rng); // sisi kanan trapesium (random)
            double panjang = acak(rng); // panjang prisma (random)

            // buat objek PrismaTrapesium dan tambahkan ke list
            list.add(new PrismaTrapesium(atas, bawah, tinggi, kiri, kanan, panjang));
        }

        return list;
    }

    /**
     * Membuat daftar LimasTrapesium dengan nilai sisi random
     */
    static List<LimasTrapesium> buatDataLimas(int jumlah) {
        Random rng = new Random();
        List<LimasTrapesium> list = new ArrayList<>();

        for (int i = 0; i < jumlah; i++) {
            double atas        = acak(rng); // sisi atas trapesium alas (random)
            double bawah       = acak(rng); // sisi bawah trapesium alas (random)
            double tinggi      = acak(rng); // tinggi trapesium alas (random)
            double kiri        = acak(rng); // sisi kiri trapesium alas (random)
            double kanan       = acak(rng); // sisi kanan trapesium alas (random)
            double tinggiLimas = acak(rng); // tinggi limas dari alas ke puncak (random)

            // buat objek LimasTrapesium dan tambahkan ke list
            list.add(new LimasTrapesium(atas, bawah, tinggi, kiri, kanan, tinggiLimas));
        }

        return list;
    }

    /**
     * Menghasilkan nilai acak antara MIN_SISI dan MAX_SISI
     */
    static double acak(Random rng) {
        // nextDouble() menghasilkan 0.0 hingga 1.0, dikali rentang lalu ditambah minimum
        return MIN_SISI + (rng.nextDouble() * (MAX_SISI - MIN_SISI));
    }

    // ======================== MULTITHREADING (ExecutorService) ========================

    /**
     * Menghitung semua data dalam list menggunakan ExecutorService (thread pool)
     * Setiap objek dihitung di thread yang dialokasikan oleh pool
     *
     * @param list         daftar objek yang akan dihitung
     * @param jumlahThread ukuran thread pool
     * @param jenis        label jenis ("Prisma" atau "Limas") untuk log
     */
    static void hitungDenganThread(List<? extends Trapesium> list,
                                   int jumlahThread, String jenis) {
        // Buat thread pool dengan ukuran sesuai input user
        ExecutorService pool = Executors.newFixedThreadPool(jumlahThread);

        for (Trapesium obj : list) {
            // Setiap objek diproses dalam task terpisah yang dilempar ke pool
            pool.submit(() -> {
                if (obj instanceof PrismaTrapesium) {
                    PrismaTrapesium p = (PrismaTrapesium) obj;
                    p.hitungLuas();           // hitung luas alas
                    p.hitungKeliling();       // hitung keliling alas
                    p.hitungVolume();         // hitung volume prisma
                    p.hitungLuasPermukaan();  // hitung luas permukaan prisma

                } else if (obj instanceof LimasTrapesium) {
                    LimasTrapesium l = (LimasTrapesium) obj;
                    l.hitungLuas();           // hitung luas alas
                    l.hitungKeliling();       // hitung keliling alas
                    l.hitungVolume();         // hitung volume limas
                    l.hitungLuasPermukaan();  // hitung luas permukaan limas
                }
            });
        }

        pool.shutdown(); // hentikan penerimaan task baru

        try {
            // Tunggu semua thread selesai, maksimal 60 detik
            boolean selesai = pool.awaitTermination(60, TimeUnit.SECONDS);
            if (!selesai) {
                System.out.println("Peringatan: Ada thread " + jenis + " yang belum selesai!");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread pool " + jenis + " terganggu: " + e.getMessage());
        }
    }

    // ======================== TAMPILKAN TABEL ========================

    /**
     * Menampilkan hasil perhitungan Prisma Trapesium dalam bentuk tabel
     */
    static void tampilkanTabelPrisma(List<PrismaTrapesium> list) {
        System.out.println("\n");
        System.out.println("===========================================================================================================================================");
        System.out.println("                                               HASIL PERHITUNGAN - PRISMA TRAPESIUM                                                      ");
        System.out.println("===========================================================================================================================================");
        System.out.printf("%-5s | %-7s | %-7s | %-7s | %-7s | %-7s | %-8s | %-12s | %-12s | %-14s | %-14s%n",
                "No", "Atas", "Bawah", "Tinggi", "Kiri", "Kanan", "Panjang",
                "Luas Alas", "Keliling", "Volume", "Luas Permukaan");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < list.size(); i++) {
            PrismaTrapesium p = list.get(i);
            System.out.printf("%-5d | %-7.2f | %-7.2f | %-7.2f | %-7.2f | %-7.2f | %-8.2f | %-12.2f | %-12.2f | %-14.2f | %-14.2f%n",
                    (i + 1),
                    p.getAtas(), p.getBawah(), p.getTinggi(), p.getKiri(), p.getKanan(),
                    p.getPanjang(),
                    p.getHasilLuas(),
                    p.getHasilKeliling(),
                    p.getHasilVolume(),
                    p.getHasilLuasPermukaan());
        }

        System.out.println("===========================================================================================================================================");
    }

    /**
     * Menampilkan hasil perhitungan Limas Trapesium dalam bentuk tabel
     */
    static void tampilkanTabelLimas(List<LimasTrapesium> list) {
        System.out.println("\n");
        System.out.println("===========================================================================================================================================");
        System.out.println("                                                HASIL PERHITUNGAN - LIMAS TRAPESIUM                                                       ");
        System.out.println("===========================================================================================================================================");
        System.out.printf("%-5s | %-7s | %-7s | %-7s | %-7s | %-7s | %-11s | %-12s | %-12s | %-14s | %-14s%n",
                "No", "Atas", "Bawah", "Tinggi", "Kiri", "Kanan", "TinggiLimas",
                "Luas Alas", "Keliling", "Volume", "Luas Permukaan");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < list.size(); i++) {
            LimasTrapesium l = list.get(i);
            System.out.printf("%-5d | %-7.2f | %-7.2f | %-7.2f | %-7.2f | %-7.2f | %-11.2f | %-12.2f | %-12.2f | %-14.2f | %-14.2f%n",
                    (i + 1),
                    l.getAtas(), l.getBawah(), l.getTinggi(), l.getKiri(), l.getKanan(),
                    l.getTinggiLimas(),
                    l.getHasilLuas(),
                    l.getHasilKeliling(),
                    l.getHasilVolume(),
                    l.getHasilLuasPermukaan());
        }

        System.out.println("===========================================================================================================================================");
    }
}