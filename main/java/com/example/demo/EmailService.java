package com.example.demo;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;

@Service
public class EmailService {

	
	@Autowired
	private JavaMailSender mailSender;
	//serve usare LIST che p la lista dei pc comprati

	public  void sendEmailWithImage(String to, String subject, ArrayList<Piano> pianoList, double totalAmount) throws MessagingException {
	    // Crea un nuovo messaggio MIME
	    MimeMessage mimeMessage = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

	    
	    helper.setTo(to);
	    helper.setSubject(subject);
	    helper.setFrom("giuseppedeno@gmail.com");

	    // Inizio del contenuto HTML dell'email
	    StringBuilder htmlContent = new StringBuilder("<html><body>");
	    htmlContent.append("<h3>Da adesso potrai iniziare il tuo percorso di apprendimento :</h3>");
	    htmlContent.append("<table style='border-collapse: collapse; width: 100%;'>")
	               .append("<tr>")
	               .append("<th style='border: 1px solid #ddd; padding: 8px;'>Nome</th>")
	               .append("<th style='border: 1px solid #ddd; padding: 8px;'>Descrizione</th>")
	               .append("<th style='border: 1px solid #ddd; padding: 8px;'>Prezzo</th>")
	              
	               .append("<th style='border: 1px solid #ddd; padding: 8px;'>Immagine</th>")
	               .append("</tr>");

	  
	    for (Piano piano : pianoList) {
	        htmlContent.append("<tr>")
	                   .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(piano.getNome()).append("</td>")
	                   .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(piano.getDescrizione()).append("</td>")
	                   .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(piano.getPrezzo()).append(" euro</td>")
	                  
	                   .append("<td style='border: 1px solid #ddd; padding: 8px;'><img src='").append(piano.getImg())
	                   .append("' style='width: 100px; height: 80px;'></td>")
	                   .append("</tr>");
	    }

	   
	    htmlContent.append("</table>");
	    htmlContent.append("<p><strong>Totale da pagare: </strong>").append(totalAmount).append(" euro</p>");
	    htmlContent.append("</body></html>");

	   
	    helper.setText(htmlContent.toString(), true);
	    
	    // Invia l'email
	    mailSender.send(mimeMessage);
	}



    
}