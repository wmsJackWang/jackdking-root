package annotation.test;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

public class AnnotationTest {
    public static void main(String[] args) {
        System.out.println("ParentController getAnnotation @RequestMapping: " + AnnotationUtils.getAnnotation(ParentController.class, RequestMapping.class));
        System.out.println("ChildController getAnnotation @RequestMapping: " +AnnotationUtils.getAnnotation(ChildController.class, RequestMapping.class));
        System.out.println();

        System.out.println("ParentController findAnnotation @RequestMapping: " + AnnotationUtils.findAnnotation(ParentController.class, RequestMapping.class));
        System.out.println("ParentController findAnnotation @RequestMapping: " + AnnotationUtils.findAnnotation(ChildController.class, RequestMapping.class));
        System.out.println();

        System.out.println("ParentController isAnnotated @RequestMapping: " + AnnotatedElementUtils.isAnnotated(ParentController.class, RequestMapping.class));
        System.out.println("ParentController getMergedAnnotation @RequestMapping: " + AnnotatedElementUtils.getMergedAnnotation(ParentController.class, RequestMapping.class));
        System.out.println("ChildController isAnnotated @RequestMapping: " + AnnotatedElementUtils.isAnnotated(ChildController.class, RequestMapping.class));
        System.out.println("ChildController getMergedAnnotation @RequestMapping: " + AnnotatedElementUtils.getMergedAnnotation(ChildController.class, RequestMapping.class));
        System.out.println();

        System.out.println("ParentController hasAnnotation @RequestMapping: " + AnnotatedElementUtils.hasAnnotation(ParentController.class, RequestMapping.class));
        System.out.println("ParentController findMergedAnnotation @RequestMapping: " + AnnotatedElementUtils.findMergedAnnotation(ParentController.class, RequestMapping.class));
        System.out.println("ChildController hasAnnotation @RequestMapping: " + AnnotatedElementUtils.hasAnnotation(ChildController.class, RequestMapping.class));
        System.out.println("ChildController findMergedAnnotation @RequestMapping: " + AnnotatedElementUtils.findMergedAnnotation(ChildController.class, RequestMapping.class));
    }
}

@RequestMapping(value = "parent/controller")
class ParentController {
}

class ChildController extends ParentController {
}