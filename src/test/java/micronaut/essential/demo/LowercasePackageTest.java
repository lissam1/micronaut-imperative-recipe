package micronaut.essential.demo;

import org.junit.jupiter.api.Test;
import org.openrewrite.PathUtils;
import org.openrewrite.java.JavaParser;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import java.nio.file.Paths;

import static org.openrewrite.java.Assertions.java;
import static org.assertj.core.api.Assertions.assertThat;

class LowercasePackageTest implements RewriteTest {

    // Note, you can define defaults for the RecipeSpec and these defaults will be
    // used for all tests.
    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new LowercasePackage());
    }

    // A Java source file that already has a lowercase package name should be left
    // unchanged.
    @Test
    void packageIsAlreadyLowercase() {
        rewriteRun(
                java(
                        """
                            package com.yourorg;
        
                            class A {}
                        """
                )
        );
    }

    // Assert that a Java source file with uppercase letters in its package name
    // is correctly transformed by the recipe.
    @Test
    void lowerCasePackage() {
        // Each test can customize the RecipeSpec before the test is executed.
        // In this case, the recipe has already been defined in defaults(). We
        // can extend that and add a parser that logs warnings and errors for
        // just this test.
        rewriteRun(
                // You'll need to have an SLF4J logger configured to see
                // these warnings and errors.
                spec -> spec
                        .parser(JavaParser.fromJavaVersion()
                                .logCompilationWarningsAndErrors(true)),
                java(
                        // The Java source file before the recipe is run:
                        """
                            package com.UPPERCASE.CamelCase;
                            class FooBar {}
                        """,
                        // The expected Java source file after the recipe is run:
                        """
                            package com.uppercase.camelcase;
                            class FooBar {}
                        """,
                        // An optional callback that can be used after the recipe has been
                        // executed to assert additional conditions on the resulting source file:
                        spec -> spec.afterRecipe(cu -> assertThat(PathUtils.equalIgnoringSeparators(cu.getSourcePath(), Paths.get("com/uppercase/camelcase/FooBar.java"))).isTrue()))
        );
    }

    // Demonstrates how you can do multiple checks in one test.
    //
    // You can also combine different types (such as `java` and `text`) in one test:
    // https://github.com/openrewrite/rewrite-spring/blob/main/src/testWithSpringBoot_2_7/java/org/openrewrite/java/spring/boot2/MoveAutoConfigurationToImportsFileTest.java#L177-L224
    @Test
    void combinedExample() {
        rewriteRun(
                // Assert the first source file is not modified.
                java(
                        """
                            package com.lowercase;
                            class A {}
                        """
                ),
                // Assert the second source file is modified.
                java(
                        """
                            package com.UPPERCASE.CamelCase;
                            class FooBar {}
                        """,
                        """
                            package com.uppercase.camelcase;
                            class FooBar {}
                        """
                )
        );
    }
}
