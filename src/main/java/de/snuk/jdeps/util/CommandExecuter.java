package de.snuk.jdeps.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.snuk.jdeps.model.MyClass;
import de.snuk.jdeps.model.MyPackage;

public class CommandExecuter {

	public static List<MyPackage> executeCommand(final String command) throws IOException {
		final Process exec = Runtime.getRuntime().exec(command);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
		final BufferedReader error = new BufferedReader(new InputStreamReader(exec.getErrorStream()));

		List<String> output = new ArrayList<>();

		output.addAll(reader.lines().map(s -> s.trim()).collect(Collectors.toList()));
		output.addAll(error.lines().collect(Collectors.toList()));

		reader.close();
		error.close();

		List<MyPackage> parseResult = parseResult(output);

		return parseResult;
	}

	private static List<MyPackage> parseResult(List<String> lines) {

		lines.remove(0);
		lines.remove(0);

		List<MyPackage> output = new ArrayList<>();

		MyPackage currentPackage = null;

		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			// System.out.println(line);
			if (currentPackage == null) {
				int indexOf = line.indexOf(" ");
				String substring = line.substring(0, indexOf);
				// System.out.println(substring);
				currentPackage = new MyPackage(substring);
			} else if (!line.startsWith("->")) {
				output.add(currentPackage);
				int indexOf = line.indexOf(" ");
				String substring = line.substring(0, indexOf);
				// System.out.println(substring);
				currentPackage = new MyPackage(substring);
			}

			if (line.startsWith("->")) {

				String substring = line.substring(3);

				if (substring.indexOf(" ") > 0) {
					substring = substring.substring(0, substring.indexOf(" "));
				}

				currentPackage.addClass(new MyClass(substring, currentPackage));
			}

			if (i == lines.size() - 1) {
				output.add(currentPackage);
			}
		}

		return output;
	}
}
