package org.jboss.fuse.tnb.product.csb.customizer;

import org.jboss.fuse.tnb.product.util.jparser.AnnotationUtils;

import java.util.List;

/**
 * For camel spring boot we add @Component to RouteBuilder class
 */
public class ComponentCustomizer extends SpringbootCustomizer {

    @Override
    public void customize() {
        getIntegrationBuilder().getRouteBuilder().ifPresent(rb ->
            AnnotationUtils.addAnnotationsToRouteBuilder(rb, List.of("org.springframework.stereotype.Component"), List.of("Component")));
    }
}
