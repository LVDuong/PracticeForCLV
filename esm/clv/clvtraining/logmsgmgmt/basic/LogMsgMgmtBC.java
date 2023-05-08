/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : LogMsgMgmtBC.java
*@FileTitle : Log Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2023.04.20
*@LastModifier : 
*@LastVersion : 1.0
* 2023.04.20 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.clvtraining.logmsgmgmt.basic;

import java.util.List;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.clvtraining.logmsgmgmt.vo.ErrMsgVO;

/**
 * ALPS-Clvtraining Business Logic Command Interface<br>
 * - ALPS-Clvtraining에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author DuongLe
 * @since J2EE 1.6
 */

public interface LogMsgMgmtBC {

	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 *
	 * @param ErrMsgVO	errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception EventException
	 */
	public List<ErrMsgVO> SearchLogMsg(ErrMsgVO errMsgVO) throws EventException;

	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 *
	 * @param ErrMsgVO[] errMsgVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void ManageLogMsg(ErrMsgVO[] errMsgVO,SignOnUserAccount account) throws EventException;
}