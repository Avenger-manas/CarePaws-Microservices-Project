package dolpi.CarePaws.ExceptionHandler;

public class ResourcesNotFound extends RuntimeException{
    public ResourcesNotFound(String message){
        super(message);
    }
}