# Système de Gestion d'une Bibliothèque Municipale

Projet Java POO — ESP/UCAD  
Professeur : Pr. Samba DIAW

---

## Description

Programme Java orienté objet qui simule la gestion d'une bibliothèque municipale :
- Catalogue d'ouvrages et exemplaires
- Inscription et gestion des membres
- Emprunts et retours
- Amendes et caisse
- Annexes et livraisons
- Conditions de conservation

---

## Technologies

- Java (POO)
- JDBC + MySQL
- Concepts : héritage, classes abstraites, interfaces, exceptions, énumérations

---

## Structure

```
bibliotheque/
├── src/com/bibliotheque/
│   ├── Main.java
│   ├── enums/        (EnumOuvrage, EnumEmprunt, EnumAmende, EnumExemplaire)
│   ├── model/        (Personne, Membre, Bibliothecaire, Livre, Exemplaire...)
│   └── service/      (MembreService, EmpruntService, AmendeService...)
├── bibliotheque.sql
└── README.md
```

---

## Installation et lancement

**1. Démarrer MySQL et créer la base :**
```bash
sudo service mysql start
mysql -u root -p < bibliotheque.sql
```

**2. Modifier le mot de passe dans `Main.java` (ligne 19) :**
```java
static final String PASSWORD = "votre_mot_de_passe";
```

**3. Compiler :**
```bash
javac -cp ../lib/mysql-connector-j-8.3.0.jar -d . \
  src/com/bibliotheque/enums/*.java \
  src/com/bibliotheque/model/*.java \
  src/com/bibliotheque/service/*.java \
  src/com/bibliotheque/Main.java
```

**4. Exécuter :**
```bash
java -cp .:../lib/mysql-connector-j-8.3.0.jar com.bibliotheque.Main
```

---

## Scénario simulé

Le `main` simule automatiquement :

1. Enregistrement d'un bibliothécaire
2. Création de 2 annexes
3. Ajout de 2 véhicules
4. Ajout de 3 ouvrages au catalogue
5. Ajout de 5 exemplaires
6. Inscription de 3 membres
7. 5 emprunts (dont test de dépassement de quota)
8. Retour à temps (Fatou) — aucune amende
9. Retour en retard (Oumar) — amende de 5 000 FCFA
10. Paiement de l'amende — caisse mise à jour
11. Livraison entre annexes
12. Relevés de stockage (normal + alerte)
13. Bilan final complet

---

Collaborateurs
https://github.com/Hissein8         (Hissein Mahamat Issa)
https://github.com/sg5324127-oss    (Pape Souleye Gassama)
https://github.com/K4izen404        (Mamadou Dia)

*ESP Dakar — 2025/2026*
