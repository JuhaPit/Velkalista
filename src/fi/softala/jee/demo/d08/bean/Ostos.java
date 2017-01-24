package fi.softala.jee.demo.d08.bean;

public class Ostos {

	private int id;
	private String ostaja;
	private String pvm;
	private String summa;

	public Ostos() {
		super();
	}

	public Ostos(int id, String ostaja, String pvm, String summa) {
		super();
		this.id = id;
		this.ostaja = ostaja;
		this.pvm = pvm;
		this.summa = summa;
	}

	public Ostos(String ostaja, String pvm, String summa) {
		super();
		this.ostaja = ostaja;
		this.pvm = pvm;
		this.summa = summa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOstaja() {
		return ostaja;
	}

	public void setOstaja(String ostaja) {
		this.ostaja = ostaja;
	}

	public String getPvm() {
		return pvm;
	}

	public void setPvm(String pvm) {
		this.pvm = pvm;
	}

	public String getSumma() {
		return summa;
	}

	public void setSumma(String summa) {
		this.summa = summa;
	}

	@Override
	public String toString() {
		return "Ostos [id=" + id + ", ostaja=" + ostaja + ", pvm=" + pvm
				+ ", summa=" + summa + "]";
	}

}
