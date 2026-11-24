package produk;

public class ProdukElektronik extends Product {

    public ProdukElektronik(String namaProduk, double harga, double tarifAcuan,
            int frekuensiPenggunaan, int skorUrgensi,
            TingkatKebutuhan tingkatKebutuhan) {
        super(namaProduk, harga, tarifAcuan, frekuensiPenggunaan, skorUrgensi, tingkatKebutuhan);
    }

    @Override
    public String getKategori() {
        return "Elektronik";
    }
}
