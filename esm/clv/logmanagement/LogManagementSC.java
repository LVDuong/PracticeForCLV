/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : LogManagementSC.java
*@FileTitle : Log Management
*Open Issues :
*Change history :
*@LastModifyDate : 2023-05-05
*@LastModifier : 
*@LastVersion : 1.0
* 2023-05-05
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.logmanagement;

import java.util.Arrays;
import java.util.List;

import com.clt.apps.opus.esm.clv.logmanagement.basic.LogManagementBC;
import com.clt.apps.opus.esm.clv.logmanagement.basic.LogManagementBCImpl;
import com.clt.apps.opus.esm.clv.logmanagement.event.LogManagementEvent;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtDetailVO;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

/**
 * edm-edm Business Logic ServiceCommand<br>
 * 
 * @author Duong Le
 * @see LogManagementDBDAO
 * @since J2EE 1.8
 */
public class LogManagementSC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;

	/**
	 * edm 업무 시나리오 선행작업<br>
	 * Codepublish업무 시나리오 호출시 관련 내부객체 생성<br>
	 */
	public void doStart() {
		try {
			account=getSignOnUserAccount();
		} catch (Exception e) {
			log.error("LogManagementSC 선행 작업 시 오류 " + e.toString(), e);
		}
	}

	/**
	 * edm 업무 시나리오 마감작업<br>
	 * Codepublish업무 시나리오 종료시 관련 내부객체 해제<br>
	 */
	public void doEnd() {
		// command.doEnd();
		log.debug("LogManagementSC 종료");
	}

	/**
	 * 각 이벤트에 해당하는 업무 시나리오 수행<br>
	 * edm-edm 업무에서 발생하는 모든 이벤트의 분기처리<br>
	 * 
	 * @param e Event
	 * @return response EventResponse
	 * @exception EventException
	 */
	public EventResponse perform(Event e) throws EventException {
		// RDTO(Data Transfer Object including Parameters)
		EventResponse eventResponse = null;
		
		// SC가 여러 이벤트를 처리하는 경우 사용해야 할 부분
		if (e.getEventName().equalsIgnoreCase("LogManagementEvent")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH01)) { 
				eventResponse = searchLogMgmtVO(e);
			} else if (e.getFormCommand().isCommand(FormCommand.SEARCH02)) {
				eventResponse = searchLogMgmtDetailVO(e);
			} else if (e.getFormCommand().isCommand(FormCommand.MULTI)) { //DELETE,INSERT APP COM_INTG_CDL
				eventResponse = manageLogMgmtVO(e);
			} else if (e.getFormCommand().isCommand(FormCommand.MULTI01)) { //DELETE,INSERT APP COM_INTG_CD_DTL
				eventResponse = manageLogMgmtDetailVO(e);
			} 
		}
		return eventResponse;
	}

	private EventResponse searchLogMgmtVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		LogManagementEvent event = (LogManagementEvent)e;
		LogManagementBC command = new LogManagementBCImpl();

		try{
			List<LogMgmtVO> list = command.searchLogMgmtVO(event.getLogMgmtVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	private EventResponse searchLogMgmtDetailVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		LogManagementEvent event = (LogManagementEvent)e;
		LogManagementBC command = new LogManagementBCImpl();

		try{
			List<LogMgmtDetailVO> list = command.searchLogMgmtDetailVO(event.getLogMgmtDetailVO());
			eventResponse.setRsVoList(list);
			eventResponse.setETCData("hasValue", list.size()==0?"false":"true");
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	private EventResponse manageLogMgmtVO(Event e) throws EventException {
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		LogManagementEvent event = (LogManagementEvent)e;
		LogManagementBC command = new LogManagementBCImpl();
		try{
			begin();
			command.manageLogMgmtVO(event.getLogMgmtVOs(),account);
			eventResponse.setUserMessage(new ErrorHandler("XXXXX").getUserMessage());
			commit();
		} catch(EventException ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch(Exception ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		return eventResponse;
	}
	
	private EventResponse manageLogMgmtDetailVO(Event e) throws EventException {
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		LogManagementEvent event = (LogManagementEvent)e;
		LogManagementBC command = new LogManagementBCImpl();
		try{
			begin();
			command.manageLogMgmtDetailVO(event.getLogMgmtDetailVOs(),account);
			eventResponse.setUserMessage(new ErrorHandler("DOU00001").getUserMessage());
			commit();
		} catch(EventException ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch(Exception ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		return eventResponse;
	}
	
}