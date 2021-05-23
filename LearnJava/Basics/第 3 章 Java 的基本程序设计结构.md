## 第 3 章 Java 的基本程序设计结构



### 数组类型

Java的数组有几个特点：

* **数组是引用类型**

- 数组所有元素初始化为默认值，整型都是`0`，浮点型是`0.0`，布尔型是`false`；
- 数组一旦创建后，**大小**就不可改变。



**数组是引用类型：**

**引用**的意思是：name[x] 是**指向**索引 x 对应的元素

```java
public class Main {
    public static void main(String[] args) {
        int[] names = {0, 1, 2};
        int s = names[1];
        names[1] = 4;
        System.out.println(s); // s是 1 还是 4?
    }//当然是 1 了！names[1] = 4
}
```





#### 遍历数组

`for each`循环：在`for (int n : ns)`循环中，变量`n`直接拿到`ns`数组的元素，而不是索引

```java
public class Main {
    public static void main(String[] args) {
        int[] ns = { 1, 4, 9, 16, 25 };
        for (int n : ns) {
            System.out.println(n);
        }
    }
}
```



`Arrays.toString()`返回数组内容：

```java
int[] ns = { 1, 1, 2, 3, 5, 8 };
System.out.println(Arrays.toString(ns));
```



#### 数组排序

`Arrays.sort()`：升序，nlog(n)

```java
public static void sort(int[] a) {//源码
        DualPivotQuicksort.sort(a, 0, a.length - 1, null, 0, 0);
}
```

*西八看源码好痛苦[^我是懒狗001]*

必须注意，对数组排序实际上修改了数组本身。例如，排序前的数组是：

```
int[] ns = { 9, 3, 6, 5 };
```

在内存中，这个整型数组表示如下：

```ascii
      ┌───┬───┬───┬───┐
ns───>│ 9 │ 3 │ 6 │ 5 │
      └───┴───┴───┴───┘
```

当我们调用`Arrays.sort(ns);`后，这个整型数组在内存中变为：

```ascii
      ┌───┬───┬───┬───┐
ns───>│ 3 │ 5 │ 6 │ 9 │
      └───┴───┴───┴───┘
```

即变量`ns`指向的数组内容已经被改变了。

如果对一个字符串数组进行排序，例如：

```
String[] ns = { "banana", "apple", "pear" };
```

排序前，这个数组在内存中表示如下：

```ascii
                   ┌──────────────────────────────────┐
               ┌───┼──────────────────────┐           │
               │   │                      ▼           ▼
         ┌───┬─┴─┬─┴─┬───┬────────┬───┬───────┬───┬──────┬───┐
ns ─────>│░░░│░░░│░░░│   │"banana"│   │"apple"│   │"pear"│   │
         └─┬─┴───┴───┴───┴────────┴───┴───────┴───┴──────┴───┘
           │                 ▲
           └─────────────────┘
```

调用`Arrays.sort(ns);`排序后，这个数组在内存中表示如下：

```ascii
                   ┌──────────────────────────────────┐
               ┌───┼──────────┐                       │
               │   │          ▼                       ▼
         ┌───┬─┴─┬─┴─┬───┬────────┬───┬───────┬───┬──────┬───┐
ns ─────>│░░░│░░░│░░░│   │"banana"│   │"apple"│   │"pear"│   │
         └─┬─┴───┴───┴───┴────────┴───┴───────┴───┴──────┴───┘
           │                              ▲
           └──────────────────────────────┘
```

原来的3个字符串在内存中均没有任何变化，但是`ns`数组的每个元素**指向**变化了。



#### 多维数组

`ns`数组的每个元素也是一个数组：

```java
int[][] ns = {
            { 1, 2, 3, 4 },
            { 5, 6, 7, 8 },
            { 9, 10, 11, 12 }
        };
        int[] arr0 = ns[0];
```





## 第 4 章 对象与类

面向对象的基本概念，包括：

- 类 (class)
- 实例 (instance)
- 方法

面向对象的实现方式，包括：

- 继承
- 多态



#### class和instance

所以，只要理解了class和instance的概念，基本上就明白了什么是面向对象编程。

class是一种对象模版，它定义了如何创建实例，因此，class本身就是一种数据类型，而instance是对象实例，instance是根据class创建的实例，可以创建多个instance，每个instance类型相同，但各自属性可能不相同



### 方法

*使用方法 ( method ) 来让外部代码可以间接修改域 ( field )*



定义方法：

```java
修饰符 方法返回类型 方法名(方法参数列表) {
    若干方法语句;
    return 方法返回值;
}
```

#### this变量

