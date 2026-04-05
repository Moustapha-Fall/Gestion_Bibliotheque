# 📘 GUIDE COMPLET — Bibliothèque Municipale

> Projet Java POO — ESP/UCAD  
> Professeur : Pr. Samba DIAW

---

## 1. STRUCTURE DU PROJET

```
bibliotheque/
│
├── lib/
│   └── sqlite-jdbc-3.45.0.0.jar    ← à télécharger (voir étape 2)
│
├── src/
│   └── com/bibliotheque/
│       │
│       ├── Main.java                ← point d'entrée du programme
│       │
│       ├── enums/
│       │   ├── EnumOuvrage.java     (ROMAN, THEATRE, FABLE, BANDE_DESSINEE, MANGA)
│       │   ├── EnumEmprunt.java     (EN_COURS, RENDUE, EN_RETARD, PERDUE)
│       │   ├── EnumAmende.java      (PERDU, DATE_PREVUE, DEPASSE)
│       │   └── EnumExemplaire.java  (DISPONIBLE, EMPRUNTER, EN_REPARATION)
│       │
│       ├── model/
│       │   ├── Personne.java        (classe abstraite)
│       │   ├── Bibliothecaire.java  (extends Personne)
│       │   ├── Membre.java          (extends Personne)
│       │   ├── Ouvrage.java         (classe abstraite)
│       │   ├── Livre.java           (extends Ouvrage)
│       │   ├── Exemplaire.java
│       │   ├── Emprunt.java
│       │   ├── Amende.java
│       │   ├── Caisse.java
│       │   ├── Annexe.java
│       │   ├── Vehicule.java
│       │   └── ConditionStockage.java
│       │
│       └── service/
│           ├── Service.java              (connexion + init tables)
│           ├── MembreService.java
│           ├── OuvrageService.java
│           ├── ExemplaireService.java
│           ├── EmpruntService.java
│           ├── AmendeService.java
│           ├── CaisseService.java
│           ├── AnnexeService.java
│           ├── VehiculeService.java
│           └── ConditionStockageService.java
│
├── bibliotheque.db    ← créé automatiquement au 1er lancement
└── GUIDE.md
```

---

## 2. INSTALLATION DU DRIVER SQLite (OBLIGATOIRE)

Le projet utilise **SQLite** comme base de données.  
SQLite n'a pas besoin de serveur — la base est un simple fichier `.db`.

### Étape 1 — Créer le dossier lib

```bash
mkdir lib
```

### Étape 2 — Télécharger le fichier JAR

Va sur ce lien et télécharge le fichier JAR :

```
https://github.com/xerial/sqlite-jdbc/releases/download/3.45.0.0/sqlite-jdbc-3.45.0.0.jar
```

Ou via la commande wget :

```bash
wget -P lib/ https://github.com/xerial/sqlite-jdbc/releases/download/3.45.0.0/sqlite-jdbc-3.45.0.0.jar
```

### Étape 3 — Vérifier

```bash
ls lib/
# tu dois voir : sqlite-jdbc-3.45.0.0.jar
```

---

## 3. COMPILATION

### Sur Linux / Mac

```bash
# Se placer à la racine du projet
cd bibliotheque/

# Compiler tous les fichiers Java
javac -cp lib/sqlite-jdbc-3.45.0.0.jar -d . src/com/bibliotheque/enums/*.java
javac -cp lib/sqlite-jdbc-3.45.0.0.jar -d . src/com/bibliotheque/model/*.java
javac -cp lib/sqlite-jdbc-3.45.0.0.jar -d . src/com/bibliotheque/service/*.java
javac -cp lib/sqlite-jdbc-3.45.0.0.jar -d . src/com/bibliotheque/Main.java
```

### Sur Windows

```cmd
javac -cp lib\sqlite-jdbc-3.45.0.0.jar -d . src\com\bibliotheque\enums\*.java
javac -cp lib\sqlite-jdbc-3.45.0.0.jar -d . src\com\bibliotheque\model\*.java
javac -cp lib\sqlite-jdbc-3.45.0.0.jar -d . src\com\bibliotheque\service\*.java
javac -cp lib\sqlite-jdbc-3.45.0.0.jar -d . src\com\bibliotheque\Main.java
```

---

## 4. LANCEMENT

### Sur Linux / Mac

```bash
java -cp .:lib/sqlite-jdbc-3.45.0.0.jar com.bibliotheque.Main
```

### Sur Windows

```cmd
java -cp .;lib\sqlite-jdbc-3.45.0.0.jar com.bibliotheque.Main
```

> **Note :** La base de données `bibliotheque.db` est créée  
> automatiquement dans le dossier courant au premier lancement.

---

## 5. UTILISATION DU PROGRAMME

### Page d'accueil

```
==========================================================
     BIENVENUE A LA BIBLIOTHEQUE MUNICIPALE
==========================================================
  Vous etes :
  1. Bibliothecaire
  2. Membre
  0. Quitter
```

### Ordre recommandé pour un premier test

Pour tester correctement, suivez cet ordre :

