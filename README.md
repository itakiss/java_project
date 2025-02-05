# 🛍️ Clothes Store - JavaFX Aplikacija  

Ova aplikacija je projekt razvijen u sklopu kolegija **"Programiranje u Javi"** na **Tehničkom veleučilištu u Zagrebu**.  
Radi se o **JavaFX aplikaciji** za upravljanje trgovinom odjeće, koja implementira različite objektno orijentirane paradigme, rad s bazom podataka, serijalizaciju podataka te sigurnosne funkcionalnosti.


## 🚀 Ključne značajke:  
✔️ Prijava korisnika s hashiranim lozinkama i ulogama  
✔️ Pregled, pretraga, filtriranje, dodavanje, uređivanje i brisanje proizvoda  
✔️ Upravljanje košaricom - dodavanje i uklanjanje proizvoda  
✔️ Bilježenje svih promjena s mogućnošću pregleda povijesti  
✔️ Automatsko osvježavanje podataka u sučelju pomoću višedretvenog programiranja  
✔️ Slanje e-mail obavijesti prilikom narudžbe  

## 🛠️ Tehnologije:  
🔹 **Java 17** - Objektno orijentirano programiranje  
🔹 **JavaFX** - Grafičko korisničko sučelje  
🔹 **JDBC** - Rad s bazom podataka  
🔹 **Logback** - Logiranje iznimki i događaja  
🔹 **Serialization** - Pohrana podataka o promjenama  
🔹 **Threads** - Višedretveno osvježavanje podataka  
🔹 **JavaMail API** - Slanje e-mailova  

## 📧 Email API  
Aplikacija koristi **JavaMail API** za slanje e-mail obavijesti korisnicima. Kada korisnik izvrši narudžbu, automatski se generira e-mail potvrda koja sadrži detalje o naručenim artiklima.  

Email se šalje pomoću SMTP servera Gmaila, koristeći TLS enkripciju za sigurno slanje poruka. E-mail može sadržavati i privitke, poput računa ili potvrde narudžbe u PDF formatu.  

## 📂 Struktura projekta:  

```
📦 hr.java.project
┣ 📂 entiteti        # Klase koje predstavljaju entitete (Product, Cart, User...)
┣ 📂 glavna          # Glavna aplikacija i pokretanje JavaFX sučelja
┣ 📂 data            # Rad s bazom podataka i datotekama
┣ 📂 fxutil          # Pomoćne klase za rad s JavaFX komponentama
┣ 📂 iznimke         # Definirane korisničke iznimke (ObjectExistsException, InvalidInputException...)
┣ 📂 api             # Slanje e-mailova i vanjske integracije (JavaMail API)
┣ 📂 threadovi       # Višedretvene operacije (osvježavanje sučelja, zapisivanje promjena)
┣ 📂 resources       # FXML datoteke i slike
┣ 📜 ProjectApplication.java  # Glavna klasa aplikacije
┣ 📜 DataBase.java            # Upravljanje konekcijom s bazom podataka
┣ 📜 logger.xml               # Konfiguracija Logback logiranja
```

## 🏁 Pokretanje aplikacije  
1️⃣ Klonirajte repozitorij:  
```bash
git clone https://github.com/korisnicko-ime/clothes-store.git
cd clothes-store
```
2️⃣ Pokrenite aplikaciju iz glavne klase `ProjectApplication.java`.   
