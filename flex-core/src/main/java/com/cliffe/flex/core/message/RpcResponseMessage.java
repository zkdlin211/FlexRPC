package com.cliffe.flex.core.message;


/**
 * @Author: Cliffe
 * @Date: 2022-12-26 11:47 上午
 */
public class RpcResponseMessage extends Message {

    /**
     * 返回值
     */
    private Object returnValue;
    /**
     * 异常值
     */
    private Exception exceptionValue;

    public static final int RpcResponseMessageType = 1;


	public RpcResponseMessage() {
		super(RpcResponseMessageType);
	}

	@Override
	public int getMessageType() {
		return RpcResponseMessageType;
	}

	@Override
	public Class<?> getMessageClass() {
		return RpcResponseMessage.class;
	}

	public Object getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	public Exception getExceptionValue() {
		return exceptionValue;
	}

	public void setExceptionValue(Exception exceptionValue) {
		this.exceptionValue = exceptionValue;
	}
}