```
1. Espace Bibliothecaire
   → Option 13 : Ajouter une annexe
   → Option 1  : Ajouter un ouvrage
   → Option 4  : Ajouter un exemplaire (pour cet ouvrage et cette annexe)
   → Option 5  : Ajouter un membre
   → Option 7  : Enregistrer un emprunt
   → Option 8  : Enregistrer un retour
   → Option 10 : Voir les amendes
   → Option 11 : Payer une amende
   → Option 12 : Voir le solde de la caisse

2. Espace Membre (numéro de carte créé à l'étape 5)
   → Option 1 : Voir les ouvrages disponibles
   → Option 2 : Voir mes emprunts
   → Option 3 : Voir mes amendes
```

---

## 6. EXPLICATION DE LA CONNEXION JDBC

Le professeur a montré ce style dans le cours :

```java
public class Service {

    public static Connection getConnexion(String URL, String USER, String PASSWORD) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
```

### Différence avec notre projet

Le prof utilise **MySQL** (nécessite un serveur).  
Notre projet utilise **SQLite** (pas de serveur, juste un fichier).

| Élement | MySQL (cours) | SQLite (notre projet) |
|---------|--------------|----------------------|
| Driver | `com.mysql.cj.jdbc.Driver` | `org.sqlite.JDBC` |
| URL | `jdbc:mysql://localhost:3306/db` | `jdbc:sqlite:bibliotheque.db` |
| Serveur | OUI (XAMPP/MySQL) | NON |
| Fichier JAR | mysql-connector.jar | sqlite-jdbc.jar |

### Notre `Service.java`

```java
public static Connection getConnexion() {
    try {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:bibliotheque.db");
        return conn;
    } catch (Exception e) {
        System.out.println("Erreur : " + e.getMessage());
        return null;
    }
}
```

---

## 7. LES 5 OPÉRATIONS JDBC (style du prof)

### Connexion

```java
Connection conn = Service.getConnexion();
```

### INSERT (PreparedStatement)

```java
String sql = "INSERT INTO membres (nom, email) VALUES (?, ?)";
PreparedStatement pst = conn.prepareStatement(sql);
pst.setString(1, "Diallo");
pst.setString(2, "diallo@gmail.com");
pst.executeUpdate();
pst.close();
```

### SELECT (Statement)

```java
String sql = "SELECT * FROM membres";
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql);
while (rs.next()) {
    System.out.println(rs.getString("nom"));
}
rs.close();
stmt.close();
```

### UPDATE (PreparedStatement)

```java
String sql = "UPDATE membres SET nom = ? WHERE id = ?";
PreparedStatement pst = conn.prepareStatement(sql);
pst.setString(1, "Nouveau nom");
pst.setInt(2, 1);
pst.executeUpdate();
pst.close();
```

### DELETE (PreparedStatement)

```java
String sql = "DELETE FROM membres WHERE id = ?";
PreparedStatement pst = conn.prepareStatement(sql);
pst.setInt(1, 1);
pst.executeUpdate();
pst.close();
```

---

## 8. PRINCIPES POO APPLIQUÉS

| Principe | Où dans le code |
|----------|----------------|
| **Encapsulation** | Tous les attributs sont `private`, accès via getters/setters |
| **Héritage** | `Bibliothecaire` et `Membre` héritent de `Personne` |
| **Héritage** | `Livre` hérite de `Ouvrage` |
| **Classe abstraite** | `Personne` et `Ouvrage` sont `abstract` |
| **Méthode abstraite** | `afficherDetails()` dans `Ouvrage`, redéfinie dans `Livre` |
| **Polymorphisme** | Un objet `Ouvrage` peut être un `Livre` |
| **Enum** | `EnumOuvrage`, `EnumEmprunt`, `EnumAmende`, `EnumExemplaire` |
| **Exceptions** | `throws SQLException` dans les services |

---

## 9. ERREURS FRÉQUENTES

| Erreur | Cause | Solution |
|--------|-------|----------|
| `ClassNotFoundException: org.sqlite.JDBC` | JAR manquant dans le classpath | Vérifier `-cp lib/sqlite-jdbc...` |
| `No such table: membres` | Tables pas initialisées | Le programme les crée au 1er lancement |
| `NumberFormatException` | Saisie invalide (lettre à la place d'un chiffre) | Ressaisir un nombre |
| `NullPointerException` sur membre | Numéro de carte inexistant | Créer le membre d'abord (option 5) |

---

## 10. SCRIPT DE COMPILATION RAPIDE

Créez un fichier `compiler.sh` à la racine :

```bash
#!/bin/bash
echo "Compilation en cours..."
javac -cp lib/sqlite-jdbc-3.45.0.0.jar -d . \
  src/com/bibliotheque/enums/*.java \
  src/com/bibliotheque/model/*.java \
  src/com/bibliotheque/service/*.java \
  src/com/bibliotheque/Main.java
echo "Terminé !"
```

Et un fichier `lancer.sh` :

```bash
#!/bin/bash
java -cp .:lib/sqlite-jdbc-3.45.0.0.jar com.bibliotheque.Main
```

Rendre exécutable :

```bash
chmod +x compiler.sh lancer.sh
./compiler.sh
./lancer.sh
```

---

*Projet Bibliothèque Municipale — ESP Dakar — 2025/2026*
