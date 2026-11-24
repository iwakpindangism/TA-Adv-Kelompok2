package produk;

import java.util.ArrayList;
import java.util.List;

public class DataProduk {
    private static final TingkatKebutuhan E = TingkatKebutuhan.ESSENTIAL;
    private static final TingkatKebutuhan N = TingkatKebutuhan.NON_ESSENTIAL;
    private static final TingkatKebutuhan L = TingkatKebutuhan.LUXURY;

    // Mengembalikan katalog baru setiap dipanggil (polymorphism: List<Product>)
    public static List<Product> getKatalog() {
        List<Product> katalog = new ArrayList<>();

        // Pangan
        katalog.add(new ProdukPangan("Beras", 15000, 0, 30, 5, E));
        katalog.add(new ProdukPangan("Garam Beryodium Halus", 6000, 0, 30, 5, E));
        katalog.add(new ProdukPangan("Telur Ayam", 24000, 0, 20, 5, E));

        // Kesehatan
        katalog.add(new ProdukKesehatan("Pembalut", 20000, 0.12, 20, 5, E));
        katalog.add(new ProdukKesehatan("Vitamin C", 30000, 0.12, 30, 2, N));
        katalog.add(new ProdukKesehatan("Masker", 20000, 0.12, 10, 3, N));

        // Rumah Tangga
        katalog.add(new ProdukRumahTangga("Sabun Mandi", 5000, 0.12, 30, 5, E));
        katalog.add(new ProdukRumahTangga("Sampo", 22000, 0.12, 15, 4, E));
        katalog.add(new ProdukRumahTangga("Deterjen", 20000, 0.12, 12, 4, E));

        // Elektronik
        katalog.add(new ProdukElektronik("Xiaomi Pad 7 (8GB/256GB)", 5499000, 0.12, 15, 3, N));
        katalog.add(new ProdukElektronik("Xiaomi 14T (12GB/512GB)", 6999000, 0.12, 30, 5, E));
        katalog.add(new ProdukElektronik("Lenovo IdeaPad Slim 5 14ABR8 (Ryzen 7 7730U/16GB/512GB)", 10999000, 0.12, 25, 5, E));

        // Hiburan
        katalog.add(new ProdukHiburan("Netflix Paket Premium", 186000, 0.12, 15, 1, L));
        katalog.add(new ProdukHiburan("Tiket Dufan Premium / Fast Trax", 1250000, 0.12, 1, 1, L));
        katalog.add(new ProdukHiburan("Tiket Bioskop", 40000, 0.12, 2, 2, N));

        return katalog;
    }
}
