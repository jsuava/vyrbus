package pe.itsb.vyrbus.service.mappers;

public class ServiciosHigienicos extends ElementoBus{
	private static final long serialVersionUID = 1L;

	public ServiciosHigienicos() {
		super("resources/mapa/bus_wc.png");
	}

	public ServiciosHigienicos(String src) {
		super(src);
	}
}
