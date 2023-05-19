/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : MoneyMgmtBC.java
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

import com.clt.apps.opus.esm.clv.moneymgmt.vo.ConditionVO;
import com.clt.apps.opus.esm.clv.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.moneymgmt.vo.SummaryVO;
import com.clt.framework.core.layer.event.EventException;

/**
 * ALPS-MoneyMgmt Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-MoneyMgmt<br>
 *
 * @author Duong Le
 * @since J2EE 1.8
 */
public interface MoneyMgmtBC {
	
	/**
	 * Search Summary data 
	 * 
	 * @param SummaryVO	summaryVO
	 * @return List<SummaryVO>
	 * @exception EventException
	 */
	public List<SummaryVO> searchSummaryVO(ConditionVO conditionVO) throws EventException;
	/**
	 * Search Detail data 
	 * @param detailVO
	 * @return List<DetailVO>
	 * @throws EventException
	 */
	public List<DetailVO> searchDetailVO(ConditionVO conditionVO) throws EventException;

	/**
	 * Get data for Partner combo box
	 * @return List<SummaryVO>
	 * @throws EventException
	 */
	public List<SummaryVO> getPartnerCodes() throws EventException;
	
	/**
	 * Get data for Lane combo box
	 * @param summaryVO
	 * @return List<SummaryVO>
	 * @throws EventException
	 */
	public List<SummaryVO> getLaneCodes(SummaryVO summaryVO) throws EventException;
	
	/**
	 * Get data for Trade combo box
	 * @param summaryVO
	 * @return List<SummaryVO>
	 * @throws EventException
	 */
	public List<SummaryVO> getTradeCodes(SummaryVO summaryVO) throws EventException;

}
