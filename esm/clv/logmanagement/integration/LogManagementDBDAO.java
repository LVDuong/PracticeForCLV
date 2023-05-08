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

import com.clt.apps.opus.esm.clv.logmanagement.event.LogManagementEvent;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtDtlVO;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtMstVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
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
	 * Codepublish의 모든 목록을 가져온다.<br>
	 * 
	 * @param e
	 * @return DBRowSet DB 처리 결과
	 * @throws DAOException
	 */
	public DBRowSet searchAPPCodeList(Event e) throws DAOException {
		DBRowSet dbRowset = null;
		LogManagementEvent event = (LogManagementEvent) e;
		// form 조회조건
		String subsystem = event.getLogMgmtCondVO().getSubsystem().toUpperCase(); 
		String searchCdTp = event.getLogMgmtCondVO().getSearchCdTp();
		String searchtype = event.getLogMgmtCondVO().getSearchtype();
		String codeVal = event.getLogMgmtCondVO().getCodeVal().toUpperCase(); 
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("subsystem", subsystem);
		param.put("codeVal", codeVal);
		param.put("searchCdTp", searchCdTp);
		Map<String, Object> velParam = new HashMap<String, Object>();
		velParam.put("subsystem", subsystem);
		velParam.put("searchCdTp", searchCdTp);
		velParam.put("searchtype", searchtype);
		velParam.put("codeVal", codeVal);
		try {
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new LogManagementDBDAOComIntgCdRSQL(), param, velParam);
		}catch(SQLException se){
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return dbRowset;
	}
	
	/**
	 * searchSubSystemCodeList<br>
	 * 
	 * @return String[]
	 * @Exception DAOException
	 */
	public String[] searchSubSystemCodeList() throws DAOException {
		String[] list = null;
		try {
			DBRowSet dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new LogManagementDBDAOsearchSubSystemCdRSQL(), null, null);
			list = new String[dbRowset.getRowCount()];
			int idx = 0;
			while(dbRowset.next()){
				list[idx++] = dbRowset.getString("sub_sys_cd");
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	/**
	 * searchMaxIntgCdId<br>
	 * 
	 * @return String[]
	 * @Exception DAOException
	 */
//	public String searchMaxIntgCdId() throws DAOException {
//		String resultStr = null;
//		try {
//			DBRowSet dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate) new LogManagementDBDAOsearchMaxIntgCdIdRSQL(), null, null);
//			if ( dbRowset!=null && dbRowset.next() ){
//				resultStr = dbRowset.getString("max_intg_cd_id");
//			}
//			
//		} catch (SQLException se) {
//			log.error(se.getMessage(), se);
//			throw new DAOException(new ErrorHandler(se).getMessage());
//		} catch (Exception ex) {
//			log.error(ex.getMessage(), ex);
//			throw new DAOException(new ErrorHandler(ex).getMessage());
//		}
//		return resultStr;
//	}
	
	/**
	 * Codepublish의 모든 목록을 가져온다.<br>
	 * 
	 * @param e
	 * @return DBRowSet DB 처리 결과
	 * @throws DAOException
	 */
	public DBRowSet searchAPPCodeDetailList(Event e) throws DAOException {
		DBRowSet dbRowset = null;
		LogManagementEvent event = (LogManagementEvent) e;
		// form 조회조건
		String codeid = event.getLogMgmtCondVO().getCodeid().toUpperCase(); 
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("codeid", codeid);
		try {
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new LogManagementDBDAOComIntgCdDtlRSQL(), param, null);
		}catch(SQLException se){
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return dbRowset;
	}

	/**
	 * Log Management - Save - Update<br>
	 * 
	 * @param List<LogMgmtMstVO> updModels
	 * @throws SQLException 
	 */
	public void modifyLogMgmtMst(List<LogMgmtMstVO> updModels) throws SQLException, Exception {
		try	{
			SQLExecuter sqlExe = new SQLExecuter("");
			int updCnt[] = null;
			if(updModels.size() > 0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new LogManagementDBDAOmodifyCodeMgmtMstUSQL(), updModels, null);
				for(int i = 0; i < updCnt.length; i++){
					if(updCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}		
		}catch (SQLException se){
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		}catch (Exception ex){
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
	}

	/**
	 * Log Management - Save - Update<br>
	 * 
	 * @param List<LogMgmtDtlVO> updModels
	 * @throws SQLException 
	 */
	public void modifyLogMgmtDtl(List<LogMgmtDtlVO> updModels) throws SQLException, Exception {
		try	{
			SQLExecuter sqlExe = new SQLExecuter("");
			int updCnt[] = null;
			if(updModels.size() > 0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new LogManagementDBDAOmodifyCodeMgmtDtlUSQL(), updModels, null);
				for(int i = 0; i < updCnt.length; i++){
					if(updCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}		
		}catch (SQLException se){
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		}catch (Exception ex){
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
	}
	
	/**
	 * 
	 * 코드 테이블을 삭제한다.<br>
	 * @param List<LogMgmtMstVO> delModels
	 * @throws DAOException
	 */
	public void removeLogMgmtMst(List<LogMgmtMstVO> delModels) throws DAOException,Exception {
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			int delCnt[] = null;
			if(delModels.size() > 0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new LogManagementDBDAOComIntgCdDtlDSQL(), delModels,null);
				for(int i = 0; i < 1; i++){
					if(delCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
			if(delModels.size() > 0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new LogManagementDBDAOComIntgCdDSQL(), delModels,null);
				for(int i = 0; i < 1; i++){
					if(delCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
	}

	/**
	 * 
	 * 코드 테이블을 삭제한다.<br>
	 * @param List<LogMgmtDtlVO> delModels
	 * @throws DAOException
	 */
	public void removeLogMgmtDtl(List<LogMgmtDtlVO> delModels) throws DAOException,Exception {
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			int delCnt[] = null;
			if(delModels.size() > 0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new LogManagementDBDAOComIntgCdDtlDSQL(), delModels,null);
				for(int i = 0; i < 1; i++){
					if(delCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
	}
	
	/**
	 * 
	 * 코드 데이터를 생성한다.<br>
	 * 
	 * @param List<LogMgmtMstVO> insModels
	 * @throws DAOException 
	 */
	public void addAPPCodeList(List<LogMgmtMstVO> insModels) throws DAOException,Exception {
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			int insCnt[] = null;
			if(insModels.size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new LogManagementDBDAOComIntgCdCSQL(), insModels,null);
				for(int i = 0; i < insCnt.length; i++){
					if(insCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch (SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}		
	}
	
	/**
	 * 
	 * 코드 데이터를 생성한다.<br>
	 * 
	 * @param List<LogMgmtDtlVO> insModels
	 * @throws DAOException 
	 */
	public void addAPPCodeDetailList(List<LogMgmtDtlVO> insModels) throws DAOException,Exception {
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			int insCnt[] = null;
			if(insModels.size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new LogManagementDBDAOComIntgCdDtlCSQL(), insModels,null);
				for(int i = 0; i < insCnt.length; i++){
					if(insCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch (SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}		
	}
	
	/**
	 * Default Currency Creation의 Duplication Checking<br>
	 * 
	 * @param inputVO
	 * @return
	 * @throws DAOException
	 */
	public String searchDupChkLogMgmtMst(LogMgmtMstVO inputVO) throws DAOException {
		DBRowSet dbRowset = null;
		String dupFlg = "";
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		try{
			if( inputVO != null ){
				Map<String, String> mapVO = inputVO .getColumnValues();
				 
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new LogManagementDBDAOsearchDupChkCodeMgmtMstRSQL(), param, velParam);
			if ( dbRowset!=null && dbRowset.next() ){
				dupFlg = dbRowset.getString(1);
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		
		return dupFlg;
	}
	
	/**
	 * Default Currency Creation의 Duplication Checking<br>
	 * 
	 * @param inputVO
	 * @return
	 * @throws DAOException
	 */
	public String searchDupChkLogMgmtDtl(LogMgmtDtlVO inputVO) throws DAOException {
		DBRowSet dbRowset = null;
		String dupFlg = "";
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		try{
			if( inputVO != null ){
				Map<String, String> mapVO = inputVO .getColumnValues();
				 
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new LogManagementDBDAOsearchDupChkCodeMgmtDtlRSQL(), param, velParam);
			if ( dbRowset!=null && dbRowset.next() ){
				dupFlg = dbRowset.getString(1);
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		
		return dupFlg;
	}

}