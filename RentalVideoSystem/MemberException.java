
public class MemberException extends Exception{
	public MemberException(String s) {
		super(s);
	}

	public MemberException() {
		super();
	}
	
	public MemberException(String s, Throwable c) {
		super(s, c);
	}
	
	public MemberException(Throwable c) {
		super(c);
	}  
}
