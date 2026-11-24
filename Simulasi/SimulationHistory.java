package simulasi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Riwayat simulasi. Hanya disimpan di memori, hilang saat program ditutup.
public class SimulationHistory implements Menampilkan {

    private final List<HasilSimulasi> daftarHasilSimulasi = new ArrayList<>();

    public void tambahRiwayat(HasilSimulasi hasil) {
        if (hasil == null) {
            throw new IllegalArgumentException("Hasil simulasi tidak boleh kosong.");
        }
        daftarHasilSimulasi.add(hasil);
    }

    public void hapusRiwayat() {
        daftarHasilSimulasi.clear();
    }

    public int getJumlah() {
        return daftarHasilSimulasi.size();
    }

    public List<HasilSimulasi> getDaftarHasilSimulasi() {
        return Collections.unmodifiableList(daftarHasilSimulasi);
    }

    @Override
    public void tampilkan() {
        tampilkanRiwayat();
    }

    private void tampilkanRiwayat() {
        System.out.println("==================================================");
        System.out.println("                RIWAYAT SIMULASI");
        System.out.println("==================================================");
        if (daftarHasilSimulasi.isEmpty()) {
            System.out.println("Belum ada riwayat simulasi.");
        } else {
            for (int i = 0; i < daftarHasilSimulasi.size(); i++) {
                System.out.println((i + 1) + ". " + daftarHasilSimulasi.get(i).getHasilSimulasi());
            }
        }
        System.out.println("==================================================");
    }
}