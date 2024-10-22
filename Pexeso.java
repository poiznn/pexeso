import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Pexeso {
    private static char[][] deska;
    private static boolean[][] odhalene;
    private static int velikost;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Zadejte velikost desky (pouze sudé číslo větší než 2): ");
            velikost = scanner.nextInt();
        } while (velikost % 2 != 0 || velikost < 4);

        vytvoritDesku();
        hratPexeso(scanner);
        scanner.close();
    }

    private static void vytvoritDesku() {
        int pocetSymbolu = (velikost * velikost) / 2;
        ArrayList<Character> symboly = new ArrayList<>();

        for (char i = 'A'; i < 'A' + pocetSymbolu; i++) {
            symboly.add(i);
            symboly.add(i);
        }

        Collections.shuffle(symboly);
        deska = new char[velikost][velikost];
        odhalene = new boolean[velikost][velikost];

        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                deska[i][j] = symboly.remove(0);
            }
        }
    }

    private static void zobrazitDesku() {
        System.out.print("   ");
        for (int j = 0; j < velikost; j++) {
            System.out.print(j + " ");
        }
        System.out.println("\n  " + "---".repeat(velikost));

        for (int i = 0; i < velikost; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < velikost; j++) {
                if (odhalene[i][j]) {
                    System.out.print(deska[i][j] + " ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean validniSouradnice(int radek, int sloupec) {
        return radek >= 0 && radek < velikost && sloupec >= 0 && sloupec < velikost && !odhalene[radek][sloupec];
    }

    private static void hratPexeso(Scanner scanner) {
        int[] hraciBody = {0, 0};
        int hrac = 0;
        int paryNalezeny = 0;

        System.out.println("Vítejte ve hře Pexeso!");
        System.out.println("Vaším úkolem je najít všechny dvojice stejných karet tím, že je budete postupně otáčet.\n");

        while (paryNalezeny < (velikost * velikost) / 2) {
            System.out.println("Hráč " + (hrac + 1) + " je na řadě.");
            zobrazitDesku();

            int radek1 = ziskatSouradnice(scanner);
            int sloupec1 = ziskatSouradnice(scanner);
            odhalene[radek1][sloupec1] = true;
            zobrazitDesku();

            int radek2 = ziskatSouradnice(scanner);
            int sloupec2 = ziskatSouradnice(scanner);
            odhalene[radek2][sloupec2] = true;
            zobrazitDesku();

            if (deska[radek1][sloupec1] == deska[radek2][sloupec2]) {
                System.out.println("Správný pár!");
                paryNalezeny++;
                hraciBody[hrac]++;
            } else {
                System.out.println("Nesprávný pár. Karty se skryjí.");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                odhalene[radek1][sloupec1] = false;
                odhalene[radek2][sloupec2] = false;
            }

            hrac = (hrac + 1) % 2;
        }

        System.out.println("Konec hry! Všechny páry byly nalezeny.");
        System.out.println("Hráč 1: " + hraciBody[0] + " párů");
        System.out.println("Hráč 2: " + hraciBody[1] + " párů");

        if (hraciBody[0] > hraciBody[1]) {
            System.out.println("Hráč 1 vyhrál!");
        } else if (hraciBody[0] < hraciBody[1]) {
            System.out.println("Hráč 2 vyhrál!");
        } else {
            System.out.println("Remíza!");
        }

        System.out.print("Chcete si zahrát znovu? (ano/ne): ");
        String odpoved = scanner.next().trim().toLowerCase();
        if (odpoved.equals("ano")) {
            vytvoritDesku();
            hratPexeso(scanner);
        }
    }

    private static int ziskatSouradnice(Scanner scanner) {
        int radek, sloupec;
        while (true) {
            System.out.print("Zadejte souřadnice karty (např. 1 2): ");
            radek = scanner.nextInt();
            sloupec = scanner.nextInt();
            if (validniSouradnice(radek, sloupec)) {
                return radek;
            } else {
                System.out.println("Neplatné souřadnice. Zkuste to znovu.");
            }
        }
    }
}

