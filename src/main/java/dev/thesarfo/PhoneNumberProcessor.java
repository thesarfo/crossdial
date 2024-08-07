package dev.thesarfo;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes("dev.thesarfo.PhoneNumber")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class PhoneNumberProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(PhoneNumber.class)) {
            if (element.getKind() != ElementKind.FIELD && element.getKind() != ElementKind.PARAMETER) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                        "@PhoneNumber can only be applied to fields or parameters.", element);
                return true;
            }
            if (!element.asType().toString().equals("java.lang.String")) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                        "@PhoneNumber can only be applied to Strings.", element);
            }
        }
        return true;
    }
}

