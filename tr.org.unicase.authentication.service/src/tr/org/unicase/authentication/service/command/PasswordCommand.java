package tr.org.unicase.authentication.service.command;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class PasswordCommand implements CommandProvider {

	private PasswordEncoder encoder = null;

	@Override
	public String getHelp() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("---UniCase Authentication Commands---\n\t");
		buffer.append("encodePassword <rawPassword> - Encode Raw Password\n\t");
		buffer.append("resetUserPassword <userId> <newRawPassword> - Reset User's Password\n\t");
		return buffer.toString();
	}

	public void _encodePassword(CommandInterpreter ci) {
		String typeIdAsString = ci.nextArgument();
		if (typeIdAsString != null && !typeIdAsString.trim().isEmpty()) {
			String encodePassword = encoder.encodePassword(typeIdAsString, null);
			ci.println(typeIdAsString + " -> " + encodePassword);
		} else {
			printUsage(ci, "encodePassword");
		}
	}
	
	@SuppressWarnings("unused")
	public void _resetUserPassword(CommandInterpreter ci) {
		String userIdAsString = ci.nextArgument();
		String userNewPassword = ci.nextArgument();
		String methodName = "referenceUserPassword";
		if (userIdAsString != null && !userIdAsString.trim().isEmpty()) {
			try {
				Long userId = Long.parseLong(userIdAsString.trim());
				ci.println("resetUserPassword daha implement edilmedi");
			} catch (NumberFormatException nfe) {
				printUsage(ci, methodName);
			}
		} else {
			printUsage(ci, methodName);
		}
	}
	
	private void printUsage(CommandInterpreter ci, String commandName) {
		ci.println("Usage: " + commandName + " <rawPassword>");
	}

	

	public PasswordEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(PasswordEncoder encoder) {
		this.encoder = encoder;
	}

}
