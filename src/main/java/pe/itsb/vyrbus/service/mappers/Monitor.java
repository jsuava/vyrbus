package pe.itsb.vyrbus.service.mappers;

public class Monitor extends ElementoBus{
	private static final long serialVersionUID = 1L;

	public Monitor() {
		super("resources/mapa/bus_tv.png");
	}

	public Monitor(String src) {
		super(src);
	}
}