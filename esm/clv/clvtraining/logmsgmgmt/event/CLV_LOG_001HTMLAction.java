/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : CLV_LOG_001HTMLAction.java
*@FileTitle : Log Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2023.04.20
*@LastModifier : 
*@LastVersion : 1.0
* 2023.04.20 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.clvtraining.logmsgmgmt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

import com.clt.apps.opus.esm.clv.clvtraining.logmsgmgmt.vo.ErrMsgVO;

/**
 * HTTP Parser<br>
 * - com.clt.apps.opus.esm.clv.clvtraining 화면을 통해 서버로 전송되는 HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
 * - Parsing 한 정보를 Event로 변환, request에 담아 ClvTrainingSC로 실행요청<br>
 * - ClvTrainingSC에서 View(JSP)로 실행결과를 전송하는 EventResponse를 request에 셋팅<br>
 * @author DuongLe
 * @see ClvTrainingEvent 참조
 * @since J2EE 1.6
 */

public class CLV_LOG_001HTMLAction extends HTMLActionSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * CLV_LOG_001HTMLAction 객체를 생성
	 */
	public CLV_LOG_001HTMLAction() {}

	/**
	 * HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
	 * HttpRequst의 정보를 ClvTrainingEvent로 파싱하여 request에 셋팅<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event Event interface를 구현한 객체
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {

		FormCommand command = FormCommand.fromRequest(request);
		ClvLog001Event event = new ClvLog001Event();

		ErrMsgVO errMsgVO = new ErrMsgVO();
		errMsgVO.setErrMsgCd(request.getParameter("s_err_msg_cd"));
		errMsgVO.setErrMsg(request.getParameter("s_err_msg"));
//		errMsgVO.setErrMsgCd(JSPUtil.getParameter(request, "s_err_msg_cd", ""));
//		errMsgVO.setErrMsg(JSPUtil.getParameter(request, "s_err_msg", ""));

		if(command.isCommand(FormCommand.MULTI)) {
			event.setErrMsgVOS((ErrMsgVO[])getVOs(request, ErrMsgVO .class,""));
		}
		else if(command.isCommand(FormCommand.SEARCH)) {
			event.setErrMsgVO(errMsgVO);
		}

		return  event;
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