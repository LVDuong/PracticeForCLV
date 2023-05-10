/*=========================================================
 *Copyright(c) 2023 CyberLogitec
 *@FileName : LogManagementBCImpl.java
 *@FileTitle : Log Management
 *Open Issues :
 *Change history :
 *@LastModifyDate : 2023-05-05
 *@LastModifier : 
 *@LastVersion : 1.0
 * 2023-05-05
 * 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.logmanagement.basic;

import java.util.ArrayList;
import java.util.List;

import com.bluecast.util.DuplicateKeyException;
import com.clt.apps.opus.esm.clv.logmanagement.integration.LogManagementDBDAO;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtDetailVO;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

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

	@Override
	public List<LogMgmtVO> searchLogMgmtVO(LogMgmtVO logMgmtVO) throws EventException {
		try {
			return dbDao.searchLogMgmtVO(logMgmtVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}

	@Override
	public List<LogMgmtDetailVO> searchLogMgmtDetailVO(LogMgmtDetailVO logMgmtDetailVO) throws EventException {
		try {
			return dbDao.searchLogMgmtDetailVO(logMgmtDetailVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	@Override
	public void manageLogMgmtVO(LogMgmtVO[] logMgmtVOs, SignOnUserAccount account) throws EventException {
		try {
			//List needs to be inserted
			List<LogMgmtVO> insertVoList = new ArrayList<LogMgmtVO>();
			
			//List needs to be updated
			List<LogMgmtVO> updateVoList = new ArrayList<LogMgmtVO>();
			
			//List needs to be deleted
			List<LogMgmtVO> deleteVoList = new ArrayList<LogMgmtVO>();
			
			List<LogMgmtDetailVO> deleteLogMgmtDetailList = new ArrayList<LogMgmtDetailVO>();
			//Invalid code
			StringBuilder invalidMsgCds=new StringBuilder();
			
			//loop through logMgmtVOs and base on IbFlag to perform corresponding action
			for ( int i=0; i<logMgmtVOs.length; i++ ) {
				
				//Insert
				if ( logMgmtVOs[i].getIbflag().equals("I")){
					//Condition need to check before inserting
					LogMgmtVO condition = new LogMgmtVO();
					//set IntgCdId for condition
					condition.setIntgCdId(logMgmtVOs[i].getIntgCdId());
					
					//if code don't exist
					if(searchLogMgmtVO(condition).size()==0){
						//set CreUsrId is current user id
						logMgmtVOs[i].setCreUsrId(account.getUsr_id());
						
						//set UpdUsrId is current user id
						logMgmtVOs[i].setUpdUsrId(account.getUsr_id());
						
						//add to inserting list
						insertVoList.add(logMgmtVOs[i]);
					}else{
						//if code already existed
						//append invalid code to invalidMsgCds variable
						invalidMsgCds.append(logMgmtVOs[i].getIntgCdId()+"|");
					}
				} else if (logMgmtVOs[i].getIbflag().equals("U")){
					//Update
					//set UpdUsrId is current user id
					logMgmtVOs[i].setUpdUsrId(account.getUsr_id());
					
					//add to updating list
					updateVoList.add(logMgmtVOs[i]);
				} else if ( logMgmtVOs[i].getIbflag().equals("D")){
					LogMgmtDetailVO logMgmtDetailVO = new LogMgmtDetailVO();
					logMgmtDetailVO.setIntgCdId(logMgmtVOs[i].getIntgCdId());
					deleteLogMgmtDetailList.addAll(dbDao.searchLogMgmtDetailVO(logMgmtDetailVO));
					deleteVoList.add(logMgmtVOs[i]);
				}
			}
			
			//if we have invalid data (because code already existed)
			if(invalidMsgCds.length()!=0){
				//remove "|" at the end
				invalidMsgCds.deleteCharAt(invalidMsgCds.length()-1);
				//throw new EventException 
				throw new EventException(new ErrorHandler("ERR12356", new String[]{invalidMsgCds.toString()}).getMessage());
			}
			
			//if inserting list isn't empty
			if ( insertVoList.size() > 0 ) {
				dbDao.addLogMgmtVOs(insertVoList);
			}
			
			//if updating list isn't empty
			if ( updateVoList.size() > 0 ) {
				dbDao.updateLogMgmtVOs(updateVoList);
			}
			
//			//if deleting list isn't empty
			if ( deleteVoList.size() > 0 ) {
				if(deleteLogMgmtDetailList.size()>0){					
					dbDao.removeLogMgmtDetailVOs(deleteLogMgmtDetailList);
				}
				dbDao.removeLogMgmtVOs(deleteVoList);
			}
		} catch(DAOException ex) {
			// throw new EventException if we have DAOException
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			//other exception
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	@Override
	public void manageLogMgmtDetailVO(LogMgmtDetailVO[] logMgmtDetailVOs, SignOnUserAccount account) throws EventException {
		try {
			//List needs to be inserted
			List<LogMgmtDetailVO> insertVoList = new ArrayList<LogMgmtDetailVO>();
			
			//List needs to be updated
			List<LogMgmtDetailVO> updateVoList = new ArrayList<LogMgmtDetailVO>();
			
			//List needs to be deleted
			List<LogMgmtDetailVO> deleteVoList = new ArrayList<LogMgmtDetailVO>();
			
			//Invalid code
			StringBuilder invalidMsgCds=new StringBuilder();
			
			//loop through logMgmtDetailVOs and base on IbFlag to perform corresponding action
			for ( int i=0; i<logMgmtDetailVOs.length; i++ ) {
				
				//Insert
				if ( logMgmtDetailVOs[i].getIbflag().equals("I")){
					//Condition need to check before inserting
					LogMgmtDetailVO condition = new LogMgmtDetailVO();
					//set IntgCdId for condition
					condition.setIntgCdId(logMgmtDetailVOs[i].getIntgCdId());
					condition.setIntgCdValCtnt(logMgmtDetailVOs[i].getIntgCdValCtnt());
					
//					if code don't exist
					if(searchLogMgmtDetailVO(condition).size()==0){
						//set CreUsrId is current user id
						logMgmtDetailVOs[i].setCreUsrId(account.getUsr_id());
						
						//set UpdUsrId is current user id
						logMgmtDetailVOs[i].setUpdUsrId(account.getUsr_id());
						
						//add to inserting list
						insertVoList.add(logMgmtDetailVOs[i]);
					}else{
						//if message code already existed
						//append invalid message code to invalidMsgCds variable
						invalidMsgCds.append(logMgmtDetailVOs[i].getIntgCdValCtnt()+"|");
					}
				} else if ( logMgmtDetailVOs[i].getIbflag().equals("U")){
					//Update
					//set UpdUsrId is current user id
					logMgmtDetailVOs[i].setUpdUsrId(account.getUsr_id());
					
					//add to updating list
					updateVoList.add(logMgmtDetailVOs[i]);
				} else if ( logMgmtDetailVOs[i].getIbflag().equals("D")){
					deleteVoList.add(logMgmtDetailVOs[i]);
				}
			}
			
			//if we have invalid data( because message code already existed)
			if(invalidMsgCds.length()!=0){
				//remove "|" at the end
				invalidMsgCds.deleteCharAt(invalidMsgCds.length()-1);
				//throw new EventException 
				throw new EventException(new ErrorHandler("ERR12356", new String[]{invalidMsgCds.toString()}).getMessage());
			}
			
			//if inserting list isn't empty
			if ( insertVoList.size() > 0 ) {
				dbDao.addLogMgmtDetailVOs(insertVoList);
			}
			
			//if updating list isn't empty
			if ( updateVoList.size() > 0 ) {
				dbDao.updateLogMgmtDetailVOs(updateVoList);
			}
			
			//if deleting list isn't empty
			if ( deleteVoList.size() > 0 ) {
				dbDao.removeLogMgmtDetailVOs(deleteVoList);
			}
		} catch(DAOException ex) {
			// throw new EventException if we have DAOException
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			//other exception
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
}