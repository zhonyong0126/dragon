package com.tianshouzhi.dragon.sharding.jdbc.statement;

import com.tianshouzhi.dragon.common.jdbc.statement.DragonPrepareStatement;
import com.tianshouzhi.dragon.sharding.jdbc.connection.DragonShardingConnection;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.tianshouzhi.dragon.common.jdbc.statement.DragonPrepareStatement.ParamType.*;
import static com.tianshouzhi.dragon.common.jdbc.statement.DragonPrepareStatement.ParamType.setBlob;
import static com.tianshouzhi.dragon.common.jdbc.statement.DragonPrepareStatement.ParamType.setNClob;
import static com.tianshouzhi.dragon.common.jdbc.statement.DragonPrepareStatement.PrepareCreateType.*;
import static com.tianshouzhi.dragon.common.jdbc.statement.DragonPrepareStatement.PrepareExecuteType.PREPARE_EXECUTE;
import static com.tianshouzhi.dragon.common.jdbc.statement.DragonPrepareStatement.PrepareExecuteType.PREPARE_EXECUTE_QUERY;
import static com.tianshouzhi.dragon.common.jdbc.statement.DragonPrepareStatement.PrepareExecuteType.PREPARE_EXECUTE_UPDATE;

/**
 * Created by TIANSHOUZHI336 on 2016/12/16.
 */
public class DragonShardingPrepareStatement extends DragonShardingStatement implements DragonPrepareStatement {

	protected Map<Integer, ParamSetting> params = new LinkedHashMap<Integer, ParamSetting>();

	protected PrepareExecuteType prepareExcuteType;

	protected PrepareCreateType prepareCreateType;

	public DragonShardingPrepareStatement(String sql, DragonShardingConnection dragonShardingConnection) {
		super(dragonShardingConnection);
		this.prepareCreateType = SQL;
		this.sql = sql;
	}

	public DragonShardingPrepareStatement(String sql, int resultSetType, int resultSetConcurrency,
	      DragonShardingConnection dragonShardingConnection) {
		super(resultSetType, resultSetConcurrency, dragonShardingConnection);
		this.prepareCreateType = SQL_RESULTSET_TYPE_CONCURRENCY;
		this.sql = sql;
	}

	public DragonShardingPrepareStatement(String sql, int resultSetType, int resultSetConcurrency,
	      int resultSetHoldability, DragonShardingConnection dragonShardingConnection) {
		super(resultSetType, resultSetConcurrency, resultSetHoldability, dragonShardingConnection);
		this.prepareCreateType = SQL_RESULTSET_TYPE_CONCURRENCY_HOLDABILITY;
		this.sql = sql;
	}

	public DragonShardingPrepareStatement(String sql, int autoGeneratedKeys,
	      DragonShardingConnection dragonShardingConnection) {
		super(dragonShardingConnection);
		this.sql = sql;
		this.autoGeneratedKeys = autoGeneratedKeys;
		this.prepareCreateType = SQL_AUTOGENERATEDKEYS;
	}

	public DragonShardingPrepareStatement(String sql, int[] columnIndexes,
	      DragonShardingConnection dragonShardingConnection) {
		super(dragonShardingConnection);
		this.sql = sql;
		this.prepareCreateType = SQL_COLUMNINDEXES;
		this.columnIndexes = columnIndexes;
	}

	public DragonShardingPrepareStatement(String sql, String[] columnNames,
	      DragonShardingConnection dragonShardingConnection) {
		super(dragonShardingConnection);
		this.sql = sql;
		this.prepareCreateType = SQL_COLUMNNAMES;
		this.columnNames = columnNames;
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		prepareExcuteType = PREPARE_EXECUTE_QUERY;
		doExecute();
		return this.resultSet;
	}

	@Override
	public int executeUpdate() throws SQLException {
		prepareExcuteType = PREPARE_EXECUTE_UPDATE;
		doExecute();
		return this.updateCount;
	}

	@Override
	public boolean execute() throws SQLException {
		prepareExcuteType = PREPARE_EXECUTE;
		return doExecute();
	}

