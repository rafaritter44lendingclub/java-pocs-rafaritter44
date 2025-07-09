# ASM

- [Official website](https://asm.ow2.io/#:~:text=ASM%20is%20an%20all%20purpose%20Java%20bytecode%20manipulation%20and%20analysis%20framework.%20It%20can%20be%20used%20to%20modify%20existing%20classes%20or%20to%20dynamically%20generate%20classes,%20directly%20in%20binary%20form.)

## Hello, World!

**Print Injector**

- [Package](app/src/main/java/org/example/printinjector)
- [Test](app/src/test/java/org/example/printinjector/PrintInjectorTest.java)

## Main types and methods

1. [ClassVisitor](https://asm.ow2.io/javadoc/org/objectweb/asm/ClassVisitor.html)
    1. [visit(...)](https://asm.ow2.io/javadoc/org/objectweb/asm/ClassVisitor.html#visit(int,int,java.lang.String,java.lang.String,java.lang.String,java.lang.String%5B%5D))
    2. [visitMethod(...)](https://asm.ow2.io/javadoc/org/objectweb/asm/ClassVisitor.html#visitMethod(int,java.lang.String,java.lang.String,java.lang.String,java.lang.String%5B%5D))
    3. [visitField(...)](https://asm.ow2.io/javadoc/org/objectweb/asm/ClassVisitor.html#visitField(int,java.lang.String,java.lang.String,java.lang.String,java.lang.Object))
    4. [visitEnd()](https://asm.ow2.io/javadoc/org/objectweb/asm/ClassVisitor.html#visitEnd())
2. [MethodVisitor](https://asm.ow2.io/javadoc/org/objectweb/asm/MethodVisitor.html)
    1. [visitCode()](https://asm.ow2.io/javadoc/org/objectweb/asm/MethodVisitor.html#visitCode())
    2. [visitLdcInsn(value)](https://asm.ow2.io/javadoc/org/objectweb/asm/MethodVisitor.html#visitLdcInsn(java.lang.Object))
    3. [visitMethodInsn(...)](https://asm.ow2.io/javadoc/org/objectweb/asm/MethodVisitor.html#visitMethodInsn(int,java.lang.String,java.lang.String,java.lang.String,boolean))
    4. [visitFieldInsn(...)](https://asm.ow2.io/javadoc/org/objectweb/asm/MethodVisitor.html#visitFieldInsn(int,java.lang.String,java.lang.String,java.lang.String))
    5. [visitEnd()](https://asm.ow2.io/javadoc/org/objectweb/asm/MethodVisitor.html#visitEnd())
3. [Opcodes](https://asm.ow2.io/javadoc/org/objectweb/asm/Opcodes.html)

## POCs

**Getter Injector**

- [Package](app/src/main/java/org/example/getterinjector)
- [Test](app/src/test/java/org/example/getterinjector/GetterInjectorTest.java)

**Mock Injector**

- [Package](app/src/main/java/org/example/mockinjector)
- [Test](app/src/test/java/org/example/mockinjector/MockInjectorTest.java)

**Coverage Injector**

- [Package](app/src/main/java/org/example/coverageinjector)
- [Test](app/src/test/java/org/example/coverageinjector/CoverageInjectorTest.java)

## JaCoCo

https://github.com/jacoco/jacoco/blob/fc3726eaa6bd48c342d2c0c41b62297d7be4928a/org.jacoco.core/src/org/jacoco/core/internal/instr/InstrSupport.java#L37
https://github.com/jacoco/jacoco/blob/fc3726eaa6bd48c342d2c0c41b62297d7be4928a/org.jacoco.core/src/org/jacoco/core/internal/instr/ClassFieldProbeArrayStrategy.java#L67
https://github.com/jacoco/jacoco/blob/fc3726eaa6bd48c342d2c0c41b62297d7be4928a/org.jacoco.core/src/org/jacoco/core/internal/instr/ProbeInserter.java#L81