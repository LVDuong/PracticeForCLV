/*=========================================================
*Copyright(c) 2006 CyberLogitec
*@FileName : ComEdm001Event.java
*@FileTitle : 공통코드관리
*Open Issues :
*Change history :
*@LastModifyDate : 2006-09-07
*@LastModifier : SeongWook Kim
*@LastVersion : 1.0
* 2006-09-07 SeongWook Kim
* 1.0 최초 생성
=========================================================*/
package com.clt.apps.opus.esm.clv.logmanagement.event;

import java.util.Arrays;
import java.util.Collection;

import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtCondVO;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtDtlVO;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtMstVO;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.controller.html.HTMLAction;
import com.clt.framework.support.layer.event.EventSupport;
import com.clt.syscommon.common.table.COM_CODEDOMAIN;
import com.clt.syscommon.common.table.COM_CODEVALUE;


/**
 * UI_COM_EDM_001 에 대한 PDTO(Data Transfer Object including Parameters)<br>
 * - UI_COM_EDM_001HTMLAction에서 작성<br>
 * - ServiceCommand Layer로 전달하는 PDTO로 사용<br>
 *
 * @author SeongWook Kim
 * @see HTMLAction 참조
 * @since J2EE 1.4
 */
public class LogManagementEvent extends EventSupport {

	private static final long serialVersionUID = 1L;
	
	/** t_codedomain Table  Value Object */
	private COM_CODEDOMAIN tCodedomain = null;

	/** t_codedomains Multi Action을 위한 Collection */
	private Collection tCodedomains = null;

	/** t_codevalue Table  Value Object */
	private COM_CODEVALUE tCodevalue = null;

	/** t_codevalues Multi Action을 위한 Collection */
	private Collection tCodevalues = null;

	/** id 변수 (Form 객체) */
	private String id = null;

	/** codeid 변수 (Form 객체) */
	private String codeid = null;

	/** name 변수 (Form 객체) */
	private String name = null;

	/** definition 변수 (Form 객체) */
	private String definition = null;

	/** datatype 변수 (Form 객체) */
	private String datatype = null;

	/** precision 변수 (Form 객체) */
	private String precision = null;

	/** var1 변수 (Form 객체) */
	private String var1 = null;
	
	private String searchCdTp = null;

	private String searchtype = null;
	
	private String selectedcodes = null;
	
	private DBRowSet dbrowset1 = null;
	
	private DBRowSet dbrowset2 = null;

	/** Table Value Object 조회 조건 처리  */
	private LogMgmtCondVO logMgmtCondVO = null;

	/** Table Value Object Multi Data 처리 */
	private LogMgmtMstVO[] logMgmtMstVOs = null;

	private LogMgmtDtlVO[] logMgmtDtlVOs = null;
	
	public LogManagementEvent(){}

	/**
	 * 
	 * ComEdm001Event 생성
	 * It's Constructor
	 * @param t_codedomain
	 */
	public LogManagementEvent(COM_CODEDOMAIN t_codedomain) {
		this.tCodedomain = t_codedomain;
    }
	
	public LogMgmtCondVO getLogMgmtCondVO() {
		return logMgmtCondVO;
	}

	public void setLogMgmtCondVO(LogMgmtCondVO logMgmtCondVO) {
		this.logMgmtCondVO = logMgmtCondVO;
	}

	public LogMgmtMstVO[] getLogMgmtMstVOs() {
		LogMgmtMstVO[] rtnVOs = null;
		if (this.logMgmtMstVOs != null) {
			rtnVOs = Arrays.copyOf(logMgmtMstVOs, logMgmtMstVOs.length);
		}
		return rtnVOs;
	}

	public void setLogMgmtMstVOs(LogMgmtMstVO[] logMgmtMstVOs) {
		if(logMgmtMstVOs != null){
			LogMgmtMstVO[] tmpVOs = Arrays.copyOf(logMgmtMstVOs, logMgmtMstVOs.length);
			this.logMgmtMstVOs  = tmpVOs;
		}
	}

	public LogMgmtDtlVO[] getLogMgmtDtlVOs() {
		LogMgmtDtlVO[] rtnVOs = null;
		if (this.logMgmtDtlVOs != null) {
			rtnVOs = Arrays.copyOf(logMgmtDtlVOs, logMgmtDtlVOs.length);
		}
		return rtnVOs;
	}

	public void setLogMgmtDtlVOs(LogMgmtDtlVO[] logMgmtDtlVOs) {
		if(logMgmtDtlVOs != null){
			LogMgmtDtlVO[] tmpVOs = Arrays.copyOf(logMgmtDtlVOs, logMgmtDtlVOs.length);
			this.logMgmtDtlVOs  = tmpVOs;
		}
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @return COM_CODEDOMAIN
	 */
	public COM_CODEDOMAIN getT_CODEDOMAIN(){
		return tCodedomain;
	}
	/**
	 * 
	 * @author 2e Consulting
	 * @return Collection
	 */
	public Collection getT_CODEDOMAINS(){
		return tCodedomains;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @return COM_CODEVALUE
	 */
	public COM_CODEVALUE getT_CODEVALUE(){
		return tCodevalue;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @return Collection
	 */
	public Collection getT_CODEVALUES(){
		return tCodevalues;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @return String
	 */
	public String getId(){
		return id;
	}


	/**
	 * 
	 * @author 2e Consulting
	 * @return String
	 */
	public String getCodeid(){
		return codeid;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @return String
	 */
	public String getName(){
		return name;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @return String
	 */
	public String getDefinition(){
		return definition;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @return String
	 */
	public String getDatatype(){
		return datatype;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @return String
	 */
	public String getPrecision(){
		return precision;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @return String
	 */
	public String getVar1(){
		return var1;
	}

	/**
	 * 
	 */
	public String getEventName() {
		return "LogManagementEvent";
	}

	/**
	 * 
	 */
	public String toString() {
		return "LogManagementEvent";
	}
	
	/**
	 * 
	 * @author 2e Consulting
	 * @return String
	 */
	public String getSearchCdTp() {
		return searchCdTp;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @param searchCdTp void
	 */
	public void setSearchCdTp(String searchCdTp) {
		this.searchCdTp = searchCdTp;
	}
	
	/**
	 * 
	 * @author 2e Consulting
	 * @return String
	 */
	public String getSearchtype() {
		return searchtype;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @param searchtype void
	 */
	public void setSearchtype(String searchtype) {
		this.searchtype = searchtype;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @return String
	 */
	public String getSelectedcodes() {
		return selectedcodes;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @param selectedcodes void
	 */
	public void setSelectedcodes(String selectedcodes) {
		this.selectedcodes = selectedcodes;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @return DBRowSet
	 */
	public DBRowSet getDbrowset1() {
		return dbrowset1;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @param dbrowset1 void
	 */
	public void setDbrowset1(DBRowSet dbrowset1) {
		this.dbrowset1 = dbrowset1;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @return DBRowSet
	 */
	public DBRowSet getDbrowset2() {
		return dbrowset2;
	}

	/**
	 * 
	 * @author 2e Consulting
	 * @param dbrowset2 void
	 */
	public void setDbrowset2(DBRowSet dbrowset2) {
		this.dbrowset2 = dbrowset2;
	}

}
