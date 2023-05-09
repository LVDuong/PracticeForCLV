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

var tabObjects=new Array();
var tabCnt=0 ;
var beforetab=1;
var sheetObjects=new Array();
var sheetCnt=0;
var rowcount=0;
document.onclick=processButtonClick;

/**
 * handle event onclick
 */
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

/**
 * Add sheet object to array
 */
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

/**
 * Function that is called after the JSP file is loaded
 */
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

/**
 * Sheet configuration
 */
function initSheet(sheetObj,sheetNo) {
	var cnt = 0;
	var sheetID=sheetObj.id;
	switch (sheetID) {
		case "sheet1": // sheet1 init
			with (sheetObj) {
			//Define header title
			//Each | will separate header that show on sheet
			var HeadTitle = "|Del|Msg Cd|Msg Type|Msg level|Message|Description";
			var headCount = ComCountHeadTitle(HeadTitle);

			//SearchMode: Configure search mode (Default: 2)
            //Page: Number of rows to display in one page (Default=20)
            //Lazy load mode, only display 20 rows in one page
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

			//Initialize columns base on above configure
			InitColumns(cols);

			//Check or configure whether to display waiting image during processing.
			SetWaitImageVisible(0);

			//Resize sheet for each screen of user
			ComResizeSheet(sheetObjects[0]);
		}
		break;
	}

}

/**
 * Define transaction logic between UI and server
 *
 * @param sheetObj
 * @param formObj
 * @param sAction
 */
function doActionIBSheet(sheetObj,formObj,sAction) {

	switch (sAction) {
	case IBSEARCH: // retrieve
		//set value for f_cmd, it will be hidden in UI
		formObj.f_cmd.value = SEARCH;

		// ComOpenWait:Whether a loading image will appears and lock the screen
		// true: lock the screen and appear loading image
	    // false: return normal
		ComOpenWait(true);

		//Call Search in server
		sheetObj.DoSearch("CLV_LOG_001GS.do", FormQueryString(formObj) );
		break;
	case IBSAVE: // save
		//set value for f_cmd, it will be hi
		// ComOpenWait:Whether a loading image will appears and lock the screen
		// true: lock the screen and appear loading image
	    // false: return normaldden in UI
		formObj.f_cmd.value = MULTI;
		var result = sheetObj.DoSave("CLV_LOG_001GS.do", FormQueryString(formObj));
		if(result === true) {
			ComShowCodeMessage('COM132601');
		}
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

//Handling event after searching
function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) {
 	ComOpenWait(false);
}

/**
 * Event fires when saving is completed using saving function
 * This event can fire when DoSave function is called.
 * ObjectID_OnSaveEnd(sheetObj, Code, Msg)
 * 	sheetObj: sheet object
 * 	Code: Processing result code (0 or higher is success, others should be processed as error)
 * 	Msg: HTTP response message
 */
function sheet1_OnSaveEnd(sheetObj, Code, Msg, StCode, StMsg) {
	//if success reload page by calling action IBSEARCH
	if(Code>=0){
		doActionIBSheet(sheetObj, document.form, IBSEARCH);
	}
	//otherwise we get all row have status 'I' (Insert)
	var invalidData=sheetObj.FindStatusRow('I');

	//slipt by ';'
	var rows=invalidData.split(';');

	//loop through rows
	for(var i=0;i<rows.length;i++){
		//get value of error message code at current cell
		var msgCd=sheetObj.GetCellValue(rows[i],"err_msg_cd");
		//if it is invalid
		if(Msg.includes(msgCd)){
			//change the row color to red
			sheetObj.SetRowBackColor(rows[i],"#f58167");
		}else{
			//otherwise we change the row color to white
			sheetObj.SetRowBackColor(rows[i],"#ffffff");
		}
	}
}


function validateForm(sheetObj, formObj, sAction) {
	//Check duplicate on Client side
    var findText=sheetObj.FindText("err_msg_cd",msgCd);
    if(findText!=-1&&findText!=sheetObj.GetSelectRow()){
    	 ComShowCodeMessage("COM131302",msgCd);
         return false;
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
	if(sheetObj.ColSaveName(Col) == "err_msg_cd"){
	    validateForm(sheetObj);
	}
}