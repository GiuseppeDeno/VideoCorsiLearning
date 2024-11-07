package com.example.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.mail.MessagingException;

@Controller
public class MyControllerCorsi {
	
	@Autowired
    pianiJdbcTemplate d1;
	
	@Autowired
   EmailService emailService; // Aggiungi questa riga per l'iniezione del servizio email poiche non mi prendeva nel /recap
 
	ArrayList<Piano> listaPiani =new ArrayList<>();
	  
	// Lista per memorizzare i piani nel carrello
	 ArrayList<Piano> carrello = new ArrayList<>();
	 
	 //variabile che indica il num di elementi presenti nel carrello
	 int numCarrello=0;
	 
	 //Lista Ordini effettuati
	 ArrayList<OrdinePiano> OrdiniEffettuati = new ArrayList<>();
	 
	 

    @GetMapping("/")
    public String getIndex(Model m1) {
        // Si può automatizzare il titolo della pagina 
        String title = "Home Page";

        listaPiani = d1.getLista();
        m1.addAttribute("lista", listaPiani);
        m1.addAttribute("numCarrello", numCarrello);
        m1.addAttribute("title", title);

        return "index";
    }

    // Iniezione del data base
    @Autowired
    public MyControllerCorsi(pianiJdbcTemplate d1) {
        this.d1 = d1;
        this.listaPiani = d1.getLista();
        
    	LocalDateTime dataOdierna = LocalDateTime.now(); //data odierna
        int i=1;
        
        // popolo l'arrayList degli ordini in modo da poterlo usare per le statistiche
        for (Piano piano : listaPiani) {
   		 

        	for(int j=0; i<piano.getQntVenduti(); j++) {
        		OrdinePiano ordine = new OrdinePiano(); // creo un oggetto ordinePiano (rappresenta il prodotto acquistato nell'ordine
        		
        		ordine.setId(i);
            	ordine.setNome(piano.getNome());
            	ordine.setDescrizione(piano.getDescrizione());
            	ordine.setPrezzo(piano.getPrezzo());
            	ordine.setImg(piano.getImg());
            	ordine.setQntVenduti(piano.getQntVenduti());
            	ordine.setVideo1(piano.getVideo1());
            	ordine.setVideo2(piano.getVideo2());
            	ordine.setVideo3(piano.getVideo3());
            	ordine.setVideo4(piano.getVideo4());
            	ordine.setDataAcquisto(dataOdierna.plusDays(- ((listaPiani.size() )-i)) ); //data odierna meno i (creo date d'acquisto fittizie)
            	
            	OrdiniEffettuati.add(ordine);
            	i++;
        	}
        	
   	 } 

    }

    @GetMapping("/vaiDashboard")
    public String getDashboard(Model m1) {
        listaPiani = d1.getLista();
        
        m1.addAttribute("title", "Dashboard");
        m1.addAttribute("lista", listaPiani);
        m1.addAttribute("numCarrello", numCarrello);
        
        return "dashboard";
    }

    
    //recupero anche la lista dei video 
    @GetMapping("/vaiStore")
    public String getStore(Model m1) {
        listaPiani = d1.getLista();
       
        
        //un array list di video presenti. poiche alcuni recodd video potrebbero essere vuoti
        ArrayList<String> videoLista= new ArrayList<>();
        
        for (Piano piano : listaPiani) {
            if (piano.getVideo1() != null) videoLista.add(piano.getVideo1());
            if (piano.getVideo2() != null) videoLista.add(piano.getVideo2());
            if (piano.getVideo3() != null) videoLista.add(piano.getVideo3());
            if (piano.getVideo4() != null) videoLista.add(piano.getVideo4());
            System.out.println(piano.getNome() + "video caricato");
        }
        
        m1.addAttribute("title", "Store");
        m1.addAttribute("lista", listaPiani);
        m1.addAttribute("videoLista", videoLista);
        m1.addAttribute("numCarrello", numCarrello);
        
        return "store";
    }
    
