/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : MoneyMgmtDBDAO.java
*@FileTitle : Money Management
*Open Issues :
*Change history :
*@LastModifyDate : 2023.05.15
*@LastModifier : 
*@LastVersion : 1.0
* 2023.05.15 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.moneymgmt.integration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.moneymgmt.vo.ConditionVO;
import com.clt.apps.opus.esm.clv.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.moneymgmt.vo.SummaryVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;


/**
 * 
 * @author SeongWook Kim
 * @see MoneyMgmtBCImpl 
 * @since J2EE 1.8
 */
public class MoneyMgmtDBDAO extends DBDAOSupport {
	
	/**
	 * Search Summary data
	 * @param summaryVO
	 * @return List<SummaryVO>
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<SummaryVO> searchSummaryVO(SummaryVO summaryVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<SummaryVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		List<String> partnerCodes=new ArrayList<String>();
		
		try{
			if(summaryVO != null){
				Map<String, String> mapVO = summaryVO .getColumnValues();
				
				if("All".equalsIgnoreCase(summaryVO.getJoCrrCd())==false){
					partnerCodes=Arrays.asList(summaryVO.getJoCrrCd().split(","));
				}
				param.put("partnerCodes",partnerCodes);
				param.putAll(mapVO);
				
				velParam.put("partnerCodes",partnerCodes);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new MoneyMgmtDBDAOSummaryVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, SummaryVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	/**
	 * Search Summary data
	 * @param conditionVO
	 * @return List<SummaryVO>
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<SummaryVO> searchSummaryVO(ConditionVO conditionVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<SummaryVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		List<String> partnerCodes=new ArrayList<String>();
		
		try{
			if(conditionVO != null){
				Map<String, String> mapVO = conditionVO .getColumnValues();
				
				if("All".equalsIgnoreCase(conditionVO.getSPartnerCode())==false){
					partnerCodes=Arrays.asList(conditionVO.getSPartnerCode().split(","));
				}
				param.put("partnerCodes",partnerCodes);
				param.putAll(mapVO);
				
				velParam.put("partnerCodes",partnerCodes);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new MoneyMgmtDBDAOSummaryVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, SummaryVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	/**
	 * Search Detail data
	 * @param detailVO
	 * @return List<DetailVO>
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<DetailVO> searchDetailVO(ConditionVO conditionVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<DetailVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		List<String> partnerCodes=new ArrayList<String>();
		
		try{
			if(conditionVO != null){
				Map<String, String> mapVO = conditionVO .getColumnValues();
				
				if("All".equalsIgnoreCase(conditionVO.getSPartnerCode())==false){
					partnerCodes=Arrays.asList(conditionVO.getSPartnerCode().split(","));
				}
				param.put("partnerCodes",partnerCodes);
				param.putAll(mapVO);
				
				velParam.put("partnerCodes",partnerCodes);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new MoneyMgmtDBDAODetailVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, DetailVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	/**
	 * Get data for Partner combo box
	 * @return List<SummaryVO>
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<SummaryVO> getPartnerCodes() throws DAOException {
		DBRowSet dbRowset = null;
		List<SummaryVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
	
		try{
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new MoneyMgmtDAOSearchPartnerCodeRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, SummaryVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	/**
	 * Get data for Lane combo box
	 * @return List<SummaryVO>
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<SummaryVO> getLaneCodes(SummaryVO summaryVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<SummaryVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		List<String> partnerCodes = new ArrayList<String>();
		
		try{
			if(summaryVO != null){
				Map<String, String> mapVO = summaryVO .getColumnValues();
				if("All".equalsIgnoreCase(summaryVO.getJoCrrCd())==false){
					partnerCodes=Arrays.asList(summaryVO.getJoCrrCd().split(","));
				}

				//add all value to param
				param.put("partnerCodes",partnerCodes);
				param.putAll(mapVO);
				
				//add all value to velParam
				velParam.put("partnerCodes",partnerCodes);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new MoneyMgmtDAOSearchLaneCodeRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, SummaryVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	/**
	 * Get data for Trade combo box
	 * @return List<SummaryVO>
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<SummaryVO> getTradeCodes(SummaryVO summaryVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<SummaryVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		List<String> partnerCodes=new ArrayList<String>();
		
		try{
			if(summaryVO != null){
				Map<String, String> mapVO = summaryVO .getColumnValues();
				if("All".equalsIgnoreCase(summaryVO.getJoCrrCd())==false){
					partnerCodes=Arrays.asList(summaryVO.getJoCrrCd().split(","));
				}

				//add all value to param
				param.put("partnerCodes",partnerCodes);
				param.putAll(mapVO);
				
				//add all value to velParam
				velParam.put("partnerCodes",partnerCodes);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new MoneyMgmtDAOSearchTradeCodeRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, SummaryVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}

}
