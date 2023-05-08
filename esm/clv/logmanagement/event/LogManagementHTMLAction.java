package com.clt.apps.opus.esm.clv.logmanagement.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtCondVO;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtDtlVO;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtMstVO;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

public class LogManagementHTMLAction extends HTMLActionSupport{
	
	/**
	 * LogManagementHTMLAction 객체를 생성
	 */
	public LogManagementHTMLAction() {
	}
	
	/**
	 * HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
	 * HttpRequst의 정보를 CLV_LOG_002Event로 파싱하여 request에 셋팅<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @return Event Event interface를 구현한 객체
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		
		FormCommand command = FormCommand.fromRequest(request);
    	LogManagementEvent event = new LogManagementEvent();
		
		if(command.isCommand(FormCommand.SEARCH01)) {
			event.setLogMgmtCondVO((LogMgmtCondVO)getVO(request, LogMgmtCondVO .class));
		}else if(command.isCommand(FormCommand.SEARCH02)) {
			event.setLogMgmtCondVO((LogMgmtCondVO)getVO(request, LogMgmtCondVO .class));
		}else if(command.isCommand(FormCommand.MULTI)) { 
			event.setLogMgmtMstVOs((LogMgmtMstVO[])getVOs(request, LogMgmtMstVO .class, "sheet1_"));
			event.setLogMgmtDtlVOs((LogMgmtDtlVO[])getVOs(request, LogMgmtDtlVO .class, "sheet2_"));
		}	
		
		return event;
		
	}
	
	/**
	 * HttpRequest의 attribute에 업무시나리오 수행결과 값 저장<br>
	 * ServiceCommand에서 View(JSP)로 실행결과를 전송하는 ResultSet을 request에 셋팅<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param eventResponse EventResponse interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
		request.setAttribute("EventResponse", eventResponse);
	}

	/**
	 * HttpRequest의 attribute에 HttpRequest 파싱 수행결과 값 저장<br>
	 * HttpRequest 파싱 수행결과 값 request에 셋팅<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param event Event interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, Event event) {
		request.setAttribute("Event", event);
	}
}
