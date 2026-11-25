package simulasi;

import evaluasi.KategoriPrioritas;

import java.text.NumberFormat;
import java.util.Locale;

// Menyimpan seluruh hasil satu kali simulasi.
public class HasilSimulasi implements Menampilkan {

    private final String namaProduk;
    private final double hargaSebelumPajak;
    private final double tarifAcuan;
    private final double pajakAcuan;
    private final double tarifSimulasi;
    private final double pajakSimulasi;
    private final double hargaSetelahSimulasi;
    private final KategoriPrioritas prioritas;     // tambahan, untuk ditampilkan & riwayat
    private final String kelompokPendapatan;       // tambahan, untuk ditampilkan & riwayat

    HasilSimulasi(String namaProduk, double hargaSebelumPajak, double tarifAcuan,
                  double pajakAcuan, double tarifSimulasi, double pajakSimulasi,
                  double hargaSetelahSimulasi, KategoriPrioritas prioritas,
                  String kelompokPendapatan) {
        if (namaProduk == null || namaProduk.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama produk tidak boleh kosong.");
        }
        if (!Double.isFinite(hargaSebelumPajak) || hargaSebelumPajak < 0
                || !Double.isFinite(tarifAcuan) || tarifAcuan < 0 || tarifAcuan > 1
                || !Double.isFinite(pajakAcuan) || pajakAcuan < 0
                || !Double.isFinite(tarifSimulasi) || tarifSimulasi < 0 || tarifSimulasi > tarifAcuan
                || !Double.isFinite(pajakSimulasi) || pajakSimulasi < 0
                || !Double.isFinite(hargaSetelahSimulasi) || hargaSetelahSimulasi < hargaSebelumPajak) {
            throw new IllegalArgumentException("Nilai hasil simulasi tidak valid.");
        }
        if (prioritas == null || kelompokPendapatan == null || kelompokPendapatan.trim().isEmpty()) {
            throw new IllegalArgumentException("Prioritas dan kelompok pendapatan wajib diisi.");
        }
        this.namaProduk = namaProduk.trim();
        this.hargaSebelumPajak = hargaSebelumPajak;
        this.tarifAcuan = tarifAcuan;
        this.pajakAcuan = pajakAcuan;
        this.tarifSimulasi = tarifSimulasi;
        this.pajakSimulasi = pajakSimulasi;
        this.hargaSetelahSimulasi = hargaSetelahSimulasi;
        this.prioritas = prioritas;
        this.kelompokPendapatan = kelompokPendapatan.trim();
    }

    // Ringkasan satu baris, dipakai pada daftar riwayat.
    public String getHasilSimulasi() {
        return namaProduk + " | Pendapatan " + kelompokPendapatan
                + " | Prioritas " + prioritas.getLabel()
                + " | Tarif " + persen(tarifAcuan) + " -> " + persen(tarifSimulasi)
                + " | Pajak " + rupiah(pajakAcuan) + " -> " + rupiah(pajakSimulasi)
                + " | Harga akhir " + rupiah(hargaSetelahSimulasi);
    }

    @Override
    public void tampilkan() {
        tampilkanPerbandingan();
    }

    // Menampilkan perbandingan harga dan pajak sebelum (acuan) dan sesudah (simulasi).
    public void tampilkanPerbandingan() {
        String garis = "--------------------------------------------------";
        System.out.println(garis);
        System.out.println("Produk              : " + namaProduk);
        System.out.println("Harga sebelum pajak : " + rupiah(hargaSebelumPajak));
        System.out.println("Kelompok pendapatan : " + kelompokPendapatan);
        System.out.println("Prioritas produk    : " + prioritas.getLabel());
        System.out.println(garis);
        System.out.printf("%-18s %-18s %-18s%n", "", "Acuan", "Simulasi");
        System.out.printf("%-18s %-18s %-18s%n", "Tarif pajak", persen(tarifAcuan), persen(tarifSimulasi));
        System.out.printf("%-18s %-18s %-18s%n", "Nominal pajak", rupiah(pajakAcuan), rupiah(pajakSimulasi));
        System.out.printf("%-18s %-18s %-18s%n", "Harga akhir",
                rupiah(hargaSebelumPajak + pajakAcuan), rupiah(hargaSetelahSimulasi));
        System.out.println(garis);
        System.out.println("Selisih pajak       : " + rupiah(pajakAcuan - pajakSimulasi));
        System.out.println(garis);
    }

    // Format rupiah gaya Indonesia, contoh: Rp 15.000
    public static String rupiah(double nilai) {
        NumberFormat f = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        f.setMaximumFractionDigits(0);
        f.setMinimumFractionDigits(0);
        return "Rp " + f.format(nilai);
    }

    private static String persen(double tarif) {
        return String.format(Locale.US, "%.0f%%", tarif * 100);
    }

    public String getNamaProduk()           { return namaProduk; }
    public double getHargaSebelumPajak()    { return hargaSebelumPajak; }
    public double getTarifAcuan()           { return tarifAcuan; }
    public double getPajakAcuan()           { return pajakAcuan; }
    public double getTarifSimulasi()        { return tarifSimulasi; }
    public double getPajakSimulasi()        { return pajakSimulasi; }
    public double getHargaSetelahSimulasi() { return hargaSetelahSimulasi; }
    public KategoriPrioritas getPrioritas() { return prioritas; }
    public String getKelompokPendapatan()   { return kelompokPendapatan; }
}
