package produk;

public class ProdukPangan extends Product {

    public ProdukPangan(String namaProduk, double harga, double tarifAcuan,
            int frekuensiPenggunaan, int skorUrgensi,
            TingkatKebutuhan tingkatKebutuhan) {
        super(namaProduk, harga, tarifAcuan, frekuensiPenggunaan, skorUrgensi, tingkatKebutuhan);
    }

    @Override
    public String getKategori() {
        return "Pangan";
    }
}
