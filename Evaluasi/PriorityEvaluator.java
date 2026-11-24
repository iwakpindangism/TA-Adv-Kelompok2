package evaluasi;

public class PriorityEvaluator {
    private static final int FREKUENSI_MAKSIMAL = 30;   // batas frekuensi dalam perhitungan

    private final int skorUrgensi;            // 1 - 5
    private final int frekuensiPenggunaan;    // kali per bulan, tidak negatif
    private double skorPrioritas;             // 0 - 100
    private KategoriPrioritas kategoriPrioritas;

    // Skor dan kategori langsung dihitung di constructor,
    // jadi getter selalu berisi hasil yang benar.
    public PriorityEvaluator(int skorUrgensi, int frekuensiPenggunaan) {
        if (skorUrgensi < 1 || skorUrgensi > 5) {
            throw new IllegalArgumentException("Skor urgensi harus 1-5, diberikan: " + skorUrgensi);
        }
        if (frekuensiPenggunaan < 0) {
            throw new IllegalArgumentException("Frekuensi penggunaan tidak boleh negatif: " + frekuensiPenggunaan);
        }
        this.skorUrgensi = skorUrgensi;
        this.frekuensiPenggunaan = frekuensiPenggunaan;
        hitungSkorPrioritas();
        tentukanPrioritas();
    }

    // Skor = (Urgensi / 5 x 60% + Frekuensi / 30 x 40%) x 100
    // Ditulis tanpa desimal 0.6 dan 0.4 supaya hasil di batas kategori (80, 60, 40) tetap tepat.
    public double hitungSkorPrioritas() {
        int frekuensiDipakai = Math.min(frekuensiPenggunaan, FREKUENSI_MAKSIMAL);
        skorPrioritas = (skorUrgensi * 60.0 / 5) + (frekuensiDipakai * 40.0 / 30);
        return skorPrioritas;
    }

    // 80-100 Sangat Tinggi, 60-79 Tinggi, 40-59 Menengah, 0-39 Rendah
    public KategoriPrioritas tentukanPrioritas() {
        if (skorPrioritas >= 80) {
            kategoriPrioritas = KategoriPrioritas.SANGAT_TINGGI;
        } else if (skorPrioritas >= 60) {
            kategoriPrioritas = KategoriPrioritas.TINGGI;
        } else if (skorPrioritas >= 40) {
            kategoriPrioritas = KategoriPrioritas.MENENGAH;
        } else {
            kategoriPrioritas = KategoriPrioritas.RENDAH;
        }
        return kategoriPrioritas;
    }

    public double getSkorPrioritas() {
        return skorPrioritas;
    }

    public KategoriPrioritas getKategoriPrioritas() {
        return kategoriPrioritas;
    }
}
