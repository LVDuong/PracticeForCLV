/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : LogManagementBC.java
*@FileTitle : Interface Log Management
*Open Issues :
*Change history :
*@LastModifyDate : 2023-05-05
*@LastModifier : 
*@LastVersion : 1.0
* 2023-05-05
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.logmanagement.basic;

import java.util.List;

import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtDetailVO;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public interface LogManagementBC  {
	
	/**
	 * This method is used to search
	 * @param logMgmtVO
	 * @return List of results
	 * @throws EventException
	 */
	public List<LogMgmtVO> searchLogMgmtVO(LogMgmtVO logMgmtVO) throws EventException;
	
	/**
	 * This method is used to search data
	 * @param logMgmtDetailVO
	 * @return List of results
	 * @throws EventException
	 */
	public List<LogMgmtDetailVO> searchLogMgmtDetailVO(LogMgmtDetailVO logMgmtDetailVO) throws EventException;
	
	/**
	 * This method is used to save data
	 * @param logMgmtVO
	 * @param account
	 * @throws EventException
	 */
	public void manageLogMgmtVO(LogMgmtVO[] logMgmtVO,SignOnUserAccount account) throws EventException;
	
	/**
	 * This method is used to save data
	 * @param logMgmtDetailVO
	 * @param account
	 * @throws EventException
	 */
	public void manageLogMgmtDetailVO(LogMgmtDetailVO[] logMgmtDetailVO,SignOnUserAccount account) throws EventException;
	
}