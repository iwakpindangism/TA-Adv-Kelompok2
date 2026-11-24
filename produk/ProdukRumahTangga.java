package produk;

public class ProdukRumahTangga extends Product {

    public ProdukRumahTangga(String namaProduk, double harga, double tarifAcuan,
            int frekuensiPenggunaan, int skorUrgensi,
            TingkatKebutuhan tingkatKebutuhan) {
        super(namaProduk, harga, tarifAcuan, frekuensiPenggunaan, skorUrgensi, tingkatKebutuhan);
    }

    @Override
    public String getKategori() {
        return "Rumah Tangga";
    }
}
