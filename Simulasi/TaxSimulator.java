package simulasi;

import evaluasi.KategoriPrioritas;
import produk.Product;

// Menghitung tarif, nominal pajak, dan harga akhir berdasarkan prioritas produk
// dan kelompok pendapatan. Tarif simulasi tidak boleh melebihi tarif acuan produk.
public class TaxSimulator {

    // Matriks tarif simulasi (asumsi akademis, bukan tarif resmi).
    // Baris   : prioritas  (Sangat Tinggi, Tinggi, Menengah, Rendah)
    // Kolom   : kelompok pendapatan (Rendah, Menengah, Tinggi)
    private static final double[][] MATRIKS_TARIF = {
        {0.00, 0.05, 0.12},   // Sangat Tinggi
        {0.02, 0.06, 0.12},   // Tinggi
        {0.05, 0.08, 0.12},   // Menengah
        {0.11, 0.11, 0.12}    // Rendah
    };

    private double hargaProduk;
    private double tarifAcuan;
    private double tarifSimulasi;
    private double pajakAcuan;
    private double nominalPajak;       // pajak hasil simulasi
    private double hargaSetelahPajak;

    // Titik masuk utama: menjalankan seluruh proses simulasi untuk satu produk.
    public HasilSimulasi jalankanSimulasi(Product produk, KategoriPrioritas prioritas, String kelompok) {
        if (produk == null) {
            throw new IllegalArgumentException("Produk tidak boleh kosong.");
        }
        if (prioritas == null) {
            throw new IllegalArgumentException("Kategori prioritas tidak boleh kosong.");
        }

        // Polymorphism: produk diterima sebagai tipe Product, apa pun kategorinya.
        this.hargaProduk = produk.getHarga();
        this.tarifAcuan = produk.getTarifAcuan();

        hitungTarifSimulasi(prioritas, kelompok);
        hitungPajak();
        hitungHargaAkhir();

        return new HasilSimulasi(
                produk.getNamaProduk(),
                hargaProduk,
                tarifAcuan,
                pajakAcuan,
                tarifSimulasi,
                nominalPajak,
                hargaSetelahPajak,
                prioritas,
                kelompok);
    }

    // Tarif simulasi = nilai terkecil antara tarif dari matriks dan tarif acuan produk.
    public double hitungTarifSimulasi(KategoriPrioritas prioritas, String kelompok) {
        int baris = indeksPrioritas(prioritas);
        int kolom = indeksKelompok(kelompok);
        double tarifMatriks = MATRIKS_TARIF[baris][kolom];
        tarifSimulasi = Math.min(tarifMatriks, tarifAcuan);
        return tarifSimulasi;
    }

    // Menghitung nominal pajak acuan dan pajak simulasi.
    public void hitungPajak() {
        pajakAcuan = hargaProduk * tarifAcuan;
        nominalPajak = hargaProduk * tarifSimulasi;
    }

    // Harga setelah pajak = harga sebelum pajak + pajak simulasi.
    public double hitungHargaAkhir() {
        hargaSetelahPajak = hargaProduk + nominalPajak;
        return hargaSetelahPajak;
    }

    // Selisih pajak acuan dan pajak simulasi (positif = pajak lebih rendah dari acuan).
    public double bandingkanPajak() {
        return pajakAcuan - nominalPajak;
    }

    private int indeksPrioritas(KategoriPrioritas prioritas) {
        switch (prioritas) {
            case SANGAT_TINGGI: return 0;
            case TINGGI:        return 1;
            case MENENGAH:      return 2;
            case RENDAH:        return 3;
            default:
                throw new IllegalArgumentException("Prioritas tidak dikenali: " + prioritas);
        }
    }

    private int indeksKelompok(String kelompok) {
        if (kelompok == null) {
            throw new IllegalArgumentException("Kelompok pendapatan tidak boleh kosong.");
        }
        String k = kelompok.trim();
        if (k.equalsIgnoreCase("Rendah"))   return 0;
        if (k.equalsIgnoreCase("Menengah")) return 1;
        if (k.equalsIgnoreCase("Tinggi"))   return 2;
        throw new IllegalArgumentException("Kelompok pendapatan tidak dikenali: " + kelompok);
    }

    public double getHargaProduk()  { return hargaProduk; }
    public double getTarifAcuan()   { return tarifAcuan; }
    public double getTarifSimulasi(){ return tarifSimulasi; }
    public double getPajakAcuan()   { return pajakAcuan; }
    public double getNominalPajak() { return nominalPajak; }
    public double getHargaSetelahPajak() { return hargaSetelahPajak; }
}