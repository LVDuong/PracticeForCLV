/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : LogMsgMgmtBCImpl.java
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

import java.util.ArrayList;
import java.util.List;

import org.python.modules.newmodule;

import com.clt.apps.opus.esm.clv.clvtraining.logmsgmgmt.integration.LogMsgMgmtDBDAO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.clvtraining.logmsgmgmt.vo.ErrMsgVO;

/**
 * ALPS-ClvTraining Business Logic Command Interface<br>
 * - ALPS-ClvTraining에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author DuongLe
 * @since J2EE 1.6
 */
public class LogMsgMgmtBCImpl extends BasicCommandSupport implements LogMsgMgmtBC {

	// Database Access Object
	private transient LogMsgMgmtDBDAO dbDao = null;

	/**
	 * LogMsgMgmtBCImpl 객체 생성<br>
	 * LogMsgMgmtDBDAO를 생성한다.<br>
	 */
	public LogMsgMgmtBCImpl() {
		dbDao = new LogMsgMgmtDBDAO();
	}
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 *
	 * @param ErrMsgVO errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception EventException
	 */
	public List<ErrMsgVO> SearchLogMsg(ErrMsgVO errMsgVO) throws EventException {
		try {
			return dbDao.SearchLogMsg(errMsgVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}

	}

	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 *
	 * @param ErrMsgVO[] errMsgVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void ManageLogMsg(ErrMsgVO[] errMsgVO, SignOnUserAccount account) throws EventException{
		try {
			List<ErrMsgVO> insertVoList = new ArrayList<ErrMsgVO>();
			List<ErrMsgVO> updateVoList = new ArrayList<ErrMsgVO>();
			List<ErrMsgVO> deleteVoList = new ArrayList<ErrMsgVO>();

			StringBuilder duplicateMsgCds = new StringBuilder();

			for ( int i=0; i<errMsgVO .length; i++ ) {
				if ( errMsgVO[i].getIbflag().equals("I")){
					//Create VO to check duplicate before insert
					ErrMsgVO dupErrMsgVO = new ErrMsgVO();
					dupErrMsgVO.setErrMsgCd(errMsgVO[i].getErrMsgCd());

					if(SearchLogMsg(dupErrMsgVO).size() == 0) {
						errMsgVO[i].setCreUsrId(account.getUsr_id());
						errMsgVO[i].setUpdUsrId(account.getUsr_id());
						insertVoList.add(errMsgVO[i]);
					} else {
						duplicateMsgCds.append(errMsgVO[i].getErrMsgCd() + "|");
					}
				} else if ( errMsgVO[i].getIbflag().equals("U")){
					errMsgVO[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(errMsgVO[i]);
				} else if ( errMsgVO[i].getIbflag().equals("D")){
					deleteVoList.add(errMsgVO[i]);
				}
			}

			if (duplicateMsgCds.length() > 0) {
				duplicateMsgCds.deleteCharAt(duplicateMsgCds.length() - 1);
				throw new EventException(new ErrorHandler("ERR12356", new String[]{duplicateMsgCds.toString()}).getMessage());
			}

			if ( insertVoList.size() > 0 ) {
				dbDao.addManageLogMsgS(insertVoList);
			}

			if ( updateVoList.size() > 0 ) {
				dbDao.modifyManageLogMsgS(updateVoList);
			}

			if ( deleteVoList.size() > 0 ) {
				dbDao.removeManageLogMsgS(deleteVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

}