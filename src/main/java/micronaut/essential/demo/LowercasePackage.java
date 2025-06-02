package micronaut.essential.demo;


import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.internal.lang.Nullable;
import org.openrewrite.java.ChangePackage;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;

import java.time.Duration;
import java.util.Collections;
import java.util.Set;

public class LowercasePackage extends Recipe {
    @Override
    public String getDisplayName() {
        return "Rename packages to lowercase";
    }

    @Override
    public String getDescription() {
        return "By convention, all Java package names should contain only lowercase letters, numbers, and dashes. " +
                "This recipe converts any uppercase letters in package names to be lowercase.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaIsoVisitor<ExecutionContext>() {
            @Override
            public J.Package visitPackage(J.Package pkg, ExecutionContext executionContext) {
                // Grab the package name without spaces
                String packageText = pkg.getExpression().print(getCursor()).replaceAll("\\s", "");
                String lowerCase = packageText.toLowerCase();

                if(!packageText.equals(lowerCase)) {
                    doAfterVisit(new ChangePackage(packageText, lowerCase, true).getVisitor());
                }

                return pkg;
            }
        };
    }
}
