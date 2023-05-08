/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : ClvLog001Event.java
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

import com.clt.framework.support.layer.event.EventSupport;
import com.clt.apps.opus.esm.clv.clvtraining.logmsgmgmt.vo.ErrMsgVO;


/**
 * CLV_LOG_001 에 대한 PDTO(Data Transfer Object including Parameters)<br>
 * -  CLV_LOG_001HTMLAction에서 작성<br>
 * - ServiceCommand Layer로 전달하는 PDTO로 사용<br>
 *
 * @author DuongLe
 * @see CLV_LOG_001HTMLAction 참조
 * @since J2EE 1.6
 */

public class ClvLog001Event extends EventSupport {

	private static final long serialVersionUID = 1L;

	/** Table Value Object 조회 조건 및 단건 처리  */
	ErrMsgVO errMsgVO = null;

	/** Table Value Object Multi Data 처리 */
	ErrMsgVO[] errMsgVOs = null;

	public ClvLog001Event(){}

	public void setErrMsgVO(ErrMsgVO errMsgVO){
		this. errMsgVO = errMsgVO;
	}

	public void setErrMsgVOS(ErrMsgVO[] errMsgVOs){
		this. errMsgVOs = errMsgVOs;
	}

	public ErrMsgVO getErrMsgVO(){
		return errMsgVO;
	}

	public ErrMsgVO[] getErrMsgVOS(){
		return errMsgVOs;
	}

}