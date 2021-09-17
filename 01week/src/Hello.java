public class Hello {
    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            if (true){
                int a = 1+2*3/4;
            }
        }
    }
}
//
//Classfile /D:/Hello.class
//Last modified 2021-9-17; size 434 bytes
//        MD5 checksum 25fef6230fe2e5fc5e7c2e1bd56057ba
//        Compiled from "Hello.java"
//public class Hello
//  minor version: 0
//        major version: 52 java版本号
//        flags: ACC_PUBLIC, ACC_SUPER
//        Constant pool:
//        #1 = Methodref          #3.#20         // java/lang/Object."<init>":()V
//        #2 = Class              #21            // Hello
//        #3 = Class              #22            // java/lang/Object
//        #4 = Utf8               <init>
//   #5 = Utf8               ()V
//           #6 = Utf8               Code
//           #7 = Utf8               LineNumberTable
//           #8 = Utf8               LocalVariableTable
//           #9 = Utf8               this
//           #10 = Utf8               LHello;
//           #11 = Utf8               main
//           #12 = Utf8               ([Ljava/lang/String;)V
//           #13 = Utf8               i
//           #14 = Utf8               I
//           #15 = Utf8               args
//           #16 = Utf8               [Ljava/lang/String;
//           #17 = Utf8               StackMapTable
//           #18 = Utf8               SourceFile
//           #19 = Utf8               Hello.java
//           #20 = NameAndType        #4:#5          // "<init>":()V
//           #21 = Utf8               Hello
//           #22 = Utf8               java/lang/Object
//           {
//public Hello();
//        descriptor: ()V
//        flags: ACC_PUBLIC
//        Code:
//        stack=1, locals=1, args_size=1
//        0: aload_0
//        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
//        4: return
//        LineNumberTable:
//        line 1: 0
//        LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//        0       5     0  this   LHello;
//
//public static void main(java.lang.String[]);
//        descriptor: ([Ljava/lang/String;)V
//        flags: ACC_PUBLIC, ACC_STATIC
//        Code:
//        stack=2, locals=3, args_size=1
//        0: iconst_0                  //获取常量
//        1: istore_1                  // i = 0 赋值
//        2: iload_1
//        3: iconst_1
//        4: if_icmpge     15
//        7: iconst_2
//        8: istore_2
//        9: iinc          1, 1
//        12: goto          2
//        15: return
//        LineNumberTable:
//        line 3: 0
//        line 5: 7
//        line 3: 9
//        line 8: 15
//        LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//        2      13     1     i   I
//        0      16     0  args   [Ljava/lang/String;
//        StackMapTable: number_of_entries = 2
//        frame_type = 252 /* append */
//        offset_delta = 2
//        locals = [ int ]
//        frame_type = 250 /* chop */
//        offset_delta = 12
//        }
//        SourceFile: "Hello.java"