在方法内部，可以使用一个隐含的变量`this`，它始终指向当前实例。因此，通过`this.field`就可以访问当前实例的字段。

如果没有命名冲突，可以省略`this`。例如：

```
class Person {
    private String name;

    public String getName() {
        return name; // 相当于this.name
    }
}
```

但是，如果有局部变量和字段重名，那么**局部变量优先级更高**，就必须加上`this`以使用域：

```
class Person {
    private String name;

    public void setName(String name) {
        this.name = name; // 前面的this不可少，少了就变成局部变量name了
    }
}
```

#### 可变参数*

可变参数用`类型...`定义，可变参数相当于数组类型：

```
class Group {
    private String[] names;

    public void setNames(String... names) {
        this.names = names;
    }
}
```

上面的`setNames()`就定义了一个可变参数。调用时，可以这么写：

```
Group g = new Group();
g.setNames("Xiao Ming", "Xiao Hong", "Xiao Jun"); // 传入3个String
g.setNames("Xiao Ming", "Xiao Hong"); // 传入2个String
g.setNames("Xiao Ming"); // 传入1个String
g.setNames(); // 传入0个String
```

完全可以把可变参数改写为`String[]`类型：

```
class Group {
    private String[] names;

    public void setNames(String[] names) {
        this.names = names;
    }
}
```

但是，调用方需要自己先构造`String[]`，比较麻烦。例如：

```
Group g = new Group();
g.setNames(new String[] {"Xiao Ming", "Xiao Hong", "Xiao Jun"}); // 传入1个String[]
```

另一个问题是，调用方可以传入`null`：

```
Group g = new Group();
g.setNames(null);
```

而可变参数可以保证无法传入`null`，因为传入0个参数时，接收到的实际值是一个空数组而不是`null`。

#### 方法参数

*方法参数共有两种类型：基本数据类型（数字、布尔值）和对象引用*



**一个方法不可能修改一个一本数据类型的参数**：基本类型参数的传递，是调用方值的复制。双方各自的后续修改，互不影响。

```java
public static void tripleValue(double x){//doesn't work
    x = 3 * x;
}

public static void tripleValue(Employee x){//work
    x.raiseSalary(200);
}
public void raiseSalary(double byPercent){
    double raise = salary * byPercent /100;
    salary += raise;
}
//in main
harry = new Employee(...);
tripleValue(harry);
```

通过上面的例子对比，我们想要改变一个实例的字段值时，调用函数时传的字段`tripleValue(salary)`和函数接收的参数是两码事。

而有效的操作其原理是：x 拷贝了 harry，x 与 harry 共同引用同一个对象，因此改变 x 引用的对象的 salary 也改变了 harry 的。



**总结：无论基本数据类型还是引用，方法中的临时变量总是拷贝的。**

了解这一点之后，书中的这三点也很自然可以理解：

> * 一个方法不能修改一个基本数据类型的参数
> * 一个方法可以改变一个对象参数的状态
> * 一个方法不能让对象参数引用一个新的对象



#### 构造方法、方法重载

*哦...*



方法名相同，但各自的参数不同，称为方法重载 (Overload)，返回类型不是方法签名（signature）的一部分



##### 调用另一个构造器

*this 的另一个含义...*

```java
public Employee(double s){
    //calls Employee(String,double)
    this("skldfj", s);
    nextId++;
}
```

当 `new Employee(123)`时，`Employee(double s)`构造器会先调用 `Employee(String,double)`



### 4.9 文档注释



JDK包含名为javadoc的工具，可以由源文件生成一个HTML文档。



#### 4.9.1 注释的插入



javadoc从下面几个特性中抽取信息：

* 包
* 公有类和接口
* 公有的和受保护的构造器及方法
* 公有的和受保护的域

注释以 /** 开始，以 */ 结束，每个文档注释在标记后紧跟着 *自由格式文本*。标记由@开始。在自由格式文本中可以使用HTML修饰符。









## 第 5 章 继承





### 5.1 父类（超类）、子类

> 注意：子类自动获得了父类的所有字段，严禁定义与父类重名的字段！

```java
public class Manager extends Employee{
    private double bonus;
    
    public void setBonus(double bonus){
        this.bonus = bonus;
    }
}
```

```java
//wrong
public class Manager extends Employee{
    public double getSalary(){
        return salary + bonus;
    }
}
```

Manager 类的 getSalary 方法**不能够直接地访问超类的私有域**，尽管每个 Manager 对象都有一个 salary 的域。如果要访问**需要借助公有接口**，如 Employee 类中的公有方法：

