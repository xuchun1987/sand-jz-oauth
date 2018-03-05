package oauth.service.redis;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisService {
	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	private StringRedisTemplate redisTemplate;
	/*@Autowired
	StringRedisTemplate stringRedisTemplate;*/
	
	/** 
     * list入栈 
     * @param key 
     * @param value 
     * @return 
     */  
    public Long listAdd(String key, String value) {  
        return redisTemplate.opsForList().leftPush(key, value);  
    }  
    
    /** 
     * list出栈 
     * @param key 
     * @return 
     */  
    public Object listGet(String key) {  
        return redisTemplate.opsForList().leftPop(key);
    } 
    
    /** 
     * list栈/队列长 
     * @param key 
     * @return 
     */  
    public Long listSize(String key) {  
        return redisTemplate.opsForList().size(key);  
    }  
    
    
    /** 
     * list检索 
     * @param key 
     * @param index 
     * @return 
     */  
    public Object listIndex(String key, long index) {  
        return redisTemplate.opsForList().index(key, index);  
    }  
    
    
    /** 
     * list范围检索 
     * @param key 
     * @param start 
     * @param end 
     * @return 
     */  
    public List<String> listRange(String key, int start, int end) {  
        return redisTemplate.opsForList().range(key, start, end);  
    }  
    
    /** 
     * list置值 
     * @param key 
     * @param index 
     * @param value 
     */  
    public void listSet(String key, long index, String value) {  
    	redisTemplate.opsForList().set(key, index, value);  
    }  
    
    /** 
     * list移除 
     * @param key 
     * @param i 
     * @param value 
     */  
    public void listRemove(String key, long i, String value) {  
    	redisTemplate.opsForList().remove(key, i, value);  
    }  
    
    /** 
     * list范围移除 
     * @param key 
     * @param start 
     * @param end 
     */  
    public void listRangeRemove(String key, long start, int end) {  
    	redisTemplate.opsForList().trim(key, start, end);  
    }  
    
	
	/**
	 * redis赋值
	 * @param key
	 * @param value
	 * @return
	 */
	public void set(String key,String value) {
		redisTemplate.opsForValue().set(key, value);
		
	}
	
	/**
	 * redis赋值
	 * @param key
	 * @param value
	 * @param date
	 * @return
	 */
	public void set(String key,String value,Date date) {
		redisTemplate.opsForValue().set(key, value);
		redisTemplate.expireAt(key, date);
	}
	
	/**
	 * redis赋值
	 * @param key
	 * @param value
	 * @param l
	 * @return
	 */
	public void set(String key,String value,long l) {
		redisTemplate.opsForValue().set(key, value);
		redisTemplate.expire(key,l,TimeUnit.MILLISECONDS);
	}

	/**
	 * 如果reids中没有该key，
	 * 设值成功返回true；
	 * 否则不操作返回false
	 * @param key
	 * @param value
	 * @param l
	 * @return
	 */
	public Boolean setNx(String key,String value,long l){
		RedisConnection con=redisTemplate.getConnectionFactory().getConnection();
		Boolean b=con.setNX(key.getBytes(),value.getBytes());
		redisTemplate.expire(key,l,TimeUnit.MILLISECONDS);
		con.close();
		return b;

	}

	/**
	 * 自增key
	 * xuchun
	 * 2017-06-22
	 * @param key
	 * @return
	 */
	public Long incrSet(String key){
		RedisConnection con=redisTemplate.getConnectionFactory().getConnection();
		Long l=con.incr(key.getBytes());
		con.close();
		return l;
	}
	
	/**
	 * redis赋值
	 * @param key
	 * @param value
	 * @param timeout 过期时间
	 * @param unit 时间类型
	 */
	public void set(String key,String value,long timeout,TimeUnit unit) {
		redisTemplate.opsForValue().set(key, value,timeout,unit);
	}

	/**
	 * redis 取值
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	/**
	 * 删除
	 * @param key
	 */
	public void delete(String key) {
		redisTemplate.delete(key);
	}
	
	/**
	 * 批量删除
	 * @param keys
	 */
	public void delete(List<String> keys) {
		redisTemplate.delete(keys);
	}
	
	/**
	 * 获取过期时间
	 * @param key
	 * @return
	 */
	public Long getExpire(String key) {
		return redisTemplate.getExpire(key);
	}
	/**
	 * 判断redis是否包含
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}


	/**
	 * 放入hash键值
	 * xuchun
	 * 2017-06-26
	 * @param key
	 * @param field
	 * @param value
	 */
	public Boolean hSet(String key,String field,String value){

		RedisConnection con=null;
		Boolean b=null;
		try{
			con=redisTemplate.getConnectionFactory().getConnection();
			b=con.hSet(key.getBytes(),field.getBytes(),value.getBytes());
		}catch (Exception e){
			logger.info("--------redis hset异常-----key:"+key+"----field:"+field+"------value:"+value,e);
			b=false;
		}finally {
			if(con!=null){
				con.close();
			}
		}
		return b;

	}

	/**
	 * 放入hash键值,仅当field不存在
	 * xuchun
	 * 2017-06-26
	 * @param key
	 * @param field
	 * @param value
	 */
	public Boolean hSetNx(String key,String field,String value){
		RedisConnection con=redisTemplate.getConnectionFactory().getConnection();
		Boolean b=con.hSetNX(key.getBytes(),field.getBytes(),value.getBytes());
		con.close();
		return b;
	}

	/**
	 * 获取hash值
	 * xuchun
	 * 2017-06-26
	 * @param key
	 * @param field
	 * @return
	 */
	public String hGet(String key,String field){
		RedisConnection con=redisTemplate.getConnectionFactory().getConnection();
		byte[] b=con.hGet(key.getBytes(),field.getBytes());
		con.close();
		if(b!=null&&b.length>0){
			return new String(b);
		}
		return null;
	}

	/**
	 * hash累加
	 * xuchun
	 * 2017-06-26
	 * @param key
	 * @param field
	 * @return
	 */
	public Long hIncr(String key,String field,long l){
		RedisConnection con=redisTemplate.getConnectionFactory().getConnection();
		Long b=con.hIncrBy(key.getBytes(),field.getBytes(),l);
		con.close();
		return b;
	}

	/**
	 * hash删除
	 * xuchun
	 * 2017-06-26
	 * @param key
	 * @param field
	 * @return
	 */
	public Long hDel(String key,String field){
		RedisConnection con=redisTemplate.getConnectionFactory().getConnection();
		Long b=con.hDel(key.getBytes(),field.getBytes());
		con.close();
		return b;
	}

	/**
	 * 如果赋值成功，设置过期时间
	 * xuchun
	 * 2017-11-06
	 * @param key
	 * @param value
	 * @param l
	 * @return
	 */
	/*public boolean setNxTest(String key,String value,long l) {
		RedisConnection connection=null;
		Boolean b=false;
		try {
			connection=	redisTemplate.getConnectionFactory().getConnection();
			b=connection.setNX(key.getBytes("UTF-8"),value.getBytes("UTF-8"));
			if(b){
				connection.expire(key.getBytes("UTF-8"),l);
			}
			*//*else{
				connection.del(key.getBytes("UTF-8"));
			}*//*
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(connection!=null){
				connection.close();
			}
		}
		return b;
	}*/
}
