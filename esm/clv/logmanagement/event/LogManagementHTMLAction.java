/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : LogManagementHTMLAction.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2023-05-05
*@LastModifier : 
*@LastVersion : 1.0
* 2023-05-05 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.logmanagement.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtDetailVO;
import com.clt.apps.opus.esm.clv.logmanagement.vo.LogMgmtVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

public class LogManagementHTMLAction extends HTMLActionSupport{
	
	//The serialVersionUID attribute is an identifier that is used to serialize/deserialize an object
	private static final long serialVersionUID = 1L;
		
	/**
	 * LogManagementHTMLAction 객체를 생성
	 */
	public LogManagementHTMLAction() {}
	
	/**
	 * HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
	 * HttpRequst의 정보를 CLV_LOG_002Event로 파싱하여 request에 셋팅<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @return Event Event interface를 구현한 객체
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		
		//Get f_command
		FormCommand command = FormCommand.fromRequest(request);
		
    	LogManagementEvent event = new LogManagementEvent();
		
		if(command.isCommand(FormCommand.SEARCH01)) {
			//Create LogMgmtVO object
			LogMgmtVO logMgmtVO = new LogMgmtVO();
			
			//set parameter has name s_intg_cd_id for logMgmtVO
			logMgmtVO.setIntgCdId(JSPUtil.getParameter(request, "s_intg_cd_id",""));
			//set parameter has name s_ownr_sub_sys_cd for logMgmtVO
			logMgmtVO.setOwnrSubSysCd(JSPUtil.getParameter(request, "s_ownr_sub_sys_cd",""));
			//set VO for event
			event.setLogMgmtVO(logMgmtVO);
		}else if(command.isCommand(FormCommand.SEARCH02)) {
			LogMgmtDetailVO logMgmtDetailVO = new LogMgmtDetailVO();
			
			//set parameter has name s_intg_cd_id for logMgmtVO
			logMgmtDetailVO.setIntgCdId(JSPUtil.getParameter(request, "s_intg_cd_id",""));
			//set VO for event
			event.setLogMgmtDetailVO(logMgmtDetailVO);
		}else if(command.isCommand(FormCommand.MULTI)) { 
//			event.setLogMgmtVOs((LogMgmtVO[])getVOs(request, LogMgmtVO.class,""));
			event.setLogMgmtVOs((LogMgmtVO[])getVOs(request, LogMgmtVO .class, "sheet1_"));
		}else if(command.isCommand(FormCommand.MULTI01)) {
			event.setLogMgmtDetailVOs((LogMgmtDetailVO[])getVOs(request, LogMgmtDetailVO.class,"sheet2_"));
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
		//set attribute has key Event for request
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
		//set attribute has key Event for request
		request.setAttribute("Event", event);
	}
}
