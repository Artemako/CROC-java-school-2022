package src.ru.croc.tasks.task4;

public class AnnotatedImage {
    private final String imagePath;
    private final Annotation[] annotations;

    public AnnotatedImage(String imagePath, Annotation... annotations) {
        this.imagePath = imagePath;
        this.annotations = annotations;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public Annotation[] getAnnotations() {
        return this.annotations;
    }

    @Override
    public String toString() {
        System.out.println("Start AnnotatedImage.");
        int i = 1;
        for (Annotation annotation : annotations) {
            System.out.println(i + ") " + annotation);
            i++;
        }
        return "End AnnotatedImage.";
    }

}
