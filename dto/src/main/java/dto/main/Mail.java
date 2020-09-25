package dto.main;

import java.util.Date;
import java.util.Map;

import lombok.Data;

@Data
public class Mail {

	private String from;
	private String[] to;
	private String[] cc;
	private String[] bcc;
	private String subject;
	private Integer priority;
	private String replyTo;
	private Date sentDate;
	private Map<String, Object> variables;
 
}
