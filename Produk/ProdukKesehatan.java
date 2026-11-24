package produk;

public class ProdukKesehatan extends Product {

    public ProdukKesehatan(String namaProduk, double harga, double tarifAcuan,
            int frekuensiPenggunaan, int skorUrgensi,
            TingkatKebutuhan tingkatKebutuhan) {
        super(namaProduk, harga, tarifAcuan, frekuensiPenggunaan, skorUrgensi, tingkatKebutuhan);
    }

    @Override
    public String getKategori() {
        return "Kesehatan";
    }
}
