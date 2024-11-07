package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class pianiJdbcTemplate {

    // Oggetto JdbcTemplate per eseguire query e update sul database.
    private JdbcTemplate jdbcTemplateObject;

    /*
     * Metodo per iniettare l'istanza di JdbcTemplate nella classe.
     * @Autowired indica a Spring di fornire automaticamente un'istanza di JdbcTemplate.
     * Autowired in Spring indica che un’istanza di JdbcTemplate deve essere iniettata automaticamente.
     * In questo caso, Spring creerà automaticamente un'istanza di JdbcTemplate (se è configurato correttamente)
     * e la fornirà al setter setJdbcTemplateObject.
     */
    @Autowired
    public void setJdbcTemplateObject(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    /*
     * Metodo per inserire un nuovo pc nel database.
     * Prende in input i valori necessari per la tabella e restituisce un intero,
     * che indica il numero di righe modificate (inserite).
     */
    public int insertPiano(String nome, String descrizione, double prezzo,  int qntVenduti ,String img, String video1, String video2,String video3, String video4) {
        String query = "INSERT INTO pianiabbonamento (nome, descrizione, prezzo, qntVenduti,img,video1,video2,video3,video4) VALUES (?, ?, ?, ?,?,?,?,?,?)";
        return jdbcTemplateObject.update(query, nome, descrizione, prezzo, qntVenduti ,img , video1,video2,video3,video4);
    }

    // Per le eventuale seconda tabella che contiene i video faremo un insert video 

    /*
     * Metodo per eliminare un PIANO ABBONAMENTO dal database in base al nome.
     * Restituisce il numero di righe eliminate.
     */
    public int deletePiano(String nome) {
        String query = "DELETE FROM pianiabbonamento WHERE nome = ?";
        return jdbcTemplateObject.update(query, nome);
    }

    /*
     * Metodo per ottenere una lista di tutti i pc presenti nel database.
     * Utilizza un ResultSetExtractor per convertire il ResultSet in un ArrayList di oggetti pc.
     */
    public ArrayList<Piano> getLista() {
        String query = "SELECT * FROM pianiAbbonamento";

        return jdbcTemplateObject.query(query, new ResultSetExtractor<ArrayList<Piano>>() {
            @Override
            public ArrayList<Piano> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<Piano> listaPiani = new ArrayList<>();

                // Itera sui risultati della query e crea un nuovo oggetto Piano per ciascun record.
                while (rs.next()) {
                    Piano piano = new Piano();
                    piano.setNome(rs.getString("nome"));
                    piano.setDescrizione(rs.getString("descrizione"));
                    piano.setPrezzo(rs.getDouble("prezzo"));
                    piano.setQntVenduti(rs.getInt("qntVenduti"));
                    piano.setImg(rs.getString("img")); 
                    piano.setVideo1(rs.getString("video1")); 
                    piano.setVideo2(rs.getString("video2")); 
                    piano.setVideo3(rs.getString("video3")); 
                    piano.setVideo4(rs.getString("video4")); 

                    listaPiani.add(piano);
                }

                return listaPiani;
            }
        });
    }

    /*
     * Metodo per fare update delle quantità di abbonamento vendute in base al numero .
     */
    public int updateQntVenduti(int num, String nome) {
        String query = "UPDATE pianiabbonamento SET qntVenduti = qntVenduti + ? WHERE nome = ?";
        System.out.println("db   aggiornato");
        return jdbcTemplateObject.update(query, num, nome);
        
    }
    
    
    @SuppressWarnings("deprecation")
	public Piano getPianoByNome(String nome) {
        String query = "SELECT * FROM pianiabbonamento WHERE nome = ?";

        return jdbcTemplateObject.query(query, new Object[]{nome}, new ResultSetExtractor<Piano>() {
            @Override
            public Piano extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    Piano piano = new Piano();
                    piano.setNome(rs.getString("nome"));
                    piano.setDescrizione(rs.getString("descrizione"));
                    piano.setPrezzo(rs.getDouble("prezzo"));
                    piano.setQntVenduti(rs.getInt("qntVenduti"));
                    piano.setImg(rs.getString("img"));
                    piano.setVideo1(rs.getString("video1"));
                    piano.setVideo2(rs.getString("video2"));
                    piano.setVideo3(rs.getString("video3"));
                    piano.setVideo4(rs.getString("video4"));
                    return piano;
                }
                return null; // Se non ci sono risultati, ritorna null
            }
        });
    }
}