/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : LogManagementDBDAOsearchDupChkCodeMgmtMstRSQL.java
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

public class LogManagementDBDAOsearchDupChkCodeMgmtMstRSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * LogManagementDBDAOsearchDupChkCodeMgmtMstRSQL
	  * </pre>
	  */
	public LogManagementDBDAOsearchDupChkCodeMgmtMstRSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("intg_cd_id",new String[]{arrTmp[0],arrTmp[1]});

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.logmanagement.integration ").append("\n"); 
		query.append("FileName : LogManagementDBDAOsearchDupChkCodeMgmtMstRSQL").append("\n"); 
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
		query.append("SELECT  DECODE(SIGN(COUNT(1)),1,'Y','N') DUP_FLG" ).append("\n"); 
		query.append("FROM    COM_INTG_CD" ).append("\n"); 
		query.append("WHERE   INTG_CD_ID = @[intg_cd_id]" ).append("\n"); 

	}
}