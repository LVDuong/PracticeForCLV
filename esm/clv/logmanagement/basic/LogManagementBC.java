/*=========================================================
*Copyright(c) 2006 CyberLogitec
*@FileName : CodepublishBC.java
*@FileTitle : 공통코드관리
*Open Issues :
*Change history :
*@LastModifyDate : 2006-09-07
*@LastModifier : SeongWook Kim
*@LastVersion : 1.0
* 2006-09-07 SeongWook Kim
* 1.0 최초 생성
=========================================================*/
package com.clt.apps.opus.esm.clv.logmanagement.basic;

import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtDtlVO;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtMstVO;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;

/**
 * edm-edm Business Logic Command Interface<br>
 * - edm-edm에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author SeongWook Kim
 * @see LogManagementEventResponse 참조
 * @since J2EE 1.4
 */
public interface LogManagementBC  {
	
	/**
	 * 조회 이벤트 처리<br>
	 * 화면에 대한 조회 이벤트 처리<br>
	 * 
	 * @return String[]
	 * @exception EventException
	 */
	public String[] searchSubSystemCodeList() throws EventException ;
	
	/**
	 * 조회 이벤트 처리<br>
	 * 화면에 대한 조회 이벤트 처리<br>
	 * 
	 * @return String
	 * @exception EventException
	 */
//	public String searchMaxIntgCdId() throws EventException ;

	/**
	 * 멀티 이벤트 처리<br>
	 * UI_COM_EDM_001 화면에 대한 멀티 이벤트 처리<br>
	 * 
	 * @param LogMgmtMstVO[] logMgmtMstVOs
	 * @param SignOnUserAccount a
	 * @exception EventException
	 */
	public void multiLogMgmtMst(LogMgmtMstVO[] logMgmtMstVOs, SignOnUserAccount a) throws EventException;
	
	/**
	 * 멀티 이벤트 처리<br>
	 * UI_COM_EDM_001 화면에 대한 멀티 이벤트 처리<br>
	 * 
	 * @param LogMgmtDtlVO[] logMgmtDtlVOs
	 * @param SignOnUserAccount a
	 * @exception EventException
	 */
	public void multiLogMgmtDtl(LogMgmtDtlVO[] logMgmtDtlVOs, SignOnUserAccount a) throws EventException;
	
	/**
	 * 조회 이벤트 처리<br>
	 * Codepublish화면에 대한 조회 이벤트 처리<br>
	 * 
	 * @param Event e
	 * @return DBRowSet
	 * @exception EventException
	 */
	public DBRowSet searchAPPCodeList(Event e) throws EventException;

	/**
	 * 조회 이벤트 처리<br>
	 * Codepublish화면에 대한 조회 이벤트 처리<br>
	 * 
	 * @param Event e
	 * @return DBRowSet
	 * @exception EventException
	 */
	public DBRowSet searchAPPCodeDetailList(Event e) throws EventException;


}