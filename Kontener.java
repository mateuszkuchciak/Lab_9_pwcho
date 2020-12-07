import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;


public class Kontener {

    public static void aktualizuj(Connection polaczenie, int idgitary, String rodzaj, String producent) throws SQLException {
        String sql = "UPDATE gitary SET rodzaj = ?, producent = ? WHERE ID = ?";
        PreparedStatement polecenie = polaczenie.prepareStatement(sql);
        polecenie.setString(1, rodzaj);
        polecenie.setString(2, producent);
        polecenie.setInt(3, idgitary);
        int modyfikacja_rekordow = polecenie.executeUpdate();
        if (modyfikacja_rekordow > 0) {
            System.out.println("Gitare o id =" + idgitary + " udalo sie zmodyfikowac.");
        }
        polecenie.close();
    }

    public static void dodaj_gitare(Connection polaczenie, String rodzaj, String producent) throws SQLException {
        String sql = "INSERT INTO gitary(rodzaj, producent) VALUES (?, ?)";
        PreparedStatement polecenie = polaczenie.prepareStatement(sql);
        polecenie.setString(1, rodzaj);
        polecenie.setString(2, producent);
        int modyfikacja_rekordow = polecenie.executeUpdate();
        if (modyfikacja_rekordow > 0) {
            System.out.println("Dodano gitare.");
        }
        polecenie.close();
    }

    public static void wyswietl_gitary(Connection polaczenie) throws SQLException {
        String sql = "SELECT * FROM gitary";
        Statement polecenie = polaczenie.createStatement();
        ResultSet wynik = polecenie.executeQuery(sql);
        while (wynik.next()) {
            String rodzaj = wynik.getString("RODZAJ");
            String producent = wynik.getString("PRODUCENT");
            int idgitary = wynik.getInt("ID");
            System.out.println("ID: " + idgitary + ", Rodzaj: " + rodzaj + ", Producent: " + producent);
        }
        wynik.close();
    }



    public static void usun_gitare(Connection polaczenie, int idgitary) throws SQLException {
        String sql = "DELETE FROM gitary WHERE ID = ?";
        PreparedStatement polecenie = polaczenie.prepareStatement(sql);
        polecenie.setInt(1, idgitary);
        int modyfikacja_rekordow = polecenie.executeUpdate();
        if (modyfikacja_rekordow > 0) {
            System.out.println("Gitare o id=" + idgitary + " usunieto z bazy.");
        }
        polecenie.close();
    }

    public static void wyswietl_menu() {
        System.out.println();
        System.out.println("Menu:");
        System.out.println("1.Pokaz gitary");
        System.out.println("2.Dodaj gitare");
        System.out.println("3.Modyfikuj gitare");
        System.out.println("4.Usun gitare");
        System.out.println("5.Zakoncz");
        System.out.println();
    }
    
    public static void dodaj_tabele(Connection polaczenie) throws SQLException{
        String sql = "CREATE TABLE gitary(ID int auto_increment primary key, RODZAJ varchar(50), PRODUCENT varchar(50));";
        Statement polecenie = polaczenie.createStatement();
        int tworzenie = polecenie.executeUpdate(sql);
        if(tworzenie != 0){
            System.out.println("Nie udalo sie utworzyc tabeli!");
        }
        polecenie.close();
    }

    public static void main(String [] args) throws SQLException{
        String URL = "jdbc:mysql://10.0.10.3:3306/gitary?characterEncoding=utf8";
        String UZYTKOWNIK = "Mkuchciak";
        String HASLO = "root";
        Connection polaczenie = null;
        try {
            while(polaczenie == null) {
                try {
                    System.out.println("Trwa proba polaczenia");
                    polaczenie = DriverManager.getConnection(URL, UZYTKOWNIK, HASLO);
                } catch (SQLException exep) {
                    System.out.println("Blad polaczenia");
                    System.out.println(exep);
                }
                try {
                    Thread.sleep(10000);
                } catch (Exception exep) {
                    System.out.println(exep);
                }
            }
	        if (polaczenie != null) {
		        System.out.println("Udane polaczenie z baza danych");
                Scanner scanner = new Scanner(System.in);
                int opcja = 0;
		        String rodzaj = null;
                String producent = null;
		        int idgitary = 0;

                dodaj_tabele(polaczenie);

		        while(opcja != 5) {
		            wyswietl_menu();
		            opcja = scanner.nextInt();
		            switch(opcja) {
		                case 1:
		                    wyswietl_gitary(polaczenie);
		                break;                    
		                case 2:
                            scanner.nextLine();
		                    System.out.println("Podaj rodzaj gitary: ");
		                    rodzaj = scanner.nextLine();
		                    System.out.println("Podaj producenta gitary: ");
		                    producent = scanner.nextLine();
		                    dodaj_gitare(polaczenie, rodzaj, producent);
		                break;
		                case 3:
		                    System.out.println("Podaj id gitary:");
		                    idgitary = scanner.nextInt();
                            scanner.nextLine();
		                    System.out.println("Podaj rodzaj gitary: ");
		                    rodzaj = scanner.nextLine();
		                    System.out.println("Podaj producenta gitary: ");
		                    producent = scanner.nextLine();
		                    aktualizuj(polaczenie, idgitary, rodzaj, producent);                        
		                break;
		                case 4:
		                    System.out.println("Podaj id:");
		                    idgitary = scanner.nextInt();
		                    usun_gitare(polaczenie, idgitary);
		                break;
		                case 5:
                            polaczenie.close();
		                    System.exit(0);
		                break;
		                default: System.out.println("Brak opcji");
		                break;
		            }
		        }
		    }
        } catch (Exception exep) {
            System.out.println(exep);
        }
        polaczenie.close();
    }
}
