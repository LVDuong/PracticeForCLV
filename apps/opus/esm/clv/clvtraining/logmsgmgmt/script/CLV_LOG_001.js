/*=========================================================
*Copyright(c) 2023 CyberLogitec
*@FileName : CLV_LOG_001.js
*@FileTitle : Log Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2023.04.20
*@LastModifier : 
*@LastVersion : 1.0
* 2023.04.20 
* 1.0 Creation
=========================================================*/
/****************************************************************************************
  이벤트 구분 코드: [초기화]INIT=0; [입력]ADD=1; [조회]SEARCH=2; [리스트조회]SEARCHLIST=3;
					[수정]MODIFY=4; [삭제]REMOVE=5; [리스트삭제]REMOVELIST=6 [다중처리]MULTI=7
					기타 여분의 문자상수  COMMAND01=11; ~ COMMAND20=30;
 ***************************************************************************************/

/*------------------다음 코드는 JSDoc을 잘 만들기 위해서 추가된 코드임 ------------------*/
   /**
     * @fileoverview 업무에서 공통으로 사용하는 자바스크립트파일로 달력 관련 함수가 정의되어 있다.
     * @author 한진해운
     */

    /**
     * @extends 
     * @class CLV_LOG_001 : CLV_LOG_001 생성을 위한 화면에서 사용하는 업무 스크립트를 정의한다.
     */
var tabObjects=new Array();
var tabCnt=0 ;
var beforetab=1;
var sheetObjects=new Array();
var sheetCnt=0;
var rowcount=0;
document.onclick=processButtonClick;

function processButtonClick() {
	/** *** setting sheet object **** */
	var sheetObject1 = sheetObjects[0];
	/** **************************************************** */
	var formObj = document.form;
	try {
		var srcName = ComGetEvent("name");
		if (srcName == null) {
			return;
		}
		switch (srcName) {
			case "btn_Retrieve":
				doActionIBSheet(sheetObject1, formObj, IBSEARCH);
				break;
			case "btn_New":
				resetForm(formObj);
				break;
			case "btn_Save":
				doActionIBSheet(sheetObject1, formObj, IBSAVE);
				break;
			case "btn_DownExcel":
				if (sheetObject1.RowCount() < 1) {// no data
					ComShowCodeMessage("COM132501");
				} else {
					sheetObject1.Down2Excel({ HiddenColumn:1,Merge:1});
				}
				break;
			case "btn_Add":
				doActionIBSheet(sheetObject1, formObj, IBINSERT);
				break;
			default :
				break;
			}
	} catch (e) {
		if (e == "[object Error]") {
			ComShowCodeMessage('JOO00001');
		} else {
			ComShowMessage(e.message);
		}
	}
}

function setSheetObject(sheet_obj) {
	sheetObjects[sheetCnt++] = sheet_obj;
}

/**
 * rest form when click new button
 */
function resetForm(formObj){
	formObj.reset();
	sheetObjects[0].RemoveAll();
	s_jo_crr_cd.SetSelectIndex(0);
}

function loadPage(){
	//generate Grid Layout
	for (i = 0; i < sheetObjects.length; i++) {
		ComConfigSheet(sheetObjects[i]);
		initSheet(sheetObjects[i], i + 1);
		ComEndConfigSheet(sheetObjects[i]);
	}
	
	//auto search data after loading finish.
	doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
}

function initSheet(sheetObj,sheetNo) {
	var cnt = 0;
	var sheetID=sheetObj.id;
	switch (sheetID) {
		case "sheet1": // sheet1 init
			with (sheetObj) {

			var HeadTitle = "|Del|Msg Cd|Msg Type|Msg level|Message|Description";
			var headCount = ComCountHeadTitle(HeadTitle);
			// (headCount, 0, 0, true);z

			SetConfig({SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1});

			var info = {Sort : 1, ColMove : 1, HeaderCheck : 0, ColResize : 1};
			var headers = [ { Text : HeadTitle, Align : "Center" }];
			InitHeaders(headers, info);

			var cols = [ 
	             { Type : "Status", 	Hidden : 1, Width : 50, 	Align : "Center", 	ColMerge : 0, SaveName : "ibflag" }, 
	             { Type : "DelCheck", 	Hidden : 0, Width : 50, 	Align : "Center", 	ColMerge : 0, SaveName : "DEL" },
	             { Type : "Text", 		Hidden : 0, Width : 100, 	Align : "Center", 	ColMerge : 0, SaveName : "err_msg_cd", 	KeyField : 1, Format : "", UpdateEdit : 0, InsertEdit : 1 },
	             { Type : "Combo", 		Hidden : 0, Width : 100, 	Align : "Center", 	ColMerge : 0, SaveName : "err_tp_cd", 	KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1, ComboText:"Server|UI|Both", ComboCode:"S|U|B" }, 
	             { Type : "Combo", 		Hidden : 0, Width : 100, 	Align : "Center", 	ColMerge : 0, SaveName : "err_lvl_cd", 	KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1, ComboText:"ERR|WARNING|INFO", ComboCode:"E|W|I" },
	             { Type : "Text", 		Hidden : 0, Width : 600, 	Align : "Left", 	ColMerge : 0, SaveName : "err_msg", 	KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1, MultiLineText:1 }, 
	             { Type : "Text", 		Hidden : 0, Width : 100, 	Align : "Left", 	ColMerge : 0, SaveName : "err_desc", 	KeyField : 0, Format : "", UpdateEdit : 1, InsertEdit : 1 } 
	             ];

			InitColumns(cols);
			SetWaitImageVisible(0);
			resizeSheet();
		}
		break;
	}

}

