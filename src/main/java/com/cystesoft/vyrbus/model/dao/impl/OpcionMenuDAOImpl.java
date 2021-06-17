package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.OpcionMenu;
import com.cystesoft.vyrbus.model.dao.OpcionMenuDAO;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Util;

/**
 * 
 * @author José Abanto
 *
 */

@SuppressWarnings("unchecked")
public class OpcionMenuDAOImpl extends GenericDAOImpl implements OpcionMenuDAO {

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.OpcionMenuDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<OpcionMenu> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return (ArrayList<OpcionMenu>) super.findByEstadoRegistro(OpcionMenu.class, estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.OpcionMenuDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<OpcionMenu> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<OpcionMenu>) super.findByX(OpcionMenu.class, criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.OpcionMenuDAO#buscarPorId(java.lang.Integer)
	 */
	@Override
	public OpcionMenu buscarPorId(Long id) {
		return (OpcionMenu) super.findById(OpcionMenu.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.OpcionMenuDAO#guardar(com.tepsa.sisvyr.model.bean.OpcionMenu)
	 */
	@Override
	public void guardar(OpcionMenu opcionMenu) {
		super.save(opcionMenu);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.OpcionMenuDAO#actualizar(com.tepsa.sisvyr.model.bean.OpcionMenu)
	 */
	@Override
	public void actualizar(OpcionMenu opcionMenu) {
		super.update(opcionMenu);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.OpcionMenuDAO#inactivar(java.lang.Integer)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(OpcionMenu.class, id);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.OpcionMenuDAO#cargarMenusPadres()
	 */
	@Override
	public List<OpcionMenu> buscaMenusPadres(){
		String sql = "SELECT op.opcmen_id, op.c_denominacion "+ //0-1
					 "FROM vrmopcmen op "+
					 "WHERE op.c_url is null AND op.c_estreg='A' " +
					 "ORDER BY op.n_ordopcmen";
		
		List<?> result = getSession().createSQLQuery(sql).list();
		List<OpcionMenu> lstResult = new ArrayList<OpcionMenu>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			
			OpcionMenu opcionMenu = new OpcionMenu();
			opcionMenu.setId(((BigDecimal) obj[0]).intValue());
			opcionMenu.setDenominacion(obj[1].toString());
			
			lstResult.add(opcionMenu);
		}
		
		return lstResult;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.OpcionMenuDAO#buscarMenus(java.lang.Integer)
	 */
	@Override
	public List<OpcionMenu> buscarMenus(Integer idMenuPadre, Integer idMenu){
		String condi =null;
		if (!(idMenu==null)){
			condi="WHERE op.c_estreg='A' AND op.opcmen_id ="+idMenu;
		}else if (!(idMenuPadre==null)){
			condi="WHERE op.c_estreg='A' AND op.opcmen_idpadre ="+idMenuPadre+" ORDER BY  op.n_ordopcmen";
		}else{
			condi=" WHERE op.c_estreg='A' ORDER BY  om.c_denominacion ";
		}
		
		
		String sql="SELECT op.opcmen_id, om.c_denominacion as menu, op.opcmen_idpadre, op.c_denominacion as subMenu, "+ //0-3
							"op.n_ordopcmen, op.c_nomobj, op.c_url, op.n_habilitado "+ //4-7
					"FROM vrmopcmen op "+
					"LEFT JOIN vrmopcmen om ON (om.opcmen_id=op.opcmen_idpadre) "+
					 condi;
					//"WHERE op.opcmen_idpadre ="+idMenuPadre+
					//" ORDER BY  op.n_ordopcmen";	
		
		List<?> result = getSession().createSQLQuery(sql).list();
		List<OpcionMenu> lstResult = new ArrayList<OpcionMenu>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			
			OpcionMenu opcionMenuPadre = new OpcionMenu();
			OpcionMenu opcionMenuHijo = new OpcionMenu();
			
			if (obj[0] !=null )
				opcionMenuPadre.setId(((BigDecimal) obj[0]).intValue());
			if (obj[1] !=null)
				opcionMenuPadre.setDenominacion(obj[1].toString());
			
			opcionMenuHijo.setOpcionMenuPadre(opcionMenuPadre);
			
			if (obj[2] !=null)		
				opcionMenuHijo.setId(((BigDecimal) obj[2]).intValue());
			if (obj[3] !=null)	
				opcionMenuHijo.setDenominacion(obj[3].toString());
			if (obj[4] !=null)
				opcionMenuHijo.setOrdenOpcionMenu(((BigDecimal) obj[4]).intValue());
			if (obj[5]!= null)
				opcionMenuHijo.setNombreObjeto(obj[5].toString());
			else
				opcionMenuHijo.setNombreObjeto("");
			if (obj[6] !=null)
				opcionMenuHijo.setUrl(obj[6].toString());
			else
				opcionMenuHijo.setUrl("");
			opcionMenuHijo.setHabilitado(((BigDecimal) obj[7]).intValue());
			
			lstResult.add(opcionMenuHijo);
			
		}
		return lstResult;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.OpcionMenuDAO#buscarOpcionesMenu()
	 */
	@Override
	public List<OpcionMenu> buscarOpcionesMenu() throws Exception{
		String sql = "SELECT menus.abuelo, omN1.c_denominacion NIVEL1, menus.idpadre padre, omN2.c_denominacion NIVEL2, menus.opcmen_id, " +
				"menus.c_denominacion, menus.c_nomobj, menus.c_url, menus.n_ordopcmen, menus.c_estreg, " +
				"to_char(menus.audfecins, 'dd/mm/yyyy hh24:mi:ss'), menus.audusuins, menus.audipinse " +
				"FROM(SELECT DECODE(op2.opcmen_idpadre, NULL, op2.opcmen_id, op2.opcmen_idpadre) ABUELO, op2.opcmen_id IDPADRE, op.* " +
				"FROM vrmopcmen op2 , " +
				"(SELECT * FROM vrmopcmen op2 WHERE op2.opcmen_idpadre IS NOT NULL AND op2.c_nomobj IS NOT NULL AND op2.n_habilitado=1) op " +
				"WHERE op2.opcmen_id=op.opcmen_idpadre)menus " +
				"INNER JOIN vrmopcmen omN1 ON omN1.opcmen_id=menus.abuelo " +
				"INNER JOIN vrmopcmen omN2 ON omN2.opcmen_id=menus.IDPADRE " +
				"ORDER BY ABUELO,IDPADRE, /*menus.opcmen_id,*/ menus.n_ordopcmen ";
		
		log.info(sql);
		
		List<?> result = getSession().createSQLQuery(sql).list();
		List<OpcionMenu> lstResult = new ArrayList<OpcionMenu>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			OpcionMenu opcionMenu = new OpcionMenu();
			opcionMenu.setIdPadre(((BigDecimal)obj[0]).intValue());
			opcionMenu.setPadre(obj[1].toString());
			opcionMenu.setIdHijo(((BigDecimal)obj[2]).intValue());
			opcionMenu.setHijo(obj[3].toString());
			opcionMenu.setId(((BigDecimal)obj[4]).intValue());
			opcionMenu.setDenominacion(obj[5].toString());
			opcionMenu.setNombreObjeto(obj[6].toString());
			opcionMenu.setUrl(obj[7].toString());
			opcionMenu.setOrdenOpcionMenu(((BigDecimal)obj[8]).intValue());
			opcionMenu.setEstadoRegistro(obj[9].toString());
			opcionMenu.setFechaInsercion(Util.StringtoDate(obj[10].toString(), Constantes.DATE_TIME_FORMAT));
			opcionMenu.setUsuarioInsercion(obj[11]==null?"":obj[11].toString());
			opcionMenu.setIpInsercion(obj[12]==null?"":obj[12].toString());
			lstResult.add(opcionMenu);
		}
		return lstResult;
	}
}