```java
public class Manager extends Employee{
    public double getSalary(){
        double baseSalary = super.getSalary();//OK
        return baseSalary + bonus;
    }
}
```



`super.getSalary()`调用了 Employee 类中的 getSalary 方法。

##### super

`super`关键字表示父类（超类）。子类引用父类的字段时，可以用`super.fieldName`。例如：

```
class Student extends Person {
    public String hello() {
        return "Hello, " + super.name;
    }
}
```

实际上，这里使用`super.name`，或者`this.name`，或者`name`，效果都是一样的。编译器会自动定位到父类的`name`字段。

### 5.1.3 子类构造器

**在Java中，任何`class`的构造方法，第一行语句必须是调用父类的构造方法。如果没有明确地调用父类的构造方法，编译器会帮我们自动加一句`super();`如果超类没有不带参数的构造器，此时就会报错**



由于 Manager 类的构造器也不能访问 Employee 类的私有域，所以必须利用 Employee 类的构造器对这部分私有预进行初始化，这必须是子类构造器的第一条语句：

```java
public Manager(asdklf,jadfkla){//子类构造器
    super(name, salary,asdf);//子类构造器的第一条语句
    bonus = 0;
}
```



### 5.1.5 多态

> 方法名相同，但各自的参数不同，称为方法重载 (Overload)，返回类型不是方法签名（signature）的一部分

在继承关系中，子类如果定义了一个与父类方法签名完全相同的方法，被称为覆写（Override）。

覆写Override 和重载 Overload 不同的是，如果方法签名如果不同（这里特指参数表不同），就是Overload，Overload方法是一个新方法（或者说方法签名是判断方式）；如果方法签名相同，并且返回值也相同，就是`Override`。（签名相同但返回值不同也是不同的方法，在 Java 程序中，出现这种情况编辑器会报错）



加上`@Override`可以让编译器帮助检查是否进行了正确的覆写。希望进行覆写，但是不小心写错了方法签名，编译器会报错：

```java
class Person {
    public void run() {}
}

public class Student extends Person {
    @Override // Compile error!
    public void run(String s) {}
}
```



一个实际类型为`Student`，引用类型为`Person`的变量，调用其`run()`方法，调用的是`Person`还是`Student`的`run()`方法？

实际上调用的方法是`Student`的`run()`方法。因此可得出结论：

Java的实例方法调用是基于运行时的**实际类型**的**动态调用**，而非变量的声明类型。☆

这个非常重要的特性在面向对象编程中称之为**多态** (Polymorphic)



#### 多态☆

多态是指，针对某个类型的方法调用，其真正执行的方法取决于**运行时期实际类型的方法**。



报税例子（太简明了！）：

```java 
public class Main {
    public static void main(String[] args) {
        // 给一个有普通收入、工资收入和享受国务院特殊津贴的小伙伴算税:
        Income[] incomes = new Income[] {// Income 是后两者的父类，后两者都覆写了 Income 的 getTax() 方法
            new Income(3000),
            new Salary(7500),
            new StateCouncilSpecialAllowance(15000)
        };
        System.out.println(totalTax(incomes));
    }

    public static double totalTax(Income... incomes) {//类似数组的可变参数还记得吗？
        double total = 0;
        for (Income income: incomes) {
            total = total + income.getTax();
        }
        return total;
    }
}
```

观察`totalTax()`方法：利用多态，`totalTax()`方法只需要和`Income`打交道，它完全不需要知道`Salary`和`StateCouncilSpecialAllowance`的存在，就可以正确计算出总的税。如果我们要新增一种稿费收入，只需要从`Income`派生，然后正确覆写`getTax()`方法就可以。把新的类型传入`totalTax()`，不需要修改任何代码。

可见，多态具有一个非常强大的功能，就是允许添加更多类型的子类实现功能扩展，却不需要修改基于父类的代码。



#### 覆写 Object 方法



因为所有的`class`最终都继承自`Object`，而`Object`定义了几个重要的方法：

- `toString()`：把instance（实例）输出为`String`；
- `equals()`：判断两个instance（实例）是否逻辑相等；
- `hashCode()`：计算一个instance（实例）的哈希值。

> `Object.toString()`方法（ 建议所有子类都重写此方法）返回一个字符串，该字符串由类名（对象是该类的一个实例）、at 标记符“`@`”和此对象哈希码的无符号十六进制表示组成。换句话说，该方法返回一个字符串，它的值等于：
>
> ```java
> getClass().getName() + '@' + Integer.toHexString(hashCode())
> ```
>
> 
>
> `Obect.equals()`注意：当此方法被重写时，通常有必要重写 `hashCode` 方法，以维护 `hashCode` 方法的常规协定，该协定声明相等对象必须具有相等的哈希码。***这个方法功能本身是倚靠 `hashCode`方法实现的，还记得吗？***



