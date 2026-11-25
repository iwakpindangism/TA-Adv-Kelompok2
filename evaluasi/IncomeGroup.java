package evaluasi;

import java.util.ArrayList;
import java.util.List;

public class IncomeGroup {
    private final String namaKelompok;
    private final double pendapatanMinimum;   // termasuk
    private final double pendapatanMaksimum;  // tidak termasuk (batas atas kelompok berikutnya)

    public IncomeGroup(String namaKelompok, double pendapatanMinimum, double pendapatanMaksimum) {
        if (namaKelompok == null || namaKelompok.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama kelompok tidak boleh kosong.");
        }
        if (!Double.isFinite(pendapatanMinimum) || pendapatanMinimum < 0) {
            throw new IllegalArgumentException("Pendapatan minimum tidak valid: " + pendapatanMinimum);
        }
        if (Double.isNaN(pendapatanMaksimum) || pendapatanMaksimum <= pendapatanMinimum) {
            throw new IllegalArgumentException("Pendapatan maksimum harus lebih besar dari minimum: " + pendapatanMaksimum);
        }
        this.namaKelompok = namaKelompok.trim();
        this.pendapatanMinimum = pendapatanMinimum;
        this.pendapatanMaksimum = pendapatanMaksimum;
    }

    // Tiga kelompok pendapatan (rupiah per bulan). Mengembalikan daftar baru setiap dipanggil.
    // Nama kelompok sama dengan kolom matriks tarif: Rendah, Menengah, Tinggi.
    public static List<IncomeGroup> getDaftarKelompok() {
        List<IncomeGroup> daftar = new ArrayList<>();
        daftar.add(new IncomeGroup("Rendah", 0, 3000000));
        daftar.add(new IncomeGroup("Menengah", 3000000, 7000000));
        daftar.add(new IncomeGroup("Tinggi", 7000000, Double.POSITIVE_INFINITY));
        return daftar;
    }

    public String getNamaKelompok() {
        return namaKelompok;
    }

    public double getPendapatanMinimum() {
        return pendapatanMinimum;
    }

    public double getPendapatanMaksimum() {
        return pendapatanMaksimum;
    }

    // true jika minimum <= pendapatan < maksimum
    public boolean cekPendapatan(double pendapatan) {
        if (!Double.isFinite(pendapatan) || pendapatan < 0) {
            throw new IllegalArgumentException("Pendapatan harus berupa angka terbatas dan tidak boleh negatif: " + pendapatan);
        }

        return pendapatan >= pendapatanMinimum && pendapatan < pendapatanMaksimum;
    }

    // Label untuk menu, contoh: "Menengah (Rp3-7 juta)"
    public String getLabelMenu() {
        String rentang;
        if (pendapatanMinimum == 0) {
            rentang = "di bawah Rp" + juta(pendapatanMaksimum) + " juta";
        } else if (Double.isInfinite(pendapatanMaksimum)) {
            rentang = "Rp" + juta(pendapatanMinimum) + " juta ke atas";
        } else {
            rentang = "Rp" + juta(pendapatanMinimum) + "-" + juta(pendapatanMaksimum) + " juta";
        }
        return namaKelompok + " (" + rentang + ")";
    }

    private String juta(double nilai) {
        double j = nilai / 1000000;
        return (j == Math.floor(j)) ? String.valueOf((long) j) : String.valueOf(j);
    }

    @Override
    public String toString() {
        return getLabelMenu();
    }
}
