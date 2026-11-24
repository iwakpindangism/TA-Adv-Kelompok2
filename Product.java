package produk;

public abstract class Product {
    private String namaProduk;
    private double harga;
    private double tarifAcuan;          // desimal, 0.12 = 12%
    private int frekuensiPenggunaan;    // kali per bulan
    private int skorUrgensi;            // 1 - 5
    private TingkatKebutuhan tingkatKebutuhan; //ambil dari TingkatKebutuhan.java (enum)

    public Product(String namaProduk, double harga, double tarifAcuan,
                   int frekuensiPenggunaan, int skorUrgensi,
                   TingkatKebutuhan tingkatKebutuhan) {
        // memakai setter supaya validasi ikut berlaku saat objek dibuat
        setNamaProduk(namaProduk);
        setHarga(harga);
        setTarifAcuan(tarifAcuan);
        setFrekuensiPenggunaan(frekuensiPenggunaan);
        setSkorUrgensi(skorUrgensi);
        setTingkatKebutuhan(tingkatKebutuhan);
    }

    // method abstract, di-override oleh child class
    public abstract String getKategori();

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        if (namaProduk == null || namaProduk.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama produk tidak boleh kosong.");
        }
        this.namaProduk = namaProduk;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        if (harga < 0) {
            throw new IllegalArgumentException("Harga tidak boleh negatif: " + harga);
        }
        this.harga = harga;
    }

    public double getTarifAcuan() {
        return tarifAcuan;
    }

    public void setTarifAcuan(double tarifAcuan) {
        if (tarifAcuan < 0) {
            throw new IllegalArgumentException("Tarif acuan tidak boleh negatif: " + tarifAcuan);
        }
        this.tarifAcuan = tarifAcuan;
    }

    public int getFrekuensiPenggunaan() {
        return frekuensiPenggunaan;
    }

    public void setFrekuensiPenggunaan(int frekuensiPenggunaan) {
        if (frekuensiPenggunaan < 0) {
            throw new IllegalArgumentException("Frekuensi penggunaan tidak boleh negatif: " + frekuensiPenggunaan);
        }
        this.frekuensiPenggunaan = frekuensiPenggunaan;
    }

    public int getSkorUrgensi() {
        return skorUrgensi;
    }

    public void setSkorUrgensi(int skorUrgensi) {
        if (skorUrgensi < 1 || skorUrgensi > 5) {
            throw new IllegalArgumentException("Skor urgensi harus 1-5, diberikan: " + skorUrgensi);
        }
        this.skorUrgensi = skorUrgensi;
    }

    public TingkatKebutuhan getTingkatKebutuhan() {
        return tingkatKebutuhan;
    }

    public void setTingkatKebutuhan(TingkatKebutuhan tingkatKebutuhan) {
        if (tingkatKebutuhan == null) {
            throw new IllegalArgumentException("Tingkat kebutuhan tidak boleh null.");
        }
        this.tingkatKebutuhan = tingkatKebutuhan;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Rp%,.0f | tarif %.0f%% | %dx/bulan | urgensi %d | %s",
                getKategori(), namaProduk, harga, tarifAcuan * 100,
                frekuensiPenggunaan, skorUrgensi, tingkatKebutuhan);
    }
}
