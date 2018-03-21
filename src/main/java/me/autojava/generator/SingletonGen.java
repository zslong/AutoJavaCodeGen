package me.autojava.generator;

import com.sun.codemodel.*;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by shilong.zhang on 2018/2/1.
 */

@Component
public class SingletonGen {
    public void genSingleton() throws Exception {
        JCodeModel cm = new JCodeModel();
        JType type = cm.parseType("Singleton");
        File destDir = new File("src/main/java");
        JDefinedClass dc = cm._class("com.sumscope.idb.dcs.DealCodeGen.dto.Singleton");

        dc.field(JMod.PRIVATE + JMod.STATIC, type, "instance"); // define private static member
        dc.constructor(JMod.PRIVATE);

        JMethod getInstanceMethod = dc.method(JMod.PUBLIC + JMod.STATIC, type, "getInstance");
        JBlock getInstanceBody = getInstanceMethod.body();

        JFieldRef fieldRef = JExpr.ref("instance");
        JConditional conditionalIf = getInstanceBody._if(fieldRef.eq(JExpr._null()));
        JBlock thenPart = conditionalIf._then();
        thenPart.assign(fieldRef, JExpr._new(type));
        getInstanceBody._return(fieldRef);

        JMethod sayHelloMethod = dc.method(JMod.PUBLIC, cm.parseType("void"), "sayHello");
        sayHelloMethod.javadoc().add("This method will say Hello to the name");
        JBlock sayHelloBody = sayHelloMethod.body();
        sayHelloMethod.param(cm.parseType("String"), "name");
        JClass sys = cm.ref("java.lang.System");
        JFieldRef ot = sys.staticRef("out");
        JExpression sentance1 = JExpr.lit("Hello ").invoke("concat").arg(JExpr.ref("name"));
        JExpression sentance2 = sentance1.invoke("concat").arg("!");
        sayHelloBody.invoke(ot, "println").arg(sentance2);

        cm.build(destDir);
    }

    public static void main(String[] args) {
        SingletonGen sg = new SingletonGen();
        try {
            sg.genSingleton();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
