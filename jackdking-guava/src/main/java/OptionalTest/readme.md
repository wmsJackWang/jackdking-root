
##方法	描述
empty	        返回一个空的Optional 实例
of	            将指定值用Optional封装之后返回，如果该值为null，则抛出一个NullPointerException 异常
ofNullable	    将指定值用 Optional 封装之后返回，如果该值为 null，则返回一个空的 Optional 对象
filter	        如果值存在并且满足提供的谓词，就返回包含该值的Optional对象；否则返回一个空的Optional 对象
map	            如果值存在，就对该值执行提供的 mapping函数调用
flatMap	        如果值存在，就对该值执行提供的 mapping函数调用，返回一个 Optional 类型的值，否则就返 回一个空的 Optional 对象
get	            如果该值存在，将该值用Optional封装返回，否则抛出一个NoSuchElementException异常
orElse	        如果有值则将其返回，否则返回一个默认值
orElseGet	    如果有值则将其返回，否则返回一个由指定的 Supplier接口生成的值
orElseThrow	    如果有值则将其返回，否则抛出一个由指定的 Supplier接口生成的异常
ifPresent	    如果值存在，就执行使用该值的方法调用，否则什么也不做
isPresent	    如果值存在就返回true，否则返回 false

##包含了以上这些方法的使用场景和代码实例