    /* per debug  faccio apparire i video in una pagina apparte*/
    @GetMapping("/vaiCarosello")
    public String getCarosello(Model m1) {
        listaPiani = d1.getLista();
       
        
        //un array list di video presenti. poiche alcuni recodd video potrebbero essere vuoti
        ArrayList<String> videoLista= new ArrayList<>();
        
        for (Piano piano : listaPiani) {
            if (piano.getVideo1() != null) videoLista.add(piano.getVideo1());
            if (piano.getVideo2() != null) videoLista.add(piano.getVideo2());
            if (piano.getVideo3() != null) videoLista.add(piano.getVideo3());
            if (piano.getVideo4() != null) videoLista.add(piano.getVideo4());
            System.out.println(piano.getNome() + "video caricato");
        }
        
        m1.addAttribute("title", "Carosello");
        m1.addAttribute("lista", listaPiani);
        m1.addAttribute("videoLista", videoLista);
        m1.addAttribute("numCarrello", numCarrello);
        
        return "carosello";
    }
    
    
    
    @GetMapping("/vaiBlog")
    public String getBlog(Model m1) {
    	
    	 m1.addAttribute("title", "Blog");
        m1.addAttribute("numCarrello", numCarrello);
   
        return "blog";
    }
    
    
    @GetMapping("/vaiCarrelloNav")
    public String getCarrello(Model m1) {
    	
        m1.addAttribute("numCarrello", numCarrello);
   
        return"redirect:/funzioneCarrello";
    }


    
    /*  backcend dashboard */
    @PostMapping("/gestioneMagazzino")
    public String getPiano(@RequestParam("nome") String nome, 
    		               @RequestParam("descrizione") String descrizione,
                           @RequestParam("prezzo") Double prezzo,
                           @RequestParam("qntVenduti") int qntVenduti, 
                           @RequestParam("img") String img,
                           @RequestParam("video1") String video1,
                           @RequestParam("video2") String video2,
                           @RequestParam("video3") String video3,
                           @RequestParam("video4") String video4,
                           
                           
                           Model m1) {

        d1.insertPiano(nome, descrizione, prezzo, qntVenduti , img, video1, video2, video3,video4);

        Piano piano1 = new Piano(nome, descrizione, prezzo, qntVenduti, img, video1, video2, video3,video4);
        listaPiani.add(piano1);

        listaPiani = d1.getLista();
        m1.addAttribute("lista", listaPiani);
        m1.addAttribute("numCarrello", numCarrello);

        return "dashboard";
    }

   
    // Commentato per evitare errori
    @PostMapping("/rimuoviDalMagazzino")
    public String rimuoviDalMagazzino(@RequestParam("nome") String nome, Model m1) {
        listaPiani.removeIf(Piano -> Piano.getNome().equals(nome));
        d1.deletePiano(nome);
        m1.addAttribute("lista", listaPiani);
        m1.addAttribute("numCarrello", numCarrello);
        
        return "redirect:/vaiDashboard";
    }
    


    @PostMapping("/rimuoviDalCarrello")
    public String rimuoviDalCarrello(@RequestParam("nome") String nome, Model m1) {
        System.out.println("Rimuovendo dal carrello: " + nome);
        boolean found = false;
        int numRemoved=0;

        if (!found) {
            boolean removed = carrello.removeIf(Piano -> Piano.getNome().equals(nome));
            if (removed) {
                System.out.println(nome + " è stato rimosso dal carrello.");
                numRemoved++;
            } else {
                System.out.println(nome + " non è stato trovato nel carrello.");
            }
        }
        
        numCarrello-=numRemoved;
        return "redirect:/funzioneCarrello";
    }
    
    

    
    /*  FROMT END  INDEX STORE E BLOG  */
      
 // Funzione per stampare il carrello IMPORTANTE 
    @GetMapping("/funzioneCarrello")
    public String stampaCarrello(Model m1) {
        double somma = 0;

        // Usa la lista carrello esistente
        for (Piano piano : carrello) {
            somma += piano.getPrezzo();
        }

        m1.addAttribute("title", "Carrello");
        m1.addAttribute("somma", somma);
        m1.addAttribute("carrello", carrello);
        m1.addAttribute("numCarrello", numCarrello);
        
        return "carrello";
    }

    
 

 

    
    @PostMapping("/aggiungiCarrello")
    public String aggiungiCarrello(@RequestParam("nome") String nome) {
        Piano pianoDaAggiungere = d1.getPianoByNome(nome); // Funzione per ottenere il piano dal DB messa nel pianiJdbcTemplate
        if (pianoDaAggiungere != null) {
            // Verifica se il piano è già nel carrello
            if (!carrello.contains(pianoDaAggiungere)) {
                carrello.add(pianoDaAggiungere);
                System.out.println("Aggiunto piano al carrello: " + pianoDaAggiungere.getNome());
                numCarrello++;
            }
        }
        return "redirect:/funzioneCarrello"; 
    }