function resizeSheet() {
	ComResizeSheet(sheetObjects[0]);
}

function doActionIBSheet(sheetObj,formObj,sAction) {
	switch (sAction) {
	case IBSEARCH: // retrieve
		if(!validateForm(sheetObj,formObj,sAction)) return
		ComOpenWait(true);
		formObj.f_cmd.value = SEARCH;
		sheetObj.DoSearch("CLV_LOG_001GS.do", FormQueryString(formObj) );
		break;
	case IBSAVE: // retrieve
		if(!validateForm(sheetObj,formObj,sAction))return;
		formObj.f_cmd.value = MULTI;
		var result = sheetObj.DoSave("CLV_LOG_001GS.do", FormQueryString(formObj));
		if(result === true) {
			ComShowCodeMessage('COM132601');
		}
//		if(sheetObj.DoSave("CLV_LOG_001GS.do", FormQueryString(formObj))) {
//			ComShowCodeMessage('COM132601');
//		}
		break;
	case IBINSERT: //Row Add button event
		sheetObj.DataInsert(-1);
		break;
	case IBDELETE: //Row Delete button event
		for( var i = sheetObj.LastRow(); i >= sheetObj.HeaderRows(); i-- ) {
			if(sheetObj.GetCellValue(i, "del_chk") == 1){
				sheetObj.SetRowHidden(i, 1);
				sheetObj.SetRowStatus(i,"D");
			}
		}
		break;
	}
}

function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
 	ComOpenWait(false);
}

function sheet1_OnSaveEnd(sheetObj, Code, Msg, StCode, StMsg) { 
	ComOpenWait(false);
}


function validateForm(sheetObj, formObj, sAction) {
    with(formObj){
//      if (!isNumber(formObj.iPage)) {
//          return false;
//      }
  }
  return true;
	
}

/**
 * Program Name Data List OpenWindow. <br>
 * @param {int}	Row	행번호
 * @param {int}	Col	컬럼번호
 **/
function sheet1_OnPopupClick(sheetObj,Row,Col){
    switch (sheetObj.ColSaveName(Col)) {
   	case "dev_dt" :
		var cal=new ComCalendarGrid();
		cal.select(sheetObj, Row, Col, 'yyyyMMdd');
        break;
    case "roles" :
		if(sheetObj.GetCellValue(Row,"ibflag")=="I" || sheetObj.GetCellValue(Row,"ibflag")=="U"){
        	 ComShowCodeMessage('COM12151','Program');
		}else if(sheetObj.GetCellValue(Row,"ibflag")=="R" && sheetObj.GetCellValue(Row,"pgm_mnu_div_cd")=="01"){
			 ComShowCodeMessage('COM12190');
         }else{
        	 alert('wait!!!!!!!!')
         }
         break;
    }
}

function sheet1_OnChange(sheetObj,Row,Col){
	 if(Col == 2){
		var code=sheetObj.GetCellValue(Row, Col);
	    for(var int=1; int < sheetObj.RowCount(); int++) {
		var orlcode=sheetObj.GetCellValue(int, Col);
		/* null 인 경우와 자기 자신은 비교할 필요가 없음 */
			if(code != '' && int != Row && code == orlcode){
				 //ComShowMessage("동일한 Message Code가 존재합니다.");
				 ComShowCodeMessage('COM131302',code);
				 sheetObj.SetCellValue(Row, Col,"");
				 return;
			 }
		 }
	 }
}

function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
 	ComOpenWait(false);
 }

	function CLV_LOG_001() {
    	this.processButtonClick		= tprocessButtonClick;
    	this.setSheetObject 		= setSheetObject;
    	this.loadPage 				= loadPage;
    	this.initSheet 				= initSheet;
    	this.initControl            = initControl;
    	this.doActionIBSheet 		= doActionIBSheet;
    	this.setTabObject 			= setTabObject;
    	this.validateForm 			= validateForm;
    }
    
   	/* 개발자 작업	*/


	/* 개발자 작업  끝 */