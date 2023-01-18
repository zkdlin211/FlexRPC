# FlexRPC - A Simple Springboot-based RPC Framework
FlexRPC is an annotation-driven, high-performance RPC framework built on top of Springboot. It uses Zookeeper as a registry center and Netty as the underlying transport layer, providing both NIO and BIO options for sending RPC requests.

## Getting Started
### Dependency
To use FlexRPC, you first need to add the framework as a dependency in your project. Then, you can define an interface for the remote service.
For provider side:
```xml
		<dependency>
			<groupId>com.cliffe.flex</groupId>
			<artifactId>flex-server</artifactId>
			<version>1.0-SNAPSHOT</version>
		</dependency>
```
For client side:
```xml
		<dependency>
			<groupId>com.cliffe.flex</groupId>
			<artifactId>flex-client</artifactId>
			<version>1.0-SNAPSHOT</version>
		</dependency>
```
### Sample Configuration file
Provider side:
```yml
flex:
  server:
    zk:
        # zookeeper registry root
        root: /rpc
        # Zookeeper server address
        addr: localhost:2181
        # connection timeout
        connect-timeout: 10000
        # rpc port (Netty server port)
    rpc-port: 28889
```
Client side:
```yml
flex:
  server:
    zk:
        # zookeeper registry root
        root: /rpc
        # Zookeeper server address
        addr: localhost:2181
        # connection timeout
        connect-timeout: 10000
        # rpc port (Netty server port)
    rpc-port: 28889
```
### Sample Usage
Then on the provider side, add the `@RpcService` annotation to the services implementations you want to provide.
This will register the service in the Zookeeper registry, making it available for clients to access.

```java
public interface HelloService {
    String sayHello(String name);
}
```

```java
@FlexService(interfaceClass = OrderService.class)
public class HelloService {
    String sayHello(String name){
        return "hello, "+name;
    }
}
```
On the client side, you can use the `@FlexRpc` annotation to inject a proxy for the remote service.
```java
@FlexRpc
private HelloService helloService;
```
You can now use the helloService object to invoke the remote sayHello() method, just like a local method.
```java
String result = helloService.sayHello("John");
```
### NIO
FlexRPC also supports for nio type of rpc request. To enable this, configure on your client side as the folloing example:
```java
@FlexService
public interface HelloService {

	@NIO(nioHandler = HelloNioHandler.class)
    String sayHello(String name);
    
}
```
You will need a NioHandler by implementing `com.cliffe.flex.client.io.NIOHandler` for handling the callback once client receives the response. The object parameter will be the response object.
```java
public class HelloNioHandler implements NIOHandler {
	@Override
	public void handleResponse(Object o) {
		// your callback logic
	}
}
```
## Custom Properties
### Serialization
You can customize Serialization type by extends`com.cliffe.flex.core.serialize.Serialization` class and implements `deserialize` and `serialize` methods.
For example,
```java
@Component
public class MySerialization extends Serialization{
    
    public static final int serializationType = 3;

    public MySerialization() {
        super(serializationType, MySerialization.class);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return null;
    }

    @Override
    public <T> byte[] serialize(T object) {
        return new byte[0];
    }

}
```
then define your class in configuration file:
```yml
flex:
  client:
    serialization-type=path.to.config.MySerialization
```
### ThreadPool
On the Client side, you can configure threadPool based on your requirement.
```yml
# this is for @NIO ThreadPool
flex:
  client:
    nio:
      thread:
        ...
```
```yml
# This is the configuration for the threadPool that receives RPC responses on the client side
flex:
  client:
    thread:
        ...
```
On the server side: TODO

### Load Balance
You can also configure the Load Balance strategy on Client side. Default is `random`.
```java
@FlexRpc(lbStrategy="hash")
private HelloService helloService;
```
You can also customize your own load balance strategy by extends `com.cliffe.flex.client.cluster.lb.LoadBalanceSelector`
```java
@Component
public class ExampleLoadBalanceSelector implements LoadBalanceSelector{

	
	@Override
	public ServiceProvider select(List<ServiceProvider> serviceProviderList, String interfaceName) {
		// your load balance strategy logic
	}

	@Override
	public String strategy() {
		return "example";
	}
}
```


### Proxy
Default support is CGlib.
TODO

### Protocol
Default support "FLEX" protocol.
TODO: "HTTP" ...
