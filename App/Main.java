package app;

import evaluasi.IncomeGroup;
import evaluasi.PriorityEvaluator;
import produk.DataProduk;
import produk.Product;
import simulasi.HasilSimulasi;
import simulasi.SimulationHistory;
import simulasi.TaxSimulator;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

// Program utama TaxFair (Tax Fairness Simulator).
// Catatan: exception handling dipusatkan di bacaAngka() dan blok try-catch pada alur simulasi.
public class Main {

    private static final Scanner INPUT = new Scanner(System.in);
    private static final List<Product> KATALOG = DataProduk.getKatalog();
    private static final List<IncomeGroup> KELOMPOK = IncomeGroup.getDaftarKelompok();
    private static final TaxSimulator SIMULATOR = new TaxSimulator();
    private static final SimulationHistory RIWAYAT = new SimulationHistory();

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   TaxFair - Tax Fairness Simulator");
        System.out.println("   Simulasi akademis, bukan penentu tarif pajak resmi.");
        System.out.println("==================================================");

        boolean berjalan = true;
        while (berjalan) {
            tampilkanMenu();
            int pilihan = bacaAngka("Pilih menu (1-3): ", 1, 3);
            switch (pilihan) {
                case 1: inputProduk(); break;
                case 2: menuRiwayat(); break;
                case 3: berjalan = false; break;
                default: break;
            }
        }