	@Override
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setNull, new Object[] { sqlType }));
	}

	@Override
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setBoolean, new Object[] { x }));
	}

	@Override
	public void setByte(int parameterIndex, byte x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setByte, new Object[] { x }));
	}

	@Override
	public void setShort(int parameterIndex, short x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setShort, new Object[] { x }));
	}

	@Override
	public void setInt(int parameterIndex, int x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setInt, new Object[] { x }));
	}

	@Override
	public void setLong(int parameterIndex, long x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setLong, new Object[] { x }));
	}

	@Override
	public void setFloat(int parameterIndex, float x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setFloat, new Object[] { x }));
	}

	@Override
	public void setDouble(int parameterIndex, double x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setDouble, new Object[] { x }));
	}

	@Override
	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setBigDecimal, new Object[] { x }));
	}

	@Override
	public void setString(int parameterIndex, String x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setString, new Object[] { x }));
	}

	@Override
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setBytes, new Object[] { x }));
	}

	@Override
	public void setDate(int parameterIndex, Date x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setDate, new Object[] { x }));
	}

	@Override
	public void setTime(int parameterIndex, Time x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setTime, new Object[] { x }));
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setTimestamp, new Object[] { x }));
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setAsciiStream2, new Object[] { x, length }));
	}

	@Override
	public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
		params.put(parameterIndex,
		      new ParamSetting(setUnicodeStream2, new Object[] { x, length }));
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setBinaryStream2, new Object[] { x, length }));
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setObject2, new Object[] { x }));
	}

	@Override
	public void setObject(int parameterIndex, Object x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setObject, new Object[] { x }));
	}

	@Override
	public void addBatch() throws SQLException {

	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
		params.put(parameterIndex,
		      new ParamSetting(setCharacterStream2, new Object[] { reader, length }));
	}

	@Override
	public void setRef(int parameterIndex, Ref x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setRef, new Object[] { x }));
	}

	@Override
	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setBlob, new Object[] { x }));
	}

	@Override
	public void setClob(int parameterIndex, Clob x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setClob, new Object[] { x }));
	}

	@Override
	public void setArray(int parameterIndex, Array x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setArray, new Object[] { x }));
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return null;
	}

	@Override
	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setDate2, new Object[] { x, cal }));
	}

	@Override
	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setTime2, new Object[] { x, cal }));
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setTimestamp, new Object[] { x, cal }));
	}

	@Override
	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setNull2, new Object[] { sqlType, typeName }));
	}

	@Override
	public void setURL(int parameterIndex, URL x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setURL, new Object[] { x }));
	}

	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		return null;
	}

	@Override
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setRowId, new Object[] { x }));
	}

	@Override
	public void setNString(int parameterIndex, String value) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setNString, new Object[] { value }));
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
		params.put(parameterIndex,
		      new ParamSetting(setNCharacterStream2, new Object[] { value, length }));
	}

	@Override
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setNClob, new Object[] { value }));
	}

	@Override
	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setClob2, new Object[] { reader, length }));
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
		params.put(parameterIndex,
		      new ParamSetting(setBlob2, new Object[] { inputStream, length }));
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setNClob2, new Object[] { reader, length }));
	}

	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setSQLXML, new Object[] { xmlObject }));
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
		params.put(parameterIndex,
		      new ParamSetting(setObject3, new Object[] { x, targetSqlType, scaleOrLength }));
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setAsciiStream2, new Object[] { x, length }));
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setBinaryStream2, new Object[] { x, length }));
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
		params.put(parameterIndex,
		      new ParamSetting(setCharacterStream2, new Object[] { reader, length }));
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setAsciiStream, new Object[] { x }));
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setBinaryStream, new Object[] { x }));
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setCharacterStream, new Object[] { reader }));
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setNCharacterStream, new Object[] { value }));
	}

	@Override
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setClob, new Object[] { reader }));
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setBlob, new Object[] { inputStream }));
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		params.put(parameterIndex, new ParamSetting(setNClob, new Object[] { reader }));
	}

	@Override
	public void clearParameters() throws SQLException {
		params.clear();
	}

	public Map<Integer, ParamSetting> getParameters() {
		return params;
	}
}
