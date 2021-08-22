
##高级代码 养成记 
##由点到面
###点： 集合【Collectors，Stream, Arrays】, Optional, 方法引用， 函数式编程， jdk函数式接口及应用场景, 自定义接口

#方法引用
BiFunction<T,U,R> T apply(T, U)
接受两个输入参数TU，并且返回一个结果R

Function<T,R>  R apply(T)
接受一个输入参数T，返回一个结果R

Supplier<T> T get()
无参数，返回一个结果T.

Predicate<T>  boolean test(T)
断言  接收一个参数T ，返回会Boolean

Consumer<T>  accept(T)
消费型接口 ， 处理参数


##函数式编程
#常用函数式接口
函数式接口	        函数描述符	    原始类型特化
Predicate	        T->boolean	    IntPredicate,DoublePredicate,LongPredicate
Consumer	        T->void	        IntConsumer,LongConsumer,DoubleConsumer
Function<T, R>	    T->R	        IntFunction,IntToDoubleFunction,IntToLongFunction,LongFunction,LongToDoubleFunction,LongToIntFunction,DoubleFunction,DoubleToIntFunction,DoubleToLongFunction, ToIntFunction,ToLongFunction
Supplier	        ()->T	        BooleanSupplier,IntSupplier,LongSupplier,DoubleSupplier
UnaryOperator	    T->T	        IntUnaryOperator,LongUnaryOperator,DoubleUnaryOperator
BinaryOperator	    (T,T)->T	    IntBinaryOperator,LongBinaryOperator,DoubleBinaryOperator
BiPredicate<T, U>	(L,R)->boolean
BiConsumer<T, U>	(T,R)->void	    ObjIntConsumer,ObjLongConsumer,ObjDoubleConsumer
BiFunction<T, U, R>	(T,U)->R	    ToIntBiFunction<T, U>,ToLongBiFunction<T, U>,ToDoubleBiFunction<T, U>


#Lambdas及函数式接口的例子
使用案例	            Lambda的例子	                                                        对应的函数式接口
布尔表达式	        (List list) -> list.isEmpty()	                                    Predicate<List>
创建对象	            ()->new Apple(10)	Supplier
消费一个对象	        (Apple a) -> System.out.println(a.getWeight())	                    Consumer
从一个对象中选择/提取	(String s) -> s.length()	                                        Function<Sring, Integer>或ToIntFunction
合并两个值	        (int a, int b) -> a*b	                                            IntBinaryOperator
比较两个对象	        (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight())	Comparator或BiFunction<Apple, Apple, Integer>或ToIntBiFunction<Apple, Apple>



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

#多值的map
Multimap<Integer, String> map = LinkedListMultimap.create();
map.put(1, "xue");
map.put(1, "wang");
map.put(1, "zhang");
map.put(1, "zhang");
System.out.println("size:"+map.size());
System.out.println(map);
System.out.println(map.get(1));

// output
size:4
{1=[xue, wang, zhang, zhang]}
[xue, wang, zhang, zhang]

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