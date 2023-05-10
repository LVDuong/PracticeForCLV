/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : LogManagementEvent.java
*@FileTitle : Log Management
*Open Issues :
*Change history :
*@LastModifyDate : 2023-05-05
*@LastModifier : 
*@LastVersion : 1.0
* 2023-05-05
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.logmanagement.event;

import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtDetailVO;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtVO;
import com.clt.framework.support.layer.event.EventSupport;

public class LogManagementEvent extends EventSupport {

	LogMgmtVO logMgmtVO = null;
	
	LogMgmtDetailVO logMgmtDetailVO = null;
	
	LogMgmtVO[] logMgmtVOs = null;
	
	LogMgmtDetailVO[] logMgmtDetailVOs = null;
	
	public LogManagementEvent(){}

	public LogMgmtVO getLogMgmtVO() {
		return logMgmtVO;
	}

	public void setLogMgmtVO(LogMgmtVO logMgmtVO) {
		this.logMgmtVO = logMgmtVO;
	}

	public LogMgmtDetailVO getLogMgmtDetailVO() {
		return logMgmtDetailVO;
	}

	public void setLogMgmtDetailVO(LogMgmtDetailVO logMgmtDetailVO) {
		this.logMgmtDetailVO = logMgmtDetailVO;
	}

	public LogMgmtVO[] getLogMgmtVOs() {
		return logMgmtVOs;
	}

	public void setLogMgmtVOs(LogMgmtVO[] logMgmtVOs) {
		this.logMgmtVOs = logMgmtVOs;
	}

	public LogMgmtDetailVO[] getLogMgmtDetailVOs() {
		return logMgmtDetailVOs;
	}

	public void setLogMgmtDetailVOs(LogMgmtDetailVO[] logMgmtDetailVOs) {
		this.logMgmtDetailVOs = logMgmtDetailVOs;
	}
	
	

}
