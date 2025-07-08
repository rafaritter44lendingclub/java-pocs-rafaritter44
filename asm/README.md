# ASM

## Main classes and methods

1. [ClassVisitor](https://asm.ow2.io/javadoc/org/objectweb/asm/ClassVisitor.html)
    - [visit(...)](https://asm.ow2.io/javadoc/org/objectweb/asm/ClassVisitor.html#visit(int,int,java.lang.String,java.lang.String,java.lang.String,java.lang.String%5B%5D))
    - [visitMethod(...)](https://asm.ow2.io/javadoc/org/objectweb/asm/ClassVisitor.html#visitMethod(int,java.lang.String,java.lang.String,java.lang.String,java.lang.String%5B%5D))
    - [visitField(...)](https://asm.ow2.io/javadoc/org/objectweb/asm/ClassVisitor.html#visitField(int,java.lang.String,java.lang.String,java.lang.String,java.lang.Object))
    - [visitEnd()](https://asm.ow2.io/javadoc/org/objectweb/asm/ClassVisitor.html#visitEnd())
2. [MethodVisitor](https://asm.ow2.io/javadoc/org/objectweb/asm/MethodVisitor.html)
    - [visitCode()]()
    - [visitFieldInsn(...)]()
    - [visitLdcInsn(...)]()
    - [visitMethodInsn(...)]()
    - [visitVarInsn(...)]()
    - [visitInsn(...)]()
    - [visitMaxs(...)]()
    - [visitIntInsn(...)]()
    - [visitEnd()]()
3. [Opcodes](https://asm.ow2.io/javadoc/org/objectweb/asm/Opcodes.html)

## JaCoCo

https://github.com/jacoco/jacoco/blob/fc3726eaa6bd48c342d2c0c41b62297d7be4928a/org.jacoco.core/src/org/jacoco/core/internal/instr/InstrSupport.java#L37
https://github.com/jacoco/jacoco/blob/fc3726eaa6bd48c342d2c0c41b62297d7be4928a/org.jacoco.core/src/org/jacoco/core/internal/instr/ClassFieldProbeArrayStrategy.java#L67
https://github.com/jacoco/jacoco/blob/fc3726eaa6bd48c342d2c0c41b62297d7be4928a/org.jacoco.core/src/org/jacoco/core/internal/instr/ProbeInserter.java#L81