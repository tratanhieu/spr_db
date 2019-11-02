package dashboard.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class DataUtils {
	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");  
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

	public static String makeSlug(String input) {
	    String noWhiteSpace = WHITESPACE.matcher(input).replaceAll("-");
	    String normalized = Normalizer.normalize(noWhiteSpace, Form.NFD);
		String slug = NONLATIN.matcher(normalized).replaceAll("");

	    return slug.toLowerCase(Locale.ENGLISH);  
	}

	// Hiện tại thì new Date() đã.. = ) )
	public static Date getSystemDate() {
		return new Date();
	}
}
