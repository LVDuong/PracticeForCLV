/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : CarrierManagementSC.java
*@FileTitle : Carrier Management
*Open Issues :
*Change history :
*@LastModifyDate : 2023-05-09
*@LastModifier : 
*@LastVersion : 1.0
* 2023-05-09 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.java.carriermanagement;

import java.util.List;

import com.clt.apps.opus.esm.clv.carriermanagement.basic.CarrierMgmtBC;
import com.clt.apps.opus.esm.clv.carriermanagement.basic.CarrierMgmtBCImpl;
import com.clt.apps.opus.esm.clv.carriermanagement.event.Practice0004Event;
import com.clt.apps.opus.esm.clv.carriermanagement.vo.CarrierVO;
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
public class CarrierManagementSC extends ServiceCommandSupport {
	
	// Login User Information
		private SignOnUserAccount account = null;

		/**
		 * DouTraining system 업무 시나리오 선행작업<br>
		 * 업무 시나리오 호출시 관련 내부객체 생성<br>
		 */
		public void doStart() {
			log.debug("DouTrainingSC 시작");
			try {
				//Authenticate
				account = getSignOnUserAccount();
			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
			}
		}

		/**
		 * DouTraining system 업무 시나리오 마감작업<br>
		 * 업무 시나리오 종료시 관련 내부객체 해제<br>
		 */
		public void doEnd() {
			log.debug("DouTrainingSC 종료");
		}

		/**
		 * 각 이벤트에 해당하는 업무 시나리오 수행<br>
		 * ALPS-DouTraining system 업무에서 발생하는 모든 이벤트의 분기처리<br>
		 * 
		 * @param e Event
		 * @return EventResponse
		 * @exception EventException
		 */
		public EventResponse perform(Event e) throws EventException {
			// RDTO(Data Transfer Object including Parameters)
			EventResponse eventResponse = null;

			// SC가 여러 이벤트를 처리하는 경우 사용해야 할 부분
			if(e.getEventName().equalsIgnoreCase("Practice0004Event")){
				if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
					eventResponse = searchCarrierVO(e);
				}
				else if (e.getFormCommand().isCommand(FormCommand.DEFAULT)) {
					eventResponse = initCombox();
				}else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
					eventResponse = manageCarrierVO(e);
				}else if (e.getFormCommand().isCommand(FormCommand.SEARCH01)) {
					eventResponse = searchCust(e);
				}
			}
			return eventResponse;
		}

		private EventResponse manageCarrierVO(Event e) throws EventException {
			GeneralEventResponse eventResponse = new GeneralEventResponse();
			Practice0004Event event = (Practice0004Event)e;
			CarrierMgmtBC command = new CarrierMgmtBCImpl();
			try{
				begin();
				command.manageCarrierVO(event.getCarrierVOs(),account);
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

		private EventResponse initCombox() throws EventException{
			GeneralEventResponse eventResponse = new GeneralEventResponse();
			CarrierMgmtBC command = new CarrierMgmtBCImpl();

			try{
				List<CarrierVO> crrCdList = command.getCrrCds();
				List<CarrierVO> lnCdList = command.getLnCds();
				StringBuilder crrCds=new StringBuilder();
				StringBuilder lnCds=new StringBuilder();
				if(crrCdList.size()!=0){
					for(int i=0;i<crrCdList.size();i++){
						crrCds.append(crrCdList.get(i).getJoCrrCd()+"|");
					}
					crrCds.deleteCharAt(crrCds.length()-1);
				}
				if(lnCdList.size()!=0){
					for(int i=0;i<lnCdList.size();i++){
						lnCds.append(lnCdList.get(i).getRlaneCd()+"|");
					}
					lnCds.deleteCharAt(lnCds.length()-1);
				}
				eventResponse.setETCData("crrCds",crrCds.toString());
				eventResponse.setETCData("lnCds",lnCds.toString());
			}catch(EventException ex){
				throw new EventException(new ErrorHandler(ex).getMessage(),ex);
			}catch(Exception ex){
				throw new EventException(new ErrorHandler(ex).getMessage(),ex);
			}	
			return eventResponse;
		}
		
		private EventResponse searchCarrierVO(Event e) throws EventException {
			// PDTO(Data Transfer Object including Parameters)
			GeneralEventResponse eventResponse = new GeneralEventResponse();
			Practice0004Event event = (Practice0004Event)e;
			CarrierMgmtBC command = new CarrierMgmtBCImpl();

			try{
				List<CarrierVO> list = command.searchCarrierVO(event.getCarrierVO());
				eventResponse.setRsVoList(list);
			}catch(EventException ex){
				throw new EventException(new ErrorHandler(ex).getMessage(),ex);
			}catch(Exception ex){
				throw new EventException(new ErrorHandler(ex).getMessage(),ex);
			}	
			return eventResponse;
		}
		
		private EventResponse searchCust(Event e) throws EventException {
			// PDTO(Data Transfer Object including Parameters)
			GeneralEventResponse eventResponse = new GeneralEventResponse();
			Practice0004Event event = (Practice0004Event)e;
			CarrierMgmtBC command = new CarrierMgmtBCImpl();

			try{
				List<CarrierVO> list = command.searchCust(event.getCarrierVO());
				eventResponse.setRsVoList(list);
			}catch(EventException ex){
				throw new EventException(new ErrorHandler(ex).getMessage(),ex);
			}catch(Exception ex){
				throw new EventException(new ErrorHandler(ex).getMessage(),ex);
			}	
			return eventResponse;
		}

}
