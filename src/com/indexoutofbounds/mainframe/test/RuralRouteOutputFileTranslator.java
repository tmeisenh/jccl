package com.indexoutofbounds.mainframe.test;

import java.io.IOException;

import com.indexoutofbounds.mainframe.exception.MainFrameConversionException;
import com.indexoutofbounds.mainframe.file.layout.FileRecordFieldLayout;
import com.indexoutofbounds.mainframe.file.layout.FileRecordLayout;
import com.indexoutofbounds.mainframe.file.layout.FlatFileLayout;
import com.indexoutofbounds.mainframe.file.layout.MetaDataType;
import com.indexoutofbounds.mainframe.legacy.converter.impl.MainFrameFlatFiletoXMLFileWriter;


/**
 * This was the test to see if I could translate the Rural Route output file.
 * The file is relative to my (Travis's) box.
 */

public class RuralRouteOutputFileTranslator {

	public static void main(String[] args) {

		FileRecordLayout record = new FileRecordLayout(370, 37000, "Rural Route Output Record");

		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "REC_STATUS"));
		record.addField(new FileRecordFieldLayout(6, MetaDataType.DISPLAY, "FINANCE_NO"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.DISPLAY, "ROUTE_NO"));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "REC_ID"));
		record.addField(new FileRecordFieldLayout(6, MetaDataType.DISPLAY, "EFF_CYRPP_NO"));

		record.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "REC_SEQ_NO"));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "BA_CODE"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.DISPLAY, "FINANCIAL_DATA_CTL_NO"));
		record.addField(new FileRecordFieldLayout(6, MetaDataType.DISPLAY, "PROC_CYRPP_NO"));
		record.addField(new FileRecordFieldLayout(7, MetaDataType.DISPLAY, "CREATED_CENTURY_DATE"));

		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "FILLER", false));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "ROUTE_LENGTH_MILES"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "BOXES_REGULAR"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "BOXES_CENTRALIZED"));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "NDCBU_COLL_BOXES"));

		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "PARCEL_LOCKERS"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "STOPS"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "LETTERS_PIECES"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "PAPERS_MAGS_CATS_PIECES"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "PARCELS_PIECES"));

		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "BOXHOLDERS_PIECES"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "REG_CERT_INS_PIECES"));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "COD_CUSTOMS_DUE_PIECES"));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "CHG_ADDRESS_PIECES"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "MARKUP_PIECES"));

		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "FORM_3579_3868_PIECES"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "RRL_RTE_DPS_LETTER_CNT"));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "MONEY_ORDER_PIECES"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "LETTERS_FLATS_PIECES"));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "ORD_INS_PARCEL_POST_PIECES"));

		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "COLLECT_REG_CERT_PIECES"));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "POSTAGE_DUE_PIECES"));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "LOAD_TIME_CNT_MINUTES"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "OTHER_SUITABLE_ALLOW_CNT"));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "PURCH_STAMP_STK_CNT"));

		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "RETURN_RECEIPT_CNT"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "AUTH_DISMOUNTS"));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 0, "TOT_DISMOUNT_DISTANCE"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "LOCK_POUCH_WEIGHT"));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "FAMILIES_SERVED"));

		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "LOCK_POUCH_STOPS"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "OFFICE_TIME"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "ROUTE_TIME_LESS_LUNCH"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "TOTAL_DAILY_TIME"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "AUX_OFFICE_TIME"));

		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "AUX_ROUTE_TIME"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "WAIT_AND_COUNT_TIME"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "DETOUR_MILES"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "SEASONAL_MILES"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "SEASONAL_BOXES_REG"));

		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "SEASONAL_BOXES_CENT"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "APT_BUILDINGS_SERVED", false));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 0, "RRL_RTE_SECTOR_SEG_LETTERS"));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "RRL_RTE_ROTATING_RELIEF_IND"));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "RRL_RTE_DEVIATION_MILES_CNT", false));

		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "DELIVER_LETTER_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "DELIVER_FLATS_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RRL_RTE_DELIV_PARCEL_OFFICE_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RRL_RTE_DELIV_PARCEL_RTE_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "WITHOLDING_TIME", false));

		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "STRAPPING_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "LOADING_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RETURN_RECEPIT_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "DISMOUNT_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "BOXHOLDERS_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "COD_CUSTOMS_OFFICE_TIME", false));

		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "DELIVER_REG_INS_OFFICE", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "MARKUP_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "ADDRESS_CHANGE_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "MONEY_ORDER_OFFICE_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "COLLECT_LETTERS_FLATS_TIME", false));

		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "COLLECT_PARCEL_OFFICE_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "STAMPS_OFFICE_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "FORM_3579_3868_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "OTHER_SUITABLE_ALLOW_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "POSTAGE_DUE_OFFICE_TIME", false));

		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "PERSONAL_OFFICE_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "DELIVERY_COD_CUSTOMS_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "DELIVERY_REG_CERT_ROUTE_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "COLLECT_MONEY_ORDER_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "COLLECT_PARCEL_TIME", false));

		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "COLLECT_REG_INS_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "MILES_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "BOXES_REGULAR_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "BOXES_CENTRALIZED_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "NDCBU_COLL_BOXES_TIME", false));

		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "PARCEL_LOCKERS_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "LOCK_POUCH_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RELOAD_UNLOAD_TIME", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RRL_RTE_SECTOR_SEG_MINUTE_COUNT", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RRL_RTE_DPS_MINUTE_COUNT", false));

		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RRL_RTE_GOVT_ADDL_MINUTE_COUNT", false));
		record.addField(new FileRecordFieldLayout(4, MetaDataType.PACKED_DECIMAL, 2, "RRL_RTE_DEVIATION_MINUTE_COUNT", false));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "ROUTE_120_MINUTE_COUNT", false));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "COMMITMENT_CODE"));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "COUNT_ELECTION", false));

		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "STANDARDS_OFFICE_HRS", false));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "STANDARDS_OFFICE_MINUTES", false));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "STANDARDS_ROUTE_HRS", false));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "STANDARDS_RTE_MINUTES", false));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "STANDARDS_ROUTE_TOTAL_HOURS", false));

		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "STANDARDS_ROUTE_TOTAL_MINUTES", false));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "ACTUAL_HOURS", false));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.PACKED_DECIMAL, 0, "ACTUAL_MINUTES", false));
		record.addField(new FileRecordFieldLayout(3, MetaDataType.PACKED_DECIMAL, 2, "BOX_FACTOR", false));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "COUNT_TYPE_CODE", false));

		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "RRL_RTE_NEGATION_ELG_INT", false));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "WITHDRAWING_MAIL_CODE", false));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "CENTRAL_MARKUP_CODE", false));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "DETOUR_ROUTE_IND"));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "SEASONAL_RTE_IND"));

		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "IN_SEASON_IND"));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "EM_SIGNAL"));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "CONVERT_TO_REGULAR_IND"));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "AMEND_SIGNAL", false));
		record.addField(new FileRecordFieldLayout(8, MetaDataType.DISPLAY, "ROUT_COUNT_BEGIN_CENTURY_DATE", false));

		record.addField(new FileRecordFieldLayout(8, MetaDataType.DISPLAY, "EFF_CENTURY_DATE", false));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "HIGH_LOW_OPTION"));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.PACKED_DECIMAL, 0, "COUNT_LENGTH", false));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "PAY_ROUTE_TYPE", false));
		record.addField(new FileRecordFieldLayout(2, MetaDataType.DISPLAY, "STEP", false));

		record.addField(new FileRecordFieldLayout(8, MetaDataType.DISPLAY, "USER_ID", false));
		record.addField(new FileRecordFieldLayout(1, MetaDataType.DISPLAY, "USER", false));

		/*
		 * Add the fields to a flatfile
		 */

		FlatFileLayout file = new FlatFileLayout("/C:/TEMP/rural_route_binary_big.txt", record);

		try {

			/*
			 * Create a MainFrameFlatFileConverter to convert the given flatfile using the
			 * given charset converter
			 */
			MainFrameFlatFiletoXMLFileWriter conv = new MainFrameFlatFiletoXMLFileWriter(file, "/C:/TEMP/rural_trimed.xml");
			long start = System.currentTimeMillis();
			System.out.println("Starting...");
			conv.convertAndWriteFlatFile();
			System.out.println("It took: " + (System.currentTimeMillis() - start) + " miliseconds.");

		} catch (IOException e) {

			e.printStackTrace();
		} catch (MainFrameConversionException e) {
			e.printStackTrace();
		}

		System.out.println("Done...");
	}
}
