/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : MoneyMgmtBCImpl.java
*@FileTitle : Money Management
*Open Issues :
*Change history :
*@LastModifyDate : 2023.05.15
*@LastModifier : 
*@LastVersion : 1.0
* 2023.05.15 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.moneymgmt.basic;

import java.util.List;

import com.clt.apps.opus.esm.clv.moneymgmt.integration.MoneyMgmtDBDAO;
import com.clt.apps.opus.esm.clv.moneymgmt.vo.ConditionVO;
import com.clt.apps.opus.esm.clv.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.moneymgmt.vo.SummaryVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;

/**
 * ALPS-moneymgmt Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-moneymgmt<br>
 *
 * @author Duong Le
 * @since J2EE 1.8
 */
public class MoneyMgmtBCImpl extends BasicCommandSupport implements MoneyMgmtBC {

	private transient MoneyMgmtDBDAO dbDao = null;

	/**
	 * MoneyMgmtBCImpl Constructor
	 * To create MoneyMgmtDBDAO
	 */
	public MoneyMgmtBCImpl() {
		dbDao = new MoneyMgmtDBDAO();
	}
	
	/**
	 * Search Summary data 
	 * @param SummaryVO summaryVO
	 * @return List<SummaryVO>
	 * @exception EventException
	 */	
	public List<SummaryVO> searchSummaryVO(ConditionVO conditionVO) throws EventException {
		try {
			return dbDao.searchSummaryVO(conditionVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/**
	 * Search Detail data 
	 * @param detailVO
	 * @return List<DetailVO>
	 * @throws EventException
	 */
	public List<DetailVO> searchDetailVO(ConditionVO conditionVO) throws EventException {
		try {
			return dbDao.searchDetailVO(conditionVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	/**
	 * Get data for Partner combo box
	 * @return List<SummaryVO>
	 * @throws EventException
	 */
	@Override
	public List<SummaryVO> getPartnerCodes() throws EventException {
		try {
			return dbDao.getPartnerCodes();
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	/**
	 * Get data for Lane combo box
	 * @param summaryVO
	 * @return List<SummaryVO>
	 * @throws EventException
	 */
	@Override
	public List<SummaryVO> getLaneCodes(SummaryVO summaryVO) throws EventException {
		try {
			return dbDao.getLaneCodes(summaryVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/**
	 * Get data for Trade combo box
	 * @param summaryVO
	 * @return List<SummaryVO>
	 * @throws EventException
	 */
	@Override
	public List<SummaryVO> getTradeCodes(SummaryVO summaryVO) throws EventException {
		try {
			return dbDao.getTradeCodes(summaryVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
}
