package tr.org.unicase.service.api.sql;


public class UnicaseQueryBuilder {

	private StringBuilder selectPartBuilder = null;
	private StringBuilder fromPartBuilder = null;
	private StringBuilder wherePartBuilder = null;

	public UnicaseQueryBuilder() {
		selectPartBuilder = new StringBuilder();
		fromPartBuilder = new StringBuilder();
		wherePartBuilder = new StringBuilder();
	}

	public UnicaseQueryBuilder select(String column, String alias) {
		if (column != null && !column.trim().isEmpty() && column.trim().length() > 0) {
			if (selectPartBuilder.length() > 0)
				selectPartBuilder.append(SQLConfiguration.SEPERATOR);
			selectPartBuilder.append(column);
			if (alias != null && !alias.trim().isEmpty())
				selectPartBuilder.append(SQLConfiguration.SELECT.AS).append(alias);
		}

		return this;
	}

	public UnicaseQueryBuilder selectAll() {
		return select(SQLConfiguration.SELECT.ALL);
	}

	public UnicaseQueryBuilder select(String column) {
		return select(column, SQLConfiguration.EMPTY_STRING);
	}
	
	public UnicaseQueryBuilder from(String tableName, String alias) {
		if (tableName != null && !tableName.trim().isEmpty() && tableName.trim().length() > 0) {
			if (fromPartBuilder.length() > 0)
				fromPartBuilder.append(",");
			fromPartBuilder.append(tableName);
			if (alias != null && !alias.trim().isEmpty() && alias.trim().length() > 0) {
				fromPartBuilder.append(alias);
			}
		}
		
		return this;
	}
	
	public UnicaseQueryBuilder from(String tableName) {
		if (tableName != null && !tableName.trim().isEmpty() && tableName.trim().length() > 0) {
			if (fromPartBuilder.length() > 0)
				fromPartBuilder.append(",");
			fromPartBuilder.append(tableName);
		}
		
		return this;
	}
	
	public UnicaseQueryBuilder whereParameterized(String column, String operation, String parameterName) {
		if (column != null && !column.trim().isEmpty() && parameterName != null && !parameterName.trim().isEmpty()) {
			wherePartBuilder
				.append(SQLConfiguration.WHERE.AND)
				.append(column)
				.append(operation)
				.append(SQLConfiguration.WHERE.SEMICOLON)
				.append(parameterName);
		}
		
		return this;
	}
	
	public UnicaseQueryBuilder whereParameterized(String column, String parameterName) {
		return whereParameterized(column, SQLConfiguration.WHERE.EQUAL, parameterName);
	}
	
	public UnicaseQueryBuilder where(String column, Long value) {
		if (column != null && !column.trim().isEmpty() && value != null) {
			wherePartBuilder
				.append(SQLConfiguration.WHERE.AND)
				.append(column)
				.append(SQLConfiguration.WHERE.EQUAL)
				.append(value);
		}

		return this;
	}
	
	public UnicaseQueryBuilder where(String column, String operation, String value) {
		if (column != null && !column.trim().isEmpty() && value != null && !value.trim().isEmpty()) {
			wherePartBuilder
			.append(SQLConfiguration.WHERE.AND)
			.append(column)
			.append(operation)
			.append(SQLConfiguration.WHERE.SINGLE_QUOTE)
			.append(value)
			.append(SQLConfiguration.WHERE.SINGLE_QUOTE);
		}
		
		return this;
	}
	
	public UnicaseQueryBuilder where(String column, String value) {
		if (column != null && !column.trim().isEmpty() && value != null && !value.trim().isEmpty()) {
			wherePartBuilder
			.append(SQLConfiguration.WHERE.AND)
			.append(column)
			.append(SQLConfiguration.WHERE.EQUAL)
			.append(SQLConfiguration.WHERE.SINGLE_QUOTE)
			.append(value)
			.append(SQLConfiguration.WHERE.SINGLE_QUOTE);
		}
		
		return this;
	}
	
	public String build() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(SQLConfiguration.SELECT.KEYWORD);
		buffer.append(selectPartBuilder.toString());
		buffer.append(SQLConfiguration.FROM.KEYWORD);
		buffer.append(fromPartBuilder.toString());
		buffer.append(SQLConfiguration.WHERE.KEYWORD);
		buffer.append(wherePartBuilder.toString());

		return buffer.toString();
	}
	
	public void clean() {
		selectPartBuilder.setLength(0);
		fromPartBuilder.setLength(0);
		wherePartBuilder.setLength(0);
		
		
		selectPartBuilder = null;
		fromPartBuilder = null;
		wherePartBuilder = null;
	}

}
