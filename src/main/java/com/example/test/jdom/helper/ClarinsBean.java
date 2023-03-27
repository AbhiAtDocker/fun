package com.example.test.jdom.helper;

import java.util.List;
import java.util.Map;

public class ClarinsBean {
	private List<Integer> enabledColumn;
	private List<Integer> mandatoryColumn;
	private List<Integer> updateColumn;
	private List<Map<String, String>> allColumns;
	private List<String> importItemRows;
	private int columnCount;
	
	public List<String> getImportItemRows() {
		return importItemRows;
	}
	public void setImportItemRows(List<String> importItemRows) {
		this.importItemRows = importItemRows;
	}
	public List<Map<String, String>> getAllColumns() {
		return allColumns;
	}
	public void setAllColumns(List<Map<String, String>> allColumns) {
		this.allColumns = allColumns;
	}
	public List<Integer> getUpdateColumn() {
		return updateColumn;
	}
	public void setUpdateColumn(List<Integer> updateColumn) {
		this.updateColumn = updateColumn;
	}
	public List<Integer> getMandatoryColumn() {
		return mandatoryColumn;
	}
	public void setMandatoryColumn(List<Integer> mandatoryColumn) {
		this.mandatoryColumn = mandatoryColumn;
	}
	public List<Integer> getEnabledColumn() {
		return enabledColumn;
	}
	public void setEnabledColumn(List<Integer> enabledColumn) {
		this.enabledColumn = enabledColumn;
	}
	private List<String> promotionListItem;
	private Map<String, List<Integer>> mappingList;
	

	public Map<String, List<Integer>> getMappingList() {
		return mappingList;
	}
	public void setMappingList(Map<String, List<Integer>> mappingList) {
		this.mappingList = mappingList;
	}
	public List<String> getPromotionListItem() {
		return promotionListItem;
	}
	public void setPromotionListItem(List<String> promotionListItem) {
		this.promotionListItem = promotionListItem;
	}
    public int getColumnCount() {
        return columnCount;
    }
    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }
}