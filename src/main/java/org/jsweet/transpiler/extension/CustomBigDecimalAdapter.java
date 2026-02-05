package org.jsweet.transpiler.extension;

import java.math.BigDecimal;

import javax.lang.model.element.Element;

import org.jsweet.transpiler.model.MethodInvocationElement;
import org.jsweet.transpiler.model.NewClassElement;

public class CustomBigDecimalAdapter extends PrinterAdapter {

    public CustomBigDecimalAdapter(PrinterAdapter parent) {
        super(parent);

        // Mapeia tipo
        addTypeMapping(BigDecimal.class.getName(), "Big");
    }

    @Override
    public boolean substituteNewClass(NewClassElement newClass) {

        String className = newClass.getTypeAsElement().toString();

        if (BigDecimal.class.getName().equals(className)) {
            print("new Big(")
                .printArgList(newClass.getArguments())
                .print(")");
            return true;
        }

        return super.substituteNewClass(newClass);
    }

    @Override
    public boolean substituteMethodInvocation(MethodInvocationElement invocation) {

        if (invocation.getTargetExpression() != null) {

            Element targetType = invocation.getTargetExpression().getTypeAsElement();

            if (BigDecimal.class.getName().equals(targetType.toString())) {

                switch (invocation.getMethodName()) {

                    case "add":
                        print(invocation.getTargetExpression())
                            .print(".plus(")
                            .printArgList(invocation.getArguments())
                            .print(")");
                        return true;

                    case "subtract":
                        print(invocation.getTargetExpression())
                            .print(".minus(")
                            .printArgList(invocation.getArguments())
                            .print(")");
                        return true;

                    case "multiply":
                        print(invocation.getTargetExpression())
                            .print(".times(")
                            .printArgList(invocation.getArguments())
                            .print(")");
                        return true;

                    case "divide":
                        print(invocation.getTargetExpression())
                            .print(".div(")
                            .printArgList(invocation.getArguments())
                            .print(")");
                        return true;
                }
            }
        }

        return super.substituteMethodInvocation(invocation);
    }
}