在必要的情况下，我们可以覆写`Object`的这几个方法。

#### 调用 super

*在子类的**覆写方法**中，如果要调用父类的被覆写的方法，可以通过`super`来调用。*



```java
class Person {
    protected String name;
    public String hello() {
        return "Hello, " + name;
    }
}

Student extends Person {
    @Override
    public String hello() {
        // 子类 hello()方法中调用父类的hello()方法:
        return super.hello() + "!";
    }
}
```



#### final



`final`修饰符有多种作用：

- `final`修饰的方法可以阻止被覆写；

- `final`修饰的class可以阻止被继承；

- `final`修饰的field必须在创建对象时初始化，随后不可修改。

  可以在**构造方法**中初始化final字段：

	```Java
	class Person {
    public final String name;
    public Person(String name) {
        this.name = name;
    }
	}
	```
	
	这种方法更为常用，因为可以保证实例一旦创建，其`final`字段就不可修改。





### 抽象类

*如果每个子类都覆写了父类的某个方法...*



Q：如果父类`Person`的`run()`方法没有实际意义，能否去掉方法的执行语句？

A：不行，定义方法时必须实现方法的语句

Q：能不能直接去掉父类`run()`方法？

A：不能，会失去多态的特性：

​	`runTwice()`就无法编译：

```java
public void runTwice(Person p) {
    p.run(); // Person没有run()方法，会导致编译错误
    p.run();
}
```



**如果父类的方法本身不需要实现任何功能，仅仅是为了定义方法签名，目的是让子类去覆写它，那么，可以把父类的方法声明为抽象方法`abstract`☆**



#### 抽象类



抽象方法的类也必须申明为抽象类（abstract class）。使用`abstract`修饰的类就是抽象类。我们无法实例化一个抽象类。



Q：无法实例化的抽象类有什么用？

A：因为抽象类本身**被设计成**只能用于被继承，抽象类可以强迫子类实现其定义的抽象方法，否则编译会报错。因此，抽象方法实际上相当于定义了**“规范”**。



这种尽量引用高层类型，避免引用实际子类型的方式，称之为面向抽象编程。

面向抽象编程的本质就是：

- 上层代码只定义规范（例如：`abstract class Person`）；
- 不需要子类就可以实现业务逻辑（正常编译）；
- 具体的业务逻辑由不同的子类实现，调用者并不关心。



### 接口

*抽象类定义了规范...那接口又是啥？*



> 在抽象类中，抽象方法本质上是定义接口规范：即规定高层类的接口，从而保证所有子类都有相同的接口实现，这样，多态就能发挥出威力。

**如果一个抽象类没有字段，所有方法全部都是抽象方法，就*可以* 把该抽象类改写为接口：`interface`**。



所谓`interface`，就是比抽象类还要抽象的纯抽象接口，因为它**不能有字段**。因为接口定义的所有方法**默认**都是`public abstract`的，所以这两个修饰符不需要写出来（写不写效果都一样）。

**当一个具体的`class`去实现一个`interface`时，需要使用`implements`关键字。**

在Java中，一个类只能继承自另一个类，不能从多个类继承。但是，一个类可以实现多个`interface`，例如：

```java
class Student implements Person, Hello { // 实现了两个interface
    ...
}
```



抽象类和接口的对比如下：

|            | abstract class       | interface                   |
| :--------- | :------------------- | :-------------------------- |
| 继承       | 只能extends一个class | 可以implements多个interface |
| 字段       | 可以定义实例字段     | 不能定义实例字段            |
| 抽象方法   | 可以定义抽象方法     | 可以定义抽象方法            |
| 非抽象方法 | 可以定义非抽象方法   | 可以定义default方法         |



#### 接口继承



一个`interface`可以继承自另一个`interface`。`interface`继承自`interface`使用`extends`，它相当于扩展了接口的方法。相当于拓展了接口的方法。



#### 继承关系☆



> 合理设计`interface`和`abstract class`的继承关系，可以充分复用代码。
>
> 一般来说，公共逻辑适合放在`abstract class`中，具体逻辑放到各个子类，而接口层次代表抽象程度。可以参考Java的集合类定义的一组接口、抽象类以及具体子类的继承关系



在使用的时候，实例化的对象永远只能是某个具体的子类，但总是通过接口去引用它，因为接口比抽象类更抽象：

