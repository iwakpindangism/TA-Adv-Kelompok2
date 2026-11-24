package produk;

import java.util.List;

// Hanya untuk menguji modul produk. Boleh dihapus setelah digabung ke Main.
public class UjiProduk {
    public static void main(String[] args) {
        List<Product> katalog = DataProduk.getKatalog();
        System.out.println("Jumlah produk: " + katalog.size());
        for (Product p : katalog) {
            System.out.println(p);
        }

        System.out.println("\n--- Uji validasi setter ---");
        Product p = katalog.get(0);
        try { p.setHarga(-1000); } catch (IllegalArgumentException e) { System.out.println("Ditolak: " + e.getMessage()); }
        try { p.setSkorUrgensi(6); } catch (IllegalArgumentException e) { System.out.println("Ditolak: " + e.getMessage()); }
        try { p.setSkorUrgensi(0); } catch (IllegalArgumentException e) { System.out.println("Ditolak: " + e.getMessage()); }
    }
}
