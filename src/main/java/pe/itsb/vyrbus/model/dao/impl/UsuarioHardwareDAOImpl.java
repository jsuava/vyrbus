package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.UsuarioHardware;
import pe.itsb.vyrbus.model.dao.UsuarioHardwareDAO;

@SuppressWarnings("unchecked")
public class UsuarioHardwareDAOImpl extends GenericDAOImpl implements UsuarioHardwareDAO{

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioHardwareDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<UsuarioHardware> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return (ArrayList<UsuarioHardware>) super.findByEstadoRegistro(UsuarioHardware.class,estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioHardwareDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<UsuarioHardware> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<UsuarioHardware>) super.findByX(UsuarioHardware.class, criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioHardwareDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public UsuarioHardware buscarPorId(Long id) {
		return (UsuarioHardware) super.findById(UsuarioHardware.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioHardwareDAO#guardar(com.tepsa.sisvyr.model.bean.UsuarioHardware)
	 */
	@Override
	public void guardar(UsuarioHardware usuarioHardware) throws Exception {
		super.save(usuarioHardware);

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioHardwareDAO#actualizar(com.tepsa.sisvyr.model.bean.UsuarioHardware)
	 */
	@Override
	public void actualizar(UsuarioHardware usuarioHardware) throws Exception {
		super.update(usuarioHardware);

	}


	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioHardwareDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(UsuarioHardware.class, id);

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.UsuarioHardwareDAO#BuscarXCodigo(java.lang.String)
	 */
	@Override
	public UsuarioHardware buscarXCodigo(String codigo) throws Exception {
		Class<?> oClass;
		oClass=UsuarioHardware.class;
		String hql = "FROM " + oClass.getSimpleName() + " WHERE  estadoRegistro='A' AND codigo = ? ";
		List<UsuarioHardware> list= getSession().createQuery(hql).setString(0, codigo).list();

		UsuarioHardware usuarioHardware = new UsuarioHardware();
		if (list.size()>0)
			usuarioHardware=list.get(0);

		return usuarioHardware;
	}


}
