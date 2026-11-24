package produk;

public class ProdukHiburan extends Product {

    public ProdukHiburan(String namaProduk, double harga, double tarifAcuan,
            int frekuensiPenggunaan, int skorUrgensi,
            TingkatKebutuhan tingkatKebutuhan) {
        super(namaProduk, harga, tarifAcuan, frekuensiPenggunaan, skorUrgensi, tingkatKebutuhan);
    }

    @Override
    public String getKategori() {
        return "Hiburan";
    }
}
