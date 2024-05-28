/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 may. 2024
 * Hora			: 22:14:40
 */
package pe.itsb.vyrbus.service.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.Executions;

import pe.itsb.vyrbus.model.bean.TarifaByAsiento;
import pe.itsb.vyrbus.model.bean.TarifaByAsientoDetalle;
import pe.itsb.vyrbus.model.bean.TarifaByAsientoPlantillaEncabezado;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.dao.TarifaByAsientoDAO;
import pe.itsb.vyrbus.model.dao.TarifaByAsientoDetalleDAO;
import pe.itsb.vyrbus.service.business.TarifaByAsientoManager;
import pe.itsb.vyrbus.service.util.Constantes;
import pe.itsb.vyrbus.service.util.UtilData;


/**
 * @author abant
 *
 */
public class TarifaByAsientoManagerImpl  implements TarifaByAsientoManager{
	private TarifaByAsientoDAO tarifaByAsientoDAO;
	private TarifaByAsientoDetalleDAO tarifaByAsientoDetalleDAO;

	/**
	 * @return the tarifaDAO
	 */
	public TarifaByAsientoDAO getTarifaByAsientoDAO() {
		return tarifaByAsientoDAO;
	}

	/**
	 * @param tarifaByAsientoDAO the tarifaDAO to set
	 */
	public void setTarifaByAsientoDAO(TarifaByAsientoDAO tarifaByAsientoDAO) {
		this.tarifaByAsientoDAO = tarifaByAsientoDAO;
	}
	
	/**
	 * @return the tarifaDetalleDAO
	 */
	public TarifaByAsientoDetalleDAO getTarifaByAsientoDetalleDAO() {
		return tarifaByAsientoDetalleDAO;
	}

