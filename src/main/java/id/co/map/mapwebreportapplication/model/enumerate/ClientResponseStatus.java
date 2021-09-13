package id.co.map.mapwebreportapplication.model.enumerate;

public enum ClientResponseStatus {
	
	USER_CREATED(1001, "User Created."),
    USER_DELETED(1003, "User Deleted."),
    
    SERVER_ERROR(5000, "Something went wrong."),
    SQL_ERROR(5001, "SQL Server Error."),;

    private final int code;
    private final String message;

    ClientResponseStatus(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }

}
