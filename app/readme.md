# gankk

构造函数  参考AboutActivityZ

$符号，表示字符串模板 参考AboutActivityZ
如:var userInfo = "name:${user.name},  age:$age"

!! 对于NPE 爱好者，我们可以写 b!! ，这会返回一个非空的 b 值 或者如果 b 为空，就会抛出一个 NPE 异常：
如:val l = b!!.length
一般可以不用

?问号 表示这个对象可能为空
text = null
text?.text = "ttttttttt"
这样不会有异常

?:
val l = b?.length ?: -1
如果 ?: 左侧表达式非空，elvis操作符就返回其左侧表达式，否则返回右侧表达式。请注意，当且仅当左侧为空时，才会对右侧表达式求值。

==号与===号
==判断值是否相等，===判断值及引用是否完全相等。