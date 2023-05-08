/*=========================================================
 *Copyright(c) 2006 CyberLogitec
 *@FileName : CodeManagementBCImpl.java
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

import java.util.ArrayList;
import java.util.List;

import com.bluecast.util.DuplicateKeyException;
import com.clt.apps.opus.esm.clv.logmanagement.integration.LogManagementDBDAO;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtDtlVO;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtMstVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

/**
 * edm-edm Business Logic Basic Command implementation<br>
 * - edm-edm에 대한 비지니스 로직을 처리한다.<br>
 * 
 * @author SeongWook Kim
 * @see LogManagementBC 각 DAO 클래스 참조
 * @since J2EE 1.4
 */
public class LogManagementBCImpl extends BasicCommandSupport implements LogManagementBC {

	// Database Access Object
	private transient LogManagementDBDAO dbDao = null;

	/**
	 * LogManagementBCImpl 객체 생성<br>
	 * LogManagementDBDAO를 생성한다.<br>
	 */
	public LogManagementBCImpl() {
		dbDao = new LogManagementDBDAO();
	}

	/**
	 * 조회 이벤트 처리<br>
	 * LogManagement화면에 대한 조회 이벤트 처리<br>
	 * 
	 * @param Event e
	 * @return DBRowSet
	 * @exception EventException
	 */
	public DBRowSet searchAPPCodeList(Event e) throws EventException {
		try {
			return dbDao.searchAPPCodeList(e);
		} catch (DAOException de) {
			log.error("err " + de.toString(), de);
			throw new EventException(de.getMessage());
		}
	}
	
	/**
	 * 조회 이벤트 처리<br>
	 * 화면에 대한 조회 이벤트 처리<br>
	 * 
	 * @return String[]
	 * @exception EventException
	 */
	public String[] searchSubSystemCodeList() throws EventException {

		try {
			return dbDao.searchSubSystemCodeList();
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage());
		}
	}
	
	/**
	 * 조회 이벤트 처리<br>
	 * 화면에 대한 조회 이벤트 처리<br>
	 * 
	 * @return String
	 * @exception EventException
	 */
