package me.autojava.generator;

import me.autojava.dao.DcsFieldDao;
import me.autojava.model.DcsField;
import me.autojava.model.Product;
import com.sun.codemodel.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shilong.zhang on 2018/2/2.
 */

@Component
public class DealGen {

    @Resource
    private DcsFieldDao dcsFieldDao;

    @Value("${gen.dest.dir:src/main/java}")
    private String destDir;

    @Value("${gen.package.name:me.autojava.dto}")
    private String packageName;

    private JCodeModel cm = new JCodeModel();

    public void generateBaseDeal() throws Exception {
        JDefinedClass dc = cm._class(JMod.PUBLIC + JMod.ABSTRACT, packageName.concat(".DcsDeal"), ClassType.CLASS);
        dc._implements(Cloneable.class);

        dc.javadoc().add("DCS deal base abstract class, contains all the fields.");

        List<DcsField> dfList = dcsFieldDao.getAllFields();

        // generate fields
        for (DcsField f : dfList) {
            dc.field(JMod.PRIVATE, cm.parseType("String"), f.getName());
        }

        // generate setters and getters
        for (DcsField f: dfList) {
            JMethod setter = dc.method(JMod.PUBLIC, cm.parseType("void"),
                    "set".concat(StringUtils.capitalize(f.getName())));
            setter.param(cm.parseType("String"), "_".concat(f.getName()));
            JBlock sbody = setter.body();
            sbody._throw(JExpr._new(cm.parseType("RuntimeException")).arg("unsupported field ".concat(f.getName())));

            JMethod getter = dc.method(JMod.PUBLIC, cm.parseType("String"),
                    "get".concat(StringUtils.capitalize(f.getName())));
            JBlock gbody = getter.body();
            gbody._throw(JExpr._new(cm.parseType("RuntimeException")).arg("unsupported field ".concat(f.getName())));
        }

        // generate clone
        generateCloneMethod(dc);

        // generate getFieldsMap
        generateFieldMapMethod(dc, dfList, true, false);
    }

    public void generateProductDeal(Product product) throws Exception {
        JDefinedClass dc = cm._class(JMod.PUBLIC,
                String.format("%s.%sDcsDeal", packageName, StringUtils.capitalize(product.name().toLowerCase())), ClassType.CLASS);
        dc._extends(cm._getClass(packageName.concat(".DcsDeal")));

        dc.javadoc().add(String.format("%s DCS deal.", product.name()));

        List<DcsField> fields = dcsFieldDao.getProductFields(product);

        // generate fields
        for (DcsField f : fields) {
            dc.field(JMod.PRIVATE, cm.parseType("String"), f.getName());
        }

        // generate setters and getters
        for (DcsField f : fields) {
            JMethod setter = dc.method(JMod.PUBLIC, cm.parseType("void"),
                    "set".concat(StringUtils.capitalize(f.getName())));
            setter.annotate(Override.class);
            setter.param(cm.parseType("String"), "_".concat(f.getName()));
            JBlock sbody = setter.body();
            sbody.assign(JExpr.ref(f.getName()), JExpr.ref("_".concat(f.getName())));

            JMethod getter = dc.method(JMod.PUBLIC, cm.parseType("String"),
                    "get".concat(StringUtils.capitalize(f.getName())));
            getter.annotate(Override.class);
            JBlock gbody = getter.body();
            gbody._return(JExpr.ref(f.getName()));
        }

        // generate clone
        generateCloneMethod(dc);

        // generate getFieldsMap
        generateFieldMapMethod(dc, fields, false, true);
    }

    public void writeToFile() throws IOException {
        File dest = new File(this.destDir);
        cm.build(dest);
    }

    private void generateCloneMethod(JDefinedClass dc) throws ClassNotFoundException {
        JMethod clone = dc.method(JMod.PUBLIC, cm.parseType("Object"), "clone");
        clone.annotate(Override.class);
        clone._throws(CloneNotSupportedException.class);
        JBlock cb = clone.body();
        cb._return(JExpr._super().invoke("clone"));
    }

    private void generateFieldMapMethod(JDefinedClass dc, List<DcsField> dfList, boolean isAbstract, boolean isOverride)
            throws ClassNotFoundException
    {
        JType retType = cm.ref("java.util.Map").narrow(cm.ref("String"), cm.ref("String"));
        JType solidType = cm.ref("java.util.HashMap").narrow(new ArrayList<>()); // java 8 type inference
        JMethod getFMap = dc.method(JMod.PUBLIC + (isAbstract ? JMod.ABSTRACT : 0), retType, "getFieldsMap");
        getFMap.javadoc().add("Returns a map containing all the fields. The key is a field's name and the value is the field's value");
        if (isOverride) {
            getFMap.annotate(Override.class);
        }

        if (!isAbstract) {
            JBlock body = getFMap.body();
            JVar fm = body.decl(retType, "fm", JExpr._new(solidType));
            for (DcsField f : dfList) {
                body.invoke(fm,"put").arg(f.getName()).arg(JExpr.ref(f.getName()));
            }
            body._return(fm);
        }
    }
}
