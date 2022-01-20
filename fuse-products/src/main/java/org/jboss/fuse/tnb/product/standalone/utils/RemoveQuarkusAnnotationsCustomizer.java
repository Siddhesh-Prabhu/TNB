package org.jboss.fuse.tnb.product.standalone.utils;

import org.jboss.fuse.tnb.product.integration.Customizer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.runtime.annotations.RegisterForReflection;

public class RemoveQuarkusAnnotationsCustomizer extends Customizer {
    @Override
    public void customize() {
        List<CompilationUnit> compilationUnits = new ArrayList<>();
        compilationUnits.add(getIntegrationBuilder().getRouteBuilder());
        compilationUnits.addAll(getIntegrationBuilder().getAdditionalClasses());
        for (CompilationUnit cu : compilationUnits) {
            cu.accept(new ModifierVisitor<>() {
                @Override
                public Visitable visit(ClassOrInterfaceDeclaration n, Object arg) {
                    super.visit(n, arg);
                    // Remove annotation if present
                    n.getAnnotations().removeIf(a -> RegisterForReflection.class.getSimpleName().equals(a.getName().getIdentifier()));
                    return n;
                }
            }, null);
            // Remove import if present
            cu.getImports().removeIf(i -> i.getName().getIdentifier().contains(RegisterForReflection.class.getSimpleName()));
        }
    }
}
