package org.jackdking.common.annotation;


import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;


public class CustomAnnotationProcessor extends AbstractProcessor {


    private Set<String> supportedAnnotationTypes = new HashSet<String>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        supportedAnnotationTypes.add(MyMethodAnnotation.class
                .getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        for (TypeElement typeElement : annotations) {
            for (Element element : roundEnv
                    .getElementsAnnotatedWith(typeElement)) {
                String info = "### content = " + element.toString();
                messager.printMessage(Kind.NOTE, info);
                //获取Annotation
                MyMethodAnnotation myMethodAnnotation = element
                        .getAnnotation(MyMethodAnnotation.class);

                if (myMethodAnnotation != null) {
                    int layoutId = myMethodAnnotation.layoutId();
                    int viewType = myMethodAnnotation.viewType();
                    String viewHolder = myMethodAnnotation.viewHolder();
                    messager.printMessage(Kind.NOTE, "layoutId = " + layoutId
                            + ",viewType = " + viewType + ",viewHolder = "
                            + viewHolder);
                }

            }
        }
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return supportedAnnotationTypes;
    }

}