```java
List list = new ArrayList(); // 用List接口引用具体子类的实例
Collection coll = list; // 向上转型为Collection接口
Iterable it = coll; // 向上转型为Iterable接口
```



#### default 方法



在**接口**中，可以定义`default`方法，`default`方法不需要实现类去覆写。目的是可以增加一个功能，但是不需要也不想要修改所有子类。

`default`方法与抽象类中的普通方法仍很不同，因为`interface`没有字段，所以`default`方法不能是需要访问字段的功能（感觉也就拿来做点输出什么的）。





### 静态字段和静态方法



`static`



实例字段在每个实例中都有自己的一个独立“空间”，但是静态字段只有一个共享“空间”，所有实例都会共享该字段。

静态成员存放在**静态域**中



#### 静态方法



静态方法属于`class`而不属于实例，因此，静态方法内部，无法访问`this`变量，也无法访问实例字段，它只能访问静态字段。（使用时通过类名调用哦）☆



#### 接口的静态字段

*诶，接口正好没有实例字段哦*



接口可以有静态字段，必须是`final`类型的，实际上只能是`public static final`类型，所以这些关键字可以全都去掉。





### 包



**我们使用`package`来解决类名冲突☆**

Java定义了一种名字空间，称之为包：`package`。一个类总是属于某个包，类名（比如`Person`）只是一个简写，真正的完整类名是`包名.类名`。



> **没有定义包名的`class`，它使用的是默认包，非常容易引起名字冲突，因此，不推荐不写包名的做法。**
>
> 我们还需要按照包结构把上面的Java文件组织起来。假设以`package_sample`作为根目录，`src`作为源码目录，那么所有文件结构就是：
>
> ```ascii
> package_sample
> └─ src
>     ├─ hong
>     │  └─ Person.java //hong.Person
>     │  ming
>     │  └─ Person.java
>     └─ mr
>        └─ jun
>           └─ Arrays.java
> ```
>
> 即所有Java文件对应的目录层次要和包的层次一致。
>
> 编译后的`.class`文件也需要按照包结构存放。



#### 包作用域



位于同一个包的类，可以访问包作用域的字段和方法。不用`public`、`protected`、`private`修饰的字段和方法就是包作用域。



#### import



> Java编译器最终编译出的`.class`文件只使用*完整类名*，因此，在代码中，当编译器遇到一个`class`名称时：
>
> - 如果是完整类名，就直接根据完整类名查找这个`class`；
> - 如果是简单类名，按下面的顺序依次查找：
>   - 查找当前`package`是否存在这个`class`；
>   - 查找`import`的包是否包含这个`class`；
>   - 查找`java.lang`包是否包含这个`class`。
>
> 如果按照上面的规则还无法确定类名，则编译报错。



> 编写class的时候，编译器会自动帮我们做两个import动作：
>
> - 默认自动`import`当前`package`的其他`class`；
> - 默认自动`import java.lang.*`。







一个用来判断是否应该设计为继承关系的规则：**"is-a"** 规则：子类的每个对象也是超类的对象。其另一种表述法是**置换规则**：程序中出现超类对象的任何地方都可以用子类对象置换。

Java中，对象变量是**多态**的。一个 Employee 变量既可以引用一个 Employee 类对象，也可以引用一个 Employee 类的任何一个子类的对象，但不能将一个超类的引用赋给子类变量。

```java
Manager boss = new Manager(...);
Employee[] staff = new Employee[3];
staff[0] = boss;
Manager m = staff[1];//Err

boss.setBonus(500);//OK
staff.setBonus(40);//Err
```

因为`staff[0]`**声明的**类型是 Employee，而 setBonus 不是 Employee 类的方法。实现类可以不必覆写`default`方法。`default`方法的目的是，当我们需要给接口新增一个方法时，会涉及到修改全部子类。如果新增的是`default`方法，那么子类就不必全部修改，只需要在需要覆写的地方去覆写新增方法。

`default`方法和抽象类的普通方法是有所不同的。因为`interface`没有字段，`default`方法无法访问字段，而抽象类的普通方法可以访问实例字段。







### 5.1.10 受保护访问——作用域

> 为了让子类可以访问父类的字段，我们需要把`private`改为`protected`，`protected`关键字可以把字段和方法的访问权限控制在继承树内部。

Java 用于控制可见性的 4 个访问修饰符：

1. private——仅对本类可见
2. public——对所有类可见
3. protected——对本包和所有**子类**可见
4. 默认——对本包可见



**Warning**：在覆盖一个方法时，子类方法不能低于超类方法的可见性。



由于Java支持嵌套类，如果一个类内部还定义了嵌套类，那么，嵌套类拥有访问`private`的权限。☆（我才知道可以嵌套类）



