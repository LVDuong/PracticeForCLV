/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : LogManagementDBDAOsearchSubSystemCdRSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2023.05.05
*@LastModifier : 
*@LastVersion : 1.0
* 2023.05.05 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.logmanagement.integration;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author Duong Le
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class LogManagementDBDAOsearchSubSystemCdRSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * CodeManagementDBDAOsearchSubSystemCdRSQL
	  * </pre>
	  */
	public LogManagementDBDAOsearchSubSystemCdRSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.logmanagement.integration ").append("\n"); 
		query.append("FileName : LogManagementDBDAOsearchSubSystemCdRSQL").append("\n"); 
		query.append("*/").append("\n"); 
	}
	
	public String getSQL(){
		return query.toString();
	}
	
	public HashMap<String,String[]> getParams() {
		return params;
	}

	/**
	 * Query 생성
	 */
	public void setQuery(){
		query.append("SELECT INTG_CD_VAL_CTNT SUB_SYS_CD" ).append("\n"); 
		query.append("FROM COM_INTG_CD_DTL" ).append("\n"); 
		query.append("WHERE INTG_CD_ID = 'CD20013' " ).append("\n"); 
		query.append("ORDER BY INTG_CD_VAL_DP_DESC" ).append("\n"); 

	}
}