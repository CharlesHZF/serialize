# serialize
serialize
Java使用Protocol Buffers入门四步骤
时间 2014-07-03 12:56:00  CSDN博客
原文  http://blog.csdn.net/xiao__gui/article/details/36643949
主题 Java Protobuf
Protocol Buffers（简称protobuf）是谷歌的一项技术，用于将结构化的数据序列化、反序列化，经常用于网络传输。

这货实际上类似于XML生成和解析，但protobuf的效率高于XML，不过protobuf生成的是字节码，可读性比XML差。类似的还有json、Java的Serializable等。

protobuf支持各种语言。本文以Java为例，简单介绍protobuf如何使用。其他语言使用方法类似。

http://download.csdn.net/download/xiao__gui/7586617

解压后有两个文件：protobuf-java-2.5.0.jar和protoc.exe。

protobuf-java-2.5.0.jar即protobuf所需要的jar包，如果用maven的话可以无视这个文件；

protoc.exe是protobuf代码生成工具。


第一步：定义数据结构

首先要定义protobuf的数据结构，这里要写一个.proto文件。这个文件有点类似于定义一个类。例如定义一个Person，保存文件PersonMsg.proto（注意文件名和里面的message名不要一样）。

message Person {
  
  // ID（必需）
  required int32 id = 1;
  
  // 姓名（必需）
  required string name = 2;
  
  // email（可选）
  optional string email = 3;

  // 朋友（集合）
  repeated string friends = 4;
}
上面的1、2、3、4是unique numbered tag，是一个唯一标识。

上面的例子中定义了一个非常简单的数据结构，当然还可以定义更复杂的结构，这里不再讨论，具体可以看官方文档。


第二步：protoc.exe生成Java代码

使用文件protoc.exe，cmd命令行运行：

protoc.exe --java_out=E:\java PersonMsg.proto

输入文件是PersonMsg.proto，也就是定义数据结构的文件；输出文件夹是E:\java，将java文件生成在E:\java中。运行命令成功后会生成PersonMsg.java：


在Eclipse中创建一个项目，将java文件拷贝到项目中。项目中需要引入protobuf-java-2.5.0.jar包。如果是maven项目则加入：

<dependency>
  <groupId>com.google.protobuf</groupId>
  <artifactId>protobuf-java</artifactId>
  <version>2.5.0</version>
</dependency>

第三步：序列化

一般来说，序列化和反序列化是分开的。例如网络传输，由一方将数据序列化后发送给另一方来接收并解析，序列化发送和接收反序列化并不在一起。但是下面为了例子简单将二者写在同一程序中。

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Main {

  public static void main(String[] args) throws IOException {
    
    // 按照定义的数据结构，创建一个Person
    PersonMsg.Person.Builder personBuilder = PersonMsg.Person.newBuilder();
    personBuilder.setId(1);
    personBuilder.setName("叉叉哥");
    personBuilder.setEmail("xxg@163.com");
    personBuilder.addFriends("Friend A");
    personBuilder.addFriends("Friend B");
    PersonMsg.Person xxg = personBuilder.build();
    
    // 将数据写到输出流，如网络输出流，这里就用ByteArrayOutputStream来代替
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    xxg.writeTo(output);
    
    // -------------- 分割线：上面是发送方，将数据序列化后发送 ---------------
    
    byte[] byteArray = output.toByteArray();
    
    // -------------- 分割线：下面是接收方，将数据接收后反序列化 ---------------
    
    // 接收到流并读取，如网络输入流，这里用ByteArrayInputStream来代替
    ByteArrayInputStream input = new ByteArrayInputStream(byteArray);
    
    // 反序列化
    PersonMsg.Person xxg2 = PersonMsg.Person.parseFrom(input);
    System.out.println("ID:" + xxg2.getId());
    System.out.println("name:" + xxg2.getName());
    System.out.println("email:" + xxg2.getEmail());
    System.out.println("friend:");
    List<String> friends = xxg2.getFriendsList();
    for(String friend : friends) {
      System.out.println(friend);
    }
  }

}