    @PostMapping("/confermaAcquisto")
    public String confermaAcquisto(Model m1) {
    	
    	int idOrdine= (OrdiniEffettuati.size()) +1;
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
    	LocalDateTime dataOdierna = LocalDateTime.now();
    	String dataOrdine = dataOdierna.format(formatter);
    	
       double somma = 0.0;
       
        for (Piano piano : carrello) {
        	OrdinePiano ordine = new OrdinePiano(); // creo un oggetto ordinePino (rappresenta il prodotto acquistato nell'ordine
        	
        	d1.updateQntVenduti(1, piano.getNome()); //aggiorno le quantità vendute del prodotto.
        	
        	ordine.setId(idOrdine);
        	ordine.setNome(piano.getNome());
        	ordine.setDescrizione(piano.getDescrizione());
        	ordine.setPrezzo(piano.getPrezzo());
        	ordine.setImg(piano.getImg());
        	ordine.setQntVenduti(piano.getQntVenduti());
        	ordine.setVideo1(piano.getVideo1());
        	ordine.setVideo2(piano.getVideo2());
        	ordine.setVideo3(piano.getVideo3());
        	ordine.setVideo4(piano.getVideo4());
        	
        	OrdiniEffettuati.add(ordine);
        	
            somma += piano.getPrezzo();
        }
        
        

       // una volta effettuato l'acquisto, azzero il carrello        
        carrello.clear();
        numCarrello=0;
        
        m1.addAttribute("somma", somma);
        m1.addAttribute("idOrdine", idOrdine);
        m1.addAttribute("dataOrdine", dataOrdine);
        m1.addAttribute("OrdiniEffettuati", OrdiniEffettuati);
        
        m1.addAttribute("numCarrello", numCarrello);

       
        return "confermaAcquisto";
    }


    @ResponseBody
    @PostMapping("/Recap")
    public String getResoconto(@RequestParam("mail") String mail, Model m1) throws MessagingException {
        double somma = 0;
        for (Piano pianoAcq : carrello) {
            somma += pianoAcq.getPrezzo();
            d1.updateQntVenduti(1, pianoAcq.getNome()); // Aggiorna nel DB IMPORTANTE . metti 1 invece di quantita venduti perche è solo 1 alla volta
        }
        emailService.sendEmailWithImage(mail, "UDENOSTRI - Riepilogo Acquisto", carrello, somma);
        m1.addAttribute("messaggio", "⭐ Congratulazioni per il tuo Acquisto! ⭐");
        
        return "confermato"; 
    }

    
    
    /* API per statistiche  di vendita nella dashboards si usa  @ResponseBody poi @GetMapping e di solito ritorna un arrayList quindi è public arrayList  */
    
    @ResponseBody
     @GetMapping("/getLista") 
    	public ArrayList<Piano> getLista(){
    	
    	ArrayList<Piano> listaVendite= d1.getLista();
        return listaVendite;
    }
    
    

}





/*
@PostMapping("/aggiungiCarrello")
public String compra(@RequestParam("nome") String nome ) {

	boolean found = false;

	for (Piano piano : listaPiani) {
		if (piano.getNome().equals(nome)) {
			for (Piano pnC : carrello) {

				if (pnC.getNome().equals(nome)) {
					found = true;
					break;

				}
			}
			if (!found) {
				Piano pnAcquistato = new Piano(piano.getNome(), piano.getDescrizione(), piano.getPrezzo() , piano.getQntVenduti(), piano.getImg(), 
						piano.getVideo1(), piano.getVideo2(), piano.getVideo3(), piano.getVideo4());
				carrello.add(pnAcquistato);
				System.out.println("aggiunto piano al carrello");
			}

		}
	}

	return "redirect:/funzioneCarrello";

}*/






