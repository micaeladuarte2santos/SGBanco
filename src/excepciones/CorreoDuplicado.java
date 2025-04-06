package excepciones;

import java.io.IOException;

public class CorreoDuplicado extends IOException{
	

    public CorreoDuplicado() {
        super();
    }
	@Override
	public String getMessage() {
		return "Este correo ya fue ingresado";
	}
	
}
