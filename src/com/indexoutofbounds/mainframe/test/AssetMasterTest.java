/*
 * Created on Feb 22, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.indexoutofbounds.mainframe.test;

import com.indexoutofbounds.mainframe.converter.IRecordConverter;
import com.indexoutofbounds.mainframe.converter.impl.SimpleRecordConverter;
import com.indexoutofbounds.mainframe.exception.MainFrameConversionException;
import com.indexoutofbounds.mainframe.exception.Sock7Exception;
import com.indexoutofbounds.mainframe.file.Record;
import com.indexoutofbounds.mainframe.file.RecordComparator;
import com.indexoutofbounds.mainframe.file.layout.FileRecordFieldLayout;
import com.indexoutofbounds.mainframe.file.layout.FileRecordLayout;
import com.indexoutofbounds.mainframe.file.layout.MetaDataType;

/**
 * AssetMaster will fail in the translation because the COBOL file is bad.
 */
public class AssetMasterTest {

	public static void main(String[] args) throws Throwable {

		FileRecordLayout recordlayout = new FileRecordLayout(200, "ASSET_MASTER");

		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.NUMBER, "NAS-REGION", false));
		recordlayout.addField(new FileRecordFieldLayout(7, MetaDataType.DISPLAY, "NAS-VEH-NUM", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-DISTRICT", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.NUMBER, "NAS-YEAR", true));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-FIN-P1", true));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.DISPLAY, "NAS-FIN-P2", true));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-GROUP", true));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-MODEL", true));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-MAKE-CODE", true));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-CAPACITY", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-BODY-TYPE", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-DRIVE", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-NUM-AXLE", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-NUM-DRIVE-AXLE", true));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.NUMBER, "NAS-SERV-LIFE", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-TYPE-ACQ", true));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.NUMBER, "NAS-ACQ-AP", true));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.NUMBER, "NAS-ACQ-FY", true));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, "NAS-ASSET-VALUE", true)); //?
		recordlayout.addField(new FileRecordFieldLayout(5, MetaDataType.PACKED_DECIMAL, 2, "NAS-SALE-AMT", false));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "NAS-SALE-PREP-EXP", true));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-DISPL-AP", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-DISPL-FY", false));
		recordlayout.addField(new FileRecordFieldLayout(7, MetaDataType.DISPLAY, "NAS-REPL-VEH-NUM", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-CURR-YR-ACQ-FLAG", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-STOR-AP", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-STOR-FY", false));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-TYPE-DISPL", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-REASON-REPL", true));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-TRANS-AP", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-TRANS-FY", false));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "FILLER1", false));
		recordlayout.addField(new FileRecordFieldLayout(17, MetaDataType.DISPLAY, "NAS-CHASSIS-NUM", true));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, "NAS-AP-DEPRE", false));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, "NAS-DEPRE-ADJ", true));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.NUMBER, "NAS-APS-SERVLIFE", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-LOC-P1", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-LOC-P2", true));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "NAS-OUT-SALE-EXP", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.DISPLAY, "NAS-SALE-NUM", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-TYPE-SALE", true));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-RESERVED", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, "NAS-ASST-VAL-INCR", false));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-TRANS-CODE", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.NUMBER, "NAS-TRANSIT-APS", false));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-STOR-REASON", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-STAT-CODE", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-VEH-TYPE", true));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.NUMBER, 2, "NAS-SALVG-PCT", false));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-VEH-COND", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-WARR-EXP-AP", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-WARR-EXP-FY", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.NUMBER, "NAS-AGE", false));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-TYPE-TRANSFER", true));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, "NAS-ACCUM-DEPRE", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, "NAS-SALVG-VAL", true));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, "NAS-UNDEPRE-BAL", false));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.NUMBER, "NAS-REMAIN-DEPRE-APS", false));
		recordlayout.addField(new FileRecordFieldLayout(15, MetaDataType.DISPLAY, "NAS-CNTRCT-PUR-ORD", true));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, "NAS-BOOK-VAL", false));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, "NAS-FREIGHT", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-STORG-CODE", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-TRANSFER-REASON", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-TYPE-CHANGE", true));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "NAS-DISPL-GAIN-LOSS", true));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, "NAS-EQUIP-COST", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-TYPE-TRANSFER-PDC", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-FUNC-MAST", true));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "NAS-AREA-MAST", true));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.DISPLAY, "NAS-NEWDIST", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "NAS-FUNCTION-CODE", true));
		recordlayout.addField(new FileRecordFieldLayout(15, MetaDataType.DISPLAY, "FILLER2", false));

		//FlatFileLayout layout = new FlatFileLayout("/T:/Team_9229/VOIS/Data Migration/Test Migration/Test Data/Mainframe BIN/AEANYN.PS600T01.AEAMAST.BACKUP.AP0306", recordlayout);

		try {
			IRecordConverter src =
				new SimpleRecordConverter(
					"/T:/Team_9229/VOIS/Data Migration/Test Migration/Test Data/Mainframe BIN/AEANYN.PS600T01.AEAMAST.BACKUP.AP0306",
					recordlayout);

			Record r;

			while ((r = src.getNextRecord()) != null) {
				
			//while ((r = getRecord(src, false)) != null) {
				//System.out.println(filerecord.toString());
				//System.out.println(r.getFieldByName("NAS-YEAR").getValue());
				System.out.println("Converted (AssetMasterTest.java) " + r.getRecordNumber());

//				if (r.getRecordNumber() == 145246 || r.getRecordNumber() == 120290) {//89
//					System.out.println("WTF: 2 " + System.currentTimeMillis());
//					System.exit(0);
//				}
			}

		} catch (MainFrameConversionException e) {
			e.printStackTrace();
			//throw e;
		}

		System.out.println("DONE");
	}

//	public static Record getRecord(IRecordConverter con, boolean f) throws MainFrameConversionException {
//
//		try {
//
//			if(f)
//				System.out.println("wtf 3");
//				
//			return con.getNextRecord();
//
//		} catch (Sock7Exception e) {
//			System.out.println("wtf 1"+ System.currentTimeMillis());
//			return getRecord(con, true);
//		}
//	}
}
