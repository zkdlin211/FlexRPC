package com.cliffe.flex.client.cache;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public abstract class CachedExecutorService extends ThreadPoolExecutor {
	protected final ThreadLocal<Map<String, Object>> localCache = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<>();
		}
	};
	public CachedExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		return super.invokeAny(tasks);
	}

//	@Override
//	public void execute(Runnable command) {
//		// Get the local cache for the current thread
//		Map<String, Object> cache = localCache.get();
//		if(cache != null){
//			return
//		}
//		super.execute(command::run);
//	}

	protected Map<String, Object> getCache(){
		return localCache.get();
	}

	protected abstract void tryPut(String cacheKey, Object result);
}
