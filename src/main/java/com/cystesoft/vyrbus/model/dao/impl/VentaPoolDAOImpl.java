/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 08/09/2016
 * Hora			: 14:28:03
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.OperadorTarjetaCredito;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.TarjetaCredito;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPool;
import com.cystesoft.vyrbus.model.dao.VentaPoolDAO;

/**
 * @author jabanto
 *
 */
@SuppressWarnings("unchecked")
public class VentaPoolDAOImpl extends GenericDAOImpl implements VentaPoolDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPoolDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<VentaPool> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<VentaPool>) super.findByEstadoRegistro(VentaPool.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPoolDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<VentaPool> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<VentaPool>) super.findByX(VentaPool.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPoolDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public VentaPool buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (VentaPool) super.findById(VentaPool.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPoolDAO#guardar(com.tepsa.sisvyr.model.bean.VentaPool)
	 */
	@Override
	public void guardar(VentaPool ventaPool) {
		// TODO Auto-generated method stub
		super.save(ventaPool);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPoolDAO#actualizar(com.tepsa.sisvyr.model.bean.VentaPool)
	 */
	@Override
	public void actualizar(VentaPool VentaPool) {
		// TODO Auto-generated method stub
		super.update(VentaPool);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPoolDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPoolDAO#buscarUsuarioPorAgencia(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Usuario> buscarUsuarioPorAgencia(Integer idAgencia,String fechaInicio, String fechaFin)throws Exception {
		String hql = "SELECT DISTINCT(vp.usuario.id), u.apellidoPaterno, u.apellidoMaterno, u.nombre, u.login " +
				"FROM VentaPool vp JOIN vp.usuario u WHERE vp.agencia.id="+idAgencia+" AND " +
					"vp.fechaLiquidacion BETWEEN to_date('"+fechaInicio+"', 'dd/mm/yyyy') AND to_date('"+fechaFin+"', 'dd/mm/yyyy') " +
				"ORDER BY u.apellidoPaterno, u.apellidoMaterno, u.nombre";

		log.info(hql);
		List<?> result = getSession().createQuery(hql).list();
		List<Usuario> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			Usuario usuario = new Usuario();
			usuario.setId(Integer.valueOf(obj[0].toString()));
			usuario.setApellidoPaterno(obj[1]==null?"":obj[1].toString());
			usuario.setApellidoMaterno(obj[2]==null?"":obj[2].toString());
			usuario.setNombre(obj[3]==null?"":obj[3].toString());
			usuario.setLogin(obj[4].toString());
			lstResult.add(usuario);
		}
		return lstResult;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPoolDAO#buscarVentas(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<VentaPool> buscarVentas(String fechaInicio, String fechaFin,Integer agenciaId, Integer usuarioId) throws Exception {
		String sql="SELECT vp.c_ope operador, vp.c_numboltepsa boleto, p.c_Apepat, p.c_Apemat,p.c_nombre nombres "
					   + ",vp.d_Fecpar fechaPartida, vp.c_horpar horaPartida, vp.c_Servicio servicio, vp.n_imppag importe "
					   + ",vp.c_estreg, vp.c_ruta, venpool_id, vp.c_numcomope, tc.opetarcre_id  "
			     + "FROM VRTVENPOOL vP "
			     	+ "INNER JOIN VRMPASAJERO p ON (p.pasajero_id=vp.pasajero_id) "
			     	+ "LEFT JOIN VRMTARCRE tc ON (tc.tarcre_id=vp.tarcre_id) "
			     + "WHERE vp.d_Fecliq BETWEEN '"+fechaInicio+"' and '"+fechaFin+"' "
			       + "AND vp.agencia_id=NVL("+agenciaId+", vp.agencia_id) "
			       + "AND vp.usuario_id=NVL("+usuarioId+", vp.usuario_id) "
			     + "ORDER BY  vp.c_numboltepsa";
		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<VentaPool> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			VentaPool ventaPool=new VentaPool();
			ventaPool.setOperador(obj[0].toString());
			ventaPool.setNumeroBoletoTepsa(obj[1].toString());
			Pasajero pasajero= new Pasajero();
			pasajero.setApellidoPaterno(obj[2].toString());
			pasajero.setApellidoMaterno(obj[3]!=null?obj[3].toString():null);
			pasajero.setNombre(obj[4].toString());
			ventaPool.setPasajero(pasajero);
			ventaPool.setFechaPartida((Date)obj[5]);
			ventaPool.setHoraPartida(obj[6].toString());
			ventaPool.setServicio(obj[7].toString());
			ventaPool.setImportePagado(((BigDecimal)obj[8]).doubleValue());
			ventaPool.setEstadoRegistro(obj[9].toString());
			ventaPool.setRuta(obj[10].toString());
			ventaPool.setId(((BigDecimal)obj[11]).longValue());
			ventaPool.setNumeroComprobanteOperador(obj[12].toString());
			if(obj[13]!=null){
				OperadorTarjetaCredito operadorTarjetaCredito= new OperadorTarjetaCredito();
				operadorTarjetaCredito.setId(((BigDecimal)obj[13]).intValue());
				TarjetaCredito tarjetaCredito= new TarjetaCredito();
				tarjetaCredito.setOperadorTarjetaCredito(operadorTarjetaCredito);
				ventaPool.setTarjetaCredito(tarjetaCredito);
			}
			lstResult.add(ventaPool);
		}

		return lstResult;
	}

}
