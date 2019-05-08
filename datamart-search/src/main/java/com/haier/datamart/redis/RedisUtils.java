package com.haier.datamart.redis;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

/**
 * Created by Jack on 2018/1/22.
 */
public class RedisUtils {

	private static RedisTemplate redisTemplate;

	private static HashOperations<String, String, Object> hashOperations = null;

	private static ValueOperations<String, Object> valueOperations = null;

	private static ListOperations<String, Object> listOperations = null;

	private static SetOperations<String, Object> setOperations = null;

	private static ZSetOperations<String, Object> zSetOperations = null;

	/*
	 * 文件转数组
	 */
	public byte[] toByteArray(InputStream in) throws IOException {
		try {
			byte[] buffer = new byte[10240];// 数组大小应适当大于文件
			int len = 0;
			int i = 0;
			while (-1 != (len = in.read()))// 逐个字节读取
			{
				buffer[i] = (byte) len;// 读取到的字节放进数组
				i++;
			}
			byte[] buffer2 = new byte[i];// 新建一个与文件大小相同的数组
			System.arraycopy(buffer, 0, buffer2, 0, i);// 复制大数组中有效内容
			return buffer2;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 将文件转成数组存到redis
	 */
	public Boolean setFile(String key, InputStream in) {
		try {
			byte[] b = toByteArray(in);
			if (b != null) {
				return RedisUtils.set(key, b);
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * 将redis中的数组读取出来转成输入流
	 */
	public static InputStream getInputStream(String key) {
		try {
			byte[] json = (byte[]) RedisUtils.get(key);// 取出数组
			if (json != null && json.length > 0) {
				try {
					InputStream inputStream = new ByteArrayInputStream(json);// 转流
					return inputStream;
				} catch (Exception e) {
					return null;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 批量删除对应的value
	 *
	 * @param keys
	 */
	public static void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 *
	 * @param pattern
	 */
	public static void removePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 删除对应的value
	 *
	 * @param key
	 */
	public static void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 *
	 * @param key
	 * @return
	 */
	public static boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 读取缓存
	 *
	 * @param key
	 * @return
	 */
	public static Object get(final String key) {
		Object result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate
				.opsForValue();
		result = operations.get(key);
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 设置过期时间
	 *
	 * @param key
	 * @param expireTime
	 * @param unit
	 */
	public static void expire(final String key, Long expireTime, TimeUnit unit) {
		redisTemplate.expire(key, expireTime, unit);
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @param timeUnit
	 * @return
	 */
	public static boolean set(final String key, Object value, Long expireTime,
			TimeUnit timeUnit) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, timeUnit);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 添加
	 *
	 * @param key
	 *            key
	 * @param domain
	 *            对象
	 * @param expire
	 *            过期时间(单位:秒),传入 -1 时表示不设置过期时间
	 */
	public static void put(String redisKey, String key, String domain,
			long expire) {
		hashOperations.put(redisKey, key, domain);
		if (expire != -1) {
			redisTemplate.expire(redisKey, expire, TimeUnit.SECONDS);
		}
	}

	/**
	 * 删除
	 *
	 * @param key
	 *            传入key的名称
	 */
	public static void remove(String redisKey, String key) {
		hashOperations.delete(redisKey, key);
	}

	/**
	 * 查询
	 *
	 * @param key
	 *            查询的key
	 * @return
	 */
	public static Object get(String redisKey, String key) {
		return hashOperations.get(redisKey, key);
	}

	/**
	 * 获取当前redis库下所有对象
	 *
	 * @return
	 */
	public static List<Object> getAll(String redisKey) {
		return hashOperations.values(redisKey);
	}

	/**
	 * 查询当前redis库下所有key
	 *
	 * @return
	 */
	public static Set<String> getKeys(String redisKey) {
		return hashOperations.keys(redisKey);
	}

	/**
	 * 判断key是否存在redis中
	 *
	 * @param key
	 *            传入key的名称
	 * @return
	 */
	public static boolean isKeyExists(String redisKey, String key) {
		return hashOperations.hasKey(redisKey, key);
	}

	/**
	 * 查询当前key下缓存数量
	 *
	 * @return
	 */
	public static long count(String redisKey) {
		return hashOperations.size(redisKey);
	}

	/**
	 * 缓存设置
	 *
	 * @param redisTemplate
	 */
	public static void setRedisTemplate(RedisTemplate redisTemplate) {
		RedisUtils.redisTemplate = redisTemplate;

		hashOperations = redisTemplate.opsForHash();

		valueOperations = redisTemplate.opsForValue();

		listOperations = redisTemplate.opsForList();

		setOperations = redisTemplate.opsForSet();

		zSetOperations = redisTemplate.opsForZSet();
	}
}
