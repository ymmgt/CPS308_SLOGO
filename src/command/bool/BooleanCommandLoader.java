package command.bool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import command.Command;

public class BooleanCommandLoader {

	public Map<String, Command> load(List<String> names) {
		String prefix = getClass().getPackage().getName() + ".";
		Map<String, Command> map = new HashMap<>();
		names.forEach((name) -> {
			try {
				map.put(name, (Command) Class.forName(prefix + name).getDeclaredConstructor().newInstance());
			} catch (Exception e) {
				System.err.println(name + " not found");
			}
		});
		return map;
	}

}
