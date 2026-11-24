package evaluasi;

public enum KategoriPrioritas {
    SANGAT_TINGGI("Sangat Tinggi"),
    TINGGI("Tinggi"),
    MENENGAH("Menengah"),
    RENDAH("Rendah");

    private final String label;   // teks untuk ditampilkan di terminal

    KategoriPrioritas(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
