# javautils
Java工具类，Java程序员日常开发会用到的工具类

## JsonUtil
提供了 Jackson/fastjson/gson 三个JSON库的工具方法，没有添加 Json-lib 工具方法，一是依赖jar包比较多，二是性能较为低下。
### 对比三种 JSON 库
fastjson/gson 不依赖于其他库，jackson 依赖的库也较少。都提供了从实体到JSON串和JSON串到实体的转换API，三种库虽然都可以实现将 JSON 串转换为对应的实体集合，但是 fastjson 本身就提供了这样的API，而 jackson/gson 还需要额外的步骤进行操作。FastJson 的 API 设计最为简单，使用最为方便。同时在 pom 文件中给出了对应的依赖。
1. Gson（项目地址：https://github.com/google/gson）
- Gson是目前功能最全的Json解析神器，Gson当初是为因应Google公司内部需求而由Google自行研发而来，但自从在2008年五月公开发布第一版后已被许多公司或用户应用。Gson的应用主要为toJson与fromJson两个转换函数，无依赖，不需要例外额外的jar，能够直接跑在JDK上。而在使用这种对象转换之前需先创建好对象的类型以及其成员才能成功的将JSON字符串成功转换成相对应的对象。类里面只要有get和set方法，Gson完全可以将复杂类型的json到bean或bean到json的转换，是JSON解析的神器。

2. FastJson（项目地址：https://github.com/alibaba/fastjson）
- Fastjson是一个Java语言编写的高性能的JSON处理器,由阿里巴巴公司开发。无依赖，不需要例外额外的jar，能够直接跑在JDK上。FastJson在复杂类型的Bean转换Json上会出现一些问题，可能会出现引用的类型，导致Json转换出错，需要制定引用。FastJson采用独创的算法，将parse的速度提升到极致，超过所有json库。

3. Jackson（项目地址：https://github.com/FasterXML/jackson）
- 相比json-lib框架，Jackson所依赖的jar包较少，简单易用并且性能也要相对高些。而且Jackson社区相对比较活跃，更新速度也比较快。Jackson对于复杂类型的json转换bean会出现问题，一些集合Map，List的转换出现问题。Jackson对于复杂类型的bean转换Json，转换的json格式不是标准的Json格式。