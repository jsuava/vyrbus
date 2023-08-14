/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: Clase que utilizaremos para mapear la tabla Usuarios con el Hibernate.
 * Autor		: José Sullo Avalos
 * Fecha		: 07/04/2012
 */
package pe.itsb.vyrbus.model.bean;

import java.io.Serializable;
import java.util.List;

import pe.itsb.vyrbus.model.bean.report.RptVentaUsuario;

/**
 * Bean que utilizaremos para mapear la tabla de Usuarios con el XML de <b>Hibernate</b>.
 * @author José Sullo Avalos
 * @since JDK1.6
 */
public class Usuario extends GenericBean implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	public static final int TIPPAS_ALEATORIO = 0;
	public static final int TIPPAS_USUARIO = 1;
	private Integer id;
	private Personal personal;
	private Agencia agencia;
	private UsuarioHardware usuarioHardware;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombre;
	private String codigo;
	private String login;
	private String password;
	private Integer tipoPassword;
	private Integer tipoSeguridad;
	private String emailFuncionario;

	//No mapeados
	private String emailInfo;			// No mapeado.
	private String pwdNormal;			//No mapeado

	//no mapeados(Para el reporte ventas por punto de venta)
	private List<RptVentaUsuario> ventasUsuarios;


	public Usuario(Integer id) {
		super();
		this.id = id;
	}

	/**
	 *
	 */
	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return Objeto id.
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id	: Setea el objeto id.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario(String login){
		this.login=login;
	}
	/**
	 * @return Objeto personal.
	 */
	public Personal getPersonal() {
		return personal;
	}
	/**
	 * @param personal	: Setea el objeto personal.
	 */
	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

	/**
	 * @return the agencia
	 */
	public Agencia getAgencia() {
		return agencia;
	}
	/**
	 * @param agencia the agencia to set
	 */
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	/**
	 * @return the usuarioHardware
	 */
	public UsuarioHardware getUsuarioHardware() {
		return usuarioHardware;
	}
	/**
	 * @param usuarioHardware the usuarioHardware to set
	 */
	public void setUsuarioHardware(UsuarioHardware usuarioHardware) {
		this.usuarioHardware = usuarioHardware;
	}

	/**
	 * @return Objeto apellidoPaterno.
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	/**
	 * @param apellidoPaterno	: Setea el objeto apellidoPaterno.
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * @return Objeto apellidoMaterno.
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	/**
	 * @param apellidoMaterno	: Setea el objeto apellidoMaterno.
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	/**
	 * @return Objeto nombre.
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre	: Setea el objeto nombre.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Objeto codigo.
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo	: Setea el objeto codigo.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return Objeto login.
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login	: Setea el objeto login.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return Objeto password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password	: Setea el objeto password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the tipoPassword
	 */
	public Integer getTipoPassword() {
		return tipoPassword;
	}
	/**
	 * @param tipoPassword the tipoPassword to set
	 */
	public void setTipoPassword(Integer tipoPassword) {
		this.tipoPassword = tipoPassword;
	}

	/**
	 * @return the tipoSeguridad
	 */
	public Integer getTipoSeguridad() {
		return tipoSeguridad;
	}
	/**
	 * @param tipoSeguridad the tipoSeguridad to set
	 */
	public void setTipoSeguridad(Integer tipoSeguridad) {
		this.tipoSeguridad = tipoSeguridad;
	}

	/**
	 * @return the emailFuncionario
	 */
	public String getEmailFuncionario() {
		return emailFuncionario;
	}
	/**
	 * @param emailFuncionario the emailFuncionario to set
	 */
	public void setEmailFuncionario(String emailFuncionario) {
		this.emailFuncionario = emailFuncionario;
	}

	/**
	 * @return the emailInfo
	 */
	public String getEmailInfo() {
		return emailInfo;
	}
	/**
	 * @param emailInfo the emailInfo to set
	 */
	public void setEmailInfo(String emailInfo) {
		this.emailInfo = emailInfo;
	}

	/**
	 * @return the pwdNormal
	 */
	public String getPwdNormal() {
		return pwdNormal;
	}
	/**
	 * @param pwdNormal the pwdNormal to set
	 */
	public void setPwdNormal(String pwdNormal) {
		this.pwdNormal = pwdNormal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return apellidoPaterno + (apellidoMaterno==null?"":" " + apellidoMaterno) + ", " + nombre;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/**
	 * @return the ventasUsuarios
	 */
	public List<RptVentaUsuario> getVentasUsuarios() {
		return ventasUsuarios;
	}

	/**
	 * @param ventasUsuarios the ventasUsuarios to set
	 */
	public void setVentasUsuarios(List<RptVentaUsuario> ventasUsuarios) {
		this.ventasUsuarios = ventasUsuarios;
	}

}
