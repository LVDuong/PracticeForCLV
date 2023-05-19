/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : Practice0003Event.java
*@FileTitle : Money Management
*Open Issues :
*Change history :
*@LastModifyDate : 2023.05.15
*@LastModifier : 
*@LastVersion : 1.0
* 2023.05.15 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.moneymgmt.event;

import com.clt.apps.opus.esm.clv.moneymgmt.vo.ConditionVO;
import com.clt.apps.opus.esm.clv.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.moneymgmt.vo.SummaryVO;
import com.clt.framework.support.layer.event.EventSupport;

/**
 * Practice0003Event for PDTO(Data Transfer Object including Parameters)<br>
 * - Created from PRACTICE_0003HTMLAction<br>
 * - Used as PDTO delivered to ServiceCommand Layer<br>
 *
 * @author Duong Le
 * @see PRACTICE_0003HTMLAction refer
 * @since J2EE 1.8
 */
public class Practice0003Event extends EventSupport {
	
	private static final long serialVersionUID = 1L;

	/** Table Value Object 조회 조건 및 단건 처리  */
	SummaryVO summaryVO = null;
	
	DetailVO detailVO = null;
	
	ConditionVO conditionVO = null;

	public ConditionVO getConditionVO() {
		return conditionVO;
	}

	public void setConditionVO(ConditionVO conditionVO) {
		this.conditionVO = conditionVO;
	}

	public Practice0003Event(){}
	
	public void setSummaryVO(SummaryVO summaryVO){
		this. summaryVO = summaryVO;
	}

	public void setDetailVO(DetailVO detailVO) {
		this.detailVO = detailVO;
	}

	public SummaryVO getSummaryVO(){
		return summaryVO;
	}

	public DetailVO getDetailVO() {
		return detailVO;
	}

}
