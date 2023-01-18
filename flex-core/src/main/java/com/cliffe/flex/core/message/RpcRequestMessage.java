package com.cliffe.flex.core.message;


import com.cliffe.flex.core.exception.FlexException;
import com.cliffe.flex.core.serialize.Serialization;
import com.cliffe.flex.core.serialize.SerializationRegistrar;

/**
 * @Author: Cliffe
 * @Date: 2022-12-26 11:47 上午
 */
public class RpcRequestMessage extends Message {
	/**
	 * 调用的接口全限定名，服务端根据它找到实现
	 */
	private String interfaceName;
	/**
	 * 调用接口中的方法名
	 */
	private String methodName;
	/**
	 * 方法返回类型
	 */
	private Class<?> returnType;
	/**
	 * 方法参数类型数组
	 */
	private Class<?>[] parameterTypes;
	/**
	 * 方法参数值数组
	 */
	private Object[] parameterValue;

	public static final int RpcRequestMessageType = 0;

	public RpcRequestMessage() {
		super(RpcRequestMessageType);
	}

	@Override
	public int getMessageType() {
		return RpcRequestMessageType;
	}

	@Override
	public Class<?> getMessageClass() {
		return RpcRequestMessage.class;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Object[] getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(Object[] parameterValue) {
		this.parameterValue = parameterValue;
	}

	public static class Builder {
		private RpcRequestMessage message;

		public Builder() {
			message = new RpcRequestMessage();
		}

		public Builder withSequenceId(int sequenceId) {
			message.setSequenceId(sequenceId);
			return this;
		}
		public Builder withSerializationType(Class<? extends Serialization> serializationType) {
			Integer serializationNo = SerializationRegistrar.getSerializationNo(serializationType);
			if(serializationNo == null){
				throw new FlexException("such serialization type: "+serializationType+" cannot be found in SerializationRegistrar, " +
						"consider add it into ApplicationContext.");
			}
			message.setSerializationType(serializationNo);
			return this;
		}


		public Builder withInterfaceName(String interfaceName) {
			message.setInterfaceName(interfaceName);
			return this;
		}

		public Builder withMethodName(String methodName) {
			message.setMethodName(methodName);
			return this;
		}

		public Builder withReturnType(Class<?> returnType) {
			message.setReturnType(returnType);
			return this;
		}

		public Builder withParameterTypes(Class<?>[] parameterTypes) {
			message.setParameterTypes(parameterTypes);
			return this;
		}

		public Builder withParameterValue(Object[] parameterValue) {
			message.setParameterValue(parameterValue);
			return this;
		}

		public RpcRequestMessage build() {
			return message;
		}
	}

}
