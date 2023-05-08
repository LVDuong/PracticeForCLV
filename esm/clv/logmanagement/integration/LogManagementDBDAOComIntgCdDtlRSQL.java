/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : LogManagementDBDAOComIntgCdDtlRSQL.java
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

public class LogManagementDBDAOComIntgCdDtlRSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * LogManagementDBDAOComIntgCdDtlRSQL
	  * </pre>
	  */
	public LogManagementDBDAOComIntgCdDtlRSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("codeid",new String[]{arrTmp[0],arrTmp[1]});

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.logmanagement.integration").append("\n"); 
		query.append("FileName : LogManagementDBDAOComIntgCdDtlRSQL").append("\n"); 
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
		query.append("SELECT B.INTG_CD_ID," ).append("\n"); 
		query.append("B.INTG_CD_VAL_CTNT," ).append("\n"); 
		query.append("REPLACE(B.INTG_CD_VAL_DP_DESC,'&','&'||'amp;') INTG_CD_VAL_DP_DESC," ).append("\n"); 
		query.append("REPLACE(B.INTG_CD_VAL_DESC,'&','&'||'amp;') INTG_CD_VAL_DESC," ).append("\n"); 
		query.append("B.INTG_CD_VAL_DP_SEQ," ).append("\n"); 
		query.append("B.APLY_ST_DT," ).append("\n"); 
		query.append("B.APLY_END_DT" ).append("\n"); 
		query.append("FROM COM_INTG_CD_DTL B" ).append("\n"); 
		query.append("WHERE B.INTG_CD_ID=@[codeid]" ).append("\n"); 
		query.append("ORDER BY B.INTG_CD_ID, B.INTG_CD_VAL_DP_SEQ" ).append("\n"); 

	}
}