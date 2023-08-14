/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 05/09/2013
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.List;

import pe.itsb.vyrbus.model.bean.Pasajero;
import pe.itsb.vyrbus.model.bean.Reniec;
import pe.itsb.vyrbus.model.bean.Sexo;
import pe.itsb.vyrbus.model.dao.ReniecDAO;
import pe.itsb.vyrbus.service.locator.ServiceLocator;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author JABANTO
 *
 */
public class ReniecDAOImpl extends GenericDAOImpl implements ReniecDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReniecDAO#buscarPax(java.lang.String)
	 */
	@Override
	public Reniec buscarPax(String numeroDocumento) throws Exception {
		try {

			// TODO Auto-generated method stub
			String sql="select r.num_doc, r.ape_pat, r.ape_mat, r.nom_per, r.fec_nac, r.cod_sex, r.tip_doc "+
					  "from RENIEC.bdreniec r where r.num_doc='"+numeroDocumento+"' ";

			log.info(sql);

			List<?> result = getSession().createSQLQuery(sql).list();
			Reniec reniec=null;
			for (Object element : result) {
				Object[] obj = (Object[])element;

				reniec= new Reniec();
				reniec.setNumeroDocumento(obj[0].toString());
				reniec.setApellidoPaterno(obj[1].toString());
				reniec.setApellidoMaterno(obj[2].toString());
				reniec.setNombres(obj[3].toString());

				/*Da formato a la fechaNacimiento*/
				String anio =obj[4].toString().substring(0,4);
				String mes=obj[4].toString().substring(4,6);
				String dia=obj[4].toString().substring(6,8);
				String fechaNacimiento=dia+"/"+mes+"/"+anio;
				reniec.setFechaNacimiento(fechaNacimiento);
				/*========================================*/
				if(obj[5].toString().equals("1"))
					reniec.setSexo(String.valueOf(Constantes.ID_SEXO_MASCULINO));
				else reniec.setSexo(String.valueOf(Constantes.ID_SEXO_FEMENINO));

				reniec.setTipoDocumento(obj[6].toString());
			}

			return reniec;

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ReniecDAO#validarPaxConReniec(com.tepsa.sisvyr.model.bean.Pasajero)
	 */
	@Override
	public  Pasajero validarPaxConReniec(Pasajero oPasajero) throws Exception{
		if(oPasajero.getNumeroDocumento()!=null && oPasajero.getTipoDocumento().getId().equals(Constantes.ID_TIPDOC_DNI)){
			Reniec reniec= buscarPax(oPasajero.getNumeroDocumento());
			boolean updatePax=false;
			if(reniec!=null){
				/*Compra apellidos Paterno*/
				if(!(reniec.getApellidoPaterno().equals(oPasajero.getApellidoPaterno()))){
					oPasajero.setApellidoPaterno(reniec.getApellidoPaterno());
					updatePax=true;
				}
				/*Compara Apellido Materno*/
				if(!(reniec.getApellidoMaterno().equals(oPasajero.getApellidoMaterno()))){
					oPasajero.setApellidoMaterno(reniec.getApellidoMaterno());
					updatePax=true;
				}
				/*Compara Nombres*/
				if(reniec.getNombres().equals(oPasajero.getNombre())){
					oPasajero.setNombre(reniec.getNombres());
					updatePax=true;
				}
				if(updatePax)
					oPasajero.setNombresApellidos(oPasajero.getNombre()+" "+oPasajero.getApellidoPaterno()+" "+oPasajero.getApellidoMaterno());

				/*Compara sexo*/
				if(reniec.getSexo().equals(oPasajero.getSexo().getId().toString())){
					Sexo oSexo= ServiceLocator.getSexoManager().buscarPorId(Long.valueOf(reniec.getSexo()));
					oPasajero.setSexo(oSexo);
					updatePax=true;
				}
				if(!(reniec.getFechaNacimiento().equals(oPasajero.getFechaNacimiento()))){
					oPasajero.setFechaNacimiento(reniec.getFechaNacimiento());
					updatePax=true;
				}

				if(updatePax){
					ServiceLocator.getPasajeroManager().actualizar(oPasajero);
				}

				oPasajero.setSincronizadoReniec(true);
			}
		}

		return oPasajero;
	}


}
