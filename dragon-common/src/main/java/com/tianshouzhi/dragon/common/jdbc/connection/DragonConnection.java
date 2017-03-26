package com.tianshouzhi.dragon.common.jdbc.connection;

import com.tianshouzhi.dragon.common.jdbc.WrapperAdapter;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by TIANSHOUZHI336 on 2016/12/11.
 */
public abstract class DragonConnection extends WrapperAdapter implements  Connection{

    protected static int defaultResultSetType;
    protected static int defualtResultSetConcurrency;
    protected static int defaultResultSetHoldability;
    protected static int defaultAutoGeneratedKeys;

    protected List<Statement> statementList=new CopyOnWriteArrayList<Statement>();
    protected String username;
    protected String password;
    protected boolean isClosed = false;
    protected boolean autoCommit = true;//是否自动提交
    protected boolean isReadOnly = false;
    protected int level=Connection.TRANSACTION_READ_COMMITTED; //mysql默认事务隔离级别
    protected String catalog;
    protected int holdability= ResultSet.CLOSE_CURSORS_AT_COMMIT;//mysql只支持这个
    protected Properties clientInfo=new Properties();
    protected Map<String, Class<?>> typeMap;

    public DragonConnection(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected void checkClosed() throws SQLException {
        if (isClosed) {
            throw new SQLException("No operations allowed after connection closed. you should to use a new connection");
        }
    }
    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        if(isClosed){
            throw new SQLClientInfoException();
        }
        if(clientInfo==null){
            clientInfo=new Properties();
        }
        this.clientInfo.put(name,value);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        if(isClosed){
            throw new SQLClientInfoException();
        }
        clientInfo=properties;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return isClosed;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        checkClosed();
        this.autoCommit = autoCommit;
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        checkClosed();
        return autoCommit;
    }
    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        checkClosed();
        this.level = level;
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        checkClosed();
        return level;
    }
    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        checkClosed();
        if (autoCommit) {
            throw new SQLException("This method cannot be called during a transaction");
        } else {
            this.isReadOnly = readOnly;
        }
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        checkClosed();
        return isReadOnly;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        checkClosed();
        this.catalog = catalog;
    }

    @Override
    public String getCatalog() throws SQLException {
        checkClosed();
        return catalog;
    }
    @Override
    public void setHoldability(int holdability) throws SQLException {
        checkClosed();
        this.holdability = holdability;
    }

    @Override
    public int getHoldability() throws SQLException {
        checkClosed();
        return holdability;
    }
    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        checkClosed();
        return typeMap;
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        checkClosed();
        typeMap=map;
    }

    protected void setConnectionParams(Connection ... connections) throws SQLException {
        if(connections==null||connections.length==0){
            throw new IllegalArgumentException("connections can't be null or empty!!!");
        }
        for (Connection connection : connections) {
            connection.setAutoCommit(autoCommit);
            if(clientInfo.size()!=0){
                connection.setClientInfo(clientInfo);
            }
            connection.setHoldability(holdability);
            if(typeMap!=null){
                connection.setTypeMap(typeMap);
            }
            if(catalog!=null){
                connection.setCatalog(catalog);
            }
                //// FIXME: 2016/12/14
            //因为ReadDBSelector会将所有的只读连接设置为readonly，如果这里设置了，则会对判断造成影响
//            wrappedConnection.setReadOnly(isReadOnly());
            connection.setTransactionIsolation(level);
        }
    }

}
