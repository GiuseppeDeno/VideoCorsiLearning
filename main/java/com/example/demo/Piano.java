package com.example.demo;

public class Piano {
	
	
	
	private String nome;
	private String descrizione;
	private Double prezzo;

	private int qntVenduti;
	private String img;
	private String video1;
	private String video2;
	private String video3;
	private String video4;
	
	public Piano() {
		
	}
	public Piano(String nome, String descrizione, Double prezzo, int qntVenduti, String img, String video1,
			String video2, String video3, String video4) {
		super();
		this.nome = nome;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.qntVenduti = qntVenduti;
		this.img = img;
		this.video1 = video1;
		this.video2 = video2;
		this.video3 = video3;
		this.video4 = video4;
	}
	@Override
	public String toString() {
		return "Piano [nome=" + nome + ", descrizione=" + descrizione + ", prezzo=" + prezzo + ", qntVenduti="
				+ qntVenduti + ", img=" + img + ", video1=" + video1 + ", video2=" + video2 + ", video3=" + video3
				+ ", video4=" + video4 + "]";
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}
	public int getQntVenduti() {
		return qntVenduti;
	}
	public void setQntVenduti(int qntVenduti) {
		this.qntVenduti = qntVenduti;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getVideo1() {
		return video1;
	}
	public void setVideo1(String video1) {
		this.video1 = video1;
	}
	public String getVideo2() {
		return video2;
	}
	public void setVideo2(String video2) {
		this.video2 = video2;
	}
	public String getVideo3() {
		return video3;
	}
	public void setVideo3(String video3) {
		this.video3 = video3;
	}
	public String getVideo4() {
		return video4;
	}
	public void setVideo4(String video4) {
		this.video4 = video4;
	}
	
	

}
