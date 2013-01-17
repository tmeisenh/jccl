/* $Id$ */
package com.indexoutofbounds.mainframe.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.indexoutofbounds.mainframe.file.layout.FileRecordFieldLayout;
import com.indexoutofbounds.mainframe.file.layout.FileRecordLayout;
import com.indexoutofbounds.mainframe.file.layout.MetaDataType;

public class SimpleRecordConverterTest extends TestCase {
	
	@Test
	public void testConvertRuralRoute() {

		FileRecordLayout recordlayout = new FileRecordLayout(370, 37000, "Rural Route Output Record");

		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "REC_STATUS"));
		recordlayout.addField(new FileRecordFieldLayout(6, MetaDataType.DISPLAY, "FINANCE_NO"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.DISPLAY, "ROUTE_NO"));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "REC_ID"));
		recordlayout.addField(new FileRecordFieldLayout(6, MetaDataType.DISPLAY, "EFF_CYRPP_NO"));

		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "REC_SEQ_NO"));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "BA_CODE"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.DISPLAY, "FINANCIAL_DATA_CTL_NO"));
		recordlayout.addField(new FileRecordFieldLayout(6, MetaDataType.DISPLAY, "PROC_CYRPP_NO"));
		recordlayout.addField(new FileRecordFieldLayout(7, MetaDataType.DISPLAY, "CREATED_CENTURY_DATE"));

		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "FILLER", false));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "ROUTE_LENGTH_MILES"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "BOXES_REGULAR"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "BOXES_CENTRALIZED"));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "NDCBU_COLL_BOXES"));

		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "PARCEL_LOCKERS"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "STOPS"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "LETTERS_PIECES"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "PAPERS_MAGS_CATS_PIECES"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "PARCELS_PIECES"));

		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "BOXHOLDERS_PIECES"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "REG_CERT_INS_PIECES"));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "COD_CUSTOMS_DUE_PIECES"));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "CHG_ADDRESS_PIECES"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "MARKUP_PIECES"));

		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "FORM_3579_3868_PIECES"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "RRL_RTE_DPS_LETTER_CNT"));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "MONEY_ORDER_PIECES"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "LETTERS_FLATS_PIECES"));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "ORD_INS_PARCEL_POST_PIECES"));

		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "COLLECT_REG_CERT_PIECES"));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "POSTAGE_DUE_PIECES"));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "LOAD_TIME_CNT_MINUTES"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "OTHER_SUITABLE_ALLOW_CNT"));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "PURCH_STAMP_STK_CNT"));

		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "RETURN_RECEIPT_CNT"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "AUTH_DISMOUNTS"));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 0, "TOT_DISMOUNT_DISTANCE"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "LOCK_POUCH_WEIGHT"));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "FAMILIES_SERVED"));

		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "LOCK_POUCH_STOPS"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "OFFICE_TIME"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "ROUTE_TIME_LESS_LUNCH"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "TOTAL_DAILY_TIME"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "AUX_OFFICE_TIME"));

		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "AUX_ROUTE_TIME"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "WAIT_AND_COUNT_TIME"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "DETOUR_MILES"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "SEASONAL_MILES"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "SEASONAL_BOXES_REG"));

		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "SEASONAL_BOXES_CENT"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "APT_BUILDINGS_SERVED", false));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "RRL_RTE_SECTOR_SEG_LETTERS"));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "RRL_RTE_ROTATING_RELIEF_IND"));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "RRL_RTE_DEVIATION_MILES_CNT", false));

		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "DELIVER_LETTER_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "DELIVER_FLATS_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RRL_RTE_DELIV_PARCEL_OFFICE_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RRL_RTE_DELIV_PARCEL_RTE_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "WITHOLDING_TIME", false));

		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "STRAPPING_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "LOADING_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RETURN_RECEPIT_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "DISMOUNT_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "BOXHOLDERS_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "COD_CUSTOMS_OFFICE_TIME", false));

		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "DELIVER_REG_INS_OFFICE", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "MARKUP_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "ADDRESS_CHANGE_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "MONEY_ORDER_OFFICE_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "COLLECT_LETTERS_FLATS_TIME", false));

		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "COLLECT_PARCEL_OFFICE_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "STAMPS_OFFICE_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "FORM_3579_3868_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "OTHER_SUITABLE_ALLOW_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "POSTAGE_DUE_OFFICE_TIME", false));

		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "PERSONAL_OFFICE_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "DELIVERY_COD_CUSTOMS_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "DELIVERY_REG_CERT_ROUTE_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "COLLECT_MONEY_ORDER_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "COLLECT_PARCEL_TIME", false));

		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "COLLECT_REG_INS_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "MILES_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "BOXES_REGULAR_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "BOXES_CENTRALIZED_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "NDCBU_COLL_BOXES_TIME", false));

		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "PARCEL_LOCKERS_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "LOCK_POUCH_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RELOAD_UNLOAD_TIME", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RRL_RTE_SECTOR_SEG_MINUTE_COUNT", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RRL_RTE_DPS_MINUTE_COUNT", false));

		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RRL_RTE_GOVT_ADDL_MINUTE_COUNT", false));
		recordlayout.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RRL_RTE_DEVIATION_MINUTE_COUNT", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "ROUTE_120_MINUTE_COUNT", false));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "COMMITMENT_CODE"));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "COUNT_ELECTION", false));

		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "STANDARDS_OFFICE_HRS", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "STANDARDS_OFFICE_MINUTES", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "STANDARDS_ROUTE_HRS", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "STANDARDS_RTE_MINUTES", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "STANDARDS_ROUTE_TOTAL_HOURS", false));

		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "STANDARDS_ROUTE_TOTAL_MINUTES", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "ACTUAL_HOURS", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "ACTUAL_MINUTES", false));
		recordlayout.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "BOX_FACTOR", false));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "COUNT_TYPE_CODE", false));

		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "RRL_RTE_NEGATION_ELG_INT", false));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "WITHDRAWING_MAIL_CODE", false));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "CENTRAL_MARKUP_CODE", false));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "DETOUR_ROUTE_IND"));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "SEASONAL_RTE_IND"));

		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "IN_SEASON_IND"));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "EM_SIGNAL"));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "CONVERT_TO_REGULAR_IND"));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "AMEND_SIGNAL", false));
		recordlayout.addField(new FileRecordFieldLayout(8, MetaDataType.DISPLAY, "ROUT_COUNT_BEGIN_CENTURY_DATE", false));

		recordlayout.addField(new FileRecordFieldLayout(8, MetaDataType.DISPLAY, "EFF_CENTURY_DATE", false));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "HIGH_LOW_OPTION"));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.PACKED_DECIMAL, 0, "COUNT_LENGTH", false));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "PAY_ROUTE_TYPE", false));
		recordlayout.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "STEP", false));

		recordlayout.addField(new FileRecordFieldLayout(8, MetaDataType.DISPLAY, "USER_ID", false));
		recordlayout.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "USER", false));
		/*
		FlatFileLayout file = new FlatFileLayout("/Volumes/User/travis/Documents/code/jcc/jccl/cobol_files/rural_route_binary_big.txt", recordlayout);
		
		try {
			IRecordConverter src = new SimpleCOBOLToAsciiRecordConverter(file);
				
			Record filerecord;
			int count = 0;
			// This takes awhile, so we're stopping at ten.
			while ( (filerecord = src.getNextRecord()) != null & count++ > 100) {
				//System.out.println(filerecord.toString());
				//System.out.println(filerecord.getFieldByName("ROUTE_LENGTH_MILES"));
				assertNotNull("Testing file record for null", filerecord);
			}
			
		} catch (MainFrameConversionException e) {
			e.printStackTrace();
		}
		*/
	}
}
