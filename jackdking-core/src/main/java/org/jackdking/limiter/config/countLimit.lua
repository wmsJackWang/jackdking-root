-- 限流
-- 获取方法签名特征
local key = KEYS[1]
-- 调用脚本传入的限流大小
local limit = tonumber(ARGV[1])
-- 获取当前流量大小
local count = tonumber(redis.call('get',key) or "0")
-- 是否超出限流阈值
if count + 1 > limit then
    -- 拒绝服务
    return false
else
    -- 没有超过阈值
    -- 设置当前访问的数量 + 1
    redis.call("incr",key)
    -- 设置过期时间
    redis.call("expire",key,ARGV[2])
    return true
end