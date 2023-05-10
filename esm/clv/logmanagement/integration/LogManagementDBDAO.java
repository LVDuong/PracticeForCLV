/*=========================================================
 *Copyright(c) 2006 CyberLogitec
 *@FileName : CodepublishDBDAO.java
 *@FileTitle : 공통코드관리
 *Open Issues :
 *Change history :
 *@LastModifyDate : 2006-09-07
 *@LastModifier : SeongWook Kim
 *@LastVersion : 1.0
 * 2006-09-07 SeongWook Kim
 * 1.0 최초 생성
=========================================================*/
package com.clt.apps.opus.esm.clv.logmanagement.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtDetailVO;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;

/**
 * edm-edm에 대한 DB 처리를 담당<br>
 * - edm-edm Business Logic을 처리하기 위한 JDBC 작업수행.<br>
 * 
 * @author SeongWook Kim
 * @see CodepublishBCImpl 참조
 * @since J2EE 1.4
 */
public class LogManagementDBDAO extends DBDAOSupport {	
	
	/**
	 * Search data
	 * @param logMgmtVO
	 * @return List<LogMgmtVO>
	 * @throws DAOException
	 */
	public List<LogMgmtVO> searchLogMgmtVO(LogMgmtVO logMgmtVO) throws DAOException {
		//Store returning value from Database
		DBRowSet dbRowset = null;
		
		//List of VO
		List<LogMgmtVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			//if logMgmtVO isn't null
			if(logMgmtVO != null){
				Map<String, String> mapVO = logMgmtVO.getColumnValues();
				//add all value to param
				param.putAll(mapVO);
				
				//add all value to velParam
				velParam.putAll(mapVO);
			}
			//Query
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new LogManagementDBDAOComIntgCdRSQL(), param, velParam);
			
			//Convert dbRowset to list
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, LogMgmtVO.class);
		} catch(SQLException se) {
			//Logging exception
			log.error(se.getMessage(),se);
			
			//throw new Exception
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	/**
	 * Search data
	 * @param logMgmtDetailVO
	 * @return List<LogMgmtDetailVO>
	 * @throws DAOException
	 */
	public List<LogMgmtDetailVO> searchLogMgmtDetailVO(LogMgmtDetailVO logMgmtDetailVO) throws DAOException {
		//Store returning value from Database
		DBRowSet dbRowset = null;
		
		//List of VO
		List<LogMgmtDetailVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			//if logMgmtDetailVO isn't null
			if(logMgmtDetailVO != null){
				Map<String, String> mapVO = logMgmtDetailVO.getColumnValues();
				//add all value to param
				param.putAll(mapVO);
				
				//add all value to velParam
				velParam.putAll(mapVO);
			}
			//Query
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new LogManagementDBDAOComIntgCdDtlRSQL(), param, velParam);
			
			//Convert dbRowset to list
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, LogMgmtDetailVO .class);
		} catch(SQLException se) {
			//Logging exception
			log.error(se.getMessage(),se);
			
			//throw new Exception
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	/**
	 * add data
	 * @param logMgmtVO
	 * @return int[]
	 * @throws DAOException
	 * @throws Exception
	 */
	public int[] addLogMgmtVOs(List<LogMgmtVO> logMgmtVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(logMgmtVO.size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new LogManagementDBDAOComIntgCdCSQL(), logMgmtVO,null);
				for(int i = 0; i < insCnt.length; i++){
					if(insCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return insCnt;
	}
	
	/**
	 * add data
	 * @param logMgmtDetailVOs
	 * @return int[]
	 * @throws DAOException
	 * @throws Exception
	 */
	public int[] addLogMgmtDetailVOs(List<LogMgmtDetailVO> logMgmtDetailVOs) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(logMgmtDetailVOs.size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new LogManagementDBDAOComIntgCdDtlCSQL(), logMgmtDetailVOs,null);
				for(int i = 0; i < insCnt.length; i++){
					if(insCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return insCnt;
	}
	
	/**
	 * add data
	 * @param logMgmtVO
	 * @return int[]
	 * @throws DAOException
	 * @throws Exception
	 */
	public int[] updateLogMgmtVOs(List<LogMgmtVO> logMgmtVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(logMgmtVO.size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new LogManagementDBDAOComIntgCdUSQL(), logMgmtVO,null);
				for(int i = 0; i < insCnt.length; i++){
					if(insCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return insCnt;
	}
	
	/**
	 * add data
	 * @param logMgmtDetailVO
	 * @return int[]
	 * @throws DAOException
	 * @throws Exception
	 */
	public int[] updateLogMgmtDetailVOs(List<LogMgmtDetailVO> logMgmtDetailVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(logMgmtDetailVO.size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new LogManagementDBDAOComIntgCdDtlUSQL(), logMgmtDetailVO,null);
				for(int i = 0; i < insCnt.length; i++){
					if(insCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return insCnt;
	}
	
	/**
	 * add data
	 * @param logMgmtVO
	 * @return int[]
	 * @throws DAOException
	 * @throws Exception
	 */
	public int[] removeLogMgmtVOs(List<LogMgmtVO> logMgmtVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(logMgmtVO.size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new LogManagementDBDAOComIntgCdDSQL(), logMgmtVO,null);
				for(int i = 0; i < insCnt.length; i++){
					if(insCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return insCnt;
	}
	
	/**
	 * add data
	 * @param logMgmtDetailVO
	 * @return int[]
	 * @throws DAOException
	 * @throws Exception
	 */
	public int[] removeLogMgmtDetailVOs(List<LogMgmtDetailVO> logMgmtDetailVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(logMgmtDetailVO.size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new LogManagementDBDAOComIntgCdDtlDSQL(), logMgmtDetailVO,null);
				for(int i = 0; i < insCnt.length; i++){
					if(insCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return insCnt;
	}

}