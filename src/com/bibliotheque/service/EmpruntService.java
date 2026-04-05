package com.bibliotheque.service;

import com.bibliotheque.model.Exemplaire;
import com.bibliotheque.model.Membre;
import com.bibliotheque.enums.EnumExemplaire;
import java.sql.*;

public class EmpruntService {

    // enregistrer un nouvel emprunt
    public static void emprunter(Connection conn, Membre membre,
            int ouvrageId, String dateEmprunt,
            String dateRetourPrevue) throws SQLException {

        // verifier que le membre peut emprunter
        if (!membre.peutEmprunter()) {
            System.out.println("  Ce membre a atteint son quota d'emprunts !");
            return;
        }

        // trouver un exemplaire disponible
        Exemplaire ex = ExemplaireService.trouverDisponible(conn, ouvrageId);
        if (ex == null) {
            System.out.println("  Aucun exemplaire disponible pour cet ouvrage !");
            return;
        }

        // creer l'emprunt en base
        String sql = "INSERT INTO emprunts (dateEmprunt, dateRetourPrevue, statut, membreId, exemplaireId)" +
                " VALUES (?, ?, 'EN_COURS', ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, dateEmprunt);
        pst.setString(2, dateRetourPrevue);
        pst.setInt(3, membre.getId());
        pst.setInt(4, ex.getId());
        pst.executeUpdate();
        pst.close();

        // marquer l'exemplaire comme emprunte
        ExemplaireService.changerEtat(conn, ex.getId(), EnumExemplaire.EMPRUNTER);

        // reduire le quota du membre
        MembreService.mettreAJourQuota(conn, membre.getId(), membre.getQuotaEmprunt() - 1);

        System.out.println("  Emprunt enregistre avec succes !");
        System.out.println("  Exemplaire #" + ex.getId() + " emprunte jusqu'au " + dateRetourPrevue);
    }

    // enregistrer un retour
    public static void retourner(Connection conn, int empruntId, String dateRetourReelle) throws SQLException {

        // trouver l'emprunt
        String sqlSelect = "SELECT * FROM emprunts WHERE id = ?";
        PreparedStatement pst1 = conn.prepareStatement(sqlSelect);
        pst1.setInt(1, empruntId);
        ResultSet rs = pst1.executeQuery();

        if (!rs.next()) {
            System.out.println("  Emprunt #" + empruntId + " introuvable !");
            rs.close();
            pst1.close();
            return;
        }

        int exemplaireId = rs.getInt("exemplaireId");
        int membreId = rs.getInt("membreId");
        String datePrevue = rs.getString("dateRetourPrevue");
        rs.close();
        pst1.close();

        // determiner le statut (en retard ou rendu)
        String statut = "RENDUE";
        if (dateRetourReelle.compareTo(datePrevue) > 0) {
            statut = "EN_RETARD";
            System.out.println("  RETARD detecte ! Une amende va etre appliquee.");
        }

        // mettre a jour l'emprunt
        String sqlUpdate = "UPDATE emprunts SET dateRetourReelle = ?, statut = ? WHERE id = ?";
        PreparedStatement pst2 = conn.prepareStatement(sqlUpdate);
        pst2.setString(1, dateRetourReelle);
        pst2.setString(2, statut);
        pst2.setInt(3, empruntId);
        pst2.executeUpdate();
        pst2.close();

        // remettre l'exemplaire disponible
        ExemplaireService.changerEtat(conn, exemplaireId, EnumExemplaire.DISPONIBLE);

        // rendre un quota au membre
        Membre m = MembreService.trouverParId(conn, membreId);
        if (m != null) {
            MembreService.mettreAJourQuota(conn, membreId, m.getQuotaEmprunt() + 1);
        }

        System.out.println("  Retour enregistre avec succes ! Statut : " + statut);
    }

    // lister les emprunts en cours
    public static void listerEmpruntsEnCours(Connection conn) throws SQLException {
        String sql = "SELECT e.*, m.prenom, m.nom FROM emprunts e" +
                " JOIN membres m ON m.id = e.membreId" +
                " WHERE e.statut = 'EN_COURS'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Emprunts en cours :");
        boolean vide = true;
        while (rs.next()) {
            vide = false;
            System.out.println("  Emprunt #" + rs.getInt("id")
                    + " | Membre: " + rs.getString("prenom") + " " + rs.getString("nom")
                    + " | Exemplaire #" + rs.getInt("exemplaireId")
                    + " | Retour prevu: " + rs.getString("dateRetourPrevue"));
        }
        if (vide)
            System.out.println("  Aucun emprunt en cours.");
        rs.close();
        stmt.close();
    }

    // lister les emprunts d'un membre
    public static void listerEmpruntsParMembre(Connection conn, int membreId) throws SQLException {
        String sql = "SELECT * FROM emprunts WHERE membreId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, membreId);
        ResultSet rs = pst.executeQuery();
        System.out.println("Mes emprunts :");
        boolean vide = true;
        while (rs.next()) {
            vide = false;
            System.out.println("  #" + rs.getInt("id")
                    + " | Exemplaire #" + rs.getInt("exemplaireId")
                    + " | Emprunt: " + rs.getString("dateEmprunt")
                    + " | Retour prevu: " + rs.getString("dateRetourPrevue")
                    + " | Statut: " + rs.getString("statut"));
        }
        if (vide)
            System.out.println("  Aucun emprunt trouve.");
        rs.close();
        pst.close();
    }
}
