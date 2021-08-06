##guava工具使用
参考文档博客:
https://www.cnblogs.com/SummerinShire/p/6054983.html
https://blog.csdn.net/dgeek/article/details/76221746
http://ifeve.com/google-guava-collectionutilities/

#集合数据创建
#不可变集合创建
Map<String,String> map = ImmutableMap.of("key1", "val1", "key2", "val2");
//map的可读性较差
ImmutableMap<String,String> map = ImmutableMap.of("key1", "value1", "key2", "value2");

#list转化为Stirng

#int数组转化为String数组

#字符比较、分离 CharMatcher Splitter
//判断字符a~z
CharMatcher charMatcher = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z'));
boolean result = charMatcher.matches('a');
System.out.println(result);

//分离字符串并处理空格和空的情况
Iterable<String> split = Splitter.on(",")
.omitEmptyStrings()
.trimResults()
.split("1,,,2, 3,    ,5");

#从字符串中得到所有的数字
String string = CharMatcher.DIGIT.retainFrom("some text 89983 and more");

#把字符串中的数据都去掉
String string = CharMatcher.DIGIT.removeFrom("some text 89983 and more");

#字符串连接器 Joiner
使用Joiner：
String[] subdirs = { "usr", "local", "lib" };
String directory = Joiner.on("/").join(subdirs);

或者这样：
int[] numbers = { 1, 2, 3, 4, 5 };
String numbersAsString = Joiner.on(";").join(Ints.asList(numbers));



#一键多值得map（普通map是一个k和一个v），ArrayListMultimap
Multimap<String, String> multimap = ArrayListMultimap.create();
multimap.put("123", "123");
multimap.put("123", "124");
System.out.println(multimap.get("123"));
//[123, 124]



#比较 Ints.compare(a,b);

#常用工具类Maps,Multimaps,Lists,Sets,Multisets,Ints，Doubles，Floats，Shorts，Bytes以及Bools

#非字符串list的存在判断
int[] array = { 1, 2, 3, 4, 5 };
boolean contains = Ints.contains(array, a);

#Maps.uniqueIndex(Iterable,Function)