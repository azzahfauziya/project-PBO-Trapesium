package kalkulatortrapesium;

import java.util.Scanner;

public class main {

    static Scanner scanner = new Scanner(System.in);
    static int pilihan;

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   KALKULATOR TRAPESIUM - MULTITHREADING   ");
        System.out.println("============================================");
        System.out.println("1. Input Manual (Trapesium, Prisma, Limas)");
        System.out.println("2. Data Random + Multithreading (GUI)");
        System.out.print("Pilih menu [1/2]: ");
        pilihan = scanner.nextInt();

        if (pilihan == 1) {
            menuManual();
        } else if (pilihan == 2) {
            javax.swing.SwingUtilities.invokeLater(KalkulatorTrapesiumGUI::new);
        } else {
            System.out.println("Pilihan tidak valid.");
        }

        scanner.close();
    }

    // ======================== MENU 1: INPUT MANUAL ========================

    static void menuManual() {
        int pilihanBangun;

        System.out.println("\n=== MENU MANUAL ===");
        System.out.println("1. Trapesium (2D)");
        System.out.println("2. Prisma Trapesium (3D)");
        System.out.println("3. Limas Trapesium (3D)");
        System.out.print("Pilih bangun [1/2/3]: ");
        pilihanBangun = scanner.nextInt();

        if (pilihanBangun == 1) {
            hitungTrapesium();
        } else if (pilihanBangun == 2) {
            hitungPrisma();
        } else if (pilihanBangun == 3) {
            hitungLimas();
        } else {
            System.out.println("Pilihan tidak valid.");
        }
    }

    // ── Hitung Trapesium 2D ──

    static void hitungTrapesium() {
        double atas, bawah, tinggi, kiri, kanan;

        System.out.println("\n--- Input Sisi Trapesium ---");
        System.out.print("Sisi atas   : "); atas   = scanner.nextDouble();
        System.out.print("Sisi bawah  : "); bawah  = scanner.nextDouble();
        System.out.print("Tinggi      : "); tinggi = scanner.nextDouble();
        System.out.print("Sisi kiri   : "); kiri   = scanner.nextDouble();
        System.out.print("Sisi kanan  : "); kanan  = scanner.nextDouble();

        Trapesium t = new Trapesium(atas, bawah, tinggi, kiri, kanan);
        double luas     = t.hitungLuas(atas, bawah, tinggi);
        double keliling = t.hitungKeliling(atas, bawah, kiri, kanan);

        System.out.println("\n========== HASIL PERHITUNGAN TRAPESIUM ==========");
        System.out.println("Rumus Luas     : 1/2 x (atas + bawah) x tinggi");
        System.out.printf ("               = 1/2 x (%.2f + %.2f) x %.2f%n", atas, bawah, tinggi);
        System.out.printf ("               = %.2f%n", luas);
        System.out.println();
        System.out.println("Rumus Keliling : atas + bawah + kiri + kanan");
        System.out.printf ("               = %.2f + %.2f + %.2f + %.2f%n", atas, bawah, kiri, kanan);
        System.out.printf ("               = %.2f%n", keliling);
        System.out.println("=================================================");
    }

    // ── Hitung Prisma Trapesium ──

    static void hitungPrisma() {
        double atas, bawah, tinggi, kiri, kanan, tinggiPrisma;

        System.out.println("\n--- Input Sisi Alas Trapesium ---");
        System.out.print("Sisi atas        : "); atas         = scanner.nextDouble();
        System.out.print("Sisi bawah       : "); bawah        = scanner.nextDouble();
        System.out.print("Tinggi trapesium : "); tinggi       = scanner.nextDouble();
        System.out.print("Sisi kiri        : "); kiri         = scanner.nextDouble();
        System.out.print("Sisi kanan       : "); kanan        = scanner.nextDouble();

        System.out.println("\n--- Input Dimensi Prisma ---");
        System.out.print("Tinggi prisma    : "); tinggiPrisma = scanner.nextDouble();

        PrismaTrapesium p = new PrismaTrapesium(atas, bawah, tinggi, kiri, kanan, tinggiPrisma);
        double luas           = p.hitungLuas(atas, bawah, tinggi);
        double keliling       = p.hitungKeliling(atas, bawah, kiri, kanan);
        double volume         = p.hitungVolume(atas, bawah, tinggi);
        double luasPermukaan  = p.hitungLuasPermukaan(atas, bawah, kanan, kiri, tinggi);

        System.out.println("\n========== HASIL PERHITUNGAN PRISMA TRAPESIUM ==========");
        System.out.println("Rumus Luas Alas        : 1/2 x (atas + bawah) x tinggi");
        System.out.printf ("                       = 1/2 x (%.2f + %.2f) x %.2f%n", atas, bawah, tinggi);
        System.out.printf ("                       = %.2f%n", luas);
        System.out.println();
        System.out.println("Rumus Keliling Alas    : atas + bawah + kiri + kanan");
        System.out.printf ("                       = %.2f + %.2f + %.2f + %.2f%n", atas, bawah, kiri, kanan);
        System.out.printf ("                       = %.2f%n", keliling);
        System.out.println();
        System.out.println("Rumus Volume           : Luas Alas x Tinggi Prisma");
        System.out.printf ("                       = %.2f x %.2f%n", luas, tinggiPrisma);
        System.out.printf ("                       = %.2f%n", volume);
        System.out.println();
        System.out.println("Rumus Luas Permukaan   : (2 x Luas Alas) + (Keliling Alas x Tinggi Prisma)");
        System.out.printf ("                       = (2 x %.2f) + (%.2f x %.2f)%n", luas, keliling, tinggiPrisma);
        System.out.printf ("                       = %.2f%n", luasPermukaan);
        System.out.println("=========================================================");
    }

    // ── Hitung Limas Trapesium ──

    static void hitungLimas() {
        double atas, bawah, tinggi, kiri, kanan, tinggiLimas;

        System.out.println("\n--- Input Sisi Alas Trapesium ---");
        System.out.print("Sisi atas        : "); atas       = scanner.nextDouble();
        System.out.print("Sisi bawah       : "); bawah      = scanner.nextDouble();
        System.out.print("Tinggi trapesium : "); tinggi     = scanner.nextDouble();
        System.out.print("Sisi kiri        : "); kiri       = scanner.nextDouble();
        System.out.print("Sisi kanan       : "); kanan      = scanner.nextDouble();

        System.out.println("\n--- Input Dimensi Limas ---");
        System.out.print("Tinggi limas     : "); tinggiLimas = scanner.nextDouble();

        LimasTrapesium l = new LimasTrapesium(atas, bawah, tinggi, kiri, kanan, tinggiLimas);
        double luas          = l.hitungLuas(atas, bawah, tinggi);
        double keliling      = l.hitungKeliling(atas, bawah, kiri, kanan);
        double volume        = l.hitungVolume(atas, bawah, tinggi);
        double luasPermukaan = l.hitungLuasPermukaan(atas, bawah, tinggi, kiri, kanan);

        System.out.println("\n========== HASIL PERHITUNGAN LIMAS TRAPESIUM ==========");
        System.out.println("Rumus Luas Alas        : 1/2 x (atas + bawah) x tinggi");
        System.out.printf ("                       = 1/2 x (%.2f + %.2f) x %.2f%n", atas, bawah, tinggi);
        System.out.printf ("                       = %.2f%n", luas);
        System.out.println();
        System.out.println("Rumus Keliling Alas    : atas + bawah + kiri + kanan");
        System.out.printf ("                       = %.2f + %.2f + %.2f + %.2f%n", atas, bawah, kiri, kanan);
        System.out.printf ("                       = %.2f%n", keliling);
        System.out.println();
        System.out.println("Rumus Volume           : 1/3 x Luas Alas x Tinggi Limas");
        System.out.printf ("                       = 1/3 x %.2f x %.2f%n", luas, tinggiLimas);
        System.out.printf ("                       = %.2f%n", volume);
        System.out.println();
        System.out.println("Rumus Luas Permukaan   : Luas Alas + jumlah luas sisi segitiga tegak");
        System.out.printf ("                       = %.2f (lihat perhitungan apotema)%n", luasPermukaan);
        System.out.println("========================================================");
    }
}