#### 包作用域

*虽然前面有这个标题，但是我们可以再说说*



包名必须完全一致，包没有父子关系，`com.apache`和`com.apache.abc`是不同的包。



#### 局部变量



在**方法内部**定义的变量称为局部变量，局部变量作用域从变量**声明处开始到对应的块结束**。方法参数也是局部变量。



#### final



参见之前内容...



### 内部类*

*好像没啥用...*



#### Inner Class



一个类定义在另一个类的内部，最经典的情景。与普通类的不同点在于`inner class`的实例不能单独存在。

```java
Outer outer = new Outer("Nested"); // 实例化一个Outer
Outer.Inner inner = outer.new Inner(); // 调用Outer的实例来实例化一个Inner
inner.hello();
```

Inner Class 可以修改 Outer Class 的私有域。



#### 匿名类



表现为在方法中实例化了一个接口、普通类

```java
Runnable r = new Runnable() {//在方法中
    // 实现必要的抽象方法...
};
```



#### Static Nested Class



能访问 Outer 的 私有静态字段和静态方法。[^存在私有静态字段吗？]





### classpath 与 jar

*书里13章？...*



`classpath`是JVM用到的一个环境变量，它用来指示JVM如何搜索`class`。



#### jar 包



把`package`组织的目录层级，以及各个目录下的所有文件（包括`.class`文件和其他文件）都打成一个jar文件，方法是打成zip包然后改后缀名。



**注意：**jar 包里的第一层目录不应该是 bin，饵应该直接是 package 们



### 模块*



Java 9





### 5.2.3 hashCode 方法



*待编辑*





























## 第 6 章 接口、lambda 表达式与内部类





## 第 7 章 异常、断言和日志





## 第 8 章 泛型程序设计





## 第 9 章 集合

### 集合简介

数组有如下限制：

- 数组初始化后大小不可变；
- 数组只能按索引顺序存取。

