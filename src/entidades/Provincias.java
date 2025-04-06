package entidades;

public class Provincias {
	String idProvincia;
	String NombreProvincia;
	public String getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
	public String getNombreProvincia() {
		return NombreProvincia;
	}
	public void setNombreProvincia(String nombreProvincia) {
		NombreProvincia = nombreProvincia;
	}
	@Override
	public String toString() {
		return "Provincias [idProvincia=" + idProvincia + ", NombreProvincia=" + NombreProvincia + "]";
	}
}