	/**
	 * @param tarifaByAsientoDetalleDAO the tarifaDetalleDAO to set
	 */
	public void setTarifaByAsientoDetalleDAO(TarifaByAsientoDetalleDAO tarifaByAsientoDetalleDAO) {
		this.tarifaByAsientoDetalleDAO = tarifaByAsientoDetalleDAO;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaManager#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<TarifaByAsiento> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return getTarifaByAsientoDAO().buscarPorEstadoRegistro(estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaByAsientoManager#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TarifaByAsiento> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return getTarifaByAsientoDAO().buscarPorX(criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaByAsientoManager#buscarPorId(java.lang.Long)
	 */
	@Override
	public TarifaByAsiento buscarPorId(Long id) {
		return getTarifaByAsientoDAO().buscarPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaByAsientoManager#guardar(com.tepsa.sisvyr.model.bean.TarifaByAsiento)
	 */
	@Transactional
	@Override
	public void guardar(TarifaByAsiento tarifaByAsiento) {
		getTarifaByAsientoDAO().guardar(tarifaByAsiento);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaManager#actualizar(com.tepsa.sisvyr.model.bean.Tarifa)
	 */
	@Transactional
	@Override
	public void actualizar(TarifaByAsiento tarifaByAsiento) {
		getTarifaByAsientoDAO().actualizar(tarifaByAsiento);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaManager#guardar(java.util.List)
	 */
	@Transactional
	@Override
	public int guardar(List<TarifaByAsiento> listTarifasByAsiento, TarifaByAsientoPlantillaEncabezado  tarifaPlantillaEncabezado, boolean isReemplazarPromos) throws Exception {
		Usuario usuario=(Usuario) Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
		int isCorrec=Constantes.FAILURE;		
		int i=0;
		
		/**Creancion de tarifas Regulares*/
		if(tarifaPlantillaEncabezado.getTipoPrecio().getId().intValue()==Constantes.ID_TIPOPRECIO_REGULAR){
			for(TarifaByAsiento tarifa: listTarifasByAsiento){
				i++;
				
				//Valida si la taria existe
				TarifaByAsiento _tarifa = buscarTarifaByAsientoByRutaServicio(Constantes.FORMAT_DATE.format(tarifa.getFecha()), tarifa.getRuta().getId(), tarifa.getServicio().getId());
				if(_tarifa==null)
					getTarifaByAsientoDAO().save(tarifa);
				else{
					_tarifa.setListTarifaByAsientoDetalle(tarifa.getListTarifaByAsientoDetalle());	
					tarifa=_tarifa;
				
					//Anula todas las tarifas regulares creados
					List<TarifaByAsientoDetalle>tarifasDetalleAnular=getTarifaByAsientoDetalleDAO().buscarByTarifaId(tarifa.getId(), null, Constantes.ID_TIPOPRECIO_REGULAR);
					for(TarifaByAsientoDetalle tarifaDetalleAnular: tarifasDetalleAnular){
						tarifaDetalleAnular.setEstadoRegistro(Constantes.VALUE_INACTIVO);
						UtilData.auditarRegistro(tarifaDetalleAnular, true, usuario, Executions.getCurrent());
						getTarifaByAsientoDetalleDAO().actualizar(tarifaDetalleAnular);
					}
				}
				
				//Inserta el detalle de las tarifas, con las nuevas tarifas
				for(TarifaByAsientoDetalle tarifaDetalle: tarifa.getListTarifaByAsientoDetalle()){
					tarifaDetalle.setTarifaByAsiento(tarifa);
					getTarifaByAsientoDetalleDAO().guardar(tarifaDetalle); 
				}	
				
				//vaciar un lote de inserciones y liberar memoria (solo cual una insercion masiba)
				 if (i % 20 == 0) { //20, same as the JDBC batch size 			      
				     getTarifaByAsientoDAO().clearMemory();
				 } 
			}
		}else if (tarifaPlantillaEncabezado.getTipoPrecio().getId().intValue()==Constantes.ID_TIPOPRECIO_PROMOCIONAL){
			/**Creacion de tarifas con asientos promocionales*/			
			for(TarifaByAsiento tarifa: listTarifasByAsiento){
				//Busca la tarifa, para el servicio y ruta
				TarifaByAsiento oTarifa = buscarTarifaByAsientoByRutaServicio(Constantes.FORMAT_DATE.format(tarifa.getFecha()), tarifa.getRuta().getId(), tarifa.getServicio().getId());
				if(oTarifa!=null){
					i++;
					
					//Valida si primero debe anular todas las tarifas promocionales existenetes
					if(isReemplazarPromos){
						List<TarifaByAsientoDetalle>tarifasDetallePromoAnular=getTarifaByAsientoDetalleDAO().buscarByTarifaId(oTarifa.getId(), null, Constantes.ID_TIPOPRECIO_PROMOCIONAL);
						for(TarifaByAsientoDetalle tarifaDetallePromoAnular: tarifasDetallePromoAnular){
							tarifaDetallePromoAnular.setEstadoRegistro(Constantes.VALUE_INACTIVO);
							UtilData.auditarRegistro(tarifaDetallePromoAnular, true, usuario, Executions.getCurrent());
							getTarifaByAsientoDetalleDAO().actualizar(tarifaDetallePromoAnular);
						}						
					}
					
					//Inserta el detalle de las tarifas, con las nuevas tarifas promocionales					
					for(TarifaByAsientoDetalle tarifaDetalle: tarifa.getListTarifaByAsientoDetalle()){
						tarifaDetalle.setTarifaByAsiento(oTarifa);
						getTarifaByAsientoDetalleDAO().guardar(tarifaDetalle);
					}	
					
					//vaciar un lote de inserciones y liberar memoria (solo cual una insercion masiba) - esto ajiliza la insercion
					 if (i % 20 == 0) { //20, same as the JDBC batch size 			      
					     getTarifaByAsientoDAO().clearMemory();
					 }
				}								
			}
		}else if (tarifaPlantillaEncabezado.getTipoPrecio().getId().intValue()==Constantes.ID_TIPOPRECIO_DSCT_TARIFA_REGULAR){
			/**Creacion del descueto*/
			
			for(TarifaByAsiento _tarifa: listTarifasByAsiento){
				List<TarifaByAsiento>result=new ArrayList<>();
				if (_tarifa.getRuta()==null) //Realiza la busqueda de las tarifas por fecha y servicio
					result= buscarTarifaByAsientosByFechaServicio(Constantes.FORMAT_DATE.format(_tarifa.getFecha()), _tarifa.getServicio().getId());
				else{
					//Realiza la busqueda de las tarifas por fecha, ruta y servicio
					TarifaByAsiento tarifa = buscarTarifaByAsientoByRutaServicio(Constantes.FORMAT_DATE.format(_tarifa.getFecha()), _tarifa.getRuta().getId(), _tarifa.getServicio().getId());
					if(tarifa!=null)
						result.add(tarifa);
				}
				
				for(TarifaByAsiento tarifa: result){
					i++;
					
					//Inserta el descuento de la tarifa - 
					for(TarifaByAsientoDetalle tarifaDetalle : _tarifa.getListTarifaByAsientoDetalle()){
						TarifaByAsientoDetalle oTarifaDetalle= (TarifaByAsientoDetalle) tarifaDetalle.clone();						
						
						//Valida que no exista otra creada previamente
						List<TarifaByAsientoDetalle> lstTarifaDetalleDupli= getTarifaByAsientoDetalleDAO().buscarByTarifaId(tarifa.getId(), oTarifaDetalle.getTipoAsiento().getId(), oTarifaDetalle.getTipoPrecio().getId());
						for(TarifaByAsientoDetalle dupliTarifaDetalle: lstTarifaDetalleDupli){
							if(dupliTarifaDetalle.getAsientos().equals(oTarifaDetalle.getAsientos())){
								dupliTarifaDetalle.setEstadoRegistro(Constantes.VALUE_INACTIVO);
								UtilData.auditarRegistro(dupliTarifaDetalle, true, usuario, Executions.getCurrent());
								getTarifaByAsientoDetalleDAO().actualizar(dupliTarifaDetalle);
							}
						}
						
						//Inserta el nuevo descuento
						oTarifaDetalle.setTarifaByAsiento(tarifa);
						getTarifaByAsientoDetalleDAO().guardar(oTarifaDetalle);
					}					
				}
				
				//vaciar un lote de inserciones y liberar memoria (solo cuando es una insercion masiba) - esto agiliza la insercion
				 if (i % 20 == 0) { //20, same as the JDBC batch size 			      
				     getTarifaByAsientoDetalleDAO().clearMemory();
				 }
			}						
		}
		
		isCorrec=Constantes.CORRECT;
		
		return isCorrec;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaManager#guardarIncrementoRebaja(java.util.List)
	 */
	@Transactional
	@Override
	public int guardarIncrementoRebaja(List<TarifaByAsiento> listTarifas)throws Exception{
		Usuario usuario= (Usuario) Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
		int i=0;
		
		for(TarifaByAsiento tarifa:listTarifas){
			TarifaByAsiento _tarifa= buscarTarifaByAsientoByRutaServicio(Constantes.FORMAT_DATE.format(tarifa.getFecha()), tarifa.getRuta().getId(), tarifa.getServicio().getId());
			if(_tarifa!=null){
				for(TarifaByAsientoDetalle tarifaDetalle:tarifa.getListTarifaByAsientoDetalle()){
					
					List<TarifaByAsientoDetalle> _listTarifaDetalle=getTarifaByAsientoDetalleDAO().buscarByTarifaId(_tarifa.getId(), tarifaDetalle.getTipoAsiento().getId(), Constantes.ID_TIPOPRECIO_REGULAR);
					for(TarifaByAsientoDetalle tarifaDetalleOrig:_listTarifaDetalle){
						if(tarifaDetalleOrig.getPrecio()!=null && tarifaDetalleOrig.getPrecio()>0.00){
							i++;
							
							TarifaByAsientoDetalle newTarifaDetalle=(TarifaByAsientoDetalle) tarifaDetalleOrig.clone();						
							//Anula el registro original y los que coinciden con la misma configuracion de asientos			
							for(TarifaByAsientoDetalle _tarifaDetalleOrig:_listTarifaDetalle){
								if(tarifaDetalleOrig.getAsientos().equals(_tarifaDetalleOrig.getAsientos()) && _tarifaDetalleOrig.getItinerario()!=null){
									_tarifaDetalleOrig.setEstadoRegistro(Constantes.VALUE_INACTIVO);
									UtilData.auditarRegistro(_tarifaDetalleOrig, true, usuario, Executions.getCurrent());
									getTarifaByAsientoDetalleDAO().actualizar(_tarifaDetalleOrig);
								}
							}
							
							//Crea el nuevo registro
							Double newPrecio=tarifaDetalleOrig.getPrecio();
							if(tarifaDetalle.isIncremento())
								newPrecio +=tarifaDetalle.getValue();
							else
								newPrecio -=tarifaDetalle.getValue();
								
							newTarifaDetalle.setId(null);					
							newTarifaDetalle.setItinerario(tarifaDetalle.getItinerario());
							newTarifaDetalle.setPrecio(newPrecio);
							newTarifaDetalle.setEstadoRegistro(Constantes.VALUE_ACTIVO);
							UtilData.auditarRegistro(newTarifaDetalle, usuario, Executions.getCurrent());
							
							//Valida que el registro a insertar no coincida con alguna ya insertado con la misma configuracion de asientos
							List<TarifaByAsientoDetalle>result=getTarifaByAsientoDetalleDAO().buscarConfigAsientosDuplicate(newTarifaDetalle);
							if(result.size()==0)
								getTarifaByAsientoDetalleDAO().guardar(newTarifaDetalle);						
							
							//vaciar un lote de inserciones y liberar memoria (solo cual una insercion masiba) - esto ajiliza la insercion
							 if (i % 20 == 0) { //20, same as the JDBC batch size 			      
								 getTarifaByAsientoDetalleDAO().clearMemory();
							 }
						}						
					}					
				}									
			}			
		}
		
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaManager#validarTarifasCreadas(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<TarifaByAsiento> validarTarifaByAsientoCreadas(String fechaInicio, String fechaFin, String rutas_ids, String servicios_ids) throws Exception {
		return getTarifaByAsientoDAO().validarTarifasCreadas(fechaInicio, fechaFin, rutas_ids, servicios_ids);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaManager#buscarTarifaByRutaServicio(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public TarifaByAsiento buscarTarifaByAsientoByRutaServicio(String fecha, Integer ruta_Id,Integer servicio_Id) throws Exception {
		return getTarifaByAsientoDAO().buscarTarifaByRutaServicio(fecha, ruta_Id, servicio_Id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaManager#buscarRutasGroupByRuta(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<TarifaByAsiento> buscarRutasGroupByRuta(String fecha,Integer localidad_idOrigen, Integer localidad_idDestino)throws Exception {	
		return getTarifaByAsientoDAO().buscarRutasGroupByRuta(fecha, localidad_idOrigen, localidad_idDestino);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.service.business.TarifaManager#buscarTarifasByFechaServicio(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<TarifaByAsiento> buscarTarifaByAsientosByFechaServicio(String fecha,Integer servicio_id) throws Exception {
		return getTarifaByAsientoDAO().buscarTarifasByFechaServicio(fecha, servicio_id);
	}

}
