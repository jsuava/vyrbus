/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Cliente;
import com.cystesoft.vyrbus.model.bean.LineaCreditoCliente;
import com.cystesoft.vyrbus.model.bean.Ubigeo;
import com.cystesoft.vyrbus.model.dao.ClienteDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class ClienteDAOImpl extends GenericDAOImpl implements ClienteDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ClienteDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<Cliente> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<Cliente>) super.findByEstadoRegistro(Cliente.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ClienteDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<Cliente> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Cliente>) super.findByX(Cliente.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ClienteDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Cliente buscarPorId(Long id) {
		return (Cliente) super.findById(Cliente.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ClienteDAO#guardar(com.tepsa.sisvyr.domain.Cliente)
	 */
	@Override
	public void guardar(Cliente cliente) {
		super.save(cliente);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ClienteDAO#actualizar(com.tepsa.sisvyr.domain.Cliente)
	 */
	@Override
	public void actualizar(Cliente cliente) {
		super.update(cliente);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.ClienteDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(Cliente.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ClienteDAO#cargaClientesSolicitud()
	 */
	@Override
	public List<Cliente> cargaClientesSolicitud(){

		String sql="SELECT DISTINCT(s.cliente_id), c.c_numdoc, c.c_razsoc "+
					"FROM vrtsolcar s "+
					"INNER JOIN vrmcliente  c ON (c.cliente_id=s.cliente_id) " +
					"ORDER BY c.c_razsoc ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<Cliente> listResul= new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			Cliente cliente=new Cliente();

			cliente.setId(((BigDecimal)obj[0]).longValue());
			cliente.setNumeroDocumento(obj[1].toString());
			cliente.setRazonSocial(obj[2].toString());

			listResul.add(cliente);
		}

		return listResul;

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ClienteDAO#buscarporFullTextIndex(java.lang.String[])
	 */
	@Override
	public ArrayList<Cliente> buscarPorRazonSocial(String[] razonSocial) throws Exception {
		String criterio = "";
		for(String valor : razonSocial){
			criterio = (criterio.equals("")?"":(criterio+" & ")) + valor+"%";
		}

//		String sql = "SELECT * FROM vrmcliente " +
//				"WHERE CATSEARCH(c_razsoc,'"+criterio+"', null)>0 AND c_estreg='"+Constantes.VALUE_ACTIVO+"' AND ROWNUM<=1000 " +
//						"ORDER BY c_razsoc";


		String sql = "SELECT cliente_id, c_numdoc,c_razsoc,c_contacto FROM vrmcliente " +
				"WHERE CATSEARCH(c_razsoc,'"+criterio+"', null)>0 AND c_estreg='"+Constantes.VALUE_ACTIVO+"' AND ROWNUM<=1000 " +
						"ORDER BY c_razsoc";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		ArrayList<Cliente> lstResult = new ArrayList<>();
		for (Object element : result) {
			Object[] obj = (Object[])element;
			Cliente cliente = new Cliente();
			cliente.setId(((BigDecimal)obj[0]).longValue());

			cliente.setNumeroDocumento(obj[1].toString());
			cliente.setRazonSocial(obj[2].toString());
			cliente.setContacto(obj[3]==null?null:obj[3].toString());

//			cliente.setNumeroDocumento(obj[3].toString());
//			cliente.setRazonSocial(obj[4].toString());
//			cliente.setContacto(obj[9]==null?null:obj[9].toString());
			lstResult.add(cliente);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ClienteDAO#buscarCliente_ServicioEspecial(java.lang.String)
	 */
	@Override
	public Cliente buscarCliente_ServicioEspecial(String Ruc){
		String sql="SELECT c.cliente_id, c.c_razsoc, NVL(lsc.n_descbaja,0) as Descuento "+
				   "FROM vrmcliente c "+
					"LEFT OUTER JOIN (SELECT sc.n_descalta, sc.n_descbaja, cl.cliente_id FROM vrtcarcli cl "+
					                 "INNER JOIN vrtsolcar sc ON (sc.solcar_id=cl.solcar_id) "+
					                 "WHERE cl.c_estcar='"+Constantes.ESTADOSOL_ACTIVA+"' "+
					                ")lsc ON (lsc.cliente_id=c.cliente_id)"+
					"WHERE c.c_numdoc='"+Ruc+"' AND c_estReg='"+Constantes.VALUE_ACTIVO+"' ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		Cliente cliente=null;
		for (Object element : result) {
			Object[] obj = (Object[])element;
			cliente = new Cliente();
			cliente.setId(((BigDecimal)obj[0]).longValue());
			cliente.setRazonSocial(obj[1].toString());
			cliente.setDescuento(((BigDecimal)obj[2]).doubleValue());

		}
		return cliente;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ClienteDAO#buscarClienteAgencia(java.lang.String)
	 */
	@Override
	public List<Cliente> buscarClienteAgencia(String ruc)throws Exception{
		String sql = "SELECT c.cliente_id, c.c_razsoc, c.c_numdoc, c.c_direccion, c.ubigeo_id, u.c_nombreubigeo, lcc.n_lincreapro, lcc.n_sobregiro, " +
				"lcc.d_fecacti, lcc.d_fecsus, lcc.c_estlincre, lcc.lincrecli_id " +
				"FROM vrmcliente c " +
				"INNER JOIN vrtcarcli cc ON cc.cliente_id=c.cliente_id " +
				"INNER JOIN vrtlincrecli lcc ON lcc.carcli_id=cc.carcli_id " +
				"INNER JOIN vrmubigeo u ON u.ubigeo_id=c.ubigeo_id " +
				"WHERE c.c_numdoc = '"+ruc+"'";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		ArrayList<Cliente> lstResult = new ArrayList<>();
		for (Object element : result) {
			Object[] obj = (Object[])element;
			Cliente cliente = new Cliente();
			cliente.setId(((BigDecimal)obj[0]).longValue());
			cliente.setRazonSocial(obj[1].toString());
			cliente.setNumeroDocumento(obj[2].toString());
			cliente.setDireccion(obj[3].toString());
			Ubigeo ubigeo = new Ubigeo();
			ubigeo.setId(obj[4].toString());
			ubigeo.setCodigoDepartamento(obj[4].toString().substring(0, 2));
			ubigeo.setCodigoProvincia(obj[4].toString().substring(2,4));
			ubigeo.setCodigoDistrito(obj[4].toString().substring(4));
			ubigeo.setNombreUbigeo(obj[5].toString());
			cliente.setUbigeo(ubigeo);
			LineaCreditoCliente lineaCreditoCliente = new LineaCreditoCliente();
			lineaCreditoCliente.setLineaCreditoAprobada(((BigDecimal)obj[6]).doubleValue());
			lineaCreditoCliente.setSobregiro(((BigDecimal)obj[7]).doubleValue());
			lineaCreditoCliente.setFechaActivacion((Date)obj[8]);
			lineaCreditoCliente.setFechaSuspension(obj[9]==null?null:(Date)obj[9]);
			lineaCreditoCliente.setEstadoLineaCredito(obj[10].toString());
			lineaCreditoCliente.setId(((BigDecimal)obj[11]).longValue());
			cliente.setLineaCreditoCliente(lineaCreditoCliente);
			lstResult.add(cliente);
		}
		return lstResult;
	}
}