![img](https://www.runoob.com/wp-content/uploads/2014/01/2243690-9cd9c896e0d512ed.gif)

#### Collection

提问：集合接口和实现类分离的意义何在呢？☆

Java标准库自带的`java.util`包提供了集合类：`Collection`，它是除`Map`外所有其他集合类的根接口。Java的`java.util`包主要提供了以下三种类型的集合：

- `List`：一种**有序列表**的集合，例如，按索引排列的`Student`的`List`；
- `Set`：一种保证**没有重复元素**的集合，例如，所有无重复名称的`Student`的`Set`；
- `Map`：一种通过**键值对**（key-value）查找的映射表集合，例如，根据`Student`的`name`查找对应`Student`的`Map`。

Java集合的设计有几个特点：一是实现了**接口和实现类**相分离，例如，有序表的接口是`List`，具体的实现类有`ArrayList`，`LinkedList`等，二是支持泛型，我们可以限制在一个集合中只能放入**同一种数据类型**的元素，例如：

```
List<String> list = new ArrayList<>(); // 只能放入String类型
```

最后，**Java访问集合总是通过统一的方式——迭代器**（Iterator）来实现，它最明显的好处在于无需知道集合内部元素是按什么方式存储的。



> 由于Java的集合设计非常久远，中间经历过大规模改进，我们要注意到有一小部分集合类是遗留类，不应该继续使用：
>
> - `Hashtable`：一种线程安全的`Map`实现；
> - `Vector`：一种线程安全的`List`实现；
> - `Stack`：基于`Vector`实现的`LIFO`的栈。
>
> 还有一小部分接口是遗留接口，也不应该继续使用：
>
> - `Enumeration<E>`：已被`Iterator<E>`取代。



### 使用List

*List 是最基础的一种集合：它是一种**有序**列表*



#### 数组、ArrayList、LinkedList



`List`的行为和数组几乎完全相同：`List`内部按照放入元素的先后顺序存放，每个元素都可以通过索引确定自己的位置，`List`的索引和数组一样，从`0`开始。

让我们使用 List 而不是数组的关键在于数组元素的**增删**十分不方便：需要对被操作元素以后的元素都进行操作。因此，在实际应用中，**需要增删元素**的有序列表，我们使用最多的是`ArrayList`。实际上，`ArrayList`在内部使用了数组来存储所有元素。

`ArrayList`把添加和删除的操作封装起来，让我们操作`List`类似于操作数组，却不用关心内部元素如何移动。

我们考察`List<E>`接口，可以看到几个主要的接口方法：

- 在末尾添加一个元素：`boolean add(E e)`
- 在指定索引添加一个元素：`boolean add(int index, E e)`
- 删除指定索引的元素，返回被删除元素内容：`E remove(int index)`
- 删除某个元素：`boolean remove(Object e)`
- 获取指定索引的元素：`E get(int index)`
- 获取链表大小（包含元素的个数）：`int size()`
- 判断`List`是否包含某个指定元素：`boolean contains(Object o)`



在`LinkedList`中，它的内部每个元素都指向下一个元素：

```shell
        ┌───┬───┐   ┌───┬───┐   ┌───┬───┐   ┌───┬───┐
HEAD ──>│ A │ ●─┼──>│ B │ ●─┼──>│ C │ ●─┼──>│ D │   │
        └───┴───┘   └───┴───┘   └───┴───┘   └───┴───┘
```

比较一下`ArrayList`和`LinkedList`：

|                     | ArrayList    | LinkedList           |
| :------------------ | :----------- | :------------------- |
| 获取指定元素        | 速度很快     | 需要从头开始查找元素 |
| 添加元素到末尾      | 速度很快     | 速度很快             |
| 在指定位置添加/删除 | 需要移动元素 | 不需要移动元素       |
| 内存占用            | 少           | 较大                 |



#### 创建 List



除了使用`ArrayList`和`LinkedList`，我们还可以通过`List`接口提供的`of()`方法，根据给定元素快速创建`List`：

```java
List<String> list1 = new ArrayList<>();
list.add("apple"); // size=1
List<Integer> list2 = List.of(1, 2, 5);
```

> `List.of()`方法不接受 null 值



#### 遍历 List

*可以用 for，但没必要*



使用 for 的两个缺点：

1. 代码复杂，
2. `get(int)`方法只有`ArrayList`的实现是高效的，换成`LinkedList`后，索引越大，访问速度越慢。



**要始终坚持使用迭代器`Iterator`来访问`List`**☆`Iterator`本身也是一个对象，但它**是由`List`的实例调用`iterator()`方法的时候创建的**。`Iterator`对象知道如何遍历一个`List`，并且不同的`List`类型，返回的`Iterator`对象实现也是不同的，但**总是具有最高的访问效率。**

`Iterator`对象有两个方法：

* `boolean hasNext()`判断是否有下一个元素
* `E next()`返回下一个元素。

​```java
public class Main {
    public static void main(String[] args) {
        List<String> list = List.of("apple", "pear", "banana");
        for (Iterator<String> it = list.iterator(); it.hasNext(); ) {//hasNext()作为条件避免越界异常
            String s = it.next();
            System.out.println(s);
        }
    }
}
```



**Java的`for each`循环本身就可以帮我们使用`Iterator`遍历。**实际上，只要实现了`Iterable`接口的集合类都可以直接用`for each`循环来遍历，Java编译器本身并不知道如何遍历集合对象，但它会自动把`for each`循环变成`Iterator`的调用，原因就在于`Iterable`接口定义了一个`Iterator<E> iterator()`方法，强迫集合类必须返回一个`Iterator`实例。



#### List 和 Array 转换





### 编写 equals 方法

要正确使用`List`的`contains()`、`indexOf()`这些方法，放入的实例必须正确覆写`equals()`方法，否则，放进去的实例，查找不到。我们之所以能正常放入`String`、`Integer`这些对象，**即便我们新建的实例也可以被检测出来表中已有**，是因为Java标准库定义的这些类已经正确实现了`equals()`方法。



#### 编写equals

如何正确编写`equals()`方法？`equals()`方法要求我们必须满足以下条件：

- 自反性（Reflexive）：对于非`null`的`x`来说，`x.equals(x)`必须返回`true`；
- 对称性（Symmetric）：对于非`null`的`x`和`y`来说，如果`x.equals(y)`为`true`，则`y.equals(x)`也必须为`true`；
- 传递性（Transitive）：对于非`null`的`x`、`y`和`z`来说，如果`x.equals(y)`为`true`，`y.equals(z)`也为`true`，那么`x.equals(z)`也必须为`true`；
- 一致性（Consistent）：对于非`null`的`x`和`y`来说，只要`x`和`y`状态不变，则`x.equals(y)`总是一致地返回`true`或者`false`；
- 对`null`的比较：即`x.equals(null)`永远返回`false`。





总结一下`equals()`方法的正确编写方法：

1. 先**确定**实例“相等”的**逻辑**，即哪些字段相等，就认为实例相等；
2. 用`instanceof`判断传入的待比较的`Object`是不是当前类型，如果是，继续比较，否则，返回`false`；
3. 对引用类型用`Objects.equals()`比较，对基本类型直接用`==`比较。

使用`Objects.equals()`比较两个引用类型是否相等的目的是省去了判断`null`的麻烦。两个引用类型都是`null`时它们也是相等的。



### 使用 Map

*键值对：成绩表...*



`Map`这种键值（key-value）映射表的数据结构，作用就是能高效通过`key`快速查找`value`（元素）。

用`Map`来实现根据`name`查询某个`Student`的代码如下：

```java
public class Main {
    public static void main(String[] args) {
        Student s = new Student("Xiao Ming", 99);
        Map<String, Student> map = new HashMap<>();
        map.put("Xiao Ming", s); // 将"Xiao Ming"和Student实例映射并关联
        Student target = map.get("Xiao Ming"); // 通过key查找并返回映射的Student实例
        System.out.println(target == s); // true，同一个实例
        System.out.println(target.score); // 99
        Student another = map.get("Bob"); // 通过另一个key查找
        System.out.println(another); // 未找到返回null
    }
}

class Student {
    public String name;
    public int score;
    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
```



和`List`类似，`Map`也是一个接口，最常用的实现类是`HashMap`。

如果只是想查询某个`key`是否存在，可以调用`boolean containsKey(K key)`方法。



**重复放入**并不会有任何问题，但是值会被更新。始终牢记：**Map中不存在重复的key**，因此放入相同的key，只会把原有的key-value对应的value给替换掉。



#### 遍历 Map



对`Map`来说，**要遍历`key`**可以使用`for each`循环遍历`Map`实例的`keySet()`方法返回的`Set`集合，它包含不重复的`key`的集合：

```java
public class Main {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 123);
        map.put("pear", 456);
        map.put("banana", 789);
        for (String key : map.keySet()) {
            Integer value = map.get(key);
            System.out.println(key + " = " + value);
        }
    }
}
```

**同时遍历`key`和`value`**可以使用`for each`循环遍历`Map`对象的`entrySet()`集合，它包含每一个`key-value`映射：

```java
public class Main {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 123);
        map.put("pear", 456);
        map.put("banana", 789);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + " = " + value);
        }
    }
}
```



`Map`和`List`不同的是，`Map`存储的是`key-value`的映射关系，并且，它**不保证顺序**☆



### 编写 equals 和 hashCode



正确使用`Map`必须保证：作为`key`的对象必须正确覆写`equals()`方法。

> 我们放入`Map`的`key`是字符串`"a"`，但是，当我们获取`Map`的`value`时，传入的变量不一定就是放入的那个`key`对象。
>
> 换句话讲，两个`key`应该是内容相同，但不一定是同一个对象。

我们经常使用`String`作为`key`，因为`String`已经正确覆写了`equals()`方法。但如果我们放入的`key`是一个自己写的类，就必须保证正确覆写了`equals()`方法。



再思考一下`HashMap`为什么能通过`key`直接计算出`value`存储的索引。相同的`key`对象（使用`equals()`判断时返回`true`）必须要计算出**相同的索引**，否则，相同的`key`每次取出的`value`就不一定对。

通过`key`计算索引的方式就是调用`key`对象的`hashCode()`方法，它返回一个`int`整数。`HashMap`正是通过这个方法直接定位`key`对应的`value`的索引，继而直接返回`value`。

因此，正确使用`Map`必须保证：

1. 作为`key`的对象必须正确覆写`equals()`方法，相等的两个`key`**实例**调用`equals()`必须返回`true`；
2. 作为`key`的对象还必须正确覆写`hashCode()`方法，且`hashCode()`方法要严格遵循以下规范：

- 如果两个对象相等，则两个对象的`hashCode()`必须相等；
- 如果两个对象不相等，则两个对象的`hashCode()`尽量不要相等。

即对应两个实例`a`和`b`：

- 如果`a`和`b`相等，那么`a.equals(b)`一定为`true`，则`a.hashCode()`必须等于`b.hashCode()`；
- 如果`a`和`b`不相等，那么`a.equals(b)`一定为`false`，则`a.hashCode()`和`b.hashCode()`尽量不要相等。

上述第一条规范是正确性，必须保证实现，否则`HashMap`不能正常工作。

而第二条如果尽量满足，则可以保证查询效率，因为不同的对象，如果返回相同的`hashCode()`，会造成`Map`内部存储冲突，使存取的效率下降。









## 第 10 章 图形程序设计





## 第 11 章 事件处理





## 第 12 章 Swing 用户界面组件





## 第 13 章 部署 Java 应用程序





## 第 14 章 并发



[^我是懒狗001]: 2021/5/22 Waiting for..
[^存在私有静态字段吗？]: 
