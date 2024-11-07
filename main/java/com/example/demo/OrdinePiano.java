package com.example.demo;

import java.time.LocalDateTime;

public class OrdinePiano extends Piano {
	
	private int id;
	private LocalDateTime dataAcquisto = LocalDateTime.now();
	
	public OrdinePiano(int id, LocalDateTime dataAcquisto) {
		super();
		this.id = id;
		this.dataAcquisto = dataAcquisto;
	}
	
	@Override
	public String toString() {
		return "Ordine [id=" + id +", dataAcquisto= "+ dataAcquisto +", nome=" + getNome() + ", descrizione=" + getDescrizione() + ", prezzo=" + getPrezzo() + ", qntVenduti="
				+ getQntVenduti() + ", img=" + getImg() + ", video1=" + getVideo1() + ", video2=" + getVideo2() + ", video3=" + getVideo3()
				+ ", video4=" + getVideo4() + "]";
	}

	public OrdinePiano() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDataAcquisto() {
		return dataAcquisto;
	}

	public void setDataAcquisto(LocalDateTime dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}

	
	

}
