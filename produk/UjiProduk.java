
package produk;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class UjiProduk {

    private static final NumberFormat FORMAT_RUPIAH =
            NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public static void main(String[] args) {

        FORMAT_RUPIAH.setMaximumFractionDigits(0);
        FORMAT_RUPIAH.setMinimumFractionDigits(0);

        List<Product> katalog = DataProduk.getKatalog();

        tampilkanKatalog(katalog);
        ujiValidasiSetter(katalog);
    }

    // Menampilkan seluruh produk berdasarkan kategori.
    private static void tampilkanKatalog(List<Product> katalog) {

        System.out.println("\n==================================================");
        System.out.println("                 KATALOG PRODUK");
        System.out.println("==================================================");
        System.out.println("Jumlah produk: " + katalog.size());

        String kategoriSebelumnya = "";

        for (int i = 0; i < katalog.size(); i++) {

            Product produk = katalog.get(i);
            String kategori = produk.getKategori();

            if (!kategori.equals(kategoriSebelumnya)) {
                System.out.println("\n[" + kategori.toUpperCase() + "]");
                kategoriSebelumnya = kategori;
            }

            System.out.printf("%02d. %s%n",
                    i + 1, produk.getNamaProduk());

            System.out.println("    Harga             : "
                    + FORMAT_RUPIAH.format(produk.getHarga()));

            System.out.printf("    Tarif Acuan       : %.0f%%%n",
                    produk.getTarifAcuan() * 100);

            System.out.println("    Frekuensi         : "
                    + produk.getFrekuensiPenggunaan() + " kali/bulan");

            System.out.println("    Skor Urgensi      : "
                    + produk.getSkorUrgensi() + "/5");

            System.out.println("    Tingkat Kebutuhan : "
                    + produk.getTingkatKebutuhan());
        }

        System.out.println("\n==================================================");
        System.out.println("             KATALOG SELESAI DITAMPILKAN");
        System.out.println("==================================================");
    }

    // Menguji validasi setter pada class Product.
    private static void ujiValidasiSetter(List<Product> katalog) {

        System.out.println("\n==================================================");
        System.out.println("             PENGUJIAN VALIDASI SETTER");
        System.out.println("==================================================");

        if (katalog.isEmpty()) {
            System.out.println("Pengujian tidak dapat dilakukan karena katalog kosong.");
            return;
        }

        Product produk = katalog.get(0);

        int berhasil = 0;
        int total = 5;

        berhasil += ujiKasus("Harga negatif",
                () -> produk.setHarga(-1000));

        berhasil += ujiKasus("Harga NaN",
                () -> produk.setHarga(Double.NaN));

        berhasil += ujiKasus("Tarif acuan Infinity",
                () -> produk.setTarifAcuan(Double.POSITIVE_INFINITY));

        berhasil += ujiKasus("Skor urgensi melebihi batas",
                () -> produk.setSkorUrgensi(6));

        berhasil += ujiKasus("Skor urgensi di bawah batas",
                () -> produk.setSkorUrgensi(0));

        System.out.println("--------------------------------------------------");
        System.out.println("Hasil pengujian: " + berhasil + "/" + total);

        if (berhasil == total) {
            System.out.println("Status: Seluruh validasi berhasil.");
        } else {
            System.out.println("Status: Terdapat validasi yang belum sesuai.");
        }

        System.out.println("==================================================");
    }

    // Memeriksa apakah input tidak valid berhasil ditolak.
    private static int ujiKasus(String namaPengujian, Runnable pengujian) {

        try {
            pengujian.run();

            System.out.println("[GAGAL] " + namaPengujian
                    + " - Input tidak ditolak.");

            return 0;

        } catch (IllegalArgumentException e) {

            System.out.println("[BERHASIL] " + namaPengujian
                    + " - Input tidak valid berhasil ditolak.");

            return 1;
        }
    }
}