        System.out.println("Terima kasih telah menggunakan TaxFair.");
        System.out.println("Riwayat simulasi tidak disimpan setelah program ditutup.");
        INPUT.close();
    }

    private static void tampilkanMenu() {
        System.out.println("\n==================================================");
        System.out.println("                   MENU UTAMA");
        System.out.println("==================================================");
        System.out.println("1. Simulasi Pajak");
        System.out.println("2. Riwayat Simulasi");
        System.out.println("3. Keluar");
        System.out.println("==================================================");
    }

    // Alur simulasi: pilih kategori -> produk -> kelompok pendapatan -> hitung -> tampilkan.
    private static void inputProduk() {
        while (true) {
            List<String> kategori = ambilKategori();

            System.out.println("\n--- PILIH KATEGORI ---");
            for (int i = 0; i < kategori.size(); i++) {
                System.out.println((i + 1) + ". " + kategori.get(i));
            }
            System.out.println("0. Kembali ke menu utama");
            int idxKategori = bacaAngka("Pilih kategori: ", 0, kategori.size());
            if (idxKategori == 0) {
                return;
            }

            Product produk = pilihProduk(kategori.get(idxKategori - 1));
            if (produk == null) {
                continue;   // pengguna memilih kembali, ulangi dari kategori
            }

            IncomeGroup kelompok = inputPendapatan();
            if (kelompok == null) {
                continue;
            }

            boolean sukses = jalankanSimulasi(produk, kelompok);
            if (!sukses) {
                continue;
            }

            System.out.println();
            int lanjut = bacaAngka("1 = Ulangi simulasi, 0 = Kembali ke menu utama: ", 0, 1);
            if (lanjut == 0) {
                return;
            }
        }
    }

    // Kategori unik sesuai urutan kemunculan di katalog.
    private static List<String> ambilKategori() {
        Set<String> unik = new LinkedHashSet<>();
        for (Product p : KATALOG) {
            unik.add(p.getKategori());   // polymorphism: getKategori() dari tipe Product
        }
        return new ArrayList<>(unik);
    }

    // Mengembalikan null jika pengguna memilih kembali.
    private static Product pilihProduk(String kategoriDipilih) {
        List<Product> daftar = new ArrayList<>();
        for (Product p : KATALOG) {
            if (p.getKategori().equals(kategoriDipilih)) {
                daftar.add(p);
            }
        }

        System.out.println("\n--- PRODUK KATEGORI " + kategoriDipilih.toUpperCase() + " ---");
        for (int i = 0; i < daftar.size(); i++) {
            Product p = daftar.get(i);
            System.out.println((i + 1) + ". " + p.getNamaProduk()
                    + " (" + HasilSimulasi.rupiah(p.getHarga()) + ")");
        }
        System.out.println("0. Kembali");

        int idx = bacaAngka("Pilih produk: ", 0, daftar.size());
        return idx == 0 ? null : daftar.get(idx - 1);
    }

    // Pengguna memilih kelompok pendapatan dari daftar. Mengembalikan null jika kembali.
    private static IncomeGroup inputPendapatan() {
        System.out.println("\n--- PILIH KELOMPOK PENDAPATAN ---");
        for (int i = 0; i < KELOMPOK.size(); i++) {
            System.out.println((i + 1) + ". " + KELOMPOK.get(i).getLabelMenu());
        }
        System.out.println("0. Kembali");

        int idx = bacaAngka("Pilih kelompok: ", 0, KELOMPOK.size());
        return idx == 0 ? null : KELOMPOK.get(idx - 1);
    }

    // Menjalankan evaluasi prioritas dan simulasi pajak, lalu menampilkan hasil.
    // Mengembalikan false jika terjadi error validasi.
    private static boolean jalankanSimulasi(Product produk, IncomeGroup kelompok) {
        try {
            PriorityEvaluator evaluator = new PriorityEvaluator(
                    produk.getSkorUrgensi(), produk.getFrekuensiPenggunaan());

            HasilSimulasi hasil = SIMULATOR.jalankanSimulasi(
                    produk, evaluator.getKategoriPrioritas(), kelompok.getNamaKelompok());

            tampilkanHasil(evaluator, hasil);
            RIWAYAT.tambahRiwayat(hasil);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("[!] Simulasi gagal: " + e.getMessage());
            return false;
        }
    }

    // Menampilkan skor prioritas dan perbandingan pajak sebelum & sesudah simulasi.
    private static void tampilkanHasil(PriorityEvaluator evaluator, HasilSimulasi hasil) {
        System.out.println();
        System.out.printf("Skor prioritas produk: %.1f (%s)%n",
                evaluator.getSkorPrioritas(), evaluator.getKategoriPrioritas().getLabel());
        hasil.tampilkan();
    }

    private static void menuRiwayat() {
        while (true) {
            System.out.println("\n--- RIWAYAT SIMULASI ---");
            System.out.println("1. Tampilkan riwayat");
            System.out.println("2. Hapus riwayat");
            System.out.println("0. Kembali ke menu utama");

            int pilihan = bacaAngka("Pilih: ", 0, 2);
            switch (pilihan) {
                case 0:
                    return;
                case 1:
                    RIWAYAT.tampilkan();
                    break;
                case 2:
                    hapusRiwayatDenganKonfirmasi();
                    break;
                default:
                    break;
            }
        }
    }

    private static void hapusRiwayatDenganKonfirmasi() {
        if (RIWAYAT.getJumlah() == 0) {
            System.out.println("Riwayat masih kosong.");
            return;
        }
        int yakin = bacaAngka("Hapus " + RIWAYAT.getJumlah() + " riwayat? (1 = Ya, 0 = Tidak): ", 0, 1);
        if (yakin == 1) {
            RIWAYAT.hapusRiwayat();
            System.out.println("Riwayat berhasil dihapus.");
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }

    // Membaca bilangan bulat dalam rentang [min, max]. Terus meminta input ulang
    // jika berupa teks, di luar rentang, atau kosong, tanpa menghentikan program.
    private static int bacaAngka(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (!INPUT.hasNextLine()) {
                // Input ditutup (mis. Ctrl+D): akhiri program dengan rapi.
                System.out.println("\nInput ditutup. Program dihentikan.");
                System.exit(0);
            }
            String teks = INPUT.nextLine().trim();
            try {
                int angka = Integer.parseInt(teks);
                if (angka < min || angka > max) {
                    throw new IllegalArgumentException(
                            "Pilihan harus antara " + min + " dan " + max + ".");
                }
                return angka;
            } catch (NumberFormatException e) {
                System.out.println("[!] Input harus berupa angka bulat, bukan \"" + teks + "\". Coba lagi.");
            } catch (IllegalArgumentException e) {
                System.out.println("[!] " + e.getMessage() + " Coba lagi.");
            }
        }
    }
}