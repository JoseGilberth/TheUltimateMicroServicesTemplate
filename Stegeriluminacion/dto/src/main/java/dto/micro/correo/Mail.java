package dto.micro.correo;

import java.util.Map;

import lombok.Data;

@Data
public class Mail {

	private String from;
	private String[] to;
	private String[] cc;
	private String[] bcc;
	private String subject;
	private Map<String, Object> variables;
 
	public Mail() {

	}


}
