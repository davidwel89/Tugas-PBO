
// PROGRAM PEMBAYARAN/PAYMENT YANG MENERAPKAN 4 PILAR UTAMA OOP DI JAVA
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

// ABSTRACTION & ENCAPSULATION
abstract class Payment {
    private double amount;
    private String status;

    public String formatRupiah() {
        Locale indo = new Locale("id", "ID");
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(indo);
        return rupiah.format(amount);
    }

    public Payment(double amount) {
        // Menggunakan setter
        setAmount(amount);
        this.status = "PENDING"; // Status default
    }

    public double getAmount() {
        return amount;
    }

    // Setter untuk mengubah/validasi nilai duit
    public void setAmount(double amount) {
        if (amount < 0) {
            System.out.println("kalo nginput dipikir dulu, lu mau bayar apa pinjam?");
        } else {
            this.amount = amount;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status != null && !status.isEmpty()) {
            this.status = status;
        }
    }

    public abstract void processPayment();
}

// 2. INHERITANCE
class CreditCard extends Payment {
    private String cardNumber;

    public CreditCard(double amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    // 3. POLYMORPHISM
    @Override
    public void processPayment() {
        // PERUBAHAN DI SINI: Gunakan formatRupiah()
        System.out.println("Memproses pembayaran kartu kredit sejumlah " + formatRupiah());
        System.out.println("Menggunakan kartu nomor: " + cardNumber);
    }
}

// Inheritance lagi utk metode pembayaran lain
class QRIS extends Payment {
    public QRIS(double amount) {
        super(amount);
    }

    // Polymorphism utk implementasi berbeda untuk QRIS
    @Override
    public void processPayment() {
        // PERUBAHAN DI SINI: Gunakan formatRupiah()
        System.out.println("Memproses pembayaran QRIS sejumlah " + formatRupiah());
        System.out.println("Menghasilkan kode QR...");
    }
}

// Class Utama untuk menjalankan dan menguji program

public class DavidWelsonTgs1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== SISTEM PEMBAYARAN ===");

        // Pilih Metode
        System.out.println("Pilih Metode Pembayaran:");
        System.out.println("1. Credit Card");
        System.out.println("2. QRIS");
        System.out.print("Masukkan pilihan (1/2): ");
        int pilihan = input.nextInt();

        // Input Jumlah (Menguji Enkapsulasi secara manual)
        System.out.print("Masukkan Jumlah Pembayaran: ");
        double nominal = input.nextDouble();
        input.nextLine(); // Membersihkan sisa enter biar yg kebaca angka nya aja atau clean buffer

        Payment transaksi = null;

        // Inisialisasi berdasarkan pilihan (Polimorfisme)
        if (pilihan == 1) {
            System.out.print("Masukkan Nomor Kartu: ");
            String nomor = input.nextLine();
            transaksi = new CreditCard(nominal, nomor);
        } else if (pilihan == 2) {
            transaksi = new QRIS(nominal);
        }

        // Eksekusi dan Verifikasi
        if (transaksi != null) {
            System.out.println("\n--- Hasil Verifikasi Sistem ---");

            // Jika user input negatif, enkapsulasi akan nolak
            if (transaksi.getAmount() == 0 && nominal != 0) {
                System.out.println("Status: TRANSAKSI GAGAL (Jumlah tidak valid)");
            } else {
                transaksi.setStatus("PROCESSED");
                System.out.println("Status: " + transaksi.getStatus());
                System.out.println("--------------------------------");
                transaksi.processPayment();

                System.out.println("\n[SUKSES] Pembayaran Berhasil!");
                System.out.println("Terima kasih telah bertransaksi.");
            }
        }

        input.close(); // biar ga bisa diinput lagi
    }
}

/*
 * TES PROGRAM
 * public static void main(String[] args) {
 * System.out.println("=== SISTEM PEMBAYARAN OOP JAVA ===\n");
 * 
 * // tipe data 'Payment' untuk berbagai jenis objek
 * Payment bayarSatu = new CreditCard(50000, "1234-5678");
 * Payment bayarDua = new QRIS(25000);
 * 
 * // NGETES ENKAPSULASI
 * System.out.println("--- Menguji Keamanan Data (Encapsulation) ---");
 * 
 * // nyoba jumlah negatif melalui Setter
 * System.out.println("Mencoba set jumlah ke -100.000...");
 * bayarSatu.setAmount(-100000);
 * 
 * // cek jumlah berubah atau tetap aman
 * System.out.println("Jumlah saat ini: Rp" + bayarSatu.getAmount());
 * 
 * // Mengubah status secara resmi melalui Setter
 * bayarSatu.setStatus("SUCCESS");
 * System.out.println("Status Transaksi 1: " + bayarSatu.getStatus());
 * System.out.println("-------------------------------------------\n");
 * 
 * // eksekusi dulu braderrr menjalankan logika dari tiap objek
 * System.out.println("--- Memproses Transaksi ---");
 * 
 * System.out.println("Transaksi 1:");
 * bayarSatu.processPayment(); // Menjalankan versi CreditCard
 * 
 * System.out.println("\nTransaksi 2:");
 * bayarDua.processPayment(); // Menjalankan versi QRIS
 * 
 * System.out.println("\n=== Transaksi Selesai ===");
 * }
 */