//	public String searchMaxIntgCdId() throws EventException {
//		
//		try {
//			return dbDao.searchMaxIntgCdId();
//		} catch (Exception ex) {
//			throw new EventException(new ErrorHandler(ex).getMessage());
//		}
//	}

	/**
	 * 조회 이벤트 처리<br>
	 * LogManagement화면에 대한 조회 이벤트 처리<br>
	 * 
	 * @param Event e
	 * @return DBRowSet
	 * @exception EventException
	 */
	public DBRowSet searchAPPCodeDetailList(Event e) throws EventException {
		try {
			return dbDao.searchAPPCodeDetailList(e);
		} catch (DAOException de) {
			log.error("err " + de.toString(), de);
			throw new EventException(de.getMessage());
		}
	}

	/**
	 * 
	 * multiLogMgmtMst
	 * @author 김성욱
	 * @param logMgmtMstVOs
	 * @param account
	 * @throws EventException void
	 */
	public void multiLogMgmtMst(LogMgmtMstVO[] logMgmtMstVOs, SignOnUserAccount account) throws EventException {
		String errFlg = "";
		String dupFlg = "";
//		GeneralCodeMgtBC command = new GeneralCodeMgtBCImpl();
		
		try {
			List<LogMgmtMstVO> insertVoList = new ArrayList<LogMgmtMstVO>();
			List<LogMgmtMstVO> updateVoList = new ArrayList<LogMgmtMstVO>();
			List<LogMgmtMstVO> deleteVoList = new ArrayList<LogMgmtMstVO>();

			for ( int i=0; i<logMgmtMstVOs .length; i++ ) {
				if ( logMgmtMstVOs[i].getIbflag().equals("I")){
					logMgmtMstVOs[i].setCreUsrId(account.getUsr_id());
					logMgmtMstVOs[i].setUpdUsrId(account.getUsr_id());
					insertVoList.add(logMgmtMstVOs[i]);
				} else if ( logMgmtMstVOs[i].getIbflag().equals("U")){
					logMgmtMstVOs[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(logMgmtMstVOs[i]);
				} else if ( logMgmtMstVOs[i].getIbflag().equals("D")){
					deleteVoList.add(logMgmtMstVOs[i]);
				}				
			} 

			if ( insertVoList.size() > 0 ) {
				//Checking Duplication
				for( int idx=0; idx<insertVoList.size(); idx++ ){
					dupFlg = dbDao.searchDupChkLogMgmtMst(insertVoList.get(idx));
					if( "Y".equals(dupFlg) ){
						errFlg = "Y";
					}
				}
				if( !"Y".equals(errFlg) ){
					dbDao.addAPPCodeList(insertVoList);
//					command.manageIntgCdToMnrBasic(insertVoList);
				} else{
					throw new DuplicateKeyException(new ErrorHandler("COM12115",new String[]{"Master Code"}).getMessage());
				}
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifyLogMgmtMst(updateVoList);
//				command.manageIntgCdToMnrBasic(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removeLogMgmtMst(deleteVoList);
//				command.manageIntgCdToMnrBasic(deleteVoList);
			}
			
		} catch(DuplicateKeyException de) {
			log.error("err " + de.toString(), de);
			throw new EventException(new ErrorHandler("COM12115",new String[]{"Master Code"}).getMessage());
		} catch(DAOException ex) {
			log.error("err " + ex.toString(), ex);
			throw new EventException(new ErrorHandler("COM12240",new String[]{}).getMessage(), ex);
		} catch(Exception ex) {
			log.error("err " + ex.toString(), ex);
			throw new EventException(new ErrorHandler("COM12240",new String[]{}).getMessage(), ex);
		}
	}
	
	/**
	 * 
	 * multiLogMgmtDtl
	 * @author 김성욱
	 * @param logMgmtDtlVOs
	 * @param account
	 * @throws EventException void
	 */
	public void multiLogMgmtDtl(LogMgmtDtlVO[] logMgmtDtlVOs, SignOnUserAccount account) throws EventException {
		String errFlg = "";
		String dupFlg = "";
		
		try {
			List<LogMgmtDtlVO> insertVoList = new ArrayList<LogMgmtDtlVO>();
			List<LogMgmtDtlVO> updateVoList = new ArrayList<LogMgmtDtlVO>();
			List<LogMgmtDtlVO> deleteVoList = new ArrayList<LogMgmtDtlVO>();

			for ( int i=0; i<logMgmtDtlVOs .length; i++ ) {
				if ( logMgmtDtlVOs[i].getIbflag().equals("I")){
					logMgmtDtlVOs[i].setCreUsrId(account.getUsr_id());
					logMgmtDtlVOs[i].setUpdUsrId(account.getUsr_id());
					insertVoList.add(logMgmtDtlVOs[i]);
				} else if ( logMgmtDtlVOs[i].getIbflag().equals("U")){
					logMgmtDtlVOs[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(logMgmtDtlVOs[i]);
				} else if ( logMgmtDtlVOs[i].getIbflag().equals("D")){
					deleteVoList.add(logMgmtDtlVOs[i]);
				}				
			} 

			if ( insertVoList.size() > 0 ) {
				//Checking Duplication
				for( int idx=0; idx<insertVoList.size(); idx++ ){
					dupFlg = dbDao.searchDupChkLogMgmtDtl(insertVoList.get(idx));
					if( "Y".equals(dupFlg) ){
						errFlg = "Y";
					}
				}
				if( !"Y".equals(errFlg) ){
					dbDao.addAPPCodeDetailList(insertVoList);
//					command.manageIntgDtlCdToMnrBasic(insertVoList);
				} else{
					throw new DuplicateKeyException(new ErrorHandler("COM12115",new String[]{"Detail Code"}).getMessage());
				}
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifyLogMgmtDtl(updateVoList);
//				command.manageIntgDtlCdToMnrBasic(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removeLogMgmtDtl(deleteVoList);
//				command.manageIntgDtlCdToMnrBasic(deleteVoList);
			}
			
		} catch(DuplicateKeyException de) {
			log.error("err " + de.toString(), de);
			throw new EventException(new ErrorHandler("COM12115",new String[]{"Detail Code"}).getMessage());
		} catch(DAOException ex) {
			log.error("err " + ex.toString(), ex);
			throw new EventException(new ErrorHandler("COM12240",new String[]{}).getMessage(), ex);
		} catch(Exception ex) {
			log.error("err " + ex.toString(), ex);
			throw new EventException(new ErrorHandler("COM12240",new String[]{}).getMessage(), ex);
		}
	}

	/**
	 * edm 업무 시나리오 마감작업<br>
	 * LogManagement업무 시나리오 종료시 관련 내부객체 해제<br>
	 */
	public void doEnd() {
		dbDao = null;
	}
}